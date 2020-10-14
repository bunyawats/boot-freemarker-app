package com.ssc.freemarker.FreeMarkerApp.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ResponseObject {
    String statusMsg;
    String statusCode;
}
