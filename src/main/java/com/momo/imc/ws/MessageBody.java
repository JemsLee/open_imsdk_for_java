package com.momo.imc.ws;

public class MessageBody {

    String eventId;//事件ID，参考事件ID文件
    String fromUid;//发送者ID
    String token;//发送者token
    String channelId="";//用户的channel

    String toUid;//接收者ID，多个以逗号隔开  重点：对于客户端发送过来的消息，不能和groupId并存，两者只能同时出现一个
    String mType;//消息类型
    String cTimest;//客户端发送时间搓
    String sTimest;//服务端接收时间搓
    String dataBody;//消息体，可以自由定义，以字符串格式传入

    String isGroup = "0";//是否群组 1-群组，0-个人
    String groupId = "";//群组ID ，对于客户端发送过来的消息，不能和toUid并存，两者只能同时出现一个
    String groupName = "";//群组名称

    String pkGroupId = ""; //pk时使用
    String spUid = "";//特殊用户ID
    String isAck = "0";//客户端接收到服务端发送的消息后，返回的状态= 1；dataBody结构 sTimest,sTimest,sTimest,sTimest......

    String isCache = "0";//是否需要存离线 1-需要，0-不需要
    String deviceId = "";//唯一设备id，目前用AFID作为标识，登录时带入
    String oldChannelId="";//准备离线的channel
    String isRoot = "0";//是否机器人 1-机器人

    String fbFlag = "";//分包的标记

    public String getFbFlag() {
        return fbFlag;
    }

    public void setFbFlag(String fbFlag) {
        this.fbFlag = fbFlag;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getFromUid() {
        return fromUid;
    }

    public void setFromUid(String fromUid) {
        this.fromUid = fromUid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getToUid() {
        return toUid;
    }

    public void setToUid(String toUid) {
        this.toUid = toUid;
    }

    public String getMType() {
        return mType;
    }

    public void setMType(String mType) {
        this.mType = mType;
    }

    public String getCTimest() {
        return cTimest;
    }

    public void setCTimest(String cTimest) {
        this.cTimest = cTimest;
    }

    public String getSTimest() {
        return sTimest;
    }

    public void setSTimest(String sTimest) {
        this.sTimest = sTimest;
    }

    public String getDataBody() {
        return dataBody;
    }

    public void setDataBody(String dataBody) {
        this.dataBody = dataBody;
    }

    public String getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(String isGroup) {
        this.isGroup = isGroup;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPkGroupId() {
        return pkGroupId;
    }

    public void setPkGroupId(String pkGroupId) {
        this.pkGroupId = pkGroupId;
    }

    public String getSpUid() {
        return spUid;
    }

    public void setSpUid(String spUid) {
        this.spUid = spUid;
    }

    public String getIsAck() {
        return isAck;
    }

    public void setIsAck(String isAck) {
        this.isAck = isAck;
    }

    public String getIsCache() {
        return isCache;
    }

    public void setIsCache(String isCache) {
        this.isCache = isCache;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getOldChannelId() {
        return oldChannelId;
    }

    public void setOldChannelId(String oldChannelId) {
        this.oldChannelId = oldChannelId;
    }

    public String getIsRoot() {
        return isRoot;
    }

    public void setIsRoot(String isRoot) {
        this.isRoot = isRoot;
    }
}
