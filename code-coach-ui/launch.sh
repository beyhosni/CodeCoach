#!/bin/bash

# Code Coach Frontend - Launch Script
# Usage: ./launch.sh

echo "🎓 Code Coach Frontend MVP - Launcher"
echo "======================================"
echo ""

# Check Node.js
if ! command -v node &> /dev/null; then
    echo "❌ Node.js not found. Please install Node.js 18+"
    exit 1
fi

NODE_VERSION=$(node -v)
echo "✅ Node.js $NODE_VERSION detected"

# Check npm
if ! command -v npm &> /dev/null; then
    echo "❌ npm not found"
    exit 1
fi

NPM_VERSION=$(npm -v)
echo "✅ npm $NPM_VERSION detected"
echo ""

# Install dependencies
if [ ! -d "node_modules" ]; then
    echo "📦 Installing dependencies..."
    npm install
    echo "✅ Dependencies installed"
else
    echo "✅ Dependencies already installed"
fi

echo ""
echo "🚀 Starting development server..."
echo "======================================"
echo ""
echo "📍 Frontend will open at: http://localhost:3000"
echo "📍 Make sure backend is running at: http://localhost:8080"
echo ""
echo "Press Ctrl+C to stop the server"
echo ""

# Start dev server
npm run dev
