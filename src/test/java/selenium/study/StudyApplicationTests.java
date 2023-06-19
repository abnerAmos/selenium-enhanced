package selenium.study;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StudyApplicationTests {

	private static final String PATH_HTML = "file:///"
			+ System.getProperty("user.dir") + "/src/test/resources/componentes.html";

	@Test
	void contextLoads() {
		WebDriverManager.chromedriver().setup();
		WebDriver webDriver = new ChromeDriver();
		webDriver.get(PATH_HTML);
//		webDriver.findElement(By.name("q")).sendKeys("StackOverflow");
//		webDriver.findElement(By.name("q")).submit();
	}

}
