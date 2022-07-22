open System
open System.IO

let melysegek = "melyseg.txt" |> File.ReadLines
                              |> Seq.map(int)
                              |> Seq.toArray

printfn "1. Feladat: %d" melysegek.Length
printfn "2. Feladat: Írjon be egy távolságértéket!"

let bekertTavolsagIndex = int(Console.ReadLine()) - 1
let melysegABekertHelyen = melysegek.[bekertTavolsagIndex]

printfn "A felszín %dm mélyen van" melysegABekertHelyen

melysegek |> Seq.filter(fun k -> k = 0)
          |> Seq.length
          |> fun k -> printfn "3. Feladat: Érintetlen felszín: %.2f%%" (float(k) / float(melysegek.Length) * float(100))

let godorKezdoZaroIndexek = seq { 0 .. melysegek.Length - 2 }
                            |> Seq.filter(fun i -> (melysegek.[i] = 0 && melysegek.[i + 1] <> 0) || (melysegek.[i] <> 0 && melysegek.[i + 1] = 0))
                            |> Seq.map(fun i -> i + 1)
                            |> Seq.toArray

let godrok = seq { 0 .. 2 .. godorKezdoZaroIndexek.Length - 1 }
             |> Seq.map(fun i -> melysegek.[godorKezdoZaroIndexek.[i] .. godorKezdoZaroIndexek.[i + 1]])
             |> Seq.toArray

File.WriteAllLines("godrok.txt", godrok |> Seq.map(fun k -> String.Join(' ', k)))

if melysegABekertHelyen = 0 then
    printfn "6. Feladat: Az adott helyen nincs gödör"
else
    let bekertGodorIndex = seq { 0 .. 2 .. godorKezdoZaroIndexek.Length - 1 }
                           |> Seq.find(fun i -> bekertTavolsagIndex >= godorKezdoZaroIndexek.[i] && bekertTavolsagIndex <= godorKezdoZaroIndexek.[i + 1])
                           |> fun i -> i / 2

    let bekertHelyKezdoGodorTavolsag = godorKezdoZaroIndexek.[bekertGodorIndex] + 1
    let bekertHelyZaroGodorTavolsag = godorKezdoZaroIndexek.[bekertGodorIndex + 1]

    printfn "    a) Gödör kezdete: %dm, vége: %dm" bekertHelyKezdoGodorTavolsag bekertHelyZaroGodorTavolsag

    let aGodor = godrok.[bekertGodorIndex]
    let legmelyebbPontIndex = seq { 0 .. aGodor.Length - 1 } |> Seq.maxBy(fun i -> aGodor.[i])

    let balSzeltolLegnagyobbigNo = seq { 0 .. legmelyebbPontIndex - 2 } |> Seq.forall(fun i -> aGodor.[i] <= aGodor.[i + 1])
    let legnagyobbtolJobbSzeligCsokken = seq { legmelyebbPontIndex + 1 .. aGodor.Length - 2 } |> Seq.forall(fun i -> aGodor.[i] >= aGodor.[i + 1])

    printfn "    b) %s" (if balSzeltolLegnagyobbigNo && legnagyobbtolJobbSzeligCsokken then "Folyamatosan Mélyül" else "Nem mélyül folyamatosan")
    printfn "    c) Legnagyobb mélység: %dm" aGodor.[legmelyebbPontIndex]

    let terfogat = (aGodor |> Seq.sum) * 10
    let vizmennyiseg = terfogat - 10 * (bekertHelyZaroGodorTavolsag - bekertHelyKezdoGodorTavolsag + 1)

    printfn "    d) Térfogat: %dm^3" terfogat
    printfn "    e) Vízmennyiség: %dm^3" vizmennyiseg