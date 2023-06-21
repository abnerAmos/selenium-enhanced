package selenium.study;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class TrainingPage extends PageObjetc {

    public void quitPages() {
        driver.quit();
    }

    public void clearField(String id) {
        driver.findElement(By.id(id)).clear();
    }

    public String getName() {
        return driver.findElement(By.id("elementosForm:nome")).getAttribute("value");
    }

    public void setName(String name) {
        clearField("elementosForm:nome");
        driver.findElement(By.id("elementosForm:nome")).sendKeys(name);
    }

    public void setLastName(String name) {
        driver.findElement(By.id("elementosForm:sobrenome")).sendKeys(name);
    }

    public String getSuggestions() {
        return driver.findElement(By.id("elementosForm:sugestoes")).getAttribute("value");
        // .getAttribute("value") captura o valor dentro de um campo no form.
    }

    public void setSuggestions(String suggestions) {
        driver.findElement(By.id("elementosForm:sugestoes")).sendKeys(suggestions);
    }

    public void setMan() {
        driver.findElement(By.id("elementosForm:sexo:0")).click();
    }

    public void setFoodMeat() {
        driver.findElement(By.id("elementosForm:comidaFavorita:0")).click();
    }

    public void setFoodVegetarian() {
        driver.findElement(By.id("elementosForm:comidaFavorita:3")).click();
    }

    public String clickSimpleButton() {
        WebElement click = driver.findElement(By.id("buttonSimple"));
        click.click();
        return click.getAttribute("value");
    }

    public boolean isManSelected() {
        return driver.findElement(By.id("elementosForm:sexo:0")).isSelected();
    }

    public boolean isMeatSelected() {
        return driver.findElement(By.id("elementosForm:comidaFavorita:0")).isSelected();
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

    public boolean isSchoolingExist() {
        WebElement dropDown = driver.findElement(By.id("elementosForm:escolaridade")); // Seleciona o Elemento da Página.
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

    public int sizeListSchooling() {
        WebElement dropDown = driver.findElement(By.id("elementosForm:escolaridade"));
        Select array = new Select(dropDown);
        List<WebElement> options = array.getOptions();
        return options.size();
    }

    public int countElementsSelected(String... sports) {
        String[] list = setSports(sports);
        return list.length;
    }

    public int countElementsSelectedAfterDeslesectedElements() {
        WebElement dropDown = driver.findElement(By.id("elementosForm:esportes"));
        Select array = new Select(dropDown);
        array.selectByVisibleText("Futebol");
        array.selectByVisibleText("Corrida");
        array.selectByVisibleText("Karate");  // Seleciona diversos itens do dropDown

        array.deselectByVisibleText("Karate"); // Deseleciona diversos itens do dropDown
        List<WebElement> allSelectOptions = array.getAllSelectedOptions();  // verificando o tamanho da lista selecionada

        return allSelectOptions.size();
    }

    public String getTextLink() {
        driver.findElement(By.linkText("Voltar")).click();
        WebElement result = driver.findElement(By.id("resultado"));
        return result.getText();
    }

    public boolean isExistInPageSource(String text) {
        return driver.getPageSource().contains(text); // busca por um texto em todoo o HTML
    }

    public boolean isExistinTag(String tag, String text) {
        return driver.findElement(By.tagName(tag)).getText().contains(text); // busca por um texto filtrando pela tag do HTML
    }

    public String getTextByClassName() {
        return driver.findElement(By.className("facilAchar")).getText(); // busca por um texto filtrando pela className do HTML
    }

    public String getTextByReturnRegister() {
        return driver.findElement(By.id("resultado")).getText();
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
        setName(name);
        setLastName(lastName);
        clickElement(String.format("elementosForm:sexo:%s", gender));
        clickElement(String.format("elementosForm:comidaFavorita:%s", food));

        new Select(
                driver.findElement(By.id("elementosForm:escolaridade")))
                .selectByVisibleText(schooling);

        new Select(
                driver.findElement(By.id("elementosForm:esportes")))
                .selectByVisibleText(sport);

        setSuggestions(suggestions);
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

     public String[] setSports(String... sports) {
        for (String sport : sports)
            selectSports(sport);
        return sports;
     }

    public void selectSports(String valor) {
        WebElement element = driver.findElement(By.id("elementosForm:esportes"));
        Select sports = new Select(element);
        sports.selectByVisibleText(valor);
    }

    public void clickElement(String id) {
        driver.findElement(By.id(id)).click();
    }

    public void sendKeys(String id, String text) {
        driver.findElement(By.id(id)).sendKeys(text);
    }

    public void register() {
        driver.findElement(By.id("elementosForm:cadastrar")).click();
    }

    public void selectElementsWithXpath() {
        driver.findElement(By.xpath("//input[@name='elementosForm:nome']")).sendKeys("Abner");
        driver.findElement(By.xpath("//input[@type='radio' and @value='F']")).click();
        driver.findElement(By.xpath("//*[@type='checkbox' and @value='pizza']")).click();
        driver.findElement(By.xpath("//table[@id='elementosForm:tableUsuarios']//tr[1]//input[@type='button']")).click();
        driver.switchTo().alert().accept();
        driver.findElement(By.xpath("//table[@id='elementosForm:tableUsuarios']//tr[4]//td[last()]")).click();
        String analfabeto = driver.findElement(By.xpath("//*[@id='tabelaSemJSF']//*[.='Analfabeto']")).getText();
        driver.findElement(By.xpath("//table[@id='elementosForm:tableUsuarios']//tr[last()]//input[@type='text']")).sendKeys(analfabeto);
    }
}
