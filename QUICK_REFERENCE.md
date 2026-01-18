# 🚀 Quick Reference Card - Spring Security SSO

## Essential Commands

### Build & Run
```bash
# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run

# Or use the quick start script
./start.sh
```

### Access URLs
- **Home**: http://localhost:8080
- **Login**: http://localhost:8080/login
- **User Dashboard**: http://localhost:8080/home (requires auth)
- **User Profile**: http://localhost:8080/profile (requires auth)
- **API - User Info**: http://localhost:8080/api/user (requires auth)

---

## OAuth2 Callback URLs

### GitHub
```
http://localhost:8080/login/oauth2/code/github
```

### Google
```
http://localhost:8080/login/oauth2/code/google
```

⚠️ **Important**: These URLs must be configured exactly in your OAuth2 provider settings!

---

## Environment Variables

```bash
# Required for OAuth2 authentication
export GITHUB_CLIENT_ID=your_github_client_id
export GITHUB_CLIENT_SECRET=your_github_client_secret
export GOOGLE_CLIENT_ID=your_google_client_id
export GOOGLE_CLIENT_SECRET=your_google_client_secret
```

Or create a `.env` file:
```bash
cp .env.example .env
# Edit .env with your credentials
```

---

## Project Structure

```
src/main/java/sn/sdley/springsecurity_sso/
├── config/
│   └── SecurityConfig.java           # Spring Security configuration
├── controller/
│   ├── HomeController.java           # MVC pages
│   └── UserRestController.java       # REST API
├── service/
│   └── CustomOAuth2UserService.java  # OAuth2 user handling
└── SpringsecuritySsoApplication.java # Main application

src/main/resources/
├── templates/
│   ├── index.html                    # Landing page
│   ├── login.html                    # Login page
│   ├── home.html                     # User dashboard
│   ├── profile.html                  # User profile
│   └── error.html                    # Error page
├── application.properties            # Main config
└── application.yml                   # YAML config (alternative)
```

---

## Key Configuration Files

### application.properties
```properties
spring.application.name=springsecurity-sso
server.port=8080

# GitHub OAuth2
spring.security.oauth2.client.registration.github.client-id=${GITHUB_CLIENT_ID}
spring.security.oauth2.client.registration.github.client-secret=${GITHUB_CLIENT_SECRET}

# Google OAuth2
spring.security.oauth2.client.registration.google.client-id=${GOOGLE_CLIENT_ID}
spring.security.oauth2.client.registration.google.client-secret=${GOOGLE_CLIENT_SECRET}
```

---

## Security Endpoints

| Endpoint | Access | Description |
|----------|--------|-------------|
| `/` | Public | Landing page |
| `/login` | Public | Login page |
| `/home` | Protected | User dashboard |
| `/profile` | Protected | User profile |
| `/api/user` | Protected | User info API |
| `/logout` | Protected | Logout endpoint (POST) |

---

## Docker Commands

```bash
# Build and run with Docker Compose
docker-compose up --build

# Run in detached mode
docker-compose up -d

# Stop containers
docker-compose down

# View logs
docker-compose logs -f spring-security-sso
```

---

## Testing Authentication Flow

### 1. Start Application
```bash
./mvnw spring-boot:run
```

### 2. Test GitHub Authentication
1. Navigate to http://localhost:8080
2. Click "Get Started - Sign In"
3. Click "Continue with GitHub"
4. Authorize the application
5. You should be redirected to `/home` with your GitHub profile

### 3. Test Google Authentication
1. Logout from the dashboard
2. Go to http://localhost:8080/login
3. Click "Continue with Google"
4. Select your Google account
5. You should be redirected to `/home` with your Google profile

### 4. Test API Endpoints
```bash
# After authentication, test the API
curl -b cookies.txt http://localhost:8080/api/user
curl -b cookies.txt http://localhost:8080/api/user/name
curl -b cookies.txt http://localhost:8080/api/user/email
```

---

## Common Issues & Solutions

### Issue: "redirect_uri_mismatch"
✅ **Solution**: Verify your callback URLs are exact matches in OAuth2 provider settings

### Issue: "invalid_client"
✅ **Solution**: Check Client ID and Secret are correct and loaded

### Issue: Port 8080 in use
✅ **Solution**: Change port in `application.properties`:
```properties
server.port=8081
```

### Issue: Application won't start
✅ **Solution**: Verify Java 17+ is installed:
```bash
java -version
```

---

## OAuth2 Provider Setup Links

- **GitHub**: https://github.com/settings/developers
- **Google**: https://console.cloud.google.com/apis/credentials

---

## Technology Stack

| Component | Version | Purpose |
|-----------|---------|---------|
| Spring Boot | 4.0.1 | Application framework |
| Spring Security | 6.x | Authentication & authorization |
| Java | 17 LTS | Programming language |
| Thymeleaf | 3.1.x | Template engine |
| Maven | 3.6+ | Build tool |
| Docker | Latest | Containerization |

---

## Security Best Practices ✅

- ✅ OAuth2 Authorization Code Flow
- ✅ CSRF Protection enabled
- ✅ Secure session cookies (HttpOnly, SameSite)
- ✅ No hardcoded credentials
- ✅ Environment variable configuration
- ✅ HTTPS recommended for production
- ✅ Session timeout (30 minutes)
- ✅ Single session per user

---

## Documentation Files

| File | Description |
|------|-------------|
| `README.md` | Comprehensive project documentation |
| `SETUP_GUIDE.md` | Step-by-step OAuth2 setup |
| `IMPLEMENTATION_SUMMARY.md` | Complete implementation details |
| `QUICK_REFERENCE.md` | This file - quick commands |

---

## Support

- 📖 Check `README.md` for detailed documentation
- 🔧 Check `SETUP_GUIDE.md` for OAuth2 configuration
- 📊 Check `IMPLEMENTATION_SUMMARY.md` for architecture details

---

**Built with ❤️ using Spring Boot & Spring Security**

*Last Updated: January 18, 2026*
