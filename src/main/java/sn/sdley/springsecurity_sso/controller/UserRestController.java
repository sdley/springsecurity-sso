package sn.sdley.springsecurity_sso.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

/**
 * REST API Controller for user information
 */
@RestController
@RequestMapping("/api")
public class UserRestController {

    /**
     * Get current authenticated user details
     * @param principal The authenticated OAuth2 user
     * @return User attributes as JSON
     */
    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return Collections.singletonMap("error", "Not authenticated");
        }
        return principal.getAttributes();
    }

    /**
     * Get user's name
     */
    @GetMapping("/user/name")
    public Map<String, String> userName(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return Collections.singletonMap("error", "Not authenticated");
        }
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }

    /**
     * Get user's email
     */
    @GetMapping("/user/email")
    public Map<String, String> userEmail(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return Collections.singletonMap("error", "Not authenticated");
        }
        return Collections.singletonMap("email", principal.getAttribute("email"));
    }
}
