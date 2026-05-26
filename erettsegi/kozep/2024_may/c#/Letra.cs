var dobasok = File.ReadAllText("dobasok.txt").Trim().Split(", ");
var lepesek = new List<LepesAllapot>();

foreach(var dobas in dobasok) {
    var dobasErtek = int.Parse(dobas);
    var elozoLepes = lepesek.Count == 0 ? new LepesAllapot(0, 0, false) : lepesek.Last();

    lepesek.Add(LepestAlkalmaz(elozoLepes, dobasErtek));
}

var kiirando = string.Join(' ', lepesek.Select(k => k.Ertek.ToString()));

Console.WriteLine($"2. Feladat: {kiirando}");

var visszalepesekSzama = lepesek.Count(k => k.Visszalepes);

Console.WriteLine($"3. Feladat: A játék során {visszalepesekSzama} alkalommal lépett létrára");
Console.WriteLine("4. Feladat: " + (lepesek[lepesek.Count - 1].Ertek > 45 ? "A játékot befejezte" : "A játékot abbahagyta"));

static LepesAllapot LepestAlkalmaz(LepesAllapot elozoAllapot, int dobottErtek) {
    var osszeg = elozoAllapot.Ertek + dobottErtek;
    var visszalepes = osszeg % 10 == 0;

    return new LepesAllapot(elozoAllapot.DobasI + 1, visszalepes ? osszeg - 3 : osszeg, visszalepes);
}

record LepesAllapot(int DobasI, int Ertek, bool Visszalepes) {}
