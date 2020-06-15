package it.univaq.disim.se4as.building.monitor;

public enum Topics {
	
	WEATHER_TEMPERATURE("weather/temperature"),
	WEATHER_HUMIDITY("weather/humidity"),
	WEATHER_CO2("weather/co2");
	
	
	private final String topic;

	private Topics(String topic) {
		this.topic = topic;
	}
	
	public String getTopic() {
		return topic;
	}

}
