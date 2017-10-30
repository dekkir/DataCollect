import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

/**
 * Реализует таблицу, из которой извлекаются и обрабатываются данные во время теста
 */
 class TableOfPurchase {
    private WebDriver driver;

    /**
     *   Селектор выбора количество за раз отображемых строк
     */
    @FindBy(xpath = ".//*[@id='BaseMainContent_MainContent_jqgTrade_toppager_center']//select")
    private WebElement selector = null;

    /**
     *   Страница в таблице
     */
    @FindBy(xpath = ".//*[@id='next_t_BaseMainContent_MainContent_jqgTrade_toppager']/span")
    private WebElement pageTable = null;

    /**
     *   Таблица
     */
    @FindBy(id = "BaseMainContent_MainContent_jqgTrade")
    private WebElement table = null;

    /**
     *   Количество страниц таблицы
     */
    @FindBy(xpath = ".//*[@id='sp_1_BaseMainContent_MainContent_jqgTrade_toppager']")
    private WebElement countPageTable = null;


    TableOfPurchase(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

     /**
      * Определяет количество страниц в таблице
      * @return количество страниц
      */
    int getCountPagesTable(){
        return Integer.parseInt(countPageTable.getText().replaceAll("\\s+",""));
    }

     /**
      * Возвращает таблицу данных
      * @return таблица данных
      */
    WebElement getTable(){
        return table;
    }

     /**
      * Изменяет номер страницы в таблице
      */
    void changePageTable(){
        pageTable.click();
    }

     /**
      * Изменяет отображение количества записей за раз на странице таблице равным 100
      */
    void changeSelect100(){
        Select select = new Select(selector);
        select.selectByValue("100");
    }

}
