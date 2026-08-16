# AutoGenesis Sample App (Android + iOS Monorepo)

Microsoft AutoGenesis の動作検証用サンプル。**BrowserStack 不使用のローカル完結**構成。

## Quick Start
```
npm install -g appium@2
appium driver install uiautomator2
appium driver install xcuitest
brew install xcodegen

# Copilot Chat: /scaffold-project
cd android && ./gradlew assembleDebug
cd ios && xcodegen generate && xcodebuild -scheme SampleApp -sdk iphonesimulator build

appium   # 別ターミナル
# Copilot Chat: "use autoGenesis-run skill and execute scenario 'Successful login with valid credentials'"
```

## 補助ドキュメント
- docs/UI_STATE_MATRIX.md : 状態別 (Loading/Empty/Success/Error) 表示
- docs/GIVEN_STATE_LIBRARY.md : 共通 Given カタログ
- docs/screenshots/ : 5画面ワイヤーフレーム (Copilot vision 用)

## 詳細は AGENTS.md を参照
