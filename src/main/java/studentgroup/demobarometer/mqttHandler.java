//package studentgroup.demobarometer;
////import org.eclipse.paho.client.mqttv3.MqttClient;
////import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
////import org.eclipse.paho.client.mqttv3.MqttException;
////import org.eclipse.paho.client.mqttv3.MqttMessage;
////import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
//import com.hivemq.client.mqtt.MqttClient;
//import com.hivemq.client.mqtt.datatypes.MqttQos;
//import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
//
//import java.util.UUID;
//
//public class mqttHandler {
//
//    private static final Mqtt3AsyncClient client = MqttClient.builder()
//            .useMqttVersion3()
//            .identifier(UUID.randomUUID().toString())
//            .automaticReconnectWithDefaultConfig()
//            .serverHost("2661f15a40284af4a2caa13a37aa9772.s1.eu.hivemq.cloud")
//            .serverPort(8883)
//            .sslWithDefaultConfig()
//            .buildAsync();
//    public void connect(String username, String password){
//        client.connectWith()
//                .simpleAuth()
//                .username(username)
//                .password(password.getBytes())
//                .applySimpleAuth()
//                .send()
//                .whenComplete((connAck, throwable) -> {
//                    // Handle connection complete
//                    System.out.println("Successfully connected:)");
//                });
//    }
//    public void publish(String message, String topic){
//        client.publishWith()
//                .topic("IoTBar/" + topic)
//                .payload(message.getBytes())
//                .qos(MqttQos.AT_MOST_ONCE)
//                .send()
//                .whenComplete((mqtt3Publish, throwable) -> {
//                    if (throwable != null) {
//                        // Handle failure to publish
//                    } else {
//                        System.out.println("Message:" + message + " successfully published");
//                    }
//                });
//    }
//    public void subscribe(String topic){
//        client.subscribeWith()
//                .topicFilter(topic)
//                .callback(publish -> {
//                    // Process the received message
//                })
//                .send()
//                .whenComplete((subAck, throwable) -> {
//                    if (throwable != null) {
//                        // Handle failure to subscribe
//                    } else {
//                        System.out.println("Successfully subscribed to "+topic);
//                    }
//                });
//    }
//}


package studentgroup.demobarometer;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class mqttHandler {
    static Mqtt5Client client;
    public void balabala() throws InterruptedException, UnknownHostException, SocketException {
        final String host = "2661f15a40284af4a2caa13a37aa9772.s1.eu.hivemq.cloud"; // use your host-name, it should look like '<alphanumeric>.s2.eu.hivemq.cloud'
        final String username = "IoTBar"; // your credentials
        final String password = "Iotbarometer1";

        final InetAddress localHost = InetAddress.getLocalHost();
        final NetworkInterface ni = NetworkInterface.getByInetAddress(localHost);
        final byte[] hardwareAddress = ni.getHardwareAddress(); // use this to get your MAC address, use it as a unique identifier

        // 1. create the client
        client = Mqtt5Client.builder()
                .identifier(UUID.randomUUID().toString()) // use a unique identifier
                .serverHost(host)
                .automaticReconnectWithDefaultConfig() // the client automatically reconnects
                .serverPort(8883) // this is the port of your cluster, for mqtt it is the default port 8883
                .sslWithDefaultConfig() // establish a secured connection to HiveMQ Cloud using TLS
                .build();


        // 2. connect the client
        client.toBlocking().connectWith()
                .simpleAuth() // using authentication, which is required for a secure connection
                .username(username) // use the username and password you just created
                .password(password.getBytes(StandardCharsets.UTF_8))
                .applySimpleAuth()
                .willPublish() // the last message, before the client disconnects
                .topic("Data")
                .payload("successfully".getBytes())
                .applyWillPublish()
                .send();
    }
    public void subscribe(String topic) {
        // 3. subscribe and consume messages
        client.toAsync().subscribeWith()
                .topicFilter(topic)
                .callback(publish -> {
                    System.out.println("Received message on topic " + publish.getTopic() + ": " +
                            new String(publish.getPayloadAsBytes(), StandardCharsets.UTF_8));
                })
                .send();
    }
    public void publish(String message, String topic) {
        client.toBlocking().publishWith()
                .topic(topic)
                .payload(message.getBytes())
                .qos(MqttQos.AT_MOST_ONCE)
                .send();
    }
        // 4. simulate periodic publishing of sensor data
//        while (true) {
//            client.toBlocking().publishWith()
//                    .topic("Data")
//                    .payload(getBrightness())
//                    .send();
//
//            TimeUnit.MILLISECONDS.sleep(500);
//
//            client.toBlocking().publishWith()
//                    .topic("Data")
//                    .payload(getTemperature())
//                    .send();
//
//            TimeUnit.MILLISECONDS.sleep(500);
//        }
    }

