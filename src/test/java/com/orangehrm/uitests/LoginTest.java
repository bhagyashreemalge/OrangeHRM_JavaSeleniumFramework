package com.orangehrm.uitests;

import Pages.DashboardPage;
import Pages.LoginPage;
import com.orangehrm.utils.commonUtils.LocalUtils;
import com.orangehrm.constants.AppConstants;
import com.orangehrm.wrappers.ReportLogger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class LoginTest extends BaseTest {
    LoginPage loginpage;

    @Parameters({"username","password"})
    @Test(priority = 1, groups={"smoke"})
    public void LoginToApp(String username, String password)
    {
        loginpage=new LoginPage(driver);
        loginpage.enterUsername(username);
        loginpage.enterPassword(password);
        ReportLogger.info("Entered Credentials: username: "+username+" Password: "+password);
        DashboardPage dashboardpage=loginpage.clickLoginButton();
        SoftAssert softassert=new SoftAssert();
        softassert.assertEquals(dashboardpage.getDashboardHeader(), AppConstants.DASHBOARDHEADER,"Dashboard does not match");
        softassert.assertAll();
    }

    //Invalid credential verification
    @Test(dataProvider = "invalidCredentials",priority = 2,groups={"smoke"})
    public void LoginWithInvalidCredentials(String username,String password){
        loginpage=new LoginPage(driver);
        loginpage.enterUsername(username);
        loginpage.enterPassword(password);
        loginpage.clickLoginButton();
        loginpage.waitForElementToBeVisible(LoginPage.locatorErrorMessage);
        Assert.assertEquals(loginpage.getLoginErrorMessage(),AppConstants.LOGIN_ERRORMESSAGE,"Error message not matching");
    }

    @Test(groups={"smoke"})
    public void VerifyForgotPassword() throws InterruptedException {
        loginpage = new LoginPage(driver);
        loginpage.clickForgotPasswordLink();
        Assert.assertTrue(loginpage.resetPasswordHeaderIsDisplayed(),"Reset Password Header is not displayed");
        loginpage.enterUsername("Admin");
        LocalUtils utils=new LocalUtils(driver);
        utils.click(LoginPage.locatorResetPasswordCancelButton);
        Thread.sleep(5000);
        Assert.assertTrue(false);
    }


    @DataProvider
    public Object[][] invalidCredentials(){
        return new Object[][]{{"Admin1","admin1234"},
                              {"Admin2","admin12345"}};
    }

}
