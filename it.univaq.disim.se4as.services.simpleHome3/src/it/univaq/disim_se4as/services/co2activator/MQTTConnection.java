package it.univaq.disim_se4as.services.co2activator;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQTTConnection {

	private final String broker = "tcp://localhost:1883";
	private final String clientId = ""; // private final String clientId = "MQTT Execution";
	private final int qos = 2;
	private MqttClient sampleClient = null;

	public MQTTConnection(){
        try {
        	sampleClient = new MqttClient(broker, clientId);
    		MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
        	sampleClient.connect(connOpts);
        } catch (Exception e) {
			System.out.println("Exception occured: "+e);
		}
	}
	
	public void send(String topic, String content) {
        try {
            System.out.println("Executor send message (" + topic + "): " + content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic, message);
        } catch(MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
	}
	
	
	@Override
	public void finalize() throws MqttException {
		sampleClient.disconnect();
        System.out.println("Executor disconnected");
	}
	
}
