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
    private Long distance;
    private Boolean isVisited;
    private NodeMode mode;
    private List<Node> path;

    public Node(final Integer col,
                final Integer row,
                final Long distance,
                final Boolean isVisited,
                final NodeMode mode) {
        this.col = col;
        this.row = row;
        this.distance = distance;
        this.isVisited = isVisited;
        this.mode = mode;
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
                ", distance=" + distance +
                ", isVisited=" + isVisited +
                ", mode=" + mode +
                '}';
    }

    public enum NodeMode {
        START,
        FINISH,
        WALL,
        GROUND,
        PATH
    }
}
