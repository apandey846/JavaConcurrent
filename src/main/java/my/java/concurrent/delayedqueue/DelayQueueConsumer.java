package my.java.concurrent.delayedqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class DelayQueueConsumer implements Runnable {
    private BlockingQueue<DelayObject> queue;
    private final Integer numberOfElementsToTake;
    final AtomicInteger numberOfConsumedElements = new AtomicInteger();

    DelayQueueConsumer(BlockingQueue<DelayObject> queue, Integer numberOfElementsToTake) {
        this.queue = queue;
        this.numberOfElementsToTake = numberOfElementsToTake;
    }


    public void run() {
        for (int i = 0; i < numberOfElementsToTake; i++) {
            try {
                DelayObject object = queue.take();
                numberOfConsumedElements.incrementAndGet();
                System.out.println("Consumer take: at "+System.currentTimeMillis()+" Object = " + object);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
