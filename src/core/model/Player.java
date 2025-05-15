package core.model;

import java.util.*;
import java.util.stream.*;

//玩家資料結構：編號、顏色、起始區與目標區
public class Player {
    private final int id;                    // 玩家編號 (1~6)
    private final PlayerColor color;         // 玩家顏色
    private final List<Position> homeArea;   // 起始區域
    private final List<Position> targetArea; // 目標區域

    // 定義六個三角形顏色對應順序 (相對應旋轉方向)
    private static final PlayerColor[] COLORS = {
        PlayerColor.RED, PlayerColor.BLUE, PlayerColor.GREEN,
        PlayerColor.YELLOW, PlayerColor.ORANGE, PlayerColor.PURPLE
    };

    /**
     * 建構子：利用 Board 初始化得到的 validPositions 來計算 homeArea 與 targetArea
     * @param id 玩家編號
     * @param color 玩家顏色
     * @param validPositions 由 BoardInitializer.addBoardToValidPosition() 回傳的 map
     */
    public Player(int id, PlayerColor color, Map<Position, PlayerColor> validPositions) {
        this.id = id;
        this.color = color;
        // 計算起始區域
        this.homeArea = validPositions.entrySet().stream()
            .filter(e -> e.getValue() == color)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
        // 找到對應的目標顏色(旋轉180°相對三角形)
        int idx = IntStream.range(0, COLORS.length)
            .filter(i -> COLORS[i] == color)
            .findFirst().orElseThrow(() -> new IllegalArgumentException("未知的玩家顏色: " + color));
        PlayerColor targetColor = COLORS[(idx + 3) % COLORS.length];
        // 計算目標區域
        this.targetArea = validPositions.entrySet().stream()
            .filter(e -> e.getValue() == targetColor)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    // getters
    public int getId() { return id; }
    public PlayerColor getColor() { return color; }
    public List<Position> getHomeArea() { return Collections.unmodifiableList(homeArea); }
    public List<Position> getTargetArea() { return Collections.unmodifiableList(targetArea); }
}
