open System
open System.IO

let mozgasok = File.ReadLines "bedat.txt"
             |> Seq.map(fun k -> k.Split ' ')
             |> Seq.map(fun k -> {| Azonosito = k[0]; Idopont = TimeOnly.Parse k[1]; Tipus = int k[2] |})
             |> Seq.toArray

printfn $"2. Feladat: Első belépő: {mozgasok[0].Idopont}, utolsó kilépő: {mozgasok[mozgasok.Length - 1].Idopont}"

let kesoKezdet = TimeOnly(7, 50)
let kesoZaras = TimeOnly(8, 15)

mozgasok |> Seq.filter(fun k -> k.Idopont > kesoKezdet && k.Idopont < kesoZaras)
         |> Seq.map(fun k -> $"{k.Idopont} {k.Azonosito}")
         |> fun k -> File.WriteAllLines("kesok.txt", k)

let menzasokSzama = mozgasok |> Seq.filter(fun k -> k.Tipus = 3)
                             |> Seq.length

printfn "4. Feladat: Menzások száma: %d" menzasokSzama

mozgasok |> Seq.filter(fun k -> k.Tipus = 4)
         |> Seq.distinctBy(fun k -> k.Azonosito)
         |> Seq.length
         |> fun kolcsonzesekSzama -> printfn "5. Feladat: Könyvtári kölcsönzések száma: %d.\n%s, mint a menzán" kolcsonzesekSzama (if kolcsonzesekSzama > menzasokSzama then "Többen voltak" else "Nem voltak többen")

let szunetKezdete = TimeOnly(10, 45)
let kapuZaras = TimeOnly(10, 50)
let szunetVege = TimeOnly(11, 0)

let szunetbeIsmertKilepok = mozgasok |> Seq.filter(fun k -> k.Idopont > szunetKezdete && k.Idopont < szunetVege)
                                     |> Seq.filter(fun k -> k.Tipus = 2)
                                     |> Seq.map(fun k -> k.Azonosito)
                                     |> Seq.toArray

let szunetElottMarBelepok = mozgasok |> Seq.filter(fun k -> k.Idopont < szunetKezdete)
                                     |> Seq.map(fun k -> k.Azonosito)
                                     |> Seq.toArray

mozgasok |> Seq.filter(fun k -> k.Idopont > kapuZaras && k.Idopont < szunetVege)
         |> Seq.filter(fun k -> k.Tipus = 1)
         |> Seq.map(fun k -> k.Azonosito)
         |> Seq.filter(fun k -> szunetbeIsmertKilepok |> Seq.contains k |> not && szunetElottMarBelepok |> Seq.contains k)
         |> fun k -> String.Join(' ', k)
         |> fun k -> printfn "6. Feladat: %s" k

printfn "7. Feladat: Kérem egy tanuló azonosítóját!"

let bekertAzonosito = Console.ReadLine()
let bekertTanuloMozgasai = mozgasok |> Seq.filter(fun k -> k.Azonosito = bekertAzonosito)
                                    |> Seq.toArray

let elteltIdo = bekertTanuloMozgasai[bekertTanuloMozgasai.Length - 1].Idopont - bekertTanuloMozgasai[0].Idopont

printfn "Eltelt idő: %d óra %d perc" elteltIdo.Hours elteltIdo.Minutes