package selenium.study;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class StudyApplicationTests{

	private static final String PATH_HTML = "file:///"
			+ System.getProperty("user.dir") + "/src/test/resources/componentes.html";
	private WebDriver driver;

	@BeforeAll
	public static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		driver = new ChromeDriver();
		driver.get(PATH_HTML);
	}

	@AfterEach
	public void afterEach() {
		driver.quit(); // Fecha todas as janelas abertas.
	}

	@Test
	void testFillForm() {
		driver.findElement(By.id("elementosForm:nome")).sendKeys("Abner");

		WebElement textArea = driver.findElement(By.id("elementosForm:sugestoes"));
		textArea.sendKeys("Teste\n\nQuebras de Linha");
		Assertions.assertEquals("Teste\n\nQuebras de Linha", textArea.getAttribute("value"));
		// .getAttribute("value") captura o valor dentro de um campo no form.
	}

	@Test
	void testRadioAndCheckBox() {
		WebElement radioBox = driver.findElement(By.id("elementosForm:sexo:0"));
		radioBox.click();
		Assertions.assertTrue(radioBox.isSelected());

		WebElement checkBox = driver.findElement(By.id("elementosForm:comidaFavorita:2"));
		checkBox.click();
		Assertions.assertTrue(checkBox.isSelected());
	}

	@Test
	void testFieldDropDown() {
		WebElement dropDown = driver.findElement(By.id("elementosForm:escolaridade")); // Seleciona o Elemento da Página
		Select array = new Select(dropDown); // observa os itens dentro de um dropdown
//		array.selectByIndex(3); // seleciona pelo intex
//		array.selectByValue("superior"); // seleciona pelo atributo "value" implicito dentro da tag html
		array.selectByVisibleText("Mestrado"); // seleciona pelo valor visivel.

		Assertions.assertEquals("Mestrado", array.getFirstSelectedOption().getText());
													// verifica o campo selecionado.
	}

	@Test
	void testOptionsDropDown() {
		WebElement dropDown = driver.findElement(By.id("elementosForm:escolaridade")); // Seleciona o Elemento da Página.
		Select array = new Select(dropDown); // captura os elementos e joga dentro de uma Array List.
		List<WebElement> options = array.getOptions(); // captura os elementos e joga dentro de um arrayList.
		Assertions.assertEquals(8, options.size()); // verifica o tamanho da lista

		// percorrendo a lista
		boolean find = false;
		for (WebElement option : options) {
			if (option.getText().equals("Mestrado")) {
				find = true;
				break;
			}
		}
		Assertions.assertTrue(find);
	}

	@Test
	void testOptionsSelectList() {
		WebElement dropDown = driver.findElement(By.id("elementosForm:esportes")); // Seleciona o Elemento da Página.
		Select array = new Select(dropDown); // captura os elementos e joga dentro de uma Array List.
		array.selectByVisibleText("Futebol");
		array.selectByVisibleText("Corrida");
		array.selectByVisibleText("Karate"); // Seleciona diversos itens do dropDown

		List<WebElement> allSelectOptions = array.getAllSelectedOptions();
		Assertions.assertEquals(3, allSelectOptions.size()); // verificando o tamanho da lista selecionada

		array.deselectByVisibleText("Karate");
		allSelectOptions = array.getAllSelectedOptions(); // Deseleciona diversos itens do dropDown
		Assertions.assertEquals(2, allSelectOptions.size());
	}

	@Test
	void testTopButtons() {
		WebElement clickButton = driver.findElement(By.id("buttonSimple"));
		clickButton.click();
		Assertions.assertEquals("Obrigado!", clickButton.getAttribute("value"));
		// .getAttribute("value") captura o valor dentro de um campo no form.
	}

	@Test
	void testLinks() {
		driver.findElement(By.linkText("Voltar")).click();
		WebElement result = driver.findElement(By.id("resultado"));
		Assertions.assertEquals("Voltou!", result.getText());
	}

	@Test
	void testFindTextOnScreen(){
		boolean isText = driver.getPageSource().contains("Campo de Treinamento");
		Assertions.assertTrue(isText);
		// busca por um texto em todoo o HTML

		boolean findTag = driver.findElement(By.tagName("h3")).getText().contains("Campo de Treinamento");
		Assertions.assertTrue(findTag);
		// busca por um texto filtrando pela tag do HTML

		Assertions.assertEquals("Cuidado onde clica, muitas armadilhas...",
				driver.findElement(By.className("facilAchar")).getText());
	}

	@Test
	void testSimpleAlert() {
		driver.findElement(By.id("alert")).click();
		Alert alert = driver.switchTo().alert(); /* O alert esta fora do HTML, para capturar os seus valores
		é necessário trocar a "visão" do HTML para o Alert*/
		Assertions.assertEquals("Alert Simples", alert.getText());
		alert.accept();
		// Fecha o alert, caso não seja fechado, as proximas interações com o HTML não irão funcionar.
	}

	@Test
	void testAlert() {
		WebElement buttonConfirm = driver.findElement(By.id("confirm"));
		buttonConfirm.click();
		Alert alert = driver.switchTo().alert();
		Assertions.assertEquals("Confirm Simples", alert.getText());
		alert.accept();
		// Fecha o alert pelo botão "OK"

		Assertions.assertEquals("Confirmado", alert.getText());
		alert.accept();

		buttonConfirm.click();
		alert = driver.switchTo().alert();
		alert.dismiss();
		// Fecha o alert pelo botão "CANCELAR"

		Assertions.assertTrue(alert.getText().contains("Negado"));
		alert.accept();
	}

	@Test
	void testAlertPromptAccetp() {
		WebElement prompt = driver.findElement(By.id("prompt"));
		prompt.click();

		Alert alert = driver.switchTo().alert();
		Assertions.assertEquals("Digite um numero", alert.getText());

		String number = "123456";

		// Preenchendo um campo dentro de um Alert
		alert.sendKeys(number);
		alert.accept();
		Assertions.assertEquals("Era " + number + "?", alert.getText());
		alert.accept();

		Assertions.assertEquals(":D", alert.getText());
		alert.accept();
	}

	@Test
	void testAlertPromptDismiss() {
		WebElement prompt = driver.findElement(By.id("prompt"));
		prompt.click();

		Alert alert = driver.switchTo().alert();
		Assertions.assertEquals("Digite um numero", alert.getText());
		alert.dismiss();

		Assertions.assertEquals("Era null?", alert.getText());
		alert.dismiss();

		Assertions.assertEquals(":(", alert.getText());
		alert.accept();
	}

	// Preenchendo um formulário e verificando o resultado
	@Test
	void testRegisterSuccess() {
		driver.findElement(By.id("elementosForm:nome")).sendKeys("Abner");
		driver.findElement(By.id("elementosForm:sobrenome")).sendKeys("Amos");
		driver.findElement(By.id("elementosForm:sexo:0")).click();
		driver.findElement(By.id("elementosForm:comidaFavorita:0")).click();
		driver.findElement(By.id("elementosForm:comidaFavorita:2")).click();

		WebElement schooling = driver.findElement(By.id("elementosForm:escolaridade"));
		Select dropDownSchool = new Select(schooling);
		dropDownSchool.selectByVisibleText("Superior");

		WebElement sports = driver.findElement(By.id("elementosForm:esportes"));
		Select dropDownSport = new Select(sports);
		dropDownSport.selectByVisibleText("Corrida");

		driver.findElement(By.id("elementosForm:sugestoes")).sendKeys("Games");
		driver.findElement(By.id("elementosForm:cadastrar")).click();

		String result = "Cadastrado!\n" +
				"Nome: Abner\n" +
				"Sobrenome: Amos\n" +
				"Sexo: Masculino\n" +
				"Comida: Carne Pizza\n" +
				"Escolaridade: superior\n" +
				"Esportes: Corrida\n" +
				"Sugestoes: Games";

		Assertions.assertEquals(result, driver.findElement(By.id("resultado")).getText());
	}

	@Test
	void testInteractFrame() {

		// Interangindo com um Iframe (HTML dentro de outro)

		driver.switchTo().frame("frame1");
		driver.findElement(By.id("frameButton")).click();
		Alert alert = driver.switchTo().alert();
		String msg = alert.getText();
		Assertions.assertEquals("Frame OK!", msg);
		alert.accept();

		driver.switchTo().defaultContent();
		driver.findElement(By.id("elementosForm:nome")).sendKeys(msg);
	}

	@Test
	void testInteractWithWindowsEasy() {

		// Interangindo com uma outra janela aberta a partir da corrente quando possuí titulo

		driver.findElement(By.id("buttonPopUpEasy")).click();
		driver.switchTo().window("Popup");
		driver.findElement(By.tagName("textarea")).sendKeys("Deu certo?");
		driver.close();
		driver.switchTo().window("");
		driver.findElement(By.tagName("textarea")).sendKeys("Deu certo!");
	}

	@Test
	void testInteractWithWindowsHard() {
		driver.findElement(By.id("buttonPopUpHard")).click();
		System.out.println(driver.getWindowHandle()); // Obtem o numero de "id" da janela corrente
		System.out.println(driver.getWindowHandles()); // Obtem o numero de "id" das janelas selecionadas em ("buttonPopUpHard")

		driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
		driver.findElement(By.tagName("textarea")).sendKeys("Deu certo?");

		driver.switchTo().window(driver.getWindowHandles().toArray()[0].toString());
		driver.findElement(By.tagName("textarea")).sendKeys("Deu certo!");
	}

	@Test
	void testShowErrorsValidationsFields() {

		// Validando erro campo NOME inválido ---------------------------------------------------
		driver.findElement(By.id("elementosForm:cadastrar")).click();
		Alert alertNome = driver.switchTo().alert();
		Assertions.assertTrue(alertNome.getText().contains("Nome eh obrigatorio"));
		alertNome.accept();

		// Validando erro campo SOBRENOME inválido ----------------------------------------------
		driver.findElement(By.id("elementosForm:nome")).sendKeys("Abner");
		driver.findElement(By.id("elementosForm:cadastrar")).click();
		Alert alertSobrenome = driver.switchTo().alert();
		Assertions.assertTrue(alertSobrenome.getText().contains("Sobrenome eh obrigatorio"));
		alertNome.accept();

		// Validando erro campo SEXO inválido ----------------------------------------------
		driver.findElement(By.id("elementosForm:nome")).sendKeys("Abner");
		driver.findElement(By.id("elementosForm:sobrenome")).sendKeys("Amos");
		driver.findElement(By.id("elementosForm:cadastrar")).click();
		Alert alertSexo = driver.switchTo().alert();
		Assertions.assertTrue(alertSexo.getText().contains("Sexo eh obrigatorio"));
		alertNome.accept();

		// Validando erro campo COMIDA FAVORITA se selecionado CARNE e VEGETARIANO inválido --
		driver.findElement(By.id("elementosForm:nome")).sendKeys("Abner");
		driver.findElement(By.id("elementosForm:sobrenome")).sendKeys("Amos");
		driver.findElement(By.id("elementosForm:sexo:0")).click();
		driver.findElement(By.id("elementosForm:comidaFavorita:0")).click();
		driver.findElement(By.id("elementosForm:comidaFavorita:3")).click();
		driver.findElement(By.id("elementosForm:cadastrar")).click();
		Alert alertComida = driver.switchTo().alert();
		Assertions.assertTrue(alertComida.getText().contains("Tem certeza que voce eh vegetariano?"));
		alertNome.accept();

		// Validando erro campo PRATICA ESPORTE se selecionado "Esporte" e "O que é esporte" inválido --
		driver.findElement(By.id("elementosForm:nome")).sendKeys("Abner");
		driver.findElement(By.id("elementosForm:sobrenome")).sendKeys("Amos");
		driver.findElement(By.id("elementosForm:sexo:0")).click();
		driver.findElement(By.id("elementosForm:comidaFavorita:0")).click();

		WebElement sports = driver.findElement(By.id("elementosForm:esportes"));
		Select select = new Select(sports);
		select.selectByVisibleText("Karate");
		select.selectByVisibleText("O que eh esporte?");

		driver.findElement(By.id("elementosForm:cadastrar")).click();
		Alert alertSport = driver.switchTo().alert();
		Assertions.assertTrue(alertSport.getText().contains("Voce faz esporte ou nao?"));
		alertNome.accept();
	}
}
