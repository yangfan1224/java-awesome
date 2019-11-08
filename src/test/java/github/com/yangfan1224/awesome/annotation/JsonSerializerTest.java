package github.com.yangfan1224.awesome.annotation;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonSerializerTest {

    @Test
    public void testSerializer() {
        Person person = new Person("yang", "fan", "34", "wuhan");
        JsonSerializer serializer = new JsonSerializer();
        String jsonString = null;
        try {
            jsonString = serializer.serialize(person);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(
                "{\"personAge\":\"34\",\"firstName\":\"Yang\",\"lastName\":\"Fan\"}",
                jsonString);
    }
}
