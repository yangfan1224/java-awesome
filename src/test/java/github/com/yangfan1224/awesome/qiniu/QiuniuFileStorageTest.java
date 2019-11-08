package github.com.yangfan1224.awesome.qiniu;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.util.StringMap;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class QiuniuFileStorageTest {

    private QiniuFileStorage qiniuFileStorage = new QiniuFileStorage(new Configuration(Zone.zone2()));

    @Before
    public void setUp(){

    }
    @Test
    public void testUpToken() {
        System.out.println(qiniuFileStorage.simpleUpToken());
    }

    @Test
    public void testUploadLocalFile() {
        //{"hash":"FnxKjQnKN2KvYeWVIJQ9wmSU-JQb","key":"FnxKjQnKN2KvYeWVIJQ9wmSU-JQb"}
        Response response = qiniuFileStorage.uploadLocalFile("/home/yangfan/IdeaProjects/java-awesome/upload_test.txt", null,
                qiniuFileStorage.simpleUpToken());
        if (response != null) {
            try {
                System.out.println(response.bodyString());
            } catch (QiniuException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testPrivateDownloadUrl() {
        String downloadUrl = "";
        try {
            downloadUrl = qiniuFileStorage.privateDownloadUrl("FnxKjQnKN2KvYeWVIJQ9wmSU-JQb", 60*30);
            System.out.println(downloadUrl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSelfReturnUpload() {
        String key = UUID.randomUUID().toString();
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        String token = qiniuFileStorage.selfReturnUpToken(key,60*30, putPolicy);
        Response response = qiniuFileStorage.uploadLocalFile(
                "/home/yangfan/IdeaProjects/java-awesome/upload_test.txt", key, token);
        try {
            System.out.println(response.bodyString());
        } catch (QiniuException e) {
            e.printStackTrace();
        }

        String downloadUrl = "";
        try {
            downloadUrl = qiniuFileStorage.privateDownloadUrl(key, 60*30);
            System.out.println(downloadUrl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
