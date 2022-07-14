import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.weather.yandex.ru")
                .addPathSegment("v2")
                .addPathSegment("forecast")
                .addQueryParameter("lat", "53.902496")
                .addQueryParameter("lon", "27.561481")
                .addQueryParameter("lang", "en_US")
                .addQueryParameter("limit", "5")
                .addQueryParameter("hours", "false")
                .addQueryParameter("extra", "true")
                .build();

        Request request = new Request.Builder()
                .addHeader("X-Yandex-API-Key", "154d97f0-613d-4497-b9d9-02259cb4980b")
                .url(url)
                .get()
                .build();

        Response response = okHttpClient.newCall(request).execute();
        String body = response.body().string();
        ObjectMapper objectMapper = new ObjectMapper();
        String city = objectMapper.readTree(body).at("/geo_object/locality/name").asText();

        ArrayNode forecasts = (ArrayNode) objectMapper.readTree(body).at("/forecasts");

        ArrayList <WeatherResponse> ws = new ArrayList<>();

        for (int i = 0; i < forecasts.size(); i++) {
            JsonNode individualElement = forecasts.get(i);
            String date = individualElement.get("date").asText();
            String dayT = individualElement.get("parts").get("day").get("temp_avg").asText();
            String dayC = individualElement.get("parts").get("day").get("condition").asText();

            ws.add(new WeatherResponse(date,city,dayC,Integer.parseInt(dayT)));
        }

        for (WeatherResponse o : ws) {
            System.out.println("IN CITY " + o.CITY() + " on " + o.DATE() + " expected " + o.WEATHER_TEXT() + " weather with temperature " + o.TEMPERATURE());
        }    }
}