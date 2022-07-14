import com.google.gson.annotations.SerializedName;

public record WeatherResponse(@SerializedName("date") String DATE,
                              @SerializedName("city") String CITY,
                              @SerializedName("condition") String WEATHER_TEXT,
                              @SerializedName("temp_avg") Integer TEMPERATURE) {
    @Override
    public String DATE() {
        return DATE;
    }

    @Override
    public String CITY() {
        return CITY;
    }

    @Override
    public String WEATHER_TEXT() {
        return WEATHER_TEXT;
    }

    @Override
    public Integer TEMPERATURE() {
        return TEMPERATURE;
    }
}
