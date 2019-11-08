package github.com.yangfan1224.awesome.qiniu;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.util.Json;
import com.qiniu.util.StringMap;
import org.junit.Assert;
import org.junit.Test;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

public class QiniuRTCManagerTest {

    private QiniuRTCManager qiniuRTCManager = new QiniuRTCManager();
    @Test
    public void testGetRoomToken(){
        qiniuRTCManager.getRoomToken();
    }

    @Test
    public void createApp(){
        Response response = qiniuRTCManager.createApp(null, "testapp", 10, false);
        if (response != null) {
            try {
                System.out.println(response.bodyString());
            } catch (QiniuException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * {"appId":"edplvbmfb",
     * "title":"testapp",
     * "uid":1381973298,
     * "hub":"",
     * "maxUsers":4,
     * "noAutoCloseRoom":false,
     * "noAutoCreateRoom":false,
     * "noAutoKickUser":false,
     * "dnsResolve":false,
     * "autoCloseTtl":10,
     * "videoCodec":"H264",
     * "createdAt":"2019-08-13T22:08:19.837760266+08:00",
     * "updatedAt":"2019-08-13T22:08:19.837760266+08:00"}
     */
    @Test
    public void testGetApp() {
        Response response = qiniuRTCManager.getApp("edplvbmfb");
        if (response != null) {
            try {
                System.out.println(response.bodyString());
                Assert.assertEquals("getApp edplvbmfb",
                        "{\"appId\":\"edplvbmfb\",\"title\":\"testapp\",\"hub\":\"\",\"maxUsers\":4,\"noAutoKickUser\":false,\"dnsResolve\":false,\"autoCloseTtl\":10,\"videoCodec\":\"H264\",\"createdAt\":\"2019-08-13T22:08:19.837+08:00\",\"updatedAt\":\"2019-08-13T22:08:19.837+08:00\"}",
                        response.bodyString());
            } catch (QiniuException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testDeleteAppNotFound() {
        Response response = qiniuRTCManager.deleteApp("edplvbmfb1");
        if (response != null) {
            try {
                //System.out.println(response.bodyString());
                Assert.assertEquals("deleteApp edplvbmfb1",
                        "{\"error\":\"app not found\"}",
                        response.bodyString());
            } catch (QiniuException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testDeleteAppNormal() {
        Response response = qiniuRTCManager.createApp(null, "testappDelete", 10, false);
        String appId = "";
        if (response != null) {
            try {
                System.out.println(response.bodyString());
                StringMap appInfo = Json.decode(response.bodyString());
                appId = (String)appInfo.get("appId");
            } catch (QiniuException e) {
                e.printStackTrace();
            }
        }
        response = qiniuRTCManager.deleteApp(appId);
        Assert.assertEquals("delete Failed", response.statusCode, 200);
    }

    @Test
    public void testUpdateApp() {
        Response response = qiniuRTCManager.updateApp("edplvbmfb", null,"testappUpdate", 10, false);
        String title = "";
        if (response != null) {
            try {
                System.out.println(response.bodyString());
                StringMap appInfo = Json.decode(response.bodyString());
                title = (String)appInfo.get("title");
            } catch (QiniuException e) {
                e.printStackTrace();
            }
        }
        Assert.assertEquals("update app Failed", "testappUpdate", title);
    }
}
