# AutoGenesis Sample App (Android + iOS Monorepo)

Microsoft AutoGenesis の動作検証用サンプル。**BrowserStack 不使用のローカル完結**構成。

## Quick Start
```bash
npm install -g appium@2
appium driver install uiautomator2
appium driver install xcuitest
brew install xcodegen
brew install uv

cd android && ./gradlew assembleDebug
cd ../ios && xcodegen generate
xcodebuild -project SampleApp.xcodeproj -scheme SampleApp \
	-sdk iphonesimulator -derivedDataPath build CODE_SIGNING_ALLOWED=NO build

cd ../behave-demo && uv sync
cd ../vendor/AutoGenesis/appium-mcp-server && uv sync

appium   # 別ターミナル

# 実行前確認
scripts/autogenesis/preflight.sh android
scripts/autogenesis/preflight.sh ios
```

Copilot Chatで次のPromptを実行します。

```text
/generate-mobile-steps platform=android scenario_name="Successful login with valid credentials"
/generate-mobile-steps platform=ios scenario_name="Successful login with valid credentials"
```

生成後はplatform別のBehave stageで再実行します。

```bash
cd behave-demo
AUTOGENESIS_PLATFORM=android uv run python -m behave --stage android --name "Successful login with valid credentials"
AUTOGENESIS_PLATFORM=ios uv run python -m behave --stage ios --name "Successful login with valid credentials"
```

AutoGenesis本体は固定revisionで `vendor/AutoGenesis/` に含まれます。外部cloneや
`AUTOGENESIS_HOME` は不要です。取得元とrevisionは `THIRD_PARTY_NOTICES.md` を参照してください。

## 補助ドキュメント
- docs/UI_STATE_MATRIX.md : 状態別 (Loading/Empty/Success/Error) 表示
- docs/GIVEN_STATE_LIBRARY.md : 共通 Given カタログ
- docs/screenshots/ : 5画面ワイヤーフレーム (Copilot vision 用)
- docs/ENVIRONMENT.md : AutoGenesis/Appiumの詳細セットアップ
- THIRD_PARTY_NOTICES.md : vendored AutoGenesisの出所と固定revision

## 詳細は AGENTS.md を参照
