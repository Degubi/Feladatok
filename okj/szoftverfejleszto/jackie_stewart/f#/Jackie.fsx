open System
open System.IO

let versenyKeszit(data: string[]) = {|
    Ev = int data.[0]
    IndulasokSzama = int data.[1]
    NyeresekSzama = int data.[2]
    DobogosokSzama = int data.[3]
    ElsoHelyrolIndulasokSzama = int data.[4]
    LeggyorsabbKorokSzama = int data.[5]
|}

let versenyek = "jackie.txt" |> File.ReadLines
                             |> Seq.skip 1
                             |> Seq.map(fun k -> k.Split '\t' |> versenyKeszit)
                             |> Seq.toArray

printfn "3. Feladat: Adatsorok száma: %d" versenyek.Length

versenyek |> Seq.maxBy(fun k -> k.IndulasokSzama)
          |> fun k -> printfn "4. Feladat: %d" k.Ev

printfn "5. Feladat:"

versenyek |> Seq.groupBy(fun k -> k.Ev / 10 * 10 % 100)
          |> Seq.iter(fun (evtized, vers) -> printfn "    %d-s évek: %d db nyerés" evtized (vers |> Seq.sumBy(fun m -> m.NyeresekSzama)))

let htmlHeader = @"<!DOCTYPE html>
                   <html>
                       <head>
                           <style>
                               td {
                                   border: 1px solid black;
                               }
                           </style>
                       </head>
                       <body>
                           <h1>Jackie Stewart</h1>
                           <table>
                           ";

let htmlFooter = @"         </table>
                        </body>
                    </html>";

let tableContent = versenyek |> Seq.map(fun k -> $"{String(' ', 12)}<tr><td>{k.Ev}</td><td>{k.IndulasokSzama}</td><td>{k.NyeresekSzama}</td></tr>")
                             |> Seq.reduce(fun k m -> k + "\n" + m)


File.WriteAllText("jackie.html", htmlHeader + tableContent + "\n" + htmlFooter)