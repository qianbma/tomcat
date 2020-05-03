package com.mqb.server;

import com.mqb.config.ServletConfig;
import com.mqb.config.ServletConfigMapping;
import com.mqb.servlet.Request;
import com.mqb.servlet.Response;
import com.mqb.servlet.Servlet;
import com.mqb.util.HttpUtil;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MyTomcat {
    private int port = 8080;

    public MyTomcat() {

    }

    public MyTomcat(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void start() throws Exception{
        initSerlet();
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Tomcat started on port "+port);
        while (true){
            Socket socket = serverSocket.accept();
            Request request = new Request(socket.getInputStream());
            Response response  = new Response(socket.getOutputStream());
            if("/".equals(request.getUrl())){
                response.write(HttpUtil.getHttpResponseContext404());
            } else if(stringClassMap.get(request.getUrl())==null){ // 没有对应的servlet则去找静态页面
                response.writeHtml(request.getUrl());
            } else {
                disPatch(request,response);
            }
            socket.close();
        }
    }

    private Map<String,Class<Servlet>> stringClassMap = new HashMap<>();
    public void initSerlet() throws ClassNotFoundException {
        for(ServletConfig servletConfig: ServletConfigMapping.getConfigs()){
            stringClassMap.put(servletConfig.getUrlMapping(),(Class<Servlet>)Class.forName(servletConfig.getClazz()));
        }
    }

    public void disPatch(Request request,Response response) throws IllegalAccessException, InstantiationException {
        Class<Servlet> servletClass = stringClassMap.get(request.getUrl());
        if(servletClass != null){
            Servlet servlet = servletClass.newInstance();
            servlet.service(request,response);
        }else {
            response.write(HttpUtil.getHttpResponseContext404());
        }
    }
    public static void main(String[] args) {
        MyTomcat myTomcat = new MyTomcat();
        try {
            myTomcat.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
