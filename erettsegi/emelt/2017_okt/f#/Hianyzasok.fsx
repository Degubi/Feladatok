open System
open System.IO

let napnev = [| "vasarnap"; "hetfo"; "kedd"; "szerda"; "csutortok"; "pentek"; "szombat" |]
let napszam = [| 0; 31; 59; 90; 120; 151; 181; 212; 243; 273; 304; 335 |]
let hetnapja(honap: int) (nap: int) = napnev.[(napszam.[honap - 1] + nap) % 7]

let collectHianyzasokBetweenDays(splitLines: string[][]) (napSorIndexek: int[]) (napSorIndexIndex: int) =
    let napKezdetIndex = napSorIndexek.[napSorIndexIndex]
    let nextNapSorIndex = napSorIndexIndex + 1
    let napZaroIndex = if nextNapSorIndex < napSorIndexek.Length then napSorIndexek.[napSorIndexIndex + 1] else splitLines.Length
    let napAdat = splitLines.[napKezdetIndex]
    let honap = int napAdat.[1]
    let nap = int napAdat.[2]

    seq { napKezdetIndex + 1 .. napZaroIndex - 1 }
    |> Seq.map(fun k -> splitLines.[k])
    |> Seq.map(fun k -> {|
        nev = k.[0] + " " + k.[1]
        orak = k.[2]
        honap = honap
        nap = nap
    |})


let splitLines = "naplo.txt" |> File.ReadLines
                             |> Seq.map(fun k -> k.Split ' ')
                             |> Seq.toArray

let napSorIndexek = seq { 0 .. splitLines.Length - 1 }
                    |> Seq.filter(fun i -> splitLines.[i].[0] = "#")
                    |> Seq.toArray

let hianyzasok = seq { 0 .. napSorIndexek.Length - 1 }
                 |> Seq.collect(fun i -> collectHianyzasokBetweenDays splitLines napSorIndexek i)
                 |> Seq.toArray

printfn "2. Feladat: Hiányzások száma: %d" hianyzasok.Length

let igazoltak = hianyzasok |> Seq.map(fun k -> k.orak)
                           |> Seq.map(fun k -> k |> Seq.filter(fun l -> l = 'X') |> Seq.length)
                           |> Seq.sum

let igazolatlanok = hianyzasok |> Seq.map(fun k -> k.orak)
                               |> Seq.map(fun k -> k |> Seq.filter(fun l -> l = 'I') |> Seq.length)
                               |> Seq.sum

printfn "3. Feladat: Igazolt hiányzások: %d, igazolatlanok: %d" igazoltak igazolatlanok
printfn "5. Feladat: Írjon be egy hónapot és egy napot"

let beHonap = Console.ReadLine() |> int
let beNap = Console.ReadLine() |> int

printfn "Azon a napon: %s volt" (hetnapja beHonap beNap)
printfn "6. Feladat: Írja be 1 nap nevét és 1 óraszámot"

let beTanNap = Console.ReadLine()
let beOraszam = (Console.ReadLine() |> int) - 1

hianyzasok |> Seq.filter(fun k -> beTanNap = hetnapja k.honap k.nap)
           |> Seq.map(fun k -> k.orak.[beOraszam])
           |> Seq.filter(fun k -> k = 'X' || k = 'I')
           |> Seq.length
           |> printfn "Ekkor %d-an hiányoztak"

printfn "7. Feladat: "

let orakatSzamol(adatok: seq<{| honap: int; nap: int; nev: string; orak: string |}>) =
    adatok |> Seq.map(fun k -> k.orak |> Seq.filter(fun l -> l = 'X' || l = 'I') |> Seq.length)
           |> Seq.sum

let hianyzasMap = hianyzasok |> Seq.groupBy(fun k -> k.nev)
                             |> Seq.map(fun (nev, adatok) -> (nev, orakatSzamol adatok))
                             |> dict

let legtobbHianyzas = hianyzasMap.Values |> Seq.max

hianyzasMap |> Seq.filter(fun k -> k.Value = legtobbHianyzas)
            |> Seq.iter(fun k -> printf "%s " k.Key)