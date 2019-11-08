package github.com.yangfan1224.awesome.concurrent.reference;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
//-Xms20m -Xmx20m
public class SoftReferenceHouse {
    public static void main(String []args) {
        List<SoftReference> houses = new ArrayList<>();
        int i = 0;
        while (true) {
            SoftReference<House> buyer2 = new SoftReference<>(new House());
            houses.add(buyer2);
            System.out.println("i=" + (++i));
        }
    }
}
