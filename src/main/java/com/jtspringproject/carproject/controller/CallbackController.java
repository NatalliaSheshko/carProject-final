package com.jtspringproject.carproject.controller;

import com.jtspringproject.carproject.models.CallbackRequest;
import com.jtspringproject.carproject.repository.CallbackRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Validator;

@Controller
public class CallbackController {

    @Autowired
    private CallbackRequestRepository repository;

    @Autowired
    private Validator validator;

    @PostMapping("/callback/send")
    @ResponseBody
    public ResponseEntity<?> handleCallback(
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam(value = "comment", required = false) String comment,
            @RequestParam("consentPersonal") boolean consentPersonal
    ) {
        CallbackRequest request = new CallbackRequest();
        request.setName(name);
        request.setPhone(phone);
        request.setComment(comment);
        request.setConsentPersonal(consentPersonal);

        repository.save(request);
        return ResponseEntity.ok().build();
    }
}
