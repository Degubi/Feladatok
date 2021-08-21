using System;
using System.IO;
using System.Linq;

var versenyek = File.ReadLines("jackie.txt")
                    .Skip(1)
                    .Select(k => new Verseny(k))
                    .ToArray();

Console.WriteLine($"3. Feladat: Adatsorok száma: {versenyek.Length}");

var legtobbIndulasosVerseny = versenyek.OrderByDescending(k => k.indulasokSzama)
                                       .First();

Console.WriteLine($"4. Feladat: {legtobbIndulasosVerseny.ev}");
Console.WriteLine("5. Feladat:");

versenyek.GroupBy(k => k.ev / 10 * 10 % 100, k => k.nyeresekSzama)
         .Select(k => new {
             Evtized = k.Key,
             Nyeresek = k.Sum()
         })
         .ToList()
         .ForEach(k => Console.WriteLine($"    {k.Evtized}-es évek: {k.Nyeresek} db nyerés"));

var htmlHeader = @"<!DOCTYPE html>
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

var htmlFooter = @"         </table>
                        </body>
                    </html>";

var tableContent = versenyek.Select(k => $"{new String(' ', 12)}<tr><td>{k.ev}</td><td>{k.indulasokSzama}</td><td>{k.nyeresekSzama}</td></tr>")
                            .Aggregate((k, m) => k + '\n' + m) + '\n';

File.WriteAllText("jackie.html", htmlHeader + tableContent + htmlFooter);