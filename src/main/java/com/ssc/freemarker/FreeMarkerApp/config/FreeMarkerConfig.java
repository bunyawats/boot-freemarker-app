package com.ssc.freemarker.FreeMarkerApp.config;

import com.ssc.freemarker.FreeMarkerApp.repository.FreeMarkerTemplateRepository;
import freemarker.cache.StringTemplateLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class FreeMarkerConfig {

    @Autowired
    FreeMarkerTemplateRepository freeMarkerTemplateRepository;

    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;

    @Bean
    @Lazy(true)
    public  freemarker.template.Configuration fmConfig(){

        Map<String, String> templateMap =  freeMarkerTemplateRepository.loadAllTemplate ( );
        log.debug ( "templateMap ------> {}",  templateMap);

        StringTemplateLoader stringLoader = new StringTemplateLoader ( );
        templateMap.forEach ( (k, v) -> stringLoader.putTemplate ( k, v ) );

        freemarker.template.Configuration cfg =  freeMarkerConfigurer.getConfiguration ();
        cfg.setTemplateLoader ( stringLoader );

        return cfg;
    }
}
