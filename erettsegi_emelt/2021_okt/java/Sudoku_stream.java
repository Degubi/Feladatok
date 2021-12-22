import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Sudoku_stream {

    public static void main(String[] args) throws IOException {
        System.out.println("1. Feladat: Írd be 1 bemeneti fájl nevét, 1 sor és 1 oszlop számát!");

        var input = new Scanner(System.in);
        var inputFileName = input.nextLine();
        var inputRowIndex = input.nextInt() - 1;
        var inputColumnIndex = input.nextInt() - 1;

        input.close();

        var numbersPerLine = Files.lines(Path.of(inputFileName))
                                  .map(k -> Arrays.stream(k.split(" ")).mapToInt(Integer::parseInt).toArray())
                                  .toArray(int[][]::new);

        var gameState = Arrays.copyOfRange(numbersPerLine, 0, 9);

        var valueAtInputSlot = gameState[inputRowIndex][inputColumnIndex];
        var tableNumber = 3 * (inputRowIndex / 3) + (inputColumnIndex / 3) + 1;
        var valueToPrint = valueAtInputSlot == 0 ? "Az adott helyet még nem töltötték ki!" : ("Adott helyen szereplő szám: " + valueAtInputSlot);

        System.out.println("3. Feladat: " + valueToPrint + ", résztáblázat száma: " + tableNumber);

        var unfilledSlotCount = Arrays.stream(gameState)
                                      .flatMapToInt(Arrays::stream)
                                      .filter(k -> k == 0)
                                      .count();

        System.out.printf("4. Feladat: Üres helyek aránya: %.1f%%\n", unfilledSlotCount / 81F * 100F);
        System.out.println("5. Feladat:");

        Arrays.stream(numbersPerLine, 9, numbersPerLine.length)
              .map(k -> "Sor: " + k[1] + ", oszlop: " + k[2] + ", érték: " + k[0] + ": " + getStepAttemptResultMessage(k[0], k[1] - 1, k[2] - 1, gameState))
              .forEach(System.out::println);
    }

    private static String getStepAttemptResultMessage(int value, int rowIndex, int columnIndex, int[][] gameState) {
        if(gameState[rowIndex][columnIndex] != 0) {
            return "A helyet már kitöltötték";
        }

        if(Arrays.stream(gameState[rowIndex]).anyMatch(k -> k == value)) {
            return "Az adott sorban már szerepel a szám";
        }

        if(Arrays.stream(gameState).anyMatch(k -> k[columnIndex] == value)) {
            return "Az adott oszlopban már szerepel a szám";
        }

        var beginRow = (rowIndex / 3) * 3;
        var endRow = beginRow + 3;
        var beginColumn = (columnIndex / 3) * 3;
        var endColumn = beginColumn + 3;

        if(Arrays.stream(gameState, beginRow, endRow).anyMatch(k -> Arrays.stream(k, beginColumn, endColumn).anyMatch(m -> m == value))) {
            return "Az adott résztáblában már szerepel a szám";
        }

        return "A lépés megtehető";
    }
}