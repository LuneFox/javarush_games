package com.javarush.games.snake.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.SnakeGame;
import com.javarush.games.snake.model.Node;
import com.javarush.games.snake.model.orbs.Orb;
import com.javarush.games.snake.model.enums.Element;

import java.util.ArrayList;
import java.util.List;

public class HelpPage {
    List<Message> messages;
    List<Orb> orbs;
    List<Node> nodes;

    private HelpPage() {
        this.messages = new ArrayList<>();
        this.orbs = new ArrayList<>();
        this.nodes = new ArrayList<>();
    }

    public static HelpPage getGoals() {
        HelpPage result = new HelpPage();

        result.messages.add(new Message(-1, 1, "GAME GOALS", Color.SKYBLUE));
        result.messages.add(new Message(-1, 5, "COLLECT TO WIN:", Color.YELLOW));
        result.messages.add(new Message(3, 7, "ORB OF WATER", Color.LIGHTBLUE));
        result.messages.add(new Message(3, 9, "ORB OF FIRE", Color.RED));
        result.messages.add(new Message(3, 11, "ORB OF EARTH", Color.ORANGE));
        result.messages.add(new Message(3, 13, "ORB OF AIR", Color.CYAN));
        result.messages.add(new Message(3, 15, "ORB OF POWER", Color.MAGENTA));
        result.messages.add(new Message(-1, 17, "COLLECT TO GROW:", Color.YELLOW));
        result.messages.add(new Message(3, 19, "ORB OF WISDOM", Color.MEDIUMPURPLE));

        result.orbs.add(Orb.create(1, 7, Element.WATER));
        result.orbs.add(Orb.create(1, 9, Element.FIRE));
        result.orbs.add(Orb.create(1, 11, Element.EARTH));
        result.orbs.add(Orb.create(1, 13, Element.AIR));
        result.orbs.add(Orb.create(1, 15, Element.ALMIGHTY));
        result.orbs.add(Orb.create(1, 19, Element.NEUTRAL));

        return result;
    }

    public static HelpPage getSnakeAbilities() {
        HelpPage result = new HelpPage();

        result.messages.add(new Message(-1, 1, "SNAKE ABILITIES", Color.SKYBLUE));
        result.messages.add(new Message(1, 5, "— NEUTRAL SNAKE", Color.LIGHTGREEN));
        result.messages.add(new Message(3, 7, "NO SPECIAL POWERS", Color.WHITE));
        result.messages.add(new Message(1, 9, "— WATER SNAKE", Color.LIGHTBLUE));
        result.messages.add(new Message(3, 11, "CAN SWIM, PUT OUT FIRE", Color.WHITE));
        result.messages.add(new Message(1, 13, "— FIRE SNAKE", Color.RED));
        result.messages.add(new Message(3, 15, "CAN BURN WOOD, HATES WATER", Color.WHITE));
        result.messages.add(new Message(1, 17, "— EARTH SNAKE", Color.ORANGE));
        result.messages.add(new Message(3, 19, "CAN USE WORMHOLES", Color.WHITE));
        result.messages.add(new Message(1, 21, "— AIR SNAKE", Color.CYAN));
        result.messages.add(new Message(3, 23, "FLIES OVER WALLS, CAN'T EAT", Color.WHITE));
        result.messages.add(new Message(1, 25, "— ALMIGHTY SNAKE", Color.MAGENTA));
        result.messages.add(new Message(3, 27, "INVULNERABLE SPACE EATER", Color.WHITE));

        return result;
    }

    public static HelpPage getTypesOfTerrain() {
        HelpPage result = new HelpPage();

        result.messages.add(new Message(-1, 1, "TYPES OF TERRAIN", Color.SKYBLUE));
        result.messages.add(new Message(3, 7, "GRASS FIELD", Color.WHITE));
        result.messages.add(new Message(3, 9, "DEEP WATER", Color.WHITE));
        result.messages.add(new Message(3, 11, "DRY SAND", Color.WHITE));
        result.messages.add(new Message(3, 13, "DENSE FOREST", Color.WHITE));
        result.messages.add(new Message(3, 15, "IMPASSABLE MOUNTAIN", Color.WHITE));
        result.messages.add(new Message(3, 17, "WOODEN FLOOR", Color.WHITE));
        result.messages.add(new Message(3, 19, "BURNING WOOD", Color.WHITE));
        result.messages.add(new Message(3, 21, "TALL WALL", Color.WHITE));
        result.messages.add(new Message(3, 23, "SECRET WORMHOLE", Color.WHITE));

        result.nodes.add(new Node(1, 7, SnakeGame.getInstance(), 0));
        result.nodes.add(new Node(1, 9, SnakeGame.getInstance(), 2));
        result.nodes.add(new Node(1, 11, SnakeGame.getInstance(), 8));
        result.nodes.add(new Node(1, 13, SnakeGame.getInstance(), 4));
        result.nodes.add(new Node(1, 15, SnakeGame.getInstance(), 6));
        result.nodes.add(new Node(1, 17, SnakeGame.getInstance(), 1));
        result.nodes.add(new Node(1, 19, SnakeGame.getInstance(), 3));
        result.nodes.add(new Node(1, 21, SnakeGame.getInstance(), 7));
        result.nodes.add(new Node(1, 23, SnakeGame.getInstance(), 5));

        return result;
    }

