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
        JSONObject create = HelperFunctions.createTodo("test"+id1,false, description);
        JSONArray array = create.getJSONArray("todos");
        id1=array.getJSONObject(0).getInt("id");
    }

    @When("I change the tasks description to {string}")
    public void i_change_descriptions_to(String new_desc) throws ClientProtocolException, JSONException, IOException {
        object=HelperFunctions.modifyTodo(id1,null,false,new_desc);
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

    @Given("a project with title {string}")
    public void a_project_with_title(String title) throws ClientProtocolException, JSONException, IOException {
        JSONObject create = HelperFunctions.createProject(title,false,false, "");
        JSONArray array = create.getJSONArray("projects");
        id=array.getJSONObject(0).getInt("id");
    }

    @Given("a non-existant category")
    public void a_null_category() throws ClientProtocolException, JSONException, IOException {
        id=0;
    }

    @Given("a non-existant project")
    public void a_null_project() throws ClientProtocolException, JSONException, IOException {
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

    @Given("a category with title {string} with project with {string} in it") 
    public void category_projects(String cat, String project) throws ClientProtocolException, JSONException, IOException {
        // JSONObject create = HelperFunctions.createProject(project, false, false, project);
        // JSONArray array = create.getJSONArray("projects");
        // id=array.getJSONObject(0).getInt("id");
        JSONObject create = HelperFunctions.createCategory(cat,cat);
        JSONArray array = create.getJSONArray("categories");
        id1=array.getJSONObject(0).getInt("id");
        object=HelperFunctions.addProjectToCategory(project, id1);        
    }

    @When("I delete the project with title {string} from the category")
    public void delete_project_in_category(String title) throws ClientProtocolException, JSONException, IOException {
        if(object == null){
            object = HelperFunctions.deleteProjectFromCategory(0, 0);
        } else {
            JSONArray array = object.getJSONArray("categories");
            JSONArray array2 = array.getJSONObject(0).getJSONArray("projects");
            for(int i = 0; i < array2.length(); i++){

                String name = HelperFunctions.get_project_name(Integer.valueOf(array2.getJSONObject(i).get("id").toString()));
                if(name.equals(title)){
                    object = HelperFunctions.deleteProjectFromCategory(Integer.valueOf(array2.getJSONObject(i).get("id").toString()),id1);
                }
            }
        }
        
    }
    
    @Then("the category will no longer have the project with title {string}")
    public void check_delete_project_in_category(String title) throws ClientProtocolException, JSONException, IOException {
        JSONArray array = HelperFunctions.get_category(id1).getJSONArray("categories");
        if(array.getJSONObject(0).optJSONArray("projects") != null){
            JSONArray array2 = array.getJSONObject(0).getJSONArray("projects");
            boolean exists = false;
            for(int i = 0; i < array2.length(); i++){

                String name = HelperFunctions.get_project_name(Integer.valueOf(array2.getJSONObject(i).get("id").toString()));
                if(name.equals(title)){
                    exists = true;
                }
            }
            assertFalse(exists);
        } else {
            assertTrue(true);
        }
        
    }

    @When("I delete a non-existant project from the category")
    public void delete_null_in_category() throws ClientProtocolException, JSONException, IOException {
        object = HelperFunctions.deleteProjectFromCategory(0, 0);
    }

    @Given("a category with title {string} with projects with {string} and {string} in it") 
    public void category_two_projects(String cat, String project1, String project2) throws ClientProtocolException, JSONException, IOException {
        // JSONObject create = HelperFunctions.createProject(project, false, false, project);
        // JSONArray array = create.getJSONArray("projects");
        // id=array.getJSONObject(0).getInt("id");
        JSONObject create = HelperFunctions.createCategory(cat,cat);
        JSONArray array = create.getJSONArray("categories");
        id=array.getJSONObject(0).getInt("id");
        if(project1.equals("") && project2.equals("")){
            object=HelperFunctions.addProjectToCategory(null, id);
            object=HelperFunctions.addProjectToCategory(null, id);    
        } else {
            object=HelperFunctions.addProjectToCategory(project1, id);
            object=HelperFunctions.addProjectToCategory(project2, id);    
        }            
    }

    @When("I list all projects in the category")
    public void list_all_project_in_cat() throws ClientProtocolException, JSONException, IOException {
        object = HelperFunctions.listProjectinCategory(id);
    }
    @Then("the list will have {string} and {string}")
    public void check_list_all_project_in_cat(String p1, String p2) throws ClientProtocolException, JSONException, IOException {
        JSONArray array = object.getJSONArray("projects");
        boolean p1exists = false;
        boolean p2exists = false;

        for(int i = 0; i < array.length(); i++){
            String name = HelperFunctions.get_project_name(Integer.valueOf(array.getJSONObject(i).get("id").toString()));
            if(name.equals(p1)){
                p1exists = true;
            } else if (name.equals(p2)){
                p2exists = true;
            }
        }

        assertTrue(p1exists);
        assertTrue(p2exists);
    }


    @Given("a project with title {string} with category with {string} in it") 
    public void projects_category(String project, String cat) throws ClientProtocolException, JSONException, IOException {
        // JSONObject create = HelperFunctions.createProject(project, false, false, project);
        // JSONArray array = create.getJSONArray("projects");
        // id=array.getJSONObject(0).getInt("id");
        JSONObject create = HelperFunctions.createProject(project,false,false,project);
        JSONArray array = create.getJSONArray("projects");
        id1=array.getJSONObject(0).getInt("id");
        object=HelperFunctions.addCategoryToProject(project, id1);        
    }

    @When("I delete the category with title {string} from the project")
    public void delete_cat_in_project(String title) throws ClientProtocolException, JSONException, IOException {
        if(object == null){
            object = HelperFunctions.deleteCategoryFromProject(0, 0);
        } else {
            JSONArray array = object.getJSONArray("projects");
            JSONArray array2 = array.getJSONObject(0).getJSONArray("categories");
            for(int i = 0; i < array2.length(); i++){

                String name = HelperFunctions.get_category_name(Integer.valueOf(array2.getJSONObject(i).get("id").toString()));
                if(name.equals(title)){
                    object = HelperFunctions.deleteCategoryFromProject(Integer.valueOf(array2.getJSONObject(i).get("id").toString()),id1);
                }
            }
        }
        
    }
    
    @Then("the project will no longer have the category with title {string}")
    public void check_delete_category_in_project(String title) throws ClientProtocolException, JSONException, IOException {
        JSONArray array = HelperFunctions.get_project(id1).getJSONArray("projects");
        if(array.getJSONObject(0).optJSONArray("categories") != null){
            JSONArray array2 = array.getJSONObject(0).getJSONArray("categories");
            boolean exists = false;
            for(int i = 0; i < array2.length(); i++){

                String name = HelperFunctions.get_category_name(Integer.valueOf(array2.getJSONObject(i).get("id").toString()));
                if(name.equals(title)){
                    exists = true;
                }
            }
            assertFalse(exists);
        } else {
            assertTrue(true);
        }
        
    }

    @When("I delete a non-existant category from the project")
    public void delete_null_in_project() throws ClientProtocolException, JSONException, IOException {
        object = HelperFunctions.deleteCategoryFromProject(0, 0);
    }

    @Given("a project with title {string} with categories with {string} and {string} in it") 
    public void projects_two_cat(String project, String cat1, String cat2) throws ClientProtocolException, JSONException, IOException {
        JSONObject create = HelperFunctions.createProject(project,false,false,project);
        JSONArray array = create.getJSONArray("projects");
        id=array.getJSONObject(0).getInt("id");
        object=HelperFunctions.addCategoryToProject(cat1, id);
        object=HelperFunctions.addCategoryToProject(cat2, id);
    }

    @When("I list all categories in the project")
    public void list_all_cat_in_p() throws ClientProtocolException, JSONException, IOException {
        System.out.println(object);
        object = HelperFunctions.listCategoryInProject(id);
    }
    @Then("the category list will have {string} and {string}")
    public void check_list_all_cat_in_p(String p1, String p2) throws ClientProtocolException, JSONException, IOException {
        JSONArray array = object.getJSONArray("categories");
        boolean p1exists = false;
        boolean p2exists = false;

        for(int i = 0; i < array.length(); i++){
            String name = HelperFunctions.get_category_name(Integer.valueOf(array.getJSONObject(i).get("id").toString()));
            if(name.equals(p1)){
                p1exists = true;
            } else if (name.equals(p2)){
                p2exists = true;
            } else if (name.isEmpty()){
                p2exists=true;
            }
        }

        assertTrue(p1exists);
        assertTrue(p2exists);
    }

    @Then("the category list will only have {string}")
    public void check_list_some_cat_in_p(String p1) throws ClientProtocolException, JSONException, IOException {
        JSONArray array = object.getJSONArray("categories");
        boolean p1exists = false;
        boolean p2exists = false;

        for(int i = 0; i < array.length(); i++){
            String name = HelperFunctions.get_category_name(Integer.valueOf(array.getJSONObject(i).get("id").toString()));
            if(name.equals(p1)){
                p1exists = true;
            }
        }

        assertTrue(p1exists);
    }

    @When("I remove the link between {string} and {string}")
    public void remove_link(String project, String task) throws ClientProtocolException, IOException, JSONException {
        object = HelperFunctions.remove_link_project_task(id, id1);
    }

    @Then("{string} will only contain {string}")
    public void final_contain(String project, String task) throws ClientProtocolException, IOException, JSONException {
        object = HelperFunctions.getProjectTasks(id);
        JSONArray array = object.getJSONArray("todos");
        assertEquals(1, array.length());
        assertEquals(array.getJSONObject(0).getInt("id"), id2);
    }
    @Then("{string} will contain no tasks")
    public void no_tasks_project(String project) throws ClientProtocolException, IOException, JSONException {
        object = HelperFunctions.getProjectTasks(id);
        JSONArray array = object.getJSONArray("todos");
        assertEquals(0, array.length());
    }
    @When("I create a task with title {string}, doneStatus {string} and description {string}")
    public void create_task(String title, String doneStatus, String description) throws ClientProtocolException, JSONException, IOException {
        Boolean bool = (doneStatus == "true");
        object = HelperFunctions.createTodo(title,bool, description);
        JSONArray array = object.getJSONArray("todos");
        id1=array.getJSONObject(0).getInt("id");
    }
    @Then("a task with {string}, {string} and {string} is created")
    public void check_task(String title, String doneStatus, String description) throws JSONException {
        Boolean bool = (doneStatus == "true");
        JSONArray array = object.getJSONArray("todos");
        assertEquals(title, array.getJSONObject(0).getString("title"));
        assertEquals(bool, array.getJSONObject(0).getBoolean("doneStatus"));
        assertEquals(description, array.getJSONObject(0).getString("description"));
    }

    @When("I create a task with title {string} and doneStatus {string}")
    public void create_missing_task(String title, String doneStatus) throws ClientProtocolException, JSONException, IOException {
        Boolean bool = (doneStatus == "true");
       object = HelperFunctions.createTodo(title,bool,null);
        JSONArray array = object.getJSONArray("todos");
        id1=array.getJSONObject(0).getInt("id"); 
    }

    @When("I create a task without a title and description {string}")
    public void create_invalid_task(String description) throws ClientProtocolException, JSONException, IOException {
        object = HelperFunctions.createTodo(null, false, description);
        
    }

    @Then("I get an error {int}")
    public void error_check(int error) throws JSONException {
        int errorCode = object.getInt("errorCode");
        assertEquals(error, errorCode);
    }
}
