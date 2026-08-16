# UI_STATE_MATRIX.md — 画面 x 状態 表示マトリクス
各画面が Loading / Empty / Success / Error でどう表示するかを定義。
Copilot が状態管理コードを一発で正しく生成できるよう、単一情報源とする。

## Login
| 状態 | 表示 | 主な要素 |
|---|---|---|
| Idle | 入力フォーム | login_email_field, login_password_field, login_submit_button |
| Loading | ボタンをスピナーに置換・入力無効 | login_submit_button (disabled) |
| Success | ItemList へ遷移 | - |
| Error | エラーテキスト表示 | login_error_text |

## ItemList
| 状態 | 表示 | 主な要素 |
|---|---|---|
| Loading | 中央プログレス | item_list_screen |
| Empty | "No items" プレースホルダ | item_list_screen |
| Success | アイテム行を描画 | item_list_row_{index} |
| Error | 再試行ボタン付きエラー | item_list_pull_to_refresh |

## ItemDetail
| 状態 | 表示 | 主な要素 |
|---|---|---|
| Loading | タイトル/本文をスケルトン | item_detail_screen |
| Success | タイトル・説明・お気に入り | item_detail_title, item_detail_favorite_toggle |
| Error | 戻るボタン付きエラー | item_detail_back_button |

## Settings
| 状態 | 表示 | 主な要素 |
|---|---|---|
| Success | テーマ切替・サインアウト | settings_theme_toggle, settings_sign_out_button |

## 実装ルール
- 状態は sealed interface (Kotlin) / enum with associated value (Swift) で表現
- 各状態のルートに TestIds.SCREEN_ROOT_* を必ず維持 (状態が変わっても同じ ID)
