Feature: The Admin can Delete a user in the Users table
  Scenario: Admin finds the specific user and deletes him
    Given the admin is in the "Login CMS" page
    When the admin enters "Super@gmail.com" as email
    And the admin enters "smartcollege123" as password
    And the admin clicks on login button
    Then the admin successfully manages to login
    When the admin navigates to the users tab
    And the admin finds the user with email "testuser55@gmail.com"
