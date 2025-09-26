Feature: The Admin can Delete a user in the Users table
  Scenario: Admin finds the specific user and deletes him
    Given the admin is in the "Login CMS" page
    When the admin enters "Super@gmail.com" as email
    And the admin enters "smartcollege123" as password
    And the admin clicks on login button
    Then the admin successfully manages to login
    When the admin navigates to the users tab
    And the admin finds the user to edit with email "hassanmohed@gmail.com"
    And changes the username of the user to "Hassanff3"
    And changes the phone of the user to "012345678909"
    And presses update button
    And the user edit is successfully changed
