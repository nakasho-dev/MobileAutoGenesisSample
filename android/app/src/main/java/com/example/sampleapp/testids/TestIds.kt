package com.example.sampleapp.testids

// 単一情報源: docs/TESTID_CATALOG.md
// 値は iOS 側 (TestIds.swift) と完全一致
object TestIds {
    const val SCREEN_ROOT_SPLASH = "splash_screen"
    const val SPLASH_LOGO = "splash_logo"

    const val SCREEN_ROOT_LOGIN = "login_screen"
    const val LOGIN_EMAIL_FIELD = "login_email_field"
    const val LOGIN_PASSWORD_FIELD = "login_password_field"
    const val LOGIN_SUBMIT_BUTTON = "login_submit_button"
    const val LOGIN_ERROR_TEXT = "login_error_text"

    const val SCREEN_ROOT_ITEM_LIST = "item_list_screen"
    fun itemListRow(index: Int) = "item_list_row_$index"
    const val ITEM_LIST_PULL_TO_REFRESH = "item_list_pull_to_refresh"
    const val ITEM_LIST_SETTINGS_BUTTON = "item_list_settings_button"

    const val SCREEN_ROOT_ITEM_DETAIL = "item_detail_screen"
    const val ITEM_DETAIL_TITLE = "item_detail_title"
    const val ITEM_DETAIL_DESCRIPTION = "item_detail_description"
    const val ITEM_DETAIL_FAVORITE_TOGGLE = "item_detail_favorite_toggle"
    const val ITEM_DETAIL_BACK_BUTTON = "item_detail_back_button"

    const val SCREEN_ROOT_SETTINGS = "settings_screen"
    const val SETTINGS_THEME_TOGGLE = "settings_theme_toggle"
    const val SETTINGS_SIGN_OUT_BUTTON = "settings_sign_out_button"
}
