open System
open System.IO

let kutyatKeszit(data: string[]) = {|
    id = int data.[0]
    fajtaId = int data.[1]
    nevId = int data.[2]
    eletkor = int data.[3]
    ellenorzes = DateTime.ParseExact(data.[4], "yyyy.MM.dd", Globalization.CultureInfo.InvariantCulture);
|}


let nevLookup = "KutyaNevek.csv" |> File.ReadLines
                                 |> Seq.skip 1
                                 |> Seq.map(fun k -> k.Split ';' |> fun d -> (int(d.[0]), d.[1]))
                                 |> Map

// A KutyaFajtak.csv 388. sorában hibás adat van, ám az eredeti név nem kell sehol a feladatban, ezért azt el se tároljuk
let fajtaLookup = "KutyaFajtak.csv" |> File.ReadLines
                                    |> Seq.skip 1
                                    |> Seq.map(fun k -> k.Split ';' |> fun d -> (int(d.[0]), d.[1]))
                                    |> Map

let kutyik = "Kutyak.csv" |> File.ReadLines
                          |> Seq.skip 1
                          |> Seq.map(fun k -> k.Split ';' |> kutyatKeszit)
                          |> Seq.toArray

printfn "3. Feladat: Kutyanevek száma: %d" nevLookup.Count

kutyik |> Seq.averageBy(fun k -> float k.eletkor)
       |> printfn "6. Feladat: Átlag életkor: %.2f év"

kutyik |> Seq.maxBy(fun k -> k.eletkor)
       |> fun k -> printfn "7. Feladat: Legidősebb kutya neve + fajtája: %s: %s" nevLookup.[k.nevId] fajtaLookup.[k.fajtaId]

printfn "8. Feladat: 2018 január 10-én vizsgált kutyik:"

kutyik |> Seq.filter(fun k -> k.ellenorzes.Year = 2018 && k.ellenorzes.Month = 1 && k.ellenorzes.Day = 10)
       |> Seq.groupBy(fun k -> fajtaLookup.[k.fajtaId])
       |> Seq.iter(fun (fajta, kutyik) -> printfn "%s: %d db kutya" fajta (kutyik |> Seq.length))

kutyik |> Seq.groupBy(fun k -> k.ellenorzes)
       |> Seq.maxBy(fun (_, kutyik) -> kutyik |> Seq.length)
       |> fun (datum, kutyik) -> printfn "9. Feladat: Legjobban leterhelt nap: %O: %d db kutya" datum (kutyik |> Seq.length)

kutyik |> Seq.groupBy(fun k -> nevLookup.[k.nevId])
       |> Seq.map(fun (nev, kutyik) -> (nev, kutyik |> Seq.length))
       |> Seq.sortByDescending(fun (_, dbSzam) -> dbSzam)
       |> Seq.map(fun (nev, dbSzam) -> $"{nev};{dbSzam}")
       |> fun k -> File.WriteAllLines("nevstatisztika.txt", k)