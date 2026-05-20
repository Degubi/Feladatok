using System;
using System.IO;

Console.WriteLine("1. Feladat: Írd be 1 bemeneti fájl nevét, 1 sor és 1 oszlop számát!");

var inputFileName = Console.ReadLine();
var inputRowIndex = int.Parse(Console.ReadLine()) - 1;
var inputColumnIndex = int.Parse(Console.ReadLine()) - 1;

var lines = File.ReadAllLines(inputFileName);

var numbersPerLine = new int[lines.Length][];
for(var j = 0; j < lines.Length; ++j) {
    var line = lines[j];
    var split = line.Split(' ');
    var values = new int[split.Length];

    for(var i = 0; i < split.Length; ++i) {
        values[i] = int.Parse(split[i]);
    }

    numbersPerLine[j] = values;
}

var gameState = numbersPerLine[0 .. 9];

var valueAtInputSlot = gameState[inputRowIndex][inputColumnIndex];
var tableNumber = 3 * (inputRowIndex / 3) + (inputColumnIndex / 3) + 1;
var valueToPrint = valueAtInputSlot == 0 ? "Az adott helyet még nem töltötték ki!" : ("Adott helyen szereplő szám: " + valueAtInputSlot);

Console.WriteLine($"3. Feladat: {valueToPrint}, résztáblázat száma: {tableNumber}");

var unfilledSlotCount = 0;
foreach(var row in gameState) {
    foreach(var value in row) {
        if(value == 0) {
            ++unfilledSlotCount;
        }
    }
}

Console.WriteLine($"4. Feladat: Üres helyek aránya: {(unfilledSlotCount / 81F * 100F).ToString("0.0")}");
Console.WriteLine("5. Feladat:");

for(var i = 9; i < numbersPerLine.Length; ++i) {
    var k = numbersPerLine[i];

    Console.WriteLine($"Sor: {k[1]}, oszlop: {k[2]}, érték: {k[0]}: {getStepAttemptResultMessage(k[0], k[1] - 1, k[2] - 1, gameState)}");
}


String getStepAttemptResultMessage(int value, int rowIndex, int columnIndex, int[][] gameState) {
    if(gameState[rowIndex][columnIndex] != 0) return "A helyet már kitöltötték";

    foreach(var values in gameState[rowIndex]) {
        if(values == value) {
            return "Az adott sorban már szerepel a szám";
        }
    }

    foreach(var row in gameState) {
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