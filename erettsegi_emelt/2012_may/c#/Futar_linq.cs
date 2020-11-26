using System;
using System.Collections.Generic;
using System.Linq;
using System.IO;

public static class Futar_linq {
    public static void ForEach<T>(this IEnumerable<T> elements, Action<T> action) => Array.ForEach(elements.ToArray(), action);

    public static void Main(string[] args){
        var fuvarLista = File.ReadLines("tavok.txt")
                             .Select(k => new Fuvar(k))
                             .ToArray();

        Console.WriteLine("2. Feladat\nA hét legelső útja km-ben: " + fuvarLista.First().tavolsag + " km");
        Console.WriteLine("3. Feladat\nA hét utolsó útja km-ben: " + fuvarLista.Last().tavolsag + " km");

        Enumerable.Range(1, 7).ForEach(day => {
            if(!fuvarLista.Select(k => k.nap)
                            .Distinct()
                            .Any(k => k == day)) Console.WriteLine("A " + day + ". nap szabadnap volt");});
    
        Console.WriteLine("5. Feladat\nLegtöbb fuvarú nap: " + fuvarLista.Where(k => k.tavolsag == fuvarLista.Max(l => l.tavolsag)).First().nap);
    
        Console.WriteLine("6. Feladat");
        Enumerable.Range(1, 7).ForEach(day => Console.WriteLine("A " + day + ". nap távja: " + 
                    fuvarLista.Where(k => k.nap == day)
                                .Select(k => k.tavolsag)
                                .Sum()));
    
        Console.WriteLine("7.Feladat\nÍrj be 1 távolságot!");
        var readKm = int.Parse(Console.ReadLine());
        Console.WriteLine($"{readKm} km esetén fizetendő: {CalcPrice(readKm)}");
    
        using(var output = new StreamWriter("dijazas.txt")){
            Array.ForEach(fuvarLista, fuvar => output.WriteLine($"{fuvar.nap}. nap {fuvar.sorszam}. fuvar: {CalcPrice(fuvar.tavolsag)} FT"));
        }
                
        Console.WriteLine("9. Feladat\nAz egész heti fizetés: " + fuvarLista.Select(k => CalcPrice(k.tavolsag)).Sum());
    }

    private static int CalcPrice(int distance) {
        if(distance <= 2) {
            return 500;
        }else if(distance <= 5) {
            return 700;
        }else if(distance <= 10) {
            return 900;
        }else if(distance <= 20) {
            return 1400;
        }
        return 2000;
    }
}