package com.hldj.hmyg.Ui.friend.bean;

/**
 * 本地消息对象
 */

public class Message {

    private int id;

    private String momentsId = "";//帖子的 id
    private String option = ""; // 增删  改查  类型
    private String type = "";
    private String sourceId = "";
    private String messageType = "";

    private String other = "";// 其他
    private long time = System.currentTimeMillis(); // 时间戳

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMomentsId() {
        return momentsId;
    }

    public void setMomentsId(String momentsId) {
        this.momentsId = momentsId;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", momentsId='" + momentsId + '\'' +
                ", option='" + option + '\'' +
                ", type='" + type + '\'' +
                ", sourceId='" + sourceId + '\'' +
                ", messageType='" + messageType + '\'' +
                ", other='" + other + '\'' +
                ", time=" + time +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
