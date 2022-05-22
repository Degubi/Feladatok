open System
open System.IO
open System.Globalization

type Utazas = { megalloSorszama: int; felszallas: DateTime; kartyaAzonosito: string; tipus: string; jegyszam: int; ervenyesseg: DateTime }
let utazastKeszit(args: string[]) = {
    megalloSorszama = int args.[0]
    felszallas = DateTime.ParseExact(args[1], "yyyyMMdd-HHmm", CultureInfo.InvariantCulture);
    kartyaAzonosito = args.[2]
    tipus = args.[3]
    jegyszam = if args.[4].Length <= 2 then int args.[4] else -1
    ervenyesseg = if args.[4].Length <= 2 then DateTime.MinValue else DateTime.ParseExact(args.[4], "yyyyMMdd", CultureInfo.InvariantCulture)
}

let utazasErvenytelenE utazas = utazas.jegyszam = 0 || (utazas.ervenyesseg <> DateTime.MinValue && utazas.felszallas > utazas.ervenyesseg)


let utazasok = "utasadat.txt" |> File.ReadLines
                              |> Seq.map(fun k -> k.Split ' ' |> utazastKeszit)
                              |> Seq.toArray

printfn $"2. Feladat: {utazasok.Length} db utas akart felszálni"

utazasok |> Seq.filter utazasErvenytelenE
         |> Seq.length
         |> fun k -> printfn $"3. Feladat: {k} utas nem szállhatott fel"

utazasok |> Seq.groupBy(fun k -> k.megalloSorszama)
         |> Seq.map(fun (megallo, utazasok) -> {| megallo = megallo; darabSzam = utazasok |> Seq.length |})
         |> Seq.maxBy(fun k -> k.darabSzam)
         |> fun k -> printfn $"4. Feladat: legtöbb utas ({k.darabSzam} fő) a {k.megallo}-as megállóban próbált felszállni"

let tipusStat = utazasok |> Seq.filter(fun k -> not(utazasErvenytelenE k))
                         |> Seq.groupBy(fun k -> k.tipus)
                         |> Seq.map(fun (tipus, utazasok) -> (tipus, utazasok |> Seq.length))
                         |> dict
printfn "5. Feladat"
printfn "Ingyenes utasok: %d %d %d" tipusStat.["NYP"] tipusStat.["RVS"] tipusStat.["GYK"]
printfn "Kedvezményes utasok: %d %d" tipusStat.["TAB"] tipusStat.["NYB"]

let kellEErtesites utazas =
    let napKulonbseg = (utazas.felszallas - utazas.ervenyesseg).Days;
    napKulonbseg >= 0 && napKulonbseg <= 3

utazasok |> Seq.filter kellEErtesites
         |> Seq.map(fun k -> $"{k.kartyaAzonosito} {k.ervenyesseg}")
         |> fun k -> File.WriteAllLines("figyelmeztetes.txt", k)