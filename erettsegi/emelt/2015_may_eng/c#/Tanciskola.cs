using System;
using System.Collections.Generic;
using System.IO;

var lines = File.ReadAllLines("tancrend.txt");
var tancok = new List<Tanc>();

for(var i = 0; i < lines.Length; i += 3) {
    tancok.Add(new Tanc(lines, i));
}

Console.WriteLine($"Első tánc neve: {tancok[0].category}, az utolsóé: {tancok[tancok.Count - 1].category}");

var szambaCounter = 0;
foreach(var dansz in tancok) {
    if(dansz.category.Equals("samba")) {
        ++szambaCounter;
    }
}

Console.WriteLine($"Összesen {szambaCounter}-an szambásztak");

var viliCat = new List<String>();
foreach(var dansz in tancok) {
    if(dansz.woman == "Vilma" && !viliCat.Contains(dansz.category)) {
        viliCat.Add(dansz.category);
    }
}

Console.WriteLine("Vilma által táncolt kategóriák: " + string.Join(", ", viliCat));
Console.WriteLine("Írj be 1 kategóriát!");

var readCat = Console.ReadLine();
var hasPrinted = false;

foreach(var dansz in tancok) {
    if(dansz.woman == "Vilma" && dansz.category == readCat) {
        Console.WriteLine("Vilma a " + readCat + " kategóriában " + dansz.man + "-val táncolt");
        hasPrinted = true;
    }
}

if(!hasPrinted) {
    Console.WriteLine($"Vilma a {readCat} kategóriában nem táncolt");
}

var lanyokToTancalkalmak = new Dictionary<string, int>();
var fiukToTancalkalmak = new Dictionary<string, int>();

foreach(var dans in tancok) {
    lanyokToTancalkalmak[dans.woman] = lanyokToTancalkalmak.GetValueOrDefault(dans.woman, 0) + 1;
    fiukToTancalkalmak[dans.man] = fiukToTancalkalmak.GetValueOrDefault(dans.man, 0) + 1;
}

File.WriteAllText("szereplok.txt", "Lányok: " + string.Join(", ", lanyokToTancalkalmak.Keys) +
                                   "\nFiúk: " + string.Join(", ", fiukToTancalkalmak.Keys));
var grillMax = 0;
foreach(var v in lanyokToTancalkalmak.Values) {
    if(v > grillMax) {
        grillMax = v;
    }
}

Console.Write("A legtöbbet táncolt lányok: ");
foreach(var e in lanyokToTancalkalmak) {
    if(e.Value == grillMax) {
        Console.Write(e.Key + ' ');
    }
}

var boiMax = 0;
foreach(var v in fiukToTancalkalmak.Values) {
    if(v > boiMax) {
        boiMax = v;
    }
}

Console.Write("\nA legtöbbet táncolt fiúk: ");
foreach(var e in fiukToTancalkalmak) {
    if(e.Value == boiMax) {
        Console.Write(e.Key + ' ');
    }
}