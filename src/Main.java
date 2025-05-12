import core.model.Board;
import core.model.Position;

import javax.swing.*;
import java.awt.*;


public class Main {
    public static void main(String[] args) {
        System.out.println("Main started.");

        Board board = new Board();
        for(Position pos : board.getValidPositions()){
            System.out.println(pos);
        }


    }
}