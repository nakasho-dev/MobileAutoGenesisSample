---
applyTo: "android/**/*.kt"
---
# Android (Kotlin + Jetpack Compose) 指示
- Compose 要素には Modifier.testTag(TestIds.XXX) を必ず付与。文字列直書き禁止
- 画面ルート Composable に Modifier.testTag(TestIds.SCREEN_ROOT_<画面名>) を付与
- 表示文字列は stringResource(R.string.xxx) を使用。ハードコード禁止
- ViewModel: androidx.lifecycle.ViewModel 継承、状態は StateFlow<T>
- ネットワークは MockRepository 経由。実サーバ禁止
- 依存追加禁止 (既存 build.gradle.kts の範囲)
