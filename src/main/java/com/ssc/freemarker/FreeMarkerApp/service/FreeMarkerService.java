package com.ssc.freemarker.FreeMarkerApp.service;


import java.io.IOException;
import java.util.Map;

public interface FreeMarkerService {

    public Map<String, String> loadAllTemplate();

    public  String  mergeTemplate(String templateName);

    public String insert();

    public String update();
}
