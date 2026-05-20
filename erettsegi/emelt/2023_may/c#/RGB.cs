using System;
using System.IO;


var lines = File.ReadAllLines("kep.txt");
var pixelek = new Color[360, 640];

for(var oszlop = 0; oszlop < 360; ++oszlop) {
    var lineSplit = lines[oszlop].Split(' ');
    var splitIndex = 0;

    for(var sor = 0; sor < 640; ++sor) {
        pixelek[oszlop, sor] = new Color(int.Parse(lineSplit[splitIndex]), int.Parse(lineSplit[splitIndex + 1]), int.Parse(lineSplit[splitIndex + 2]));
        splitIndex += 3;
    }
}

Console.WriteLine("2. Feladat: Kérem egy képpont adatait!");
Console.WriteLine("Sor:");

var bekertSorIndex = int.Parse(Console.ReadLine()) - 1;

Console.WriteLine("Oszlop:");

var bekertOszlopIndex = int.Parse(Console.ReadLine()) - 1;

Console.WriteLine($"Képpont színe: {pixelek[bekertSorIndex, bekertOszlopIndex]}");

var vilagosKeppontokSzama = 0;
var legsotetebbKeppontErteke = 255 * 3;

foreach(var oszlop in pixelek) {
    var szinSum = oszlop.Sum();

    if(szinSum > 600) {
        ++vilagosKeppontokSzama;
    }

    if(szinSum < legsotetebbKeppontErteke) {
        legsotetebbKeppontErteke = szinSum;
    }
}

Console.WriteLine($"3. Feladat: Világos képpontok száma: {vilagosKeppontokSzama}");
Console.WriteLine($"4. Feladat: Legsötétebb pont RGB összege: {legsotetebbKeppontErteke}");

foreach(var oszlop in pixelek) {
    if(oszlop.Sum() == legsotetebbKeppontErteke) {
        Console.WriteLine(oszlop);
    }
}

Console.WriteLine("6. Feladat:");

for(var i = 0; i < 640; ++i) {
    if(hatar(i, 10, pixelek)) {
        Console.WriteLine($"Felhő legfelső sora: {i + 1}");
        break;
    }
}

for(var i = 639; i >= 0; --i) {
    if(hatar(i, 10, pixelek)) {
        Console.WriteLine($"Felhő legalsó sora: {i + 1}");
        break;
    }
}

static bool hatar(int sorSzam, int elteres, Color[,] pixelek) {
    for(var i = 0; i < 639; ++i) {
        if(Math.Abs(pixelek[sorSzam, i].blue - pixelek[sorSzam, i + 1].blue) > elteres) {
            return true;
        }
    }

    return false;
}