package com.choi76.web_socket_jwt.global.exception.controller;

import com.choi76.web_socket_jwt.global.enums.statuscode.ErrorStatus;
import com.choi76.web_socket_jwt.global.exception.GeneralException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 잘못된 엔드포인트로 api가 요청된 경우
@RestController
public class ResourceErrorController {
    @RequestMapping("/**")
    public void handleNotFound() {
        throw new GeneralException(ErrorStatus.RESOURCE_NOT_FOUND);
    }
}

