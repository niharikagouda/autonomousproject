package it.univaq.disim.se4as.building.monitor.runner;

import it.univaq.disim.se4as.building.monitor.Monitor;

public class MonitorRunner {
	public static void main(String[] args) throws InterruptedException {
		try {
			new Monitor();
			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
			Thread.sleep(2000L);
			main(null);
		}
	}
}
