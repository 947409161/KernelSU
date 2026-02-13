#!/bin/bash
set -e

echo "🚀 Starting complete Miuix to Material 3 migration..."
echo ""

# Check if we're in the right directory
if [ ! -d "manager" ]; then
    echo "❌ Error: Please run from KernelSU root directory"
    exit 1
fi

# Create backup branch
echo "📦 Creating backup branch..."
git checkout -b miuix-migration-backup || true
git add -A
git commit -m "Backup before Miuix migration" || true

# Run step 1
echo ""
echo "════════════════════════════════"
echo "Step 1: Replacing imports..."
echo "════════════════════════════════"
bash scripts/step1_replace_imports.sh

# Run step 2
echo ""
echo "════════════════════════════════"
echo "Step 2: Removing Haze/Capsule..."
echo "════════════════════════════════"
bash scripts/step2_remove_haze_capsule.sh

# Run step 3
echo ""
echo "════════════════════════════════"
echo "Step 3: Cleaning imports..."
echo "════════════════════════════════"
bash scripts/step3_clean_unused_imports.sh

echo ""
echo "✅ Automated migration completed!"
echo ""
echo "📋 Next steps:"
echo "   1. Review changes: git diff"
echo "   2. Test build: cd manager && ./gradlew assembleDebug"
echo "   3. Fix any compilation errors manually"
echo "   4. Test app functionality"
echo ""
echo "💡 Tip: Use 'git checkout -b miuix-migration-backup' to rollback if needed"
