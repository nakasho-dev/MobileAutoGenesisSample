Feature: Item list and detail navigation

  Background:
    Given I am signed in as "demo@example.com"

  @smoke @android @ios
  Scenario: Navigate to item detail from list
    When I tap element "item_list_row_0"
    Then I should see element "item_detail_screen"
    And I should see element "item_detail_title"

  @regression @android @ios
  Scenario: Toggle favorite on item detail
    Given I am viewing the first item detail
    When I tap element "item_detail_favorite_toggle"
    Then element "item_detail_favorite_toggle" should have state "on"
