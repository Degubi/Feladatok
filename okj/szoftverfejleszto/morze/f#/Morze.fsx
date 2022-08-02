open System
open System.IO

let morze2Szoveg(uzenet: string) (abc: Map<string, string>) =
    uzenet.Split "       " |> Seq.map(fun k -> k.Split "   " |> Seq.map(fun n -> abc.[n]) |> fun n -> String.Join("", n))
                           |> fun k -> String.Join(" ", k)

let idezetetKeszit(abc: Map<string, string>) (data: string[]) = {|
    szerzo = morze2Szoveg data.[0] abc
    uzenet = morze2Szoveg data.[1] abc
|}

let betuToMorze = File.ReadLines("morzeabc.txt", System.Text.Encoding.Latin1)
                  |> Seq.skip 1
                  |> Seq.map(fun k -> k.Split "\t")
                  |> Seq.map(fun k -> (k.[0], k.[1]))
                  |> Map

printfn "3. Feladat: Karakterek száma: %d" betuToMorze.Count
printfn "4. Feladat: Írjon be 1 karaktert!"

let bekert = Console.ReadLine()

match betuToMorze.ContainsKey bekert with
    | true -> printfn "A %s karakter kódja: %s" bekert betuToMorze.[bekert]
    | false -> printfn "Nem található a kódtárban ilyen karakter!"

let morzeToBetu = betuToMorze |> Seq.map(fun (KeyValue(k, v)) -> (v, k)) |> Map

let idezetek = "morze.txt" |> File.ReadLines
                           |> Seq.map(fun k -> k.Split ";" |> idezetetKeszit morzeToBetu)
                           |> Seq.toArray

printfn "7. Feladat: Első idézet szerzője: %s" idezetek.[0].szerzo

idezetek |> Seq.maxBy(fun k -> k.uzenet.Length)
         |> fun k -> printfn "8. Feladat: Leghosszab idézet: %s: %s" k.szerzo k.uzenet

printfn "9. Feladat: Arisztotelés idézetei: "

idezetek |> Seq.filter(fun k -> k.szerzo = "ARISZTOTELÉSZ")
         |> Seq.iter(fun k -> printfn "\t- %s" k.uzenet)

idezetek |> Seq.map(fun k -> $"{k.szerzo}:{k.uzenet}")
         |> fun k -> File.WriteAllLines("forditas.txt", k)