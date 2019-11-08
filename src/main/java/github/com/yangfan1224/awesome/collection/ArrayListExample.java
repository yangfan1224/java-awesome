package github.com.yangfan1224.awesome.collection;

import java.util.ArrayList;
import java.util.List;

public class ArrayListExample {

    static void testDefaultCapacity() {
        List<Integer> intList = new ArrayList<>();
        long start = System.nanoTime();
        for (int i = 0; i < 1000000; i++ ){
            intList.add(i);
        }
        long end = System.nanoTime();
        System.out.println((end - start)/(1000 * 1000));
    }

    static void testDefinedCapacity() {
        List<Integer> intList = new ArrayList<>(1000000);
        long start = System.nanoTime();
        for (int i = 0; i < 1000000; i++ ){
            intList.add(i);
        }
        long end = System.nanoTime();
        System.out.println((end - start)/(1000 * 1000));
    }

    public static void main(String []args) {
        testDefaultCapacity();
        testDefinedCapacity();
    }
}
