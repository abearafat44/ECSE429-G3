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
        JSONObject task_project = HelperFunctions.addTasks(1,id);
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


}
