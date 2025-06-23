#!/bin/bash

JAR_PATH="libs/json-simple-1.1.1.jar"

# Create output directory
mkdir -p out

echo "🔧 Compiling Java files..."
javac -cp ".:$JAR_PATH" -d out *.java
if [ $? -ne 0 ]; then
    echo "❌ Compilation failed."
    exit 1
fi

echo "📁 Copying resources..."
cp -r src/audio out/
cp -r src/images out/

echo "🚀 Launching the game..."
(cd out && java -cp ".:../$JAR_PATH" MainMenu)
