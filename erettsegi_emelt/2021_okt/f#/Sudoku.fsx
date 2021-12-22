open System
open System.IO

let getStepAttemptResultMessage(value: int, rowIndex: int, columnIndex: int, gameState: int[][]) =
    match gameState with
    | _ when gameState.[rowIndex].[columnIndex] <> 0 -> "A helyet már kitöltötték"
    | _ when gameState.[rowIndex] |> Seq.exists(fun k -> k = value) -> "Az adott sorban már szerepel a szám"
    | _ when gameState |> Seq.exists(fun k -> k.[columnIndex] = value) -> "Az adott oszlopban már szerepel a szám"
    | _ when
        let beginRow = (rowIndex / 3) * 3
        let endRow = beginRow + 3
        let beginColumn = (columnIndex / 3) * 3
        let endColumn = beginColumn + 3

        gameState.[beginRow .. endRow - 1] |> Seq.exists(fun k -> k.[beginColumn .. endColumn - 1] |> Seq.exists(fun m -> m = value)) -> "Az adott résztáblában már szerepel a szám"
    | _ -> "A lépés megtehető"

printfn "1. Feladat: Írd be 1 bemeneti fájl nevét, 1 sor és 1 oszlop számát!"

let inputFileName = Console.ReadLine()
let inputRowIndex = int(Console.ReadLine()) - 1
let inputColumnIndex = int(Console.ReadLine()) - 1

let numbersPerLine = File.ReadLines(inputFileName) |> Seq.map(fun k -> k.Split(" ") |> Seq.map(int) |> Seq.toArray) |> Seq.toArray
let gameState = numbersPerLine.[0 .. 8]

let valueAtInputSlot = gameState.[inputRowIndex].[inputColumnIndex]
let tableNumber = 3 * (inputRowIndex / 3) + (inputColumnIndex / 3) + 1
let valueToPrint = if valueAtInputSlot = 0 then "Az adott helyet még nem töltötték ki!" else ($"Adott helyen szereplő szám: {valueAtInputSlot}")

printfn $"3. Feladat: {valueToPrint}, résztáblázat száma: {tableNumber}"

let unfilledSlotCount = gameState |> Seq.concat |> Seq.filter(fun k -> k = 0) |> Seq.length |> float

printfn "4. Feladat: Üres helyek aránya: %.1f%%\n" (unfilledSlotCount / 81.0 * 100.0)
printfn "5. Feladat:"

numbersPerLine.[9 .. numbersPerLine.Length - 1]
|> Seq.map(fun k -> $"Sor: {k.[1]}, oszlop: {k.[2]}, érték: {k.[0]}: " + getStepAttemptResultMessage(k.[0], k.[1] - 1, k.[2] - 1, gameState))
|> Seq.iter(Console.WriteLine)