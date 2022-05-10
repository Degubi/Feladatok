open System
open System.IO

let URESRAJTSZAM = -1

let versenyzotKeszit(data: string[]) = {|
    Nev = data.[0]
    SzuletesiDatum = DateTime.Parse(data.[1])
    Nemzetiseg = data.[2]
    Rajtszam = if data.[3] = "" then URESRAJTSZAM else int data.[3]
|}

let pilotak = "pilotak.csv" |> File.ReadLines
                            |> Seq.skip 1
                            |> Seq.map(fun k -> k.Split ';' |> versenyzotKeszit)
                            |> Seq.toArray

printfn "3. Feladat: Adatsorok száma: %d" pilotak.Length
printfn "4. Feladat: Utolsó pilóta neve: %s" pilotak.[pilotak.Length - 1].Nev
printfn "5. Feladat:"

pilotak |> Seq.filter(fun k -> k.SzuletesiDatum.Year >= 1800 && k.SzuletesiDatum.Year <= 1900)
        |> Seq.iter(fun k -> printfn "    %s (%d)" k.Nev k.SzuletesiDatum.Year)

pilotak |> Seq.filter(fun k -> k.Rajtszam <> URESRAJTSZAM)
        |> Seq.minBy(fun k -> k.Rajtszam)
        |> fun k -> printfn "6. Feladat: %s" k.Nemzetiseg

printfn "7. Feladat:"

pilotak |> Seq.filter(fun k -> k.Rajtszam <> URESRAJTSZAM)
        |> Seq.countBy(fun k -> k.Rajtszam)
        |> Seq.filter(fun (_, dbSzam) -> dbSzam > 1)
        |> Seq.iter(fun (rajtszam, _) -> printf "%d " rajtszam)