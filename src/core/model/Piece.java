package core.model;

import java.awt.Color;

public class Piece{

    private final Color color;

    public Piece(Color color){
        this.color = color;
    }

    // getter
    public Color getColor(){
        return this.color;
    }

    // 棋子移動
//    public void moveTo(Position newPosition){
//
//    }

    // 判斷移動是否合法
//    public boolean checkMove(Position newPosition){
//
//
//        return false;
//    }

}