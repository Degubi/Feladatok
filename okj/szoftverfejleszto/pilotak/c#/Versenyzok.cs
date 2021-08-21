using System;
using System.Collections.Generic;
using System.IO;

var lines = File.ReadAllLines("pilotak.csv");
var pilotak = new List<Pilota>();

for(var i = 1; i < lines.Length; ++i) {
    pilotak.Add(new Pilota(lines[i]));
}

Console.WriteLine($"3. Feladat: Adatsorok száma: {pilotak.Count}");
Console.WriteLine($"4. Feladat: Utolsó pilóta neve: {pilotak[pilotak.Count - 1].nev}");
Console.WriteLine("5. Feladat:");

foreach(var pilota in pilotak) {
    var szuletesiEv = pilota.szuletesiDatum.Year;

    if(szuletesiEv >= 1800 && szuletesiEv <= 1900) {
        Console.WriteLine("    " + pilota.nev + " (" + pilota.szuletesiDatum + ")");
    }
}

var legkisebbRajtszamuPilota = pilotak[0];
foreach(var pilota in pilotak) {
    if(pilota.rajtszam != Pilota.URES_RAJTSZAM && pilota.rajtszam < legkisebbRajtszamuPilota.rajtszam) {
        legkisebbRajtszamuPilota = pilota;
    }
}

Console.WriteLine("6. Feladat: " + legkisebbRajtszamuPilota.nemzetiseg);
Console.Write("7. Feladat: ");

var rajtszamStat = new Dictionary<int, int>();
foreach(var pilota in pilotak) {
    var rajtszam = pilota.rajtszam;

    if(rajtszam != Pilota.URES_RAJTSZAM) {
        rajtszamStat[rajtszam] = rajtszamStat.GetValueOrDefault(rajtszam, 0) + 1;
    }
}

foreach(var (rajtszam, dbSzam) in rajtszamStat) {
    if(dbSzam > 1) {
        Console.Write(rajtszam + " ");
    }
}