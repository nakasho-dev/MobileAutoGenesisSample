---
mode: agent
description: 空実装を最小限で動く状態にする
---
# Scaffold Project
## Steps
1. docs/BASIC_DESIGN.md を熟読、docs/TESTID_CATALOG.md を暗記
2. docs/screenshots/ のワイヤーフレームを参照してレイアウトを再現
3. Android: Compose 最小構成で各画面の空 Composable を配置
4. iOS: SwiftUI 最小構成で各画面の空 View を配置
5. 各画面ルートに TestIds.SCREEN_ROOT_<画面名> を必ず付与
6. Login のみ実ロジック実装 (demo@example.com / password で成功)
7. MockRepository にダミーアイテム 3 件

## Constraints
- 依存追加禁止 (既存 build.gradle.kts / Package.swift の範囲)
- **画面単位で提示** (1ターンで全部書かず、トークン節約)
