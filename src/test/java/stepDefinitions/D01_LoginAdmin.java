package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.P01_LoginAdmin;

import java.time.Duration;

public class D01_LoginAdmin {
    P01_LoginAdmin loginpage = new P01_LoginAdmin();
    private WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(10));
    @Given("the admin is in the {string} page")
    public void assert_the_loginAdmin(String pageName){
        Assert.assertEquals(pageName , loginpage.loginTitle().getText());
    }
    @When("the admin enters {string} as email")
    public void enter_email_admin(String email){
        loginpage.emailTxt().sendKeys(email);
    }
    @And("the admin enters {string} as password")
    public void enter_password_admin(String password){
        loginpage.passwordTxt().sendKeys(password);
    }
    @And("the admin clicks on login button")
    public void press_login_button_admin(){
        loginpage.loginButton().click();
    }
    @Then("the admin successfully manages to login")
    public void assert_success_login_admin(){
        WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(15));
        // This step now ONLY verifies that the login UI works.
        WebElement hometitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"root\"]/div/div/div/main/header/div[1]/div[2]")));
        Assert.assertTrue(hometitle.isDisplayed(), "Home title isn't shown after login.");
        System.out.println("UI Login test successful.");
    }
}
