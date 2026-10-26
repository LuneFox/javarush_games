package com.javarush.games.snake.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.SnakeGame;
import com.javarush.games.snake.model.Strings;
import com.javarush.games.snake.model.terrain.Terrain;
import com.javarush.games.snake.model.orbs.Orb;
import com.javarush.games.snake.model.enums.Element;

import java.util.ArrayList;
import java.util.List;

public class HelpPage {
    List<Message> messages;
    List<Orb> orbs;
    List<Terrain> terrains;

    private HelpPage() {
        this.messages = new ArrayList<>();
        this.orbs = new ArrayList<>();
        this.terrains = new ArrayList<>();
    }

    public static HelpPage getGoals() {
        HelpPage result = new HelpPage();

        result.messages.add(new Message(-1, 1, Strings.HELP_GOALS_TITLE.get(), Color.SKYBLUE));
        result.messages.add(new Message(-1, 5, Strings.HELP_GOALS_WIN.get(), Color.YELLOW));
        result.messages.add(new Message(3, 7, Strings.HELP_ORB_WATER.get(), Color.LIGHTBLUE));
        result.messages.add(new Message(3, 9, Strings.HELP_ORB_FIRE.get(), Color.RED));
        result.messages.add(new Message(3, 11, Strings.HELP_ORB_EARTH.get(), Color.ORANGE));
        result.messages.add(new Message(3, 13, Strings.HELP_ORB_AIR.get(), Color.CYAN));
        result.messages.add(new Message(3, 15, Strings.HELP_ORB_POWER.get(), Color.MAGENTA));
        result.messages.add(new Message(-1, 17, Strings.HELP_GOALS_GROW.get(), Color.YELLOW));
        result.messages.add(new Message(3, 19, Strings.HELP_ORB_WISDOM.get(), Color.MEDIUMPURPLE));

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

        result.messages.add(new Message(-1, 1, Strings.HELP_ABILITIES_TITLE.get(), Color.SKYBLUE));
        result.messages.add(new Message(1, 5, Strings.HELP_NEUTRAL_NAME.get(), Color.LIGHTGREEN));
        result.messages.add(new Message(3, 7, Strings.HELP_NEUTRAL_DESC.get(), Color.WHITE));
        result.messages.add(new Message(1, 9, Strings.HELP_WATER_NAME.get(), Color.LIGHTBLUE));
        result.messages.add(new Message(3, 11, Strings.HELP_WATER_DESC.get(), Color.WHITE));
        result.messages.add(new Message(1, 13, Strings.HELP_FIRE_NAME.get(), Color.RED));
        result.messages.add(new Message(3, 15, Strings.HELP_FIRE_DESC.get(), Color.WHITE));
        result.messages.add(new Message(1, 17, Strings.HELP_EARTH_NAME.get(), Color.ORANGE));
        result.messages.add(new Message(3, 19, Strings.HELP_EARTH_DESC.get(), Color.WHITE));
        result.messages.add(new Message(1, 21, Strings.HELP_AIR_NAME.get(), Color.CYAN));
        result.messages.add(new Message(3, 23, Strings.HELP_AIR_DESC.get(), Color.WHITE));
        result.messages.add(new Message(1, 25, Strings.HELP_ALMIGHTY_NAME.get(), Color.MAGENTA));
        result.messages.add(new Message(3, 27, Strings.HELP_ALMIGHTY_DESC.get(), Color.WHITE));

        return result;
    }

    public static HelpPage getTypesOfTerrain() {
        HelpPage result = new HelpPage();

        result.messages.add(new Message(-1, 1, Strings.HELP_TERRAIN_TITLE.get(), Color.SKYBLUE));
        result.messages.add(new Message(3, 7, Strings.TERRAIN_FIELD.get(), Color.WHITE));
        result.messages.add(new Message(3, 9, Strings.TERRAIN_WATER.get(), Color.WHITE));
        result.messages.add(new Message(3, 11, Strings.TERRAIN_SAND.get(), Color.WHITE));
        result.messages.add(new Message(3, 13, Strings.TERRAIN_FOREST.get(), Color.WHITE));
        result.messages.add(new Message(3, 15, Strings.TERRAIN_MOUNTAIN.get(), Color.WHITE));
        result.messages.add(new Message(3, 17, Strings.TERRAIN_WOOD.get(), Color.WHITE));
        result.messages.add(new Message(3, 19, Strings.TERRAIN_FIRE.get(), Color.WHITE));
        result.messages.add(new Message(3, 21, Strings.TERRAIN_WALL.get(), Color.WHITE));
        result.messages.add(new Message(3, 23, Strings.TERRAIN_WORMHOLE.get(), Color.WHITE));

        result.terrains.add(Terrain.create(1, 7, Terrain.FIELD));
        result.terrains.add(Terrain.create(1, 9, Terrain.WATER));
        result.terrains.add(Terrain.create(1, 11, Terrain.SAND));
        result.terrains.add(Terrain.create(1, 13, Terrain.FOREST));
        result.terrains.add(Terrain.create(1, 15, Terrain.MOUNTAIN));
        result.terrains.add(Terrain.create(1, 17, Terrain.WOOD));
        result.terrains.add(Terrain.create(1, 19, Terrain.FIRE));
        result.terrains.add(Terrain.create(1, 21, Terrain.WALL));
        result.terrains.add(Terrain.create(1, 23, Terrain.WORMHOLE));

        return result;
    }

