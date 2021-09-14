package pl.rafalb.rsockettest.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class Node {
    private Integer col;
    private Integer row;
    private Boolean isStart;
    private Boolean isFinish;
    private Long distance;
    private Boolean isVisited;
    private Boolean isWall;
    private Boolean isPath;
    private List<Node> path;

    public Node(final Integer col, final Integer row, final Boolean isStart, final Boolean isFinish, final Long distance, final Boolean isVisited, final Boolean isWall) {
        this.col = col;
        this.row = row;
        this.isStart = isStart;
        this.isFinish = isFinish;
        this.distance = distance;
        this.isVisited = isVisited;
        this.isWall = isWall;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return col.equals(node.col) && row.equals(node.row);
    }

    @Override
    public int hashCode() {
        return Objects.hash(col, row);
    }

    @Override
    public String toString() {
        return "Node{" +
                "col=" + col +
                ", row=" + row +
                ", isStart=" + isStart +
                ", isFinish=" + isFinish +
                ", distance=" + distance +
                '}';
    }
}
