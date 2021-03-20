import java.util.Scanner;


public class Main {
    public static void main(String ... args) {
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        if (isValid(str)) {
            System.out.println(takeStringFromBrackets(str));
        }
    }

    public static boolean isValid(String str) {
        //первое - неправильная скобочная последовательность
        int pos = 0;
        int openBrackets = 0;
        while (pos < str.length()) {
            if (str.charAt(pos) == '[') openBrackets++;
            else if(str.charAt(pos) == ']') openBrackets--;
            if (openBrackets < 0) throw new WrongBracketsOrderException();
            pos++;
        }
        if (openBrackets != 0) throw new WrongBracketsOrderException();

        //лишние символы между числом и скобками при условии правильной скобочной последовательности
        pos = 0;
        while (pos < str.length()) {
            if (isNumber(str.charAt(pos))) {
                while (pos < str.length() && isNumber(str.charAt(pos))) {
                    pos++;
                }
                if (pos == str.length()) throw new BadCredentialsException();
                else if(str.charAt(pos) != '[') throw new SymbolBetwenNumberAndBracketException();
            }
            pos++;
        }

        //скобки без чисел
        pos = 0;
        while (pos < str.length()) {
            if (str.charAt(pos) == '[' && (pos - 1 < 0 || !isNumber(str.charAt(pos - 1))))
                throw new BadCredentialsException();
            pos++;
        }

        return true;
    }

    // основная рекурсивная функция. Работает при условии валидности данных.
    // Валидность проверяем в другой функции
    public static String takeStringFromBrackets(String str) {
        StringBuilder newString = new StringBuilder();
        int i = 0;
        while(i < str.length()) {
            if(isNumber(str.charAt(i))) {
                StringBuilder execStrBuilder = new StringBuilder();
                int amountOfRepeats = Integer.parseInt(str.substring(i, endOfNumber(str, i) + 1));
                execStrBuilder.append(str.substring(str.indexOf('[') + 1));
                String execStr = execStrBuilder.substring(0, endOfExecStr(execStrBuilder) - 1);
                String appendix = takeStringFromBrackets(execStr);
                newString.append(appendix.repeat(Math.max(0, amountOfRepeats)));
                i = newString.toString().length();
                str = newString.toString() + str.substring(findLastCloseBracket(str));

            }
            else if(isBracket(str.charAt(i))){
                return newString.toString();
            }
            //буква
            else {
                newString.append(str.charAt(i));
                i++;
            }
        }
        return newString.toString();
    }

    //выдает индекс ] из строки вида 12[...] - последнюю
    private static int findLastCloseBracket(String str) {
        int opr = 1;
        int pos = str.indexOf('[') + 1;
        while (pos < str.length() && opr > 0) {
            if (str.charAt(pos) == '[') opr++;
            else if (str.charAt(pos) == ']') opr--;
            pos++;
        }
        return pos;
    }

    //возвращает конец проверяемой строки, то есть где закрывается скобка.
    //пример: "w3[d]s1[e]]" - функция выведет 10 - последняя закрывающая ']'.
    private static int endOfExecStr(StringBuilder execStr) {
        int opr = 1;
        int pos = 0;
        while (opr > 0 && pos < execStr.length()) {
            if (execStr.toString().charAt(pos) == '[') opr++;
            else if(execStr.toString().charAt(pos) == ']') opr--;
            pos++;
        }
        return pos;
    }

    //проверяет, является ли символ скобкой (любой)
    private static boolean isBracket(char charAt) {
        return charAt == '[' || charAt == ']';
    }

    //возвращает позицию последней цифры числа, например в строке "345" число 345 начинается с индекса 0
    //и заканчивается индексом 2. Эта функция в данном случае вернет 2.
    private static int endOfNumber(String str, int startPos) {
        int i = startPos;
        while (isNumber(str.charAt(i))) {
            i++;
        }
        return i - 1;
    }

    //определяем, является ли символ числом
    public static Boolean isNumber(Character symbol) {
        return symbol <= '9' && symbol >= '0';
    }
}
