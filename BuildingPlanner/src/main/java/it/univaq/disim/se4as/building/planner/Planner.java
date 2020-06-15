package it.univaq.disim.se4as.building.planner;

import java.util.Calendar;

public class Planner {

	Calendar cal = Calendar.getInstance();

	public String setFan(int temperature) {

		int month = cal.get(Calendar.MONTH);

		// summer season
		if (month >= 4 && month <= 6) {
			if (temperature > 35) {
				return "On";
			} else
				return "Off";
		}
		// winter season
		else {
			if (temperature > 20 && temperature < 24) {
				return "On";

			} else
				return "Off";
		}
	}

	public String setLight(int co2Value) {
		int hour = cal.get(Calendar.HOUR);
        //At night, light will be switched on.
		if (hour >= 17 && hour <= 24) {
			return "On";
		} else if (co2Value > 1000) {
			return "On";
		} else
			return "Off";
	}

	public String operationWindows(int humidityValue) {
		if (humidityValue > 70 && humidityValue < 100) {
			return "Close";
		} else if (humidityValue < 20) {
			return "Close";
		} else
			return "Open";
	}
}
