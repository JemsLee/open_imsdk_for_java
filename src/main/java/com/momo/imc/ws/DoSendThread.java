package com.momo.imc.ws;

import com.alibaba.fastjson.JSONObject;
import com.momo.imc.utils.IMEncryptUtil;
import com.momo.imc.utils.IMTimeUtils;
import org.java_websocket.WebSocket;

import java.util.logging.Logger;

public class DoSendThread extends Thread {

    IMWebSocketClient imWebSocketClient;
    String sTimest = "";
    String fromUid = "";
    String fbFlag = "";

    @Override
    public void run() {
        super.run();
        handleExc();
    }

    public void handleExc() {

        MessageBody messageBody = new MessageBody();
        messageBody.setEventId("1000002");
        messageBody.setFromUid(fromUid);
        messageBody.setToUid("");
        messageBody.setCTimest(IMTimeUtils.getTimeSt());
        messageBody.setSTimest("");
        messageBody.setDataBody(sTimest);
        messageBody.setMType("1");
        messageBody.setIsAck("1");
        messageBody.setIsCache("0");
        messageBody.setFbFlag(fbFlag);

        messageBody.setIsGroup("0");
        messageBody.setGroupId("");
        messageBody.setGroupName("");

        String wantToSendStr = ((JSONObject) JSONObject.toJSON(messageBody)).toJSONString();
        wantToSendStr = IMEncryptUtil.encrypt(IMEncryptUtil.getUidKey(messageBody.getFromUid()),wantToSendStr);
        while (!imWebSocketClient.isLogin){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        imWebSocketClient.send(wantToSendStr);
    }

}
