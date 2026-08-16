---
applyTo: "behave-demo/features/**/*.feature"
---
# Gherkin (.feature) 作成指示
- 1 feature = 1 機能。ファイル名はスネークケース
- 各 Scenario には @smoke, @android, @ios のいずれかを最低1つ付与
- 手順は英語 (AutoGenesis の LLM が英語ベース)
- 要素キーは docs/TESTID_CATALOG.md の英名と一致
- 共通の Given は docs/GIVEN_STATE_LIBRARY.md を参照
- 1 Scenario は 3〜7 ステップに収める
