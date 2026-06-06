package com.ensa.akinator.Managers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ensa.akinator.Exceptions.AIConnectionFailedException;

public class AIManager {
    private static String appTitle = "My Java Akinator";
    private static String apiKey = "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";
    private static String apiUrl = "https://api.groq.com/openai/v1/responses";
    private static String model2Name = "llama-3.1-8b-instant";
    private static String modelName = "llama-3.3-70b-versatile";

    public static String sendApiRequest(String message) throws AIConnectionFailedException {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + apiKey);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("X-Title", appTitle);
            conn.setDoOutput(true);

            JSONObject payload = new JSONObject();
            payload.put("model", modelName);
            payload.put("input", message);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = payload.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                StringBuilder responseString = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    responseString.append(line);
                }

                JSONObject jsonResponse = new JSONObject(responseString.toString());
                return jsonResponse.getJSONArray("output")
                        .getJSONObject(1)
                        .getJSONArray("content")
                        .getJSONObject(0)
                        .getString("text");
            } else {
                System.out.println("API Error: Response Code " + conn.getResponseMessage());
                throw new AIConnectionFailedException("API Error: Response Code " + responseCode);
            }

        } catch (Exception e) {
            throw new AIConnectionFailedException(e.toString());
        }
    }
}
