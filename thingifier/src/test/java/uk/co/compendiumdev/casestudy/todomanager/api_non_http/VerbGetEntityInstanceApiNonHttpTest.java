package uk.co.compendiumdev.casestudy.todomanager.api_non_http;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.compendiumdev.casestudy.todomanager.TodoManagerModel;
import uk.co.compendiumdev.thingifier.Thing;
import uk.co.compendiumdev.thingifier.Thingifier;
import uk.co.compendiumdev.thingifier.api.http.HttpApiRequest;
import uk.co.compendiumdev.thingifier.api.http.bodyparser.BodyParser;
import uk.co.compendiumdev.thingifier.api.response.ApiResponse;
import uk.co.compendiumdev.thingifier.domain.definitions.FieldValue;
import uk.co.compendiumdev.thingifier.domain.instances.ThingInstance;

import java.util.*;

public class VerbGetEntityInstanceApiNonHttpTest {


    private Thingifier todoManager;

    Thing todo;
    Thing project;


    // TODO: tests that use the TodoManagerModel were created early and are too complicated - simplify
    // when the thingifier was a prototype and we were building the todo manager at the same
    // time this saved time. Now, the tests are too complicated to maintain because the TodoManagerModel
    // is complex. We should simplify these tests and move them into the actual standAlone
    // projects
    @BeforeEach
    public void createDefinitions() {

        todoManager = TodoManagerModel.definedAsThingifier();

        todo = todoManager.getThingNamed("todo");
        project = todoManager.getThingNamed("project");

    }
    
       /*


    Non HTTP API Based Tests


    */


    @Test
    public void getCanReturnASingleEntityInstance() {

        // add some data
        todo.addInstance(todo.createInstance().setValue("title", "My Title" + System.nanoTime()));
        todo.addInstance(todo.createInstance().setValue("title", "My Title" + System.nanoTime()));


        ThingInstance findThis = todo.createInstance().setValue("title", "My Title" + System.nanoTime());
        todo.addInstance(findThis);

        todo.addInstance(todo.createInstance().setValue("title", "My Title" + System.nanoTime()));


        ApiResponse apiResponse = todoManager.api().get("/todo/" + findThis.getGUID());

        Assertions.assertEquals(200, apiResponse.getStatusCode());
        Assertions.assertFalse(apiResponse.isCollection(),
                "Should be a single item, rather than a collection");
        Assertions.assertTrue(apiResponse.hasABody());

        Assertions.assertEquals(findThis.getValue("title"), apiResponse.getReturnedInstance().getValue("title"));
        Assertions.assertEquals(findThis.getValue("guid"), apiResponse.getReturnedInstance().getValue("guid"));

        Assertions.assertEquals(findThis, apiResponse.getReturnedInstance());
        Assertions.assertEquals(0, apiResponse.getErrorMessages().size());

    }

    @Test
    public void getCanReturnMultipleEntityInstances() {

        // add some data
        todo.addInstance(todo.createInstance().setValue("title", "My Title" + System.nanoTime()));
        todo.addInstance(todo.createInstance().setValue("title", "My Title" + System.nanoTime()));
        todo.addInstance(todo.createInstance().setValue("title", "My Title" + System.nanoTime()));
        todo.addInstance(todo.createInstance().setValue("title", "My Title" + System.nanoTime()));


        ApiResponse apiResponse = todoManager.api().get("/todo");

        Assertions.assertEquals(200, apiResponse.getStatusCode());
        Assertions.assertTrue(apiResponse.isCollection(),
                "Should be a collection");
        Assertions.assertTrue(apiResponse.hasABody());

        Assertions.assertEquals(todo.countInstances(), apiResponse.getReturnedInstanceCollection().size());

        Set<String> guidSet = new HashSet<>();

        for (ThingInstance item : apiResponse.getReturnedInstanceCollection()) {
            guidSet.add(item.getGUID());
            Assertions.assertNotNull(todo.findInstanceByGUID(item.getGUID()));
        }

        Assertions.assertEquals(guidSet.size(), todo.countInstances());
        Assertions.assertEquals(guidSet.size(), apiResponse.getReturnedInstanceCollection().size());

        Assertions.assertEquals(0, apiResponse.getErrorMessages().size());

    }



}
