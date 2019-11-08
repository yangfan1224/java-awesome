package github.com.yangfan1224.awesome;

import github.com.yangfan1224.awesome.autoservice.TranslationService;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

public class MainApp {
    public static void main(String [] args) {
        ServiceLoader<TranslationService> loader = ServiceLoader.load(TranslationService.class);
        long count = StreamSupport.stream(loader.spliterator(), false).count();
        System.out.println(count);
    }
}
