package studentgroup.demobarometer;
import org.json.simple.JSONObject;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import org.json.simple.parser.JSONParser;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;

//Class to establish MQTT connection
public class mqttHandler {
    static Mqtt5Client client;
    final String host = "57bba886099840f59d51b9ead1aa1c24.s1.eu.hivemq.cloud";
    final int port = 8883;
    final String username = "IoTBar";
    static boolean flag = false;
    final String password = "Iotbarometer1";

    //Establish connection to database
    public DatabaseConnection dbconn = new DatabaseConnection();

    //Function to initialize MQTT connection
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

    //Function to subscribe to MQTT broker's topic
    public void subscribe(String topic) {
        client.toAsync().subscribeWith()
                .topicFilter(topic)
                .callback(publish -> {
                    callback(publish.getTopic().toString(), new String(publish.getPayloadAsBytes(), StandardCharsets.UTF_8));
                })
                .send();
    }

    //Function to recieve callback from MQTT broker
    private void callback(String topic, String message)  {
        if (flag){
            System.out.println("Received message on topic " + topic + "\nMessage: " + message + "\n");
            JSONObject json = createJSONObject(message);
            //LocalDateTime dtime = LocalDateTime.parse("2018-05-05T11:50:55");
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

    //Function to create JSON Object
    private static JSONObject createJSONObject(String jsonString){
        JSONObject  jsonObject = new JSONObject();
        JSONParser jsonParser = new  JSONParser();

        if ((jsonString != null) && !(jsonString.isEmpty())) {
            try {
                jsonObject = (JSONObject) jsonParser.parse(jsonString);
            }
            catch (org.json.simple.parser.ParseException ignored) {
            }
        }
        return jsonObject;
    }

    //Function to publish message on MQTT broker
//    public void publish(String message, String topic) {
//        client.toBlocking().publishWith()
//                .topic(topic)
//                .payload(message.getBytes())
//                .qos(MqttQos.AT_MOST_ONCE)
//                .send();
//    }
}