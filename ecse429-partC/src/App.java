import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;

public class App {
     private static int loop_number = 10;
     private static String uri = "http://localhost:4567/";
     static OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
     private static Process process;

    public static void main(String[] args) throws Exception {

        for(int i = 1; i < loop_number; i++) {
            process = startAPI();
            Thread.sleep(2000);
            createElementsTodos(i);
            int timeAdd = 0;
            int timeModify = 0;
            int timeDelete = 0;
            double cpuLoad = 0;
            long memSize = 0;
            int id = 2 + i;
            timeAdd = tests.createTodo();
            timeModify = tests.modifyTodo(id);
            timeDelete = tests.deleteTodo(id);
            cpuLoad = osBean.getProcessCpuLoad();
            memSize = osBean.getFreeMemorySize();
            if (timeAdd < 0) {
                timeAdd = 1000 + timeAdd;
            }
            else if (timeModify < 0) {
                timeModify = 1000 + timeModify;
            }
            else if (timeDelete < 0) {
                timeDelete = 1000 + timeDelete;
            }
            System.out.println("Time to add todo with " + i + " extra elements: " + (timeAdd/1000000));
            System.out.println("Time to modify todo with " + i + " extra elements: " + (timeModify/1000000));
            System.out.println("Time to delete todo with " + i + " extra elements: " + (timeDelete/1000000));
            System.out.println("CPU load of operations when " + i + " extra elements: " + (cpuLoad*100) + "%");
            System.out.println("Available memory with " + i + " extra elements: " + memSize + "\n");            
            process.destroy();
        }

        for(int i = 1; i < loop_number; i++) {
            process = startAPI();
            Thread.sleep(2000);
            createElementsCategories(i);
            int timeAdd = 0;
            int timeModify = 0;
            int timeDelete = 0;
            double cpuLoad = 0;
            long memSize = 0;
            int id = 2 + i;
            timeAdd = tests.createCategories();
            timeModify = tests.modifyCategories(id);
            timeDelete = tests.deleteCategories(id);
            cpuLoad = osBean.getProcessCpuLoad();
            if(cpuLoad == 0.0) cpuLoad = osBean.getProcessCpuLoad();
            memSize = osBean.getFreeMemorySize();
            if (timeAdd < 0) {
                timeAdd = 1000 + timeAdd;
            }
            if (timeModify < 0) {
                timeModify = 1000 + timeModify;
            }
            if (timeDelete < 0) {
                timeDelete = 1000 + timeDelete;
            }
            System.out.println("Time to add category with " + i + " extra elements: " + (timeAdd/1000000));
            System.out.println("Time to modify category with " + i + " extra elements: " + (timeModify/1000000));
            System.out.println("Time to delete category with " + i + " extra elements: " + (timeDelete/1000000));
            System.out.println("CPU load of operations when " + i + " extra elements: " + (cpuLoad*100) + "%");
            System.out.println("Available memory with " + i + " extra elements: " + memSize + "\n");            
            process.destroy();
        }

        for(int i = 1; i < loop_number; i++) {
            process = startAPI();
            Thread.sleep(2000);
            createElementsProjects(i);
            int timeAdd = 0;
            int timeModify = 0;
            int timeDelete = 0;
            double cpuLoad = 0;
            long memSize = 0;
            int id = 2 + i;
            timeAdd = tests.createProjects();
            timeModify = tests.modifyProjects(id);
            timeDelete = tests.deleteProjects(id);
            cpuLoad = osBean.getProcessCpuLoad();
            memSize = osBean.getFreeMemorySize();
            if (timeAdd < 0) {
                timeAdd = 1000 + timeAdd;
            }
            if (timeModify < 0) {
                timeModify = 1000 + timeModify;
            }
            if (timeDelete < 0) {
                timeDelete = 1000 + timeDelete;
            }
            System.out.println("Time to add category with " + i + " extra elements: " + (timeAdd/1000000));
            System.out.println("Time to modify category with " + i + " extra elements: " + (timeModify/1000000));
            System.out.println("Time to delete category with " + i + " extra elements: " + (timeDelete/1000000));
            System.out.println("CPU load of operations when " + i + " extra elements: " + (cpuLoad*100) + "%");
            System.out.println("Available memory with " + i + " extra elements: " + memSize + "\n");            
            process.destroy();
        }



    }


    private static void createElementsTodos(int number) throws IOException, InterruptedException {
        String title = "Title #";
        Boolean doneStatus = false;
        String description = "Description #";

        for(int i = 1; i < number; i++) {
            todoDummy(title + i, doneStatus, description + i);
        }
        System.out.println("Todos set up: "+number);
    }

    private static void createElementsProjects(int number) throws IOException, InterruptedException {
        String title = "Title #";
        Boolean doneStatus = false;
        String description = "Description #";

        for(int i = 0; i < number; i++) {
            projectDummy(title, doneStatus, description);
        }
        System.out.println("Projects set up: "+number);
    }

    private static void createElementsCategories(int number) throws IOException, InterruptedException {
        String title = "Title #";
        String description = "Description #";

        for(int i = 0; i < number; i++) {
            categoriesDummy(title, description);
        }
        System.out.println("Categories set up: "+number);
    }


    private static void todoDummy(String title, Boolean doneStatus, String description) throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    JSONObject payload = new JSONObject();
    payload.put("title", title);
    payload.put("doneStatus",doneStatus);
    payload.put("description", description);
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri+"todos")).POST(HttpRequest.BodyPublishers.ofString(payload.toString())).build();
    HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals(201, response_str.statusCode());
    }

    private static void projectDummy(String title, Boolean active, String description) throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    JSONObject payload = new JSONObject();
    payload.put("title", title);
    payload.put("active",active);
    payload.put("description", description);
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri+"projects")).POST(HttpRequest.BodyPublishers.ofString(payload.toString())).build();
    HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals(201, response_str.statusCode());
    }

    private static void categoriesDummy(String title, String description) throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    JSONObject payload = new JSONObject();
    payload.put("title", title);
    payload.put("description", description);
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri+"categories")).POST(HttpRequest.BodyPublishers.ofString(payload.toString())).build();
    HttpResponse<String> response_str = client.send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals(201, response_str.statusCode());
    }

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
}
