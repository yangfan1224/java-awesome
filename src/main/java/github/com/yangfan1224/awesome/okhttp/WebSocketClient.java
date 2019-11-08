package github.com.yangfan1224.awesome.okhttp;

import okhttp3.*;
import okio.ByteString;

/**
 * @author yangfan
 */
public class WebSocketClient {
    public static void main(String [] args) {
        String wsUrl = "ws://" + "localhost" + ":" + 8090 + "/v1/websocket/";
//新建client
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        //构造request对象
        Request request = new Request.Builder()
                .url(wsUrl)
                .build();
        //建立连接
        client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                System.out.println("client onOpen");
                System.out.println("client request header:" + response.request().headers());
                System.out.println("client response header:" + response.headers());
                System.out.println("client response:" + response);

                //开启消息定时发送
                byte[] message = "hello,world".getBytes();
                if (!webSocket.send(ByteString.of(message))) {
                    System.out.println("webSocket send error");
                }
            }
            @Override
            public void onMessage(WebSocket webSocket, String text) {
                System.out.println("client onMessage");
                System.out.println("message:" + text);
            }
            @Override
            public void onMessage(WebSocket webSocket, ByteString text) {
                System.out.println("client onMessage");
                System.out.println("message:" + text.hex());
            }
            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                System.out.println("client onClosing");
                System.out.println("code:" + code + " reason:" + reason);
            }
            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                System.out.println("client onClosed");
                System.out.println("code:" + code + " reason:" + reason);
            }
            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                //出现异常会进入此回调
                System.out.println("client onFailure");
                System.out.println("throwable:" + t);
                System.out.println("response:" + response);
            }
        });
    }
}
