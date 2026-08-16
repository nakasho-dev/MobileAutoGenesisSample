# gradle-wrapper.jar について
このディレクトリには本来 `gradle-wrapper.jar` (バイナリ) が必要ですが、
配布容量とライセンスの都合で同梱していません。初回のみ次のいずれかで生成してください。

- Android Studio でプロジェクトを開く → 自動生成
- ローカルに Gradle がある場合: `gradle wrapper --gradle-version 8.5`

jar 生成後は `./gradlew assembleDebug` が動作します。
