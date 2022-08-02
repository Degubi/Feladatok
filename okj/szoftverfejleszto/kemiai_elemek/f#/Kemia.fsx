open System
open System.IO

let OKOR_EV = -1
let createElem(data: string[]) = {|
    ev = if data.[0] = "Ókor" then OKOR_EV else int data.[0]
    nev = data.[1]
    vegyjel = data.[2]
    rendszam = int data.[3]
    felfedezo = data.[4]
|}

let vegyjeletBeker _ =
    printfn "Írj be egy vegyjelet! (1-2 karakter)"
    Console.ReadLine()


let elemek = File.ReadLines("felfedezesek.csv", System.Text.Encoding.Latin1)
            |> Seq.skip 1
            |> Seq.map(fun k -> k.Split ';' |> createElem)
            |> Seq.toArray

printfn "3. Feladat: Elemek száma: %d" elemek.Length

elemek |> Seq.filter(fun k -> k.ev = OKOR_EV)
       |> Seq.length
       |> printfn "4. Feladat: Ókori elemek száma: %d"

printfn "5. Feladat:"

let bekertVegyjel = Seq.initInfinite vegyjeletBeker
                   |> Seq.find(fun k -> (k.Length = 1 || k.Length = 2) && k |> Seq.forall Char.IsLetter)

printfn "6. Feladat:"

match elemek |> Seq.tryFind(fun k -> String.Equals(k.vegyjel, bekertVegyjel, StringComparison.OrdinalIgnoreCase)) with
| Some(elem) -> printfn "%s: %s, rsz.: %d, év: %d, felf.: %s" elem.vegyjel elem.nev elem.rendszam elem.ev elem.felfedezo
| None -> printfn "Nincs ilyen elem eltárolva!"

seq { 0 .. elemek.Length - 2 }
|> Seq.filter(fun i -> elemek.[i].ev <> OKOR_EV && elemek.[i + 1].ev <> OKOR_EV)
|> Seq.maxBy(fun i -> elemek.[i + 1].ev - elemek.[i].ev)
|> printfn "7. Feladat: Leghoszabb idő: %d év"

printfn "8. Feladat"

elemek |> Seq.filter(fun k -> k.ev <> OKOR_EV)
       |> Seq.groupBy(fun k -> k.ev)
       |> Seq.map(fun (ev, elemek) -> (ev, elemek |> Seq.length))
       |> Seq.filter(fun (_, dbSzam) -> dbSzam > 3)
       |> Seq.iter(fun (ev, dbSzam) -> printfn "%d: %d db" ev dbSzam)