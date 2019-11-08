package github.com.yangfan1224.awesome;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MainTestUnit {

    @Before
    public void beforeTest() {
        System.out.println("beforeTest");
    }

    @Test
    public void testMain() {
        Map<String, Integer> testMap = new HashMap<String, Integer>();
        testMap.put("yangfan", 1);
        testMap.put("liushun", 2);
        testMap.put("ranran3", 3);
        testMap.put("ranran4", 4);
        testMap.put("ranran5", 5);
        testMap.put("ranran6", 6);
        for(Map.Entry<String, Integer> entrySet : testMap.entrySet()) {
            System.out.println(String.format("{\"%s\": %d}", entrySet.getKey(), entrySet.getValue()));
        }
        System.out.println("-----------------------------");
        for (String key: testMap.keySet()) {
            System.out.println(String.format("{\"%s\": %d}", key, testMap.get(key)));
        }
        sun.reflect.annotation.TypeAnnotationParser handler = null;
        System.out.println("testMain Execute");
    }

    @After
    public void afterTest() {
        System.out.println("afterTest");
    }
}
