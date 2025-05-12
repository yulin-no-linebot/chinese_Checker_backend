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

// 只有過度時用到 所有儲存的資料型態都是Axial
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


public class HexagonTrianglePart{

    // 生成第一個三角形
    // TODO: 要重寫, 這是ChatGPT生的, 好像並不是從原本的六角形去延伸的, 不過下面的rotate沒有問題
    public static List<Axial> generateTriangle(int R) {

        List<Axial> topTriangle = new ArrayList<>();

        for (int dq = -R + 1; dq <= R - 1; dq++) {
            for (int dr = -R; dr < 0; dr++) {
                int q = dq;
                int r = dr - Math.abs(dq); // 讓邊緣內縮成三角形形狀
                if (Math.abs(-q - r) > R) {
                    topTriangle.add(new Axial(q, r));
                }
            }
        }
        return topTriangle;
    }

    public static List<Axial> rotateTriangle(List<Axial> baseTriangle){

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

    public static void addTriangleToValidPosition(List<Position> list){
        List<Axial> baseTri = generateTriangle(Board.radius);
        List<Axial> allTri = rotateTriangle(baseTri);

        Set<Position> uniquePos = new HashSet<>();
        for(Axial a: allTri){
            uniquePos.add(new Position(a.q, a.r));
        }
        list.addAll(uniquePos);
    }
}