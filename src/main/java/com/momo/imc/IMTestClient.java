package com.momo.imc;

import com.alibaba.fastjson.JSONObject;
import com.momo.imc.observer.IMManager;
import com.momo.imc.observer.IMObserver;
import com.momo.imc.utils.IMTimeUtils;
import com.momo.imc.ws.MessageBody;

import java.util.Random;

public class IMTestClient implements IMObserver {

    String imIPAndPort = "xxx.xxx.xxx.xxx:xxxx"; //测试IM
    String fromUid = "xxxx";
    String token = "xxxx";
    String deviceId = IMTimeUtils.getNanoTime()+"";
    public String fbFlag = "xxxx";


    public IMTestClient() {

        IMManager.instance().imIPAndPort = imIPAndPort;
        IMManager.instance().fromUid = fromUid;
        IMManager.instance().token = token;
        IMManager.instance().deviceId = deviceId;
        IMManager.instance().needAck = "1000001,5000004,8000000";
        IMManager.instance().fbFlag = fbFlag;
        IMManager.instance().imManagerSubject.addObserver(this);
        IMManager.instance().startSocket();
    }



    public static void main(String[] arg0) {

        IMTestClient imClient = new IMTestClient();
//        imClient.createRoom(imClient.fromUid, imClient.fbFlag);
//        imClient.joinRoom(imClient.fromUid,imClient.fbFlag);
//        imClient.sendMessageRoom(imClient.fromUid, imClient.fbFlag);

//        String gid = "23882_472783";
//        String fromuid = "23883";
//        System.out.println(isNetWorkAvailable());
//        System.out.println(new Random().nextInt(1));

    }

    public static boolean isNetWorkAvailable() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process pingProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitCode = pingProcess.waitFor();
            return (exitCode == 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    int i = 0;
    @Override
    public void onIMMessage(String message) {
//        if(message.indexOf("-------") >= 0) {
            System.out.println(i + ":接收到的IM消息:" + message);
            i++;
//        }

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

    private void createRoom(String fromUid,String fbFlag){

        MessageBody messageBody = new MessageBody();
        messageBody.setEventId("5000000");
        messageBody.setFromUid(fromUid);
        messageBody.setToUid("");
        messageBody.setCTimest(IMTimeUtils.getTimeSt());
        messageBody.setSTimest("");
        messageBody.setDataBody("");
        messageBody.setMType("50");
        messageBody.setIsAck("");
        messageBody.setFbFlag(fbFlag);
        messageBody.setIsCache("0");
        messageBody.setIsGroup("1");
        messageBody.setGroupId("1001_314780_1665306248");
        messageBody.setGroupName("1001_303498_1663642005");
        IMManager.instance().sendMessage(messageBody);
    }

    private void joinRoom(String fromUid,String fbFlag){
        MessageBody messageBody = new MessageBody();
        messageBody.setEventId("5000001");
        messageBody.setFromUid(fromUid);
        messageBody.setToUid("");
        messageBody.setCTimest(IMTimeUtils.getTimeSt());
        messageBody.setSTimest("");
        messageBody.setDataBody("");
        messageBody.setMType("50");
        messageBody.setIsAck("");
        messageBody.setFbFlag(fbFlag);
        messageBody.setIsCache("0");
        messageBody.setIsGroup("1");
        messageBody.setGroupId("1001_314780_1665306248");
        messageBody.setGroupName("1001_314780_1665306248");
        IMManager.instance().sendMessage(messageBody);
    }

    private void leveRoom(String fromUid,String fbFlag){

        MessageBody messageBody = new MessageBody();
        messageBody.setEventId("5000002");
        messageBody.setFromUid(fromUid);
        messageBody.setToUid("");
        messageBody.setCTimest(IMTimeUtils.getTimeSt());
        messageBody.setSTimest("");
        messageBody.setDataBody("");
        messageBody.setMType("50");
        messageBody.setIsAck("");
        messageBody.setFbFlag(fbFlag);
        messageBody.setIsCache("0");
        messageBody.setIsGroup("1");
        messageBody.setGroupId("1001_303498_1663642005");
        messageBody.setGroupName("1001_303498_1663642005");
        IMManager.instance().sendMessage(messageBody);
    }

    private void dissRoom(String fromUid,String fbFlag){

        MessageBody messageBody = new MessageBody();
        messageBody.setEventId("5000003");
        messageBody.setFromUid(fromUid);
        messageBody.setToUid("");
        messageBody.setCTimest(IMTimeUtils.getTimeSt());
        messageBody.setSTimest("");
        messageBody.setDataBody("");
        messageBody.setMType("50");
        messageBody.setIsAck("");
        messageBody.setFbFlag(fbFlag);
        messageBody.setIsCache("0");
        messageBody.setIsGroup("1");
        messageBody.setGroupId("1001_303498_1663642005");
        messageBody.setGroupName("1001_303498_1663642005");
        IMManager.instance().sendMessage(messageBody);
    }

    private void sendMessageRoom(String fromUid,String fbFlag){


        for (int i = 1; i <= 10; i++) {

            MessageBody messageBody = new MessageBody();
            messageBody.setEventId("5000004");
            messageBody.setFromUid(fromUid);
            messageBody.setToUid("");
            messageBody.setCTimest(IMTimeUtils.getTimeSt());
            messageBody.setSTimest("");
            messageBody.setDataBody("消息---------"+i+"------------");
            messageBody.setMType("50");
            messageBody.setIsAck("");
            messageBody.setFbFlag(fbFlag);
            messageBody.setIsCache("0");

            messageBody.setIsGroup("1");
            messageBody.setGroupId("1001_314780_1665306248");
            messageBody.setGroupName("1001_314780_1665306248");

            IMManager.instance().sendMessage(messageBody);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

        }
    }

}
