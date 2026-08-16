Feature: Settings and sign out

  Background:
    Given I am signed in as "demo@example.com"

  @regression @android @ios
  Scenario: Sign out returns to login
    When I tap element "item_list_settings_button"
    And I tap element "settings_sign_out_button"
    Then I should see element "login_screen"
