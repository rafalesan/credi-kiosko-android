#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
export ANDROID_HOME="${ANDROID_HOME:-$HOME/Android/Sdk}"
export ANDROID_SDK_ROOT="$ANDROID_HOME"
CMDTOOLS_DIR="$ANDROID_HOME/cmdline-tools/latest"
SDKMANAGER=""

mkdir -p "$ANDROID_HOME"

if [[ ! -x "$CMDTOOLS_DIR/bin/sdkmanager" ]]; then
  TMP_ZIP="$(mktemp /tmp/cmdline-tools.XXXXXX.zip)"
  TMP_DIR="$(mktemp -d /tmp/cmdline-tools.XXXXXX)"
  curl -fsSL -o "$TMP_ZIP" "https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip"
  unzip -q "$TMP_ZIP" -d "$TMP_DIR"
  rm -rf "$CMDTOOLS_DIR"
  mkdir -p "$ANDROID_HOME/cmdline-tools"
  mv "$TMP_DIR/cmdline-tools" "$CMDTOOLS_DIR"
  rm -rf "$TMP_ZIP" "$TMP_DIR"
fi

SDKMANAGER="$CMDTOOLS_DIR/bin/sdkmanager"
export PATH="$CMDTOOLS_DIR/bin:$ANDROID_HOME/platform-tools:$PATH"

yes | "$SDKMANAGER" --licenses >/tmp/android-sdk-licenses.log 2>&1 || true
"$SDKMANAGER" --install \
  "platform-tools" \
  "platforms;android-35" \
  "build-tools;35.0.0"

mkdir -p "$ROOT/.ci"
KEYSTORE="$ROOT/.ci/debug-ci.keystore"
if [[ ! -f "$KEYSTORE" ]]; then
  keytool -genkeypair \
    -keystore "$KEYSTORE" \
    -storepass android \
    -keypass android \
    -alias androiddebugkey \
    -keyalg RSA \
    -keysize 2048 \
    -validity 10000 \
    -dname "CN=CI,OU=CI,O=CrediKiosko,L=CI,ST=CI,C=US"
fi

cat > "$ROOT/local.properties" <<EOF
sdk.dir=$ANDROID_HOME
storeFile=$KEYSTORE
storePassword=android
keyAlias=androiddebugkey
keyPassword=android
EOF

if [[ -n "${GOOGLE_SERVICES_JSON:-}" ]]; then
  printf '%s\n' "$GOOGLE_SERVICES_JSON" > "$ROOT/app/google-services.json"
else
  cat > "$ROOT/app/google-services.json" <<'EOF'
{
  "project_info": {
    "project_number": "000000000000",
    "project_id": "credikiosko-ci-placeholder",
    "storage_bucket": "credikiosko-ci-placeholder.appspot.com"
  },
  "client": [
    {
      "client_info": {
        "mobilesdk_app_id": "1:000000000000:android:aaaaaaaaaaaaaaaaaaaaaaaa",
        "android_client_info": {
          "package_name": "com.rafalesan.credikiosko"
        }
      },
      "oauth_client": [],
      "api_key": [{ "current_key": "AIzaSyCiPlaceholderKeyDoNotUseInProd000" }],
      "services": { "appinvite_service": { "other_platform_oauth_client": [] } }
    },
    {
      "client_info": {
        "mobilesdk_app_id": "1:000000000000:android:bbbbbbbbbbbbbbbbbbbbbbbb",
        "android_client_info": {
          "package_name": "com.rafalesan.credikiosko.dev"
        }
      },
      "oauth_client": [],
      "api_key": [{ "current_key": "AIzaSyCiPlaceholderKeyDoNotUseInProd000" }],
      "services": { "appinvite_service": { "other_platform_oauth_client": [] } }
    }
  ],
  "configuration_version": "1"
}
EOF
fi

cd "$ROOT"
./gradlew :app:assembleDevDebug --quiet
