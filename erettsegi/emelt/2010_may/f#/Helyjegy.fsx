open System
open System.IO

type Utas = {
    sorszam: int
    ules: int
    felszallasKm: int
    leszallasKm: int
}

let createUtas(sorszam: int) (data: string[]) = {
    sorszam = sorszam
    ules = int data.[0]
    felszallasKm = int data.[1]
    leszallasKm = int data.[2]
}

let getAr(arPer10km: int) (utas: Utas) =
    let tav = utas.leszallasKm - utas.felszallasKm
    let utolsoSzamjegy = tav % 10
    let tizesek = tav / 10 + (if utolsoSzamjegy = 3 || utolsoSzamjegy = 4 || utolsoSzamjegy = 8 || utolsoSzamjegy = 9 then 1 else 0)

    arPer10km * tizesek

let getUlesStatus(bekertKm: int) (utasok: Utas[]) (ules: int) =
    let utasUlesen = utasok |> Seq.tryFind(fun k -> k.ules = ules && (k.felszallasKm = bekertKm || k.leszallasKm = bekertKm))

    string(ules) + ". ülés: " + (if utasUlesen.IsNone then "üres" else (string(utasUlesen.Value.sorszam) + ". utas"))


let lines = File.ReadAllLines "eladott.txt"
let firstLineSplit = lines.[0].Split ' '

let vonalHossz = int firstLineSplit.[1]
let arPer10Km = int firstLineSplit.[2]
let utasok = seq { 0 .. lines.Length - 1 }
             |> Seq.map(fun i -> lines.[i].Split ' ' |> createUtas i)
             |> Seq.toArray

let utolso = utasok.[utasok.Length - 1]

printfn "2. Feladat: Utolsó utas ülése: %d utazott távolság: %d" utolso.ules (utolso.leszallasKm - utolso.felszallasKm)
printfn "3.Feladat:"

utasok |> Seq.filter(fun k -> (k.leszallasKm - k.felszallasKm) = vonalHossz)
       |> Seq.iter(fun k -> printf "%d " k.sorszam)

utasok |> Seq.sumBy(getAr arPer10Km)
       |> printfn "\n4. Feladat: Összes bevétel: %d"

let utolsoElottiMegalloKm = utasok |> Seq.map(fun k -> k.leszallasKm)
                                   |> Seq.filter(fun k -> k <> vonalHossz)
                                   |> Seq.distinct
                                   |> Seq.max

let utoljaraFelszallok = utasok |> Seq.filter(fun k -> k.felszallasKm = utolsoElottiMegalloKm) |> Seq.length
let utoljaraLeszallok  = utasok |> Seq.filter(fun k -> k.leszallasKm = utolsoElottiMegalloKm) |> Seq.length

printfn "5.Feladat: Utolsó megállónál felszállók: %d, leszállók: %d" utoljaraFelszallok utoljaraLeszallok

let megallokSzama = Seq.concat [ (utasok |> Seq.map(fun k -> k.leszallasKm)); (utasok |> Seq.map(fun k -> k.felszallasKm)) ]
                 |> Seq.distinct
                 |> Seq.length

printfn "6.Feladat: Megállók száma: %d" (megallokSzama - 2)
printfn "Írj be 1 km számot!"

let bekertKm = Console.ReadLine() |> int

seq { 1 .. 48 }
|> Seq.map(getUlesStatus bekertKm utasok)
|> fun k -> File.WriteAllLines("kihol.txt", k)