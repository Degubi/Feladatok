using System;
using System.Collections.Generic;
using System.IO;

var telkek = new List<Telek>();
var hazszamSzamlalok = new int[] { 2, 1 };

foreach(var sor in File.ReadAllLines("kerites.txt")) {
    var split = sor.Split(' ');
    var hazszamSzamlaloIndex = int.Parse(split[0]);

    telkek.Add(new Telek(hazszamSzamlalok[hazszamSzamlaloIndex], int.Parse(split[1]), split[2][0]));
    hazszamSzamlalok[hazszamSzamlaloIndex] += 2;
}

var utolsoTelek = telkek[telkek.Count - 1];

Console.WriteLine($"2. Feladat: Eladott telkek száma: {telkek.Count}");
Console.WriteLine($"3. Feladat: Az utolsó telek: {(utolsoTelek.parosE ? "Páros" : "Páratlan")}, házszáma: {utolsoTelek.hazszam}");

for(var i = 0; i < telkek.Count - 1; ++i) {
    var telek = telkek[i];
    var kovetkezo = telkek[i + 1];

    if (!telek.parosE && telek.keritesSzine != ':' && telek.keritesSzine != '#' && !kovetkezo.parosE && kovetkezo.keritesSzine == telek.keritesSzine) {
        Console.WriteLine($"4. Feladat: Talált házszám: {telek.hazszam}");
        break;
    }
}

Console.WriteLine("5. Feladat: Írd be 1 telek számát!");
var beolvasottTelekSzam = int.Parse(Console.ReadLine());

for(int i = 0; i < telkek.Count; ++i) {
    var jelenlegiTelek = telkek[i];

    if(jelenlegiTelek.hazszam == beolvasottTelekSzam) {
        Console.WriteLine("Kerítés színe: " + (jelenlegiTelek.keritesSzine == ':' ? "Nem készült el" :
                                               jelenlegiTelek.keritesSzine == '#' ? "Festetlen" : jelenlegiTelek.keritesSzine.ToString()));
        var balSzomszed = telkek[i - 1];
        var jobbSzomszed = telkek[i + 1];

        for(char generalt = 'A'; ; ++generalt) {
            if(balSzomszed.keritesSzine != generalt && jobbSzomszed.keritesSzine != generalt && jelenlegiTelek.keritesSzine != generalt) {
                Console.WriteLine($"Az új szín lehet: {generalt}");
                break;
            }
        }

        break;
    }
}

using var output = new StreamWriter("utcakep.txt");

foreach(var telek in telkek) {
    if(!telek.parosE) {
        for(int k = 0; k < telek.szelesseg; ++k) {
            output.Write(telek.keritesSzine);
        }
    }
}

output.WriteLine();

foreach(var telek in telkek) {
    if (!telek.parosE) {
        var hazszamString = telek.hazszam.ToString();

        output.Write(hazszamString + new String(' ', telek.szelesseg - hazszamString.Length));
    }
}