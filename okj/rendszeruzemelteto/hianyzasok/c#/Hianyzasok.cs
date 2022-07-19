using System;
using System.Collections.Generic;
using System.IO;

var hianyzasok = new List<Hianyzas>();
var lines = File.ReadAllLines("szeptember.csv");

for(var i = 1; i < lines.Length; ++i) {
    hianyzasok.Add(new Hianyzas(lines[i]));
}

var totalHianyzottOrak = 0;
foreach(var hianyzas in hianyzasok) {
    totalHianyzottOrak += hianyzas.mulasztottOrak;
}

Console.WriteLine($"2. Feladat: Hianyzott orak: {totalHianyzottOrak}");
Console.WriteLine("3. Feladat: Írj be egy napot(1-30) és egy nevet!");

var bekertNap = int.Parse(Console.ReadLine());
var bekertNev = Console.ReadLine();

Console.WriteLine("4. Feladat");

var hianyzottE = false;
foreach(var hianyzas in hianyzasok) {
    if(hianyzas.nev == bekertNev) {
        hianyzottE = true;
        break;
    }
}

Console.WriteLine(bekertNev + (hianyzottE ? " hianyzott" : " nem hianyzott"));
Console.WriteLine("5. Feladat");

var azonANaponHianyoztak = new List<Hianyzas>();
foreach(var hianyzas in hianyzasok) {
    if(bekertNap >= hianyzas.elsoNap && bekertNap <= hianyzas.utolsoNap) {
        azonANaponHianyoztak.Add(hianyzas);
    }
}

if(azonANaponHianyoztak.Count == 0) {
    Console.WriteLine("Nem volt hiányzó");
}else{
    foreach(var hiany in azonANaponHianyoztak) {
        Console.WriteLine($"{hiany.nev} {hiany.osztaly}");
    }
}

var hianyzasokStat = new Dictionary<string, int>();
foreach(var hianyzas in hianyzasok) {
    hianyzasokStat[hianyzas.osztaly] = hianyzasokStat.GetValueOrDefault(hianyzas.osztaly, 0) + hianyzas.mulasztottOrak;
}

using var output = new StreamWriter("osszesitett.csv");
foreach(var (osztaly, orak) in hianyzasokStat) {
    output.WriteLine($"{osztaly};{orak}");
}