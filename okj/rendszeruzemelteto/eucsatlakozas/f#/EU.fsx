open System
open System.IO

let csatlakozasok = File.ReadLines("EUcsatlakozas.txt", System.Text.Encoding.GetEncoding("ISO-8859-1"))
                    |> Seq.map(fun k -> k.Split ';')
                    |> Seq.map(fun m -> {| Orszag = m.[0]; Datum = DateTime.Parse(m.[1]) |})
                    |> Seq.toArray

printfn "3. Feladat: 2018-ig csatlakozott országok száma: %d" csatlakozasok.Length

csatlakozasok |> Seq.filter(fun k -> k.Datum.Year = 2007)
              |> Seq.length
              |> fun k -> printfn "4. Feladat: 2007-ben csatlakozott országok száma: %d" k

csatlakozasok |> Seq.find(fun k -> k.Orszag = "Magyarország")
              |> fun k -> printfn "5. Feladat: Magyarország csatlakozása: %O" k.Datum

csatlakozasok |> Seq.exists(fun k -> k.Datum.Month = 5)
              |> fun k -> printfn "6. Feladat: %s májusban csatlakozás" (if k then "Volt" else "Nem volt")

csatlakozasok |> Seq.maxBy(fun k -> k.Datum)
              |> fun k -> printfn "7. Feladat: Utoljára csatlakozott: %s" k.Orszag

printfn "8. Feladat:"

csatlakozasok |> Seq.countBy(fun k -> k.Datum.Year)
              |> Seq.iter(fun (ev, db) -> printfn "%d - %d db ország" ev db)