package it.univaq.disim_se4as.services.temperatureactivator;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import it.univaq.disim.se4as.services.sensor.api.*;

public class SensorProbingThread extends Thread {
	private volatile boolean active = true;
	private BundleContext context;
	
	private MQTTConnection connection = new MQTTConnection();

	public void run() {
		while (active) {
			try {
				printSensorValues();
				Thread.sleep(5000);
			} catch (InvalidSyntaxException | InterruptedException | MqttException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void setContext(BundleContext context) {
		this.context = context;
	}

	private void printSensorValues() throws InvalidSyntaxException, InterruptedException, MqttException {
		ServiceReference<?>[] refs = context.getAllServiceReferences(ISensor.class.getName(), null);
		
		if (refs != null) {
			
			for (int i = 0; i < refs.length; i++) {
				ISensor sensor = (ISensor) context.getService(refs[i]);
				if (refs[i] != null) {
					if (sensor != null) {
						// System.out.println("GAS Sensed value" );
						// System.out.println("Unit: " + sensor.getData().getUnit());
						int temp = sensor.getData().getVal();
						if (temp < 20) {
							for (int k = 0; k < 15; k++) {
								int data = temp + 16 + k;
								connection.send(Topics.WEATHER_TEMPERATURE.getTopic(), (data)+"");
								System.out.println(
										"Temperature Sensed value: " + (data) + sensor.getData().getUnit());
								Thread.sleep(5000);

								// System.out.println("Accuracy: " + sensor.getData().getAccuracy());

							}
						} else
							for (int k = 0; k < 10; k++) {
								connection.send(Topics.WEATHER_TEMPERATURE.getTopic(), (temp + k)+"");
								System.out.println(
										"Temperature Sensed value: " + (temp + k) + sensor.getData().getUnit());
								Thread.sleep(5000);
							}
					}

				}
			}

		}
		//connection.finalize();
	}
}
