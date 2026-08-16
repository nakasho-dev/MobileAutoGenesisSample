---
applyTo: "ios/**/*.swift"
---
# iOS (Swift + SwiftUI) 指示
- SwiftUI 要素には .accessibilityIdentifier(TestIds.xxx) を必ず付与。文字列直書き禁止
- 画面ルート View に .accessibilityIdentifier(TestIds.screenRoot_<画面名>) を付与
- 表示文字列は NSLocalizedString / String(localized:) を使用。ハードコード禁止
- 状態は @Observable (Swift 5.9+) または @StateObject
- ネットワークは MockRepository 経由
- SwiftPM 依存追加禁止
