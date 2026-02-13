#!/bin/bash
set -e

echo "🚀 Step 1: Replacing Miuix imports with Material 3..."

cd "manager/app/src/main/java" || exit 1

# Backup current state
echo "📦 Creating backup..."
git add -A || true
git stash save -u "before-miuix-migration" || true

# Replace Miuix basic imports with Material 3
echo "📦 Replacing Miuix basic -> Material 3..."
find . -name "*.kt" -type f -exec sed -i 's/import top\.yukonga\.miuix\.kmp\.basic\./import androidx\.compose\.material3\./g' {} \;

# Replace Miuix icons with Material Icons
echo "📦 Replacing MiuixIcons -> Material Icons..."
find . -name "*.kt" -type f -exec sed -i 's/import top\.yukonga\.miuix\.kmp\.icon\.MiuixIcons/import androidx\.compose\.material\.icons\.Icons/g' {} \;

# Replace Miuix theme imports
echo "📦 Replacing Miuix theme imports..."
find . -name "*.kt" -type f -exec sed -i 's/import top\.yukonga\.miuix\.kmp\.theme\./import androidx\.compose\.material3\./g' {} \;

# Replace component names
echo "📦 Replacing component names..."
find . -name "*.kt" -type f -exec sed -i 's/\bMiuixScaffold\b/Scaffold/g' {} \;
find . -name "*.kt" -type f -exec sed -i 's/\bMiuixTopAppBar\b/TopAppBar/g' {} \;
find . -name "*.kt" -type f -exec sed -i 's/\bMiuixCard\b/Card/g' {} \;
find . -name "*.kt" -type f -exec sed -i 's/\bMiuixText\b/Text/g' {} \;
find . -name "*.kt" -type f -exec sed -i 's/\bMiuixScrollBehavior\b/TopAppBarDefaults\.enterAlwaysScrollBehavior/g' {} \;

echo "✅ Step 1 completed!"
echo "⚠️  Please review git diff before proceeding to Step 2"
