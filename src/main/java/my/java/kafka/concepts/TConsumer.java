package my.java.kafka.concepts;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TConsumer {

    public static void main(String[] args) {
        Runnable consumer1 = ()->runConsumer();
        Runnable consumer2 = ()->runConsumer();
        Runnable consumer3 = ()->runConsumer();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(consumer1);
        executorService.submit(consumer2);
        executorService.submit(consumer3);
    }

    public static void runConsumer(){
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(getConsumerProperties("consumer-group"));
        kafkaConsumer.subscribe(Arrays.asList("TRADING-INFO"));
        while(true) {
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(1000);
            consumerRecords.forEach(e -> {
                System.out.println(Thread.currentThread().getName()+" : "+e.value());
            });
        }
    }

    private static Properties getConsumerProperties(String consumerGroupId) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", consumerGroupId);
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        return props;
    }
}
