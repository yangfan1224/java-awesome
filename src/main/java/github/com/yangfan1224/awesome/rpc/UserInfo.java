package github.com.yangfan1224.awesome.rpc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Java序列化编码存储空间与效率验证
 */
public class UserInfo implements Serializable {
    private String userName;
    private int userID;

    public UserInfo buildUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserInfo buildUserID(int userID) {
        this.userID = userID;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public byte[] codeC(ByteBuffer buffer) {
        buffer.clear();
        byte[] value = this.userName.getBytes();
        buffer.putInt(value.length);
        buffer.put(value);
        buffer.putInt(this.userID);
        buffer.flip();
        value = null;
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);
        return result;
    }

    public static void codecSize() throws IOException {
        System.out.println("codecSize : ");
        UserInfo userInfo = new UserInfo();
        userInfo.buildUserID(100).buildUserName("yangfan");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(userInfo);
        os.flush();
        os.close();
        byte[] b = bos.toByteArray();
        System.out.println("The jdk serializable length is : " + b.length);
        bos.close();
        System.out.println("-----------------------------------");
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        System.out.println("The byte array serializable length is : " + userInfo.codeC(buffer).length);
    }

    public static void benchmark() throws IOException {
        System.out.println("benchmark : ");
        UserInfo info = new UserInfo();
        info.buildUserID(1000).buildUserName("yangfan");
        int loop = 1000000;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        long startTime = System.nanoTime();
        for (int i = 0; i < loop; i++) {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(info);
            os.flush();
            os.close();
            byte[] b = bos.toByteArray();
            bos.close();
        }
        System.out.println("The jdk serializable cost time is : " + (System.nanoTime() - startTime)/1000000 + " ms");
        System.out.println("-----------------------------------------------");
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        startTime = System.nanoTime();
        for (int i = 0; i < loop; i++) {
            byte[] b = info.codeC(buffer);
        }
        System.out.println("The byte array serializable cost time is : " + (System.nanoTime() - startTime)/1000000 + " ms");
    }
    public static void main(String[] args) throws IOException {
        codecSize();
        benchmark();
    }
}
