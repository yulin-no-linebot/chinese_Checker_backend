package core.model;

import org.w3c.dom.ls.LSOutput;

import java.util.*;

public class Board {

    private final Map<Position, Piece> board;  // 每個位置對應的棋子（若有）
    private final List<Position> validPositions = new ArrayList<>();
    public static final int radius = 4;

    public Board() {
        this.board = new HashMap<>();
        validPositions.addAll(initialize_hexagon());
    }

    //初始化中間六角形
    private List<Position> initialize_hexagon() {
        List<Position> results = new ArrayList<>();
        // 邏輯迴圈
        for(int q = -radius ; q <= radius; q++){
            for(int r = -radius; r <= radius; r++){
                if(Math.abs(-q-r) <= radius){
                    Position pos = new Position(q, r);
                    results.add(pos);
                }
            }
        }
        return results;
    }

    private List<Position> initialize_triangle() {

        return List.of();
    }






    public List<Position> getValidPositions(){
        return this.validPositions;
    }


}

