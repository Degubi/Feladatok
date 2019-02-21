using System;
using System.Linq;
using System.IO;
 
namespace ConsoleApplication1{
    class SMS_linq{
        static void Main(string[] args){
		var file = File.ReadAllLines("sms.txt");

		Uzenet[] smss = Enumerable.Range(1, file.Length - 1)
                                  .Where(k => k % 2 == 1)
				                  .Select(k => new Uzenet(file[k].Split(' '), file[k + 1]))
				                  .ToArray();
		
		Console.WriteLine("2.Feladat\nUtolsó sms szövege: " + smss.Last().message + "\n3.Feladat");
		Console.WriteLine("Leghosszabb üzenet: " + smss.Min(k => k.message));
		Console.WriteLine("Legrövidebb üzenet: " + smss.Max(k => k.message));
		Console.WriteLine("4.Feladat");
		Console.WriteLine("1-20 karakteres üzenetek: " + smss.Where(k => k.message.Length <= 20).Count());
		Console.WriteLine("21-40 karakteres üzenetek: " + smss.Where(k => k.message.Length > 20 && k.message.Length <= 40).Count());
		Console.WriteLine("41-60 karakteres üzenetek: " + smss.Where(k => k.message.Length > 40 && k.message.Length <= 60).Count());
		Console.WriteLine("61-80 karakteres üzenetek: " + smss.Where(k => k.message.Length > 60 && k.message.Length <= 80).Count());
		Console.WriteLine("81-100 karakteres üzenetek: " + smss.Where(k => k.message.Length > 80 && k.message.Length <= 100).Count());
		
		//5. Feladat több mint valószínű hogy rossz, még a kérdést se értettem meg teljesen...
		Console.WriteLine("5.Feladat\nKihagyott üzenetek: " + smss.Where(k => k.time.Minute == 0).Count());
		
		Console.WriteLine("6. Feladat\nLeghosszabb idő: " + Enumerable.Range(0, smss.Length)
                 .Where(k => k % 2 == 0)
				 .Where(k => smss[k].number == 123456789)
				 .Select(k => smss[k + 1].time - smss[k].time)
				 .Max()
				 .Minutes);
		
		using(StreamWriter output = new StreamWriter("smski.txt")){
			Console.WriteLine("Írd be a hiányzó üzenetet! (Óra perc szám üzenet)");
			Uzenet missing = new Uzenet(new [] {Console.ReadLine(), Console.ReadLine(), Console.ReadLine()}, Console.ReadLine());

            smss.OrderBy(k => k.number)
                .GroupBy(k => k.number)
                .ToList()
                .ForEach(k => {
                    output.WriteLine(k.Key);
                    k.ToList()
                     .ForEach(msg => output.WriteLine(msg.time.Hour + " " + msg.time.Minute + " " + msg.message));
                     });
                }
        }
	
	    struct Uzenet{
		    public DateTime time;
		    public int number;
		    public string message;
		
		    public Uzenet(string[] data, string msg) {
                time = new DateTime(2018, 3, 12, int.Parse(data[0]), int.Parse(data[1]), 0);
			    number = int.Parse(data[2]);
			    message = msg;
		    }
		
		    public override string ToString() {
			    return "Idő: " + time.Hour + " " + time.Minute + ", telefonszám: " + number + ", üzenet: " + message;
		    }
	    }
    }
}