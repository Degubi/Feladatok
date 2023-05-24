open System
open System.IO

type Color = { red: int; green: int; blue: int }
let colorSum color = color.red + color.green + color.blue

let hatar sorSzam elteres (pixelek2D: Color[][]) =
    let sor = pixelek2D.[sorSzam]

    seq { 0 .. sor.Length - 2 } |> Seq.exists(fun i -> Math.Abs(sor.[i].blue - sor.[i + 1].blue) > elteres)

let kepSortBeolvas(line: string) =
    let split = line.Split(' ')

    seq { 0 .. split.Length - 1 }
    |> Seq.filter(fun i -> i % 3 = 0)
    |> Seq.map(fun i -> { red = int split.[i]; green = int split.[i + 1]; blue = int split.[i + 2] })
    |> Seq.toArray


let pixelek2D = "kep.txt" |> File.ReadLines |> Seq.map kepSortBeolvas |> Seq.toArray

printfn "2. Feladat: Kérem egy képpont adatait!"
printfn "Sor:"

let bekertSorIndex = int(Console.ReadLine()) - 1

printfn "Oszlop:"

let bekertOszlopIndex = int(Console.ReadLine()) - 1

printfn "Képpont színe:\n%O" (pixelek2D.[bekertSorIndex].[bekertOszlopIndex])

let pixelek1D = pixelek2D |> Seq.collect id |> Seq.toArray

let vilagosKeppontokSzama = pixelek1D |> Seq.filter(fun k -> colorSum k > 600) |> Seq.length
let legsotetebbKeppontErtek = pixelek1D |> Seq.map colorSum |> Seq.min

printfn "3. Feladat: Világos képpontok száma: %d" vilagosKeppontokSzama
printfn "4. Feladat: Legsötétebb pont RGB összege: %d" legsotetebbKeppontErtek

pixelek1D |> Seq.filter(fun k -> colorSum k = legsotetebbKeppontErtek)
          |> Seq.iter(fun k -> printfn "%O" k)

let hatarOszlopIndexek = seq { 0 .. pixelek2D.Length - 1 }
                        |> Seq.filter(fun i -> hatar i 10 pixelek2D)
                        |> Seq.toArray

printfn "6. Feladat: Felhő legfelső sora: %d, utolsó sora: %d" (hatarOszlopIndexek[0] + 1) (hatarOszlopIndexek[hatarOszlopIndexek.Length - 1] + 1)