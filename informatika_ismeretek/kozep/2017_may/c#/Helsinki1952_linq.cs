using System;
using System.Linq;
using System.IO;

var helyezesek = File.ReadLines("helsinki.txt")
                     .Select(line => new Helyezes(line))
                     .ToArray();

Console.WriteLine("3.Feladat\nPontszerző helyezések száma: " + helyezesek.Length);

var aranyak = helyezesek.Where(k => k.helyezes == 1).Count();
var ezustok = helyezesek.Where(k => k.helyezes == 2).Count();
var bronzok = helyezesek.Where(k => k.helyezes == 3).Count();

Console.WriteLine($"4.Feladat\nAranyak: {aranyak}, ezustok: {ezustok}, bronzok: {bronzok}, összesen: {aranyak + ezustok + bronzok}");
Console.WriteLine("5.Feladat\nPontok száma: " + helyezesek.Select(k => k.PontCalc()).Sum());

var uszas = helyezesek.Where(k => k.helyezes <= 3)
                      .Where(k => k.sportag == "uszas")
                      .Count();

var torna = helyezesek.Where(k => k.helyezes <= 3)
                      .Where(k => k.sportag == "torna")
                      .Count();

Console.WriteLine("6.Feladat");
Console.WriteLine(uszas == torna ? "Egyenlőek" : (torna > uszas) ? "Torna több" : "Úszás több");

var fileba = helyezesek.Select(k => k.helyezes + " " + k.sportolokSzama + " " + k.PontCalc() + " " + k.sportag.Replace("kajakkenu", "kajak-kenu") + k.versenyszam);
File.WriteAllLines("helsinki2.txt", fileba);

Console.WriteLine("8. Feladat");

var max = helyezesek.OrderByDescending(k => k.sportolokSzama).First();
Console.WriteLine($"Helyezés: {max.helyezes}, sportág: {max.sportag}, szám: {max.versenyszam}, sportolók: {max.sportolokSzama}");