import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;    
import org.json.*;
import org.junit.Test;
import java.time.*;
public class tests {
    

  private static String uri = "http://localhost:4567/" ;


  @Test
  public static int createTodo() throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    JSONObject payload = new JSONObject();
    payload.put("title", "newTodo");
    payload.put("doneStatus",false);
    payload.put("description", "dummy");
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri+"todos")).POST(HttpRequest.BodyPublishers.ofString(payload.toString())).build();

    int start_time = LocalTime.now().getNano();
    Thread.sleep(100);
    HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals(201, response_str.statusCode());
    int end_time = LocalTime.now().getNano();
    
    return end_time - start_time - 100000000;
  }

  @Test
  public static int createCategories() throws IOException, InterruptedException{
    HttpClient client = HttpClient.newHttpClient();
    JSONObject payload = new JSONObject();
    payload.put("title", "newCategory");
    payload.put("description", "dummy");
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri+"categories")).POST(HttpRequest.BodyPublishers.ofString(payload.toString())).build();
    int start_time = LocalTime.now().getNano();
    Thread.sleep(100);
    HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals(201, response_str.statusCode());
    int end_time = LocalTime.now().getNano();

    return end_time - start_time - 100000000;
  }

  @Test
  public static int createProjects() throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    JSONObject payload = new JSONObject();
    payload.put("title", "newProject");
    payload.put("completed", false);
    payload.put("active", false);
    payload.put("description", "dummy");
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri+"projects")).POST(HttpRequest.BodyPublishers.ofString(payload.toString())).build();

    int start_time = LocalTime.now().getNano();
    Thread.sleep(100);
    HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals(201, response_str.statusCode());
    int end_time = LocalTime.now().getNano();

    return end_time - start_time - 100000000;
  }
  @Test
  public static int modifyTodo(int id) throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    JSONObject payload = new JSONObject();
    payload.put("title", "newTodo");
    payload.put("doneStatus",true);
    payload.put("description", "dummy");
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri+"todos/"+id)).POST(HttpRequest.BodyPublishers.ofString(payload.toString())).build();

    int start_time = LocalTime.now().getNano();
    Thread.sleep(100);
    HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals(200, response_str.statusCode());
    int end_time = LocalTime.now().getNano();
    

    return end_time - start_time - 100000000;
  }
  @Test
  public static int modifyCategories(int id) throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    JSONObject payload = new JSONObject();
    payload.put("title", "newCategory");
    payload.put("description", "dummy 2");
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri+"categories/"+id)).POST(HttpRequest.BodyPublishers.ofString(payload.toString())).build();

    int start_time = LocalTime.now().getNano();
    Thread.sleep(100);
    HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals(200, response_str.statusCode());
    int end_time =LocalTime.now().getNano();

    return end_time - start_time - 100000000;    
  }

  @Test
  public static int modifyProjects(int id) throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    JSONObject payload = new JSONObject();
    payload.put("title", "newProject");
    payload.put("completed", false);
    payload.put("active", true);
    payload.put("description", "dummy 2");
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri+"projects/"+id)).POST(HttpRequest.BodyPublishers.ofString(payload.toString())).build();

    int start_time = LocalTime.now().getNano();
    Thread.sleep(100);
    HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals(200, response_str.statusCode());
    int end_time = LocalTime.now().getNano();

    return end_time - start_time - 100000000;
  }
  @Test
  public static int deleteTodo(int id) throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request_del = HttpRequest.newBuilder().uri(URI.create(uri+"todos/" + id)).DELETE().build();
    int start_time = LocalTime.now().getNano();
    Thread.sleep(100);
    HttpResponse<String> response_str_del = client.send(request_del, HttpResponse.BodyHandlers.ofString());
    assertEquals(200, response_str_del.statusCode());
    int end_time = LocalTime.now().getNano();
    return end_time - start_time - 100000000;
  }
  @Test
  public static int deleteCategories(int id) throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request_del = HttpRequest.newBuilder().uri(URI.create(uri+"categories/" + id)).DELETE().build();
    int start_time = LocalTime.now().getNano();
    Thread.sleep(100);
    HttpResponse<String> response_str_del = client.send(request_del, HttpResponse.BodyHandlers.ofString());
    assertEquals(200, response_str_del.statusCode());
    int end_time = LocalTime.now().getNano();
    
    return end_time - start_time - 100000000;
  }
  @Test
  public static int deleteProjects(int id) throws IOException, InterruptedException {
  HttpClient client = HttpClient.newHttpClient();
    HttpRequest request_del = HttpRequest.newBuilder().uri(URI.create(uri+"projects/" + id)).DELETE().build();
    int start_time = LocalTime.now().getNano();
    Thread.sleep(100);
    HttpResponse<String> response_str_del = client.send(request_del, HttpResponse.BodyHandlers.ofString());
    assertEquals(200, response_str_del.statusCode());
    int end_time = LocalTime.now().getNano();
    return end_time - start_time - 100000000;
  }

}
