import core.model.Board;
import core.model.Position;
import core.model.PlayerColor;

import javax.swing.*;
import java.awt.*;
import java.util.Map;


public class Main {
    public static void main(String[] args) {
        System.out.println("Main started.");

        Board board = new Board();
        for(Map.Entry<Position, PlayerColor> entry : board.getValidPositions().entrySet()){
            System.out.println("Position: " + entry.getKey() + ", Color: " + entry.getValue());
        }


    }
}