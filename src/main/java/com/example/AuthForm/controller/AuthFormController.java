package com.example.AuthForm.controller;


import com.example.AuthForm.config.SecurityConfig;
import com.example.AuthForm.model.User;
import com.example.AuthForm.service.AuthFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class AuthFormController {

    @Autowired
    AuthFormService authFormService;

    @Autowired
    SecurityConfig securityConfig;



    @GetMapping("/")
    public String index() {
        return "redirect:/signUp";
    }

    @GetMapping("/signUp")
    public String signUp(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/welcome";
        }
        model.addAttribute("display", "display:none;");
        return "signUpPage";
    }

    @PostMapping("/signUp")
    public String signUp(Model model, RedirectAttributes redirectAttributes, @RequestParam(value = "firstName", required = true) String firstName,
                         @RequestParam(value = "lastName", required = true) String lastName,
                         @RequestParam(value = "email", required = true) String email,
                         @RequestParam(value = "password", required = true) String password
                       ) {
        firstName = firstName.strip();
        lastName = lastName.strip();
        email = email.strip();
        password = password.strip();

        try {
            password = securityConfig.passwordEncoder().encode(password);
            User user = authFormService.signUp(firstName, lastName, email, password);
            redirectAttributes.addFlashAttribute("message", "Account creato, effettua subito il login!");
            return "redirect:/login";
        }
        catch (DataIntegrityViolationException e) {
            String constraintMessage = e.getMostSpecificCause().getMessage();
            Pattern pattern = Pattern.compile("'.+\\.(.+)'$");
            Matcher matcher = pattern.matcher(constraintMessage);

            matcher.find();
            String constraint = matcher.group(1);

            if (constraint.equals("email_not_registered")) {
                model.addAttribute("errorMessage", "Registrazione fallita, email già utilizzata");
                model.addAttribute("color", "color:red;");
                return "signUpPage";
            }
            return "signUpPage";
        }
    }

    @GetMapping("/login")
    public String login(Model model,RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/welcome";
        }
        if (model.getAttribute("message") == null) {
            model.addAttribute("display", "display:none;");
        }
        return "login";
    }

    @GetMapping("/loginError")
    public String loginError(Model model,RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "Mail o password errate");
        redirectAttributes.addFlashAttribute("color", "color:red;");
        return "redirect:/login";
    }

    @GetMapping("/welcome")
    public String welcome(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("user", authFormService.getUserByEmail(email));
        return "welcome";
    }


}
