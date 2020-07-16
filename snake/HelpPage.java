package com.javarush.games.snake;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.enums.Element;

import java.util.ArrayList;
import java.util.List;

public class HelpPage {
    List<Message> messages;
    List<Orb> orbs;

    private HelpPage() {
        this.messages = new ArrayList<>();
        this.orbs = new ArrayList<>();
    }

    static HelpPage getGoals() {
        HelpPage result = new HelpPage();
        result.messages.add(new Message(-1, 5, "HELP", Color.SKYBLUE));
        result.messages.add(new Message(-1, 9, "COLLECT TO WIN:", Color.YELLOW));
        result.messages.add(new Message(3, 11, "ORB OF WATER", Color.LIGHTBLUE));
        result.messages.add(new Message(3, 13, "ORB OF FIRE", Color.RED));
        result.messages.add(new Message(3, 15, "ORB OF EARTH", Color.ORANGE));
        result.messages.add(new Message(3, 17, "ORB OF AIR", Color.AZURE));
        result.messages.add(new Message(3, 19, "ORB OF POWER", Color.PINK));
        result.messages.add(new Message(-1, 21, "COLLECT TO GROW:", Color.YELLOW));
        result.messages.add(new Message(3, 23, "ORB OF WISDOM", Color.MEDIUMPURPLE));
        result.orbs.add(new Orb(1, 11, Element.WATER));
        result.orbs.add(new Orb(1, 13, Element.FIRE));
        result.orbs.add(new Orb(1, 15, Element.EARTH));
        result.orbs.add(new Orb(1, 17, Element.AIR));
        result.orbs.add(new Orb(1, 19, Element.ALMIGHTY));
        result.orbs.add(new Orb(1, 23, Element.NEUTRAL));
        return result;
    }

    static HelpPage getSnakeAbilities() {
        HelpPage result = new HelpPage();
        result.messages.add(new Message(-1, 5, "SNAKE ABILITIES", Color.SKYBLUE));
        return result;
    }

    static HelpPage getControls() {
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
        for (Orb orb : orbs) {
            orb.draw(game);
        }
    }
}
