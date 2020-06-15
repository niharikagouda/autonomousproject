package it.univaq.disim.se4as.building.executor;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

public class Executor {
	private final String broker = "tcp://localhost:1883";
	private final String clientId = ""; // private final String clientId = "MQTT Execution";
	private final int qos = 2;
	private MqttClient sampleClient = null;
	
	public Executor() {
		try {
        	sampleClient = new MqttClient(broker, clientId);
    		MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
        	sampleClient.connect(connOpts);
        } catch (Exception e) {
			System.out.println("Exception occured: "+e);
		}
		
	}
	
	public void publishFanValue(String fanVal) throws MqttPersistenceException, MqttException {
		MqttMessage message = new MqttMessage(fanVal.getBytes());
        message.setQos(qos);
		sampleClient.publish("sm/fan", message);
		
	}
    public void publishLightValue(String lightVal) throws MqttPersistenceException, MqttException {
		MqttMessage message = new MqttMessage(lightVal.getBytes());
        message.setQos(qos);
		sampleClient.publish("sm/light", message);
	}

	public void publishWindowValue(String windowVal) throws MqttPersistenceException, MqttException {
		MqttMessage message = new MqttMessage(windowVal.getBytes());
        message.setQos(qos);
		sampleClient.publish("sm/window", message);
	}
	

}
