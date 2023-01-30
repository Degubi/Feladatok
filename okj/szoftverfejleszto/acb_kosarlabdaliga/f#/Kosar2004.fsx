open System
open System.IO

let eredmenytKeszit(data: string[]) = {|
    hazaiCsapat = data.[0]
    idegenCsapat = data.[1]
    hazaiPont = int data.[2]
    idegenPont = int data.[3]
    helyszin = data.[4]
    idopont = DateTime.Parse data.[5]
|}

let eredmenyek = "eredmenyek.csv" |> File.ReadLines
                                  |> Seq.skip 1
                                  |> Seq.map(fun k -> k.Split ';' |> eredmenytKeszit)
                                  |> Seq.toArray

let hazaiMadridDb = eredmenyek |> Seq.filter(fun k -> k.hazaiCsapat = "Real Madrid")
                               |> Seq.length

let idegenMadridDb = eredmenyek |> Seq.filter(fun k -> k.idegenCsapat = "Real Madrid")
                                |> Seq.length

printfn "3. Feladat: Hazai: %d, idegen: %d" hazaiMadridDb idegenMadridDb

eredmenyek |> Seq.exists(fun k -> k.hazaiPont = k.idegenPont)
           |> fun k -> printfn "4. Feladat: Volt e döntetlen: %s" (if k then "igen" else "nem")

eredmenyek |> Seq.tryFind(fun k -> k.hazaiCsapat.Contains("Barcelona") || k.idegenCsapat.Contains("Barcelona"))
           |> function
              | Some k -> printfn "5. Feladat: Barcelona csapat neve: %s" (if k.hazaiCsapat.Contains("Barcelona") then k.hazaiCsapat else k.idegenCsapat)
              | None -> ()

let hatosFeladatIdopont = DateTime(2004, 11, 21)

printfn "6. Feladat:"
eredmenyek |> Seq.filter(fun k -> k.idopont = hatosFeladatIdopont)
           |> Seq.iter(fun k -> printfn "    %s - %s (%d:%d)" k.hazaiCsapat k.idegenCsapat k.hazaiPont k.idegenPont)

printfn "7. Feladat:"
eredmenyek |> Seq.countBy(fun k -> k.helyszin)
           |> Seq.filter(fun (_, count) -> count > 20)
           |> Seq.iter(fun (helyszin, count) -> printfn "    %s: %d" helyszin count)