package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreProtocolPNames;
import org.json.*;

public class HelperFunctions {

    private static Process process;
    private static String uri = "http://localhost:4567/" ;

    public static Process startAPI() {
        Runtime rt = Runtime.getRuntime();
        try{
            process = rt.exec("java -jar runTodoManagerRestAPI-1.5.5.jar");
        }
        catch (IOException error){
            error.printStackTrace();
        }
        return process;
    }

    public static void stopAPI() {
        if (process != null) process.destroy();
    }
    

    public static JSONObject createTodo(String name, boolean done, String desc) throws JSONException, ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(uri + "todos" );
        JSONObject payload = new JSONObject();
        if(name != null) payload.put("title", name);
        if(done == true || done == false) payload.put("doneStatus", done);
        if(desc != null) payload.put("description", desc);
        StringEntity entity = new StringEntity(payload.toString());
        request.addHeader("content-type", "application/json");
        request.setEntity(entity);
        HttpResponse response = httpClient.execute(request);
        if(response.getStatusLine().getStatusCode() == 400) {
            JSONObject answer = new JSONObject();
            answer.put("errorCode", response.getStatusLine().getStatusCode());
            return answer;
        }
        if(desc != null){
            HttpGet request2 = new HttpGet(uri+"todos?description="+desc);
        request2.setProtocolVersion(HttpVersion.HTTP_1_0);
        HttpResponse response2 = httpClient.execute(request2);
        String buffer = convertResponseToString(response2);
        JSONObject answer = new JSONObject(buffer);
        httpClient.close();
        return answer;
        }
        else{
           HttpGet request2 = new HttpGet(uri+"todos?title="+name);
        request2.setProtocolVersion(HttpVersion.HTTP_1_0);
        HttpResponse response2 = httpClient.execute(request2);
        String buffer = convertResponseToString(response2);
        JSONObject answer = new JSONObject(buffer);
        httpClient.close();
        return answer; 
        }
        
    }

    public static JSONObject modifyTodo(int id,String name, boolean done, String desc) throws JSONException, ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(uri + "todos/" + id);
        JSONObject payload = new JSONObject();
        if(name != null) payload.put("title", name);
        if(done == true || done == false) payload.put("doneStatus", done);
        if(desc != null) payload.put("description", desc);
        StringEntity entity = new StringEntity(payload.toString());
        request.setEntity(entity);
        HttpResponse response = null;
        try{
            response = httpClient.execute(request);
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        if(response.getStatusLine().getStatusCode() == 404) {
            JSONObject error = new JSONObject();
            error.put("error",response.getStatusLine().getStatusCode());
            return error;
        }
        String buffer = convertResponseToString(response);
        JSONObject answer = new JSONObject(buffer);
        httpClient.close();
        return answer;
    }

    public static JSONObject createProject(String name, boolean complete, boolean active,String description) throws JSONException, ClientProtocolException, IOException {
      CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(uri + "projects" );
        JSONObject payload = new JSONObject();
        if(name != null) payload.put("title", name);
        if(complete == true || complete == false) payload.put("completed", complete);
        if(active == true || active == false) payload.put("active", active);
        if(description != null) payload.put("description", description);
        StringEntity entity = new StringEntity(payload.toString());
        request.addHeader("content-type", "application/json");
        request.setEntity(entity);
        HttpResponse response = httpClient.execute(request);
        assertEquals(201, response.getStatusLine().getStatusCode());
        HttpGet request2 = new HttpGet(uri+"projects?title="+name);
        request2.setProtocolVersion(HttpVersion.HTTP_1_0);
        HttpResponse response2 = httpClient.execute(request2);
        String buffer = convertResponseToString(response2);
        JSONObject answer = new JSONObject(buffer);
        httpClient.close();
        return answer;  
    }

    public static int project_delete(int id) throws ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete request = new HttpDelete(uri+"projects/" + id);
        HttpResponse response = httpClient.execute(request);
        int status = response.getStatusLine().getStatusCode();
        return status;

    }

    public static void addTasks(int task_id, int project_id) throws JSONException, ClientProtocolException, IOException{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(uri + "projects/" + project_id + "/tasks");
        JSONObject payload = new JSONObject();
        payload.put("id",String.valueOf(task_id));
        StringEntity entity = new StringEntity(payload.toString());
        request.setEntity(entity);
        HttpResponse response = httpClient.execute(request);
        assertEquals(201, response.getStatusLine().getStatusCode()); 
    }

    public static JSONObject getIncompleteTasksProject(int id) throws ClientProtocolException, IOException, JSONException {
      CloseableHttpClient httpClient = HttpClients.createDefault();
      HttpGet request = new HttpGet(uri + "projects/" + id + "/tasks?doneStatus=false");
      HttpResponse response = httpClient.execute(request);
      if(response.getStatusLine().getStatusCode() == 404) {
        JSONObject answer = new JSONObject();
        answer.put("errorCode", response.getStatusLine().getStatusCode());
        return answer;
      }
      String buffer = convertResponseToString(response);
      JSONObject answer = new JSONObject(buffer);
      httpClient.close();
      return answer; 
    }

    public static JSONObject createCategory(String title, String description) throws JSONException, ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
          HttpPost request = new HttpPost(uri + "categories" );
          JSONObject payload = new JSONObject();
          if(title != null) payload.put("title", title);
          if(description != null) payload.put("description", description);
          StringEntity entity = new StringEntity(payload.toString());
          request.addHeader("content-type", "application/json");
          request.setEntity(entity);
          HttpResponse response = httpClient.execute(request);
          assertEquals(201, response.getStatusLine().getStatusCode());
          HttpGet request2 = new HttpGet(uri+"categories?title="+title);
          request2.setProtocolVersion(HttpVersion.HTTP_1_0);
          HttpResponse response2 = httpClient.execute(request2);
          String buffer = convertResponseToString(response2);
          JSONObject answer = new JSONObject(buffer);
          httpClient.close();
          return answer;  
      }

      public static JSONObject addProjectToCategory(String title, int catId) throws JSONException, ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
          HttpPost request = new HttpPost(uri + "categories/" + catId + "/projects");
          JSONObject payload = new JSONObject();
          if(title != null) payload.put("title", title);
          StringEntity entity = new StringEntity(payload.toString());
          request.addHeader("content-type", "application/json");
          request.setEntity(entity);
          HttpResponse response = httpClient.execute(request);
          //assertEquals(201, response.getStatusLine().getStatusCode());
          if(response.getStatusLine().getStatusCode() == 201){
            HttpGet request2 = new HttpGet(uri+"categories/"+catId);
            request2.setProtocolVersion(HttpVersion.HTTP_1_0);
            HttpResponse response2 = httpClient.execute(request2);
            String buffer = convertResponseToString(response2);
            JSONObject answer = new JSONObject(buffer);
            httpClient.close();
            return answer;  
          } else {
            try{
                response = httpClient.execute(request);
            }
            catch (Exception e) {
                System.out.println(e.toString());
            }
            if(response.getStatusLine().getStatusCode() == 404) {
                JSONObject error = new JSONObject();
                error.put("error",response.getStatusLine().getStatusCode());
                return error;
            }
            String buffer = convertResponseToString(response);
            JSONObject answer = new JSONObject(buffer);
            httpClient.close();
            return answer;
          }
      }

      public static JSONObject addCategoryToProject(String title, int pId) throws JSONException, ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
          HttpPost request = new HttpPost(uri + "projects/" + pId + "/categories");
          JSONObject payload = new JSONObject();
          if(title != null) payload.put("title", title);
          StringEntity entity = new StringEntity(payload.toString());
          request.addHeader("content-type", "application/json");
          request.setEntity(entity);
          HttpResponse response = httpClient.execute(request);
          //assertEquals(201, response.getStatusLine().getStatusCode());
          if(response.getStatusLine().getStatusCode() == 201){
            HttpGet request2 = new HttpGet(uri+"projects/"+pId);
            request2.setProtocolVersion(HttpVersion.HTTP_1_0);
            HttpResponse response2 = httpClient.execute(request2);
            String buffer = convertResponseToString(response2);
            JSONObject answer = new JSONObject(buffer);
            httpClient.close();
            return answer;  
          } else {
            try{
                response = httpClient.execute(request);
            }
            catch (Exception e) {
                System.out.println(e.toString());
            }
            if(response.getStatusLine().getStatusCode() == 404) {
                JSONObject error = new JSONObject();
                error.put("error",response.getStatusLine().getStatusCode());
                return error;
            }
            String buffer = convertResponseToString(response);
            JSONObject answer = new JSONObject(buffer);
            httpClient.close();
            return answer;
          }
      }

      public static String get_project_name(int id)throws JSONException, ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
          HttpGet request = new HttpGet(uri + "projects/" + id);
          JSONObject payload = new JSONObject();
          StringEntity entity = new StringEntity(payload.toString());
          request.addHeader("content-type", "application/json");
          HttpResponse response = httpClient.execute(request);
          String buffer = convertResponseToString(response);
          JSONObject answer = new JSONObject(buffer);
          JSONArray array = answer.getJSONArray("projects");
          return array.getJSONObject(0).getString("title");
      }

    
      public static JSONObject remove_link_project_task(int project, int task) throws ClientProtocolException, IOException, JSONException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete request = new HttpDelete(uri + "projects/" + project + "/tasks/" + task);
        HttpResponse response = httpClient.execute(request);
        if(response.getStatusLine().getStatusCode() == 404) {
            JSONObject answer = new JSONObject();
            answer.put("errorCode", response.getStatusLine().getStatusCode());
            return answer;
        }
        else {
            return null;
        }
      }

      public static JSONObject getProjectTasks(int id) throws ClientProtocolException, IOException, JSONException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
      HttpGet request = new HttpGet(uri + "projects/" + id + "/tasks");
      HttpResponse response = httpClient.execute(request);
      if(response.getStatusLine().getStatusCode() == 404) {
        JSONObject answer = new JSONObject();
        answer.put("errorCode", response.getStatusLine().getStatusCode());
        return answer;
      }
      String buffer = convertResponseToString(response);
      JSONObject answer = new JSONObject(buffer);
      httpClient.close();
      return answer; 
      }
      public static String get_category_name(int id)throws JSONException, ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
          HttpGet request = new HttpGet(uri + "categories/" + id);
          JSONObject payload = new JSONObject();
          StringEntity entity = new StringEntity(payload.toString());
          request.addHeader("content-type", "application/json");
          HttpResponse response = httpClient.execute(request);
          String buffer = convertResponseToString(response);
          JSONObject answer = new JSONObject(buffer);
          JSONArray array = answer.getJSONArray("categories");
          return array.getJSONObject(0).getString("title");
      }

      public static JSONObject deleteProjectFromCategory(int pId, int catId) throws JSONException, ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
          HttpDelete request = new HttpDelete(uri + "categories/" + catId + "/projects/" + pId);
          JSONObject payload = new JSONObject();
          StringEntity entity = new StringEntity(payload.toString());
          request.addHeader("content-type", "application/json");
          HttpResponse response = httpClient.execute(request);
          //assertEquals(201, response.getStatusLine().getStatusCode());
          if(response.getStatusLine().getStatusCode() == 201){
            HttpGet request2 = new HttpGet(uri+"categories/"+catId);
            request2.setProtocolVersion(HttpVersion.HTTP_1_0);
            HttpResponse response2 = httpClient.execute(request2);
            String buffer = convertResponseToString(response2);
            JSONObject answer = new JSONObject(buffer);
            httpClient.close();
            return answer;  
          } else {
            try{
                response = httpClient.execute(request);
            }
            catch (Exception e) {
                System.out.println(e.toString());
            }
            if(response.getStatusLine().getStatusCode() == 404) {
                JSONObject error = new JSONObject();
                error.put("error",response.getStatusLine().getStatusCode());
                return error;
            }
            String buffer = convertResponseToString(response);
            JSONObject answer = new JSONObject(buffer);
            httpClient.close();
            return answer;
          }
      }

      public static JSONObject deleteCategoryFromProject(int catId, int pId) throws JSONException, ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
          HttpDelete request = new HttpDelete(uri + "projects/" + pId + "/categories/" + catId);
          JSONObject payload = new JSONObject();
          StringEntity entity = new StringEntity(payload.toString());
          request.addHeader("content-type", "application/json");
          HttpResponse response = httpClient.execute(request);
          //assertEquals(201, response.getStatusLine().getStatusCode());
          if(response.getStatusLine().getStatusCode() == 201){
            HttpGet request2 = new HttpGet(uri+"projects/"+pId);
            request2.setProtocolVersion(HttpVersion.HTTP_1_0);
            HttpResponse response2 = httpClient.execute(request2);
            String buffer = convertResponseToString(response2);
            JSONObject answer = new JSONObject(buffer);
            httpClient.close();
            return answer;  
          } else {
            try{
                response = httpClient.execute(request);
            }
            catch (Exception e) {
                System.out.println(e.toString());
            }
            if(response.getStatusLine().getStatusCode() == 404) {
                JSONObject error = new JSONObject();
                error.put("error",response.getStatusLine().getStatusCode());
                return error;
            }
            String buffer = convertResponseToString(response);
            JSONObject answer = new JSONObject(buffer);
            httpClient.close();
            return answer;
          }
      }

      public static JSONObject get_category(int id)throws JSONException, ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
          HttpGet request = new HttpGet(uri + "categories/" + id);
          JSONObject payload = new JSONObject();
          StringEntity entity = new StringEntity(payload.toString());
          request.addHeader("content-type", "application/json");
          HttpResponse response = httpClient.execute(request);
          String buffer = convertResponseToString(response);
          JSONObject answer = new JSONObject(buffer);
          return answer;
      }

      public static JSONObject get_project(int id)throws JSONException, ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
          HttpGet request = new HttpGet(uri + "projects/" + id);
          JSONObject payload = new JSONObject();
          StringEntity entity = new StringEntity(payload.toString());
          request.addHeader("content-type", "application/json");
          HttpResponse response = httpClient.execute(request);
          String buffer = convertResponseToString(response);
          JSONObject answer = new JSONObject(buffer);
          return answer;
      }

      public static JSONObject listProjectinCategory(int catId) throws JSONException, ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
          HttpGet request = new HttpGet(uri + "categories/" + catId + "/projects");

          JSONObject payload = new JSONObject();
          StringEntity entity = new StringEntity(payload.toString());
          request.addHeader("content-type", "application/json");
          HttpResponse response = httpClient.execute(request);
          //assertEquals(201, response.getStatusLine().getStatusCode());
          if(response.getStatusLine().getStatusCode() == 200){
            String buffer = convertResponseToString(response);
            JSONObject answer = new JSONObject(buffer);
            httpClient.close();

            return answer;  
          } else {
            try{
                response = httpClient.execute(request);
            }
            catch (Exception e) {
                System.out.println(e.toString());
            }
            if(response.getStatusLine().getStatusCode() == 404) {
                JSONObject error = new JSONObject();
                error.put("error",response.getStatusLine().getStatusCode());
                return error;
            }
            String buffer = convertResponseToString(response);
            JSONObject answer = new JSONObject(buffer);
            httpClient.close();
            return answer;
          }
      }

      public static JSONObject listCategoryInProject(int pId) throws JSONException, ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
          HttpGet request = new HttpGet(uri + "projects/" + pId + "/categories");

          JSONObject payload = new JSONObject();
          StringEntity entity = new StringEntity(payload.toString());
          request.addHeader("content-type", "application/json");
          HttpResponse response = httpClient.execute(request);
          //assertEquals(201, response.getStatusLine().getStatusCode());
          if(response.getStatusLine().getStatusCode() == 200){
            String buffer = convertResponseToString(response);
            JSONObject answer = new JSONObject(buffer);
            httpClient.close();

            return answer;  
          } else {
            try{
                response = httpClient.execute(request);
            }
            catch (Exception e) {
                System.out.println(e.toString());
            }
            if(response.getStatusLine().getStatusCode() == 404) {
                JSONObject error = new JSONObject();
                error.put("error",response.getStatusLine().getStatusCode());
                return error;
            }
            String buffer = convertResponseToString(response);
            JSONObject answer = new JSONObject(buffer);
            httpClient.close();
            return answer;
          }
      }

    // public static int project_delete(int id) {
        
    // }
    //Helper function taken from https://www.baeldung.com/cucumber-rest-api-testing, all credit the author
    private static String convertResponseToString(HttpResponse response) throws IOException {
    InputStream responseStream = response.getEntity().getContent();
    Scanner scanner = new Scanner(responseStream, "UTF-8");
    String responseString = scanner.useDelimiter("\\Z").next();
    scanner.close();
    return responseString;
}


}
