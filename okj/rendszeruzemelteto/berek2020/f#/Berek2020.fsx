open System
open System.IO

let dolgozotKeszit(data: string[]) = {|
    Nev = data.[0]
    Nem = data.[1]
    MunkaReszleg = data.[2]
    MunkabaLepesEv = int data.[3]
    MunkaBer = int data.[4]
|}

let dolgozok = "berek2020.txt" |> File.ReadLines
                               |> Seq.skip 1
                               |> Seq.map(fun k -> k.Split ';' |> dolgozotKeszit)
                               |> Seq.toArray

printfn "3. Feladat: Dolgozók száma: %d" dolgozok.Length

dolgozok |> Seq.averageBy(fun k -> float k.MunkaBer)
         |> fun k -> printfn "4. Feladat: Átlagbér: %.2f" (k / float 1000)

printfn "5. Feladat: Írjon be 1 részleg nevet!"

let bekertReszleg = Console.ReadLine()
let bekertHelyenDolgozok = dolgozok |> Seq.filter(fun k -> k.MunkaReszleg = bekertReszleg) |> Seq.toArray

printf "6. Feladat: "

if bekertHelyenDolgozok.Length = 0 then
    printfn "A megadott részleg nem létezik a cégnél!"
else
    bekertHelyenDolgozok |> Seq.maxBy(fun k -> k.MunkaBer)
                         |> fun k -> printfn "%s (%d) : %d FT" k.Nev k.MunkabaLepesEv k.MunkaBer

printfn "7. Feladat:"

dolgozok |> Seq.countBy(fun k -> k.MunkaReszleg)
         |> Seq.iter(fun (reszleg, count) -> printfn "    %s - %d fő" reszleg count)