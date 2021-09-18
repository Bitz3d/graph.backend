package pl.rafalb.rsockettest.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.rafalb.rsockettest.domain.position.Coordinate;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class Node {


    private Coordinate position;
    private Long distance;
    private Boolean isVisited;
    private NodeMode mode;
    private List<Node> path;

    public Node(final Coordinate position,
                final Long distance,
                final Boolean isVisited,
                final NodeMode mode) {
        this.position = position;
        this.distance = distance;
        this.isVisited = isVisited;
        this.mode = mode;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return position.equals(node.position) && Objects.equals(distance, node.distance) && Objects.equals(isVisited, node.isVisited) && mode == node.mode && Objects.equals(path, node.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    @Override
    public String toString() {
        return "Node{" +
                "position=" + position +
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
