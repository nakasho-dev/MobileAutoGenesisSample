import Foundation

// 単一情報源: docs/TESTID_CATALOG.md
// 値は Android 側 (TestIds.kt) と完全一致
enum TestIds {
    static let screenRoot_Splash = "splash_screen"
    static let splash_Logo = "splash_logo"

    static let screenRoot_Login = "login_screen"
    static let login_EmailField = "login_email_field"
    static let login_PasswordField = "login_password_field"
    static let login_SubmitButton = "login_submit_button"
    static let login_ErrorText = "login_error_text"

    static let screenRoot_ItemList = "item_list_screen"
    static func itemList_Row(_ index: Int) -> String { "item_list_row_\(index)" }
    static let itemList_PullToRefresh = "item_list_pull_to_refresh"
    static let itemList_SettingsButton = "item_list_settings_button"

    static let screenRoot_ItemDetail = "item_detail_screen"
    static let itemDetail_Title = "item_detail_title"
    static let itemDetail_Description = "item_detail_description"
    static let itemDetail_FavoriteToggle = "item_detail_favorite_toggle"
    static let itemDetail_BackButton = "item_detail_back_button"

    static let screenRoot_Settings = "settings_screen"
    static let settings_ThemeToggle = "settings_theme_toggle"
    static let settings_SignOutButton = "settings_sign_out_button"
}
