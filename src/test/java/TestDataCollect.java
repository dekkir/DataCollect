import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.List;

public class TestDataCollect {

    private PurchaseSearchForm psf;
    private  TableOfPurchase top;

    /**
     * Количество страниц для обработки по умолчанию 5, чтобы обрабатывать все страницы, измените
     * EXAMPLE на true
     */
    private static int countPages = 5;
    private  boolean EXAMPLE = true;

    @BeforeSuite
    public void setUp(){
        Driver.log = new Logger();
        Driver.log.init("src\\rezult\\log" + Driver.log.getDate() + ".txt");
        Driver.log.writeData("Начало работы драйвера");
        Driver.init();
        psf = new PurchaseSearchForm(Driver.driver);
        top = new TableOfPurchase(Driver.driver);
    }

    @AfterSuite
    public void close(){
        Driver.log.writeData("Завершение работы драйвера");
        Driver.finish();
        Driver.log.finishLog();
    }

    /**
     * Сбор данных по параметрам:
     * check-box Тип закупки: коммерческая закупка
     * check-box Тип закупки: Закупка в соответствии с нормами 223-ФЗ
     * Начальная цена от 0
     * Собираются: сумма лотов, имеющихся ЕИС и не имеющих статус "Отменена"
     *      и сумма лотов, имеющих ЕИС и статус "Отменена"
     * Тест состоит из 5 этапов:
     * 1. Ввод параметров и осуществление поиска
     * 2. Изменение количества записей в таблице на одной странице с 10 до 100
     * 3. Непосредственно работа с таблицей и данными
     * 4. Обработка статистики по странице
     * 5. Обработка статистики по пройденным страницам
     */
    @Test
    public void dataCollect(){
        /*
          Ввод параметров и осуществление поиска
         */
        Driver.log.writeData("Ввод параметров поиска");
        psf.selectTypePurchaseCommercial();
        psf.selectTypePurchaseWith223FZ();
        psf.fillPriceFrom("0");
        psf.clickSearch();
        Driver.await(7);
        /*
         * Изменение количества записей в таблице на одной странице с 10 до 100
         */
        top.changeSelect100();
        Driver.await(5);
        if(!EXAMPLE) //обработка всех страниц
            countPages = top.getCountPagesTable();
        else{
            Driver.log.writeDataConsol("Внимание. Будет обработано только первые 5 страниц по 100 записей,\n" +
                    "чтобы обработать все страницы, смените значение переменной EXAMPLE на false\n" +
                    "в классе TestDataCollect\n" +
                    "Это сделано с целью для быстрой демонстрации работы теста");
            Driver.log.writeData("Внимание. Будет обработано только первые 5 страниц по 100 записей,\n" +
                    "чтобы обработать все страницы, смените значение переменной EXAMPLE на false\n" +
                    "в классе TestDataCollect\n" +
                    "Это сделано с целью для быстрой демонстрации работы теста\");");
        }
        double allSum[] = new double[countPages]; //суммы с каждой страницы (лоты с ЕИС и без признака "отменена")
        int allLots[] = new int[countPages]; //кол-во лотов с каждой страницы (с ЕИС и без признака "отменена")
        /*
         * Непосредственно работа с таблицей и данными
         */
        WebElement table = top.getTable();
        Driver.log.writeData("Начало работы с таблицей данных");
        for(int j = 0; j <  countPages; j++){
            Driver.await(5); //ждем загрузку записей очередной страницы
            List<WebElement> allRows = table.findElements(By.tagName("tr"));
            /*
             * Переменные для сбора статистики по лотам
             */
            double sumWithEIS = 0, sumWithOutEIS = 0;
            int countLotsWithEIS = 0, countLotsWithOutEIS = 0;
            /*
             * Обработка страницы
             */
            for (int i = 1; i < allRows.size(); i++) {//i == 0 - header
                List<WebElement> cells = (allRows.get(i)).findElements(By.tagName("td"));
                String eis = cells.get(5).getAttribute("title");
                String status = cells.get(15).getAttribute("title");
                //есть ЕИС и не отменена
                if(!(eis.isEmpty()) && !(status.equals("Отменена"))) {//EIS
                    sumWithEIS += Number.getNumber(cells.get(10).getAttribute("title")); //price
                    countLotsWithEIS++;
                }
                //есть ЕИС и отменена
                else if(!(eis.isEmpty()) && status.equals("Отменена")) {
                    sumWithOutEIS += Number.getNumber(cells.get(10).getAttribute("title")); //price
                    countLotsWithOutEIS++;
                }
            }
            /*
             * Обработка статистики по странице
             */
            allSum[j] = sumWithEIS - sumWithOutEIS;
            allLots[j] = countLotsWithEIS - countLotsWithOutEIS;
            Driver.log.writeData(j, countLotsWithEIS, sumWithEIS, countLotsWithOutEIS, sumWithOutEIS);
            Driver.log.writeDataConsol(j, countLotsWithEIS, sumWithEIS);
            top.changePageTable(); //новая страница таблицы
        }
        /*
         * Обработка статистики по пройденным страницам
         */
        Driver.log.writeDataConsol("________\nВся сумма по лотам: " + Number.getAllSum(allSum));
        Driver.log.writeData(
                "\nОбщая сумма по лотам, имеющим ЕИС и не имеющим статус \"Отменена\": " +
                        Number.getAllSum(allSum));
        Driver.log.writeDataConsol("Общее количество лотов: " + Number.getAllLots(allLots));
        Driver.log.writeData("\nОбщее количество лотов: " + Number.getAllLots(allLots));
        Driver.log.writeDataConsol("Подробный отчет в файле.");
    }
}
