package core.model;

import org.w3c.dom.ls.LSOutput;

import java.util.*;

public class Board {

    private final Map<Position, Piece> board;  // 每個位置對應的棋子（若有）
    private final Map<Position, PlayerColor> validPositions;

    public Board() {
        this.board = new HashMap<>();
        this.validPositions = BoardInitializer.addBoardToValidPosition();

    }


    public Map<Position, PlayerColor> getValidPositions() {
        return this.validPositions;
    }


}

