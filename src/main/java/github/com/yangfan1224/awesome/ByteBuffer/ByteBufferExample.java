package github.com.yangfan1224.awesome.ByteBuffer;

import java.nio.ByteBuffer;

public class ByteBufferExample {
    public static void main(String [] args) {
        byte[] bytes = new byte[1024];
        ByteBuffer byteBufferRead = ByteBuffer.wrap(bytes);
        System.out.println(byteBufferRead.capacity());

        ByteBuffer byteBufferAlloc = ByteBuffer.allocate(1024);
        System.out.println(byteBufferAlloc.capacity());
    }
}
