using System;
using System.Collections.Generic;
using System.IO;

var csatlakozasok = new List<Csatlakozas>();
var lines = File.ReadAllLines("EUcsatlakozas.txt", System.Text.Encoding.GetEncoding("ISO-8859-1"));

foreach(var line in lines) {
    csatlakozasok.Add(new Csatlakozas(line));
}

Console.WriteLine($"3. Feladat: 2018-ig csatlakozott országok száma: {csatlakozasok.Count}");

var tagallamok2007ben = 0;
foreach(var csati in csatlakozasok) {
    if(csati.datum.Year == 2007) {
        ++tagallamok2007ben;
    }
}

Console.WriteLine($"4. Feladat: 2007-ben csatlakozott országok száma: {tagallamok2007ben}");

foreach(var csati in csatlakozasok) {
    if(csati.orszag == "Magyarország") {
        Console.WriteLine($"5. Feladat: Magyarország csatlakozása: {csati.datum}");
        break;
    }
}

var voltEMajusban = false;
foreach(var csati in csatlakozasok) {
    if(csati.datum.Month == 5) {
        voltEMajusban = true;
        break;
    }
}

Console.WriteLine("6. Feladat: " + (voltEMajusban ? "Volt" : "Nem volt") + " májusban csatlakozás");

var utoljaraCsatlakozo = csatlakozasok[0];
foreach(var csati in csatlakozasok) {
    if(csati.datum > utoljaraCsatlakozo.datum) {
        utoljaraCsatlakozo = csati;
    }
}

Console.WriteLine($"7. Feladat: Utoljára csatlakozott: {utoljaraCsatlakozo.orszag}");
Console.WriteLine("8. Feladat:");

var stat = new Dictionary<int, int>();
foreach(var csati in csatlakozasok) {
    var ev = csati.datum.Year;

    stat[ev] = stat.GetValueOrDefault(ev, 0) + 1;
}

foreach(var (ev, dbSzam) in stat) {
    Console.WriteLine($"{ev} - {dbSzam} db ország");
}