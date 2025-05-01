package tn.esprit.util;

import java.net.*;
import java.net.http.*;
import java.time.Duration;
import com.google.gson.*;

public class BadWordsFilter {
    private static final String API_KEY = "Gm8cf947SAxv3d4KJgDNng==pc4y8RDZd0uouYG4";
    private static final String API_URL = "https://api.api-ninjas.com/v1/profanityfilter";
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    public static boolean containsBadWords(String text) {
        if (text == null || text.isEmpty()) return false;

        try {
            String encodedText = URLEncoder.encode(text, "UTF-8");
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + "?text=" + encodedText))
                    .header("X-Api-Key", API_KEY)
                    .timeout(Duration.ofSeconds(3))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return parseApiResponse(response.body());
        } catch (Exception e) {
            // logError("API Error", e.toString()); // You can log errors if needed but not for every call
            return false; // Don't block the application on error
        }
    }

    private static boolean parseApiResponse(String json) {
        try {
            JsonObject response = JsonParser.parseString(json).getAsJsonObject();
            return response.get("has_profanity").getAsBoolean();
        } catch (Exception e) {
            // logError("JSON Parsing Error", e.toString()); // You can log errors if needed but not for every call
            return false;
        }
    }
}
