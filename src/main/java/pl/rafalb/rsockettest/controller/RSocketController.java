package pl.rafalb.rsockettest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import pl.rafalb.rsockettest.domain.Graph;
import pl.rafalb.rsockettest.domain.Node;
import pl.rafalb.rsockettest.service.ExpandService;
import reactor.core.publisher.Flux;

@Controller
@RequiredArgsConstructor
public class RSocketController {

    private final ExpandService service;

    @MessageMapping("board.bfs")
    public Flux<Node> breadthSearch(Graph graph) {
        return service.breadthExpand(graph);
    }

    @MessageMapping("board.dfs")
    public Flux<Node> deepSearch(Graph graph) {
        return service.deepExpand(graph);
    }
}
