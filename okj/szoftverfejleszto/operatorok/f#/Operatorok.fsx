open System
open System.IO
open System.Text
open Microsoft.FSharp.Core.Operators.Checked // Ez kell, mert így az operatorok exception-t dobnak overflow esetén

type Kifejezes = { ElsoOperandus: int; Operator: string; MasodikOperandus: int; TeljesKifejezes: string }

let kifejezestKeszit(line: string) =
    let split = line.Split(' ')

    {
        ElsoOperandus = int split.[0]
        Operator = split.[1]
        MasodikOperandus = int split.[2]
        TeljesKifejezes = line
    }

let kifejezestBeker(_) =
    printfn "Kérek egy kifejezést!"
    Console.ReadLine()

let kifejezestKiertekel(kif: Kifejezes) =
    try
        match kif.Operator with
        | "+" -> kif.ElsoOperandus + kif.MasodikOperandus |> string
        | "-" -> kif.ElsoOperandus - kif.MasodikOperandus |> string
        | "*" -> kif.ElsoOperandus * kif.MasodikOperandus |> string
        | "/" -> (double kif.ElsoOperandus) / (double kif.MasodikOperandus) |> string
        | "div" -> kif.ElsoOperandus / kif.MasodikOperandus |> string
        | "mod" -> kif.ElsoOperandus % kif.MasodikOperandus |> string
        | _ -> "Hibás operátor!"
    with
    | _ -> "Egyéb hiba!"

let kifejezesek = File.ReadLines("kifejezesek.txt", Encoding.Latin1)
                  |> Seq.map(kifejezestKeszit)
                  |> Seq.toArray

printfn "2. Feladat: Kifejezések száma: %d" kifejezesek.Length

let operatoronkentiDbSzam = kifejezesek |> Seq.countBy(fun k -> k.Operator)
                                        |> dict

printfn "3. Feladat: Maradékos osztások száma: %d" operatoronkentiDbSzam.["mod"]

kifejezesek |> Seq.exists(fun k -> k.ElsoOperandus % 10 = 0 && k.MasodikOperandus % 10 = 0)
            |> fun k -> printfn "4. Feladat: %s ilyen kifejezés" (if k then "Van" else "Nincs")

Console.WriteLine("5. Feladat: \n" +
                  "    'mod' -> " + string operatoronkentiDbSzam.["mod"] + " db\n" +
                  "      '/' -> " + string operatoronkentiDbSzam.["/"] + " db\n" +
                  "    'div' -> " + string operatoronkentiDbSzam.["div"] + " db\n" +
                  "      '-' -> " + string operatoronkentiDbSzam.["-"] + " db\n" +
                  "      '*' -> " + string operatoronkentiDbSzam.["*"] + " db\n" +
                  "      '+' -> " + string operatoronkentiDbSzam.["+"] + " db")

Seq.initInfinite(kifejezestBeker) |> Seq.takeWhile(fun k -> k <> "vége")
                                  |> Seq.iter(fun k-> printfn "%s = %s" k (k |> kifejezestKeszit |> kifejezestKiertekel))

kifejezesek |> Seq.map(fun k -> k.TeljesKifejezes + " = " + kifejezestKiertekel k)
            |> fun k -> File.WriteAllLines("eredmenyek.txt", k)