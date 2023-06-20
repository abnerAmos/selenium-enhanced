package selenium.study;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class TestPage extends PageObjetc{

    public void fillField(String id, String text) {
        clearField(id);
        driver.findElement(By.id(id)).sendKeys(text);
    }

    public String getTextField(String id) {
        return driver.findElement(By.id(id)).getAttribute("value");
        // .getAttribute("value") captura o valor dentro de um campo no form.
    }

    public void clearField(String id) {
        driver.findElement(By.id(id)).clear();
    }

    public String clickElementWithReturn(String id) {
        WebElement click = driver.findElement(By.id(id));
        click.click();
        return click.getAttribute("value");
    }

    public void clickElement(String id) {
        driver.findElement(By.id(id)).click();
    }

    public boolean isElementSelected(String id) {
        return driver.findElement(By.id(id)).isSelected();
    }

    public String isElementEquals(WebElement value) {
        return new Select(value).getFirstSelectedOption().getText(); // verifica o campo selecionado.
    }

    public WebElement selectSchooling(String schooling) {
        WebElement dropDown = driver.findElement(By.id("elementosForm:escolaridade")); // Seleciona o Elemento da Página
        Select array = new Select(dropDown); // observa os itens dentro de um dropdown
//		array.selectByIndex(3); // seleciona pelo intex
//		array.selectByValue("superior"); // seleciona pelo atributo "value" implicito dentro da tag html
        array.selectByVisibleText(schooling); // seleciona pelo valor visivel.
        return dropDown;
    }

    public boolean isElementExist(String id) {
        WebElement dropDown = driver.findElement(By.id(id)); // Seleciona o Elemento da Página.
        Select array = new Select(dropDown); // captura os elementos e joga dentro de uma Array List.
        List<WebElement> options = array.getOptions(); // captura os elementos e joga dentro de um arrayList.

        // percorrendo a lista
        boolean find = false;
        for (WebElement option : options) {
            if (option.getText().equals("Mestrado")) {
                find = true;
                break;
            }
        }
        return find;
    }

    public int sizeElement(String id) {
        WebElement dropDown = driver.findElement(By.id(id));
        Select array = new Select(dropDown);
        List<WebElement> options = array.getOptions();
        return options.size();
    }

    public int countElementsSelected(String id) {
        WebElement dropDown = driver.findElement(By.id(id)); // Seleciona o Elemento da Página.
        Select array = new Select(dropDown); // captura os elementos e joga dentro de uma Array List.
        array.selectByVisibleText("Futebol");
        array.selectByVisibleText("Corrida");
        array.selectByVisibleText("Karate"); // Seleciona diversos itens do dropDown

        List<WebElement> allSelectOptions = array.getAllSelectedOptions();
        return allSelectOptions.size(); // verificando o tamanho da lista selecionada
    }

    public int countElementsSelectedAfterDeslesectedElements(String id) {
        WebElement dropDown = driver.findElement(By.id(id));
        Select array = new Select(dropDown);
        array.selectByVisibleText("Futebol");
        array.selectByVisibleText("Corrida");
        array.selectByVisibleText("Karate");

        array.deselectByVisibleText("Karate");
        List<WebElement> allSelectOptions = array.getAllSelectedOptions(); // Deseleciona diversos itens do dropDown

        return allSelectOptions.size();
    }

    public String getTextLink(String link) {
        driver.findElement(By.linkText(link)).click();
        WebElement result = driver.findElement(By.id("resultado"));
        return result.getText();
    }

    public boolean isExistInPageSource(String text) {
        return driver.getPageSource().contains(text); // busca por um texto em todoo o HTML
    }

    public boolean isExistinTag(String tag, String text) {
        return driver.findElement(By.tagName(tag)).getText().contains(text); // busca por um texto filtrando pela tag do HTML
    }

    public String getTextByClassName(String className) {
        return driver.findElement(By.className(className)).getText(); // busca por um texto filtrando pela className do HTML
    }

    public String getTextByElement(String id) {
        return driver.findElement(By.id(id)).getText();
    }

    public String getTextAlert() {
        return driver.switchTo().alert().getText();
        /* O alert esta fora do HTML, para capturar os seus valores
		é necessário trocar a "visão" do HTML para o Alert */
    }

    public void acceptAlert() {
        driver.switchTo().alert().accept();
        // Fecha o alert pelo "OK", caso não seja fechado, as proximas interações com o HTML não irão funcionar.
    }

    public void dismisstAlert() {
        driver.switchTo().alert().dismiss();
        // Fecha o alert pelo botão "CANCELAR"
    }

    public void sendKeysAlert(String number) {
        Alert alert = driver.switchTo().alert();
        alert.sendKeys(number); // Preenchendo um campo dentro de um Alert
        acceptAlert();
    }

    public String fillFildsForRegister(String name, String lastName, String gender, String food, String schooling, String sport, String suggestions) {
        fillField("elementosForm:nome", name);
        fillField("elementosForm:sobrenome", lastName);
        clickElement(String.format("elementosForm:sexo:%s", gender));
        clickElement(String.format("elementosForm:comidaFavorita:%s", food));

        new Select(
                driver.findElement(By.id("elementosForm:escolaridade")))
                .selectByVisibleText(schooling);

        new Select(
                driver.findElement(By.id("elementosForm:esportes")))
                .selectByVisibleText(sport);

        fillField("elementosForm:sugestoes", suggestions);
        clickElement("elementosForm:cadastrar");

        return """
                Cadastrado!
                Nome: Abner
                Sobrenome: Amos
                Sexo: Masculino
                Comida: Carne
                Escolaridade: mestrado
                Esportes: Karate
                Sugestoes: Games""";
    }

    public String getTextOtherAlertByIframe(String frame, String id) {
        // Interangindo com um Iframe (HTML dentro de outro)

        driver.switchTo().frame(frame);
        driver.findElement(By.id(id)).click();
        Alert alert = driver.switchTo().alert();
        return alert.getText();
    }

    public void switchToDefaultPage() {
        driver.switchTo().defaultContent();
    }

    public void switchToOtherWindowsWithNameHandle() {
        // Interangindo com uma outra janela aberta a partir da corrente quando possuí titulo

        clickElement("buttonPopUpEasy");
        driver.switchTo().window("Popup");
        driver.findElement(By.tagName("textarea")).sendKeys("Deu certo?");
        driver.close(); // Fecha apenas a janela atual
        driver.switchTo().window("");
        driver.findElement(By.tagName("textarea")).sendKeys("Deu certo!");
    }

     public void switchToOtherWindowWithoutNameHandle() {
         driver.findElement(By.id("buttonPopUpHard")).click();
         System.out.println(driver.getWindowHandle()); // Obtem o numero de "id" da janela corrente
         System.out.println(driver.getWindowHandles()); // Obtem o numero de "id" das janelas selecionadas em ("buttonPopUpHard")

         driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
         driver.findElement(By.tagName("textarea")).sendKeys("Deu certo?");

         driver.switchTo().window(driver.getWindowHandles().toArray()[0].toString());
         driver.findElement(By.tagName("textarea")).sendKeys("Deu certo!");
     }

     public void isSportsPractitioner(String id) {
         WebElement sports = driver.findElement(By.id(id));
         Select select = new Select(sports);
         select.selectByVisibleText("Karate");
         select.selectByVisibleText("O que eh esporte?");
     }
}
