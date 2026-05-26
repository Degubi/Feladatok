var urtartalmak = File.ReadAllText("uvegek.txt").Trim().Split(", ")
                      .Select(int.Parse)
                      .ToArray();

Console.Write("2. Feladat: Mari néni lekvárja (dl): ");

var bekertUrtartalom = int.Parse(Console.ReadLine()!);
var legnagyobbUvegI = Enumerable.Range(0, urtartalmak.Length)
                                .MaxBy(i => urtartalmak[i]);

Console.WriteLine($"3. Feladat: A legnagyobb üveg: {urtartalmak[legnagyobbUvegI]} dl és {legnagyobbUvegI + 1} . a sorban");

var elegVanE = bekertUrtartalom <= urtartalmak.Sum();

Console.WriteLine("4. Feladat: " + (elegVanE ? "Elegendő üveg volt" : "Maradt lekvár"));
