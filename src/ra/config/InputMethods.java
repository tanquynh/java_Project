package ra.config;

import java.util.Scanner;

public class InputMethods {
    private static final String ERROR_ALERT =
            "\n+----------------------------------+\n" +
            "|     ĐỊNH DẠNG KHÔNG HỢP LỆ       |\n" +
            "+----------------------------------+\n";
    ;
    private static final String EMPTY_ALERT =
            "\n+----------------------------------+\n" +
            "|TRƯỜNG NHẬP VÀO KHÔNG ĐƯỢC ĐỂ TRỐNG|\n" +
            "+-----------------------------------+\n";

    public static Scanner scanner() {
        return new Scanner(System.in);
    }
    private static String getInput() {
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }
    public static String getString() {
        while (true) {
            String result = getInput() ;
             if(result.trim().equals("")) {
                 System.err.println(EMPTY_ALERT);
                 continue;
             }
             return result;
        }
    }
    public static int getInteger() {
        while (true) {
            try {
                return Integer.parseInt(getString());
            } catch (NumberFormatException errException) {
                System.err.println(ERROR_ALERT);
            }
        }
    }

    public static double getDouble() {
        while (true) {
            try {
                return Double.parseDouble(getString());
            } catch (NumberFormatException errException) {
                System.err.println(ERROR_ALERT);
            }
        }
    }




}
