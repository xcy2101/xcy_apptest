package com.example.myapplication;

public class UserMessage {
    public static final int TYPE_SEND = 1;//用户接收的消息
    public static final int TYPE_RECEIVE = 0;//用户接收的消息

    private String content;//消息内容

    private  Integer id;
    private int type;//消息类型


    public UserMessage(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public UserMessage(int id,String content, int type) {
        this.content = content;
        this.id = id;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

