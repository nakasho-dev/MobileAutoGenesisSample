# ENVIRONMENT.md

## 前提
macOS, Node.js 20+, Python 3.10+, uv, Android Studio, Xcode, XcodeGen

## セットアップ
```
npm install -g appium@2
appium driver install uiautomator2
appium driver install xcuitest
brew install xcodegen        # iOS プロジェクト生成用

git clone https://github.com/microsoft/AutoGenesis.git
cd AutoGenesis/appium-mcp-server && uv sync

code --install-extension ms-edge-qa.bdd-ai-toolkit

cd android && ./gradlew assembleDebug
cd ../ios && xcodegen generate && xcodebuild -scheme SampleApp -sdk iphonesimulator build

appium  # 別ターミナル
# Copilot Chat: "use autoGenesis-run skill and execute scenario 'Successful login with valid credentials'"
```

## Gradle Wrapper の jar について
`android/gradle/wrapper/gradle-wrapper.jar` はバイナリのため本リポジトリには含めていません。
初回のみ以下のいずれかで生成してください:
- Android Studio でプロジェクトを開く (自動生成)
- `gradle wrapper --gradle-version 8.5` を実行
