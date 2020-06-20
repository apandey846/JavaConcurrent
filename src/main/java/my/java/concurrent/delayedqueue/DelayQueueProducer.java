package my.java.concurrent.delayedqueue;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;

public class DelayQueueProducer implements Runnable {

    private BlockingQueue<DelayObject> queue;
    private final Integer numberOfElementsToProduce;
    private final Integer delayOfEachProducedMessageMilliseconds;

    DelayQueueProducer(BlockingQueue<DelayObject> queue,
                       Integer numberOfElementsToProduce,
                       Integer delayOfEachProducedMessageMilliseconds) {
        this.queue = queue;
        this.numberOfElementsToProduce = numberOfElementsToProduce;
        this.delayOfEachProducedMessageMilliseconds = delayOfEachProducedMessageMilliseconds;
    }

    public void run() {
        int[] arr = {500,6000,300,200,4000};
        for (int i = 0; i < numberOfElementsToProduce; i++) {
            DelayObject object
                    = new DelayObject(UUID.randomUUID().toString(), arr[i]);
            System.out.println("Producer put take: at "+System.currentTimeMillis()+" Object = " + object);
            try {
                queue.put(object);
                Thread.sleep(50);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
}
