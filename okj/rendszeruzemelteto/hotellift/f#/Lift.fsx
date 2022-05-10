open System
open System.IO

let hasznalatotKeszit(data: string[]) = {|
    Idopont = DateTime.ParseExact(data.[0], "yyyy.MM.dd.", null);
    KartyaSorszam = int data.[1];
    InduloSzint = int data.[2];
    CelSzint = int data.[3]
|}

let parseIntOrDefaultToFive(input: string) =
    match Int32.TryParse input with
    | true, value -> value
    | _ -> 5

let hasznalatok = "lift.txt" |> File.ReadLines
                             |> Seq.map(fun k -> k.Split ' ' |> hasznalatotKeszit)
                             |> Seq.toArray

printfn "3. Feladat: Lift alkalmak száma: %d" hasznalatok.Length
printfn "4. Feladat: A korszak %s-től %s-ig tartott" (hasznalatok.[0].Idopont.ToString "yyyy-MM-dd")
                                                     (hasznalatok.[hasznalatok.Length - 1].Idopont.Date.ToString "yyyy-MM-dd")
hasznalatok |> Seq.maxBy(fun k -> k.CelSzint)
            |> fun k -> printfn "5. Feladat: Max célszint: %d" k.CelSzint

printfn "6. Feladat: Írj be egy kártyaszámot és egy célszintet"

let beKartya = Console.ReadLine() |> parseIntOrDefaultToFive
let beCelszint = Console.ReadLine() |> parseIntOrDefaultToFive

hasznalatok |> Seq.exists(fun k -> k.KartyaSorszam = beKartya && k.CelSzint = beCelszint)
            |> fun k -> printfn "7. Feldadat: A %d kártyával%s utaztak a %d. emeletre" beKartya (if k then "" else " nem") beCelszint

printfn "8. Feladat:"

hasznalatok |> Seq.countBy(fun k -> k.Idopont)
            |> Seq.iter(fun (idopont, db) -> printfn "%s - %dx" (idopont.ToString "yyyy-MM-dd") db)