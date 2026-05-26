Console.Write("Hány alkalommal legyen feldobás? ");

var random = new Random();
var alkalmakSzama = int.Parse(Console.ReadLine()!);
var korok = Generate(() => KortGeneral(random))
           .Distinct()
           .Take(alkalmakSzama)
           .ToList();

korok.ForEach(k => Console.WriteLine($"Dobás: {k.Elso} + {k.Masodik} + {k.Harmadik} = {k.Osszeg}, nyert: " + (k.Osszeg < 10 ? "Anni" : "Panni")));

var statok = korok.GroupBy(k => k.Osszeg < 10)
                  .ToDictionary(k => k.Key, k => k.Count());

Console.WriteLine($"A játékok során {statok.GetValueOrDefault(true, 0)} alkalommal nyert Anni, {statok.GetValueOrDefault(false, 0)} alkalommal pedig Panni");

static Kor KortGeneral(Random random) {
    var elso = random.Next(1, 6);
    var masodik = random.Next(1, 6);
    var harmadik = random.Next(1, 6);

    return new Kor(elso, masodik, harmadik, elso + masodik + harmadik);
}

static IEnumerable<T> Generate<T>(Func<T> generator) {
    while(true) yield return generator.Invoke();
}

record Kor(int Elso, int Masodik, int Harmadik, int Osszeg) {}
