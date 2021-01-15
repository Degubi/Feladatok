open System
open System.IO

let dijatKeszit(data: string[]) = {|
    Evszam = int data.[0]
    Tipus = data.[1]
    KeresztNev = data.[2]
    VezetekNev = if data.Length = 4 then data.[3] else ""
|}

let dijak = File.ReadAllLines "nobel.csv" |> Seq.skip(1)
                                          |> Seq.map(fun k -> k.Split ';' |> dijatKeszit)
                                          |> Seq.toArray

dijak |> Seq.filter(fun k -> k.KeresztNev = "Arthur B." && k.VezetekNev = "McDonald")
      |> Seq.head
      |> fun k -> printfn "3. Feladat: Arthur %s díjat kapott" k.Tipus

printfn "4. Feladat"

dijak |> Seq.filter(fun k -> k.Evszam = 2017 && k.Tipus = "irodalmi")
      |> Seq.iter(fun k -> printfn "Irodalmi díjat kapott: %s %s" k.KeresztNev k.VezetekNev)

printfn "5. Feladat"

dijak |> Seq.filter(fun k -> k.VezetekNev = "" && k.Evszam >= 1990)
      |> Seq.iter(fun k -> printfn "%d: %s" k.Evszam k.KeresztNev)

printfn "6. Feladat"

dijak |> Seq.filter(fun k -> k.VezetekNev.Contains "Curie")
      |> Seq.iter(fun k -> printfn "%d: %s %s: %s" k.Evszam k.KeresztNev k.VezetekNev k.Tipus)

printfn "7. Feladat"

dijak |> Seq.groupBy(fun k -> k.Tipus)
      |> Seq.iter(fun (k, e) -> printfn "%s: %d db" k (e |> Seq.length))

dijak |> Seq.filter(fun k -> k.Tipus = "orvosi")
      |> Seq.sortBy(fun k -> k.Evszam)
      |> Seq.map(fun k -> string k.Evszam + ":" + k.KeresztNev + " " + k.VezetekNev)
      |> Seq.toArray
      |> fun k -> File.WriteAllLines("orvosi.txt", k)