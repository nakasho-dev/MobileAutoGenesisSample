---
name: autoGenesis-run
description: 'AutoGenesisとローカルAppiumでAndroidまたはiOSのGherkinシナリオを実行し、Behaveテストコードを自動生成する。Use when: execute scenario, generate test code, Appium, Android, iOS.'
argument-hint: 'platform=android|ios scenario_name="Scenario name"'
user-invocable: true
---

# AutoGenesis Mobile Run

## Inputs

- `platform`: `android` または `ios`。省略時は実行せずユーザーに確認する。
- `scenario_name`: `.feature` 内のScenario名と完全一致する文字列。

## Procedure

1. `behave-demo/features/**/*.feature` から `scenario_name` と完全一致するScenarioを一意に特定する。0件または複数件なら実行しない。
2. 選択したplatformのMCP serverだけを使用する。
   - Android: `auto-genesis-mcp-android`
   - iOS: `auto-genesis-mcp-ios`
3. ScenarioのBackgroundと全stepを、keywordと本文を変更せず元の順序で抽出する。
4. 最初のstepより前に、選択platformのMCPで `before_gen_code` を1回だけ呼ぶ。
   - `feature_file`: featureファイルの絶対パス
   - `step_file`: Androidは `behave-demo/features/android_steps/<feature-stem>.py`、iOSは `behave-demo/features/ios_steps/<feature-stem>.py` の絶対パス
5. 各Gherkin stepにつき、必ず1回以上MCP toolを呼ぶ。stepを結合、省略、追加、言い換えしてはならない。toolには元のkeyword、step本文、Scenario名を記録用引数として渡す。
6. toolが失敗した場合は同じstep内でsnapshotまたはpage sourceを更新し、Test IDを優先してlocatorを修正して再試行する。別stepへ進まない。
7. 全stepが成功した場合だけ `preview_code_changes` を呼び、生成先とstep patternを確認する。
8. previewが対象Scenarioの成功した呼び出しだけを含む場合に限り `confirm_code_changes` を呼ぶ。失敗または未実行stepがあればconfirmしない。
9. 生成ファイル、platform、実行したstep数、再試行、preview/confirm結果を報告する。

## Constraints

- ローカルAppiumのみを使用する。BrowserStack、SauceLabs、その他クラウドdeviceは禁止。
- accessibility IDは `docs/TESTID_CATALOG.md` を単一情報源とする。
- `android_steps/` と `ios_steps/` はAutoGenesis出力領域であり、生成後の手修正は禁止。
- Appium session、MCP process、emulator、Simulatorを無断で切り替えない。
- 実行中のplatform以外のMCP serverを呼ばない。