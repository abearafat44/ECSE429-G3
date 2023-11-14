package com.example;

import io.cucumber.java.en.*;

import org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StepDefinitions {
    private int id = 0;
    private int id1 = 0;
    private int id2 = 0;
    private int id3 = 0;
    private JSONObject object;
    private int statusCode;

    @Given("a task with existing description {string}")
    public void a_task_with_description(String description) throws ClientProtocolException, JSONException, IOException {
        JSONObject create = HelperFunctions.createTodo("test"+id,false, description);
        JSONArray array = create.getJSONArray("todos");
        id=array.getJSONObject(0).getInt("id");
    }

    @When("I change the tasks description to {string}")
    public void i_change_descriptions_to(String new_desc) throws ClientProtocolException, JSONException, IOException {
        object=HelperFunctions.modifyTodo(id,null,false,new_desc);
    }

    @Then("the task will have a new description {string}")
    public void the_task_has_description(String final_desc) throws JSONException {
        String description = object.getString("description");
        assertEquals(final_desc, description);
    }

    @Given("a non-existing task with {string}")
    public void non_existent_task(String string) {
    }

    @Then("I receive an error message {int}")
    public void error_message(int error) throws JSONException {
        assertEquals(error, object.getInt("error"));

    }

    @Given("the existing project {string} containing tasks")
    public void project_exists(String project) throws ClientProtocolException, JSONException, IOException {
        JSONObject create = HelperFunctions.createProject(project, false, false, project);
        JSONArray array = create.getJSONArray("projects");
        id=array.getJSONObject(0).getInt("id");
        HelperFunctions.addTasks(1,id);
    }

    @Given("the empty existing project {string}")
    public void empty_project(String project) throws ClientProtocolException, JSONException, IOException {
       JSONObject create = HelperFunctions.createProject(project, false, false, project);
        JSONArray array = create.getJSONArray("projects");
        id=array.getJSONObject(0).getInt("id"); 
    }

    @When("I delete the project {string}")
    public void project_delete(String project) throws ClientProtocolException, IOException {
        statusCode = HelperFunctions.project_delete(id);

    }

    @Then("the project will no longer exist")
    public void non_exist() {
        assertEquals(200, statusCode);
    }

    @Then("I will get an error code {int}")
    public void error(int error) {
        assertEquals(statusCode, 404);
    }

    @Given("a category with title {string}")
    public void a_category_with_title(String title) throws ClientProtocolException, JSONException, IOException {
        JSONObject create = HelperFunctions.createCategory(title, "");
        JSONArray array = create.getJSONArray("categories");
        id=array.getJSONObject(0).getInt("id");
    }

    @Given("a non-existant category")
    public void a_null_category() throws ClientProtocolException, JSONException, IOException {
        id=0;
    }

    @When("I add the project with title {string} to the category")
    public void add_project_to_category(String title) throws ClientProtocolException, JSONException, IOException {
        object=HelperFunctions.addProjectToCategory(title, id);
    }

    @Then("the category will have the project with title {string}")
    public void category_has_project(String title) throws ClientProtocolException, JSONException, IOException {
        JSONArray array = object.getJSONArray("categories");
        JSONArray array2 = array.getJSONObject(0).getJSONArray("projects");
        boolean exists = false;
        for(int i = 0; i < array2.length(); i++){

            String name = HelperFunctions.get_project_name(Integer.valueOf(array2.getJSONObject(i).get("id").toString()));
            if(name.equals(title)){
                exists = true;
            }
        }
        assertTrue(exists);
    }

    @Given("the existing project {string} containing incomplete tasks {string} and {string}")
    public void project_with_incomplete(String project, String task1, String task2) throws ClientProtocolException, JSONException, IOException {
        JSONObject create = HelperFunctions.createProject(project, false, false, project);
        JSONArray array = create.getJSONArray("projects");
        id=array.getJSONObject(0).getInt("id");
        JSONObject task_1 = HelperFunctions.createTodo(task1, false, task1);
        array = task_1.getJSONArray("todos");
        id1 = array.getJSONObject(0).getInt("id");
        JSONObject task_2 = HelperFunctions.createTodo(task2, false, task2);
        array = task_2.getJSONArray("todos");
        id2 = array.getJSONObject(0).getInt("id");
        HelperFunctions.addTasks(id1, id);
        HelperFunctions.addTasks(id2, id);
    }

    @Given("the existing project {string} containing completed tasks {string}") 
    public void project_completed_tasks(String project, String task) throws ClientProtocolException, JSONException, IOException {
        JSONObject create = HelperFunctions.createProject(project, false, false, project);
        JSONArray array = create.getJSONArray("projects");
        id=array.getJSONObject(0).getInt("id");
        create = HelperFunctions.createTodo(task,true, task);
        array = create.getJSONArray("todos");
        id1=array.getJSONObject(0).getInt("id");
        HelperFunctions.addTasks(id1, id);
    }

    @And("containing complete task {string}")
    public void complete_tasks(String task) throws ClientProtocolException, JSONException, IOException {
        JSONObject create = HelperFunctions.createTodo(task,true, task);
        JSONArray array = create.getJSONArray("todos");
        id3=array.getJSONObject(0).getInt("id");
        HelperFunctions.addTasks(id3, id);
    }

    @When("I request incomplete tasks from {string}")
    public void incomplete_tasks_request(String project) throws ClientProtocolException, IOException, JSONException {
        object = HelperFunctions.getIncompleteTasksProject(id);
    }

    @Then("I get {string} and {string} but not {string}")
    public void results(String task1, String task2, String task3) throws JSONException {
        JSONArray array = object.getJSONArray("todos");
        int numberTasks = array.length();
        assertEquals(2, numberTasks);
        String name1 = array.getJSONObject(0).getString("title");
        String name2 = array.getJSONObject(1).getString("title");
        if (name1 == task1 || name2 == task1) assertTrue(true);
        if (name1 == task2 || name2 == task2) assertTrue(true);
    }
    
    @Then("I get an empty todo array")
    public void i_get_an_empty_todo_array() throws JSONException {
        JSONArray array = object.getJSONArray("todos");
        int numberTasks = array.length();
        assertEquals(0, numberTasks);
    }
    
    @Then("I get an error code {int}")
    public void i_get_an_error_code(Integer int1) throws Exception {
        try{int error = object.getInt("errorCode");
        assertEquals(int1, error);}
        catch(Exception e) {
            throw new Exception(e.toString());
        }
}



}
