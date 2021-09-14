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

    public Flux<Node> deepExpand(Graph graph) {
        return Mono.just(graph.getRoot())
                .expandDeep(expand(graph))
                .takeUntil(Node::getIsFinish)
                .delayElements(Duration.ofMillis(10))
                .log();

    }

    public Flux<Node> breadthExpand(Graph graph) {
        return Mono.just(graph.getRoot())
                .expand(expand(graph))
                .takeUntil(node -> {
                    if (node.getIsFinish()) {
                        backtrack(graph, node);
                        return true;
                    }
                    return false;
                })
                .delayElements(Duration.ofMillis(10))
                .log();

    }

    private void backtrack(final Graph graph, final Node node) {
        List<Node> path = new ArrayList<>();
        shortestPath(graph, node, path);
        node.setPath(path);
    }

    private void shortestPath(final Graph graph, final Node node, final List<Node> path) {
        if (node.getIsStart()) return;
        List<Node> neighbours = getNeighbours(graph.getGraph(), node);
        Node nearestNode = neighbours
                .stream()
                .filter(Node::getIsVisited)
                .min(Comparator.comparing(Node::getDistance))
                .orElseThrow();
        nearestNode.setIsPath(true);
        path.add(nearestNode);
        shortestPath(graph, nearestNode, path);
    }

    private Function<Node, Publisher<? extends Node>> expand(final Graph graph) {
        return n -> Flux.fromIterable(getNeighbours(graph.getGraph(), n))
                .filter(h -> !h.getIsWall())
                .filter(j -> !j.getIsVisited())
                .map(node -> node.setIsVisited(true).setDistance(n.getDistance() + 1))
                .flatMap(g -> nextNode(graph, g));
    }

    private List<Node> getNeighbours(final Node[][] graph, Node processNode) {
        List<Node> neighbors = new ArrayList<>();

        if (!outOfBoundPosition(graph, processNode.getCol() + 1, processNode.getRow())) {
            neighbors.add(graph[processNode.getCol() + 1][processNode.getRow()]);
        }
        if (!outOfBoundPosition(graph, processNode.getCol() - 1, processNode.getRow())) {
            neighbors.add(graph[processNode.getCol() - 1][processNode.getRow()]);
        }
        if (!outOfBoundPosition(graph, processNode.getCol(), processNode.getRow() + 1)) {
            neighbors.add(graph[processNode.getCol()][processNode.getRow() + 1]);
        }
        if (!outOfBoundPosition(graph, processNode.getCol(), processNode.getRow() - 1)) {
            neighbors.add(graph[processNode.getCol()][processNode.getRow() - 1]);
        }
        return neighbors;
    }

    private boolean outOfBoundPosition(final Node[][] graph,
                                       final int row,
                                       final int column) {
        return !(0 <= row && row < graph.length) || !(0 <= column && column < graph[row].length);
    }


    private Mono<Node> nextNode(Graph graph, final Node nodeRef) {
        return Mono.just(graph.getGraph()[nodeRef.getCol()][nodeRef.getRow()]);
    }


}
