package core.model;
import java.util.*;

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
    private static Set<Position> initialize_hexagon() {
        Set<Position> results = new HashSet<>();
        int radius = 4;

        // 邏輯迴圈
        for (int q = -radius; q <= radius; q++) {
            for (int r = -radius; r <= radius; r++) {
                if (Math.abs(-q - r) <= radius) {
                    Position pos = new Position(q, r);
                    results.add(pos);
                }
            }
        }
        return results;
    }

    // 生成第一個三角形 | 北邊
    // 確定對了 不用改 如果出錯那可能是rotate壞掉
    // int R目前沒用到 如果固定10棋的話以後都用不到
    private static List<Axial> generateTriangle(int R) {
        List<Axial> triangle = new ArrayList<>();

        // 三角形向北（r值增加）擴展
        for (int i = 0; i < R; i++) {
            int r = 5 + i;                  // 固定起始r = 5，可根據需求參數化
            int qStart = -4;               // 最左邊 q 固定為 -4
            int qEnd = qStart + (R - i);   // 每層 q 長度減少
            for (int q = qStart; q < qEnd; q++) {
                triangle.add(new Axial(q, r));
            }
        }
        return triangle;
    }

    // 旋轉北邊三角形 取得六個三角形座標
    private static List<Axial> rotateTriangle(List<Axial> baseTriangle){

        List<Axial> allTriangle = new ArrayList<>();

        allTriangle.addAll(baseTriangle);

        for(int i = 1; i <= 6; i++){
            for (Axial a: baseTriangle){
                Cube rotated = a.toCube().rotateN60(i);
                allTriangle.add(Axial.fromCube(rotated));
            }
        }
        return allTriangle;
    }

    public static Set<Position> addBoardToValidPosition(int triRadius){

        List<Axial> baseTri = generateTriangle(triRadius);
        List<Axial> allTri = rotateTriangle(baseTri);

        Set<Position> uniquePos = new HashSet<>();
        for(Axial a: allTri){
            uniquePos.add(new Position(a.q, a.r));
        }

        Set<Position> hexagon = initialize_hexagon();
        for(Position pos: hexagon){
            uniquePos.add(pos);
        }

        return uniquePos;
    }




}