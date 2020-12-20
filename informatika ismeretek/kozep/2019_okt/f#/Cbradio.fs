open System
open System.IO

let bejegyzestKeszit (k: string[]) = {| Ora = int k.[0]; Perc = int k.[1]; Adasok = int k.[2]; Nev = k.[3]|}
let bejegyzesek = File.ReadLines("cb.txt") |> Seq.skip(1)
                                           |> Seq.map(fun k -> k.Split(';') |> bejegyzestKeszit)
                                           |> Seq.toArray

printfn "3. Feladat: Bejegyzések száma: %d" bejegyzesek.Length

let voltEEloadas = if bejegyzesek |> Seq.exists(fun k -> k.Adasok = 4) then "4. Feladat: Volt 4 adást indító sofőr"
                                                                       else "4. Feladat: Nem volt 4 adást indító sofőr"
printfn "%s" voltEEloadas
printfn "5. Feladat: Írj be egy nevet!"

let bekertNev = Console.ReadLine()
let bekertHasznalatok = bejegyzesek |> Seq.filter(fun k -> k.Nev = bekertNev)
                                    |> Seq.sumBy(fun k -> k.Adasok)

if bekertHasznalatok > 0 then
    printfn "%s %dx használta a rádiót" bekertNev bekertHasznalatok
else
    printfn "Nincs ilyen nevű sofőr!"

let atszamolPercre (ora: int, perc: int) = ora * 60 + perc
let fileContent = bejegyzesek |> Seq.map(fun k -> string(atszamolPercre(k.Ora, k.Perc)) + ";" + k.Nev + ";" + string(k.Adasok))
                              |> Seq.toArray

File.WriteAllText("cb2.txt", "Kezdes;Nev;AdasDb\n" + String.Join("\n", fileContent))

bejegyzesek |> Seq.map(fun k -> k.Nev)
            |> Seq.distinct
            |> Seq.length
            |> printfn "8. Feladat: Sofőrök száma: %d"

bejegyzesek |> Seq.groupBy(fun k -> k.Nev)
            |> Seq.map(fun (k, v) -> {| Key = k; Value = v |> Seq.sumBy(fun m -> m.Adasok) |})
            |> Seq.maxBy(fun k -> k.Value)
            |> fun k -> printfn "9. Feladat: Legtöbb adás sofőre: %s: %d db" k.Key k.Value