package core.model;
import java.util.*;

import core.model.PlayerColor;

class Axial{
    int q, r;
    Axial(int q, int r){
        this.q = q;
        this.r = r;
    }

    Cube toCube(){
        int x = q;
        int z = r;
        int y = -x - z;
        return new Cube(x, y, z);
    }

    // 從 Cube 座標轉換回 Axial
    static Axial fromCube(Cube c) {
        return new Axial(c.x, c.z);
    }
}

// 只有過渡時用到 所有儲存的資料型態都是Axial
class Cube{
    int x, y, z;
    Cube(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    Cube rotate60(){
        return new Cube(-z, -x, -y);
    }

    Cube rotateN60(int times) {
        Cube res = this;
        for (int i = 0; i < times; i++) {
            res = res.rotate60();
        }
        return res;
    }
}


public class BoardInitializer{

    //初始化中間六角形
    private static Map<Position, PlayerColor> initializeHexagon() {
        Map<Position, PlayerColor> results = new HashMap<>();
        int radius = 4;

        // 邏輯迴圈
        for (int q = -radius; q <= radius; q++) {
            for (int r = -radius; r <= radius; r++) {
                if (Math.abs(-q - r) <= radius) {
                    results.put(new Position(q, r), PlayerColor.NONE);
                }
            }
        }
        return results;
    }

    // 生成第一個三角形 | 北邊 紅色
    private static Map<Axial, PlayerColor> generateTriangle() {
        Map<Axial, PlayerColor> triangle = new HashMap<>();
        int R = 4;

        for (int i = 0; i < R; i++) {
            int r = 5 + i;
            int qStart = -4;
            int qEnd = qStart + (R - i);
            for (int q = qStart; q < qEnd; q++) {
                triangle.put(new Axial(q, r), PlayerColor.RED);
            }
        }
        return triangle;
    }

    // 將 baseTriangle 旋轉 6 次並套用對應顏色
    private static Map<Position, PlayerColor> rotateTriangle(Map<Axial, PlayerColor> baseTriangle) {
        Map<Position, PlayerColor> allTriangle = new HashMap<>();

        // 旋轉對應的顏色
        PlayerColor[] colors = {
                PlayerColor.RED, PlayerColor.BLUE, PlayerColor.GREEN,
                PlayerColor.YELLOW, PlayerColor.ORANGE, PlayerColor.PURPLE
        };

        // 原始 RED 三角形
        for (Map.Entry<Axial, PlayerColor> entry : baseTriangle.entrySet()) {
            allTriangle.put(new Position(entry.getKey().q, entry.getKey().r), entry.getValue());
        }

        // 旋轉其餘 5 個方向並加入不同顏色
        for (int i = 1; i < 6; i++) {
            for (Axial a : baseTriangle.keySet()) {
                Cube rotated = a.toCube().rotateN60(i);
                Axial newAxial = Axial.fromCube(rotated);
                allTriangle.put(new Position(newAxial.q, newAxial.r), colors[i]);
            }
        }

        return allTriangle;
    }

    // 將所有 Position 與顏色整理為 Map 給 Board 使用
    public static Map<Position, PlayerColor> addBoardToValidPosition() {
        Map<Axial, PlayerColor> baseTri = generateTriangle();
        Map<Position, PlayerColor> triangles = rotateTriangle(baseTri);
        Map<Position, PlayerColor> hexagon = initializeHexagon();

        Map<Position, PlayerColor> result = new HashMap<>();
        result.putAll(triangles);
        result.putAll(hexagon); // hexagon 會保留 NONE 顏色，避免覆蓋三角形
        return result;
    }




}