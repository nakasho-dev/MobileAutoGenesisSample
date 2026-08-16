# Copilot Global Instructions
**このファイルは全リクエストで送信される。極力短く保つこと。**

- 言語: Kotlin (Android), Swift (iOS), Python (Behave step)
- アーキ: MVVM + Jetpack Compose / SwiftUI, MockRepository のみ
- accessibility ID は docs/TESTID_CATALOG.md を単一情報源、各OSの TestIds に集約
- UI 文字列は strings.xml / Localizable.strings を単一情報源
- テスト: AutoGenesis (Appium+MCP+Behave)。**ローカル Appium のみ** (BrowserStack禁止)
- 応答は日本語。コード内コメントも日本語可
- 詳細は AGENTS.md と .github/instructions/*.instructions.md
