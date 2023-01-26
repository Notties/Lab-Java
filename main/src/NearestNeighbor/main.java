package NearestNeighbor;

import java.util.Random;
import java.util.Scanner;

public class main {
    public static Random rand = new Random();

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static int TABLE_SIZE = 10;
    public static final int NUM_RANGE = 100;
    public static int[][] table = generateRandomTable();

    public static void main(String[] args) {
        menu();
    }

    private static void menu() {
        Scanner sc = new Scanner(System.in);
        String number;
        do {
            System.out.print("================= Nearest Neighbor =================\n" +
                    "1. Input table size(Optional)\n" +
                    "2. Generate random table\n" +
                    "3. Show table with NearestNeighbor\n" +
                    "4. Generate random & show table NearestNeighbor\n" +
                    "0. Exit program\n" +
                    "====================================================\n" +
                    "Please Select menu: ");
            number = sc.next();
            switch (number) {
                case "1":
                    while (true) {
                        System.out.print("Enter table size: ");
                        if (sc.hasNextInt()) {
                            TABLE_SIZE = sc.nextInt();
                            break;
                        } else {
                            System.out.println("Invalid input. Please enter a number: ");
                            sc.next();
                        }
                    }
                    break;
                case "2":
                    table = generateRandomTable();
                    printtable(table);
                    break;
                case "3":
                    updatetableWithNearestNeighbor(table);
                    printtable(table);
                    break;
                case "4":
                    table = generateRandomTable();
                    updatetableWithNearestNeighbor(table);
                    printtable(table);
                    break;
                case "0":
                    System.out.println("Program exit.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("\n----------------------------------------------------\n" +
                            "Please input number 1-4 or 0 to exit program\n" +
                            "----------------------------------------------------\n");
                    break;
            }
        } while (number != "0");
    }

    private static int[][] generateRandomTable() {
        int[][] table = new int[TABLE_SIZE][TABLE_SIZE];
        Random rand = new Random();

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                table[i][j] = ' ';
            }
        }
        for (int i = 0; i < 12; i++) {
            int x = rand.nextInt(TABLE_SIZE);
            int y = rand.nextInt(TABLE_SIZE);
            while (table[x][y] != ' ') {
                x = rand.nextInt(TABLE_SIZE);
                y = rand.nextInt(TABLE_SIZE);
            }
            table[x][y] = (i < 6) ? -1 : -2;
        }
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                if (table[i][j] == ' ') {
                    table[i][j] = i * 10 + j;
                }
            }
        }
        return table;
    }

    private static void updatetableWithNearestNeighbor(int[][] table) {
        for (int i = 0; i < TABLE_SIZE; i++) {
            for (int j = 0; j < TABLE_SIZE; j++) {
                if (table[i][j] == -1 || table[i][j] == -2)
                    continue;
                int closestValue = 100;
                int closestValueX = 0;
                int closestValueY = 0;
                for (int x = 0; x < TABLE_SIZE; x++) {
                    for (int y = 0; y < TABLE_SIZE; y++) {
                        if (table[x][y] == -1 || table[x][y] == -2) {
                            int distance = (int) Math.sqrt(Math.pow(i - x, 2) + Math.pow(j - y, 2));
                            System.out.print(distance+"  \n");
                            if (distance < closestValue) {
                                closestValue = distance;
                                closestValueX = x;
                                closestValueY = y;
                            }
                        }
                    }
                }
                if (table[closestValueX][closestValueY] == -1) {
                    table[i][j] = -11;
                }
                if (table[closestValueX][closestValueY] == -2) {
                    table[i][j] = -22;
                }
            }
        }
    }

    private static void printtable(int[][] table) {
        String horizontalLine = "";
        for (int i = 0; i < TABLE_SIZE; i++) {
            if (i == 0) {
                horizontalLine += "----";
            } else if (i == TABLE_SIZE - 1) {
                horizontalLine += "+----";
            } else {
                horizontalLine += "+---";
            }
        }
        System.out.println(horizontalLine);
        for (int i = 0; i < table.length; i++) {
            System.out.print("|");
            for (int j = 0; j < table.length; j++) {
                String out = -1 == table[i][j] ? ANSI_YELLOW + "X" + ANSI_RESET
                        : -2 == table[i][j] ? ANSI_BLUE + "O" + ANSI_RESET
                                : -11 == table[i][j] ? ANSI_RED + "x" + ANSI_RESET
                                        : -22 == table[i][j] ? ANSI_GREEN + "o" + ANSI_RESET : " ";
                System.out.print(" " + out + " |");
            }
            System.out.println("\n" + horizontalLine);
        }
    }
}
