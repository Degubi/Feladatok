open System
open System.IO

let versenytKeszit(args: string[]) = {|
        Csapat = args.[0];
        Versenyzo = args.[1];
        Eletkor = int args.[2];
        Palya = args.[3];
        Korido = TimeSpan.Parse(args.[4]);
        Kor = int args.[5]
    |}

let versenyek = File.ReadLines("autoverseny.csv") |> Seq.skip(1)
                                                  |> Seq.map(fun k -> k.Split(';') |> versenytKeszit)
                                                  |> Seq.toArray

printfn "3. Feladat: Adatsorok száma: %d" versenyek.Length

versenyek |> Seq.find(fun k -> k.Versenyzo = "Fürge Ferenc" && k.Palya = "Gran Prix Circuit" && k.Kor = 3)
          |> fun k -> printfn "4. Feladat: %.0f mp" k.Korido.TotalSeconds

printfn "5. Feladat: Írj be egy nevet"
let bekertNev = Console.ReadLine()

printfn "6. Feladat:"

try
    versenyek |> Seq.filter(fun k -> k.Versenyzo = bekertNev)
              |> Seq.minBy(fun k -> k.Korido)
              |> fun k -> printfn "%s" (k.Korido.ToString())
with
    | :? ArgumentException -> printfn "Nincs ilyen versenyző"  // Ez elég ronda, de jobbat nem tudtam... :/