open System
open System.IO

let telketKeszit(data: string[]) = {|
    adoszam = int data.[0]
    utcaNev = data.[1]
    hazSzam = data.[2]
    adosav = data.[3]
    terulet = int data.[4]
|}

let ado adosav terulet (fizetendoAdokSavonkent: Map<string, int>) =
    fizetendoAdokSavonkent.[adosav] * terulet;


let lines = File.ReadAllLines "utca.txt"
let fizetendoAdokSavonkent = lines.[0].Split ' ' |> Seq.map(int)
                                                 |> Seq.toArray
                                                 |> fun k -> Map([ "A", k.[0]; "B", k.[1]; "C", k.[2] ])
let telkek = lines |> Seq.skip 1
                   |> Seq.map(fun k -> k.Split ' ' |> telketKeszit)
                   |> Seq.toArray

printfn "2. Feladat: Telkek száma: %d" telkek.Length
printfn "3. Feladat: Írj be 1 adószámot!"

let bekertAdoszam = Console.ReadLine() |> int
let bekertTelkei = telkek |> Seq.filter(fun k -> k.adoszam = bekertAdoszam)
                          |> Seq.toArray

(if bekertTelkei.Length = 0 then "Nem szerepel az adatállományban"
                           else bekertTelkei |> Seq.map(fun k -> $"{k.utcaNev} {k.hazSzam}")
                                             |> fun k -> String.Join('\n', k)
) |> Console.WriteLine

printfn "5. Feladat:"

telkek |> Seq.groupBy(fun k -> k.adosav)
       |> Seq.map(fun (sav, savhozTelkek) -> (sav, savhozTelkek |> Seq.length, savhozTelkek |> Seq.sumBy(fun k -> ado k.adosav k.terulet fizetendoAdokSavonkent)))
       |> Seq.iter(fun (sav, telkekSzama, teljesAdo) -> printfn "%s sáv: %d telek, adó: %d" sav telkekSzama teljesAdo)

printfn "6. Feladat: Több sávba sorolt utcák:"

telkek |> Seq.groupBy(fun k -> k.utcaNev)
       |> Seq.filter(fun (_, telkekUtcahoz) -> telkekUtcahoz |> Seq.distinctBy(fun k -> k.adosav) |> Seq.length > 1)
       |> Seq.iter(fun (utcaNev, _) -> printfn "%s" utcaNev)

telkek |> Seq.groupBy(fun k -> k.adoszam)
       |> Seq.map(fun (adoszam, telkekAdoszamhoz) -> (adoszam, telkekAdoszamhoz |> Seq.sumBy(fun k -> ado k.adosav k.terulet fizetendoAdokSavonkent)))
       |> Seq.map(fun (adoszam, osszAdo) -> $"{adoszam} {osszAdo}")
       |> fun k -> File.WriteAllLines("fizetendo.txt", k)