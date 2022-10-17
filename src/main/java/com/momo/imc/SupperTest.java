package com.momo.imc;

import com.alibaba.fastjson.JSONObject;
import com.momo.imc.observer.IMManager;
import com.momo.imc.observer.IMObserver;
import com.momo.imc.observer.IMSupperManager;
import com.momo.imc.observer.TopManager;
import com.momo.imc.utils.IMTimeUtils;
import com.momo.imc.ws.MessageBody;

public class SupperTest implements IMObserver {

    String imIPAndPort_01 = "3.1.84.229:9944";
    String fromUid_01 = "30099";
    String token_01 = "123";
    String deviceId_01 = "1649303524433-44426425";

    String imIPAndPort_02 = "52.221.226.203:9922";
    String fromUid_02 = "30088";
    String token_02 = "123";
    String deviceId_02 = "1649303524433-44426426";

    String imIPAndPort_03 = "52.221.226.203:9933";
    String fromUid_03 = "30077";
    String token_03 = "123";
    String deviceId_03 = "1649303524433-44426427";
    IMSupperManager imSupperManager_01,imSupperManager_02,imSupperManager_03;

    public SupperTest(){

        imSupperManager_01 = TopManager.instance().createManager();
        imSupperManager_01.imIPAndPort = imIPAndPort_01;
        imSupperManager_01.fromUid = fromUid_01;
        imSupperManager_01.token = token_01;
        imSupperManager_01.deviceId = deviceId_01;
        imSupperManager_01.needAck = "1000001,5000004,8000000";
        imSupperManager_01.imManagerSubject.addObserver(this);
        imSupperManager_01.startSocket();

        imSupperManager_02 = TopManager.instance().createManager();
        imSupperManager_02.imIPAndPort = imIPAndPort_02;
        imSupperManager_02.fromUid = fromUid_02;
        imSupperManager_02.token = token_02;
        imSupperManager_02.deviceId = deviceId_02;
        imSupperManager_02.needAck = "1000001,5000004,8000000";
        imSupperManager_02.imManagerSubject.addObserver(this);
        imSupperManager_02.startSocket();

        imSupperManager_03 = TopManager.instance().createManager();
        imSupperManager_03.imIPAndPort = imIPAndPort_03;
        imSupperManager_03.fromUid = fromUid_03;
        imSupperManager_03.token = token_03;
        imSupperManager_03.deviceId = deviceId_03;
        imSupperManager_03.needAck = "1000001,5000004,8000000";
        imSupperManager_03.imManagerSubject.addObserver(this);
        imSupperManager_03.startSocket();
    }

    public static void main(String[] arg0) {
        SupperTest supperTest = new SupperTest();

        MessageBody messageBody = new MessageBody();
        messageBody.setEventId("1000001");
        messageBody.setFromUid("30099");
        messageBody.setToUid("30088");
        messageBody.setCTimest(IMTimeUtils.getTimeSt());
        messageBody.setSTimest("");
        messageBody.setDataBody("30099->30088消息来了");
        messageBody.setMType("1");
        messageBody.setIsAck("");
        messageBody.setIsCache("0");
        messageBody.setIsGroup("0");
        messageBody.setGroupId("");
        messageBody.setGroupName("");
        if(supperTest.imSupperManager_01.imWebSocketClient.isLogin)
            supperTest.imSupperManager_01.sendMessage(messageBody);

        MessageBody messageBody2 = new MessageBody();
        messageBody2.setEventId("5000000");
        messageBody2.setFromUid("30088");
        messageBody2.setToUid("");
        messageBody2.setCTimest(IMTimeUtils.getTimeSt());
        messageBody2.setSTimest("");
        messageBody2.setDataBody("30099");
        messageBody2.setMType("1");
        messageBody2.setIsAck("");
        messageBody2.setIsCache("0");
        messageBody2.setIsGroup("1");
        messageBody2.setGroupId("30088_3489792374793");
        messageBody2.setGroupName("");
        if(supperTest.imSupperManager_02.imWebSocketClient.isLogin)
            supperTest.imSupperManager_02.sendMessage(messageBody2);

        MessageBody messageBody3 = new MessageBody();
        messageBody3.setEventId("5000001");
        messageBody3.setFromUid("30077");
        messageBody3.setToUid("");
        messageBody3.setCTimest(IMTimeUtils.getTimeSt());
        messageBody3.setSTimest("");
        messageBody3.setDataBody("");
        messageBody3.setMType("1");
        messageBody3.setIsAck("");
        messageBody3.setIsCache("0");
        messageBody3.setIsGroup("1");
        messageBody3.setGroupId("30088_3489792374793");
        messageBody3.setGroupName("");
        if(supperTest.imSupperManager_03.imWebSocketClient.isLogin)
            supperTest.imSupperManager_03.sendMessage(messageBody3);

    }

    @Override
    public void onIMMessage(String message) {
        System.out.println("接收到的消息:" + message);
        JSONObject messageJson = null;
        try {
            messageJson = JSONObject.parseObject(message);
            String eventId = messageJson.get("eventId").toString();
            if(Integer.parseInt(eventId) == 3000001 ){
                IMManager.instance().stopSocket();
                System.exit(0);
            }
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void onIMError(String message) {
        System.out.println("接收到的IM错误消息:" + message);
    }
}
