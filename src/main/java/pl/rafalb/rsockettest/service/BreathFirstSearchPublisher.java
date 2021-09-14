package pl.rafalb.rsockettest.service;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class BreathFirstSearchPublisher<Graph> implements Publisher<Graph> {

    private final Graph[][] graph;

    public BreathFirstSearchPublisher(final Graph[][] graph) {
        this.graph = graph;
    }


    @Override
    public void subscribe(final Subscriber<? super Graph> subscriber) {
        subscriber.onSubscribe(new Subscription() {
            @Override
            public void request(final long l) {

            }

            @Override
            public void cancel() {

            }
        });

        for (int i = 0; i < 3; i++) {
            subscriber.onNext(graph[0][i]);
        }
        subscriber.onComplete();
    }
}
