import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Sudoku {

    public static void main(String[] args) throws IOException {
        System.out.println("1. Feladat: Írd be 1 bemeneti fájl nevét, 1 sor és 1 oszlop számát!");

        var input = new Scanner(System.in);
        var inputFileName = input.nextLine();
        var inputRowIndex = input.nextInt() - 1;
        var inputColumnIndex = input.nextInt() - 1;

        input.close();

        var lines = Files.readAllLines(Path.of(inputFileName));

        var numbersPerLine = new int[lines.size()][];
        for(var j = 0; j < lines.size(); j++) {
            var line = lines.get(j);
            var split = line.split(" ");
            var values = new int[split.length];

            for(var i = 0; i < split.length; ++i) {
                values[i] = Integer.parseInt(split[i]);
            }

            numbersPerLine[j] = values;
        }

        var gameState = Arrays.copyOfRange(numbersPerLine, 0, 9);

        var valueAtInputSlot = gameState[inputRowIndex][inputColumnIndex];
        var tableNumber = 3 * (inputRowIndex / 3) + (inputColumnIndex / 3) + 1;
        var valueToPrint = valueAtInputSlot == 0 ? "Az adott helyet még nem töltötték ki!" : ("Adott helyen szereplő szám: " + valueAtInputSlot);

        System.out.println("3. Feladat: " + valueToPrint + ", résztáblázat száma: " + tableNumber);

        var unfilledSlotCount = 0;
        for(var row : gameState) {
            for(var value : row) {
                if(value == 0) {
                    ++unfilledSlotCount;
                }
            }
        }

        System.out.printf("4. Feladat: Üres helyek aránya: %.1f%%\n", unfilledSlotCount / 81F * 100F);
        System.out.println("5. Feladat:");

        for(var i = 9; i < numbersPerLine.length; ++i) {
            var k = numbersPerLine[i];

            System.out.println("Sor: " + k[1] + ", oszlop: " + k[2] + ", érték: " + k[0] + ": " + getStepAttemptResultMessage(k[0], k[1] - 1, k[2] - 1, gameState));
        }
    }

    private static String getStepAttemptResultMessage(int value, int rowIndex, int columnIndex, int[][] gameState) {
        if(gameState[rowIndex][columnIndex] != 0) return "A helyet már kitöltötték";

        for(var values : gameState[rowIndex]) {
            if(values == value) {
                return "Az adott sorban már szerepel a szám";
            }
        }

        for(var row : gameState) {
            if(row[columnIndex] == value) {
                return "Az adott oszlopban már szerepel a szám";
            }
        }

        var beginRow = (rowIndex / 3) * 3;
        var endRow = beginRow + 3;
        var beginColumn = (columnIndex / 3) * 3;
        var endColumn = beginColumn + 3;

        for(var i = beginRow; i < endRow; ++i) {
            for(var k = beginColumn; k < endColumn; ++k) {
                if(gameState[i][k] == value) {
                    return "Az adott résztáblában már szerepel a szám";
                }
            }
        }

        return "A lépés megtehető";
    }
}