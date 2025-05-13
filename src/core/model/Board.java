package core.model;

import org.w3c.dom.ls.LSOutput;

import java.util.*;

public class Board {

    private final Map<Position, Piece> board;  // 每個位置對應的棋子（若有）
    private final Set<Position> validPositions = new HashSet<>();
    public final int triRadius;

    public Board(int triRadius) {
        this.triRadius = triRadius;
        this.board = new HashMap<>();
        validPositions.addAll(BoardInitializer.addBoardToValidPosition(this.triRadius));

    }


    public Set<Position> getValidPositions() {
        return this.validPositions;
    }

    private int getTriRadius(){ return this.triRadius; }

}

