package core.model;

import java.util.Objects;

/*
Initialize : Position(q, r)



*/

public class Position {
    private final int q;
    private final int r;

    public Position(int q, int r) {
        this.q = q;
        this.r = r;
    }

    public int getQ() {
        return q;
    }

    public int getR() {
        return r;
    }

    // 透過公式推得 z
    public int getZ() {
        return -q - r;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Position)) return false;
        Position other = (Position) obj;
        return q == other.q && r == other.r;
    }

    @Override
    public int hashCode() {
        return Objects.hash(q, r);
    }

    @Override
    public String toString() {
        return "(" + q + "," + r + "," + ")";
    }
}
