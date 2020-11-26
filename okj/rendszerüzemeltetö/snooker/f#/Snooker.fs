open System.IO

[<EntryPoint>]
let main _ =
    let versenyzotKeszit (k: string[]) = {| Helyezes = int k.[0]; Nev = k.[1]; Orszag = k.[2]; Nyeremeny = int k.[3] |}
    let versenyzok = File.ReadLines("snooker.txt") |> Seq.skip(1)
                                                   |> Seq.map(fun k -> k.Split(";") |> versenyzotKeszit)
                                                   |> Seq.toArray

    printfn "3. Feladat: Versenyzők száma: %d" versenyzok.Length

    versenyzok |> Seq.averageBy(fun k -> float k.Nyeremeny)
               |> printfn "4. Feladat: Átlag kereset: %.2f"

    versenyzok |> Seq.filter(fun k -> k.Orszag = "Kína")
               |> Seq.maxBy(fun k -> k.Nyeremeny)
               |> fun k -> printfn "5. Feladat: Legjobban kereső kínai versenyző:\n\tHelyezés: %d\n\tOrszág: Kína\n\tNyeremény: %d FT" k.Helyezes (k.Nyeremeny * 380)

    versenyzok |> Seq.exists(fun k -> k.Orszag = "Norvégia")
               |> fun k -> printfn "6. Feladat: %s norvég játékos" (if k then "Van" else "Nincs")

    printfn "7. Feladat:"

    versenyzok |> Seq.map(fun k -> k.Orszag)
               |> Seq.countBy(id)
               |> Seq.filter(fun (_, v) -> v > 4)
               |> Seq.iter(fun (k, v) -> printfn "%s: %d fő" k v)
    0