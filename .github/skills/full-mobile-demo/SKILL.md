---
name: full-mobile-demo
description: '全Gherkinシナリオを依存順にAndroid/iOSへ実装し、単体テストとローカルAppiumによるAutoGenesis/Behaveテストを完了する。Use when: full mobile demo, implement all scenarios, all Appium tests, end-to-end mobile demo.'
argument-hint: 'platform=android|ios|both'
user-invocable: true
---

# Full Mobile Demo

clone直後の未実装状態から、`config/full_mobile_demo_plan.json` に定義された全scenarioを、依存順に実装・単体テスト・AutoGenesis生成・Behave再実行まで完了させる。

## Input

- `platform`: `android`、`ios`、または`both`。省略時は`both`として扱う。

## Authoritative Sources

開始時に次を順に読む。矛盾があれば、そのfeatureの実装前にユーザーへ確認する。

1. `AGENTS.md`
2. `docs/BASIC_DESIGN.md`
3. `docs/TESTID_CATALOG.md`
4. `docs/UI_STATE_MATRIX.md`
5. `docs/GIVEN_STATE_LIBRARY.md`
6. `config/full_mobile_demo_plan.json`
7. 各対象featureと対応するワイヤーフレーム
8. 対象パスに適用される`.github/instructions/*.instructions.md`

## Required Order

`config/full_mobile_demo_plan.json`の順序だけを使用する。現時点の順序は次のとおり。

1. `login`: Successful login with valid credentials, Failed login shows error message
2. `list_and_detail`: Navigate to item detail from list, Toggle favorite on item detail
3. `settings`: Sign out returns to login

前のfeatureが通過するまで次のfeatureへ進まない。`list_and_detail`と`settings`のBackgroundはログイン機能に依存するため、順序の変更は禁止する。

## Procedure

1. 最初に`python3 scripts/autogenesis/verify_demo_plan.py --print`を実行する。失敗時は実装・生成を開始しない。
2. 対象OSの単体テスト実行基盤を最初のfeatureより前に確認する。不足時は、Androidでは`src/test`とJUnit依存関係、iOSでは`SampleAppTests`のunit-test targetを追加する。UIテストtargetは単体テストの代替にしない。
3. 計画内の各featureについて、`implement-mobile-from-spec` skillの手順を対象platformへ適用する。カタログ、両OSのTestIds、ローカライズ文字列を先に更新し、MockRepositoryだけを使う。
4. featureごとに、そのfeatureの全scenarioを満たすViewModelまたはRepositoryの単体テストをAndroid/iOSへ追加する。成功・失敗・状態遷移を観測可能な振る舞いとして検証する。
5. featureの実装後、対象OSの単体テストとdebug buildを成功させる。失敗時は同じfeatureだけを修正して再実行する。
   - Android: `cd android && ./gradlew test assembleDebug`
   - iOS: `cd ios && xcodegen generate && xcodebuild -project SampleApp.xcodeproj -scheme SampleApp -destination 'platform=iOS Simulator,name=iPhone 15' test`
6. build成果物ができ、ローカルAppiumが起動していることを確認する。platformごとに`./scripts/autogenesis/preflight.sh <platform>`を実行し、失敗時は環境を修正して再実行する。
7. 現在のfeatureの各scenarioを記載順に、platformごとに`autoGenesis-run` skillで実行する。生成先はAndroidが`behave-demo/features/android_steps/<feature>.py`、iOSが`behave-demo/features/ios_steps/<feature>.py`である。生成領域を手動編集しない。
8. scenarioを生成するたびに、同じplatformとscenario名でBehaveを再実行する。失敗した場合はプロダクトコード、Test ID、またはfeature仕様との不一致を修正し、単体テストとbuildを通してからそのscenarioを再生成する。
   - Android: `cd behave-demo && AUTOGENESIS_PLATFORM=android uv run python -m behave --stage android --name "<Scenario name>"`
   - iOS: `cd behave-demo && AUTOGENESIS_PLATFORM=ios uv run python -m behave --stage ios --name "<Scenario name>"`
9. 全feature完了後、platformごとにタグ指定のBehave全件実行を行う。
   - Android: `cd behave-demo && AUTOGENESIS_PLATFORM=android uv run python -m behave --stage android --tags=@android`
   - iOS: `cd behave-demo && AUTOGENESIS_PLATFORM=ios uv run python -m behave --stage ios --tags=@ios`
10. 各featureについて、実装ファイル、追加した単体テスト、生成ファイル、unit/build/Behave結果を報告する。最後に5 scenarioすべての実行結果を一覧化する。

## Recovery Rules

- 失敗したscenarioだけを修正・再生成・再実行し、成功済みfeatureを作り直さない。
- featureのGherkinを変更しない。仕様変更が必要なら停止して確認する。
- Appiumのセッション、emulator、Simulatorを無断で切り替えない。
- 生成済みの`android_steps/`と`ios_steps/`を手動修正しない。生成の再実行だけで更新する。
- BrowserStack、SauceLabs、実ネットワーク、実行時LLMは使用しない。

## Completion Criteria

- デモ計画の検証が成功する。
- 対象OSで全featureの単体テストとdebug buildが成功する。
- 各platformで5 scenarioすべてについてAutoGenesisのpreview/confirmとBehave再実行が成功する。
- 最後の`@android`または`@ios`のBehave全件実行が成功する。