    public static HelpPage getTips1() {
        HelpPage result = new HelpPage();

        result.messages.add(new Message(-1, 1, Strings.HELP_TIPS1_TITLE.get(), Color.SKYBLUE));
        result.messages.add(new Message(1, 5, Strings.HELP_TIPS1_COLLECT.get(), Color.WHITE));
        result.messages.add(new Message(1, 7, Strings.HELP_TIPS1_FAST.get(), Color.WHITE));
        result.messages.add(new Message(1, 10, Strings.HELP_TIPS1_EAT.get(), Color.YELLOW));
        result.messages.add(new Message(1, 12, Strings.HELP_TIPS1_USE.get(), Color.YELLOW));
        result.messages.add(new Message(1, 15, Strings.HELP_TIPS1_PICK.get(), Color.LAWNGREEN));
        result.messages.add(new Message(1, 17, Strings.HELP_TIPS1_SWITCH.get(), Color.LAWNGREEN));
        result.messages.add(new Message(1, 20, Strings.HELP_TIPS1_NOSWAP.get(), Color.CYAN));
        result.messages.add(new Message(1, 22, Strings.HELP_TIPS1_WHILE.get(), Color.CYAN));
        result.messages.add(new Message(1, 25, Strings.HELP_TIPS1_TURN.get(), Color.MAGENTA));
        result.messages.add(new Message(1, 27, Strings.HELP_TIPS1_SLEEP.get(), Color.MAGENTA));

        return result;
    }

    public static HelpPage getTips2() {
        HelpPage result = new HelpPage();

        result.messages.add(new Message(-1, 1, Strings.HELP_TIPS2_TITLE.get(), Color.SKYBLUE));
        result.messages.add(new Message(1, 5, Strings.HELP_TIPS2_LONG.get(), Color.ORANGE));
        result.messages.add(new Message(1, 7, Strings.HELP_TIPS2_FASTER.get(), Color.ORANGE));
        result.messages.add(new Message(1, 10, Strings.HELP_TIPS2_BUT.get(), Color.GOLD));
        result.messages.add(new Message(1, 12, Strings.HELP_TIPS2_HUNGRY.get(), Color.GOLD));
        result.messages.add(new Message(1, 15, Strings.HELP_TIPS2_TAIL.get(), Color.PINK));
        result.messages.add(new Message(1, 17, Strings.HELP_TIPS2_SHORTER.get(), Color.PINK));
        result.messages.add(new Message(1, 20, Strings.HELP_TIPS2_WATER.get(), Color.PEACHPUFF));
        result.messages.add(new Message(1, 22, Strings.HELP_TIPS2_EFFICIENT.get(), Color.PEACHPUFF));
        result.messages.add(new Message(1, 25, Strings.HELP_TIPS2_FIRE.get(), Color.LIGHTBLUE));
        result.messages.add(new Message(1, 27, Strings.HELP_TIPS2_MELT.get(), Color.LIGHTBLUE));

        return result;
    }

    public static HelpPage getControls() {
        HelpPage result = new HelpPage();

        result.messages.add(new Message(-1, 7, Strings.HELP_CONTROLS_TITLE.get(), Color.SKYBLUE));
        result.messages.add(new Message(1, 11, Strings.HELP_CONTROL_ARROWS.get(), Color.YELLOW));
        result.messages.add(new Message(1, 13, Strings.HELP_CONTROL_HOLD.get(), Color.YELLOW));
        result.messages.add(new Message(1, 15, Strings.HELP_CONTROL_ENTER.get(), Color.YELLOW));
        result.messages.add(new Message(1, 17, Strings.HELP_CONTROL_ESC.get(), Color.YELLOW));
        result.messages.add(new Message(1, 21, Strings.HELP_CONTROL_SPACE_GAME.get(), Color.YELLOW));
        result.messages.add(new Message(1, 23, Strings.HELP_CONTROL_SPACE_OVER.get(), Color.YELLOW));
        result.messages.add(new Message(17, 11, Strings.HELP_CONTROL_DIRECTION.get(), Color.WHITE));
        result.messages.add(new Message(17, 13, Strings.HELP_CONTROL_ACCELERATE.get(), Color.WHITE));
        result.messages.add(new Message(17, 15, Strings.HELP_CONTROL_NEXT.get(), Color.WHITE));
        result.messages.add(new Message(17, 17, Strings.HELP_CONTROL_PREV.get(), Color.WHITE));
        result.messages.add(new Message(17, 21, Strings.HELP_CONTROL_SLEEP.get(), Color.WHITE));
        result.messages.add(new Message(17, 23, Strings.HELP_CONTROL_BACK.get(), Color.WHITE));

        return result;
    }

    public void draw(SnakeGame game) {
        messages.forEach(Message::draw);
        orbs.forEach(orb -> orb.draw(game));
        terrains.forEach(node -> node.draw(game));
    }
}
