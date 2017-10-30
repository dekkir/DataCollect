import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
/**
 * Реализует некоторые поля формы, которые используются для поиска данных
 * Реализует методы для работы и обработки данных с таблицы
 * Используется ObjectPage на снове PageFactory
 */
class PurchaseSearchForm {
    private String title = "Поиск закупок";
    private WebDriver driver;

    /**
     *   Тип закупки:
     *   Коммерческая закупка
     */
    @FindBy(id = "BaseMainContent_MainContent_chkPurchaseType_1")
    private WebElement typePurchaseCommercial = null;

    /**
     *   Тип закупки:
     *   Закупка в соответствии с нормами 223-ФЗ
     */
    @FindBy(id = "BaseMainContent_MainContent_chkPurchaseType_0")
    private WebElement typePurchaseWith223FZ = null;

    /**
     *   Начальная цена
     *   от
     */
    @FindBy(id = "BaseMainContent_MainContent_txtStartPrice_txtRangeFrom")
    private WebElement priceFrom = null;

    /**
     *   Кнопка Поиск
     */
    @FindBy(id = "BaseMainContent_MainContent_btnSearch")
    private WebElement buttonSearch = null;



    PurchaseSearchForm(WebDriver driver){

        if(!driver.getTitle().equals(title)){
            throw new IllegalStateException(
                    "This is not the page you are expected"
            );
        }
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    /**
     * Выбор
     * Тип закупки:
     * Коммерческая закупка
     */
    void selectTypePurchaseCommercial(){
        typePurchaseCommercial.click();
    }

    /**
     * Выбор
     * Тип закупки:
     * Закупка в соответствии с нормами 223-ФЗ
     */
    void selectTypePurchaseWith223FZ(){
        typePurchaseWith223FZ.click();
    }

    /**
     * Заполнение
     * Начальная цена
     * @param s от
     */
    void fillPriceFrom(String s){
        priceFrom.sendKeys(s);
    }

    /**
     * Поиск по выбранным параметрам
     */
    void clickSearch(){
        buttonSearch.click();
    }





}
