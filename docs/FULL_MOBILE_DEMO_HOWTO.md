# Full Mobile Demo HowTo

この手順は、fresh cloneしたリポジトリで全Gherkin scenarioを依存順に実装し、単体テストとローカルAppiumを使ったAutoGenesis/Behaveテストを生成・実行するためのものです。実行はCopilot Chatの`/full-mobile-demo` promptが担当し、クラウドdeviceは使用しません。

## 完成対象

`config/full_mobile_demo_plan.json`がデモの唯一の実行計画です。現在は3 feature・5 scenarioを次の順で扱います。

| 順序 | Feature | Scenario | 主な単体テスト観点 |
|---:|---|---|---|
| 1 | login | Successful login with valid credentials | 正しい認証情報で一覧へ遷移する |
| 2 | login | Failed login shows error message | 不正な認証情報でError状態になる |
| 3 | list_and_detail | Navigate to item detail from list | 先頭行の選択で詳細へ遷移する |
| 4 | list_and_detail | Toggle favorite on item detail | お気に入り状態がonへ変わる |
| 5 | settings | Sign out returns to login | サインアウトでログインへ戻る |

`login`は後続featureのGivenに必要です。`list_and_detail`と`settings`はログインを前提にするため、計画の順番を変更しません。

## 初回セットアップ

macOSでNode.js 20+、Python 3.10+、Android Studio、Xcodeを用意します。まずリポジトリをcloneしてrootへ移動します。

```bash
git clone <repository-url> MobileAutoGenesisSample
cd MobileAutoGenesisSample
```

次にリポジトリrootで実行します。

```bash
brew install uv xcodegen
npm install -g appium@2
appium driver install uiautomator2
appium driver install xcuitest

export ANDROID_HOME="$HOME/Library/Android/sdk"
export ANDROID_SDK_ROOT="$ANDROID_HOME"
export PATH="$ANDROID_HOME/platform-tools:$PATH"

(cd behave-demo && uv sync)
(cd vendor/AutoGenesis/appium-mcp-server && uv sync)
(cd android && ./gradlew assembleDebug)
(cd ios && xcodegen generate && xcodebuild -project SampleApp.xcodeproj -scheme SampleApp -sdk iphonesimulator -destination 'platform=iOS Simulator,name=iPhone 15' -derivedDataPath build CODE_SIGNING_ALLOWED=NO build)

python3 scripts/autogenesis/verify_demo_plan.py --print
```

Android StudioのDevice ManagerでAndroid emulatorを起動し、Simulator.appで`iPhone 15` simulatorを起動します。各端末を準備した後に、別ターミナルでローカルAppiumを起動します。

```bash
appium
```

次の確認が両方成功すれば、デモを開始できます。

```bash
./scripts/autogenesis/preflight.sh android
./scripts/autogenesis/preflight.sh ios
```

## 一括デモの実行

VS Codeでこのrepositoryを開き、Copilot Chatから次を1回実行します。

```text
/full-mobile-demo platform=both
```

一方のOSだけを対象にするときは、`platform=android`または`platform=ios`を指定します。promptは計画を検証してから、featureごとに次を直列に完了します。

1. Gherkin、状態マトリクス、Test IDカタログ、ワイヤーフレームを根拠にプロダクトコードを実装する。
2. Android/iOSそれぞれでScenarioの成功・失敗・状態遷移を検証する単体テストを追加し、unit testとdebug buildを通す。
3. 各scenarioをAutoGenesisで実行し、OS別のBehave stepを生成する。
4. 生成したscenarioをBehaveで再実行する。
5. 全scenario完了後に`@android`と`@ios`をそれぞれ全件実行する。

## 実行結果の確認

最終的に、対象OSごとに以下が成功していることを確認します。

```bash
cd android && ./gradlew test assembleDebug

cd ios && xcodegen generate && xcodebuild -project SampleApp.xcodeproj -scheme SampleApp -destination 'platform=iOS Simulator,name=iPhone 15' test

cd behave-demo && AUTOGENESIS_PLATFORM=android uv run python -m behave --stage android --tags=@android
cd behave-demo && AUTOGENESIS_PLATFORM=ios uv run python -m behave --stage ios --tags=@ios
```

生成済みstepは`behave-demo/features/android_steps/`および`behave-demo/features/ios_steps/`に出力されます。これらはAutoGenesisの出力領域であり、手動では変更しません。

## 中断と再開

feature内で失敗した場合、CopilotはそのscenarioのプロダクトコードまたはTest IDを修正し、unit testとbuildを通過させてからAutoGenesis生成とBehaveを再実行します。後続featureには進めません。

作業を中断した場合も、同じ`/full-mobile-demo` promptを再実行します。成功済みのfeatureを変更せず、失敗したfeatureから継続するよう、最初に現在のテストと生成済みstepを確認します。Gherkinを変更する必要がある仕様変更は、実装前に明示的に確認します。

## 計画を変更するとき

featureまたはscenarioを追加、削除、改名したら、同じ変更で`config/full_mobile_demo_plan.json`を更新します。その後に必ず次を実行します。

```bash
python3 scripts/autogenesis/verify_demo_plan.py --print
```

この検証が失敗している間は、全件デモを開始しません。