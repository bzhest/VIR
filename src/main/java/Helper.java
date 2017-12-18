import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by DWork on 15.12.2017.
 */
public class Helper {
    private WebDriver driver;
    private List<Double> digits;
    private Random random;


    public Helper(WebDriver driver) {

        this.driver = driver;
        random = new Random();
        digits = new ArrayList<>(Arrays.asList(1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8, 1.9,
                2.0, 2.1, 2.2, 2.3, 2.4, 2.5, 2.6, 2.7, 2.8, 2.9,
                3.0, 3.1, 3.2, 3.3, 3.4, 3.5, 3.6, 3.7, 3.8, 3.9,
                4.0, 4.1, 4.2, 4.3, 4.4, 4.5, 4.6, 4.7, 4.8, 4.9, 5.0));

    }

    public void loginToDMS() {
        //driver.get("http://www.anastasiya.motorboxx.goxloo.com/dms/auctions/admin#grading");
        String URL = "http://www.anastasiya.motorboxx.goxloo.com/dms/auctions/admin#grading";
        driver.get("http://www.anastasiya.motorboxx.goxloo.com/dms/auctions/admin#grading");
        driver.findElement(By.cssSelector("#login")).clear();
        driver.findElement(By.cssSelector("#login")).sendKeys("andrey.bzhestovskyy@xloo.com");
        driver.findElement(By.cssSelector("#password")).clear();
        driver.findElement(By.cssSelector("#password")).sendKeys("andrey87");
        driver.findElement(By.cssSelector("#login2")).click();
        sleepMagic(1000);
        driver.findElement(By.cssSelector("#tab_grading")).click();
    }

    public void inputValue(WebElement element) {
        if (hasTitle(element)) {
            element.clear();
            element.sendKeys(getRandom().toString());
            element.sendKeys(Keys.ENTER);
        }else{
            element.sendKeys(Keys.ENTER);
        }
    }

    public boolean hasTitle(WebElement element) {
        try {
            String value = element.getAttribute("title");
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public Double getRandom() {
        int index = random.nextInt(digits.size());
        return digits.get(index);
    }

    public  void sleepMagic(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public  void clickOnElement(WebDriver driver, WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click", element);
    }

    public void scrollToElement(WebDriver driver, WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void focusOnElement(WebDriver driver, WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].focus();", element);
    }

    public void closeAlert(WebDriverWait wait){
        List<WebElement> cross = driver.findElements(By.cssSelector(".close"));
        if(cross.size() > 0) {
            for (int i = 0; i < cross.size(); i++) {
                cross.get(i).click();
                //sleepMagic(400);
                //wait.until(ExpectedConditions.invisibilityOf(cross.get(i)));
            }
        }
    }

    public boolean waitForJSandJQueryToLoadWithTimeout(Integer timeout) {
        Integer defaultTimeout = 30;
        timeout = (null == timeout || defaultTimeout > timeout) ? defaultTimeout : timeout;
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        //method for execute Java Script: page should be loaded
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };

        // wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    // no jQuery present
                    return true;
                }
            }
        };
        return wait.until(jQueryLoad) && wait.until(jsLoad);
    }
}

