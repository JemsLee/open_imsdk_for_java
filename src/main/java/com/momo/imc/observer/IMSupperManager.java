package com.momo.imc.observer;

import com.alibaba.fastjson.JSONObject;
import com.momo.imc.utils.IMEncryptUtil;
import com.momo.imc.utils.IMTimeUtils;
import com.momo.imc.ws.IMWebSocketClient;
import com.momo.imc.ws.MessageBody;

import java.net.URI;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TimerTask;
import java.util.concurrent.*;

public class IMSupperManager {

    private static final int CORE_POOL_SIZE = 10;
    private static final int MAX_POOL_SIZE = 5000;
    private static final int QUEUE_CAPACITY = 20000;
    private static final Long KEEP_ALIVE_TIME = 1L;
    private ThreadPoolExecutor executor;
    private boolean isRuning = false;
    private LinkedList<String> linkedList = new LinkedList<>();

    private static ScheduledExecutorService scheduledExecutor;

    public String imIPAndPort = "";
    public String fromUid = "";
    public String token = "";
    public String deviceId = "";
    public String needAck = null;

    public IMWebSocketClient imWebSocketClient;
    public IMManagerSubject imManagerSubject;


    public IMSupperManager() {
        imManagerSubject = new IMManagerSubject();
    }

    /**
     * 初始化并开始
     */
    public void startSocket(){

        scheduledExecutor = Executors.newScheduledThreadPool(3);

        executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy());

        try {

            URI serverUri = new URI("ws://" + imIPAndPort + "/");
            imWebSocketClient = new IMWebSocketClient(serverUri);
            imWebSocketClient.imManagerSubject = imManagerSubject;
            imWebSocketClient.executor = executor;
            imWebSocketClient.fromUid = fromUid;
            imWebSocketClient.token = token;
            imWebSocketClient.deviceId = deviceId;
            if(needAck != null){
                String[] needAcks = needAck.split(",");
                imWebSocketClient.needACKFlag = new HashMap<>();
                for (String needack:needAcks) {
                    imWebSocketClient.needACKFlag.put(needack,needack);
                }
            }
            imWebSocketClient.connect();


            startPing();
            startScan();
            isRuning = true;

        } catch (Exception e) {

        }
    }

    /**
     * 停止
     */
    public void stopSocket(){
        isRuning = false;
        imWebSocketClient.close();
        scheduledExecutor.shutdown();
    }

    /**
     * 发送消息
     * @param messageBody
     * @return
     */
    public boolean sendMessage(MessageBody messageBody){
        boolean sendrs = false;
        if(isRuning) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(messageBody);
            String str = IMEncryptUtil.encrypt(IMEncryptUtil.getUidKey(fromUid), jsonObject.toJSONString());
            try {
                if (imWebSocketClient.isLogin) {
                    imWebSocketClient.send(str);
                    sendrs = true;
                } else {
                    linkedList.addFirst(str);
                }
            }catch (Exception exception){
                linkedList.addFirst(str);
            }
        }
        return sendrs;
    }

    private void startPing(){
        scheduledExecutor.scheduleWithFixedDelay(new TimerTask() {
            @Override
            public void run() {
                if(isRuning) {
                    try {
                        if (imWebSocketClient.isLogin) {
                            imWebSocketClient.send(createPingStr());
                        } else {
                            imWebSocketClient.reconnect();
                        }
                    } catch (Exception exception){
                        imWebSocketClient.reconnect();
                    }
                }
            }
        },5,5, TimeUnit.SECONDS);
    }

    private void startScan(){
        scheduledExecutor.scheduleWithFixedDelay(new TimerTask() {
            @Override
            public void run() {
                if(isRuning) {
                    while (!linkedList.isEmpty()) {
                        try {
                            if (imWebSocketClient.isLogin) {
                                imWebSocketClient.send(linkedList.removeLast());
                            }
                        }catch (Exception exception){
                            break;
                        }
                    }
                }
            }
        },3,3, TimeUnit.SECONDS);
    }

    public String createPingStr() {
        MessageBody messageBodyLogin = new MessageBody();
        messageBodyLogin.setEventId("9000000");
        messageBodyLogin.setFromUid(fromUid);
        messageBodyLogin.setDeviceId(deviceId);
        messageBodyLogin.setCTimest(IMTimeUtils.getNanoTime()+"");
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(messageBodyLogin);
        return IMEncryptUtil.encrypt(IMEncryptUtil.getUidKey(fromUid),jsonObject.toJSONString());
    }

}
