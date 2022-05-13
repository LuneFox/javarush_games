package com.javarush.games.minesweeper.model.board;

import com.javarush.games.minesweeper.DeveloperOption;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.gui.interactive.SwitchSelector;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.model.board.field.Cell;
import com.javarush.games.minesweeper.model.board.field.FieldDAO;
import com.javarush.games.minesweeper.model.player.Player;
import com.javarush.games.minesweeper.model.shop.Shop;
import com.javarush.games.minesweeper.model.shop.items.Shield;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BoardManager {
    private final MinesweeperGame game;
    private final FieldDAO fieldDAO;
    private final FlagManager flagManager;
    private final Timer timer;

    private boolean isUnableToCheatMore;
    private boolean isFlagExplosionAllowed;
    private boolean isFirstMove;
    private boolean isRecursiveMove;
    private boolean miniBombHitMine;

    public BoardManager(MinesweeperGame game) {
        this.game = game;
        this.fieldDAO = new FieldDAO();
        this.timer = new Timer();
        this.flagManager = new FlagManager(game);
    }

    /*
     * Creation
     */

    public void reset() {
        fieldDAO.createNewField();
        plantMines();
        enumerateMinedCells();
        timer.reset();
        isRecursiveMove = false;
        isFirstMove = true;
    }

    private void plantMines() {
        double numberOfMinesToPlant = (Options.difficulty / 1.5);
        while (fieldDAO.getAllCells(Cell::isMined).size() < numberOfMinesToPlant) {
            int randomX = game.getRandomNumber(10);
            int randomY = game.getRandomNumber(10);
            Cell cell = getCell(randomX, randomY);
            if (!cell.isMined()) {
                cell.setMined(true);
            }
        }
    }

    public void enumerateMinedCells() {
        fieldDAO.getAllCells(Cell::isNumerable).forEach(cell -> {
            final int minesCount = fieldDAO.getNeighborCells(cell, Cell::isMined).size();
            cell.setCountMinedNeighbors(minesCount);
        });
    }

    /*
     * Draw
     */

    public void draw() {
        drawField();
        timer.draw();
        game.getShop().drawItemAssets();
    }

    private void drawField() {
        fieldDAO.getAllCells().forEach(Cell::draw);
    }

    /*
     * Interaction
     */

    public void interactWithLeftClick(int x, int y) {
        Cell cell = fieldDAO.getCellByCoordinates(x, y);
        if (cell.isShop()) {
            Phase.setActive(Phase.SHOP);
            return;
        }

        if (game.isScannerOrBombActivated()) {
            game.aimWithScannerOrBomb(cell);
            return;
        }

        openCell(cell);
    }

    public void interactWithRightClick(int x, int y) {
        Cell cell = fieldDAO.getCellByCoordinates(x, y);
        if (cell.isShop()) {
            PopUpMessage.show("двери магазина");
            return;
        }

        flagManager.swapFlag(cell);  // works only on closed cells
        openSurroundingCells(cell);  // works only on open cells
    }

    /*
     * Open cell
     */

    private void openCell(Cell cell) {
        if (cell.cannotBeOpened()) return;

        if (isFirstMove) {
            cell = transformToEmptyShopCell(cell);
            isFirstMove = false;
        }

        if (cell.isMined()) {
            tryUsingShieldOnMinedCell(cell);
            if (!cell.isShielded()) {
                gameOverFromCellExplosion(cell);
                return;
            }
        }

        cell.open();

        if (cell.isEmpty()) {
            isRecursiveMove = true;
            fieldDAO.getNeighborCells(cell, Cell::isClosed).forEach(this::openCell);
        }

        registerScoreAndMoney(cell);
        checkVictory();
    }

    private Cell transformToEmptyShopCell(Cell cell) {
        List<Cell> oldFlaggedCells = collectFlagsFromField();
        cell = restartUntilCellIsEmpty(cell);
        cell.setShop(true);
        placeCollectedFlagsOnNewCells(oldFlaggedCells);
        return cell;
    }

    private Cell restartUntilCellIsEmpty(Cell cell) {
        while (!cell.isEmpty()) {
            reset();
            cell = getCell(cell.x, cell.y);
        }
        return cell;
    }

    private List<Cell> collectFlagsFromField() {
        List<Cell> flaggedCellsBefore = fieldDAO.getAllCells(Cell::isFlagged);
        flaggedCellsBefore.forEach(flagManager::returnFlagToPlayerInventory);
        return flaggedCellsBefore;
    }

    private void placeCollectedFlagsOnNewCells(List<Cell> oldFLaggedCells) {
        oldFLaggedCells.forEach(oldFlaggedCell -> {
            Cell newCellToFlag = fieldDAO.getCell(oldFlaggedCell.x, oldFlaggedCell.y);
            flagManager.placeFlagFromPlayerInventory(newCellToFlag);
        });
    }

    private void tryUsingShieldOnMinedCell(Cell cell) {
        Shield shield = game.getShop().getShield();
        if (shield.isActivated()) shield.use(cell);
    }

    private void gameOverFromCellExplosion(Cell cell) {
        cell.setGameOverCause(true);
        game.lose();
    }

    private void registerScoreAndMoney(Cell cell) {
        addPlayerMove();
        addTimerScore();
        useDice(cell);
        earnMoneyFromCell(cell);
    }

    private void addPlayerMove() {
        if (isRecursiveMove()) return;
        game.getPlayer().addMove();
    }

    private void addTimerScore() {
        if (isRecursiveMove) return;
        game.getScore().addTimerScore();
        timer.reset();
    }

    private void useDice(Cell cell) {
        game.getShop().getDice().use(cell);
    }

    private void earnMoneyFromCell(Cell cell) {
        final Player player = game.getPlayer();
        player.gainMoney(cell.produceMoney());
    }

    private void checkVictory() {
        final long countClosedCells = fieldDAO.getAllCells(Cell::isClosed).size();
        final long countDangerousCells = fieldDAO.getAllCells(Cell::isDangerousToOpen).size();

        if (countClosedCells == countDangerousCells) {
            game.win();
        }
    }

    /*
     * Destroy cell (mini bomb action)
     */

    public void destroyCell(Cell cell) {
        if (cell.isIndestructible() && !isRecursiveMove) {
            PopUpMessage.show("Не получилось!");
            return;
        }

        addPlayerMove();
        addTimerScore();
        flagManager.returnFlagToShop(cell);

        if (cell.isMined()) {
            PopUpMessage.show(isRecursiveMove ? "Взорвались мины!" : "Взорвалась мина!");

            miniBombHitMine = true;
            isRecursiveMove = true;
            isFlagExplosionAllowed = true;

            cell.destroy();

            fieldDAO.getNeighborCells(cell, Cell::isMined).forEach(this::destroyCell);
        } else {
            cell.destroy();
        }
        enumerateMinedCells();
        checkVictory();
    }

    public void cleanUpAfterMineDestruction() {
        if (!miniBombHitMine) return;

        fieldDAO.getAllCells()
                .stream()
                .filter(Cell::isDestroyed)
                .filter(Cell::wasMinedBeforeDestruction)
                .collect(Collectors.toList()).forEach(explodedMine ->
                        fieldDAO.getNeighborCells(explodedMine, Cell::isClosed)
                                .forEach(this::openCell));

        refreshOpenedCellsGraphics();
        miniBombHitMine = false;
    }

    /*
     * Scan neighbors (scanner action)
     */

    public void scanNeighbors(Cell cell) {  // action for Scanner
        List<Cell> safeCells = fieldDAO.getCellsIn3x3area(cell).stream()
                .filter(Cell::isSafeToOpen)
                .collect(Collectors.toList());
        if (safeCells.size() != 0) {
            PopUpMessage.show("Сканирование...");
            scanRandomCell(safeCells);
        } else {
            PopUpMessage.show("Нечего сканировать");
            placeFlagsForPlayer(cell);
        }
    }

    private void scanRandomCell(List<Cell> safeNeighbors) {
        Cell cell = safeNeighbors.get(game.getRandomNumber(safeNeighbors.size()));
        if (cell.isFlagged()) { // by mistake
            flagManager.swapFlag(cell);
        }
        cell.setScanned(true);
        openCell(cell);
    }

    private void placeFlagsForPlayer(Cell cell) {
        fieldDAO.getCellsIn3x3area(cell, Cell::isClosed).forEach(closedCell -> {
            final Player player = game.getPlayer();
            Shop shop = game.getShop();

            if (player.countFlags() == 0) {
                shop.give(shop.getFlag());
            }

            flagManager.placeFlagFromPlayerInventory(closedCell);
        });
    }

    /*
     * Other
     */

    private void openSurroundingCells(Cell cell) {
        if (game.isScannerOrBombActivated()) return;
        if (!cell.isOpen() || cell.isEmpty() || cell.isMined()) return;

        int countMinedNeighbors = cell.getCountMinedNeighbors();
        long countShieldedNeighbors = fieldDAO.getNeighborCells(cell, Cell::isShielded).size();
        long countFlaggedNeighbors = fieldDAO.getNeighborCells(cell, Cell::isFlagged).size();

        if (countMinedNeighbors == countFlaggedNeighbors + countShieldedNeighbors) {
            fieldDAO.getNeighborCells(cell).forEach(this::openCell);
        }
    }

    public void revealMines() {
        fieldDAO.getAllCells(Cell::isMined).forEach(Cell::open);
    }

    public void refreshOpenedCellsGraphics() {
        if (game.isStopped()) return;
        fieldDAO.getAllCells(Cell::isOpen).forEach(Cell::setGraphicsForOpenedState);
    }


    /*
     * Cheats
     */

    @DeveloperOption
    public void autoFlag() {
        if (!Options.developerModeEnabled) return;

        SwitchSelector selector = Options.autoBuyFlagsSelector;
        if (!selector.isEnabled()) {
            selector.click(selector.x, selector.y); // click self
        }

        boolean[] success = new boolean[1];

        fieldDAO.getAllCells(Cell::isNumerable).forEach(cell -> {
            if (!cell.isOpen()) return;

            List<Cell> dangerousNeighbors = fieldDAO.getNeighborCells(cell, Cell::isDangerousToOpen);
            List<Cell> closedNeighbors = fieldDAO.getNeighborCells(cell, Cell::isClosed);

            if (dangerousNeighbors.size() == closedNeighbors.size()) {
                dangerousNeighbors.stream()
                        .filter(Cell::isNotFlagged)
                        .forEach(dangerousNeighbor -> {
                            if (dangerousNeighbor.isFlagged()) return;

                            if (game.getPlayer().countFlags() == 0) {
                                Shop shop = game.getShop();
                                shop.sellFlag();
                            }

                            flagManager.swapFlag(dangerousNeighbor);
                            success[0] = true;
                        });
            }
        });

        if (!success[0]) {
            PopUpMessage.show("DEV: CANNOT FLAG");
            isUnableToCheatMore = true;
        } else {
            PopUpMessage.show("DEV: AUTO FLAG");
        }
    }

    @DeveloperOption
    public void autoOpen() {
        if (!Options.developerModeEnabled) return;

        long closedCells = fieldDAO.getAllCells(Cell::isClosed).size();

        if (isFirstMove) {
            List<Cell> allCells = fieldDAO.getAllCells();
            Cell randomCell = allCells.get(game.getRandomNumber(allCells.size()));
            game.onMouseLeftClick(randomCell.x * 10, randomCell.y * 10);
        } else {
            fieldDAO.getAllCells().stream()
                    .filter(Cell::isNumerable)
                    .filter(Cell::isOpen)
                    .forEach(cell -> game.onMouseRightClick(cell.x * 10, cell.y * 10));
        }
        PopUpMessage.show(fieldDAO.getAllCells(Cell::isClosed).size() == closedCells ? "DEV: CANNOT OPEN" : "DEV: AUTO OPEN");
    }

    @DeveloperOption
    public void autoScan() {
        if (!Options.developerModeEnabled) return;

        List<Cell> safeCells = fieldDAO.getAllCells(Cell::isSafeToOpen);
        if (safeCells.isEmpty()) return;

        Cell randomCell = safeCells.get(game.getRandomNumber(safeCells.size()));
        Shop shop = game.getShop();
        shop.getScanner().activate();
        game.onMouseLeftClick(randomCell.x * 10, randomCell.y * 10);
        game.onMouseLeftClick(randomCell.x * 10, randomCell.y * 10);
        PopUpMessage.show("DEV: RANDOM SCAN");
    }

    @DeveloperOption
    public void autoSolve() {
        if (!Options.developerModeEnabled) return;

        long closedCells = fieldDAO.getAllCells(Cell::isClosed).size();
        isUnableToCheatMore = false;
        int limit = 0;
        while (!isUnableToCheatMore) {
            autoOpen();
            autoFlag();
            if (limit++ > closedCells) {
                // Limit is set to prevent accidental infinite loops
                break;
            }
        }
        autoOpen();
        PopUpMessage.show(fieldDAO.getAllCells(Cell::isClosed).size() == closedCells ? "DEV: CANNOT SOLVE!" : "DEV: SOLVING...");
    }


    /*
     * Delegations
     */
    public Cell getCell(int x, int y) {
        return fieldDAO.getCell(x, y);
    }

    public List<Cell> getAllCells(Predicate<Cell> predicate) {
        return fieldDAO.getAllCells(predicate);
    }


    /*
     * Getters, setters
     */
    public boolean isFlagExplosionAllowed() {
        return isFlagExplosionAllowed;
    }

    public void setFlagExplosionAllowed(boolean flagExplosionAllowed) {
        isFlagExplosionAllowed = flagExplosionAllowed;
    }

    public boolean isRecursiveMove() {
        return isRecursiveMove;
    }

    public void setRecursiveMove(boolean recursiveMove) {
        isRecursiveMove = recursiveMove;
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public Timer getTimer() {
        return timer;
    }
}
