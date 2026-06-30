package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    public static By locatorUsernameField= By.cssSelector("input[placeholder='Username']");
    By locatorPasswordField=By.cssSelector("input[placeholder='Password']");
    By locatorLoginButton=By.xpath("//button[contains(.,'Login')]");
    public static By locatorErrorMessage=By.cssSelector(".oxd-alert-content.oxd-alert-content--error p");
    public static By locatorForgotPassword=By.xpath("//p[text()='Forgot your password? ']");
    public static By locatorResetPasswordHeader=By.xpath("//h6[text()='Reset Password']");
    public static By locatorResetPasswordCancelButton=By.xpath("//button[contains(@class,'orangehrm-forgot-password-button--cancel')]");;

    public LoginPage(WebDriver driver)
    {
        super(driver);
    }

    public void enterUsername(String username)
    {
        driver.findElement(locatorUsernameField).sendKeys(username);
    }

    public void enterPassword(String password){
        driver.findElement(locatorPasswordField).sendKeys(password);
    }

    public DashboardPage clickLoginButton(){
        driver.findElement(locatorLoginButton).click();
        return new DashboardPage(driver);
    }

    public String getLoginErrorMessage()
    {
        return driver.findElement(locatorErrorMessage).getText();
    }

    public void clickForgotPasswordLink()
    {
        driver.findElement(locatorForgotPassword).click();
    }

    public boolean resetPasswordHeaderIsDisplayed(){
        return driver.findElement(locatorResetPasswordHeader).isDisplayed();
    }

    public void clickResetPasswordCancelButton(){
        driver.findElement(locatorResetPasswordCancelButton).click();
    }

}
