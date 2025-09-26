Feature: The Admin can Delete a user in the Users table
  Scenario: Admin adds a user
    Given the admin is in the "Login CMS" page
    When the admin enters "Super@gmail.com" as email
    And the admin enters "smartcollege123" as password
    And the admin clicks on login button
    Then the admin successfully manages to login
    When the admin navigates to the users tab
    And the admin clicks on add user button
    And the admin fills in name as "Test User"
    And the admin fills in phone number "0123455667"
    And the admin fills in the email "testuser123@gmail.com"
    And the admin fills in the password "123456789"
    And the admin clicks on submit button

  Scenario: Admin when adding the user tries to reset the data
    Given the admin is in the "Login CMS" page
    When the admin enters "Super@gmail.com" as email
    And the admin enters "smartcollege123" as password
    And the admin clicks on login button
    Then the admin successfully manages to login
    When the admin navigates to the users tab
    And the admin clicks on add user button
    And the admin fills in name as "Test User"
    And the admin fills in phone number "0123455667"
    And the admin fills in the email "testuser123@gmail.com"
    And the admin fills in the password "123456789"
    And the admin clicks on reset button
    Then all fields of adding user is cleared