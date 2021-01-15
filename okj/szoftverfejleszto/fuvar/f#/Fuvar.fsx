open System
open System.IO

let fuvarAdatotKeszit(data: string[]) = {|
    Azonosito = int data.[0]
    Indulas = data.[1].Replace(" ", "T") |> DateTime.Parse
    Idotartam = int data.[2]
    Tavolsag = data.[3].Replace(",", ".") |> float
    Dij = data.[4].Replace(",", ".") |> float
    Borravalo = data.[5].Replace(",", ".") |> float
    FizetesMod = data.[6]
|}

let fuvarok = File.ReadLines "fuvar.csv" |> Seq.skip(1)
                                         |> Seq.map(fun k -> k.Split ';' |> fuvarAdatotKeszit)
                                         |> Seq.toArray

printfn "3. Feladat: Fuvarok száma: %d" fuvarok.Length

let szurt = fuvarok |> Seq.filter(fun k -> k.Azonosito = 6185)
                    |> Seq.toArray

printfn "4. Feladat: %d db fuvarra: %.2f$" szurt.Length (szurt |> Seq.sumBy(fun k -> k.Dij))
printfn "5. Feladat"

fuvarok |> Seq.groupBy(fun k -> k.FizetesMod)
        |> Seq.iter(fun (k, e) -> printfn "%s: %d db" k (e |> Seq.length))

printfn "6. Feladat: %.2f km" (1.6 * (fuvarok |> Seq.sumBy(fun k -> k.Tavolsag)))

fuvarok |> Seq.maxBy(fun k -> k.Idotartam)
        |> fun k -> printfn "7. Feladat: %d mp, azonosito: %d, távolság: %.2f km, díj: %.2f$\n", k.Idotartam, k.Azonosito, k.Tavolsag, k.Dij

let header = "taxi_id;indulas;idotartam;tavolsag;viteldij;borravalo;fizetes_modja\n";

fuvarok |> Seq.filter(fun k -> k.Idotartam > 0 && k.Dij > 0. && k.Tavolsag = 0.)
        |> Seq.sortBy(fun k -> k.Indulas)
        |> Seq.map(fun k -> (string k.Azonosito) + ";" + (string k.Indulas) + ";" + (string k.Idotartam) + ";" +
                            (string k.Tavolsag) + ";" + (string k.Dij) + ";" + (string k.Borravalo) + ";" + k.FizetesMod)
        |> Seq.toArray
        |> fun k -> String.Join("\n", k)
        |> fun k -> File.WriteAllText("hibak.txt", header + k)