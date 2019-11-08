package github.com.yangfan1224.awesome.jvm;

import java.net.URL;

//查看Bootstrap加载器加载的类库
public class BootstrapLoad {

    public static void main(String [] args) {
        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (URL url : urls ) {
            System.out.println(url.toExternalForm());
        }
    }
}
