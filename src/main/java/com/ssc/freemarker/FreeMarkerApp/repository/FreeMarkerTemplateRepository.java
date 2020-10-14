package com.ssc.freemarker.FreeMarkerApp.repository;

import java.util.Map;

public interface FreeMarkerTemplateRepository {

    public Map<String, String> loadAllTemplate();

    String updatePojo();

    String updateRecord();

    String insertPojo();

    String insertRecord();
}
