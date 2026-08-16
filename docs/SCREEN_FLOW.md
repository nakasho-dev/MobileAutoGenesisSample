# SCREEN_FLOW.md
BASIC_DESIGN.md の遷移図を機械可読にしたもの。

| From | Event | To | Preconditions |
|---|---|---|---|
| Splash | Auto (2s) | Login | (none) |
| Login | Tap login_submit_button (valid) | ItemList | demo@example.com / password |
| Login | Tap login_submit_button (invalid) | Login | login_error_text visible |
| ItemList | Tap item_list_row_{i} | ItemDetail | item at i exists |
| ItemDetail | Tap item_detail_back_button | ItemList | (none) |
| ItemDetail | Tap item_detail_favorite_toggle | ItemDetail | toggle flipped |
| ItemList | Tap item_list_settings_button | Settings | (none) |
| Settings | Tap settings_sign_out_button | Login | session cleared |
