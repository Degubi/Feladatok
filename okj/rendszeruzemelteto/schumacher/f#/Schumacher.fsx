open System
open System.IO

let eredmenytKeszit (split: string[]) = {|
    datum = DateTime.Parse split.[0]
    dijNev = split.[1]
    helyezes = int split.[2]
    befejezettKorok = int split.[3]
    szerzettPontok = int split.[4]
    csapat = split.[5]
    vegeredmenyStatusz = split.[6]
|}


let eredmenyek = "schumacher.csv" |> File.ReadLines
                                  |> Seq.skip 1
                                  |> Seq.map(fun k -> k.Split ';' |> eredmenytKeszit)
                                  |> Seq.toArray

printfn "3. Feladat: Adatsorok száma: %d" eredmenyek.Length
printfn "4. Feladat:"

eredmenyek |> Seq.filter(fun k -> k.dijNev = "Hungarian Grand Prix")
           |> Seq.filter(fun k -> k.helyezes <> 0)
           |> Seq.iter(fun k -> printfn "    %s: %d. hely" (k.datum.ToShortDateString()) k.helyezes)

printfn "5. Feladat:"

eredmenyek |> Seq.filter(fun k -> k.helyezes = 0)
           |> Seq.groupBy(fun k -> k.vegeredmenyStatusz)
           |> Seq.map(fun (status, stat) -> (status, stat |> Seq.length))
           |> dict
           |> Seq.filter(fun k -> k.Value > 2)
           |> Seq.iter(fun k -> printfn "    %s: %d" k.Key k.Value)