package github.com.yangfan1224.awesome.jni;

public class HelloWorldJNI {
    static {
        System.loadLibrary("native");
    }

    public static void main(String [] args) {
        HelloWorldJNI jniObj = new HelloWorldJNI();
        jniObj.sayHello();
        System.out.println(jniObj.sumInteger(1,2));
        System.out.println(jniObj.sayHelloMe("Yangfan", false));
        UserData userData = jniObj.createUser("YangFan", 1000);
        System.out.println(jniObj.printUserData(userData));
    }

    private native void sayHello();
    private native long sumInteger(int first,  int second);
    private native String sayHelloMe(String name, boolean isFemale);
    public native UserData createUser(String name, double balance);
    public native String printUserData(UserData user);
}
