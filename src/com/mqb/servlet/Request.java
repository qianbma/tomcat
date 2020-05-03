package com.mqb.servlet;

import java.io.InputStream;

public class Request {
    private String url;
    private String method;
    private InputStream inputStream;

    public Request(InputStream inputStream) throws Exception {
        this.inputStream = inputStream;
        int count = 0;
        while(count==0){
            count= inputStream.available();// 请求信息不是一次发送完成，inputStream.available用来用来预估网络的长度
        }
        byte [] bytes = new byte[count];
        inputStream.read(bytes);
        extractFild(new String(bytes));
    }

    private void extractFild(String content){
        if("".equals(content)){
            System.out.println("empyt");
        }else {
            String firstLine = content.split("\\n")[0];
            String[] split = firstLine.split("\\s");
            setMethod(split[0]);
            setUrl(split[1].substring(1));
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
