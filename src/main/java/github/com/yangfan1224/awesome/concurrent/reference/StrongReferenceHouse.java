package github.com.yangfan1224.awesome.concurrent.reference;

import java.util.ArrayList;
import java.util.List;

//-Xms20m -Xmx20m
public class StrongReferenceHouse {
    public static void main(String []args) {
        List<House> houses = new ArrayList<>();
        int i = 0;
        while (true) {
            houses.add(new House());
            System.out.println("i=" + (++i));
        }
    }
}
