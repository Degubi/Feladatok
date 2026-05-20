open System
open System.IO

type TelepulesAdat = {
    Telepules: string
    Ido: TimeSpan
    SzelIrany: string
    SzelErosseg: int
    Homerseklet: int
}

let idojarasAdatotKeszit(args: string[]) =
    let teljesIdo = int args.[1]

    {
        Telepules = args.[0]
        Ido = TimeSpan(teljesIdo / 100, teljesIdo % 100, 0)
        SzelIrany = args.[2].Substring(0, 3)
        SzelErosseg = args.[2].Substring(3) |> int
        Homerseklet = int args.[3]
    }

let telepuleshezTartozoKiiratandoHomersekletAdat telepules adatok =
    let homersekletek = adatok |> Seq.map(fun k -> k.Homerseklet)
                               |> Seq.toArray

    let ingadozas = Seq.max homersekletek - Seq.min homersekletek |> string
    let mindenOraraVanAdat = adatok |> Seq.map(fun k -> k.Ido.Hours)
                                    |> Seq.distinct
                                    |> Seq.length = 24

    if mindenOraraVanAdat then
        let kerekitettKozep = (Seq.averageBy float homersekletek) |> Math.Ceiling
        telepules + ": középhőmérséklet: " + string kerekitettKozep + "; Ingadozás: " + ingadozas
    else
        telepules + ": NA; Ingadozás: " + ingadozas

let telepulesAdataiFajlba telepules adatok =
    let adatSorok = adatok |> Seq.map(fun k -> string k.Ido + " " + String('#', k.SzelErosseg))
                           |> String.concat("\n")

    File.WriteAllText(telepules + ".txt", telepules + "\n" + adatSorok)


let adatok = "tavirathu13.txt" |> File.ReadLines
                               |> Seq.map(fun k -> k.Split ' ' |> idojarasAdatotKeszit)
                               |> Seq.toArray

printfn "2. Feladat: Írj be 1 városkódot!"

let bekertKod = Console.ReadLine()

adatok |> Seq.filter(fun k -> k.Telepules = bekertKod)
       |> Seq.last
       |> fun k -> printfn "Utolsó mérés időpontja: %O" k.Ido

printfn "3. Feladat:"

let rendezettAdatok = adatok |> Seq.sortBy(fun k -> k.Homerseklet)
                             |> Seq.toArray

let legalacsonyabbAdat = rendezettAdatok.[0]
let legmagasabbAdat = rendezettAdatok.[rendezettAdatok.Length - 1]

printfn "Legalacsonyabb hőmérséklet: %s %O %d fok" legalacsonyabbAdat.Telepules legalacsonyabbAdat.Ido legalacsonyabbAdat.Homerseklet
printfn "Legmagasabb hőmérséklet: %s %O %d fok" legmagasabbAdat.Telepules legmagasabbAdat.Ido legmagasabbAdat.Homerseklet
printfn "4. Feladat:"

let szelcsendek = adatok |> Seq.filter(fun k -> k.SzelIrany = "000" && k.SzelErosseg = 0)
                         |> Seq.toArray

if szelcsendek.Length = 0 then
    printfn "Nem volt szélcsend a mérések idején."
else
    szelcsendek |> Seq.iter(fun k -> printfn "%s: %O" k.Telepules k.Ido)

printfn "5. Feladat:"

let adatokVarosonkent = adatok |> Seq.groupBy(fun k -> k.Telepules)

adatokVarosonkent |> Seq.map(fun (k, v) -> telepuleshezTartozoKiiratandoHomersekletAdat k v)
                  |> Seq.iter(fun k -> printfn "%s" k)

adatokVarosonkent |> Seq.iter(fun (k, v) -> telepulesAdataiFajlba k v)