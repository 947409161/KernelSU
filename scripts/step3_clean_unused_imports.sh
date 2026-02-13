#!/bin/bash
set -e

echo "🧹 Step 3: Cleaning unused imports..."

cd "manager/app/src/main/java" || exit 1

# Remove any remaining Miuix imports
echo "🧹 Removing remaining Miuix imports..."
find . -name "*.kt" -type f -exec sed -i '/^import top\.yukonga\.miuix\./d' {} \;

# Remove any remaining Haze imports (except MonetColorsProvider)
echo "🧹 Removing remaining Haze imports..."
find . -name "*.kt" ! -name "MonetColorsProvider.kt" -type f -exec sed -i '/^import dev\.chrisbanes\.haze\./d' {} \;

# Remove any remaining Capsule imports
echo "🧹 Removing remaining Capsule imports..."
find . -name "*.kt" -type f -exec sed -i '/^import com\.kyant\.capsule\./d' {} \;

# Remove any remaining Miuix icon imports
echo "🧹 Removing remaining Miuix icon imports..."
find . -name "*.kt" -type f -exec sed -i '/^import top\.yukonga\.miuix\.kmp\.icon\./d' {} \;

echo "✅ Step 3 completed!"
echo "🧹 Unused imports cleaned"
