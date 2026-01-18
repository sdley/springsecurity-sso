package sn.sdley.springsecurity_sso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import sn.sdley.springsecurity_sso.service.CustomOAuth2UserService;

/**
 * Security Configuration for OAuth2 Login
 * Implements best practices for Spring Security 6.x
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    /**
     * Configure Security Filter Chain with OAuth2 Login
     * Following Spring Security 6.x lambda DSL best practices
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Configure authorization rules
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/", "/login**", "/error", "/webjars/**", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated()
            )
            // Configure OAuth2 Login with custom user service
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
                .defaultSuccessUrl("/home", true)
                .failureUrl("/login?error=true")
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(customOAuth2UserService)
                )
            )
            // Configure Logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            // CSRF Configuration - enabled by default, good practice
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**") // Disable for REST APIs if needed
            )
            // Session Management
            .sessionManagement(session -> session
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
            );

        return http.build();
    }

    /**
     * Custom Logout Success Handler (optional)
     */
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> {
            if (authentication != null && authentication.isAuthenticated()) {
                // Log logout event or perform cleanup
                System.out.println("User logged out: " + authentication.getName());
            }
            response.sendRedirect("/");
        };
    }
}
