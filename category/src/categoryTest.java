import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.simple.JSONObject;
import org.junit.Test;

public class categoryTest {
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

