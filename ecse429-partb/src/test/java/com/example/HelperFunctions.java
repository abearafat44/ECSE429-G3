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
        assertEquals(201, response.getStatusLine().getStatusCode());
        HttpGet request2 = new HttpGet(uri+"todos?description="+desc);
        request2.setProtocolVersion(HttpVersion.HTTP_1_0);
        HttpResponse response2 = httpClient.execute(request2);
        String buffer = convertResponseToString(response2);
        JSONObject answer = new JSONObject(buffer);
        httpClient.close();
        return answer;
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

    public static int project_delete(int id) {
        
    }
    //Helper function taken from https://www.baeldung.com/cucumber-rest-api-testing, all credit the author
    private static String convertResponseToString(HttpResponse response) throws IOException {
    InputStream responseStream = response.getEntity().getContent();
    Scanner scanner = new Scanner(responseStream, "UTF-8");
    String responseString = scanner.useDelimiter("\\Z").next();
    scanner.close();
    return responseString;
}


}
