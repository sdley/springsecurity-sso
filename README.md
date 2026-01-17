# Spring Security SSO Application

A modern, feature-rich **Single Sign-On (SSO)** application built with **Spring Boot 4.0.1** and **Spring Security**, providing seamless OAuth2 authentication with **GitHub** and **Google**.

## 🎯 Features

- ✅ **OAuth2 Integration** with GitHub and Google
- ✅ **Spring Security 6.x** for robust authentication
- ✅ **Spring Boot 4.0.1** with latest Java 25 support
- ✅ **User-Friendly Authentication Flow**
- ✅ **Session Management**
- ✅ **Responsive Web Interface** (MVC-based)
- ✅ **Secure by Default**
- ✅ **RESTful Architecture**

## 🚀 Quick Start

### Prerequisites

- **Java 25** or higher
- **Maven 3.6+**
- Git

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/sdley/springsecurity-sso.git
   cd springsecurity-sso
   ```

2. **Set up OAuth2 Credentials**

   ### GitHub OAuth App Setup
   - Navigate to [GitHub Settings → Developer settings → OAuth Apps](https://github.com/settings/developers)
   - Click **New OAuth App**
   - Fill in the following:
     - **Application name**: Spring Security SSO
     - **Homepage URL**: `http://localhost:8080`
     - **Authorization callback URL**: `http://localhost:8080/login/oauth2/code/github`
   - Copy your **Client ID** and **Client Secret**

   ### Google OAuth 2.0 Setup
   - Go to [Google Cloud Console](https://console.cloud.google.com/)
   - Create a new project or select an existing one
   - Navigate to **Credentials** → **Create Credentials** → **OAuth 2.0 Client ID**
   - Select **Web application**
   - Add Authorized redirect URIs:
     - `http://localhost:8080/login/oauth2/code/google`
   - Copy your **Client ID** and **Client Secret**

3. **Configure Application Properties**

   Create or update `src/main/resources/application.properties`:
   ```properties
   spring.application.name=springsecurity-sso
   server.port=8080
   
   # GitHub OAuth2
   spring.security.oauth2.client.registration.github.client-id=your_github_client_id
   spring.security.oauth2.client.registration.github.client-secret=your_github_client_secret
   
   # Google OAuth2
   spring.security.oauth2.client.registration.google.client-id=your_google_client_id
   spring.security.oauth2.client.registration.google.client-secret=your_google_client_secret
   ```

   **Note:** For production, use environment variables:
   ```bash
   export GITHUB_CLIENT_ID=your_github_client_id
   export GITHUB_CLIENT_SECRET=your_github_client_secret
   export GOOGLE_CLIENT_ID=your_google_client_id
   export GOOGLE_CLIENT_SECRET=your_google_client_secret
   ```

   Then reference them in `application.properties`:
   ```properties
   spring.security.oauth2.client.registration.github.client-id=${GITHUB_CLIENT_ID}
   spring.security.oauth2.client.registration.github.client-secret=${GITHUB_CLIENT_SECRET}
   spring.security.oauth2.client.registration.google.client-id=${GOOGLE_CLIENT_ID}
   spring.security.oauth2.client.registration.google.client-secret=${GOOGLE_CLIENT_SECRET}
   ```

4. **Build and Run**

   ```bash
   # Build the project
   ./mvnw clean install
   
   # Run the application
   ./mvnw spring-boot:run
   ```

   The application will start at `http://localhost:8080`

## 🔐 Security Configuration

This project is configured with Spring Security 6.x providing:

- **CSRF Protection** enabled by default
- **CORS** configuration for secure cross-origin requests
- **OAuth2 Resource Server** support
- **Session Management** with secure cookies
- **Form-based and OAuth2 Login** options

### Security Best Practices Implemented

1. ✅ HTTPS recommended for production
2. ✅ Secure cookie settings (HttpOnly, SameSite)
3. ✅ OAuth2 client credentials never exposed in frontend
4. ✅ Environment variables for sensitive configurations
5. ✅ Spring Security default configurations follow OWASP guidelines

## 📁 Project Structure

```
springsecurity-sso/
├── src/
│   ├── main/
│   │   ├── java/sn/sdley/springsecurity_sso/
│   │   │   └── SpringsecuritySsoApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/          # Static assets (CSS, JS, images)
│   │       └── templates/       # Thymeleaf templates (HTML)
│   └── test/
│       └── java/sn/sdley/springsecurity_sso/
│           └── SpringsecuritySsoApplicationTests.java
├── pom.xml                      # Maven configuration
├── mvnw / mvnw.cmd             # Maven wrapper
├── HELP.md                      # Generated help file
└── README.md                    # This file
```

## 📚 Dependencies

### Core Dependencies
- **Spring Boot Starter Security**: OAuth2 and authentication
- **Spring Boot Starter WebMVC**: Web application support

### Test Dependencies
- **Spring Boot Starter Security Test**: Security testing utilities
- **Spring Boot Starter WebMVC Test**: MVC testing support

See `pom.xml` for complete dependency list.

## 🛠️ Development

### Adding Custom Security Configuration

Create a new `@Configuration` class:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/", "/login**").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .defaultSuccessUrl("/home")
            );
        return http.build();
    }
}
```

### Adding Custom OAuth2 User Details

Implement `OAuth2UserService<OAuth2UserRequest, OAuth2User>` to customize user details loading.

### Testing

Run the test suite:
```bash
./mvnw test
```

## 🔄 Authentication Flow

```
User Access
    ↓
