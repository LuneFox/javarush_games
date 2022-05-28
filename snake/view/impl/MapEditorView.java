package com.javarush.games.snake.view.impl;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.SnakeGame;
import com.javarush.games.snake.model.Map;
import com.javarush.games.snake.model.Node;
import com.javarush.games.snake.view.Message;
import com.javarush.games.snake.view.View;

public class MapEditorView extends View {
    private static MapEditorView instance;
    private int brush;

    public static MapEditorView getInstance() {
        if (instance == null) instance = new MapEditorView();
        return instance;
    }

    @Override
    public void update() {
        Node node = new Node(1, 1, game, brush);
        game.getMap().setLayoutNode(1, 1, node.getTerrain());
        GameFieldView.getInstance().drawMap();
        Message.print(3, 1, node.getTerrain().name(), Color.WHITE);
    }

    public void brushNext() {
        if (brush < 9) {
            brush++;
        } else {
            brush = 0;
        }
    }

    public void brushPrevious() {
        if (brush > 0) {
            brush--;
        } else {
            brush = 9;
        }
    }

    public void drawTerrain(int x, int y) {
        Node node = new Node(x, y, game, brush);
        game.getMap().setLayoutNode(node.x, node.y, node.getTerrain());
    }

    public void copyTerrain(int x, int y) {
        brush = game.getMap().getLayoutNode(x, y).getTerrain().ordinal();
    }

    public void printTerrain() {
        Map map = game.getMap();

        System.out.println("new int[][]{");

        for (int y = 0; y < SnakeGame.SIZE; y++) {
            for (int x = 0; x < SnakeGame.SIZE; x++) {
                int number = (y < 4 ? 9 : map.getLayoutNode(x, y).getTerrain().ordinal());

                if (x == 0) {
                    System.out.print("{");
                }

                if (x < SnakeGame.SIZE - 1) {
                    System.out.print(number + ", ");
                }

                if (x == SnakeGame.SIZE - 1) {
                    System.out.print(number);
                    System.out.println("},");
                }

            }
        }

        System.out.println("});");
    }

    public void printCoordinate(int x, int y) {
        System.out.println(x + "," + y);
    }
}
