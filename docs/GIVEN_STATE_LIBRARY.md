# GIVEN_STATE_LIBRARY.md — 共通 Given カタログ
繰り返し使う前提条件 (Given) を集約。AutoGenesis が重複ステップを検出しやすくなり、
生成される step 定義の重複が減る。

| Given 文 | 意味 | 到達状態 |
|---|---|---|
| Given I have launched the sample app | アプリ起動直後 (Splash->Login) | login_screen 表示 |
| Given I am signed in as "<email>" | 指定ユーザでログイン済み | item_list_screen 表示 |
| Given I am viewing the first item detail | 一覧の先頭アイテム詳細を開いた状態 | item_detail_screen 表示 |
| Given the item list is empty | モックが空リストを返す状態 | item_list_screen (Empty) |
| Given the network returns an error | モックが失敗応答を返す状態 | Error 状態 |

## 実装の指針 (steps/*.py)
- 上記 Given は environment.py もしくは共通 steps に一元実装
- "I am signed in as" は login フローを context.execute_steps で内部実行
- 新しい Given を .feature に書く場合は、まずこの表に追記してから実装する
