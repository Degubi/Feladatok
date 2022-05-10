open System.IO

let versenyzotKeszit (k: string[]) = {|
    Helyezes = int k.[0]
    Nev = k.[1]
    Orszag = k.[2]
    Nyeremeny = int k.[3]
|}

let versenyzok = "snooker.txt" |> File.ReadLines
                               |> Seq.skip 1
                               |> Seq.map(fun k -> k.Split ';' |> versenyzotKeszit)
                               |> Seq.toArray

printfn "3. Feladat: Versenyzők száma: %d" versenyzok.Length

versenyzok |> Seq.averageBy(fun k -> float k.Nyeremeny)
           |> printfn "4. Feladat: Átlag kereset: %.2f"

versenyzok |> Seq.filter(fun k -> k.Orszag = "Kína")
           |> Seq.maxBy(fun k -> k.Nyeremeny)
           |> fun k -> printfn "5. Feladat: Legjobban kereső kínai versenyző:\n  Helyezés: %d\n  Név: %s\n  Nyeremény: %d FT" k.Helyezes k.Nev (k.Nyeremeny * 380)

versenyzok |> Seq.exists(fun k -> k.Orszag = "Norvégia")
           |> fun k -> printfn "6. Feladat: %s norvég játékos" (if k then "Van" else "Nincs")

printfn "7. Feladat:"

versenyzok |> Seq.countBy(fun k -> k.Orszag)
           |> Seq.filter(fun (_, count) -> count > 4)
           |> Seq.iter(fun (orszag, count) -> printfn "%s: %d fő" orszag count)