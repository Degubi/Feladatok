using System;
using System.IO;
using System.Collections.Generic;

var lines = File.ReadAllLines("nobel.csv");
var dijak = new List<Dij>();

for(var i = 1; i < lines.Length; ++i) {
    dijak.Add(new Dij(lines[i]));
}

foreach(var dij in dijak) {
    if(dij.keresztNev == "Arthur B." && dij.vezetekNev == "McDonald") {
        Console.WriteLine("3. Feladat: Arthur " + dij.tipus + " díjat kapott");
        break;
    }
}

Console.WriteLine("4. Feladat:");
foreach(var dij in dijak) {
    if(dij.ev == 2017 && dij.tipus == "irodalmi") {
        Console.WriteLine("Irodalmi díjat kapott: " + dij.keresztNev + " " + dij.vezetekNev);
    }
}

Console.WriteLine("5. Feladat:");
foreach(var dij in dijak) {
    if(dij.ev >= 1990 && dij.vezetekNev == "") {
        Console.WriteLine(dij.ev + ": " + dij.keresztNev);
    }
}

Console.WriteLine("6. Feladat");
foreach(var dij in dijak) {
    if(dij.vezetekNev.Contains("Curie")) {
        Console.WriteLine(dij.ev + ": " + dij.keresztNev + " " + dij.vezetekNev + ": " + dij.tipus);
    }
}

Console.WriteLine("7. Feladat");
var tipusSzamlalok = new Dictionary<string, int>();
foreach(var dij in dijak) {
    if(tipusSzamlalok.ContainsKey(dij.tipus)) {
        tipusSzamlalok[dij.tipus] = tipusSzamlalok[dij.tipus] + 1;
    }else{
        tipusSzamlalok[dij.tipus] = 0;
    }
}

foreach(var entry in tipusSzamlalok) {
    Console.WriteLine(entry.Key + ": " + entry.Value + " db");
}

dijak.Sort((k, l) => k.ev.CompareTo(l.ev));

using var output = new StreamWriter("orvosi.txt");
foreach(var dij in dijak) {
    if(dij.tipus == "orvosi") {
        output.WriteLine(dij.ev + ":" + dij.keresztNev + " " + dij.vezetekNev);
    }
}