Feature: Login
  As a user of the sample app
  I want to sign in with valid credentials
  So that I can access the item list

  @smoke @android @ios
  Scenario: Successful login with valid credentials
    Given I have launched the sample app
    When I input "demo@example.com" in element "login_email_field"
    And I input "password" in element "login_password_field"
    And I tap element "login_submit_button"
    Then I should see element "item_list_screen"

  @smoke @android @ios
  Scenario: Failed login shows error message
    Given I have launched the sample app
    When I input "wrong@example.com" in element "login_email_field"
    And I input "wrongpass" in element "login_password_field"
    And I tap element "login_submit_button"
    Then I should see element "login_error_text"
