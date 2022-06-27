open System
open System.IO

let hianyzastKeszit(data: string[]) = {|
    nev = data.[0]
    osztaly = data.[1]
    elsoNap = int(data.[2])
    utolsoNap = int(data.[3])
    mulasztottOrak = int(data.[4])
|}

let hianyzasok = "szeptember.csv" |> File.ReadLines
                                  |> Seq.skip 1
                                  |> Seq.map(fun k -> k.Split ";" |> hianyzastKeszit)
                                  |> Seq.toArray

hianyzasok |> Seq.sumBy(fun k -> k.mulasztottOrak)
           |> fun k -> printfn "2. Feladat: Hianyzott orak: %d" k

printfn "3. Feladat: Írj be egy napot(1-30) és egy nevet!"

let bekertNap = Console.ReadLine() |> int
let bekertNev = Console.ReadLine()

hianyzasok |> Seq.exists(fun k -> k.nev = bekertNev)
           |> function
               | true  -> printfn "4. Feladat: %s hiányzott" bekertNev
               | false -> printfn "4. Feladat: %s nem hiányzott" bekertNev

printfn "5. Feladat"

let azonANaponHianyoztak = hianyzasok |> Seq.filter(fun k -> bekertNap >= k.elsoNap && bekertNap <= k.utolsoNap)
                                      |> Seq.toArray

match azonANaponHianyoztak.Length with
    | 0 -> printfn "Nem volt hiányzó"
    | _ -> azonANaponHianyoztak |> Seq.iter(fun k -> printfn "%s %s" k.nev k.osztaly)

hianyzasok |> Seq.groupBy(fun k -> k.osztaly)
           |> Seq.map(fun (osztaly, statok) -> $"{osztaly};{statok |> Seq.sumBy(fun k -> k.mulasztottOrak)}")
           |> fun k -> File.WriteAllLines("osszesites.csv", k)