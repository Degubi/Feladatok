using System;
using System.IO;
using System.Linq;

var pixelek2D = File.ReadLines("kep.txt")
                    .Select(kepSortBeolvas)
                    .ToArray();

Console.WriteLine("2. Feladat: Kérem egy képpont adatait!");
Console.WriteLine("Sor:");

var bekertSorIndex = int.Parse(Console.ReadLine()) - 1;

Console.WriteLine("Oszlop:");

var bekertOszlopIndex = int.Parse(Console.ReadLine()) - 1;

Console.WriteLine($"Képpont színe: {pixelek2D[bekertSorIndex][bekertOszlopIndex]}");

var pixelek1D = pixelek2D.SelectMany(k => k).ToArray();
var vilagosKeppontokSzama = pixelek1D.Count(k => k.Sum() > 600);
var legsotetebbKeppontErtek = pixelek1D.Min(k => k.Sum());

Console.WriteLine($"3. Feladat: Világos képpontok száma: {vilagosKeppontokSzama}");
Console.WriteLine($"4. Feladat: Legsötétebb pont RGB összege: {legsotetebbKeppontErtek}");

pixelek1D.Where(k => k.Sum() == legsotetebbKeppontErtek)
         .ToList()
         .ForEach(k => Console.WriteLine(k));

var hatarOszlopIndexek = Enumerable.Range(0, pixelek2D.Length)
                                   .Where(i => Hatar(i, 10, pixelek2D))
                                   .ToArray();

Console.WriteLine($"6. Feladat: Felhő legfelső sora: {hatarOszlopIndexek[0] + 1}, utolsó sora: {hatarOszlopIndexek[hatarOszlopIndexek.Length - 1] + 1}");


static Color[] kepSortBeolvas(string line) {
    var split = line.Split(' ');

    return Enumerable.Range(0, split.Length)
                     .Where(i => i % 3 == 0)
                     .Select(i => new Color(int.Parse(split[i]), int.Parse(split[i + 1]), int.Parse(split[i + 2])))
                     .ToArray();
}

static bool Hatar(int sorSzam, int elteres, Color[][] pixelek2D) {
    var sor = pixelek2D[sorSzam];

    return Enumerable.Range(0, sor.Length - 1)
                     .Any(i => Math.Abs(sor[i].blue - sor[i + 1].blue) > elteres);
}