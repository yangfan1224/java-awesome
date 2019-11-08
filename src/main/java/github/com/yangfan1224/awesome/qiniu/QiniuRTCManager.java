package github.com.yangfan1224.awesome.qiniu;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.rtc.RtcAppManager;
import com.qiniu.rtc.RtcRoomManager;
import com.qiniu.util.Auth;

public class QiniuRTCManager {

    private static final String ACCESS_KEY = "mxHbabWQtObqtyYdZeCDnIxDfWFnB0CEv-lMi2L3";
    private static final String SECRET_KEY = "Z7j0AdxukTORowQnlP7uxmiHJvIr1diF23JUHrll";
    private static final String BUCKET_NAME = "foxth";
    private static final String domainOfBucket = "http://pvyojgwyx.bkt.clouddn.com";

    private RtcRoomManager  rrManager;

    private RtcAppManager   raManager;

    public QiniuRTCManager() {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        rrManager = new RtcRoomManager(auth);
        raManager = new RtcAppManager(auth);
    }

    public String getRoomToken() {
        String token = null;
        try {
            token = rrManager.getRoomToken("appid", "roomname", "userid", 1525410499, "user");
            System.out.println(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    public Response createApp(String hub, String title, int maxUsers,
                              boolean noAutoKickUser) {
        Response response = null;
        try {
            response = raManager.createApp(hub, title, maxUsers, noAutoKickUser);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return response;
    }

    public Response getApp(String appId) {
        Response response = null;
        try {
            response = raManager.getApp(appId);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return response;
    }

    public Response deleteApp(String appId) {
        Response response = null;
        try {
            response = raManager.deleteApp(appId);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return response;
    }

    public Response updateApp(String appId, String hub, String title, int maxUsers, boolean noAutoKickUser) {
        Response response = null;
        try {
            response = raManager.updateApp(appId, hub, title, maxUsers, noAutoKickUser);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return response;
    }
}

