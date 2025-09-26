Feature: The admin goes to the login and enters his Credentials to successfully manage to login to the dashboard
  Scenario: The Admin logs in successfully
  Given the admin is in the "Login CMS" page
  When the admin enters "Super@gmail.com" as email
  And the admin enters "smartcollege123" as password
  And the admin clicks on login button
    Then the admin successfully manages to login