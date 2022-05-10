open System
open System.IO

let domain(szint: int) (domain: string) =
    let split = domain.Split '.'
    let utolsoIndex = split.Length - 1

    if utolsoIndex < szint then "nincs" else split.[utolsoIndex - szint]

let formatPair(index: int) (pairs: {| Domain: string; IP: string |}[]) =
    let pair = pairs.[index]
    let elements = seq { 0 .. 4 } |> Seq.map(fun i -> "<td>" + domain i pair.Domain + "</td>")
                                  |> fun k -> String.Join("\n", k)
    "<tr>\n" +
    "<th style='text-align: left'>" + string (index + 1) + ".</th>\n" +
    "<td>" + pair.Domain + "</td>\n" +
    "<td>" + pair.IP + "</td>\n" + elements + "\n"


let pairs = "csudh.txt" |> File.ReadLines
                        |> Seq.skip 1
                        |> Seq.map(fun k -> k.Split ';')
                        |> Seq.map(fun k -> {| Domain = k.[0]; IP = k.[1] |})
                        |> Seq.toArray

printfn "3. Feladat: Párok száma: %d" pairs.Length
printfn "5. Feladat"

seq { 0 .. 4 } |> Seq.iter(fun i -> printfn "%d. szint: %s" (i + 1) (domain i pairs.[0].Domain))

let header = "<table>\n" +
             "<tr>\n" +
             "<th style='text-align: left'>Sorszam</th>\n" +
             "<th style='text-align: left'>Host domain neve</th>\n" +
             "<th style='text-align: left'>Host IP címe</th>\n" +
             "<th style='text-align: left'>1. szint</th>\n" +
             "<th style='text-align: left'>2. szint</th>\n" +
             "<th style='text-align: left'>3. szint</th>\n" +
             "<th style='text-align: left'>4. szint</th>\n" +
             "<th style='text-align: left'>5. szint</th>\n" +
             "</tr>"

seq { 0 .. pairs.Length - 1 } |> Seq.map(fun i -> formatPair i pairs)
                              |> fun k -> String.Join("</tr>\n", k)
                              |> fun formattedPairs -> File.WriteAllText("table.html", header + formattedPairs + "</table>")