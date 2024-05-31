//package studentgroup.demobarometer;
//import com.hivemq.client.mqtt.datatypes.MqttQos;
//import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
//import java.net.InetAddress;
//import java.net.NetworkInterface;
//import java.net.SocketException;
//import java.net.UnknownHostException;
//import java.nio.charset.StandardCharsets;
//import java.util.UUID;
//import java.util.ArrayList;
//import java.util.List;
//
//public class mqttHandler {
//    static Mqtt5Client client;
//    static ConnectionController cc = new ConnectionController();
//    final String host = "2661f15a40284af4a2caa13a37aa9772.s1.eu.hivemq.cloud";
////    final String host = "broker.hivemq.com";
//    final int port = 8883;
//    final String username = "IoTBar";
//    final String password = "Iotbarometer1";
//    private List<CallbackListener> listeners = new ArrayList<>();
//    public void addCallbackListener(CallbackListener listener) {
//        listeners.add(listener);
//    }
//
//    public void init_mqtt() throws InterruptedException, UnknownHostException, SocketException {
//        final InetAddress localHost = InetAddress.getLocalHost();
//        final NetworkInterface ni = NetworkInterface.getByInetAddress(localHost);
//        final byte[] hardwareAddress = ni.getHardwareAddress(); //If needed, use this to get your MAC address, use it as a unique identifier
//
//        //Creating the client
//        client = Mqtt5Client.builder()
//                .identifier(UUID.randomUUID().toString()) //Using a unique identifier
//                .serverHost(host)
//                .automaticReconnectWithDefaultConfig() //The client automatically reconnects
//                .serverPort(port) //This is the port of MQTT cluster
//                .sslWithDefaultConfig() //Establishing a secured connection to HiveMQ Cloud using TLS
//                .build();
//
//        //Connecting to the client
//        client.toBlocking().connectWith()
//                .simpleAuth() //Using authentication, which is required for a secure connection
//                .username(username) //Using the username and password
//                .password(password.getBytes(StandardCharsets.UTF_8))
//                .applySimpleAuth()
//                .willPublish() //The last message, before the client disconnects
//                .topic("Data")
//                .payload("Successfully connected".getBytes())
//                .applyWillPublish()
//                .send();
//    }
//
//    public void subscribe(String topic) {
//        //Subscribing and consuming messages
//        client.toAsync().subscribeWith()
//                .topicFilter(topic)
//                .callback(publish -> {
//                    callback(publish.getTopic().toString(), new String(publish.getPayloadAsBytes(), StandardCharsets.UTF_8));
//                    for (CallbackListener listener : listeners) {
//                        listener.onCallback(new String(publish.getPayloadAsBytes(), StandardCharsets.UTF_8));
//                    }
//                })
//                .send();
//    }
//
//    public void callback(String topic, String message){
//        for (CallbackListener listener : listeners) {
//            listener.onCallback(message);
//        }
//        System.out.println("Received message on topic " + topic + "\nMessage: " + message + "\n");
//
//    }
//
//    public void publish(String message, String topic) {
//        client.toBlocking().publishWith()
//                .topic(topic)
//                .payload(message.getBytes())
//                .qos(MqttQos.AT_MOST_ONCE)
//                .send();
//    }
//
//    //Simulating periodic publishing of sensor data
////        while (true) {
////            client.toBlocking().publishWith()
////                    .topic("Data")
////                    .payload(getBrightness())
////                    .send();
////
////            TimeUnit.MILLISECONDS.sleep(500);
////
////            client.toBlocking().publishWith()
////                    .topic("Data")
////                    .payload(getTemperature())
////                    .send();
////
////            TimeUnit.MILLISECONDS.sleep(500);
////        }
//    }
//


package studentgroup.demobarometer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;

public class mqttHandler {

    static Mqtt5Client client;
    final String host = "57bba886099840f59d51b9ead1aa1c24.s1.eu.hivemq.cloud";
    final int port = 8883;
    final String username = "IoTBar";
    static boolean flag = false;
    final String password = "Iotbarometer1";
    public DatabaseConnection dbconn = new DatabaseConnection();

    public void init_mqtt() throws InterruptedException, UnknownHostException, SocketException {
        final InetAddress localHost = InetAddress.getLocalHost();
        final NetworkInterface ni = NetworkInterface.getByInetAddress(localHost);
        final byte[] hardwareAddress = ni.getHardwareAddress();

        client = Mqtt5Client.builder()
                .identifier(UUID.randomUUID().toString())
                .serverHost(host)
                .automaticReconnectWithDefaultConfig()
                .serverPort(port)
                .sslWithDefaultConfig()
                .build();

        client.toBlocking().connectWith()
                .simpleAuth()
                .username(username)
                .password(password.getBytes(StandardCharsets.UTF_8))
                .applySimpleAuth()
                .willPublish()
                .topic("Data")
                .payload("Successfully connected".getBytes())
                .applyWillPublish()
                .send();
    }

    public void subscribe(String topic) {
        client.toAsync().subscribeWith()
                .topicFilter(topic)
                .callback(publish -> {
                    callback(publish.getTopic().toString(), new String(publish.getPayloadAsBytes(), StandardCharsets.UTF_8));
                })
                .send();
    }

    private void callback(String topic, String message)  {
        if (flag){
            System.out.println("Received message on topic " + topic + "\nMessage: " + message + "\n");
            JSONObject json = createJSONObject(message);
//        LocalDateTime dtime = LocalDateTime.parse("2018-05-05T11:50:55");
            LocalDateTime dtime = LocalDateTime.parse(json.get("time").toString());
            Measurement measurement = new Measurement(
                    Double.parseDouble(json.get("temperature").toString()),
                    Double.parseDouble(json.get("pressure").toString()),
                    Double.parseDouble(json.get("humidity").toString()),
                    Double.parseDouble(json.get("approxHeight").toString()),
                    dtime

            );
            dbconn.insertItem(measurement);
        }
        flag = true;
    }
    private static JSONObject createJSONObject(String jsonString){
        JSONObject  jsonObject=new JSONObject();
        JSONParser jsonParser=new  JSONParser();
        if ((jsonString != null) && !(jsonString.isEmpty())) {
            try {
                jsonObject=(JSONObject) jsonParser.parse(jsonString);
            } catch (org.json.simple.parser.ParseException ignored) {

            }
        }
        return jsonObject;
    }

    public void publish(String message, String topic) {
        client.toBlocking().publishWith()
                .topic(topic)
                .payload(message.getBytes())
                .qos(MqttQos.AT_MOST_ONCE)
                .send();
    }
}
