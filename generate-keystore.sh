#!/bin/bash
# 生成测试用的 keystore.jks
# 警告：这个 keystore 仅用于开发测试！
# 正式发布请使用正式的签名文件！

set -e

if [ -f "keystore.jks" ]; then
    echo "❌ keystore.jks 已经存在"
    exit 1
fi

echo "🔑 生成测试用 keystore.jks..."

# 使用 JDK 17 的 keytool
export JAVA_HOME="$JAVA_HOME"
KEYTOOL="$JAVA_HOME/bin/keytool"

if [ ! -f "$KEYTOOL" ]; then
    # 尝试找到 keytool
    KEYTOOL="$(find /usr -name keytool 2>/dev/null | head -1)"
fi

if [ ! -f "$KEYTOOL" ]; then
    echo "❌ 找不到 keytool"
    echo "请手动运行："
    echo "  $ keytool -genkey -v -keystore keystore.jks -alias key0 -keyalg RSA -keysize 2048 -validity 10000 -storepass your_password -keypass your_password -dname \"CN=Test Key, O=KernelSU, OU=Dev, L=Beijing, ST=Beijing, C=CN\""
    exit 1
fi

# 生成密钥对
KEY_PASSWORD="test123"
STORE_PASSWORD="test123"
KEY_ALIAS="key0"

# 生成 keystore
"$KEYTOOL" -genkey \
    -v \
    -keystore keystore.jks \
    -alias "$KEY_ALIAS" \
    -keyalg RSA \
    -keysize 2048 \
    -validity 10000 \
    -storepass "$STORE_PASSWORD" \
    -keypass "$KEY_PASSWORD" \
    -dname "CN=Test Key, O=KernelSU, OU=Dev, L=Beijing, ST=Beijing, C=CN" \
    -startdate "$(date +%Y%m%d010000Z)" \
    -ext SAN=dns:kernel-su.local

echo "✅ keystore.jks 生成完成！"
echo ""
echo "⚠️  重要提示："
echo "1. 这个 keystore 仅用于开发测试"
echo "2. 正式发布请使用正式签名文件"
echo "3. 密码都是测试用的（test123）"
echo "4. 切勿提交到 Git 或上传到 GitHub"
echo ""
echo "📋 生成的环境变量（用于本地构建）："
echo "export STORE_PASSWORD=$STORE_PASSWORD"
echo "export KEY_ALIAS=$KEY_ALIAS"
echo "export KEY_PASSWORD=$KEY_PASSWORD"
