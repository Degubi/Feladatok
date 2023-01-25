using System;
using System.Collections.Generic;
using System.IO;

var lines = File.ReadAllLines("schumacher.csv");
var eredmenyek = new List<Eredmeny>();

for(var i = 1; i < lines.Length; ++i) {
    eredmenyek.Add(new Eredmeny(lines[i]));
}

Console.WriteLine($"3. Feladat: Adatsorok száma: {eredmenyek.Count}");
Console.WriteLine("4. Feladat:");

foreach(var eredmeny in eredmenyek) {
    if(eredmeny.dijNev == "Hungarian Grand Prix" && eredmeny.helyezes != 0) {
        Console.WriteLine($"    {eredmeny.datum.ToShortDateString()}: {eredmeny.helyezes}. hely");
    }
}

var celbaeresStat = new Dictionary<string, int>();
foreach(var eredmeny in eredmenyek) {
    var key = eredmeny.vegeredmenyStatusz;

    if(eredmeny.helyezes == 0) {
        celbaeresStat[key] = celbaeresStat.GetValueOrDefault(key, 0) + 1;
    }
}

Console.WriteLine("5. Feladat:");

foreach(var celbaeres in celbaeresStat) {
    if(celbaeres.Value > 2) {
        Console.WriteLine($"    {celbaeres.Key}: {celbaeres.Value}");
    }
}