---
mode: agent
description: 新画面を Android + iOS 同時に追加する
---
# Add Screen
## Input: screen_name (PascalCase), purpose(日), 主要要素
## Steps
1. docs/BASIC_DESIGN.md 画面一覧表に 1 行追加
2. docs/TESTID_CATALOG.md に SCREEN_ROOT_<画面名> + 要素 ID 追記
3. docs/UI_STATE_MATRIX.md に状態別表示を 1 行追加
4. Android: android/app/src/main/java/com/example/sampleapp/ui/<screen_name>/ に実装
5. iOS: ios/SampleApp/Screens/<screen_name>/ に実装
6. 両OSの TestIds に定数追加
7. behave-demo/features/<screen_name>.feature に 1 Scenario 以上

## Constraints: 依存追加禁止、モックのみ、命名規約遵守
