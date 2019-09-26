using System;
using System.Collections.Generic;
using System.Linq;
using System.IO;
 
namespace ConsoleApplication1{
    class Szavak{
        static void Main(string[] args){
		    Console.WriteLine("Írj be 1 szót");
		    string read = Console.ReadLine().ToLower();
		
		    if(read.Contains('a') || read.Contains('e') || read.Contains('i') || read.Contains('o') || read.Contains('u')) {
			    Console.WriteLine("Van benne magánhangzó");
		    }else{
			    Console.WriteLine("Nincs benne magánhangzó");
		    }
		
		    List<string> otbetus = new List<string>();
		    string longest = "";
		    int wordCounter = 0, lineCounter = 0;
		    using(StreamReader input = new StreamReader("szoveg.txt")){
			    for(string line = input.ReadLine(); line != null; line = input.ReadLine(), ++lineCounter) {
				    int lineLenght = line.Length;
				    if(lineLenght > longest.Length) {
					    longest = line;
				    }
				    if(lineLenght == 5) {
					    otbetus.Add(line);
				    }
				    int mghCounter = 0;
				    for(int k = 0; k < lineLenght; ++k) {
					    char temp = line[k];
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
		    string meh = Console.ReadLine();
		    foreach(string check in otbetus) {
			    if(check.Contains(meh)) {
				    Console.Write(check + " ");
			    }
		    }
		    Console.WriteLine();
		
		    using(StreamWriter output = new StreamWriter("letra.txt")){
			    List<string> used = new List<string>();
			    List<string> buffer = new List<string>();
			    foreach(string check in otbetus) {
				    string threeLetters = check.Substring(1, 4);
				    if(!used.Contains(threeLetters)) {
					    used.Add(threeLetters);
					    foreach(string sajt in otbetus) {
                            string trimmed = sajt.Substring(1, 4);
						    if(trimmed.Equals(threeLetters)) {
							    buffer.Add(sajt);
						    }
					    }
					    if(buffer.Count > 1) {
						    foreach(string print in buffer) {
							    output.WriteLine(print);
						    }
						    output.WriteLine();
					    }
					    buffer.Clear();
				    }
			    }
		    }
            Console.WriteLine("Press enter to exit...");
            Console.Read();
        }
    }
}