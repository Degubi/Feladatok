open System
open System.IO

let isAnagramm word1 word2 =
    let firstWordLetters = word1 |> Seq.sort
    let secondWordLetters = word2 |> Seq.sort

    Seq.compareWith compare firstWordLetters secondWordLetters = 0

let anagramGroupSeqs(words: string[]) =
    words |> Seq.map(fun k -> k |> Seq.sort |> Seq.toArray |> String)
          |> Seq.distinct
          |> Seq.map(fun k -> words |> Seq.filter(fun n -> isAnagramm k n) |> Seq.toArray)


printfn "1. Feladat: Írjon be 1 szöveget!"

let bekertSzoveg = Console.ReadLine()
let bekertSzovegKarakterek = bekertSzoveg |> Seq.distinct |> Seq.sort |> Seq.toArray

printfn "Karakterek száma: %d (%s)" bekertSzovegKarakterek.Length (String bekertSzovegKarakterek)

let szotarSzavak = File.ReadAllLines "szotar.txt"

szotarSzavak |> Seq.map(fun k -> k |> Seq.sort |> Seq.toArray |> String)
             |> fun k -> File.WriteAllLines("abc.txt", k)

printfn "4. Feladat: Írjon be 2 szót!"

let elsoSzo = Console.ReadLine()
let masodikSzo = Console.ReadLine()

printfn "%s" (if isAnagramm elsoSzo masodikSzo then "Anagramma" else "Nem anagramma")
printfn "5. Feladat: Írjon be 1 szót!"

let bekertSzo = Console.ReadLine()

szotarSzavak |> Seq.filter(fun k -> isAnagramm k bekertSzo)
             |> Seq.toArray
             |> fun k -> printfn "%s" (if k.Length = 0 then "Nincs a szótárban anagramma" else "    " + String.Join("\n    ", k))

printfn "6. Feladat:"

let legosszabbSzoHossza = szotarSzavak |> Seq.map(fun k -> k.Length)
                                       |> Seq.max

szotarSzavak |> Seq.filter(fun k -> k.Length = legosszabbSzoHossza)
             |> Seq.toArray
             |> anagramGroupSeqs
             |> Seq.map(fun k -> k.[0] + ": \n" + (k |> Seq.skip 1 |> Seq.map(fun n -> "    " + n) |> fun n -> String.Join('\n', n)) + "\n")
             |> Seq.iter(Console.WriteLine)

szotarSzavak |> anagramGroupSeqs
             |> Seq.groupBy(fun k -> k.[0].Length)
             |> Seq.sortBy(fun (k, _) -> k)
             |> Seq.map(fun (_, k) -> k)
             |> Seq.map(fun k -> k |> Seq.map(fun n -> String.Join(' ', n)) |> fun n -> String.Join('\n', n) + "\n")
             |> fun k -> File.WriteAllLines("rendezve.txt", k)