using System;
using System.Collections.Generic;
using System.IO;

var lines = File.ReadAllLines("berek2020.txt");
var dolgozok = new List<Dolgozo>();

for(var i = 1; i < lines.Length; ++i) {
    dolgozok.Add(new Dolgozo(lines[i]));
}

Console.WriteLine($"3. Feladat: Dolgozók száma: {dolgozok.Count}");

var totalBer = 0;
foreach(var dolgozo in dolgozok) {
    totalBer += dolgozo.munkaBer;
}

Console.WriteLine($"4. Feladat: Átlagbér: {((float) totalBer / dolgozok.Count / 1000).ToString("0.00")}");
Console.WriteLine("5. Feladat: Írjon be 1 részleg nevet!");
Console.Write("6. Feladat: ");

var bekertReszleg = Console.ReadLine();
var legtobbBeresDolgozo = (Dolgozo) null;
var legtobbBer = 0;

foreach(var dolgozo in dolgozok) {
    if(dolgozo.munkaReszleg == bekertReszleg && dolgozo.munkaBer > legtobbBer) {
        legtobbBeresDolgozo = dolgozo;
        legtobbBer = dolgozo.munkaBer;
    }
}

if(legtobbBeresDolgozo == null) {
    Console.WriteLine("A megadott részleg nem létezik a cégnél!");
}else{
    Console.WriteLine($"{legtobbBeresDolgozo.nev} ({legtobbBeresDolgozo.munkabaLepesEv}) : {legtobbBeresDolgozo.munkaBer} FT");
}

Console.WriteLine("7. Feladat:");

var reszlegStat = new Dictionary<string, int>();
foreach(var dolgozo in dolgozok) {
    reszlegStat[dolgozo.munkaReszleg] = reszlegStat.GetValueOrDefault(dolgozo.munkaReszleg, 0) + 1;
}

foreach(var (reszleg, dbSzam) in reszlegStat) {
    Console.WriteLine($"    {reszleg} - {dbSzam} fő");
}