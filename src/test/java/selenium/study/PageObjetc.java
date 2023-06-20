package selenium.study;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class PageObjetc {

    protected WebDriver driver;
    protected static final String PATH_HTML = "file:///"
            + System.getProperty("user.dir") + "/src/test/resources/componentes.html";

    public PageObjetc() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(PATH_HTML);
    }

    public void quitPages() {
        driver.quit(); // Fecha todas as janelas abertas.
    }
}
