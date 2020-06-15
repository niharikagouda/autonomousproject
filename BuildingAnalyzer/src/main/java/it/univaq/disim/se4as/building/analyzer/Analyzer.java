package it.univaq.disim.se4as.building.analyzer;

import it.univaq.disim.se4as.building.planner.Planner;

public class Analyzer {

	private Planner planner = new Planner();

	public String processTemperature(int temperature) {
		return planner.setFan(temperature);

	}

	public String processCO2(int co2Value) {
		return planner.setLight(co2Value);

	}

	public String processHumidity(int humidityValue) {
		return planner.operationWindows(humidityValue);

	}

}
