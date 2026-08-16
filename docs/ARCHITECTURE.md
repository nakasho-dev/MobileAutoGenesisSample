# ARCHITECTURE.md

## 全体構成
```
[User] -> [Native App (Android/iOS)] -> [MockRepository (in-memory)]
                    ^
[AutoGenesis LLM] -> [MCP Server] -> [Appium Server] -> [Emulator/Simulator]
```

## モノレポの狙い
- Android/iOS を同じ commit で同期変更、accessibility ID の齟齬をレビュー時に検知
- .feature が両OS共通、モノレポで同一シナリオを両OSで実行可能
- docs/ を両実装の単一情報源に保てる

## ローカル完結の理由
- BrowserStack はバイナリアップロード + クラウド課金必須で検証段階には過剰
- ローカル Appium + Android エミュ / iOS シミュで十分 (公式が Optional として明記)
