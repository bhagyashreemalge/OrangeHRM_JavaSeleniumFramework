package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DashboardPage extends BasePage{

    @FindBy(css=".oxd-topbar-header-title span h6")
    WebElement dashboardHeader;

    DashboardPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver,this);
    }

    public String getDashboardHeader(){
        if(driver.getCurrentUrl().contains("dashboard")){
            return dashboardHeader.getText();
        }
        return null;
    }

}
