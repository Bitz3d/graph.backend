package pl.rafalb.rsockettest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.rafalb.rsockettest.domain.Graph;
import pl.rafalb.rsockettest.domain.Node;
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
        Flux<Node> breadthSearch = service.deepExpand(graph);
        // when
        // then
        StepVerifier.create(breadthSearch)
                .expectNext(
                        new Node(0, 0, true, false, null, true, false),
                        new Node(1, 0, true, false, null, true, false),
                        new Node(0, 0, true, false, null, true, false),
                        new Node(1, 1, true, true, null, true, false)
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
                        new Node(0, 0, true, false, null, true, false),
                        new Node(1, 0, true, false, null, true, false),
                        new Node(0, 1, true, false, null, true, false),
                        new Node(0, 0, true, false, null, true, false),
                        new Node(1, 1, true, true, null, true, false)
                )
                .verifyComplete();
    }

    @Test
    void checkIfWallIsMissed() {
        // given
        graph.getGraph()[1][0].setIsWall(true);
        Flux<Node> breadthSearch = service.breadthExpand(graph);
        // when
        // then
        StepVerifier.create(breadthSearch)
                .expectNext(
                        new Node(0, 0, true, false, null, true, false),
                        new Node(0, 1, true, false, null, true, false),
                        new Node(1, 1, true, true, null, true, false)
                )
                .verifyComplete();
    }
}
