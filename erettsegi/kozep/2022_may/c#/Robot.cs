Console.Write("Kérem a robot parancsait: ");

var parancsok = Console.ReadLine()!;

parancsok.GroupBy(k => k)
         .ToDictionary(k => k.Key, k => k.Count())
         .ToList()
         .ForEach(k => Console.WriteLine($"{k.Key} betűk száma: {k.Value}"));

var (vegsoX, vegsoY) = parancsok.Aggregate((0, 0), (result, k) => parancsotAlkalmaz(result, k));
var xParancsBetu = vegsoX > 0 ? "K" : "N";
var egyszerusitettXParancsok = string.Join("", Enumerable.Repeat(xParancsBetu, Math.Abs(vegsoX)));
var yParancsBetu = vegsoY > 0 ? "E" : "D";
var egyszerusitettYParancsok = string.Join("", Enumerable.Repeat(yParancsBetu, Math.Abs(vegsoY)));

Console.WriteLine($"Egy legrövidebb út parancsszava: {egyszerusitettXParancsok}{egyszerusitettYParancsok}");

static (int, int) parancsotAlkalmaz((int x, int y) elozo, int parancs) {
    return (
        parancs == 'N' ? elozo.x - 1 : parancs == 'K' ? elozo.x + 1 : elozo.x,
        parancs == 'E' ? elozo.y + 1 : parancs == 'D' ? elozo.y - 1 : elozo.y
    );
}
