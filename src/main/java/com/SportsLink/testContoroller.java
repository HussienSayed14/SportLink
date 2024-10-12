package com.SportsLink;

import com.SportsLink.userAuthentication.signUp.requests.SignUpRequest;
import com.SportsLink.utils.GenericResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sportsLink/api/v1/test")
public class testContoroller {

    @GetMapping(value = "/test")
        String signUp(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
      return "Hello Bro";
    }

}
