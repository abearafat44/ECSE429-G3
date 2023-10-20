import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpResponse.BodyHandlers;

import javax.swing.JFrame;
import javax.swing.JOptionPane;     
import org.json.*;

import org.junit.Test;


public class tests {

    @Test
    public void testing_post() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "test");
        jSONObject.put("doneStatus",false);
        jSONObject.put("description","trying so hard rn");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos")).POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response_str.statusCode());
        System.out.println(response_str.body());

        String xml = XML.toString(jSONObject);
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos")).POST(HttpRequest.BodyPublishers.ofString(xml)).build();
        assertEquals(201, response_str.statusCode());
        response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response_str.body());
    }
    @Test
    public void get_categories() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories")).GET().build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response_str.statusCode());
        System.out.println(response_str.body());
    }
    @Test
    public void head_categories() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories")).headers().build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response_str.statusCode());
        System.out.println(response_str.body());
    }
    @Test
    public void post_categories() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "example title");
        jSONObject.put("description","example description");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories")).POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response_str.statusCode());
        System.out.println(response_str.body());
    }
    
}