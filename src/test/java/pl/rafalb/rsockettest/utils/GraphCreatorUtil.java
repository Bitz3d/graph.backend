package pl.rafalb.rsockettest.utils;

import pl.rafalb.rsockettest.domain.Graph;
import pl.rafalb.rsockettest.domain.Node;

import java.util.concurrent.ThreadLocalRandom;

public class GraphCreatorUtil {
    public static Graph randomGraph() {
        int columnLength = 5;
        int nodeLength = 5;
        Node[][] graph = new Node[columnLength][nodeLength];
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                graph[i][j] = new Node()
                        .setCol(i)
                        .setRow(j)
                        .setIsFinish(false)
                        .setIsStart(false)
                        .setIsWall(false)
                        .setIsVisited(false);
            }
        }
        Node startPoint = createStartPoint(columnLength, nodeLength, graph);
        createFinishPoint(columnLength, nodeLength, graph);
        return new Graph().setGraph(graph).setRoot(startPoint);
    }

    public static Graph fixedGraph() {
        int columnLength = 2;
        int nodeLength = 2;
        Node[][] graph = new Node[columnLength][nodeLength];
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                graph[i][j] = new Node()
                        .setCol(i)
                        .setRow(j)
                        .setIsFinish(false)
                        .setIsStart(false)
                        .setIsWall(false)
                        .setIsVisited(false);
            }
        }
        Node startPoint = graph[0][0];
        startPoint.setIsStart(true);
        graph[1][1].setIsFinish(true);
        return new Graph().setGraph(graph).setRoot(startPoint);
    }

    private static Node createStartPoint(final int columnLength, final int nodeLength, final Node[][] nodes) {
        int randomColumn = ThreadLocalRandom.current().nextInt(0, columnLength);
        int randomNode = ThreadLocalRandom.current().nextInt(0, nodeLength);
        nodes[randomColumn][randomNode].setIsStart(true);
        return nodes[randomColumn][randomNode];
    }

    private static void createFinishPoint(final int columnLength, final int nodeLength, final Node[][] nodes) {
        int randomColumn = ThreadLocalRandom.current().nextInt(0, columnLength);
        int randomNode = ThreadLocalRandom.current().nextInt(0, nodeLength);
        if (nodes[randomColumn][randomNode].getIsStart()) {
            createFinishPoint(columnLength, nodeLength, nodes);
        }
        nodes[randomColumn][randomNode].setIsFinish(true);
    }
}