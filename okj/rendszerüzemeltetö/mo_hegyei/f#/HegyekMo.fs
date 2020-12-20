open System
open System.IO

let hegyetKeszit (k: string[]) = {| Nev = k.[0]; Hegyseg = k.[1]; Magassag = float k.[2] |}
let hegyek = File.ReadLines("hegyekMo.txt") |> Seq.skip(1)
                                            |> Seq.map(fun k -> k.Split(';') |> hegyetKeszit)
                                            |> Seq.toArray

printfn "3. Feladat: Hegyek száma: %d db" hegyek.Length

hegyek |> Seq.averageBy(fun k -> k.Magassag)
       |> printfn "4. Feladat: Átlagmagasság: %.2f m"

hegyek |> Seq.maxBy(fun k -> k.Magassag)
       |> fun k -> printfn "5. Feladat: Legmagasabb hegy: %s-ben a %s, magassága: %.0f m" k.Hegyseg k.Nev k.Magassag

printfn "6. Feladat: Írj be egy magasságot!"
let beMagassag = Console.ReadLine() |> float

hegyek |> Seq.exists(fun k -> k.Hegyseg = "Börzsöny" && k.Magassag > beMagassag)
       |> fun k -> printfn "%s magasabb hegység ennél a Börzsönyben" (if k then "Van" else "Nincs")

let konvertaltLab3000 = 3000.0 / 3.280839895;

hegyek |> Seq.filter(fun k -> k.Magassag > konvertaltLab3000)
       |> Seq.sumBy(fun _ -> 1)
       |> printfn "7. Feladat: 3000 lábnál magasabbak száma: %d"

printfn "8. Feladat: Hegység stat"

hegyek |> Seq.map(fun k -> k.Hegyseg)
       |> Seq.countBy(id)
       |> Seq.iter(fun (k, l) -> printfn "    %s: %d" k l)

let fileAdat = hegyek |> Seq.filter(fun k -> k.Hegyseg = "Bükk-vidék")
                      |> Seq.map(fun k -> sprintf "%s;%.2f" k.Nev (k.Magassag * 3.280839895) |> fun k -> k.Replace(",", "."))
                      |> Seq.toArray

File.WriteAllText("bukk-videk.txt", "Hegycsúcs neve;Magasság láb\n" + String.Join("\n", fileAdat))