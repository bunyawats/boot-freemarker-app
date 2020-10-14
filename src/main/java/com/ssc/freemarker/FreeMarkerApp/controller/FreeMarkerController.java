package com.ssc.freemarker.FreeMarkerApp.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssc.freemarker.FreeMarkerApp.service.FreeMarkerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class FreeMarkerController {

    @Autowired
    FreeMarkerService freeMarkerService;

    @GetMapping("/viewpage")
    public String viewPage(@RequestParam String pageName){
        return  freeMarkerService.mergeTemplate ( pageName );
    }


    @PostMapping("/insert")
    public String insert() {
        return freeMarkerService.insert ( );
    }

    @PutMapping("/update")
    public String update() {
        return freeMarkerService.update ( );
    }

    @PostMapping("/customer")
    public ResponseObject createCustomer(@RequestBody RequestObject reqObject) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper ( );

        String json = mapper.writeValueAsString ( reqObject );
        Object jsonObject = mapper.readValue ( json, Object.class );
        String prettyJson = mapper
                .writerWithDefaultPrettyPrinter ( )
                .writeValueAsString ( jsonObject );

        log.info ( "\nprettyJson : \n{}\n", prettyJson );

        return new ResponseObject ( "success", "10000" );
    }

}
