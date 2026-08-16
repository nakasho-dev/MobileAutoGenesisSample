# TESTID_CATALOG.md — accessibility ID 単一情報源
**追加/変更したら Android/iOS 両方の TestIds を同時更新する。**

## 命名規約
| 部位 | 表記 | 例 |
|---|---|---|
| Kotlin定数 | SCREAMING_SNAKE | LOGIN_EMAIL_FIELD |
| Swift定数 | lowerCamel | login_EmailField |
| **実値 (両OS共通)** | lower_snake | "login_email_field" |

## Splash
| Key | Value |
|---|---|
| SCREEN_ROOT_SPLASH | splash_screen |
| SPLASH_LOGO | splash_logo |

## Login
| Key | Value |
|---|---|
| SCREEN_ROOT_LOGIN | login_screen |
| LOGIN_EMAIL_FIELD | login_email_field |
| LOGIN_PASSWORD_FIELD | login_password_field |
| LOGIN_SUBMIT_BUTTON | login_submit_button |
| LOGIN_ERROR_TEXT | login_error_text |

## ItemList
| Key | Value |
|---|---|
| SCREEN_ROOT_ITEM_LIST | item_list_screen |
| ITEM_LIST_ROW | item_list_row_{index} |
| ITEM_LIST_PULL_TO_REFRESH | item_list_pull_to_refresh |
| ITEM_LIST_SETTINGS_BUTTON | item_list_settings_button |

## ItemDetail
| Key | Value |
|---|---|
| SCREEN_ROOT_ITEM_DETAIL | item_detail_screen |
| ITEM_DETAIL_TITLE | item_detail_title |
| ITEM_DETAIL_DESCRIPTION | item_detail_description |
| ITEM_DETAIL_FAVORITE_TOGGLE | item_detail_favorite_toggle |
| ITEM_DETAIL_BACK_BUTTON | item_detail_back_button |

## Settings
| Key | Value |
|---|---|
| SCREEN_ROOT_SETTINGS | settings_screen |
| SETTINGS_THEME_TOGGLE | settings_theme_toggle |
| SETTINGS_SIGN_OUT_BUTTON | settings_sign_out_button |
