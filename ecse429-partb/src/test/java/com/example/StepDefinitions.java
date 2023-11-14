package com.example;

import io.cucumber.java.en.*;

import org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StepDefinitions {
    private int id = 0;
    private JSONObject object;

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
    }

    @When("I delete the project {string}")
    public void project_delete(String project) {
        

    }

    @Then("The project will no longer exist")
    public void non_exist() {

    }

    @Then("I will get an error code {int}")
    public void error(int error) {

    }

}
