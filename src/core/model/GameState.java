package core.model;

import java.awt.Color;
import java.util.*;

//管理遊戲整體狀態：棋盤、玩家順序、勝利判斷等
public class GameState {
    private final Board board;              // 棋盤
    private final List<Player> players;     // 玩家列表
    private int currentPlayerIndex;         // 現在輪到哪位玩家

    //建構子：傳入玩家列表 (至少 2 人)，並初始化棋盤與棋子
    public GameState(List<Player> players) {
        if (players == null || players.size() < 2) {
            throw new IllegalArgumentException("需要至少 2 位玩家");
        }
        this.players = new ArrayList<>(players);
        this.board = new Board();
        this.currentPlayerIndex = 0;
        initPieces();
    }

    //將每位玩家的棋子擺放在其 homeArea
    private void initPieces() {
        Map<Position, Piece> map = board.getBoardMap();
        for (Player p : players) {
            Color awtColor = toAwtColor(p.getColor());
            for (Position pos : p.getHomeArea()) {
                map.put(pos, new Piece(awtColor));
            }
        }
    }

    //取得當前玩家
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    //跳至下一位玩家的回合 (自動略過已完成的玩家)
    public void nextTurn() {
        int size = players.size();
        do {
            currentPlayerIndex = (currentPlayerIndex + 1) % size;
        } while (isPlayerFinished(players.get(currentPlayerIndex)));
    }

    //判斷是否有玩家勝利 (至少一位完成)
    public boolean isWin() {
        return players.stream().anyMatch(this::isPlayerFinished);
    }

    //判斷是否平手 (兩位以上同時完成)
    public boolean isWinWin() {
        return players.stream().filter(this::isPlayerFinished).count() > 1;
    }

    //檢查特定玩家是否完成：所有 targetArea 上都有該玩家的棋子
    private boolean isPlayerFinished(Player p) {
        Map<Position, Piece> map = board.getBoardMap();
        Color awtColor = toAwtColor(p.getColor());
        for (Position pos : p.getTargetArea()) {
            Piece piece = map.get(pos);
            if (piece == null || !piece.getColor().equals(awtColor)) {
                return false;
            }
        }
        return true;
    }

    //將 PlayerColor 轉為 java.awt.Color
    private Color toAwtColor(PlayerColor pc) {
        switch (pc) {
            case RED:    return Color.RED;
            case BLUE:   return Color.BLUE;
            case GREEN:  return Color.GREEN;
            case YELLOW: return Color.YELLOW;
            case ORANGE: return Color.ORANGE;
            case PURPLE: return new Color(128, 0, 128);
            default:     return Color.GRAY;
        }
    }

/*
    外部讀取棋盤狀態
    public Map<Position, Piece> getBoardState() {
        return Collections.unmodifiableMap(board.getBoardMap());
    }
*/
}
