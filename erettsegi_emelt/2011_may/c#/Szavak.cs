using System;
using System.Collections.Generic;
using System.IO;
 
public class Szavak {

    public static void Main(string[] args){
        Console.WriteLine("Írj be 1 szót");
        var read = Console.ReadLine().ToLower();
    
        if(read.Contains('a') || read.Contains('e') || read.Contains('i') || read.Contains('o') || read.Contains('u')) {
            Console.WriteLine("Van benne magánhangzó");
        }else{
            Console.WriteLine("Nincs benne magánhangzó");
        }
    
        var otbetus = new List<string>();
        var longest = "";
        var wordCounter = 0;
        var lineCounter = 0;

        using(var input = new StreamReader("szoveg.txt")){
            for(var line = input.ReadLine(); line != null; line = input.ReadLine(), ++lineCounter) {
                var lineLenght = line.Length;

                if(lineLenght > longest.Length) {
                    longest = line;
                }

                if(lineLenght == 5) {
                    otbetus.Add(line);
                }
                
                var mghCounter = 0;
                for(int k = 0; k < lineLenght; ++k) {
                    var temp = line[k];

                    if(temp == 'a' || temp == 'e' || temp == 'i' || temp == 'o' || temp == 'u') {
                        ++mghCounter;
                    }
                }

                if(lineLenght / 2 < mghCounter) {
                    ++wordCounter;
                    Console.Write(line + " ");
                }
            }
        }
    
        Console.WriteLine();
        Console.WriteLine("A leghoszabb szó: " + longest + ", hossza: " + longest.Length);
        Console.WriteLine("Mgh-s szavak száma: " + wordCounter);
        Console.WriteLine("Összes szó: " + lineCounter);
        Console.WriteLine(wordCounter + "/" + lineCounter + ", ez százalékban {0}%\n", ((float)wordCounter / lineCounter * 100).ToString("0.00"));
    
        Console.WriteLine("Írj be 1 szórészletet");

        var meh = Console.ReadLine();
        foreach(var check in otbetus) {
            if(check.Contains(meh)) {
                Console.Write(check + " ");
            }
        }
        Console.WriteLine();
    
        using(var output = new StreamWriter("letra.txt")){
            var used = new List<string>();
            var buffer = new List<string>();

            foreach(string check in otbetus) {
                var threeLetters = check.Substring(1, 4);

                if(!used.Contains(threeLetters)) {
                    used.Add(threeLetters);

                    foreach(var sajt in otbetus) {
                        var trimmed = sajt.Substring(1, 4);

                        if(trimmed.Equals(threeLetters)) {
                            buffer.Add(sajt);
                        }
                    }

                    if(buffer.Count > 1) {
                        foreach(var print in buffer) {
                            output.WriteLine(print);
                        }
                        output.WriteLine();
                    }
                    buffer.Clear();
                }
            }
        }
    }
}