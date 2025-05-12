package core.logicUtils;

import java.util.HashSet;
import java.util.Set;
import core.model.Position;

public class PositionFactory {

    // 產生完整六角形棋盤（半徑 radius = 4）
    public static Set<Position> generate10PlayerBoard() {
        Set<Position> positions = new HashSet<>();

        int radius = 4; // 半徑（中心+外層3層）

        for (int q = -radius; q <= radius; q++) {
            int r1 = Math.max(-radius, -q - radius);
            int r2 = Math.min(radius, -q + radius);
            for (int r = r1; r <= r2; r++) {
                positions.add(new Position(q, r));
            }
        }

        return positions;
    }
}
