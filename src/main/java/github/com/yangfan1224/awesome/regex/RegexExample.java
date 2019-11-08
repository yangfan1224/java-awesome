package github.com.yangfan1224.awesome.regex;

public class RegexExample {
    public static void main(String []args) {
        String address = "+8618627992565";
        String out = address.replaceAll("[^\\d.]", "");
        System.out.println(out);
    }
}
