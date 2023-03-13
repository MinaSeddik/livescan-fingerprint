package com.softxpert.livescanfingerprint.controller;

import com.softxpert.livescanfingerprint.model.Application;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/v1")
@Slf4j
public class PrintCardController {
    @PostMapping("/create-fd-258")
    public HttpEntity<byte[]> createFd258InkCard(@RequestBody @Valid Application application,
                                                 HttpServletResponse response) {


//        System.out.println("Inside the controller with data: " + application);


        String fileName = "justdummyfornow";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        return new HttpEntity<byte[]>("dummydata".getBytes(), headers);
    }
}
