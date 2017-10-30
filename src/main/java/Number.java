import java.math.BigDecimal;

/**
 * Реализует вспомогательные методы для обработки числовых данных из таблицы
 */
class Number {
    /**
     * Преобразует строку в число
     * @param s строка вида "123 456, 65 руб."
     * @return число вида 123456.65
     */
    static double getNumber(String s){
        s = s.replaceAll("\\s+", "");
        s = s.replaceAll(",", ".");
        s = s.replaceAll("[A-Za-zА-Яaа-я]+.", "");
        return Double.parseDouble(s);
    }

    /**
     * Округляет число до сотых
     * @param d число вида 123.45118
     * @return число вида 123.45
     */
    static double around(double d){
        return Math.rint(100.0 * d) / 100.0;
    }

    /**
     * Возвращает сумму элементов массива double
     * Такое название использовано для наглядности
     * @param allSum суммы лотов по страницам
     * @return сумма всех элементов
     */
    static String getAllSum(double[] allSum){
        BigDecimal b = new BigDecimal("0");
        for (double anAllSum : allSum) {
            b = b.add(BigDecimal.valueOf(anAllSum));
        }
        return formatDouble(b.setScale(2, BigDecimal.ROUND_CEILING).toPlainString());
    }

    /**
     * Возвращает сумму элементов массива int
     * Такое название использовано для наглядности
     * @param allLots массив с числом лотов по каждой странице
     * @return сумма количества лотов
     */
    static String getAllLots(int allLots[]){
        int rez = 0;
        for (int allLot : allLots) rez += allLot;
        return Integer.toString(rez);
    }

    /**
     * Преобразует double в строку вида "[0-9]+ [0-9]+ ... [0-9]+.[0-9]+", пример 234 432.34
     * @param d число для преобразования
     * @return строка вида "[0-9]+ [0-9]+ ... [0-9]+.[0-9]+"
     */
    static String formatDouble(double d){
        return formatDouble(String.valueOf(d));
    }

    /**
     * Преобразует строку с числом в строку вида "[0-9]+ [0-9]+ ... [0-9]+.[0-9]+", пример 234 432.34
     * @param s число для преобразования
     * @return строка вида "[0-9]+ [0-9]+ ... [0-9]+.[0-9]+"
     */
    static String formatDouble(String s){
        String remainder = s.substring(s.indexOf(".")+1);
        String full = s.substring(0, s.indexOf("."));
        String rezult = "";
        char ch[] = full.toCharArray();
        for(int i = ch.length-1; i >= 0;){
            for(int j = 0; j < 3  && i >= 0; i--, j++)
                rezult += ch[i];
            rezult += " ";
        }
        rezult = recursiveReverse(rezult.substring(0, rezult.length()-1)); //delete space
        return rezult + "." + remainder;

    }

    /**
     * Реверсирует строку
     * @param s строка для реверса
     * @return реверсивная строка
     */
    private static String recursiveReverse(String s) {
        if ((null == s) || (s.length() <= 1))
            return s;
        return recursiveReverse(s.substring(1)) + s.charAt(0);
    }
}
