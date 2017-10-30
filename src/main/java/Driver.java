import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static java.lang.Thread.sleep;

/**
 * Реализует драйвер для проведения тестов в браузере FireFox
 */
class Driver {
    static Logger log;
    static WebDriver driver = null;

    /**
     * Инициализация драйвера
     */
    static void init(){
        driver = new FirefoxDriver();
        driver.get("https://223.rts-tender.ru/supplier/auction/Trade/Search.aspx");
        await(1);
        System.out.println("Внимание. Ожидание загрузки очередной страницы данных в таблице равно 5 секунд");
        log.writeData("Внимание. Ожидание загрузки очередной страницы данных в таблице равно 5 секунд");
    }

    /**
     * Завершение работы драйвера
     */
    static void finish(){
        driver.close();
    }

    /**
     * Приостановить работу на заданное время
     * @param sec количество секунд, на которые поток заснет
     */
    static void await(int sec){
        try{
            sleep(sec * 1000);
        } catch (InterruptedException ie){
            System.out.println("In method await\n" + ie);
        }
    }



}
