package github.com.yangfan1224.awesome.qiniu;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class QiniuFileStorage {
    private static final String ACCESS_KEY = "mxHbabWQtObqtyYdZeCDnIxDfWFnB0CEv-lMi2L3";
    private static final String SECRET_KEY = "Z7j0AdxukTORowQnlP7uxmiHJvIr1diF23JUHrll";
    private static final String BUCKET_NAME = "foxth";
    private static final String domainOfBucket = "http://pvyojgwyx.bkt.clouddn.com";

    private static  final Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);;

    private UploadManager uploadManager;

    public QiniuFileStorage(Configuration cfg) {
        this.uploadManager = uploadManager = new UploadManager(cfg);
    }

    /**
     * 默认的上传回复凭证
     * {"hash":"Ftgm-CkWePC9fzMBTRNmPMhGBcSV","key":"qiniu.jpg"}{"hash":"Ftgm-CkWePC9fzMBTRNmPMhGBcSV","key":"qiniu.jpg"}
     * @return
     */
    public String simpleUpToken() {
        return auth.uploadToken(BUCKET_NAME);
    }

    /**
     * 默认的上传回复凭证
     * {"hash":"Ftgm-CkWePC9fzMBTRNmPMhGBcSV","key":"qiniu.jpg"}{"hash":"Ftgm-CkWePC9fzMBTRNmPMhGBcSV","key":"qiniu.jpg"}
     * @return
     */
    public String overrideUpToken(String key) {
        return auth.uploadToken(BUCKET_NAME, key);
    }

    /**
     * StringMap putPolicy = new StringMap();
     * putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
     * @param expireSeconds
     * @param policy
     * @return
     */
    public String selfReturnUpToken(String key, long expireSeconds, StringMap policy) {
        return auth.uploadToken(BUCKET_NAME, key, expireSeconds, policy);
    }

    public Response uploadLocalFile(String localFilePath, String key, String upToken) {
        Response response = null;
        try {
            response = uploadManager.put(localFilePath, key, upToken);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return response;
    }

    public Response uploadBytes(byte[] uploadBytes, String key, String upToken) {
        Response response = null;
        try {
            response = uploadManager.put(uploadBytes, key, upToken);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return response;
    }

    public String privateDownloadUrl(String fileName, long expireInSeconds) throws UnsupportedEncodingException {
        String encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
        String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
        return auth.privateDownloadUrl(publicUrl, expireInSeconds);
    }

    public String publicDownloadUrl(String domainOfBucket, String fileName) {
        return String.format("%s/%s", domainOfBucket, fileName);
    }
}
