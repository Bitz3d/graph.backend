package pl.rafalb.rsockettest.service;


import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import pl.rafalb.rsockettest.domain.Graph;
import pl.rafalb.rsockettest.domain.Node;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

@Service
public class ExpandService {

    private final List<Node.NodeMode> EXCLUDED = List.of(Node.NodeMode.START, Node.NodeMode.FINISH, Node.NodeMode.WALL);

    public Flux<Node> deepExpand(Graph graph) {
        return Mono.just(graph.getRoot())
                .expandDeep(expand(graph))
                .takeUntil(node -> Node.NodeMode.FINISH.equals(node.getMode()))
                .delayElements(Duration.ofMillis(10));

    }

    public Flux<Node> breadthExpand(Graph graph) {
        return Mono.just(graph.getRoot())
                .expand(expand(graph))
                .takeUntil(node -> {
                    if (Node.NodeMode.FINISH.equals(node.getMode())) {
                        backtrack(graph, node);
                        return true;
                    }
                    return false;
                })
                .delayElements(Duration.ofMillis(1));

    }

    private void backtrack(final Graph graph, final Node node) {
        List<Node> path = new ArrayList<>();
        shortestPath(graph, node, path);
        node.setPath(path);
    }

    private void shortestPath(final Graph graph, final Node node, final List<Node> path) {
        if (Node.NodeMode.START.equals(node.getMode())) return;
        List<Node> neighbours = getNeighbours(graph.getGrade(), node);
        Node nearestNode = neighbours
                .stream()
                .filter(Node::getIsVisited)
                .filter(finish -> !Node.NodeMode.FINISH.equals(finish.getMode()))
                .min(Comparator.comparing(Node::getDistance))
                .orElse(null);

        if (nearestNode == null) {
            return;
        }
        if (!EXCLUDED.contains(nearestNode.getMode())) {
            nearestNode.setMode(Node.NodeMode.PATH);
        }
        path.add(nearestNode);
        shortestPath(graph, nearestNode, path);
    }

    private Function<Node, Publisher<? extends Node>> expand(final Graph graph) {
        return n -> Flux.fromIterable(getNeighbours(graph.getGrade(), n))
                .filter(node -> !Node.NodeMode.WALL.equals(node.getMode()))
                .filter(node -> !node.getIsVisited())
                .map(node -> node.setIsVisited(true).setDistance(n.getDistance() + 1))
                .flatMap(g -> nextNode(graph, g));
    }

    private List<Node> getNeighbours(final Node[][] graph, Node processNode) {
        List<Node> neighbors = new ArrayList<>();

        if (!outOfBoundPosition(graph, Math.round(processNode.getPosition().getX()) + 1, Math.round(processNode.getPosition().getY()))) {
            neighbors.add(graph[Math.round(processNode.getPosition().getX()) + 1][Math.round(processNode.getPosition().getY())]);
        }
        if (!outOfBoundPosition(graph, Math.round(processNode.getPosition().getX()) - 1, Math.round(processNode.getPosition().getY()))) {
            neighbors.add(graph[Math.round(processNode.getPosition().getX()) - 1][Math.round(processNode.getPosition().getY())]);
        }
        if (!outOfBoundPosition(graph, Math.round(processNode.getPosition().getX()), Math.round(processNode.getPosition().getY()) + 1)) {
            neighbors.add(graph[Math.round(processNode.getPosition().getX())][Math.round(processNode.getPosition().getY()) + 1]);
        }
        if (!outOfBoundPosition(graph, Math.round(processNode.getPosition().getX()), Math.round(processNode.getPosition().getY()) - 1)) {
            neighbors.add(graph[Math.round(processNode.getPosition().getX())][Math.round(processNode.getPosition().getY()) - 1]);
        }
        return neighbors;
    }

    private boolean outOfBoundPosition(final Node[][] graph,
                                       final int row,
                                       final int column) {
        return !(0 <= row && row < graph.length) || !(0 <= column && column < graph[row].length);
    }


    private Mono<Node> nextNode(Graph graph, final Node nodeRef) {
        return Mono.just(graph.getGrade()[Math.round(nodeRef.getPosition().getX())][Math.round(nodeRef.getPosition().getY())]);
    }


}
