# AGENTS.md — AutoGenesis Sample App

> AIコーディングエージェント向けの運用マニュアル。まずこのファイルを読み、次に
> `docs/BASIC_DESIGN.md` と `.github/instructions/` を参照すること。

## 1. Project Purpose
Microsoft AutoGenesis (https://github.com/microsoft/AutoGenesis) の**動作検証用サンプルモバイルアプリ**。
Android(Kotlin/Compose) と iOS(Swift/SwiftUI) のネイティブ実装をモノレポで管理。
`behave-demo/features/` の Gherkin シナリオを AutoGenesis で自動生成・実行できる状態を目指す。
**外部クラウド (BrowserStack 等) は使用しない。ローカル Appium のみ。**

## 2. Directory Map
| Path | Role |
|---|---|
| android/ | Kotlin + Jetpack Compose |
| ios/ | Swift + SwiftUI |
| behave-demo/features/ | AutoGenesis 互換 Gherkin + step 出力先 |
| config/ | Appium / MCP のローカル設定 |
| docs/ | 設計ドキュメント (単一情報源) |
| docs/screenshots/ | 画面ワイヤーフレーム (Copilot vision 用) |
| .github/instructions/ | パス別 Copilot ルール (applyTo) |
| .github/workflows/ | CI 雛形 |

## 3. Standard Commands
### Android
```
cd android && ./gradlew assembleDebug
cd android && ./gradlew installDebug
cd android && ./gradlew test
```
### iOS
```
cd ios && xcodegen generate        # project.yml から SampleApp.xcodeproj を生成
cd ios && xcodebuild -scheme SampleApp -sdk iphonesimulator -destination 'platform=iOS Simulator,name=iPhone 15' build
cd ios && xcodebuild -scheme SampleApp test
```
### AutoGenesis E2E
```
appium  # 別ターミナルで
# Copilot Chat: "use autoGenesis-run skill and execute scenario 'Successful login with valid credentials'"
uv run python -m behave --tags=@smoke
```

## 4. Boundaries (DO / DO NOT)
**DO**
- accessibility ID は docs/TESTID_CATALOG.md を単一情報源
- Android: Modifier.testTag(TestIds.XXX)
- iOS: .accessibilityIdentifier(TestIds.xxx)
- 画面ルートに SCREEN_ROOT_* を付与
- UI 文字列は res/values/strings.xml と Localizable.strings を単一情報源

**DO NOT**
- 要素特定に文字列直書き禁止
- BrowserStack / SauceLabs 等クラウド capability 禁止
- behave-demo/features/steps/ は AutoGenesis 出力領域 (手動編集禁止)
- アプリコードに実行時 LLM 呼び出しロジックを含めない

## 5. Where AutoGenesis Reads/Writes
| Kind | Path | Direction |
|---|---|---|
| Gherkin | behave-demo/features/*.feature | Read |
| Generated step | behave-demo/features/steps/*_steps.py | Write |
| Appium capability | config/appium_conf.*.json | Read |
| MCP config | .vscode/mcp.json | Read |

OS別のAutoGenesis生成先は `behave-demo/features/android_steps/` と
`behave-demo/features/ios_steps/`。どちらも手動編集禁止。

`vendor/AutoGenesis/` は `THIRD_PARTY_NOTICES.md` に記録された固定revisionの
上流snapshot。直接編集せず、プロジェクト固有のadapterは `scripts/autogenesis/` に置く。

## 6. Personas
- @android-agent : Kotlin/Compose 担当
- @ios-agent     : Swift/SwiftUI 担当
- @feature-agent : Gherkin 担当

## 7. First-Read Order
1. AGENTS.md -> 2. docs/BASIC_DESIGN.md -> 3. docs/TESTID_CATALOG.md
-> 4. docs/UI_STATE_MATRIX.md -> 5. docs/GIVEN_STATE_LIBRARY.md
-> 6. .github/copilot-instructions.md -> 7. 対象パス下の .github/instructions/*.instructions.md
