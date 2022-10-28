package com.momo.imc.ws;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.momo.imc.observer.IMManagerSubject;
import com.momo.imc.utils.IMEncryptUtil;
import com.momo.imc.utils.IMLogger;
import com.momo.imc.utils.IMStatus;
import com.momo.imc.utils.IMTimeUtils;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 服务器间通讯的Websocket客户端
 * 版本：1.0.0
 * 2022-01-03
 * 作者：Jem.Lee
 */

public class IMWebSocketClient extends WebSocketClient {

    public String fromUid = "";
    public String token = "";
    public String deviceId = "";
    public String fbFlag = "";

    public boolean isLogin = false;

    public ThreadPoolExecutor executor;

    public IMManagerSubject imManagerSubject;

    public Map needACKFlag = null;
    String key;

    public IMWebSocketClient(URI serverUris) {
        super(serverUris);
    }

    @Override
    public void onOpen(ServerHandshake arg0) {
        key = IMEncryptUtil.getUidKey(fromUid);
        send(createLoginStr());
    }

    @Override
    public void onClose(int arg0, String arg1, boolean arg2) {
        IMStatus imStatus = new IMStatus();
        imStatus.setImcode("501");
        imStatus.setDesc("服务器断开了你的链接:" + arg1);
        imStatus.setActionTime(IMTimeUtils.getDateTime());
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(imStatus);
        imManagerSubject.publishError(jsonObject.toJSONString());
        isLogin = false;
    }

    @Override
    public void onError(Exception arg0) {
        IMStatus imStatus = new IMStatus();
        imStatus.setImcode("502");
        imStatus.setDesc(arg0.getMessage());
        imStatus.setActionTime(IMTimeUtils.getDateTime());
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(imStatus);
        imManagerSubject.publishError(jsonObject.toJSONString());
        isLogin = false;
    }

    @Override
    public void onMessage(String arg0) {

        if (arg0.indexOf("{") >= 0) {
            if (arg0.indexOf("登录成功") >= 0) {
                isLogin = true;
            }
            imManagerSubject.publish(arg0);
        } else {
            String recStr = IMEncryptUtil.decrypt(key, arg0);
            imManagerSubject.publish(recStr);
            if (needACKFlag != null) {
                JSONObject jsonObject = JSON.parseObject(recStr);
                String eventId = jsonObject.getString("eventId");
                if (needACKFlag.get(eventId) != null) {
                    sendACK(jsonObject.getString("sTimest"));
                }
            }
        }
    }

    public void sendACK(String sTimest) {

        DoSendThread doSendThread = new DoSendThread();
        doSendThread.sTimest = sTimest;
        doSendThread.fromUid = fromUid;
        doSendThread.imWebSocketClient = this;
        executor.execute(doSendThread);

    }

    public String createLoginStr() {
        MessageBody messageBodyLogin = new MessageBody();
        messageBodyLogin.setEventId("1000000");
        messageBodyLogin.setFromUid(fromUid);
        messageBodyLogin.setToken(token);
        messageBodyLogin.setFbFlag(fbFlag);
        messageBodyLogin.setDeviceId(deviceId);
        messageBodyLogin.setCTimest(IMTimeUtils.getTimeSt());
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(messageBodyLogin);
//        IMLogger.getInstance().info("login的消息"+jsonObject.toJSONString());
        return jsonObject.toJSONString();
    }
}
