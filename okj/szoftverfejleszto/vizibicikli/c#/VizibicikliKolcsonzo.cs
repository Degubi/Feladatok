using System;
using System.Collections.Generic;
using System.IO;

var lines = File.ReadAllLines("kolcsonzesek.txt");
var kolcsonzesek = new List<Kolcsonzes>();

for(var i = 1; i < lines.Length; ++i) {
    kolcsonzesek.Add(new Kolcsonzes(lines[i]));
}

Console.WriteLine($"5. Feladat: Kölcsönzések száma: {kolcsonzesek.Count}");
Console.WriteLine("Írj be 1 nevet!");

var bekertNev = Console.ReadLine();
var bekertNevhezTartozoKolcsonzesek = new List<Kolcsonzes>();

foreach(var kolcsonzes in kolcsonzesek) {
    if(kolcsonzes.nev == bekertNev) {
        bekertNevhezTartozoKolcsonzesek.Add(kolcsonzes);
    }
}

if(bekertNevhezTartozoKolcsonzesek.Count == 0) {
    Console.WriteLine("Nem volt ilyen nevű kölcsönző");
}else{
    foreach(var kolcsonzes in bekertNevhezTartozoKolcsonzesek) {
        Console.WriteLine($"{kolcsonzes.elvitelIdopont}-{kolcsonzes.visszahozatalIdopont}");
    }
}

Console.WriteLine("Adj meg 1 időpontot! (óra:perc)");

var bekertIdopontParts = Console.ReadLine().Split(':');
var bekertIdopont = new TimeSpan(int.Parse(bekertIdopontParts[0]), int.Parse(bekertIdopontParts[1]), 0);

Console.WriteLine("7. Feladat:");

foreach(var kolcsonzes in kolcsonzesek) {
    if(bekertIdopont > kolcsonzes.elvitelIdopont && bekertIdopont < kolcsonzes.visszahozatalIdopont) {
        Console.WriteLine($"    {kolcsonzes.elvitelIdopont}-{kolcsonzes.visszahozatalIdopont}: {kolcsonzes.nev}");
    }
}

var totalStartedHours = 0;
foreach(var kolcsonzes in kolcsonzesek) {
    var minutesBetweenStartEnd = (kolcsonzes.visszahozatalIdopont - kolcsonzes.elvitelIdopont).Minutes;

    totalStartedHours += (int) Math.Ceiling(minutesBetweenStartEnd / 30.0);
}

Console.WriteLine($"8. Feladat: A bevétel {totalStartedHours * 2400}Ft");

var fileba = new List<string>();
foreach(var kolcsonzes in kolcsonzesek) {
    if(kolcsonzes.jarmuAzonosito == "F") {
        fileba.Add($"{kolcsonzes.elvitelIdopont}-{kolcsonzes.visszahozatalIdopont}: {kolcsonzes.nev}");
    }
}

File.WriteAllLines("F.txt", fileba);
Console.WriteLine("10. Feladat:");

var azonositoStat = new Dictionary<string, int>();
foreach(var kolcsonzes in kolcsonzesek) {
    var key = kolcsonzes.jarmuAzonosito;

    azonositoStat.Add(key, azonositoStat.GetValueOrDefault(key, 0) + 1);
}

foreach(var (azonosito, dbSzam) in azonositoStat) {
    Console.WriteLine($"    {azonosito}-{dbSzam}");
}