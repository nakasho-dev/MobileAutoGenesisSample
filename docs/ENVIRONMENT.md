# ENVIRONMENT.md

## 前提
macOS, Node.js 20+, Python 3.10+, uv, Android Studio, Xcode, XcodeGen

## 共通セットアップ

```bash
npm install -g appium@2
appium driver install uiautomator2
appium driver install xcuitest
brew install uv xcodegen

cd behave-demo && uv sync
cd ../vendor/AutoGenesis/appium-mcp-server && uv sync
```

AutoGenesisは `vendor/AutoGenesis/` に固定revisionで含まれています。
外部cloneと `AUTOGENESIS_HOME` は使用しません。revisionとライセンスは
`THIRD_PARTY_NOTICES.md` に記録しています。

## Android

```bash
cd android && ./gradlew assembleDebug
cd ..

export ANDROID_HOME="$HOME/Library/Android/sdk"
export ANDROID_SDK_ROOT="$ANDROID_HOME"
export PATH="$ANDROID_HOME/platform-tools:$PATH"

appium  # 別ターミナルで起動
scripts/autogenesis/preflight.sh android
```

Copilot Chat:

```text
/generate-mobile-steps platform=android scenario_name="Successful login with valid credentials"
```

生成先は `behave-demo/features/android_steps/<feature名>.py` です。

```bash
cd behave-demo
AUTOGENESIS_PLATFORM=android uv run python -m behave --stage android --name "Successful login with valid credentials"
```

## iOS Simulator

利用可能なSimulatorを起動してから、同じdestination向けにbuildします。

```bash
cd ios
xcodegen generate
xcrun simctl list devices available
open -a Simulator
xcodebuild -project SampleApp.xcodeproj -scheme SampleApp \
	-sdk iphonesimulator -destination 'platform=iOS Simulator,name=iPhone 16 Pro' \
	-derivedDataPath build CODE_SIGNING_ALLOWED=NO build
cd ..

appium  # 別ターミナルで起動
scripts/autogenesis/preflight.sh ios
```

起動中Simulatorの名前とUDIDはMCP起動時に自動検出されます。

Copilot Chat:

```text
/generate-mobile-steps platform=ios scenario_name="Successful login with valid credentials"
```

生成先は `behave-demo/features/ios_steps/<feature名>.py` です。

```bash
cd behave-demo
AUTOGENESIS_PLATFORM=ios uv run python -m behave --stage ios --name "Successful login with valid credentials"
```

## MCP設定

`.vscode/mcp.json` は次のstdio serverを定義します。

- `auto-genesis-mcp-android`
- `auto-genesis-mcp-ios`

両serverは `scripts/autogenesis/start-mcp.sh` を経由して、vendored
`appium-mcp-server/simple_server.py` を起動します。Appium本体はローカルの
`http://127.0.0.1:4723` だけを使用します。BrowserStackやSauceLabsは使用しません。

## 全scenarioデモ

fresh clone後に全Gherkin scenarioを実装、単体テスト、AutoGenesis生成、Behave再実行まで実施する手順は [FULL_MOBILE_DEMO_HOWTO.md](FULL_MOBILE_DEMO_HOWTO.md) を参照します。

実行計画は `config/full_mobile_demo_plan.json` に定義し、実際のfeatureとの一致は次で確認します。

```bash
python3 scripts/autogenesis/verify_demo_plan.py --print
```

環境を準備してlocal Appiumを起動した後、Copilot Chatで実行します。

```text
/full-mobile-demo platform=both
```

## AutoGenesis更新

現在のrevisionは `158020978f651834912ea867b356845549f7a032` です。

1. `vendor/AutoGenesis/` にローカル変更がないことを確認します。
2. 現在のdirectoryをreview後に削除します。
3. `scripts/autogenesis/update-vendor.sh <commit-sha>` を実行します。
4. `THIRD_PARTY_NOTICES.md` のrevisionと取得日を更新します。
5. Android/iOSのpreflight、MCP tool一覧、生成と再実行を検証します。

`vendor/AutoGenesis/` は上流snapshotなので直接編集しません。プロジェクト固有の
設定やadapterは `config/` と `scripts/autogenesis/` に置きます。

## Gradle Wrapper の jar について
`android/gradle/wrapper/gradle-wrapper.jar` はバイナリのため本リポジトリには含めていません。
初回のみ以下のいずれかで生成してください:
- Android Studio でプロジェクトを開く (自動生成)
- `gradle wrapper --gradle-version 8.5` を実行
