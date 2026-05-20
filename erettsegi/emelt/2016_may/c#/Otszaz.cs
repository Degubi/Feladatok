using System;
using System.Collections.Generic;
using System.IO;

var lines = File.ReadAllLines("penztar.txt");
var vasarlasok = new List<Dictionary<string, int>>();
var itemBuffer = new List<string>();

foreach(var line in lines) {
    if(line == "F") {
        var itemFreqMap = new Dictionary<string, int>();

        foreach(var item in itemBuffer) {
            itemFreqMap.Add(item, itemFreqMap.GetValueOrDefault(item, 0) + 1);
        }

        vasarlasok.Add(itemFreqMap);
        itemBuffer.Clear();
    }else{
        itemBuffer.Add(line);
    }
}

Console.WriteLine($"2. Feladat: Vásárlások száma: {vasarlasok.Count}");
Console.WriteLine($"3. Feladat: Elsö vásárlásnál vett dolgok száma: {vasarlasok[0].Count}");

Console.WriteLine("4. Feladat:");
Console.WriteLine("Írj be 1 sorszámot");
var bekertSorszam = int.Parse(Console.ReadLine());

Console.WriteLine("Írj be 1 árut");
var bekertAru = Console.ReadLine();

Console.WriteLine("Írj be 1 mennyiséget");
var bekertDbszam = int.Parse(Console.ReadLine());

Console.WriteLine("5. Feladat:");

var bekertAruOsszDbSzam = 0;
var bekertAruUtolsoVasarlasIndex = 0;

for(var k = 0; k < vasarlasok.Count; ++k) {
    foreach(var item in vasarlasok[k].Keys) {
        if(item == bekertAru) {
            ++bekertAruOsszDbSzam;
            bekertAruUtolsoVasarlasIndex = k;

            if(bekertAruOsszDbSzam == 1) {
                Console.WriteLine("Először a " + (k + 1) + ". vásárlásnál vettek " + bekertAru + "-t");
            }
        }
    }
}

Console.WriteLine($"Utoljára a {bekertAruUtolsoVasarlasIndex + 1}. vásárlásnál vettek {bekertAru}-t");
Console.WriteLine($"Összesen {bekertAruOsszDbSzam}-szor vettek {bekertAru}-t");
Console.WriteLine($"6. Feladat: {bekertDbszam} db esetén a fizetendő: {ertek(bekertDbszam)}");
Console.WriteLine($"7. Feladat: A {bekertSorszam}. vásárláskor vásárolt dolgok:");

foreach(var (item, db) in vasarlasok[bekertSorszam - 1]) {
    Console.WriteLine($"{item}-ből: {db} db");
}

using var output = new StreamWriter("osszeg.txt");

for(int k = 0; k < vasarlasok.Count; ++k) {
    var printMount = 0;

    foreach(var dbSzam in vasarlasok[k].Values) {
        printMount += ertek(dbSzam);
    }
    output.WriteLine((k + 1).ToString() + ":" + printMount);
}

int ertek(int dbSzam) => dbSzam == 1 ? 500 : dbSzam == 2 ? 950 : 1350 + (400 * (dbSzam - 3));