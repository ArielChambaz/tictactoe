#!/bin/bash

# Create output directory
mkdir -p out

echo "🔧 Compiling Java files..."
javac -d out *.java
if [ $? -ne 0 ]; then
    echo "❌ Compilation failed."
    exit 1
fi

echo "📁 Copying resources..."
cp -r src/audio out/
cp -r src/images out/

echo "🚀 Launching the game..."
(cd out && java GameMain)
