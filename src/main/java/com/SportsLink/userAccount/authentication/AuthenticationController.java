package com.SportsLink.userAccount.authentication;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sportsLink/api/v1/account")
@RequiredArgsConstructor
@Tag(name = "Users (Require Authentication)" , description = "Apis That is Responsible User Authenticated Operations.")
public class AuthenticationController {
}
