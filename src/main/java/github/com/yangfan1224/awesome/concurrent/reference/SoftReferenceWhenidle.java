package github.com.yangfan1224.awesome.concurrent.reference;

import java.lang.ref.SoftReference;

//-Xms20m -Xmx20m －Xlog:gc
public class SoftReferenceWhenidle {
    public static void main(String []args) {
            House seller = new House();
        SoftReference<House> buyer2 = new SoftReference<House>(seller);
        seller = null;
        while(true){
            System.gc();
            System.runFinalization();
            if (buyer2.get() == null) {
                System.out.println("house is null");
                break;
            } else {
                System.out.println("still here.");
            }
        }
    }
}
