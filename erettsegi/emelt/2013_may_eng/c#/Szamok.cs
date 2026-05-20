using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

var rand = new Random();
var feladatok = new List<Feladat>();
var lines = File.ReadAllLines("felszam.txt");

for(var k = 0; k < lines.Length - 1; k += 2) {
    var split = lines[k + 1].Split(' ');
    feladatok.Add(new Feladat(lines[k], int.Parse(split[0]), int.Parse(split[1]), split[2]));
}

Console.WriteLine($"{feladatok.Count} Feladat van a fájlban!");

var temakorok = new List<String>();
var matekCounters = new int[4];

foreach(var all in feladatok) {
    if(!temakorok.Contains(all.temakor)) {
        temakorok.Add(all.temakor);
    }

    if(all.temakor.Equals("matematika")) {
        ++matekCounters[0];

        if(all.pont == 1) {
            ++matekCounters[1];
        }else if(all.pont == 2) {
            ++matekCounters[2];
        }else {
            ++matekCounters[3];
        }
    }
}

Console.WriteLine($"Az adatfajlban {matekCounters[0]} matematika feladat van,\n1 pontot er: " +
                  $"{matekCounters[1]} feladat, 2 pontot er {matekCounters[2]} feladat, 3 pontot er {matekCounters[3]} feladat. ");

feladatok = feladatok.OrderBy(k => k.valasz).ToList();

Console.WriteLine($"A legkisebb válaszú feladat: {feladatok[0].valasz}, a legnagyobb: {feladatok[feladatok.Count - 1].valasz}");
Console.WriteLine($"Előforduló témakörök: {temakorok}");

Console.WriteLine("Írj be 1 témakört!");
var readCat = Console.ReadLine();
var categorizált = new List<Feladat>();

foreach(var all in feladatok) {
    if(all.temakor.Equals(readCat)) {
        categorizált.Add(all);
    }
}
var chosen = categorizált[rand.Next(categorizált.Count)];

Console.WriteLine(chosen.kerdes);

if(int.Parse(Console.ReadLine()) == chosen.valasz) {
    Console.WriteLine("Kapott pontszám: " + chosen.pont);
}else{
    Console.WriteLine("Rossz válasz, 0 pont...\nA helyes válasz: " + chosen.valasz);
}

var generalt = new List<Feladat>();
for(var k = 0; k < 10; ++k) {
    var randomFeladat = feladatok[rand.Next(feladatok.Count)];

    if(generalt.Contains(randomFeladat)) {
        --k;
    }else{
        generalt.Add(randomFeladat);
    }
}

var osszpont = 0;
using var output = new StreamWriter("tesztfel.txt");

foreach(var toPrint in generalt) {
    osszpont += toPrint.pont;
    output.WriteLine(toPrint.pont + " " + toPrint.kerdes);
}
output.Write(osszpont);