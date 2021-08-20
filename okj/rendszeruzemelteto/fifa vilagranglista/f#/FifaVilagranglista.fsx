open System.IO
open System.Text

let eredmenytKeszit(data: string[]) = {|
    Csapat = data.[0]
    Helyezes = int data.[1]
    Valtozas = int data.[2]
    Pontszam = int data.[3]
|}

let eredmenyek = File.ReadLines("fifa.txt", Encoding.Latin1) |> Seq.skip(1)
                                                             |> Seq.map(fun k -> k.Split(';') |> eredmenytKeszit)
                                                             |> Seq.toArray
printfn "3. Feladat: Csapatok száma: %d" eredmenyek.Length

eredmenyek |> Seq.averageBy(fun k -> float k.Pontszam)
           |> printfn "4. Feladat: Átlagpontszám: %.2f"

eredmenyek |> Seq.maxBy(fun k -> k.Valtozas)
           |> fun k -> printfn "5. Feladat: Legtöbbet javító csapat: %s, helyezés: %d, pontszám: %d" k.Csapat k.Helyezes k.Pontszam

eredmenyek |> Seq.exists(fun k -> k.Csapat = "Magyarország")
           |> fun k -> printfn "6. Feladat: Csapatok között %s Magyarország" (if k then "van" else "nincs")

printfn "7. Feladat"

eredmenyek |> Seq.groupBy(fun k -> k.Valtozas)
           |> Seq.map(fun (k, e) -> (k, e |> Seq.length))
           |> Seq.filter(fun (_, c) -> c > 1)
           |> Seq.iter(fun (k, c) -> printfn "    %d helyet változott: %d csapat" k c)