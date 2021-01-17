using System;
using System.Collections.Generic;
using System.IO;
using System.Text;

public class Operatorok {

    public static void Main() {
        var kifejezesek = new List<Kifejezes>();
        var lines = File.ReadAllLines("kifejezesek.txt", Encoding.GetEncoding("ISO-8859-1"));

        foreach(var line in lines) {
            kifejezesek.Add(new Kifejezes(line));
        }

        Console.WriteLine($"2. Feladat: Kifejezések száma: {kifejezesek.Count}");

        var operatoronkentiDbSzam = new Dictionary<string, int>();
        foreach(var kifejezes in kifejezesek) {
            operatoronkentiDbSzam.Add(kifejezes.Operator, operatoronkentiDbSzam.GetValueOrDefault(kifejezes.Operator) + 1);
        }

        Console.WriteLine($"2. Feladat: Maradékos osztások száma: {operatoronkentiDbSzam["mod"]}");

        var vanETizzelOszthatoOperandusu = false;
        foreach(var kifejezes in kifejezesek) {
            if(kifejezes.ElsoOperandus % 10 == 0 && kifejezes.MasodikOperandus % 10 == 0) {
                vanETizzelOszthatoOperandusu = true;
                break;
            }
        }

        Console.WriteLine($"4. Feladat: {(vanETizzelOszthatoOperandusu ? "Van" : "Nincs")} ilyen kifejezés");
        Console.WriteLine("5. Feladat: \n" +
                           "    'mod' -> " + operatoronkentiDbSzam["mod"] + " db\n" +
                           "      '/' -> " + operatoronkentiDbSzam["/"] + " db\n" +
                           "    'div' -> " + operatoronkentiDbSzam["div"] + " db\n" +
                           "      '-' -> " + operatoronkentiDbSzam["-"] + " db\n" +
                           "      '*' -> " + operatoronkentiDbSzam["*"] + " db\n" +
                           "      '+' -> " + operatoronkentiDbSzam["+"] + " db");

        while(true) {
            Console.WriteLine("Kérek egy kifejezést");
            var bekert = Console.ReadLine();

            if(bekert == "vége") {
                break;
            }

            Console.WriteLine($"{bekert} = {new Kifejezes(bekert).kiertekel()}");
        }

        var kiertekeltSorok = new List<string>();
        foreach(var kifejezes in kifejezesek) {
            kiertekeltSorok.Add(kifejezes.kiertekel());
        }

        File.WriteAllLines("eredmenyek.txt", kiertekeltSorok);
    }
}