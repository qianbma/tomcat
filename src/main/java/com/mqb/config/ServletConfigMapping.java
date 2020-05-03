package com.mqb.config;

import java.util.ArrayList;
import java.util.List;

public class ServletConfigMapping {
    private static List<ServletConfig> configs = new ArrayList<>();

    static {
        configs.add(new ServletConfig("myServlet", "MyServlet", "com.mqb.servlet.MyServlet"));
    }

    public static List<ServletConfig> getConfigs() {
        return configs;
    }
}
