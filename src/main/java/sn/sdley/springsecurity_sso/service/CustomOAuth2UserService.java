package sn.sdley.springsecurity_sso.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * Custom OAuth2 User Service
 * Extends DefaultOAuth2UserService to add custom logic for user loading
 * This follows best practices for OAuth2 user management
 */
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private static final Logger logger = Logger.getLogger(CustomOAuth2UserService.class.getName());

    /**
     * Load OAuth2 User with custom logic
     * This method is called after successful OAuth2 authentication
     *
     * @param userRequest The OAuth2 user request
     * @return OAuth2User The loaded user
     * @throws OAuth2AuthenticationException if authentication fails
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // Call parent method to load user from OAuth2 provider
        OAuth2User oauth2User = super.loadUser(userRequest);

        // Get registration ID (github or google)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // Log authentication event (best practice for audit trail)
        logger.info("User authenticated via " + registrationId + ": " +
                    oauth2User.getAttribute("email"));

        // Here you can add custom logic:
        // - Save user to database
        // - Update user information
        // - Add custom authorities/roles
        // - Track login statistics

        return oauth2User;
    }
}
