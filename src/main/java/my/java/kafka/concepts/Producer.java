package my.java.kafka.concepts;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * https://anirudhbhatnagar.com/2016/08/22/achieving-order-guarnetee-in-kafka-with-partitioning/
 */
public class Producer {

    public static void main(String[] args) {
        final String TOPIC = "TRADING-INFO";
        KafkaProducer kafkaProducer = new KafkaProducer(getProducerProperties());

        Runnable task1 = () -> sendTradeToTopic(TOPIC, kafkaProducer, "ABCD", 1, 5);
        Runnable task2 = () -> sendTradeToTopic(TOPIC, kafkaProducer, "PQ1234@1211111111111", 6, 10);
        Runnable task3 = () -> sendTradeToTopic(TOPIC, kafkaProducer, "ZX12345OOO", 11, 15);

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(task1);
        executorService.submit(task2);
        executorService.submit(task3);

        executorService.shutdown();

    }

    private static void sendTradeToTopic(String topic, KafkaProducer kafkaProducer, String securityId, int idStart, int idEnd) {
        for (int i = idStart; i <= idEnd; i++) {
            //Trade trade = Trade.builder().id(i).securityId(securityId).value("abcd").build();
            Trade trade = new Trade("I = "+i, securityId, "SHT", "abcd");
            try {
                String s = trade.toString();
                kafkaProducer.send(new ProducerRecord(topic, trade.getSecurityId(), s));
                System.out.println("Sending to " + topic + "msg : " + s);
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static Properties getProducerProperties() {
        Properties props = new Properties();
        String KAFKA_SERVER_IP = "localhost:9092";
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER_IP);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return props;
    }

}
