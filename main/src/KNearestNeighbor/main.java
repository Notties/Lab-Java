package KNearestNeighbor;

import java.util.Arrays;
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

  public static int table_size = 20;
  public static int Key = 7;
  public static final int NUM_RANGE = 100;
  public static int[][] table = generateRandomTable();

  public static void main(String[] args) {
    menu();
  }

  private static void menu() {
    Scanner sc = new Scanner(System.in);
    String number;
    do {
      System.out.print(
        "================= K-Nearest Neighbor =================\n" +
        "1. Input table size & K value(Optional)\n" +
        "2. Generate random table\n" +
        "3. Show table with K-NearestNeighbor\n" +
        "4. Generate random & show table K-NearestNeighbor\n" +
        "0. Exit program\n" +
        "====================================================\n" +
        "Please Select menu: "
      );
      number = sc.next();
      switch (number) {
        case "1":
          while (true) {
            System.out.print("Enter table size: ");
            if (sc.hasNextInt()) {
              table_size = sc.nextInt();
              break;
            } else {
              System.out.println("Invalid input. Please enter a number: ");
              sc.next();
            }
          }
          while (true) {
            System.out.print("Enter K value: ");
            if (sc.hasNextInt()) {
              Key = sc.nextInt();
              break;
            } else {
              System.out.println("Invalid input. Please enter a number: ");
              sc.next();
            }
          }
          break;
        case "2":
          table = generateRandomTable();
          printTable(table);
          break;
        case "3":
          updatetableWithKNearestNeighbor(table, Key);
          printTable(table);
          break;
        case "4":
          table = generateRandomTable();
          updatetableWithKNearestNeighbor(table, Key);
          printTable(table);
          break;
        case "0":
          System.out.println("Program exit.");
          System.exit(0);
          break;
        default:
          System.out.println(
            "\n----------------------------------------------------\n" +
            "Please input number 1-4 or 0 to exit program\n" +
            "----------------------------------------------------\n"
          );
          break;
      }
    } while (number != "0");
  }

  private static int[][] generateRandomTable() {
    int[][] table = new int[table_size][table_size];
    Random rand = new Random();

    // initialize values array2D
    for (int i = 0; i < table.length; i++) {
      for (int j = 0; j < table.length; j++) {
        table[i][j] = ' ';
      }
    }

    // random values -1 6 each & -2 6 each in array2D
    for (int i = 0; i < 12; i++) {
      int x = rand.nextInt(table_size);
      int y = rand.nextInt(table_size);
      while (table[x][y] != ' ') {
        x = rand.nextInt(table_size);
        y = rand.nextInt(table_size);
      }
      table[x][y] = (i < 6) ? -1 : -2;
    }

    // add number 0-99 in array2D
    for (int i = 0; i < table.length; i++) {
      for (int j = 0; j < table.length; j++) {
        if (table[i][j] == ' ') {
          table[i][j] = i * 10 + j;
        }
      }
    }
    return table;
  }

  private static void updatetableWithKNearestNeighbor(int[][] table, int k) {
    for (int i = 0; i < table_size; i++) {
        for (int j = 0; j < table_size; j++) {
            if (table[i][j] == -1 || table[i][j] == -2) continue;
            int[] closestValues = new int[k];
            int[] closestValuesX = new int[k];
            int[] closestValuesY = new int[k];
            Arrays.fill(closestValues, Integer.MAX_VALUE);
            for (int x = 0; x < table_size; x++) {
                for (int y = 0; y < table_size; y++) {
                    if (table[x][y] == -1 || table[x][y] == -2) {
                        int distance = (int) Math.sqrt(Math.pow(i - x, 2) + Math.pow(j - y, 2));
                        for (int n = 0; n < k; n++) {
                            if (distance < closestValues[n]) {
                                for (int m = k - 1; m > n; m--) {
                                    closestValues[m] = closestValues[m - 1];
                                    closestValuesX[m] = closestValuesX[m - 1];
                                    closestValuesY[m] = closestValuesY[m - 1];
                                }
                                closestValues[n] = distance;
                                closestValuesX[n] = x;
                                closestValuesY[n] = y;
                                break;
                            }
                        }
                    }
                }
            }
            int countNeg1 = 0;
            int countNeg2 = 0;
            for (int n = 0; n < k; n++) {
                if (table[closestValuesX[n]][closestValuesY[n]] == -1) {
                    countNeg1++;
                } else if (table[closestValuesX[n]][closestValuesY[n]] == -2) {
                    countNeg2++;
                }
            }
            table[i][j] = countNeg1 > countNeg2 ? -11 : -22;
        }
    }
}


  private static void printTable(int[][] table) {
    String horizontalLine = "";
    for (int i = 0; i < table_size; i++) {
      if (i == 0) {
        horizontalLine += "----";
      } else if (i == table_size - 1) {
        horizontalLine += "+----";
      } else {
        horizontalLine += "+---";
      }
    }
    System.out.println(horizontalLine);
    for (int i = 0; i < table.length; i++) {
      System.out.print("|");
      for (int j = 0; j < table.length; j++) {
        String out = -1 == table[i][j]
          ? ANSI_YELLOW + "X" + ANSI_RESET
          : -2 == table[i][j]
            ? ANSI_BLUE + "O" + ANSI_RESET
            : -11 == table[i][j]
              ? ANSI_RED + "x" + ANSI_RESET
              : -22 == table[i][j] ? ANSI_GREEN + "o" + ANSI_RESET : " ";
        System.out.print(" " + out + " |");
      }
      System.out.println("\n" + horizontalLine);
    }
  }
}
