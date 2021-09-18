package pl.rafalb.rsockettest.service;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import pl.rafalb.rsockettest.domain.Graph;
import pl.rafalb.rsockettest.domain.Node;
import pl.rafalb.rsockettest.utils.GraphCreatorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class BreadthFirstSearchPublisherTest {

    @Test
    public void everyStepShouldBeExecutedInSomeOrder() throws InterruptedException {
        // Given
        CountDownLatch latch = new CountDownLatch(1);
        Graph graph = GraphCreatorUtil.randomGraph();
        List<String> observableSignals = new ArrayList<>();
        BreathFirstSearchPublisher<Node[][]> publisher = new BreathFirstSearchPublisher(graph.getGrade());
        publisher.subscribe(new Subscriber() {
            @Override
            public void onSubscribe(final Subscription subscription) {
                observableSignals.add("onSubscribe()");
                subscription.request(25);
            }

            @Override
            public void onNext(final Object o) {
                observableSignals.add("onNext()");
            }

            @Override
            public void onError(final Throwable throwable) {

            }

            @Override
            public void onComplete() {
                observableSignals.add("onComplete()");
                latch.countDown();
            }


        });

        assertThat(latch.await(1, TimeUnit.MILLISECONDS)).isTrue();
        assertThat(observableSignals).containsExactly(
                "onSubscribe()",
                "onNext()",
                "onNext()",
                "onNext()",
                "onComplete()"
        );
    }
}
