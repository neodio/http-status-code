package com.neodio.httpstatuscode.controller;

import com.neodio.httpstatuscode.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatusController {

    @GetMapping(value = "/{code:\\d{3}}")
    @ResponseBody
    public CommonResponse<String> removeCache(@PathVariable String code, @RequestParam String sleep) {
        return CommonResponse.success(code);
    }

//    @GetMapping(value = {"**", "*"})
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public String route() {
//        return "error/404";
//    }


    @GetMapping(value = {"**", "*"})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonResponse route(HttpServletResponse response) {
        return CommonResponse.fail("","404");
    }
}
