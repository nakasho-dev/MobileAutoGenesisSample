---
applyTo: "**/TestIds.{kt,swift}"
---
# TestIds 定義ファイル指示
- Kotlin=SCREAMING_SNAKE_CASE / Swift=lowerCamelCase
- 命名: `<screen>_<component>_<role>` (例: login_email_field)
- **Android/iOS で同じ role には同じ文字列値** (Appium accessibility_id が両OSで一致)
- 追加時は docs/TESTID_CATALOG.md にも 1 行追加
