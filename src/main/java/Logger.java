import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

class Logger {
    private PrintWriter log = null;
    private String path;
    private Desktop desktop = null;

    /**
     * Инициализация логгера
     * @param pathLog файл для логгера
     */
    void init(String pathLog){
        try {
            path = pathLog;
            desktop = Desktop.getDesktop();
            log  = new PrintWriter(pathLog);
            writeData("Создан лог-файл.");
        } catch (FileNotFoundException e) {
            System.out.println(getDate() +"     Не удалось создать лог-файл.");
        }
    }

    /**
     * Завершить работу логгера
     */
    void finishLog(){
        try {
            Driver.log.desktop.open(new File(Driver.log.path));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        log.close();
    }

    /**
     * Возвращает дату
     * @return возвращает дату в формате yyyy.MM.dd-HH.mm.ss
     */
    String getDate(){
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Moscow"));
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
        Date date=new Date();
        return format.format(date);
    }

    /**
     * Запись в лог-файл
     * @param s данные для записи
     */
    void writeData(String s){
        log.println(getDate() + "       " + s);
    }

    /**
     * Запись в лог-файл информации о странице
     * @param j номер страницы
     * @param countLotsWithEIS Количество лотов, имеющих ЕИС и не имеющих статус "Отменена"
     * @param sumWithEIS Сумма таких лотов
     * @param countLotsWithOutEIS Количество лотов, имеющих ЕИС и статус "Отменена"
     * @param sumWithOutEIS Сумма таких лотов
     */
    void writeData(int j, int countLotsWithEIS, double sumWithEIS, int countLotsWithOutEIS, double sumWithOutEIS){
        log.println();
        log.println(getDate());
        log.println("Страница " + (j+1) + ":");
        log.println("Количество лотов, имеющих ЕИС и не имеющих статус \"Отменена\": " + countLotsWithEIS);
        log.println("Сумма: " +
                Number.formatDouble(
                        BigDecimal.valueOf(
                                Number.around(
                                        sumWithEIS)).toPlainString()));
        log.println("Количество лотов, имеющих ЕИС и статус \"Отменена\": " + countLotsWithOutEIS);
        log.println("Сумма: " +
                Number.formatDouble(Number.around( sumWithOutEIS)));
    }

    /**
     * Печатает данные в терминал
     * @param j номер страницы
     * @param countLotsWithEIS количество лотов, имеющих ЕИС
     * @param sumWithEIS сумма по лотам, имеющих ЕИС
     */
    void writeDataConsol(int j, int countLotsWithEIS, double sumWithEIS){
        System.out.println("Страница " + (j+1));
        System.out.println("Количество лотов, имеющих ЕИС и не имеющих статус \"Отменено\": "+ countLotsWithEIS);
        System.out.println("Сумма: " +
                Number.formatDouble(
                        BigDecimal.valueOf(
                                Number.around(
                                        sumWithEIS)).toPlainString()) );
    }

    /**
     * Печатает данные в терминал
     * @param s данные для печати
     */
    void writeDataConsol(String s){
        System.out.println(s);
    }
}


