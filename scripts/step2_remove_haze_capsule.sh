#!/bin/bash
set -e

echo "🌫 Step 2: Removing Haze and Capsule dependencies..."

cd "manager/app/src/main/java" || exit 1

# Remove Haze imports (except MonetColorsProvider which uses it internally)
echo "🌫 Removing Haze imports..."
find . -name "*.kt" ! -name "MonetColorsProvider.kt" -type f -exec sed -i '/^import dev\.chrisbanes\.haze\./d' {} \;

# Remove Haze usage patterns
echo "🌫 Removing Haze modifier usage..."
find . -name "*.kt" -type f -exec sed -i '/Modifier\.hazeChild([^)]*)/d' {} \;
find . -name "*.kt" -type f -exec sed -i '/hazeStyle = HazeStyle/d' {} \;
find . -name "*.kt" -type f -exec sed -i '/blurRadius = [0-9]\+\.dp/d' {} \;

# Remove Capsule imports
echo "🔲 Removing Capsule imports..."
find . -name "*.kt" -type f -exec sed -i '/^import com\.kyant\.capsule\./d' {} \;

# Replace Capsule shapes with Material shapes
echo "🔲 Replacing Capsule shapes..."
find . -name "*.kt" -type f -exec sed -i 's/shape = ContinuouslyRoundedRectangle([0-9]\+ percent)/shape = RoundedCornerShape(\1.percent)/g' {} \;

echo "✅ Step 2 completed!"
echo "⚠️  Haze and Capsule dependencies removed"
