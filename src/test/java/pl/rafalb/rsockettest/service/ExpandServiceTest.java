package pl.rafalb.rsockettest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.rafalb.rsockettest.domain.Graph;
import pl.rafalb.rsockettest.domain.Node;
import pl.rafalb.rsockettest.domain.position.Coordinate;
import pl.rafalb.rsockettest.utils.GraphCreatorUtil;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class ExpandServiceTest {

    private Graph graph;
    private ExpandService service;

    @BeforeEach
    void setUp() {
        graph = GraphCreatorUtil.fixedGraph();
        service = new ExpandService();
    }

    @Test
    void deepExpand() {
// given
        Flux<Node> deepExpand = service.deepExpand(graph);
        // when
        // then
        StepVerifier.create(deepExpand)
                .expectNext(
                        new Node(new Coordinate(0, 0, 0), 2L, true, Node.NodeMode.START),
                        new Node(new Coordinate(1, 0, 0), 1L, true, Node.NodeMode.GROUND),
                        new Node(new Coordinate(0, 1, 0), 1L, true, Node.NodeMode.GROUND),
                        new Node(new Coordinate(1, 1, 0), 1L, true, Node.NodeMode.FINISH)
                )
                .verifyComplete();
    }

    @Test
    void breadthExpand() {
        // given
        Flux<Node> breadthSearch = service.breadthExpand(graph);
        // when
        // then
        StepVerifier.create(breadthSearch)
                .expectNext(
                        new Node(new Coordinate(0, 0, 0), null, true, Node.NodeMode.START),
                        new Node(new Coordinate(1, 0, 0), null, true, Node.NodeMode.GROUND),
                        new Node(new Coordinate(0, 1, 0), null, true, Node.NodeMode.GROUND),
                        new Node(new Coordinate(0, 0, 0), null, true, Node.NodeMode.GROUND),
                        new Node(new Coordinate(1, 1, 0), null, true, Node.NodeMode.FINISH)
                )
                .verifyComplete();
    }

    @Test
    void checkIfWallIsMissed() {
        // given
        graph.getGrade()[1][0].setMode(Node.NodeMode.WALL);
        Flux<Node> breadthSearch = service.breadthExpand(graph);
        // when
        // then
        StepVerifier.create(breadthSearch)
                .expectNext(
                        new Node(new Coordinate(0, 0, 0), null, true, Node.NodeMode.START),
                        new Node(new Coordinate(0, 1, 0), null, true, Node.NodeMode.GROUND),
                        new Node(new Coordinate(1, 1, 0), null, true, Node.NodeMode.FINISH)
                )
                .verifyComplete();
    }
}
