package github.com.yangfan1224.awesome;

import github.com.yangfan1224.awesome.autoservice.TranslationService;
import org.junit.jupiter.api.Test;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AutoServiceTest {
    @Test
    void testAutoService() {
        ServiceLoader<TranslationService> loader = ServiceLoader.load(TranslationService.class);
        long count = StreamSupport.stream(loader.spliterator(), false).count();
        assertEquals(2, count);
    }
}
