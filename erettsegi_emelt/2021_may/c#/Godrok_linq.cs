using System;
using System.IO;
using System.Linq;

var melysegek = File.ReadLines("melyseg.txt")
                    .Select(int.Parse)
                    .ToArray();

Console.WriteLine($"1. Feladat: {melysegek.Length}");
Console.WriteLine("2. Feladat: Írjon be egy távolságértéket!");

var bekertTavolsagIndexe = int.Parse(Console.ReadLine()) - 1;
var melysegABekertHelyen = melysegek[bekertTavolsagIndexe];

Console.WriteLine($"A felszín {melysegABekertHelyen}m mélyen van");

var erintetlenFeluletSzam = melysegek.Where(k => k == 0).Count();

Console.WriteLine($"3. Feladat: Érintetlen felszín: {((float) erintetlenFeluletSzam / melysegek.Length * 100).ToString("0.00")}");

var godorKezdoZaroIndexek = Enumerable.Range(0, melysegek.Length - 1)
                                      .Where(i => (melysegek[i] == 0 && melysegek[i + 1] != 0) || (melysegek[i] != 0 && melysegek[i + 1] == 0))
                                      .Select(k => k + 1)
                                      .ToArray();

var godrok = Enumerable.Range(0, godorKezdoZaroIndexek.Length)
                       .Where(i => i % 2 == 0)
                       .Select(i => GodrotMasol(godorKezdoZaroIndexek, i, melysegek))
                       .ToArray();

File.WriteAllLines("godrok.txt", godrok.Select(k => string.Join(' ', k)));

Console.WriteLine($"5. Feladat: Gödrök száma: {godrok.Length}");

if(melysegABekertHelyen == 0) {
    Console.WriteLine("6. Feladat: Az adott helyen nincs gödör");
}else{
    var bekertGodorIndex = Enumerable.Range(0, godorKezdoZaroIndexek.Length)
                                     .Where(i => i % 2 == 0)
                                     .Where(i => bekertTavolsagIndexe >= godorKezdoZaroIndexek[i] && bekertTavolsagIndexe <= godorKezdoZaroIndexek[i + 1])
                                     .First() / 2;

    var bekertHelyKezdoGodorTavolsag = godorKezdoZaroIndexek[bekertGodorIndex] + 1;
    var bekertHelyZaroGodorTavolsag = godorKezdoZaroIndexek[bekertGodorIndex + 1];

    Console.WriteLine($"    a) Gödör kezdete: {bekertHelyKezdoGodorTavolsag}m, vége: {bekertHelyZaroGodorTavolsag}m");

    var aGodor = godrok[bekertGodorIndex];
    var legmelyebbPontErtek = Enumerable.Range(0, aGodor.Length)
                                        .Max(i => aGodor[i]);

    var legmelyebbPontIndex = Enumerable.Range(0, aGodor.Length)
                                        .First(i => aGodor[i] == legmelyebbPontErtek);

    var legnagyobbMelyseg = aGodor[legmelyebbPontIndex];

    var balSzeltolLegnagyobbigNo = Enumerable.Range(0, legmelyebbPontIndex - 1)
                                             .All(i => aGodor[i] <= aGodor[i + 1]);

    var legnagyobbtolJobbSzeligCsokken = Enumerable.Range(legmelyebbPontIndex + 1, aGodor.Length - 1)
                                                   .All(i => aGodor[i] >= aGodor[i + 1]);

    Console.WriteLine("    b) " + (balSzeltolLegnagyobbigNo && legnagyobbtolJobbSzeligCsokken ? "Folyamatosan Mélyül" : "Nem mélyül folyamatosan"));
    Console.WriteLine($"    c) Legnagyobb méység: {legnagyobbMelyseg}m");

    var terfogat = aGodor.Sum() * 10;
    var vizmennyiseg = terfogat - 10 * (bekertHelyZaroGodorTavolsag - bekertHelyKezdoGodorTavolsag + 1);

    Console.WriteLine($"    d) Térfogat: {terfogat}m^3");
    Console.WriteLine($"    e) Vízmennyiség: {vizmennyiseg}m^3");
}

static int[] GodrotMasol(int[] godorKezdoZaroIndexek, int i, int[] melysegek) {
    var kezdoIndex = godorKezdoZaroIndexek[i];
    var zaroIndex = godorKezdoZaroIndexek[i + 1];
    var godor = new int[zaroIndex - kezdoIndex];

    Array.Copy(melysegek, kezdoIndex, godor, 0, godor.Length);

    return godor;
}