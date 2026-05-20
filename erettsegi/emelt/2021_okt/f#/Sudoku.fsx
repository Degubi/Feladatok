open System
open System.IO

let getStepAttemptResultMessage(value: int, rowIndex: int, columnIndex: int, gameState: int[][]) =
    if gameState.[rowIndex].[columnIndex] <> 0 then "A helyet már kitöltötték"
    elif gameState.[rowIndex] |> Seq.exists(fun k -> k = value) then "Az adott sorban már szerepel a szám"
    elif gameState |> Seq.exists(fun k -> k.[columnIndex] = value) then "Az adott oszlopban már szerepel a szám"
    else
        let beginRow = (rowIndex / 3) * 3
        let endRow = beginRow + 3
        let beginColumn = (columnIndex / 3) * 3
        let endColumn = beginColumn + 3

        if gameState.[beginRow .. endRow - 1] |> Seq.exists(fun k -> k.[beginColumn .. endColumn - 1] |> Seq.exists(fun m -> m = value))
        then "Az adott résztáblában már szerepel a szám"
        else "A lépés megtehető"

printfn "1. Feladat: Írd be 1 bemeneti fájl nevét, 1 sor és 1 oszlop számát!"

let inputFileName = Console.ReadLine()
let inputRowIndex = int(Console.ReadLine()) - 1
let inputColumnIndex = int(Console.ReadLine()) - 1

let numbersPerLine = inputFileName |> File.ReadLines
                                   |> Seq.map(fun k -> k.Split " " |> Seq.map(int) |> Seq.toArray)
                                   |> Seq.toArray

let gameState = numbersPerLine.[0 .. 8]
let valueAtInputSlot = gameState.[inputRowIndex].[inputColumnIndex]
let tableNumber = 3 * (inputRowIndex / 3) + (inputColumnIndex / 3) + 1
let valueToPrint = if valueAtInputSlot = 0 then "Az adott helyet még nem töltötték ki!" else ($"Adott helyen szereplő szám: {valueAtInputSlot}")

printfn $"3. Feladat: {valueToPrint}, résztáblázat száma: {tableNumber}"

let unfilledSlotCount = gameState |> Seq.concat
                                  |> Seq.filter(fun k -> k = 0)
                                  |> Seq.length
                                  |> float

printfn "4. Feladat: Üres helyek aránya: %.1f%%\n" (unfilledSlotCount / 81.0 * 100.0)
printfn "5. Feladat:"

numbersPerLine.[9 .. numbersPerLine.Length - 1]
|> Seq.iter(fun k -> printfn "Sor: %d, oszlop: %d, érték: %d: %s" k.[1] k.[2] k.[0] (getStepAttemptResultMessage(k.[0], k.[1] - 1, k.[2] - 1, gameState)))