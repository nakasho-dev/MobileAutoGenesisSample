---
applyTo: "behave-demo/features/steps/**/*.py"
---
# Behave step 定義ファイル指示 (AutoGenesis 出力領域)
- **原則、このディレクトリは AutoGenesis が生成・書換する領域**
- 手動編集は environment.py のみ
- ロケータは accessibility_id 優先。xpath はフォールバック
- 重複ステップは context.execute_steps で共通化
