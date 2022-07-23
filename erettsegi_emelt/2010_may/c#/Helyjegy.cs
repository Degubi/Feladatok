using System;
using System.Collections.Generic;
using System.IO;

var lines = File.ReadAllLines("eladott.txt");
var firstLineSplit = lines[0].Split(' ');

var vonalHossz = int.Parse(firstLineSplit[1]);
var arPer10km = int.Parse(firstLineSplit[2]);
var utasok = new List<Utas>();

for(var k = 1; k < lines.Length; ++k) {
    utasok.Add(new Utas(lines[k], k));
}

var utolso = utasok[utasok.Count - 1];
Console.WriteLine($"2.Feladat: Utolsó utas ülése: {utolso.ules} utazott távolság: {utolso.leszallasKm - utolso.felszallasKm}");
Console.WriteLine("3.Feladat:");

var osszesPenz = 0;
foreach(var utas in utasok) {
    osszesPenz += Utas.getAr(utas, arPer10km);

    if((utas.leszallasKm - utas.felszallasKm) == vonalHossz) {
        Console.Write(utas.sorszam + " ");
    }
}

Console.WriteLine($"\n4.Feladat: Összes bevétel: {osszesPenz}");

var utolsoElottiMegalloKm = 0;
foreach(var utas in utasok) {
    if(utas.leszallasKm > utolsoElottiMegalloKm && utas.leszallasKm != vonalHossz) {
        utolsoElottiMegalloKm = utas.leszallasKm;
    }
}

var utoljaraFelszallok = 0;
var utoljaraLeszallok = 0;
foreach(var utas in utasok) {
    if(utas.felszallasKm == utolsoElottiMegalloKm) {
        ++utoljaraFelszallok;
    }

    if(utas.leszallasKm == utolsoElottiMegalloKm) {
        ++utoljaraLeszallok;
    }
}

Console.WriteLine($"5.Feladat: Utolsó megállónál felszállók: {utoljaraFelszallok}, leszállók: {utoljaraLeszallok}");

var allomasok = new HashSet<int>();
foreach(var utas in utasok) {
    allomasok.Add(utas.felszallasKm);
    allomasok.Add(utas.leszallasKm);
}

Console.WriteLine($"6.Feladat: Megállók száma: {allomasok.Count - 2}");
Console.WriteLine("Írj be 1 km számot!");

var bekertKm = int.Parse(Console.ReadLine());
using var output = new StreamWriter("kihol.txt");

for(var k = 1; k <= 48; ++k) {
    var currentUtas = (Utas) null;

    foreach(var utas in utasok) {
        if(utas.ules == k && (utas.felszallasKm == bekertKm || utas.leszallasKm == bekertKm)) {
            currentUtas = utas;
        }
    }

    output.WriteLine(k + ". ülés: " + (currentUtas == null ? "üres" : (currentUtas.sorszam + ". utas")));
}