package selenium.study;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TrainingTest {

	private TrainingPage page;

	@BeforeEach
	public void beforeEach() {
		page = new TrainingPage();
	}

	@AfterEach
	public void afterEach() {
		page.quitPages();
	}

	@Test
	void shouldFillFieldNameAndSuggestions() {
		page.setName("Abner");
		page.setSuggestions("Teste\n\nQuebras de Linha");
		Assertions.assertEquals("Teste\n\nQuebras de Linha", page.getSuggestions());
	}

	@Test
	void shouldClearBeforedAndFillField() {
		page.setName("Abner");
		Assertions.assertEquals("Abner", page.getName());
		page.setName("Erik");
		Assertions.assertEquals("Erik", page.getName());
	}

	@Test
	void shouldSelectRadioAndCheckBox() {
		page.setMan();
		Assertions.assertTrue(page.isManSelected());

		page.setFoodMeat();
		Assertions.assertTrue(page.isMeatSelected());
	}

	@Test
	void shouldCountAllSelectFieldsSports() {
		page.countElementsSelected("Futebol", "Corrida", "Karate");
	}

	@Test
	void shouldCountAllSelectBeforeDescelectFieldsSports() {
		page.countElementsSelectedAfterDeslesectedElements();
	}

	@Test
	void shouldVerifyOptionsInDropDown() {
		Assertions.assertEquals("Superior", page.isElementEquals(page.selectSchooling("Superior")));
		Assertions.assertTrue(page.isSchoolingExist());
		Assertions.assertEquals(8, page.sizeListSchooling());
	}

	@Test
	void shouldReturnPhraseOnClickInSimpleButton() {
		Assertions.assertEquals("Obrigado!", page.clickSimpleButton());
	}

	@Test
	void shouldReturnPhraseOnClickInTextLink() {
		Assertions.assertEquals("Voltou!", page.getTextLink());
	}

	@Test
	void shouldFindTextOnScreen(){
		Assertions.assertTrue(page.isExistInPageSource("Campo de Treinamento"));
		Assertions.assertTrue(page.isExistinTag("h3", "Campo de Treinamento"));
		Assertions.assertEquals("Cuidado onde clica, muitas armadilhas...", page.getTextByClassName());
	}

	@Test
	void shouldReturnSimpleTextInAlert() {
		page.clickElement("alert");
		Assertions.assertEquals("Alert Simples", page.getTextAlert());
		page.acceptAlert();
	}

	@Test
	void shouldVerifyAllResultsInAlerts() {
		page.clickElement("confirm");
		Assertions.assertEquals("Confirm Simples", page.getTextAlert());
		page.acceptAlert();

		Assertions.assertEquals("Confirmado", page.getTextAlert());
		page.acceptAlert();

		page.clickElement("confirm");
		page.dismisstAlert();

		Assertions.assertEquals("Negado", page.getTextAlert());
		page.acceptAlert();
	}

	@Test
	void shouldVerifyAllResultsInAlertPromptWitchAccept() {
		page.clickElement("prompt");
		Assertions.assertEquals("Digite um numero", page.getTextAlert());

		page.sendKeysAlert("123456");
		Assertions.assertEquals("Era 123456?", page.getTextAlert());
		page.acceptAlert();

		Assertions.assertEquals(":D", page.getTextAlert());
		page.acceptAlert();
	}

	@Test
	void shouldVerifyAllResultsInAlertPromptWitchDismiss() {
		page.clickElement("prompt");
		Assertions.assertEquals("Digite um numero", page.getTextAlert());
		page.dismisstAlert();

		Assertions.assertEquals("Era null?", page.getTextAlert());
		page.dismisstAlert();

		Assertions.assertEquals(":(", page.getTextAlert());
		page.acceptAlert();
	}

	@Test
	void testRegisterSuccess() {
		// Preenchendo um formulário e verificando o resultado
		String result = page.fillFildsForRegister("Abner", "Amos", "0", "0", "Mestrado", "Karate", "Games");
		Assertions.assertEquals(result, page.getTextByReturnRegister());
	}

	@Test
	void shouldInteractWithFrame() {
		String frame = page.getTextOtherAlertByIframe("frame1", "frameButton");
		Assertions.assertEquals("Frame OK!", frame);
		page.acceptAlert();
		page.switchToDefaultPage();
		Assertions.assertTrue(page.isExistInPageSource("Campo de Treinamento"));
	}

	@Test
	void shouldInteractWithWindowsEasy() {
		page.switchToOtherWindowsWithNameHandle();
		Assertions.assertTrue(page.isExistInPageSource("Campo de Treinamento"));
	}

	@Test
	void shouldInteractWithWindowsHard() {
		page.switchToOtherWindowWithoutNameHandle();
		Assertions.assertTrue(page.isExistInPageSource("Campo de Treinamento"));
	}

	@Test
	void shouldShowAndVerifyAllErrorsValidationsFields() {

		// Validando erro campo NOME inválido ---------------------------------------------------
		page.clickElement("elementosForm:cadastrar");
		Assertions.assertEquals("Nome eh obrigatorio", page.getTextAlert());
		page.acceptAlert();

		// Validando erro campo SOBRENOME inválido ----------------------------------------------
		page.setName("Abner");
		page.register();
		Assertions.assertEquals("Sobrenome eh obrigatorio", page.getTextAlert());
		page.acceptAlert();

		// Validando erro campo SEXO inválido ----------------------------------------------
		page.setName("Abner");
		page.setLastName("Amos");
		page.register();
		Assertions.assertEquals("Sexo eh obrigatorio", page.getTextAlert());
		page.acceptAlert();

		// Validando erro campo COMIDA FAVORITA se selecionado CARNE e VEGETARIANO inválido --
		page.setName("Abner");
		page.setLastName("Amos");
		page.setMan();
		page.setFoodMeat();
		page.setFoodVegetarian();
		page.register();
		Assertions.assertEquals("Tem certeza que voce eh vegetariano?", page.getTextAlert());
		page.acceptAlert();

		// Validando erro campo PRATICA ESPORTE se selecionado "Esporte" e "O que é esporte" inválido --
		page.setName("Abner");
		page.setLastName("Amos");
		page.setMan();
		page.setFoodMeat();
		page.setSports("Karate", "O que eh esporte?");
		page.register();
		Assertions.assertEquals("Voce faz esporte ou nao?", page.getTextAlert());
		page.acceptAlert();
	}
}
