package com.javarush.games.minesweeper.model.board;

import com.javarush.games.minesweeper.DeveloperOption;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.gui.interactive.SwitchSelector;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.model.board.field.Cell;
import com.javarush.games.minesweeper.model.board.field.FieldDao;
import com.javarush.games.minesweeper.model.player.Player;
import com.javarush.games.minesweeper.model.player.Score;
import com.javarush.games.minesweeper.model.shop.Shop;
import com.javarush.games.minesweeper.model.shop.items.Dice;
import com.javarush.games.minesweeper.model.shop.items.Shield;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BoardManager {
    private final MinesweeperGame game;
    private final FieldDao fieldDao;
    private final FlagManager flagManager;
    private final Timer timer;

    private boolean stopAutoSolve;
    private boolean isFlagExplosionAllowed;
    private boolean isFirstMove;
    private boolean isRecursiveMove;
    private boolean miniBombHitMine;

    public BoardManager(MinesweeperGame game) {
        this.game = game;
        this.fieldDao = new FieldDao();
        this.timer = new Timer();
        this.flagManager = new FlagManager(game);
    }

    /*
     * Creation
     */

    public void reset() {
        fieldDao.createNewField();
        plantMines();
        enumerateMinedCells();
        timer.reset();
        isRecursiveMove = false;
        isFirstMove = true;
    }

    private void plantMines() {
        double numberOfMinesToPlant = (Options.getDifficulty() / 1.5);
        while (fieldDao.getAllCells(Cell::isMined).size() < numberOfMinesToPlant) {
            int randomX = game.getRandomNumber(10);
            int randomY = game.getRandomNumber(10);
            Cell cell = getCell(randomX, randomY);
            if (!cell.isMined()) {
                cell.setMined(true);
            }
        }
    }

    public void enumerateMinedCells() {
        fieldDao.getAllCells(Cell::isNumerable).forEach(cell -> {
            final int minesCount = fieldDao.getNeighborCells(cell, Cell::isMined).size();
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
        fieldDao.getAllCells().forEach(Cell::draw);
    }

    /*
     * Interaction
     */

    public void interactWithLeftClick(int x, int y) {
        Cell cell = fieldDao.getCellByCoordinates(x, y);
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
        Cell cell = fieldDao.getCellByCoordinates(x, y);
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

        addMove();
        addMoney(cell);
        addTimerBonusScore();
        addDiceBonusScore(cell);

        if (cell.isEmpty()) {
            isRecursiveMove = true;
            fieldDao.getNeighborCells(cell, Cell::isClosed).forEach(this::openCell);
        }

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
        List<Cell> flaggedCellsBefore = fieldDao.getAllCells(Cell::isFlagged);
        flaggedCellsBefore.forEach(flagManager::returnFlagToPlayerInventory);
        return flaggedCellsBefore;
    }

    private void placeCollectedFlagsOnNewCells(List<Cell> oldFLaggedCells) {
        oldFLaggedCells.forEach(oldFlaggedCell -> {
            Cell newCellToFlag = fieldDao.getCell(oldFlaggedCell.x, oldFlaggedCell.y);
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

    private void addMove() {
        if (isRecursiveMove()) return;
        game.getPlayer().addMove();
    }

    private void addTimerBonusScore() {
        if (isRecursiveMove) return;
        final Score score = game.getScore();
        score.addTimerScore();
        timer.reset();
    }

    private void addDiceBonusScore(Cell cell) {
        final Shop shop = game.getShop();
        final Dice dice = shop.getDice();
        dice.use(cell);
    }

    private void addMoney(Cell cell) {
        final Player player = game.getPlayer();
        player.gainMoney(cell.produceMoney());
    }

    private void checkVictory() {
        final long countClosedCells = fieldDao.getAllCells(Cell::isClosed).size();
        final long countDangerousCells = fieldDao.getAllCells(Cell::isDangerousToOpen).size();

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

        addMove();
        addTimerBonusScore();
        flagManager.returnFlagToShop(cell);

        if (cell.isMined()) {
            PopUpMessage.show(isRecursiveMove ? "Взорвались мины!" : "Взорвалась мина!");

            miniBombHitMine = true;
            isRecursiveMove = true;
            isFlagExplosionAllowed = true;

            cell.destroy();

            fieldDao.getNeighborCells(cell, Cell::isMined).forEach(this::destroyCell);
        } else {
            cell.destroy();
        }
        enumerateMinedCells();
        checkVictory();
    }

    public void cleanUpAfterMineDestruction() {
        if (!miniBombHitMine) return;

        fieldDao.getAllCells()
                .stream()
                .filter(Cell::isDestroyed)
                .filter(Cell::wasMinedBeforeDestruction)
                .collect(Collectors.toList()).forEach(explodedMine ->
                        fieldDao.getNeighborCells(explodedMine, Cell::isClosed)
                                .forEach(this::openCell));

        refreshOpenedCellsGraphics();
        miniBombHitMine = false;
    }

    /*
     * Scan neighbors (scanner action)
     */

    public void scanNeighbors(Cell cell) {  // action for Scanner
        List<Cell> safeCells = fieldDao.getCellsIn3x3area(cell).stream()
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
        fieldDao.getCellsIn3x3area(cell, Cell::isClosed).forEach(closedCell -> {
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
        long countShieldedNeighbors = fieldDao.getNeighborCells(cell, Cell::isShielded).size();
        long countFlaggedNeighbors = fieldDao.getNeighborCells(cell, Cell::isFlagged).size();

        if (countMinedNeighbors == countFlaggedNeighbors + countShieldedNeighbors) {
            fieldDao.getNeighborCells(cell).forEach(this::openCell);
        }
    }

    public void revealMines() {
        fieldDao.getAllCells(Cell::isMined).forEach(Cell::open);
    }

    public void refreshOpenedCellsGraphics() {
        if (game.isStopped()) return;
        fieldDao.getAllCells(Cell::isOpen).forEach(Cell::setGraphicsForOpenedState);
    }


    /*
     * Cheats
     */

    @DeveloperOption
    public void autoFlag() {
        if (!Options.isDeveloperModeEnabled()) return;

        forceEnableAutoBuyFlags();

        boolean[] success = new boolean[1];

        fieldDao.getAllCells(Cell::isNumerable).forEach(cell -> {
            if (!cell.isOpen()) return;

            List<Cell> dangerousNeighbors = fieldDao.getNeighborCells(cell, Cell::isDangerousToOpen);
            List<Cell> closedNeighbors = fieldDao.getNeighborCells(cell, Cell::isClosed);

            if (dangerousNeighbors.size() == closedNeighbors.size()) {
                dangerousNeighbors.stream()
                        .filter(Cell::isNotFlagged)
                        .forEach(dangerousNeighbor -> {
                            buyFlagIfAbsent();
                            flagManager.swapFlag(dangerousNeighbor);
                            success[0] = true;
                        });
            }
        });

        showAutoFlagResult(success);
    }

    private void buyFlagIfAbsent() {
        if (game.getPlayer().countFlags() == 0) {
            Shop shop = game.getShop();
            shop.sellFlag();
        }
    }

    private void forceEnableAutoBuyFlags() {
        SwitchSelector selector = Options.getAutoBuyFlagsSelector();
        if (!selector.isEnabled()) {
            selector.click(selector.x, selector.y); // click self
        }
    }

    private void showAutoFlagResult(boolean[] success) {
        if (!success[0]) {
            stopAutoSolve = true;
            PopUpMessage.show("DEV: CANNOT FLAG");
        } else {
            PopUpMessage.show("DEV: AUTO FLAG");
        }
    }

    @DeveloperOption
    public void autoOpen() {
        if (!Options.isDeveloperModeEnabled()) return;

        long closedCells = fieldDao.getAllCells(Cell::isClosed).size();

        if (isFirstMove) {
            List<Cell> allCells = fieldDao.getAllCells();
            Cell randomCell = allCells.get(game.getRandomNumber(allCells.size()));
            game.onMouseLeftClick(randomCell.x * 10, randomCell.y * 10);
        } else {
            fieldDao.getAllCells().stream()
                    .filter(Cell::isNumerable)
                    .filter(Cell::isOpen)
                    .forEach(cell -> game.onMouseRightClick(cell.x * 10, cell.y * 10));
        }
        PopUpMessage.show(fieldDao.getAllCells(Cell::isClosed).size() == closedCells ? "DEV: CANNOT OPEN" : "DEV: AUTO OPEN");
    }

    @DeveloperOption
    public void autoScan() {
        if (!Options.isDeveloperModeEnabled()) return;

        List<Cell> safeCells = fieldDao.getAllCells(Cell::isSafeToOpen);
        if (safeCells.isEmpty()) return;

        Cell randomCell = safeCells.get(game.getRandomNumber(safeCells.size()));
        game.getShop().getScanner().activate();
        game.onMouseLeftClick(randomCell.x * 10, randomCell.y * 10);
        game.onMouseLeftClick(randomCell.x * 10, randomCell.y * 10);

        PopUpMessage.show("DEV: RANDOM SCAN");
    }

    @DeveloperOption
    public void autoSolve() {
        if (!Options.isDeveloperModeEnabled()) return;

        long closedCells = fieldDao.getAllCells(Cell::isClosed).size();
        stopAutoSolve = false;
        int limit = 0;

        while (!stopAutoSolve) {
            autoOpen();
            autoFlag();
            if (limit++ > closedCells) break; // Limit is set to prevent accidental infinite loops
        }
        autoOpen();

        PopUpMessage.show(fieldDao.getAllCells(Cell::isClosed).size() == closedCells ? "DEV: CANNOT SOLVE!" : "DEV: SOLVING...");
    }


    /*
     * Delegations
     */
    public Cell getCell(int x, int y) {
        return fieldDao.getCell(x, y);
    }

    public List<Cell> getAllCells(Predicate<Cell> predicate) {
        return fieldDao.getAllCells(predicate);
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
