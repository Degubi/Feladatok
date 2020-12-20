open System
open System.IO

let varostBeker(_) =
    printfn "Kérek 1 város nevet!"
    Console.ReadLine()


let helyszintKeszit (k: string[]) = {| Varos = k.[0]; Nev1 = k.[1]; Nev2 = k.[2]; Ferohely = int k.[3] |}
let helyszinek = File.ReadLines("vb2018.txt") |> Seq.skip(1)
                                              |> Seq.map(fun k -> k.Split(';') |> helyszintKeszit)
                                              |> Seq.toArray

printfn "3. Feladat: Stadionok száma: %d" helyszinek.Length

helyszinek |> Seq.minBy(fun k -> k.Ferohely)
           |> fun k -> printfn "4. Feladat: Város: %s, nev1: %s, nev2: %s, ferohelyek: %d" k.Varos k.Nev1 k.Nev2 k.Ferohely

helyszinek |> Seq.averageBy(fun k -> float k.Ferohely)
           |> printfn "5. Feladat: Férőhelyek átlaga: %.1f"

helyszinek |> Seq.filter(fun k -> k.Nev2 <> "n.a.")
           |> Seq.sumBy(fun _ -> 1)
           |> printfn "6. Feladat: Alternativ neves stadionok: %d"

printfn "7. Feladat:"

let olvasottNev = Seq.initInfinite(varostBeker) |> Seq.find(fun k -> k.Length >= 3)

helyszinek |> Seq.exists(fun k -> String.Equals(k.Varos, olvasottNev, StringComparison.CurrentCultureIgnoreCase))
           |> fun k -> printfn "8. Feladat: %s %s-ban mérkőzés" (if k then "Volt" else "Nem volt") olvasottNev

helyszinek |> Seq.map(fun k -> k.Varos)
           |> Seq.distinct
           |> Seq.sumBy(fun _ -> 1)
           |> printfn "9. Feladat: Városok száma: %d"