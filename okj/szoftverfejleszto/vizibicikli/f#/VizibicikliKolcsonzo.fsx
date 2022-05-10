open System
open System.IO

let kolcsonzestKeszit(data: string[]) = {|
    nev = data.[0]
    jarmuAzonosito = data.[1]
    elvitelIdopont = TimeSpan(int data.[2], int data.[3], 0)
    visszahozatalIdopont = TimeSpan(int data.[4], int data.[5], 0)
|}

let kolcsonzesek = "kolcsonzesek.txt" |> File.ReadLines
                                      |> Seq.skip 1
                                      |> Seq.map(fun k -> k.Split ';' |> kolcsonzestKeszit)
                                      |> Seq.toArray

printfn "5. Feladat: Kölcsönzések száma: %d" kolcsonzesek.Length
printfn "Írj be 1 nevet!"

let bekertNev = Console.ReadLine()
let bekertNevhezTartozoKolcsonzesek = kolcsonzesek |> Seq.filter(fun k -> k.nev = bekertNev)
                                                   |> Seq.toArray

let bekerthezKiirando = if bekertNevhezTartozoKolcsonzesek.Length = 0
                        then "Nem volt ilyen nevű kölcsönző"
                        else bekertNevhezTartozoKolcsonzesek |> Seq.map(fun k -> $"{k.elvitelIdopont}-{k.visszahozatalIdopont}")
                                                             |> fun k -> String.Join('\n', k)
printfn "%s" bekerthezKiirando
printfn "Adj meg 1 időpontot! (óra:perc)"

let bekertIdopontParts = Console.ReadLine().Split ':'
let bekertIdopont = TimeSpan(int bekertIdopontParts.[0], int bekertIdopontParts.[1], 0)

printfn "7. Feladat:"

kolcsonzesek |> Seq.filter(fun k -> bekertIdopont > k.elvitelIdopont && bekertIdopont < k.visszahozatalIdopont)
             |> Seq.map(fun k -> $"    {k.elvitelIdopont}-{k.visszahozatalIdopont}: {k.nev}")
             |> Seq.iter(Console.WriteLine)

kolcsonzesek |> Seq.map(fun k -> (k.visszahozatalIdopont - k.elvitelIdopont).TotalMinutes)
             |> Seq.map(fun k -> int(Math.Ceiling(k / 30.0)))
             |> Seq.sum
             |> fun k -> printfn "8. Feladat: A bevétel %d Ft" (k * 2400)

kolcsonzesek |> Seq.filter(fun k -> k.jarmuAzonosito = "F")
             |> Seq.map(fun k -> $"{k.elvitelIdopont}-{k.visszahozatalIdopont}: {k.nev}")
             |> fun k -> File.WriteAllLines("F.txt", k)

printfn "10. Feladat:"

kolcsonzesek |> Seq.groupBy(fun k -> k.jarmuAzonosito)
             |> Seq.map(fun (azonosito, kolcsonzesek) -> (azonosito, kolcsonzesek |> Seq.length))
             |> Seq.sortBy(fun (azonosito, _) -> azonosito)
             |> Seq.iter(fun (azonosito, dbSzam) -> printfn $"    {azonosito} - {dbSzam}")