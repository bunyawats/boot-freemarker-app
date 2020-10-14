package com.ssc.freemarker.FreeMarkerApp.repository;

import com.google.gson.JsonObject;
import com.ssc.freemarker.FreeMarkerApp.db.tables.daos.FreemarkerTemplateDao;
import com.ssc.freemarker.FreeMarkerApp.db.tables.pojos.FreemarkerTemplatePojo;
import com.ssc.freemarker.FreeMarkerApp.db.tables.records.FreemarkerTemplateRecord;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.JSON;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.ssc.freemarker.FreeMarkerApp.db.Tables.FREEMARKER_TEMPLATE;

@Slf4j
@Repository
public class FreeMarkerTemplateRepositoryImpl implements FreeMarkerTemplateRepository {


    @Autowired
    DSLContext dsl;

    @Override
    public Map<String, String> loadAllTemplate() {

        Map<String, String> templateMap = new HashMap<> (  );


        try {
            templateMap = dsl.select (
                    FREEMARKER_TEMPLATE.TEMPLATE_NAME,
                    FREEMARKER_TEMPLATE.TEMPLATE_BODY )
                    .from ( FREEMARKER_TEMPLATE ).fetch ( )
                    .intoMap (
                            FREEMARKER_TEMPLATE.TEMPLATE_NAME,
                            FREEMARKER_TEMPLATE.TEMPLATE_BODY );
        } catch (IllegalArgumentException e) {
            e.printStackTrace ( );
        } catch (DataAccessException e) {
            e.printStackTrace ( );
        }


        return templateMap;
    }

    @Override
    public String updatePojo() {


        FreemarkerTemplateDao freemarkerTemplateDao = new FreemarkerTemplateDao ( dsl.configuration ( ) );

        FreemarkerTemplatePojo freemarkerTemplate = freemarkerTemplateDao.findById ( 1L );
        freemarkerTemplate.setTemplateName ( "hello123" );

        freemarkerTemplate.setTemplateJson ( createDummyJSON ( ) );

        freemarkerTemplateDao.update ( freemarkerTemplate );

        return "update pojo success";
    }


    @Override
    public String insertPojo() {

        FreemarkerTemplateDao freemarkerTemplateDao = new FreemarkerTemplateDao ( dsl.configuration ( ) );

        FreemarkerTemplatePojo freemarkerTemplate = new FreemarkerTemplatePojo ( );

        long nextId = (new Random ( )).nextInt ( 1000 );
        log.debug ( "nextId: {}", nextId );

        freemarkerTemplate.setId ( nextId );
        freemarkerTemplate.setTemplateName ( "hello123" );

        freemarkerTemplate.setTemplateJson ( createDummyJSON ( ) );

        freemarkerTemplateDao.insert ( freemarkerTemplate );

        return "insert pojo success";
    }

    @Override
    public String updateRecord() {

        FreemarkerTemplateRecord record = dsl.fetchOne ( FREEMARKER_TEMPLATE, FREEMARKER_TEMPLATE.ID.endsWith ( 1L ) );
        record.setTemplateName ( "template name" );

        record.setTemplateJson ( createDummyJSON ( ) );

        record.store ( );

        return "update record success";
    }

    @Override
    public String insertRecord() {

        FreemarkerTemplateRecord record = dsl.newRecord ( FREEMARKER_TEMPLATE );

        long nextId =  (new Random()).nextInt (1000);
        log.debug ( "nextId: {}", nextId );

        record.setId ( nextId );
        record.setTemplateName ( "hello123" );

        record.setTemplateJson ( createDummyJSON ( ) );

        record.store ( );

        return "insert record success";
    }

    private JSON createDummyJSON() {

        JsonObject json = new JsonObject ( );
        json.addProperty ( "name", "bunyawat [---] " + new Date ( ) );

        return JSON.valueOf ( json.toString ( ) );
    }

}
