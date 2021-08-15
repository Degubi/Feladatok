using System;
using System.IO;
using System.Collections.Generic;

var napnev = new []{ "vasarnap", "hetfo", "kedd", "szerda", "csutortok", "pentek", "szombat" };
var napszam = new []{ 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 335 };

var hianyzasok = new List<Hianyzas>();
var honapNapSzamlalok = new int[2];

foreach(var line in File.ReadAllLines("naplo.txt")) {
    var split = line.Split(' ');

    if(split[0] == "#") {
        honapNapSzamlalok[0] = int.Parse(split[1]);
        honapNapSzamlalok[1] = int.Parse(split[2]);
    }else{
        hianyzasok.Add(new Hianyzas(split[0] + ' ' +  split[1], split[2], honapNapSzamlalok[0], honapNapSzamlalok[1]));
    }
}

Console.WriteLine($"2. Feladat: Hiányzások száma: {hianyzasok.Count}");

var igazoltHianyzasok = 0;
var igazolatlanHianyzasok = 0;

foreach(var hiany in hianyzasok) {
    for(var i = 0; i < hiany.orak.Length; ++i) {
        var ora = hiany.orak[i];

        if(ora == 'X') {
            ++igazoltHianyzasok;
        }else if(ora == 'I') {
            ++igazolatlanHianyzasok;
        }
    }
}

Console.WriteLine($"3. Feladat: Igazolt hiányzások: {igazoltHianyzasok}, igazolatlanok: {igazolatlanHianyzasok}");
Console.WriteLine("5. Feladat: Írjon be egy hónapot és egy napot");

var beHonap = int.Parse(Console.ReadLine());
var beNap = int.Parse(Console.ReadLine());

Console.WriteLine($"Azon a napon: {Hetnapja(beHonap, beNap)} volt");
Console.WriteLine("6. Feladat: Írja be 1 nap nevét és 1 óraszámot");

var beTanNap = Console.ReadLine();
var beOraszam = int.Parse(Console.ReadLine()) - 1;
var hianyzottakSzama = 0;

foreach(var hiany in hianyzasok) {
    if(beTanNap == Hetnapja(hiany.honap, hiany.nap)) {
        var ora = hiany.orak[beOraszam];

        if(ora == 'X' || ora == 'I') {
            ++hianyzottakSzama;
        }
    }
}

Console.WriteLine($"Ekkor {hianyzottakSzama}-an hiányoztak");
Console.WriteLine("7. Feladat: ");

var hianyzasMap = new Dictionary<string, int>();
foreach(var hiany in hianyzasok) {
    var hianyzottakk = 0;

    for(var i = 0; i < hiany.orak.Length; ++i) {
        var ora = hiany.orak[i];

        if(ora == 'X' || ora == 'I') {
            ++hianyzottakk;
        }
    }

    hianyzasMap.TryGetValue(hiany.nev, out int eddigiHianyzottak);
    hianyzasMap.Add(hiany.nev, eddigiHianyzottak + hianyzottakk);
}

var legtobbHianyzas = -1;
foreach(var v in hianyzasMap.Values) {
    if(v > legtobbHianyzas) {
        legtobbHianyzas = v;
    }
}

foreach(var (k, v) in hianyzasMap) {
    if(v == legtobbHianyzas) {
        Console.Write(k + ' ');
    }
}

string Hetnapja(int honap, int nap) => napnev[(napszam[honap - 1] + nap) % 7];