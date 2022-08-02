using System;
using System.Collections.Generic;
using System.IO;

var autokLines = File.ReadAllLines("autok.csv", System.Text.Encoding.Latin1);
var autok = new List<Auto>();

for(var i = 1; i < autokLines.Length; ++i) {
    autok.Add(new Auto(autokLines[i]));
}

Console.WriteLine($"2. Feladat: Hirdetesek szama: {autok.Count}");

var bpToMiskolc = 0;
foreach(var auto in autok) {
    if(auto.indulas == "Budapest" && auto.cel == "Miskolc") {
        bpToMiskolc += auto.ferohely;
    }
}

Console.WriteLine($"3. Feladat: BP to Miskolc hely: {bpToMiskolc}");

var ferohelyStat = new Dictionary<string, int>();
foreach(var auto in autok) {
    var key = auto.indulas + '-' + auto.cel;

    ferohelyStat[key] = ferohelyStat.GetValueOrDefault(key, 0) + auto.ferohely;
}

var maxFerohelyEntry = ferohelyStat.GetEnumerator().Current;
foreach(var e in ferohelyStat) {
    if(e.Value > maxFerohelyEntry.Value) {
        maxFerohelyEntry = e;
    }
}

Console.WriteLine($"4. Feladat: {maxFerohelyEntry.Key}: {maxFerohelyEntry.Value} hely");
Console.WriteLine("5. Feladat");

var igenyekLines = File.ReadAllLines("igenyek.csv", System.Text.Encoding.Latin1);
var igenyek = new List<Igeny>();

for(var i = 1; i < igenyekLines.Length; ++i) {
    igenyek.Add(new Igeny(igenyekLines[i]));
}

var fileba = new List<String>();
foreach(var igeny in igenyek) {
    var autoIgenyre = autotKeresIgenyre(igeny, autok);

    if(autoIgenyre != null) {
        fileba.Add($"{igeny.azonosito}: Rendszam: {autoIgenyre.rendszam}, Telefonszam: {autoIgenyre.telefonszam}");

        Console.WriteLine($"{igeny.azonosito} -> {autoIgenyre.rendszam}");
    }else{
        fileba.Add($"{igeny.azonosito}: Sajnos nem sikerült autót találni");
    }
}

File.WriteAllLines("utazasuzenetek.txt", fileba);


Auto autotKeresIgenyre(Igeny igeny, List<Auto> autok) {
    foreach(var auto in autok) {
        if(auto.indulas == igeny.indulas && auto.cel == igeny.cel && auto.ferohely >= igeny.szemelyek) {
            return auto;
        }
    }

    return null;
}