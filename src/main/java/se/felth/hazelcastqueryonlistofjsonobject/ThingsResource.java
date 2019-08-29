package se.felth.hazelcastqueryonlistofjsonobject;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastJsonValue;
import com.hazelcast.core.IMap;
import com.hazelcast.query.SqlPredicate;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonStructure;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.UUID;

@Path("things")
public class ThingsResource {

    @Inject
    HazelcastInstance hz;

    IMap<String, HazelcastJsonValue> thingMap;

    @PostConstruct
    public void init() {
        thingMap = hz.getMap("things");
    }

    private void handleOne(JsonObject jo) {
        try (StringWriter w = new StringWriter()) {
            Json.createWriter(w).writeObject(jo);
            HazelcastJsonValue jv = new HazelcastJsonValue(w.toString());
            thingMap.put(UUID.randomUUID().toString(), jv);
        } catch (IOException e) {
            e.printStackTrace();
            throw new WebApplicationException(500);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonStructure post(JsonStructure js) {
        if (js instanceof JsonObject) {
            handleOne((JsonObject)js);
        } else if (js instanceof JsonArray) {
            ((JsonArray)js).stream().map(v -> (JsonObject)v).forEach(this::handleOne);
        }

        return js;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<HazelcastJsonValue> get(@QueryParam("query") String q) {
        return thingMap.values(new SqlPredicate(q));
    }
}
