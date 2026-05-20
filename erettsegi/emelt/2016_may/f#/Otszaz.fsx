open System
open System.IO

let lines = File.ReadAllLines "penztar.txt"
let vasarlasKezdoZaroIndexek = seq { -1 .. lines.Length - 1 }
                              |> Seq.filter(fun i -> i = -1 || lines.[i] = "F")
                              |> Seq.toArray

let vasarlasok = seq { 0 .. vasarlasKezdoZaroIndexek.Length - 2 }
                |> Seq.map(fun i -> lines.[vasarlasKezdoZaroIndexek.[i] + 1 .. vasarlasKezdoZaroIndexek.[i + 1] - 1])
                |> Seq.map(fun k -> k |> Seq.groupBy(fun n -> n)
                                      |> Seq.map(fun (n, ns) -> (n, ns |> Seq.length))
                                      |> Map)
                |> Seq.toArray

printfn "2. Feladat: Vásárlások száma: %d" vasarlasok.Length
printfn "3. Feladat: Elsö vásárlásnál vett dolgok száma: %d" vasarlasok.[0].Count
printfn "4. Feladat:"

printfn "Írj be 1 sorszámot"
let bekertSorszam = Console.ReadLine() |> int

printfn "Írj be 1 árut"
let bekertAru = Console.ReadLine()

printfn "Írj be 1 mennyiséget"
let bekertDBszam = Console.ReadLine() |> int

printfn "5. Feladat:"

seq { 0 .. vasarlasok.Length - 1 }
|> Seq.find(fun i -> vasarlasok.[i].ContainsKey bekertAru)
|> fun i -> printfn "Először a %d. vásárlásnál vettek %s-t" (i + 1) bekertAru

seq { vasarlasok.Length - 1 .. -1 .. 0 }
|> Seq.find(fun i -> vasarlasok.[i].ContainsKey bekertAru)
|> fun i -> printfn "Utoljára a %d. vásárlásnál vettek %s-t" (i + 1) bekertAru

let aruDarab db = match db with
                  | Some (dbSzam) -> dbSzam
                  | None -> 0

vasarlasok |> Seq.sumBy(fun k -> k.TryFind bekertAru |> aruDarab)
           |> fun k -> printfn "Összesen %d-szor vettek %s-t" k bekertAru

let ertek dbSzam = if dbSzam = 1 then 500 elif dbSzam = 2 then 950 else 1350 + (400 * (dbSzam - 3))

printfn "6. Feladat: %d db esetén a fizetendő: %d" bekertDBszam (ertek bekertDBszam)
printfn "7. Feladat: A %d. vásárláskor vásárolt dolgok: " bekertSorszam

vasarlasok.[bekertSorszam - 1] |> Seq.iter(fun (KeyValue(item, db)) -> printfn "%s-ből: %d db" item db)

seq { 0 .. vasarlasok.Length - 1 }
|> Seq.map(fun i -> $"{i + 1}: {vasarlasok.[i].Values |> Seq.sumBy(ertek)}")
|> fun k -> File.WriteAllLines("osszeg.txt", k)