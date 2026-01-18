#!/bin/bash

# Spring Security SSO - Quick Start Script
# This script helps you set up and run the application

echo "=================================="
echo "Spring Security SSO - Quick Start"
echo "=================================="
echo ""

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed. Please install Java 25 or higher."
    exit 1
fi

echo "✓ Java found: $(java -version 2>&1 | head -n 1)"
echo ""

# Check if .env file exists
if [ ! -f .env ]; then
    echo "⚠️  .env file not found!"
    echo "Creating .env from .env.example..."
    cp .env.example .env
    echo ""
    echo "Please edit .env file and add your OAuth2 credentials:"
    echo "  - GITHUB_CLIENT_ID"
    echo "  - GITHUB_CLIENT_SECRET"
    echo "  - GOOGLE_CLIENT_ID"
    echo "  - GOOGLE_CLIENT_SECRET"
    echo ""
    read -p "Press Enter after you've configured .env file..."
fi

# Load environment variables from .env
if [ -f .env ]; then
    echo "Loading environment variables from .env..."
    export $(cat .env | grep -v '^#' | xargs)
fi

echo ""
echo "Building the application..."
./mvnw clean install

if [ $? -eq 0 ]; then
    echo ""
    echo "✓ Build successful!"
    echo ""
    echo "Starting the application..."
    echo "Access the app at: http://localhost:8080"
    echo ""
    ./mvnw spring-boot:run
else
    echo ""
    echo "❌ Build failed. Please check the errors above."
    exit 1
fi
