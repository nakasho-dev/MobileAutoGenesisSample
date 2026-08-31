---
name: implement-mobile-from-spec
description: 'Gherkinシナリオと画面ワイヤーフレームから、Android Kotlin/Compose・iOS SwiftUIのプロダクトコードと単体テストを実装し、ローカルAppiumでBehave stepを生成する。Use when: implement mobile feature from Gherkin, wireframe to Android iOS, generate mobile app code and tests, create Appium steps.'
argument-hint: 'feature=<feature-stem> platform=android|ios|both'
user-invocable: true
---

# Implement Mobile From Specification

Gherkinシナリオと`docs/screenshots/`の画面ワイヤーフレームを仕様として、Android/iOSの同等な機能を実装・単体テストし、最後にローカルAppiumでBehave stepを生成する。

## Inputs

- `feature`: `behave-demo/features/<feature>.feature`の拡張子を除いた名前。
- `platform`: `android`、`ios`、または`both`。省略時は実装対象を確認してから開始する。

## Authoritative Sources

実装前に次を読む。矛盾があれば実装を止め、ユーザーに確認する。

1. `AGENTS.md`
2. `docs/BASIC_DESIGN.md`
3. `docs/TESTID_CATALOG.md`
4. `docs/UI_STATE_MATRIX.md`
5. `docs/GIVEN_STATE_LIBRARY.md`
6. `behave-demo/features/<feature>.feature`
7. `docs/screenshots/README.md`と対象画面のPNG
8. 対象パスに適用される`.github/instructions/*.instructions.md`

## Procedure

1. 指定featureファイルを一意に特定し、Backgroundを含む全Scenarioを抽出する。対象画面、遷移、入力、期待結果を一覧化する。
2. ワイヤーフレームを確認し、Gherkinで観測できる要素と画面構成を対応付ける。既存画面を変更する場合は、その実装と既存の単体テストを先に読む。
3. `docs/TESTID_CATALOG.md`に必要なTest IDがあることを確認する。不足する場合は、カタログとAndroid/iOS両方の`TestIds`を先に追加する。UI文字列はAndroidの`strings.xml`とiOSの`Localizable.strings`に追加し、UIコードへ直書きしない。
4. Androidを対象に含む場合、MVVM + Composeで実装する。状態は`sealed interface`で表現し、各画面のrootと操作要素に`Modifier.testTag(TestIds.XXX)`を付ける。RepositoryはMockRepositoryだけを使用する。
5. iOSを対象に含む場合、SwiftUIで同等の画面・状態・遷移を実装する。状態は関連値を持つenumで表現し、各画面のrootと操作要素に`.accessibilityIdentifier(TestIds.xxx)`を付ける。RepositoryはMockRepositoryだけを使用する。
6. 各対象OSに、Scenarioが要求する成功・失敗・状態遷移を検証する単体テストを追加する。実装詳細ではなくViewModelまたはRepositoryの観測可能な振る舞いを検証する。
7. 対象OSをビルドし、追加した単体テストを実行する。失敗した場合は、そのOSの実装またはテストを修正して同じ確認を再実行する。
   - Android: `cd android && ./gradlew test assembleDebug`
   - iOS: `cd ios && xcodegen generate && xcodebuild -scheme SampleApp test`
8. 実装と単体テストが成功した後、対象platformごとに`autoGenesis-run` skillを使用し、feature内の各ScenarioのBehave stepを生成する。
   - 実行前に`./scripts/autogenesis/preflight.sh <platform>`を通す。
   - ローカルAppiumだけを使用する。BrowserStack、SauceLabs、その他クラウドdeviceは使用しない。
   - `android_steps/`と`ios_steps/`は出力領域なので、生成後に手動編集しない。
9. 生成済みstepを含めて、feature単位のBehave実行を行う。
   - Android: `cd behave-demo && AUTOGENESIS_PLATFORM=android uv run python -m behave --stage android --name "<Scenario>"`
   - iOS: `cd behave-demo && AUTOGENESIS_PLATFORM=ios uv run python -m behave --stage ios --name "<Scenario>"`
10. 変更した実装、テスト、生成済みstep、実行した検証と結果を報告する。

## Constraints

- Android/iOSの観測可能な振る舞いとTest ID実値を一致させる。
- accessibility IDの単一情報源は`docs/TESTID_CATALOG.md`とする。
- 画面rootには`SCREEN_ROOT_*`を必ず付与する。
- 依存ライブラリ、実行時LLM、実ネットワーク、BrowserStack/SauceLabsを追加しない。
- Gherkinのkeyword、step本文、順序を変更しない。仕様変更が必要な場合は、実装前に確認する。
- プラットフォームごとに、ビルドと単体テストが通るまでAppium step生成へ進まない。
- `platform=both`では、一方の実装をもう一方の根拠にせず、共通仕様資料を根拠として同等の振る舞いを実装する。