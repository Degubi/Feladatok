using System;
using System.IO;
using System.Linq;

Console.WriteLine("1. Feladat: Írd be 1 bemeneti fájl nevét, 1 sor és 1 oszlop számát!");

var inputFileName = Console.ReadLine();
var inputRowIndex = int.Parse(Console.ReadLine()) - 1;
var inputColumnIndex = int.Parse(Console.ReadLine()) - 1;

var numbersPerLine = File.ReadLines(inputFileName)
                         .Select(k => k.Split(' ').Select(int.Parse).ToArray())
                         .ToArray();

var gameState = numbersPerLine[0 .. 9];

var valueAtInputSlot = gameState[inputRowIndex][inputColumnIndex];
var tableNumber = 3 * (inputRowIndex / 3) + (inputColumnIndex / 3) + 1;
var valueToPrint = valueAtInputSlot == 0 ? "Az adott helyet még nem töltötték ki!" : ("Adott helyen szereplő szám: " + valueAtInputSlot);

Console.WriteLine($"3. Feladat: {valueToPrint}, résztáblázat száma: {tableNumber}");

var unfilledSlotCount = gameState.SelectMany(k => k).Count(k => k == 0);

Console.WriteLine($"4. Feladat: Üres helyek aránya: {(unfilledSlotCount / 81F * 100F).ToString("0.0")}");
Console.WriteLine("5. Feladat:");

numbersPerLine.Skip(9)
              .ToList()
              .ForEach(k => Console.WriteLine($"Sor: {k[1]}, oszlop: {k[2]}, érték: {k[0]}: {getStepAttemptResultMessage(k[0], k[1] - 1, k[2] - 1, gameState)}"));


String getStepAttemptResultMessage(int value, int rowIndex, int columnIndex, int[][] gameState) {
    if(gameState[rowIndex][columnIndex] != 0) return "A helyet már kitöltötték";
    if(gameState[rowIndex].Any(k => k == value)) return "Az adott sorban már szerepel a szám";
    if(gameState.Any(k => k[columnIndex] == value)) return "Az adott oszlopban már szerepel a szám";

    var beginRow = (rowIndex / 3) * 3;
    var endRow = beginRow + 3;
    var beginColumn = (columnIndex / 3) * 3;
    var endColumn = beginColumn + 3;

    if(gameState[beginRow .. endRow].Any(k => k[beginColumn .. endColumn].Any(m => m == value))) {
        return "Az adott résztáblában már szerepel a szám";
    }

    return "A lépés megtehető";
}