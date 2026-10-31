package org.example.sb3.controller;

import jakarta.validation.Valid;
import org.example.sb3.pojo.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/validate")
public class ValidateController {

    @PostMapping("/test")
    public String test(@Valid @RequestBody User user, BindingResult bindingResult) {
        System.out.println(user.toString());
        StringBuffer stringBuffer = new StringBuffer();
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            for (ObjectError objectError : list) {
                stringBuffer.append(objectError.getDefaultMessage());
                stringBuffer.append("---");
            }
        }
        return stringBuffer != null ? stringBuffer.toString() : "";
    }

}
