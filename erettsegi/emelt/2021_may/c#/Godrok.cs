using System;
using System.Collections.Generic;
using System.IO;

var lines = File.ReadAllLines("melyseg.txt");
var melysegek = new int[lines.Length];

for(var i = 0; i < lines.Length; ++i) {
    melysegek[i] = int.Parse(lines[i]);
}

Console.WriteLine($"1. Feladat: {melysegek.Length}");
Console.WriteLine("2. Feladat: Írjon be egy távolságértéket!");

var bekertTavolsagIndexe = int.Parse(Console.ReadLine()) - 1;
var melysegABekertHelyen = melysegek[bekertTavolsagIndexe];

Console.WriteLine($"A felszín {melysegABekertHelyen}m mélyen van");

var erintetlenFeluletSzam = 0;
foreach(var melyseg in melysegek) {
    if(melyseg == 0) {
        ++erintetlenFeluletSzam;
    }
}

Console.WriteLine($"3. Feladat: Érintetlen felszín: {((float) erintetlenFeluletSzam / melysegek.Length * 100).ToString("0.00")}");

var godrok = new List<int[]>();
var godorKezdoZaroIndexek = new List<int>();

for(var i = 0; i < melysegek.Length - 1; ++i) {
    var elsoMelyseg = melysegek[i];
    var masodikMelyseg = melysegek[i + 1];

    if(elsoMelyseg == 0 && masodikMelyseg != 0) {
        godorKezdoZaroIndexek.Add(i + 1);
    }else if(elsoMelyseg != 0 && masodikMelyseg == 0) {
        godrok.Add(GodrotMasol(godorKezdoZaroIndexek, i, melysegek));
        godorKezdoZaroIndexek.Add(i + 1);
    }
}

using var output = new StreamWriter("godrok.txt");

foreach(var godor in godrok) {
    foreach(var melyseg in godor) {
        output.Write(melyseg + " ");
    }

    output.WriteLine();
}

Console.WriteLine($"5. Feladat: Gödrök száma: {godrok.Count}");

if(melysegABekertHelyen == 0) {
    Console.WriteLine("6. Feladat: Az adott helyen nincs gödör");
}else{
    var bekertGodorIndex = 0;
    for(var i = 0; i < godorKezdoZaroIndexek.Count; i += 2) {
        if(bekertTavolsagIndexe >= godorKezdoZaroIndexek[i] && bekertTavolsagIndexe <= godorKezdoZaroIndexek[i + 1]) {
            bekertGodorIndex = i / 2;
        }
    }

    var bekertHelyKezdoGodorTavolsag = godorKezdoZaroIndexek[bekertGodorIndex] + 1;
    var bekertHelyZaroGodorTavolsag = godorKezdoZaroIndexek[bekertGodorIndex + 1];

    Console.WriteLine($"    a) Gödör kezdete: {bekertHelyKezdoGodorTavolsag}m, vége: {bekertHelyZaroGodorTavolsag}m");

    var aGodor = godrok[bekertGodorIndex];
    var legmelyebbPontIndex = 0;

    for(var i = 0; i < aGodor.Length; ++i) {
        if(aGodor[i] > aGodor[legmelyebbPontIndex]) {
            legmelyebbPontIndex = i;
        }
    }

    var balSzeltolLegnagyobbigNo = true;
    for(var i = 0; i < legmelyebbPontIndex - 1; ++i) {
        if(aGodor[i] > aGodor[i + 1]) {
            balSzeltolLegnagyobbigNo = false;
            break;
        }
    }

    var legnagyobbtolJobbSzeligCsokken = true;
    for(var i = legmelyebbPontIndex + 1; i < aGodor.Length - 1; ++i) {
        if(aGodor[i] > aGodor[i + 1]) {
            legnagyobbtolJobbSzeligCsokken = false;
            break;
        }
    }

    Console.WriteLine("    b) " + (balSzeltolLegnagyobbigNo && legnagyobbtolJobbSzeligCsokken ? "Folyamatosan Mélyül" : "Nem mélyül folyamatosan"));
    Console.WriteLine($"    c) Legnagyobb méység: {aGodor[legmelyebbPontIndex]}m");

    var godorMelysegekSum = 0;
    foreach(var melyseg in aGodor) {
        godorMelysegekSum += melyseg;
    }

    var terfogat = godorMelysegekSum * 10;
    var vizmennyiseg = terfogat - 10 * (bekertHelyZaroGodorTavolsag - bekertHelyKezdoGodorTavolsag + 1);

    Console.WriteLine($"    d) Térfogat: {terfogat}m^3");
    Console.WriteLine($"    e) Vízmennyiség: {vizmennyiseg}m^3");
}

static int[] GodrotMasol(List<int> godorKezdoZaroIndexek, int i, int[] melysegek) {
    var kezdoIndex = godorKezdoZaroIndexek[godorKezdoZaroIndexek.Count - 1];
    var zaroIndex = i + 1;
    var godor = new int[zaroIndex - kezdoIndex];

    Array.Copy(melysegek, kezdoIndex, godor, 0, godor.Length);

    return godor;
}