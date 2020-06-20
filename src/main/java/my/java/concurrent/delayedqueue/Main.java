package my.java.concurrent.delayedqueue;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] arg) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        BlockingQueue<DelayObject> queue = new DelayQueue<>();
        int numberOfElementsToProduce = 5;
        int delayOfEachProducedMessageMilliseconds = 8000;

        DelayQueueConsumer consumer = new DelayQueueConsumer(
                queue, numberOfElementsToProduce);

        DelayQueueProducer producer = new DelayQueueProducer(
                queue, numberOfElementsToProduce, delayOfEachProducedMessageMilliseconds);

        // when
        executor.submit(producer);
        executor.submit(consumer);

        // then
        executor.awaitTermination(5, TimeUnit.SECONDS);
        executor.shutdown();
    }
}
