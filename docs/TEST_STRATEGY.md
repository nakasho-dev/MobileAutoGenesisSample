# TEST_STRATEGY.md

## 優先観点
1. 要素特定の安定性 (accessibility_id 経由で両OSの要素が確実に取れる)
2. 画面遷移の再現性 (Login->List->Detail->Settings->Login)
3. 状態変化の検証 (Favorite の on/off)
4. 失敗系の検出 (誤ログイン時のエラー表示)

## 初期シナリオ
| Scenario | Tags | OS | 目的 |
|---|---|---|---|
| Successful login with valid credentials | @smoke @android @ios | Both | 基本疎通 |
| Failed login shows error message | @smoke @android @ios | Both | 失敗系 |
| Navigate to item detail from list | @smoke @android @ios | Both | 遷移 |
| Toggle favorite on item detail | @regression @android @ios | Both | 状態変化 |
| Sign out returns to login | @regression @android @ios | Both | セッション |

## 方針
- ローカル: emulator-5554 / iPhone 15 Simulator
- 1 Scenario は 3〜7 ステップ
- 待機は wait_for_element_visible。sleep 禁止
- 全件デモの実行順、featureとscenarioの対応は `config/full_mobile_demo_plan.json` を単一情報源とする。
- featureごとに、Appium生成の前に対象OSのViewModelまたはMockRepositoryの単体テストとdebug buildを成功させる。
- AutoGenesis生成後は各scenarioをBehaveで再実行し、最後にplatformタグで全件を実行する。詳細は [FULL_MOBILE_DEMO_HOWTO.md](FULL_MOBILE_DEMO_HOWTO.md) を参照する。
