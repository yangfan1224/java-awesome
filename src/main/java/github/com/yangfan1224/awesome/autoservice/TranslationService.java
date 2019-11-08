package github.com.yangfan1224.awesome.autoservice;

import java.util.Locale;

public interface TranslationService {
    String translate(String message, Locale from, Locale to);
}
