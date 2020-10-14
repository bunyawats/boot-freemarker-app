package com.ssc.freemarker.FreeMarkerApp.service;

import com.ssc.freemarker.FreeMarkerApp.repository.FreeMarkerTemplateRepository;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class FreeMarkerServiceImpl implements FreeMarkerService {

    @Autowired
    FreeMarkerTemplateRepository freeMarkerTemplateRepository;

    //read template from database
    @Lazy
    @Autowired
    freemarker.template.Configuration fmConfig;

    //read template from file
    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;

    @Override
    public Map<String, String> loadAllTemplate() {
        return freeMarkerTemplateRepository.loadAllTemplate ( );
    }

    @Override
    public String mergeTemplate(String templateName) {

        String pageMerged = "NA";

        Map<String, Object> data = new HashMap<> ( );
        data.put ( "user", "bunyawat" );

        try {
            Template template = fmConfig.getTemplate ( templateName ); //read template from database
//            Template template = freeMarkerConfigurer.getConfiguration ().getTemplate ( templateName ); //read template from file

            pageMerged = FreeMarkerTemplateUtils.processTemplateIntoString ( template, data );
        } catch (IOException | TemplateException e) {
            e.printStackTrace ( );
        };

        return pageMerged;
    }

    @Override
    public String insert() {
//        return freeMarkerTemplateRepository.insertPojo ( );
        return freeMarkerTemplateRepository.insertRecord ( );
    }

    @Override
    public String update() {
//        return freeMarkerTemplateRepository.updatePojo ( );
        return freeMarkerTemplateRepository.updateRecord ( );
    }
}
