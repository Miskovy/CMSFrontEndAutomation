package stepDefinitions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.types.Hook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.P02_Dashboard;
import utils.ApiAssertions;

import java.time.Duration;


import static utils.TableDeleteHelper.clickEditByEmail;
import static utils.TableDeleteHelper.deleteUserByEmail;

public class D02_UserManagement {
    P02_Dashboard dashboard = new P02_Dashboard(Hooks.driver);
    WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(20));
    String baseUrl = "https://9c2qqfmx-3000.uks1.devtunnels.ms/api/admin/users/";
    String token   = ""; // maybe grab it from login
    String userId  = "68bff5b93a55f3e247946540";
    @When("the admin navigates to the users tab")
    public void navigate_to_the_users_tab(){
        dashboard.usersButton().click();
        wait.until(ExpectedConditions.visibilityOf(dashboard.userTitle()));
        Hooks.driver.navigate().refresh();
    }
    @And("the admin finds the user with email {string}")
    public void find_the_user_with_email(String email){
        deleteUserByEmail(Hooks.driver, email, 10);
    }
    @And("the admin clicks on add user button")
    public void click_add_user(){
        WebElement addButton = Hooks.driver.findElement(By.xpath("//a[contains(text(), ' Add User')]"));
        addButton.click();
    }
    @And("the admin fills in name as {string}")
    public void adds_user_name(String name){
        WebElement nameTxt = Hooks.driver.findElement(By.xpath("//*[@id=\"name\"]"));
        nameTxt.click();
        nameTxt.sendKeys(name);
    }
    @And("the admin fills in phone number {string}")
    public void add_user_phone(String phone){
        WebElement PhoneTxt = Hooks.driver.findElement(By.xpath("//*[@id=\"phonenumber\"]"));
        PhoneTxt.click();
        PhoneTxt.sendKeys(phone);
    }
    @And("the admin fills in the email {string}")
    public void add_user_email(String email){
        WebElement emailTxt = Hooks.driver.findElement(By.xpath("//*[@id=\"email\"]"));
        emailTxt.click();
        emailTxt.sendKeys(email);
    }
    @And("the admin fills in the password {string}")
    public void add_user_password(String password){
        WebElement passwordTxt = Hooks.driver.findElement(By.xpath("//*[@id=\"password\"]"));
        passwordTxt.click();
        passwordTxt.sendKeys(password);
    }
    @And("the admin clicks on submit button")
    public void add_user_submit_button(){
        WebElement submitButton = Hooks.driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/main/div/div/div[3]/button[2]"));
        submitButton.click();
    }
    @And("the admin clicks on reset button")
    public void add_user_reset_button(){
        WebElement resetButton = Hooks.driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/main/div/div/div[3]/button[1]"));
        resetButton.click();
    }
    @And("all fields of adding user is cleared")
    public void assert_cleared_fields(){
        WebElement nameTxt = Hooks.driver.findElement(By.xpath("//*[@id=\"name\"]"));
        WebElement PhoneTxt = Hooks.driver.findElement(By.xpath("//*[@id=\"phonenumber\"]"));
        WebElement emailTxt = Hooks.driver.findElement(By.xpath("//*[@id=\"email\"]"));
        WebElement passwordTxt = Hooks.driver.findElement(By.xpath("//*[@id=\"password\"]"));
        Assert.assertTrue(nameTxt.getText().isEmpty());
        Assert.assertTrue(PhoneTxt.getText().isEmpty());
        Assert.assertTrue(emailTxt.getText().isEmpty());
        Assert.assertTrue(passwordTxt.getText().isEmpty());
    }
    @And("the admin finds the user to edit with email {string}")
    public void find_the_user_with_email_edit(String email){
        clickEditByEmail(Hooks.driver,email,10);
    }
    @And("changes the username of the user to {string}")
    public void change_username_edit(String username){
        WebElement change_usernameTxt = Hooks.driver.findElement(By.xpath("//*[@id=\"name\"]"));
        if(!(change_usernameTxt.getText().isEmpty())){
            change_usernameTxt.click();
            change_usernameTxt.clear();
            change_usernameTxt.sendKeys(username);
        }
    }
    @And("changes the phone of the user to {string}")
    public void change_phone_number_edit(String phone){
        WebElement change_PhoneTxt = Hooks.driver.findElement(By.xpath("//*[@id=\"phonenumber\"]"));
        if(!(change_PhoneTxt.getText().isEmpty())){
            change_PhoneTxt.click();
            change_PhoneTxt.clear();
            change_PhoneTxt.sendKeys(phone);
        }
    }
    @And("presses update button")
    public void click_update(){
        WebElement updateButton = Hooks.driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/main/div/div/div[3]/button[2]"));
        updateButton.click();
    }
    @And("the user edit is successfully changed")
    public void assert_user_is_edited(){

    }
}