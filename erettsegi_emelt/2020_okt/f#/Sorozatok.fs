open System
open System.IO
open System.Globalization

let napok = [| "v"; "h"; "k"; "sz"; "cs"; "p"; "szo" |]
let honapok = [| 0; 3; 2; 5; 0; 3; 5; 1; 4; 6; 2; 4 |]

let hetnapja(ev: int, ho: int, nap: int) =
    let ev2 = if ho < 3 then ev - 1 else ev

    napok.[(ev2 + ev2 / 4 - ev2 / 100 + ev2 / 400 + honapok.[ho - 1] + nap) % 7]

let sorozatotKeszit(lines: string[], index: int) =
    let datumStr = lines.[index]
    let epizodInfoSplit = lines.[index + 2].Split('x')

    {|
        AdasbaKerulesiDatum = if datumStr = "NI" then DateTime.MinValue else DateTime.ParseExact(datumStr, "yyyy.MM.dd", CultureInfo.InvariantCulture);
        Cim = lines.[index + 1];
        EvadokSzama = int epizodInfoSplit.[0];
        EpizodokSzama = int epizodInfoSplit.[1];
        EpizodonkentiHossz = int lines.[index + 3]
        LattaEMarAKeszito = int lines.[index + 4] = 1
    |}

[<EntryPoint>]
let main _ =
    let lines = File.ReadAllLines("lista.txt")
    let sorozatok = seq { 0 .. 5 .. lines.Length - 1 } |> Seq.map(fun i -> sorozatotKeszit(lines, i))
                                                       |> Seq.toArray

    sorozatok |> Seq.filter(fun k -> k.AdasbaKerulesiDatum <> DateTime.MinValue)
              |> Seq.length
              |> printfn "2. Feladat: %d db ismert dátumú epizód van"

    sorozatok |> Seq.filter(fun k -> k.LattaEMarAKeszito)
              |> Seq.length
              |> fun k -> float k / float sorozatok.Length * 100.0
              |> printfn "3. Feladat: Látottak százaléka: %.2f%%"

    sorozatok |> Seq.filter(fun k -> k.LattaEMarAKeszito)
              |> Seq.sumBy(fun k -> k.EpizodonkentiHossz)
              |> float
              |> TimeSpan.FromMinutes
              |> fun k -> printfn "4. Feladat: Eltöltött idő: %d nap, %d óra és %d perc" k.Days k.Hours k.Minutes

    printfn "5. Feladat: Írj be 1 dátumot! (éééé.hh.nn)"

    let bekertDatumStr = Console.ReadLine()
    let bekertDatum = DateTime.ParseExact(bekertDatumStr, "yyyy.MM.dd", CultureInfo.InvariantCulture)

    sorozatok |> Seq.filter(fun k -> k.AdasbaKerulesiDatum <> DateTime.MinValue && not k.LattaEMarAKeszito)
              |> Seq.filter(fun k -> k.AdasbaKerulesiDatum < bekertDatum || k.AdasbaKerulesiDatum = bekertDatum)
              |> Seq.iter(fun k -> printfn "%dx%d\t%s" k.EvadokSzama k.EpizodokSzama k.Cim)

    printfn "7. Feladat: Add meg 1 hét napját! (h, k, sze, cs, p, szo, v)"

    let bekertNap = Console.ReadLine()

    sorozatok |> Seq.filter(fun k -> k.AdasbaKerulesiDatum <> DateTime.MinValue)
              |> Seq.filter(fun k -> bekertNap = hetnapja(k.AdasbaKerulesiDatum.Year, k.AdasbaKerulesiDatum.Month, k.AdasbaKerulesiDatum.Day))
              |> Seq.map(fun k -> k.Cim)
              |> Seq.distinct
              |> Seq.toArray
              |> fun k -> if k.Length = 0 then "Az adott napon nem kerül adásba sorozat." else String.Join("\n", k)
              |> printfn "%s"

    sorozatok |> Seq.groupBy(fun k -> k.Cim)
              |> Seq.map(fun (k, v) -> k + " " + string (v |> Seq.sumBy(fun m -> m.EpizodonkentiHossz)) + " " + string (v |> Seq.length))
              |> Seq.toArray
              |> fun k -> File.WriteAllLines("summa.txt", k)
    0