Spring Security Interceptor
    ↓
Check Authentication Status
    ├─ Authenticated → Continue to Resource
    └─ Not Authenticated → Redirect to /login
        ↓
    Login Page with OAuth2 Options
        ├─ GitHub Login
        └─ Google Login
            ↓
        OAuth2 Provider Authorization
            ↓
        Redirect to Callback URL
            ↓
        User Created/Updated in Session
            ↓
        Redirect to Home Page
```

## 📖 API Endpoints (Examples)

- `GET /` - Public home page
- `GET /login` - Login page with OAuth2 options
- `GET /login/oauth2/code/github` - GitHub callback (auto-handled)
- `GET /login/oauth2/code/google` - Google callback (auto-handled)
- `POST /logout` - Logout endpoint
- `GET /user` - Get current authenticated user

## 🌐 Environment Variables

| Variable | Description | Example |
|----------|-------------|---------|
| `GITHUB_CLIENT_ID` | GitHub OAuth App Client ID | `Iv1.xxxxx` |
| `GITHUB_CLIENT_SECRET` | GitHub OAuth App Secret | `xxxxx` |
| `GOOGLE_CLIENT_ID` | Google OAuth Client ID | `xxxxx.apps.googleusercontent.com` |
| `GOOGLE_CLIENT_SECRET` | Google OAuth Client Secret | `xxxxx` |
| `SERVER_PORT` | Application port | `8080` |

## 🐛 Troubleshooting

### Issue: "Invalid client id" Error
- **Solution**: Verify your Client ID and Client Secret are correctly set in `application.properties` or environment variables
- Ensure the Client ID/Secret matches the provider you're trying to authenticate with

### Issue: "Redirect URI mismatch"
- **Solution**: Check that the redirect URI in your OAuth2 provider settings exactly matches:
  - GitHub: `http://localhost:8080/login/oauth2/code/github`
  - Google: `http://localhost:8080/login/oauth2/code/google`

### Issue: Session Not Persisting
- **Solution**: Ensure cookies are enabled in your browser
- Check that `server.servlet.session.cookie.http-only=true` in production

### Issue: CSRF Token Errors
- **Solution**: Ensure CSRF protection is properly configured and tokens are included in forms
- Verify `<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>`

## 📝 Configuration Examples

### application.yaml (Alternative Format)

```yaml
spring:
  application:
    name: springsecurity-sso
  security:
    oauth2:
      client:
        registration:
          github:
            client-id: ${GITHUB_CLIENT_ID}
            client-secret: ${GITHUB_CLIENT_SECRET}
          google:
            client-id: ${GOOGLE_CLIENT_ID}
            client-secret: ${GOOGLE_CLIENT_SECRET}
  servlet:
    session:
      cookie:
        http-only: true
        secure: true
        same-site: strict
```

## 🚢 Deployment

### Docker Deployment

Create a `Dockerfile`:

```dockerfile
FROM eclipse-temurin:25-jdk-jammy
WORKDIR /app
COPY target/springsecurity-sso-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Build and run:
```bash
./mvnw clean install
docker build -t springsecurity-sso .
docker run -p 8080:8080 \
  -e GITHUB_CLIENT_ID=your_id \
  -e GITHUB_CLIENT_SECRET=your_secret \
  -e GOOGLE_CLIENT_ID=your_id \
  -e GOOGLE_CLIENT_SECRET=your_secret \
  springsecurity-sso
```

### Production Checklist

- [ ] Enable HTTPS (SSL/TLS certificates)
- [ ] Set secure environment variables
- [ ] Configure CORS appropriately
- [ ] Enable CSRF protection
- [ ] Set secure cookie flags
- [ ] Configure session timeout
- [ ] Set up proper logging
- [ ] Review and restrict API endpoints
- [ ] Enable rate limiting
- [ ] Set up monitoring and alerting

## 📚 References

- [Spring Security Official Docs](https://spring.io/projects/spring-security)
- [Spring Boot OAuth2 Tutorial](https://spring.io/guides/tutorials/spring-boot-oauth2/)
- [OAuth 2.0 Specification](https://tools.ietf.org/html/rfc6749)
- [Spring Boot Reference Guide](https://docs.spring.io/spring-boot/4.0.1/reference/)

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 👤 Author

**sdley**
- GitHub: [@sdley](https://github.com/sdley)

## 💬 Support

For issues, questions, or suggestions, please open an [GitHub Issue](https://github.com/yourusername/springsecurity-sso/issues).

---

**Happy Coding! 🚀**

Last Updated: January 2026
