# OAuth2 Setup Guide for Spring Security SSO

This guide will walk you through setting up OAuth2 authentication with GitHub and Google for your Spring Security SSO application.

## Table of Contents
1. [GitHub OAuth Setup](#github-oauth-setup)
2. [Google OAuth Setup](#google-oauth-setup)
3. [Configuration](#configuration)
4. [Testing](#testing)

---

## GitHub OAuth Setup

### Step 1: Create a GitHub OAuth App

1. Go to [GitHub Settings](https://github.com/settings/profile)
2. Click on **Developer settings** (left sidebar, bottom)
3. Click on **OAuth Apps**
4. Click **New OAuth App** (or **Register a new application**)

### Step 2: Fill in Application Details

- **Application name**: `Spring Security SSO` (or your preferred name)
- **Homepage URL**: `http://localhost:8080`
- **Application description**: (Optional) `OAuth2 Single Sign-On Application`
- **Authorization callback URL**: `http://localhost:8080/login/oauth2/code/github`

⚠️ **Important**: The callback URL must match exactly!

### Step 3: Get Your Credentials

After creating the app:
1. You'll see your **Client ID** - copy this
2. Click **Generate a new client secret**
3. Copy the **Client Secret** immediately (you won't be able to see it again)

### Step 4: Save Credentials

Add to your `.env` file:
```bash
GITHUB_CLIENT_ID=Iv1.xxxxxxxxxxxxxxxx
GITHUB_CLIENT_SECRET=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

---

## Google OAuth Setup

### Step 1: Create a Google Cloud Project

1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Click on the project dropdown (top bar)
3. Click **New Project**
4. Enter project name: `spring-security-sso` (or your preferred name)
5. Click **Create**

### Step 2: Enable OAuth Consent Screen

1. In the left sidebar, navigate to **APIs & Services** → **OAuth consent screen**
2. Select **External** user type
3. Click **Create**

### Step 3: Configure OAuth Consent Screen

Fill in the required fields:
- **App name**: `Spring Security SSO`
- **User support email**: Your email
- **Developer contact information**: Your email

Click **Save and Continue** through all steps.

### Step 4: Create OAuth 2.0 Credentials

1. Go to **APIs & Services** → **Credentials**
2. Click **Create Credentials** → **OAuth 2.0 Client ID**
3. Select **Application type**: **Web application**
4. **Name**: `Spring Security SSO Web Client`

### Step 5: Configure Authorized Redirect URIs

Under **Authorized redirect URIs**, click **Add URI** and add:
```
http://localhost:8080/login/oauth2/code/google
```

⚠️ **Important**: The redirect URI must match exactly!

Click **Create**.

### Step 6: Get Your Credentials

You'll see a dialog with:
- **Client ID**: Something like `xxxxx.apps.googleusercontent.com`
- **Client Secret**: A long string

Copy both values.

### Step 7: Save Credentials

Add to your `.env` file:
```bash
GOOGLE_CLIENT_ID=xxxxx-xxxxx.apps.googleusercontent.com
GOOGLE_CLIENT_SECRET=xxxxxxxx-xxxxxxxxxxxxxxxxxxxxxxxx
```

---

## Configuration

### Option 1: Using .env File (Recommended for Development)

1. Copy `.env.example` to `.env`:
   ```bash
   cp .env.example .env
   ```

2. Edit `.env` and add your credentials:
   ```bash
   GITHUB_CLIENT_ID=your_actual_github_client_id
   GITHUB_CLIENT_SECRET=your_actual_github_client_secret
   GOOGLE_CLIENT_ID=your_actual_google_client_id
   GOOGLE_CLIENT_SECRET=your_actual_google_client_secret
   ```

3. Load environment variables before running:
   ```bash
   export $(cat .env | grep -v '^#' | xargs)
   ```

### Option 2: Using application.properties Directly

Edit `src/main/resources/application.properties`:

```properties
# GitHub OAuth2
spring.security.oauth2.client.registration.github.client-id=your_github_client_id
spring.security.oauth2.client.registration.github.client-secret=your_github_client_secret

# Google OAuth2
spring.security.oauth2.client.registration.google.client-id=your_google_client_id
spring.security.oauth2.client.registration.google.client-secret=your_google_client_secret
```

⚠️ **Warning**: Never commit credentials to version control!

### Option 3: Using Environment Variables (Production)

Set environment variables in your deployment environment:

```bash
export GITHUB_CLIENT_ID=your_github_client_id
export GITHUB_CLIENT_SECRET=your_github_client_secret
export GOOGLE_CLIENT_ID=your_google_client_id
export GOOGLE_CLIENT_SECRET=your_google_client_secret
```

---

## Testing

### 1. Build the Application

```bash
./mvnw clean install
```

### 2. Run the Application

```bash
./mvnw spring-boot:run
```

Or use the quick start script:
```bash
./start.sh
```

### 3. Access the Application

Open your browser and navigate to:
```
http://localhost:8080
```

### 4. Test Authentication

1. Click **"Get Started - Sign In"**
2. Try **"Continue with GitHub"**
   - You'll be redirected to GitHub
   - Authorize the application
   - You'll be redirected back to `/home`
3. Logout and try **"Continue with Google"**
   - You'll be redirected to Google
   - Select your Google account
   - You'll be redirected back to `/home`

### 5. Test API Endpoints

Once authenticated, test these endpoints:

- `GET http://localhost:8080/api/user` - Get user details as JSON
- `GET http://localhost:8080/api/user/name` - Get user name
- `GET http://localhost:8080/api/user/email` - Get user email

---

## Troubleshooting

### Issue: "redirect_uri_mismatch" Error

**Solution**: Check that your redirect URIs are exactly:
- GitHub: `http://localhost:8080/login/oauth2/code/github`
- Google: `http://localhost:8080/login/oauth2/code/google`

### Issue: "invalid_client" Error

**Solution**: Verify your Client ID and Client Secret are correct and properly loaded.

### Issue: Port Already in Use

**Solution**: Change the port in `application.properties`:
```properties
server.port=8081
```

And update your OAuth redirect URIs accordingly.

### Issue: Google Shows "This app hasn't been verified"

**Solution**: During development, click **Advanced** → **Go to [app name] (unsafe)**. For production, submit your app for verification.

---

## Security Best Practices

1. ✅ Never commit `.env` or credentials to version control
2. ✅ Use environment variables in production
3. ✅ Enable HTTPS in production (set `server.servlet.session.cookie.secure=true`)
4. ✅ Regularly rotate OAuth client secrets
5. ✅ Monitor authentication logs
6. ✅ Implement rate limiting for authentication endpoints
7. ✅ Keep dependencies updated

---

## Next Steps

- Add user persistence (database)
- Implement role-based access control (RBAC)
- Add more OAuth2 providers (Facebook, Microsoft, etc.)
- Implement JWT tokens for stateless authentication
- Add email verification
- Implement remember me functionality

---

## Support

For issues or questions:
- Check the [README.md](README.md) for general documentation
- Review Spring Security documentation: https://spring.io/projects/spring-security
- Check OAuth2 RFC: https://tools.ietf.org/html/rfc6749

---

**Happy Coding! 🚀**
