import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;    
import org.json.*;

import org.junit.Test;
import org.junit.runner.OrderWith;

@OrderWith(RandomOrder.class)
public class tests {

    @Test
    public void get_todos() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos")).GET().build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
        System.out.println(response_str.body());
    }

    @Test
    public void get_todos_xml() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos")).header("Content-Type" ,"application/xml").GET().build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
        System.out.println(response_str.body());
    }
    
    @Test
    public void todo_post() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "test");
        jSONObject.put("doneStatus",false);
        jSONObject.put("description","json payload new todo");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos")).POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response_str.statusCode());
        System.out.println(response_str.body());

        JSONObject response_json = new JSONObject(response_str.body());
        assertEquals(response_json.get("title"), "test");
        assertEquals(response_json.get("description"), "json payload new todo");
        int newId = Integer.parseInt(response_json.get("id").toString());

        HttpRequest request_del = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/" + newId)).DELETE().build();
        HttpResponse<String> response_str_del = client.send(request_del, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str_del.statusCode());
    }
    

    @Test
    public void todo_post_xml() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "test_post_xml");
        jSONObject.put("doneStatus",false);
        jSONObject.put("description","xml payload new todo");
        var xml = XML.toString(jSONObject,"todo");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos")).header("Content-Type" ,"application/xml").POST(HttpRequest.BodyPublishers.ofString(xml)).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response_str.statusCode());
        System.out.println(response_str.body());

        JSONObject response_json = new JSONObject(response_str.body());
        assertEquals(response_json.get("title"), "test_post_xml");
        assertEquals(response_json.get("description"), "xml payload new todo");
        int newId = Integer.parseInt(response_json.get("id").toString());

        HttpRequest request_del = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/" + newId)).DELETE().build();
        HttpResponse<String> response_str_del = client.send(request_del, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str_del.statusCode());        
    }


    @Test
    public void todo_id_get_test() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/1")).GET().build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
        System.out.println(response_str.body());        
    }

        @Test
    public void todo_id_get_test_xml() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/1")).header("Content-Type" ,"application/xml").GET().build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
        System.out.println(response_str.body());        
    }

    @Test
    public void todo_id_post_test() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "test_id_post");
        jSONObject.put("doneStatus",false);
        jSONObject.put("description","id post testing for todo #2");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/2")).POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
    }

    @Test // Testing to show POST doesn't modify fields not included in the payload of the request
    public void todo_id_post_test_xml() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "test_id_post 2");
        jSONObject.put("doneStatus",false);
        var xml = XML.toString(jSONObject,"todo");        
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/2")).header("Content-Type" ,"application/xml").POST(HttpRequest.BodyPublishers.ofString(xml)).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
        System.out.println(response_str.body());          
    }

    @Test
    public void todo_id_put_test() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "test_id_put");
        jSONObject.put("doneStatus",false);
        jSONObject.put("description","id put testing for todo #2");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/2")).PUT(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
        JSONObject response_json = new JSONObject(response_str.body());
        assertEquals(response_json.get("title"), "test_id_put");
        assertEquals(response_json.get("description"), "id put testing for todo #2");    
    }

    @Test // Test showing PUT defaults the fields not mentioned in the payload instead of not touching them
    public void todo_id_put__xml_test() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "test_id_put 2");
        jSONObject.put("doneStatus",false);
        var xml = XML.toString(jSONObject,"todo"); 
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/2")).header("Content-Type" ,"application/xml").PUT(HttpRequest.BodyPublishers.ofString(xml)).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
        System.out.println(response_str.body());
        JSONObject response_json = new JSONObject(response_str.body());
        assertEquals(response_json.get("title"), "test_id_put 2");
        assertEquals(response_json.get("description"), "");            
    }    

    @Test
    public void delete_todo_id_test() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "test");
        jSONObject.put("doneStatus",false);
        jSONObject.put("description","json payload new todo");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos")).POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response_str.statusCode());
        System.out.println(response_str.body());

        JSONObject response_json = new JSONObject(response_str.body());
        assertEquals(response_json.get("title"), "test");
        assertEquals(response_json.get("description"), "json payload new todo");
        int newId = Integer.parseInt(response_json.get("id").toString());

        HttpRequest request_del = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/" + newId)).DELETE().build();
        HttpResponse<String> response_str_del = client.send(request_del, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str_del.statusCode());       
    }

    @Test
    public void todo_id_taskof_get_test() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/1/tasksof")).GET().build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());

    }

    @Test //Test failing because of bug in XML delivery, errorMessage: "can't find matching value for id" --BUG
    public void todo_id_tasksof_post_test_xml() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("id", "1");
        var xml = XML.toString(jSONObject,"todo"); 
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/1/tasksof")).header("Content-Type" ,"application/xml").POST(HttpRequest.BodyPublishers.ofString(xml)).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, response_str.statusCode());       
    }
    @Test 
    public void todo_id_tasksof_post_and_delete_test() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        JSONObject newProject = new JSONObject();
        newProject.put("title", "project 2");
        newProject.put("completed", false);
        newProject.put("active", true);
        newProject.put("description", "project 2 dummy");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects")).POST(HttpRequest.BodyPublishers.ofString(newProject.toString())).build();
        HttpResponse<String> response_str1 = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response_str1.statusCode());

        JSONObject id = new JSONObject(response_str1.body());
        var ID = id.get("id");
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("ID", ID);
        HttpRequest request2 = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/1/tasksof")).POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response_str2.statusCode());
        
        HttpRequest request3 = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/1/tasksof/" + ID)).DELETE().build();
        HttpResponse<String> response_str3 = client.send(request3, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str3.statusCode());  
        HttpRequest request4 = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/" + ID)).DELETE().build();
        HttpResponse<String> response_str4 = client.send(request4, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str4.statusCode());             
    }


    @Test
    public void todo_id_categories_get_test() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/1/categories")).GET().build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());   
    }


    @Test //Bug testing, Should return 404 yet doesn't
    public void erroneous_todo_id_category_get_test() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/500000/categories")).GET().build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertNotEquals(404, response_str.statusCode());        
    } 
    
    @Test
    public void todo_id_categories_post_and_delete_test() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("id", "1");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/1/categories/1")).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode()); 
        HttpRequest request2 = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/1/categories")).GET().build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response2.statusCode());
        JSONObject categories = new JSONObject(response2.body());
        assertEquals("[]", categories.get("categories").toString());      
        HttpRequest request3 = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/1/categories")).POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str = client.send(request3, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response_str.statusCode());
        HttpRequest request4 = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/1/categories")).GET().build();
        HttpResponse<String> response4 = client.send(request4, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response4.statusCode());
    }

    @Test // Same bug, XML and ids
    public void todo_id_categories_post_test_xml() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("id", "1");
        var xml = XML.toString(jSONObject,"todo"); 
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/3/categories")).header("Content-Type" ,"application/xml").POST(HttpRequest.BodyPublishers.ofString(xml)).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, response_str.statusCode());
    }


    @Test // bug concerning the example provided by the documentation
    public void wrong_todo_test() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "test");
        jSONObject.put("doneStatus","false");
        jSONObject.put("description","wrong format due to false being a string");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos")).POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response_str.statusCode());       
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

    @Test
    public void get_categories_xml() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories")).header("Content-Type" ,"application/xml").GET().build();
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
    public void post_categories_xml() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "example title");
        jSONObject.put("description","example description");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories")).header("Content-Type" ,"application/xml").POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response_str.statusCode());
        JSONObject response_json = new JSONObject(response_str.body());
        assertEquals(response_json.get("title"), "example title");
        assertEquals(response_json.get("description"), "example description");
        int newId = Integer.parseInt(response_json.get("id").toString());

        HttpRequest request_del = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/" + newId)).header("Content-Type" ,"application/xml").DELETE().build();
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
    public void get_categories_i_xml() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/2")).header("Content-Type" ,"application/xml").GET().build();
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
    public void post_categories_i_xml() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request_get = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/1")).header("Content-Type" ,"application/xml").GET().build();
        HttpResponse<String> response_str_get = client.send(request_get, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str_get.statusCode());
        JSONObject response_json_get = new JSONObject(response_str_get.body());
        JSONArray response_object = new JSONArray(response_json_get.get("categories").toString());
        String original_title = response_object.getJSONObject(0).get("title").toString();
        String original_desc = response_object.getJSONObject(0).get("description").toString();

        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "different title");
        jSONObject.put("description","different description");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/1")).header("Content-Type" ,"application/xml").POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
        JSONObject response_json = new JSONObject(response_str.body());
        assertEquals(response_json.get("title"), "different title");
        assertEquals(response_json.get("description"), "different description");

        JSONObject reset = new JSONObject();
        reset.put("title", original_title);
        reset.put("description",original_desc);
        HttpRequest request_reset = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/1")).header("Content-Type" ,"application/xml").POST(HttpRequest.BodyPublishers.ofString(reset.toString())).build();
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
    public void put_categories_i_xml() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request_get = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/1")).header("Content-Type" ,"application/xml").GET().build();
        HttpResponse<String> response_str_get = client.send(request_get, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str_get.statusCode());
        JSONObject response_json_get = new JSONObject(response_str_get.body());
        JSONArray response_object = new JSONArray(response_json_get.get("categories").toString());
        String original_title = response_object.getJSONObject(0).get("title").toString();
        String original_desc = response_object.getJSONObject(0).get("description").toString();

        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "even more different title");
        jSONObject.put("description","even more different description");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/1")).header("Content-Type" ,"application/xml").PUT(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
        JSONObject response_json = new JSONObject(response_str.body());
        assertEquals(response_json.get("title"), "even more different title");
        assertEquals(response_json.get("description"), "even more different description");

        JSONObject reset = new JSONObject();
        reset.put("title", original_title);
        reset.put("description",original_desc);
        HttpRequest request_reset = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/1")).header("Content-Type" ,"application/xml").POST(HttpRequest.BodyPublishers.ofString(reset.toString())).build();
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
    public void delete_categories_i_xml() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "new title");
        jSONObject.put("description","new description");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories")).header("Content-Type" ,"application/xml").POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response_str.statusCode());
        JSONObject response_json = new JSONObject(response_str.body());
        assertEquals(response_json.get("title"), "new title");
        assertEquals(response_json.get("description"), "new description");
        int newId = Integer.parseInt(response_json.get("id").toString());

        HttpRequest del_request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/" + newId)).header("Content-Type" ,"application/xml").DELETE().build();
        HttpResponse<String> response_str_del = client.send(del_request, HttpResponse.BodyHandlers.ofString());

        HttpRequest request_get = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories")).header("Content-Type" ,"application/xml").GET().build();
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
    public void get_categories_i_projects_xml() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/2/projects")).header("Content-Type" ,"application/xml").GET().build();
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
    public void post_categories_i_projects_xml() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "new project title");
        jSONObject.put("description","new project description");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/2/projects")).header("Content-Type" ,"application/xml").POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str_post = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject response_json_post = new JSONObject(response_str_post.body());
        assertEquals(201, response_str_post.statusCode());
        assertEquals(response_json_post.get("title"), "new project title");
        assertEquals(response_json_post.get("description"), "new project description");
        int newId = Integer.parseInt(response_json_post.get("id").toString());

        HttpRequest request_get = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/2/projects")).header("Content-Type" ,"application/xml").GET().build();
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

        HttpRequest request_del = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/2/projects/" + newId)).header("Content-Type" ,"application/xml").DELETE().build();
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
    public void delete_categories_i_projects_i_xml() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "newer project title");
        jSONObject.put("description","newer project description");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/2/projects")).header("Content-Type" ,"application/xml").POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str_post = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject response_json_post = new JSONObject(response_str_post.body());
        assertEquals(201, response_str_post.statusCode());
        assertEquals(response_json_post.get("title"), "newer project title");
        assertEquals(response_json_post.get("description"), "newer project description");
        int newId = Integer.parseInt(response_json_post.get("id").toString());

        HttpRequest request_del = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/2/projects/" + newId)).header("Content-Type" ,"application/xml").DELETE().build();
        HttpResponse<String> response_str_del = client.send(request_del, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str_del.statusCode());

        HttpRequest request_get = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/2/projects")).header("Content-Type" ,"application/xml").GET().build();
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
    public void get_categories_i_todos_xml() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/2/todos")).header("Content-Type" ,"application/xml").GET().build();
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
    public void post_categories_i_todos_xml() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "new todo title");
        jSONObject.put("description","new todo description");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/2/todos")).header("Content-Type" ,"application/xml").POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str_post = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject response_json_post = new JSONObject(response_str_post.body());
        assertEquals(201, response_str_post.statusCode());
        assertEquals(response_json_post.get("title"), "new todo title");
        assertEquals(response_json_post.get("description"), "new todo description");
        int newId = Integer.parseInt(response_json_post.get("id").toString());

        HttpRequest request_get = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/2/todos")).header("Content-Type" ,"application/xml").GET().build();
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

        HttpRequest request_del = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/2/todos/" + newId)).header("Content-Type" ,"application/xml").DELETE().build();
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

    @Test
    public void delete_categories_i_todos_i_xml() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "newer todo title");
        jSONObject.put("description","newer todo description");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/2/todos")).header("Content-Type" ,"application/xml").POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str_post = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject response_json_post = new JSONObject(response_str_post.body());
        assertEquals(201, response_str_post.statusCode());
        assertEquals(response_json_post.get("title"), "newer todo title");
        assertEquals(response_json_post.get("description"), "newer todo description");
        int newId = Integer.parseInt(response_json_post.get("id").toString());

        HttpRequest request_del = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/2/todos/" + newId)).header("Content-Type" ,"application/xml").DELETE().build();
        HttpResponse<String> response_str_del = client.send(request_del, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str_del.statusCode());

        HttpRequest request_get = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/categories/2/todos")).header("Content-Type" ,"application/xml").GET().build();
        HttpResponse<String> response_str = client.send(request_get, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
        JSONObject response_json = new JSONObject(response_str.body());
        JSONArray response_object = new JSONArray(response_json.get("todos").toString());
        for(int i = 0; i<response_object.length(); i++){
            assertNotEquals(response_object.getJSONObject(i).get("id").toString(), Integer.toString(newId));
        }
    }
    
    //ABE - PROJECTS
    @Test
    public void get_projects() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects")).GET().build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
        System.out.println(response_str.body());
    }

    @Test
    public void get_projects_xml() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects")).header("Content-Type" ,"application/xml").GET().build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
        System.out.println(response_str.body());
    }

    @Test
    public void project_post() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "test_proj");
        jSONObject.put("completed", true);
        jSONObject.put("active", true);
        jSONObject.put("description","json payload new proj");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects")).POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response_str.statusCode());
        System.out.println(response_str.body());

        JSONObject response_json = new JSONObject(response_str.body());
        assertEquals(response_json.get("title"), "test_proj");
        assertEquals(response_json.get("completed"), "true");
        assertEquals(response_json.get("active"), "true");
        assertEquals(response_json.get("description"), "json payload new proj");
        int newId = Integer.parseInt(response_json.get("id").toString());

        HttpRequest request_del = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/" + newId)).DELETE().build();
        HttpResponse<String> response_str_del = client.send(request_del, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str_del.statusCode());
    }

    @Test
    public void project_post_xml() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "test_proj");
        jSONObject.put("completed", true);
        jSONObject.put("active", true);
        jSONObject.put("description","json payload new proj");
        var xml = XML.toString(jSONObject,"project");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects")).header("Content-Type" ,"application/xml").POST(HttpRequest.BodyPublishers.ofString(xml)).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response_str.statusCode());
        System.out.println(response_str.body());

        JSONObject response_json = new JSONObject(response_str.body());
        assertEquals(response_json.get("title"), "test_proj");
        assertEquals(response_json.get("completed"), "true");
        assertEquals(response_json.get("active"), "true");
        assertEquals(response_json.get("description"), "json payload new proj");
        int newId = Integer.parseInt(response_json.get("id").toString());

        HttpRequest request_del = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/" + newId)).DELETE().build();
        HttpResponse<String> response_str_del = client.send(request_del, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str_del.statusCode());
    }

    @Test
    public void get_projects_id() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1")).GET().build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
        System.out.println(response_str.body());
    }
    @Test

    public void get_projects_id_xml() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1")).header("Content-Type" ,"application/xml").GET().build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
        System.out.println(response_str.body());
    }

    @Test
    public void project_id_post() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "updated_test_proj");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1")).POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
        System.out.println(response_str.body());
        JSONObject response_json = new JSONObject(response_str.body());
        assertEquals(response_json.get("title"), "updated_test_proj");
        

        jSONObject.put("title", "test_proj");
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1")).POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Test
    public void project_id_post_xml() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "updated_test_proj");
        var xml = XML.toString(jSONObject,"project");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1")).header("Content-Type" ,"application/xml").POST(HttpRequest.BodyPublishers.ofString(xml)).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
        System.out.println(response_str.body());

        JSONObject response_json = new JSONObject(response_str.body());
        assertEquals(response_json.get("title"), "updated_test_proj");

        jSONObject.put("title", "test_proj");
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1")).POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Test
    public void project_id_put() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "updated_test_proj");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1")).PUT(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
        System.out.println(response_str.body());

        JSONObject response_json = new JSONObject(response_str.body());
        assertEquals(response_json.get("title"), "updated_test_proj");

        jSONObject.put("title", "test_proj");
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1")).POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Test
    public void project_id_put_xml() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", "updated_test_proj");
        var xml = XML.toString(jSONObject,"project");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1")).header("Content-Type" ,"application/xml").PUT(HttpRequest.BodyPublishers.ofString(xml)).build();        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
        System.out.println(response_str.body());

        JSONObject response_json = new JSONObject(response_str.body());
        assertEquals(response_json.get("title"), "updated_test_proj");

        jSONObject.put("title", "test_proj");
        request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1")).POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Test
    public void project_id_delete() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        
        jSONObject.put("title", "test_proj");
        jSONObject.put("description","json payload new proj");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects")).POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response_str.body());

        JSONObject response_json = new JSONObject(response_str.body());
        int newId = Integer.parseInt(response_json.get("id").toString());

        HttpRequest request_del = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/"+newId)).DELETE().build();
        HttpResponse<String> response_str_del = client.send(request_del, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str_del.statusCode());

    }

    @Test
    public void project_id_delete_xml() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        
        jSONObject.put("title", "test_proj");
        jSONObject.put("description","json payload new proj");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects")).POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response_str.body());

        JSONObject response_json = new JSONObject(response_str.body());
        int newId = Integer.parseInt(response_json.get("id").toString());

        HttpRequest request_del = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/"+newId)).header("Content-Type" ,"application/xml").DELETE().build();
        HttpResponse<String> response_str_del = client.send(request_del, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str_del.statusCode());

    }

    @Test
    public void get_projects_id_task() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1/tasks")).GET().build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
        System.out.println(response_str.body());
    }

    @Test
    public void get_projects_id_task_xml() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1/tasks")).header("Content-Type" ,"application/xml").GET().build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
        System.out.println(response_str.body());
    }

    @Test
    public void project_id_task_post() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("id", "2");
        
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1/tasks")).POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response_str.statusCode());
        System.out.println(response_str.body());
    }

    @Test
    public void project_post_id_task_xml_expected() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("id", "2");
        var xml = XML.toString(jSONObject,"project");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1/tasks")).header("Content-Type" ,"application/xml").POST(HttpRequest.BodyPublishers.ofString(xml)).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertNotEquals(201, response_str.statusCode());
        System.out.println(response_str.body());
    }

    @Test
    public void project_post_id_task_xml_unexpected() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("id", "2");
        var xml = XML.toString(jSONObject,"project");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1/tasks")).header("Content-Type" ,"application/xml").POST(HttpRequest.BodyPublishers.ofString(xml)).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, response_str.statusCode());
        System.out.println(response_str.body());
    }

    @Test
    public void project_id_task_delete() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("id", "3");
        
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1/tasks")).POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response_str.statusCode());
        System.out.println(response_str.body());

        HttpRequest request_del = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1/tasks/3")).DELETE().build();
        HttpResponse<String> response_str_del = client.send(request_del, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str_del.statusCode());
    }

    @Test
    public void project_id_task_delete_xml() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("id", "3");
        
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1/tasks")).POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response_str.statusCode());
        System.out.println(response_str.body());

        HttpRequest request_del = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1/tasks/3")).header("Content-Type" ,"application/xml").DELETE().build();
        HttpResponse<String> response_str_del = client.send(request_del, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str_del.statusCode());
    }

    @Test
    public void get_projects_id_categories() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1/categories")).GET().build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
        System.out.println(response_str.body());
    }

    @Test
    public void get_projects_id_categories_xml() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1/categories")).header("Content-Type" ,"application/xml").GET().build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str.statusCode());
        System.out.println(response_str.body());
    }

    @Test
    public void project_id_categories_post() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("id", "2");
        
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1/categories")).POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response_str.statusCode());
        System.out.println(response_str.body());
    }

    @Test
    public void project_post_id_categories_xml_expected() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("id", "2");
        var xml = XML.toString(jSONObject,"project");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1/categories")).header("Content-Type" ,"application/xml").POST(HttpRequest.BodyPublishers.ofString(xml)).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertNotEquals(201, response_str.statusCode());
        System.out.println(response_str.body());
    }

    @Test
    public void project_post_id_categories_xml_unexpected() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("id", "2");
        var xml = XML.toString(jSONObject,"project");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1/categories")).header("Content-Type" ,"application/xml").POST(HttpRequest.BodyPublishers.ofString(xml)).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, response_str.statusCode());
        System.out.println(response_str.body());
    }

    @Test
    public void project_id_categories_delete() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("id", "2");
        
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1/categories")).POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response_str.statusCode());
        System.out.println(response_str.body());

        HttpRequest request_del = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1/categories/2")).DELETE().build();
        HttpResponse<String> response_str_del = client.send(request_del, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str_del.statusCode());
    }

    @Test
    public void project_id_categories_delete_xml() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("id", "2");
        
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1/categories")).POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response_str.statusCode());
        System.out.println(response_str.body());

        HttpRequest request_del = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/1/categories/2")).header("Content-Type" ,"application/xml").DELETE().build();
        HttpResponse<String> response_str_del = client.send(request_del, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response_str_del.statusCode());
    }

    @Test //Bug testing, Should return 404 yet doesn't
    public void erroneous_project_id_category_get_test() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/projects/500000/categories")).GET().build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertNotEquals(404, response_str.statusCode());
    }

    @Test // Problem in the JSON: the right type is not used at the right place for multiple fields
    public void malformed_json_payload_test() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", true);
        jSONObject.put("doneStatus","false");
        jSONObject.put("description",55);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/2")).POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response_str.statusCode());      
    }

    @Test // malformed XML payload: the tag "todo" is not put at the start
    public void malformed_xml_payload_test() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("title", true);
        jSONObject.put("doneStatus","false");
        jSONObject.put("description",55);
        String xml = XML.toString(jSONObject);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:4567/todos/2")).headers("Content-Type" ,"application/xml").POST(HttpRequest.BodyPublishers.ofString(jSONObject.toString())).build();
        HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response_str.statusCode());   
    }

}