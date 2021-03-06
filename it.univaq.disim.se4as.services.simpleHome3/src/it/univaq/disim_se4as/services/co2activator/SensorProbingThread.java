package it.univaq.disim_se4as.services.co2activator;

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

			} catch (InvalidSyntaxException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void setContext(BundleContext context) {
		this.context = context;
	}

	private void printSensorValues() throws InvalidSyntaxException, InterruptedException {
		ServiceReference<?>[] refs = context.getAllServiceReferences(ISensor.class.getName(), null);
		if (refs != null) {
			for (int i = 0; i < refs.length; i++) {
				ISensor sensor = (ISensor) context.getService(refs[i]);
				if (refs[i] != null) {
					if (sensor != null) {
						// System.out.println("GAS Sensed value" );
						// System.out.println("Unit: " + sensor.getData().getUnit());
						int co2 = sensor.getData().getVal();

						for (int k = 0; k < 8; k++) {
							connection.send(Topics.WEATHER_CO2.getTopic(), (co2 + 980 + k) + "");
							System.out.println("CO2 Sensed value: " + (co2 + 980 + k) + sensor.getData().getUnit());
							Thread.sleep(5000);

							// System.out.println("Accuracy: " + sensor.getData().getAccuracy());

						}
					}

				}

			}
		}

	}
}
