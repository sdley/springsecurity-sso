package sn.sdley.springsecurity_sso.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * Main Controller for handling page navigation and user authentication
 */
@Controller
public class HomeController {

    /**
     * Public landing page
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * Custom login page
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Home page - requires authentication
     */
    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            model.addAttribute("name", principal.getAttribute("name"));
            model.addAttribute("email", principal.getAttribute("email"));
            model.addAttribute("avatarUrl", principal.getAttribute("avatar_url")); // GitHub
            model.addAttribute("picture", principal.getAttribute("picture")); // Google
            model.addAttribute("attributes", principal.getAttributes());
        }
        return "home";
    }

    /**
     * User profile page
     */
    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            model.addAttribute("principal", principal);
            model.addAttribute("attributes", principal.getAttributes());
        }
        return "profile";
    }

    /**
     * Error page
     */
    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
