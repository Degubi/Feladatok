using System;
using System.Collections.Generic;
using System.IO;

var feladvanyok = new List<Feladvany>();
var lines = File.ReadAllLines("feladvanyok.txt");

foreach(var line in lines) {
    feladvanyok.Add(new Feladvany(line));
}

Console.WriteLine($"3. Feladat: Feladványok száma: {feladvanyok.Count}");
Console.WriteLine("4. Feladat:");

var bekertMeret = MeretetBeker();
var bekertMeretuek = new List<Feladvany>();

foreach(var feladvany in feladvanyok) {
    if(feladvany.meret == bekertMeret) {
        bekertMeretuek.Add(feladvany);
    }
}

var kivalasztott = bekertMeretuek[new Random().Next(bekertMeretuek.Count)];

Console.WriteLine($"Ebből a méretből {bekertMeretuek.Count} db van");
Console.WriteLine($"5. Feladat: {kivalasztott.feladvanyTeljes}");

var kitoltottCellakSzama = 0;
for(var i = 0; i < kivalasztott.feladvanyTeljes.Length; ++i) {
    if(kivalasztott.feladvanyTeljes[i] != '0') {
        ++kitoltottCellakSzama;
    }
}

Console.WriteLine("6. Feladat: A feladvány kitöltöttsége: " + (int) ((float) kitoltottCellakSzama / kivalasztott.feladvanyTeljes.Length * 100) + "%");
Console.WriteLine("7. Feladat:");
kivalasztott.Kirajzol();

var bekertMeretuekFeladvanyai = new List<String>();
foreach(var feladvany in bekertMeretuek) {
    bekertMeretuekFeladvanyai.Add(feladvany.feladvanyTeljes);
}

File.WriteAllLines($"sudoku{bekertMeret}.txt", bekertMeretuekFeladvanyai);


int MeretetBeker() {
    while(true) {
        Console.WriteLine("Kérem 1 feladvány méretét!");
        var bekertMeret = int.Parse(Console.ReadLine());

        if(bekertMeret >= 4 && bekertMeret <= 9) {
            return bekertMeret;
        }
    }
}