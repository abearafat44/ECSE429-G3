import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;
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
        assertEquals(200, response_str.statusCode());
        JSONObject response_json = new JSONObject(response_str.body());
        JSONArray response_object = new JSONArray(response_json.get("categories").toString());
        for(int i = 0; i<response_object.length(); i++){
            if(response_object.getJSONObject(i).get("id").toString().equals("2")){
                assertEquals("Home", response_object.getJSONObject(i).get("title"));
            }
        }
    }
    // @Test
    // public void head_categories() throws IOException, InterruptedException {
    //     HttpClient client = HttpClient.newHttpClient();
    //     JSONObject jSONObject = new JSONObject();
    //     HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories")).headers().build();
    //     HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
    //     assertEquals(201, response_str.statusCode());
    //     System.out.println(response_str.body());
    // }
    @Test
    public void post_categories() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "example title");
        jSONObject.put("description","example description");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories")).POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response_str.statusCode());
        JSONObject response_json = new JSONObject(response_str.body());
        assertEquals(response_json.get("title"), "example title");
        assertEquals(response_json.get("description"), "example description");
        int newId = Integer.parseInt(response_json.get("id").toString());

        HttpRequest request_del = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/" + newId)).DELETE().build();
        HttpResponse<String> response_str_del = client.send(request_del, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str_del.statusCode());
    }
    @Test
    public void get_categories_i() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/2")).GET().build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
        JSONObject response_json = new JSONObject(response_str.body());
        JSONArray response_object = new JSONArray(response_json.get("categories").toString());
        assertEquals(response_object.getJSONObject(0).get("title").toString(), "Home");
    }

    @Test
    public void post_categories_i() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request_get = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/1")).GET().build();
        HttpResponse<String> response_str_get = client.send(request_get, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str_get.statusCode());
        JSONObject response_json_get = new JSONObject(response_str_get.body());
        JSONArray response_object = new JSONArray(response_json_get.get("categories").toString());
        String original_title = response_object.getJSONObject(0).get("title").toString();
        String original_desc = response_object.getJSONObject(0).get("description").toString();

        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "different title");
        jSONObject.put("description","different description");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/1")).POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
        JSONObject response_json = new JSONObject(response_str.body());
        assertEquals(response_json.get("title"), "different title");
        assertEquals(response_json.get("description"), "different description");

        JSONObject reset = new JSONObject();
        reset.put("title", original_title);
        reset.put("description",original_desc);
        HttpRequest request_reset = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/1")).POST(HttpRequest.BodyPublishers.ofString(reset.toString())).build();
        HttpResponse<String> response_str_reset = client.send(request_reset, HttpResponse.BodyHandlers.ofString());
    }

    @Test
    public void put_categories_i() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request_get = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/1")).GET().build();
        HttpResponse<String> response_str_get = client.send(request_get, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str_get.statusCode());
        JSONObject response_json_get = new JSONObject(response_str_get.body());
        JSONArray response_object = new JSONArray(response_json_get.get("categories").toString());
        String original_title = response_object.getJSONObject(0).get("title").toString();
        String original_desc = response_object.getJSONObject(0).get("description").toString();

        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "even more different title");
        jSONObject.put("description","even more different description");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/1")).PUT(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
        JSONObject response_json = new JSONObject(response_str.body());
        assertEquals(response_json.get("title"), "even more different title");
        assertEquals(response_json.get("description"), "even more different description");

        JSONObject reset = new JSONObject();
        reset.put("title", original_title);
        reset.put("description",original_desc);
        HttpRequest request_reset = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/1")).POST(HttpRequest.BodyPublishers.ofString(reset.toString())).build();
        HttpResponse<String> response_str_reset = client.send(request_reset, HttpResponse.BodyHandlers.ofString());
    }

    @Test
    public void delete_categories_i() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "new title");
        jSONObject.put("description","new description");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories")).POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response_str.statusCode());
        JSONObject response_json = new JSONObject(response_str.body());
        assertEquals(response_json.get("title"), "new title");
        assertEquals(response_json.get("description"), "new description");
        int newId = Integer.parseInt(response_json.get("id").toString());

        HttpRequest del_request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/" + newId)).DELETE().build();
        HttpResponse<String> response_str_del = client.send(del_request, HttpResponse.BodyHandlers.ofString());

        HttpRequest request_get = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories")).GET().build();
        HttpResponse<String> response_str_get = client.send(request_get, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response_str.statusCode());
        JSONObject response_json_get = new JSONObject(response_str_get.body());
        JSONArray response_object = new JSONArray(response_json_get.get("categories").toString());
        for(int i = 0; i<response_object.length(); i++){
            assertNotEquals(response_object.getJSONObject(i).get("id").toString(), Integer.toString(newId));
        }
    }

    @Test
    public void get_categories_i_projects() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/2/projects")).GET().build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
    }
    
    @Test
    public void post_categories_i_projects() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "new project title");
        jSONObject.put("description","new project description");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/2/projects")).POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str_post = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject response_json_post = new JSONObject(response_str_post.body());
        assertEquals(201, response_str_post.statusCode());
        assertEquals(response_json_post.get("title"), "new project title");
        assertEquals(response_json_post.get("description"), "new project description");
        int newId = Integer.parseInt(response_json_post.get("id").toString());

        HttpRequest request_get = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/2/projects")).GET().build();
        HttpResponse<String> response_str = client.send(request_get, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
        JSONObject response_json = new JSONObject(response_str.body());
        JSONArray response_object = new JSONArray(response_json.get("projects").toString());
        for(int i = 0; i<response_object.length(); i++){
            if(response_object.getJSONObject(i).get("id").toString().equals(Integer.toString(newId))){
                assertEquals("new project title", response_object.getJSONObject(i).get("title"));
                assertEquals("new project description", response_object.getJSONObject(i).get("description"));
            }
        }

        HttpRequest request_del = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/2/projects/" + newId)).DELETE().build();
        HttpResponse<String> response_str_del = client.send(request_del, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str_del.statusCode());
    }

    @Test
    public void delete_categories_i_projects_i() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "newer project title");
        jSONObject.put("description","newer project description");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/2/projects")).POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str_post = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject response_json_post = new JSONObject(response_str_post.body());
        assertEquals(201, response_str_post.statusCode());
        assertEquals(response_json_post.get("title"), "newer project title");
        assertEquals(response_json_post.get("description"), "newer project description");
        int newId = Integer.parseInt(response_json_post.get("id").toString());

        HttpRequest request_del = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/2/projects/" + newId)).DELETE().build();
        HttpResponse<String> response_str_del = client.send(request_del, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str_del.statusCode());

        HttpRequest request_get = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/2/projects")).GET().build();
        HttpResponse<String> response_str = client.send(request_get, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
        JSONObject response_json = new JSONObject(response_str.body());
        JSONArray response_object = new JSONArray(response_json.get("projects").toString());
        for(int i = 0; i<response_object.length(); i++){
            assertNotEquals(response_object.getJSONObject(i).get("id").toString(), Integer.toString(newId));
        }
    }

    @Test
    public void get_categories_i_todos() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/2/todos")).GET().build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
    }
    
    @Test
    public void post_categories_i_todos() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "new todo title");
        jSONObject.put("description","new todo description");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/2/todos")).POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str_post = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject response_json_post = new JSONObject(response_str_post.body());
        assertEquals(201, response_str_post.statusCode());
        assertEquals(response_json_post.get("title"), "new todo title");
        assertEquals(response_json_post.get("description"), "new todo description");
        int newId = Integer.parseInt(response_json_post.get("id").toString());

        HttpRequest request_get = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/2/todos")).GET().build();
        HttpResponse<String> response_str = client.send(request_get, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
        JSONObject response_json = new JSONObject(response_str.body());
        JSONArray response_object = new JSONArray(response_json.get("todos").toString());
        for(int i = 0; i<response_object.length(); i++){
            if(response_object.getJSONObject(i).get("id").toString().equals(Integer.toString(newId))){
                assertEquals("new todo title", response_object.getJSONObject(i).get("title"));
                assertEquals("new todo description", response_object.getJSONObject(i).get("description"));
            }
        }

        HttpRequest request_del = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/2/todos/" + newId)).DELETE().build();
        HttpResponse<String> response_str_del = client.send(request_del, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str_del.statusCode());
    }

    @Test
    public void delete_categories_i_todos_i() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "newer todo title");
        jSONObject.put("description","newer todo description");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/2/todos")).POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str_post = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject response_json_post = new JSONObject(response_str_post.body());
        assertEquals(201, response_str_post.statusCode());
        assertEquals(response_json_post.get("title"), "newer todo title");
        assertEquals(response_json_post.get("description"), "newer todo description");
        int newId = Integer.parseInt(response_json_post.get("id").toString());

        HttpRequest request_del = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/2/todos/" + newId)).DELETE().build();
        HttpResponse<String> response_str_del = client.send(request_del, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str_del.statusCode());

        HttpRequest request_get = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/2/todos")).GET().build();
        HttpResponse<String> response_str = client.send(request_get, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
        JSONObject response_json = new JSONObject(response_str.body());
        JSONArray response_object = new JSONArray(response_json.get("todos").toString());
        for(int i = 0; i<response_object.length(); i++){
            assertNotEquals(response_object.getJSONObject(i).get("id").toString(), Integer.toString(newId));
        }
    }
}

