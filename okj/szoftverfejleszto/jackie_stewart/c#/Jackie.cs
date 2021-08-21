using System;
using System.Collections.Generic;
using System.IO;

var lines = File.ReadAllLines("jackie.txt");
var versenyek = new List<Verseny>();

for(var i = 1; i < lines.Length; ++i) {
    versenyek.Add(new Verseny(lines[i]));
}

Console.WriteLine($"3. Feladat: Adatsorok száma: {versenyek.Count}");

var legtobbIndulasosVerseny = versenyek[0];
foreach(var verseny in versenyek) {
    if(verseny.indulasokSzama > legtobbIndulasosVerseny.indulasokSzama) {
        legtobbIndulasosVerseny = verseny;
    }
}

Console.WriteLine($"4. Feladat: {legtobbIndulasosVerseny.ev}");
Console.WriteLine("5. Feladat:");

var evtizedesStat = new Dictionary<int, int>();
foreach(var verseny in versenyek) {
    var evtized = verseny.ev / 10 * 10 % 100;

    evtizedesStat[evtized] = evtizedesStat.GetValueOrDefault(evtized, 0) + verseny.nyeresekSzama;
}

foreach(var (evtized, nyeresekSzama) in evtizedesStat) {
    Console.WriteLine($"    {evtized}-es évek: {nyeresekSzama} db nyerés");
}

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

using var output = new StreamWriter("jackie.html");

output.Write(htmlHeader);

foreach(var verseny in versenyek) {

    output.WriteLine($"{new String(' ', 12)}<tr><td>{verseny.ev}</td><td>{verseny.indulasokSzama}</td><td>{verseny.nyeresekSzama}</td></tr>");
}

output.Write(htmlFooter);