    public static HelpPage getTips1(){
        HelpPage result = new HelpPage();

        result.messages.add(new Message(-1, 1, "RULES AND TIPS (1)", Color.SKYBLUE));
        result.messages.add(new Message(1, 5, "YOU SHOULD COLLECT ALL ORBS", Color.WHITE));
        result.messages.add(new Message(1, 7, "AS FAST AS POSSIBLE.", Color.WHITE));
        result.messages.add(new Message(1, 10, "EATING ELEMENTAL ORBS LETS", Color.YELLOW));
        result.messages.add(new Message(1, 12, "YOU USE THEIR ELEMENTS.", Color.YELLOW));
        result.messages.add(new Message(1, 15, "PICKING UP AN ELEMENTAL ORB", Color.LAWNGREEN));
        result.messages.add(new Message(1, 17, "SWITCHES YOU TO ITS ELEMENT.", Color.LAWNGREEN));
        result.messages.add(new Message(1, 20, "YOU CAN'T SWAP ELEMENTS", Color.CYAN));
        result.messages.add(new Message(1, 22, "WHILE EATING A NEW ONE.", Color.CYAN));
        result.messages.add(new Message(1, 25, "YOU CAN TURN & SWAP ELEMENTS", Color.MAGENTA));
        result.messages.add(new Message(1, 27, "EVEN WHILE SLEEPING.", Color.MAGENTA));

        return result;
    }

    public static HelpPage getTips2(){
        HelpPage result = new HelpPage();

        result.messages.add(new Message(-1, 1, "RULES AND TIPS (2)", Color.SKYBLUE));
        result.messages.add(new Message(1, 5, "LONG SNAKE BECOMES", Color.ORANGE));
        result.messages.add(new Message(1, 7, "FASTER AND STRONGER.", Color.ORANGE));
        result.messages.add(new Message(1, 10, "BUT, LONGER SNAKE", Color.GOLD));
        result.messages.add(new Message(1, 12, "WANTS TO EAT MORE OFTEN.", Color.GOLD));
        result.messages.add(new Message(1, 15, "HUNGRY SNAKE LOSES ITS TAIL", Color.PINK));
        result.messages.add(new Message(1, 17, "AND BECOMES LITTLE SHORTER.", Color.PINK));
        result.messages.add(new Message(1, 20, "LONGER WATER SNAKE PUTS", Color.PEACHPUFF));
        result.messages.add(new Message(1, 22, "OUT FIRE MORE EFFICIENTLY.", Color.PEACHPUFF));
        result.messages.add(new Message(1, 25, "ALSO, FIRE SNAKE MELTS", Color.LIGHTBLUE));
        result.messages.add(new Message(1, 27, "EVERY STEP IN THE WATER!", Color.LIGHTBLUE));

        return result;
    }

    public static HelpPage getControls() {
        HelpPage result = new HelpPage();

        result.messages.add(new Message(-1, 7, "CONTROLS", Color.SKYBLUE));
        result.messages.add(new Message(1, 11, "↑ ↓ ← →       :", Color.YELLOW));
        result.messages.add(new Message(1, 13, "HOLD DIRECTION:", Color.YELLOW));
        result.messages.add(new Message(1, 15, "ENTER, R-CLICK:", Color.YELLOW));
        result.messages.add(new Message(1, 17, "ESC,   L-CLICK:", Color.YELLOW));
        result.messages.add(new Message(1, 21, "SPACE (GAME)  :", Color.YELLOW));
        result.messages.add(new Message(1, 23, "SPACE (G.OVER):", Color.YELLOW));
        result.messages.add(new Message(17, 11, "DIRECTION", Color.WHITE));
        result.messages.add(new Message(17, 13, "ACCELERATE", Color.WHITE));
        result.messages.add(new Message(17, 15, "NEXT ELEMENT", Color.WHITE));
        result.messages.add(new Message(17, 17, "PREV ELEMENT", Color.WHITE));
        result.messages.add(new Message(17, 21, "SLEEP", Color.WHITE));
        result.messages.add(new Message(17, 23, "BACK TO MENU", Color.WHITE));

        return result;
    }

    public void draw(SnakeGame game) {
        messages.forEach(Message::draw);
        orbs.forEach(orb -> orb.draw(game));
        nodes.forEach(node -> node.draw(game));
    }
}
