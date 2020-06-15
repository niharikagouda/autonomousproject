package it.univaq.disim.se4as.building.monitor;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import it.univaq.disim.se4as.building.analyzer.Analyzer;
import it.univaq.disim.se4as.building.executor.Executor;

public class Monitor implements MqttCallback {
	private final String broker = "tcp://localhost:1883";
	private final String client = "";
	private MqttClient mqttclient = null;

	private Analyzer analyzer = new Analyzer();
	private Executor executor = new Executor();

	public Monitor() throws MqttException {
		System.out.println("Monitoring service executing");
		this.mqttclient = new MqttClient(broker, client);
		
		MqttConnectOptions connOpts = new MqttConnectOptions();
		connOpts.setCleanSession(true);
		
		this.mqttclient.connect(connOpts);
		this.mqttclient.setCallback(this);

		for (Topics topic : Topics.values()) {
			this.mqttclient.subscribe(topic.getTopic());
		}
	}

	public void disconnect() throws MqttException {
		this.mqttclient.disconnect();
		System.out.println("Monitoring service disconnect.");
	}

	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub{
		System.out.println("Monitoring service lost MQTT connection.");
		cause.printStackTrace();
		System.exit(0);

	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		System.out.println("Delivery complete. Token: " + token);

	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// TODO Auto-generated method stub
		String[] parts = topic.split("/");
		switch (parts[1]) {
		case "temperature":
			String temperature = new String(message.getPayload());
			
			String fanVal = analyzer.processTemperature(Integer.parseInt(temperature));
			System.out.println("Niharika.tmp "+fanVal);
			executor.publishFanValue(fanVal);
			break;
		case "co2":
			String co2 = new String(message.getPayload());
			
			String lightVal = analyzer.processCO2(Integer.parseInt(co2));
			System.out.println("Niharika.co2 "+ lightVal);
			executor.publishLightValue(lightVal);
			break;
		case "humidity":
			String humidity = new String(message.getPayload());
			
			String windowVal = analyzer.processHumidity(Integer.parseInt(humidity));
			System.out.println("Niharika.humi "+windowVal);
			executor.publishWindowValue(windowVal);
			break;

		}

	}

}
