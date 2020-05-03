package com.mqb.servlet;

import com.mqb.util.HttpUtil;

public class MyServlet extends Servlet {
    @Override
    public void doGet(Request request, Response response) {
        String content = "</h1>This is Get request "+request.getUrl()+"</h1>";
        response.write(HttpUtil.getHttpResponseContext200(content));
    }

    @Override
    public void doPost(Request request, Response response) {
        String content = "</h1>This is Post request "+request.getUrl()+"</h1>";
        response.write(HttpUtil.getHttpResponseContext200(content));
    }
}
