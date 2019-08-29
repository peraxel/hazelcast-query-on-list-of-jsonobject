package se.felth.hazelcastqueryonlistofjsonobject;

import com.hazelcast.core.HazelcastJsonValue;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.stream.Collectors;

@Provider
public class HzJsonMessageBodyWriter implements MessageBodyWriter<Collection<HazelcastJsonValue>> {

    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public void writeTo(Collection<HazelcastJsonValue> hazelcastJsonValues, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        String things = hazelcastJsonValues.stream().map(v -> v.toString()).collect(Collectors.joining(","));

        outputStream.write("[".getBytes());
        outputStream.write(things.getBytes());
        outputStream.write("]".getBytes());
    }
}
