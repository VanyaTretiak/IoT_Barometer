package studentgroup.demobarometer;

public class ConnectionController {
    private static mqttHandler mqtt = new mqttHandler();

    static void TrySendMqtt() {
        System.out.println("Trying to transmit message...");
        //mqtt.publish("Sent", "Data");
    }
    static public int mqttLoad() {

        return 0;
    }
}