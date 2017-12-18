import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by DWork on 15.12.2017.
 */
@Getter
@Setter
public class Vir {

    private List<WebElement> areaList;
    private List<WebElement> subAreasList;
    private static List<WebElement> rowsList;
    private static List<WebElement> cellsList;
    private static WebElement area;
    private static WebElement expandArea;
    private static WebElement subArea;
    private static WebElement cell;
    private static WebElement row;


    static By byArea = By.cssSelector(".expand-section.area-inline");
    static By byExpandArea = By.cssSelector(".expand-areas");
    static By bySubArea = By.cssSelector(".areas-content");
    static By byTable = By.cssSelector("div[id *=gview]");
    static By byRows = By.cssSelector("div[id *=gview] div[style=\"position:relative;\"] tbody tr");
    static By byCells = By.cssSelector("td");
    static By byInput = By.cssSelector("input");


    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "D:\\Autotests\\VirGrading\\chromedriverNewest.exe");
        //ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 50);
        Helper helper = new Helper(driver);
        Actions act = new Actions(driver);
        helper.loginToDMS();


        for (int i = 0; i < 5; i++) {   //iterate over the areas
            area = driver.findElements(byArea).get(i);
            System.out.println("Area:  " + area.getText().trim());
            area.click();
            wait.until(ExpectedConditions.visibilityOf(driver.findElements(byExpandArea).get(i)));
            int subAreasNumber = driver.findElements(byExpandArea).get(i).findElements(bySubArea).size();

            //for (int j = 0; j < subAreasNumber; j++) {    // iterate over the subareas
            for (int j = 4; j < subAreasNumber; j++) {
                subArea = driver.findElements(byExpandArea).get(i).findElements(bySubArea).get(j);
                System.out.println("  Subarea:  " + subArea.getText().trim());
                act.moveToElement(subArea).perform();
                subArea.click();
                wait.until(ExpectedConditions.presenceOfElementLocated(byTable));
                rowsList = driver.findElements(byRows);

                int rowsNumber = rowsList.size();
                int cellNumber = rowsList.get(0).findElements(byCells).size();

                for (int k = 37; k < rowsNumber; k++) {    //iterate over the byTable byRows
                    System.out.println("Row Number: " + k);
                    for (int m = 1; m < cellNumber; m++) {    //iterate over the row byCells

                        cell = rowsList.get(k).findElements(byCells).get(m);
                        act.moveToElement(rowsList.get(k).findElements(byCells).get(m-1)).perform();
                        cell.click();
                        WebElement input = cell.findElement(byInput);
                        helper.inputValue(input);
                        helper.closeAlert(wait);

                    }
                }
                subArea.click();
                //wait.until(ExpectedConditions.invisibilityOf(driver.findElement(byTable)));

            }
        }

    }
}
