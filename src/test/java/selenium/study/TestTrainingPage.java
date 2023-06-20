package selenium.study;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class TestTrainingPage {

	private TestPage testPage;

	@BeforeEach
	public void beforeEach() {
		testPage = new TestPage();
	}

	@AfterEach
	public void afterEach() {
		testPage.quitPages();
	}

	@Test
	void testFillForm() {
		testPage.fillField("elementosForm:nome", "Abner");
		testPage.fillField("elementosForm:sugestoes", "Teste\n\nQuebras de Linha");
		Assertions.assertEquals("Teste\n\nQuebras de Linha", testPage.getTextField("elementosForm:sugestoes"));
	}

	@Test
	void testClearField() {
		testPage.fillField("elementosForm:nome", "Abner");
		Assertions.assertEquals("Abner", testPage.getTextField("elementosForm:nome"));
		testPage.fillField("elementosForm:nome", "Erik");
		Assertions.assertEquals("Erik", testPage.getTextField("elementosForm:nome"));
	}

	@Test
	void testRadioAndCheckBox() {
		testPage.clickElement("elementosForm:sexo:0");
		Assertions.assertTrue(testPage.isElementSelected("elementosForm:sexo:0"));

		testPage.clickElement("elementosForm:comidaFavorita:2");
		Assertions.assertTrue(testPage.isElementSelected("elementosForm:comidaFavorita:2"));
	}

	@Test
	void testFieldDropDown() {
		Assertions.assertEquals("Superior", testPage.isElementEquals(testPage.selectSchooling("Superior")));
	}

	@Test
	void testOptionsDropDown() {
		Assertions.assertTrue(testPage.isElementExist("elementosForm:escolaridade"));
		Assertions.assertEquals(8, testPage.sizeElement("elementosForm:escolaridade"));
	}

	@Test
	void testTopButtons() {
		Assertions.assertEquals("Obrigado!", testPage.clickElementWithReturn("buttonSimple"));
	}

	@Test
	void testLinks() {
		Assertions.assertEquals("Voltou!", testPage.getTextLink("Voltar"));
	}

	@Test
	void testFindTextOnScreen(){
		Assertions.assertTrue(testPage.isExistInPageSource("Campo de Treinamento"));
		Assertions.assertTrue(testPage.isExistinTag("h3", "Campo de Treinamento"));
		Assertions.assertEquals("Cuidado onde clica, muitas armadilhas...", testPage.getTextByClassName("facilAchar"));
	}

	@Test
	void testSimpleAlert() {
		testPage.clickElement("alert");
		Assertions.assertEquals("Alert Simples", testPage.getTextAlert());
		testPage.acceptAlert();
	}

	@Test
	void testAllResultsAlert() {
		testPage.clickElement("confirm");
		Assertions.assertEquals("Confirm Simples", testPage.getTextAlert());
		testPage.acceptAlert();

		Assertions.assertEquals("Confirmado", testPage.getTextAlert());
		testPage.acceptAlert();

		testPage.clickElement("confirm");
		testPage.dismisstAlert();

		Assertions.assertEquals("Negado", testPage.getTextAlert());
		testPage.acceptAlert();
	}

	@Test
	void testAlertPromptAccetp() {
		testPage.clickElement("prompt");
		Assertions.assertEquals("Digite um numero", testPage.getTextAlert());

		testPage.sendKeysAlert("123456");
		Assertions.assertEquals("Era 123456?", testPage.getTextAlert());
		testPage.acceptAlert();

		Assertions.assertEquals(":D", testPage.getTextAlert());
		testPage.acceptAlert();
	}

	@Test
	void testAlertPromptDismiss() {
		testPage.clickElement("prompt");
		Assertions.assertEquals("Digite um numero", testPage.getTextAlert());
		testPage.dismisstAlert();

		Assertions.assertEquals("Era null?", testPage.getTextAlert());
		testPage.dismisstAlert();

		Assertions.assertEquals(":(", testPage.getTextAlert());
		testPage.acceptAlert();
	}

	// Preenchendo um formulário e verificando o resultado
	@Test
	void testRegisterSuccess() {
		String result = testPage.fillFildsForRegister("Abner", "Amos", "0", "0", "Mestrado", "Karate", "Games");
		Assertions.assertEquals(result, testPage.getTextByElement("resultado"));
	}

	@Test
	void testInteractFrame() {
		String frame = testPage.getTextOtherAlertByIframe("frame1", "frameButton");
		Assertions.assertEquals("Frame OK!", frame);
		testPage.acceptAlert();
		testPage.switchToDefaultPage();
		Assertions.assertTrue(testPage.isExistInPageSource("Campo de Treinamento"));
	}

	@Test
	void testInteractWithWindowsEasy() {
		testPage.switchToOtherWindowsWithNameHandle();
		Assertions.assertTrue(testPage.isExistInPageSource("Campo de Treinamento"));
	}

	@Test
	void testInteractWithWindowsHard() {
		testPage.switchToOtherWindowWithoutNameHandle();
		Assertions.assertTrue(testPage.isExistInPageSource("Campo de Treinamento"));
	}

	@Test
	void testShowErrorsValidationsFields() {

		// Validando erro campo NOME inválido ---------------------------------------------------
		testPage.clickElement("elementosForm:cadastrar");
		Assertions.assertEquals("Nome eh obrigatorio", testPage.getTextAlert());
		testPage.acceptAlert();

		// Validando erro campo SOBRENOME inválido ----------------------------------------------
		testPage.fillField("elementosForm:nome", "Abner");
		testPage.clickElement("elementosForm:cadastrar");
		Assertions.assertEquals("Sobrenome eh obrigatorio", testPage.getTextAlert());
		testPage.acceptAlert();

		// Validando erro campo SEXO inválido ----------------------------------------------
		testPage.fillField("elementosForm:nome", "Abner");
		testPage.fillField("elementosForm:sobrenome", "Amos");
		testPage.clickElement("elementosForm:cadastrar");
		Assertions.assertEquals("Sexo eh obrigatorio", testPage.getTextAlert());
		testPage.acceptAlert();

		// Validando erro campo COMIDA FAVORITA se selecionado CARNE e VEGETARIANO inválido --
		testPage.fillField("elementosForm:nome", "Abner");
		testPage.fillField("elementosForm:sobrenome", "Amos");
		testPage.clickElement("elementosForm:sexo:0");
		testPage.clickElement("elementosForm:comidaFavorita:0");
		testPage.clickElement("elementosForm:comidaFavorita:3");
		testPage.clickElement("elementosForm:cadastrar");
		Assertions.assertEquals("Tem certeza que voce eh vegetariano?", testPage.getTextAlert());
		testPage.acceptAlert();

		// Validando erro campo PRATICA ESPORTE se selecionado "Esporte" e "O que é esporte" inválido --
		testPage.fillField("elementosForm:nome", "Abner");
		testPage.fillField("elementosForm:sobrenome", "Amos");
		testPage.clickElement("elementosForm:sexo:0");
		testPage.clickElement("elementosForm:comidaFavorita:0");

		testPage.isSportsPractitioner("elementosForm:esportes");

		testPage.clickElement("elementosForm:cadastrar");
		Assertions.assertEquals("Voce faz esporte ou nao?", testPage.getTextAlert());
		testPage.acceptAlert();
	}
}
