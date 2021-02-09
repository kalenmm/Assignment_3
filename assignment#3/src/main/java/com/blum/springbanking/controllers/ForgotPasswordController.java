package com.blum.springbanking.controllers;

import com.blum.springbanking.CustomUserDetails;
import com.blum.springbanking.models.User;
import com.blum.springbanking.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ForgotPasswordController {

    @Autowired
    private CustomUserDetailsService userService;


    @GetMapping("/reset_password")
    public String showResetPasswordForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        String token = user.getToken();
        model.addAttribute("token", token);

        return "reset-form";
    }

    @PostMapping("/reset_password")
    public void processResetPassword(@RequestParam(name="password") String password, Model model) {
        User user = userService.getByResetPasswordToken((String) model.getAttribute("token"));
        System.out.println(user.toString());
        userService.updatePassword(user, password);
}}