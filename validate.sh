#!/bin/bash

# Project Validation Script
# Verifies all components are properly configured

echo "=============================================="
echo "  Spring Security SSO - Project Validation"
echo "=============================================="
echo ""

ERRORS=0

# Check Java Source Files
echo "✓ Checking Java Source Files..."
JAVA_FILES=(
    "src/main/java/sn/sdley/springsecurity_sso/SpringsecuritySsoApplication.java"
    "src/main/java/sn/sdley/springsecurity_sso/config/SecurityConfig.java"
    "src/main/java/sn/sdley/springsecurity_sso/service/CustomOAuth2UserService.java"
    "src/main/java/sn/sdley/springsecurity_sso/controller/HomeController.java"
    "src/main/java/sn/sdley/springsecurity_sso/controller/UserRestController.java"
)

for file in "${JAVA_FILES[@]}"; do
    if [ -f "$file" ]; then
        echo "  ✓ $file"
    else
        echo "  ✗ MISSING: $file"
        ERRORS=$((ERRORS + 1))
    fi
done

echo ""

# Check HTML Templates
echo "✓ Checking HTML Templates..."
TEMPLATES=(
    "src/main/resources/templates/index.html"
    "src/main/resources/templates/login.html"
    "src/main/resources/templates/home.html"
    "src/main/resources/templates/profile.html"
    "src/main/resources/templates/error.html"
)

for file in "${TEMPLATES[@]}"; do
    if [ -f "$file" ]; then
        echo "  ✓ $file"
    else
        echo "  ✗ MISSING: $file"
        ERRORS=$((ERRORS + 1))
    fi
done

echo ""

# Check Configuration Files
echo "✓ Checking Configuration Files..."
CONFIG_FILES=(
    "src/main/resources/application.properties"
    "src/main/resources/application.yml"
    "pom.xml"
)

for file in "${CONFIG_FILES[@]}"; do
    if [ -f "$file" ]; then
        echo "  ✓ $file"
    else
        echo "  ✗ MISSING: $file"
        ERRORS=$((ERRORS + 1))
    fi
done

echo ""

# Check Documentation
echo "✓ Checking Documentation..."
DOC_FILES=(
    "README.md"
    "SETUP_GUIDE.md"
    "IMPLEMENTATION_SUMMARY.md"
    "QUICK_REFERENCE.md"
)

for file in "${DOC_FILES[@]}"; do
    if [ -f "$file" ]; then
        echo "  ✓ $file"
    else
        echo "  ✗ MISSING: $file"
        ERRORS=$((ERRORS + 1))
    fi
done

echo ""

# Check Docker Files
echo "✓ Checking Docker Files..."
DOCKER_FILES=(
    "Dockerfile"
    "docker-compose.yml"
)

for file in "${DOCKER_FILES[@]}"; do
    if [ -f "$file" ]; then
        echo "  ✓ $file"
    else
        echo "  ✗ MISSING: $file"
        ERRORS=$((ERRORS + 1))
    fi
done

echo ""

# Check Other Important Files
echo "✓ Checking Other Files..."
OTHER_FILES=(
    ".env.example"
    "start.sh"
    "LICENSE"
)

for file in "${OTHER_FILES[@]}"; do
    if [ -f "$file" ]; then
        echo "  ✓ $file"
    else
        echo "  ✗ MISSING: $file"
        ERRORS=$((ERRORS + 1))
    fi
done

echo ""
echo "=============================================="

if [ $ERRORS -eq 0 ]; then
    echo "✓ SUCCESS: All files are present!"
    echo ""
    echo "Next Steps:"
    echo "1. Configure OAuth2 credentials (see SETUP_GUIDE.md)"
    echo "2. Run: ./mvnw spring-boot:run"
    echo "3. Visit: http://localhost:8080"
    echo ""
    echo "Or use the quick start script: ./start.sh"
    exit 0
else
    echo "✗ ERRORS FOUND: $ERRORS missing file(s)"
    exit 1
fi
