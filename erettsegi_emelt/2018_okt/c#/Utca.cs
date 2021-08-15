using System;
using System.Collections.Generic;
using System.IO;

var sorok = File.ReadAllLines("kerites.txt");
var telkek = new List<Telek>();

var parosHazszam = 2;
var paratlanHazszam = 1;

foreach(var sor in sorok) {
    var split = sor.Split(' ');
    var parose = int.Parse(split[0]) == 0;

    telkek.Add(new Telek(parose, parose ? parosHazszam : paratlanHazszam, int.Parse(split[1]), split[2][0]));

    if(parose) {
        parosHazszam += 2;
    }else{
        paratlanHazszam += 2;
    }
}

Console.WriteLine("2. Feladat:");
Console.WriteLine($"Eladott telkek száma: {telkek.Count}");

var utolsoTelek = telkek[telkek.Count - 1];

Console.WriteLine("3. Feladat:");
Console.WriteLine($"Az utolsó telek: {(utolsoTelek.parosE ? "Páros" : "Páratlan")}");
Console.WriteLine($"Az utolsó telek házszáma: {utolsoTelek.hazszam}");
Console.WriteLine("4. Feladat:");

for(var i = 0; i < telkek.Count - 1; ++i) {
    var telek = telkek[i];
    var kovetkezo = telkek[i + 1];

    if (!telek.parosE && telek.keritesSzine != ':' && telek.keritesSzine != '#' && !kovetkezo.parosE && kovetkezo.keritesSzine == telek.keritesSzine) {
        Console.WriteLine($"Talált házszám: {telek.hazszam}");
        break;
    }
}

Console.WriteLine("5. Feladat:");
Console.WriteLine("Írd be 1 telek számát!");
var beolvasottTelekSzam = int.Parse(Console.ReadLine());

for(int i = 0; i < telkek.Count; ++i) {
    var jelenlegiTelek = telkek[i];

    if(jelenlegiTelek.hazszam == beolvasottTelekSzam) {
        Console.WriteLine("Kerítés színe: " + (jelenlegiTelek.keritesSzine == ':' ? "Nem készült el" : jelenlegiTelek.keritesSzine == '#' ? "Festetlen" : jelenlegiTelek.keritesSzine.ToString()));

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
        for(int k = 0; k < telek.szelesseg; ++k) {
            if(k == 0) {
                output.Write(telek.hazszam);

                if(telek.hazszam > 9) {
                    ++k;
                }

                if(telek.hazszam > 99) {
                    ++k;
                }
            }else{
                output.Write(' ');
            }
        }
    }
}