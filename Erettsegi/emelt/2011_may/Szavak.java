import java.io.*;
import java.util.*;

public class Szavak {
	
	public static void main(String[] args) throws IOException{
		var conPut = new Scanner(System.in);
		System.out.println("Írj be 1 szót");
		String read = conPut.next().toLowerCase();
		
		if(read.contains("a") || read.contains("e") || read.contains("i") || read.contains("o") || read.contains("u")) {
			System.out.println("Van benne magánhangzó");
		}else {
			System.out.println("Nincs benne magánhangzó");
		}
		
		ArrayList<String> otbetus = new ArrayList();
		String longest = "";
		int wordCounter = 0, lineCounter = 0;
		
		try(var input = new BufferedReader(new FileReader("szoveg.txt"))){
			for(String line = input.readLine(); line != null; line = input.readLine(), ++lineCounter) {
				int lineLenght = line.length();
				if(lineLenght > longest.length()) {
					longest = line;
				}
				if(lineLenght == 5) {
					otbetus.add(line);
				}
				int mghCounter = 0;
				for(int k = 0; k < lineLenght; ++k) {
					char temp = line.charAt(k);
					if(temp == 'a' || temp == 'e' || temp == 'i' || temp == 'o' || temp == 'u') {
						++mghCounter;
					}
				}
				if(lineLenght / 2 < mghCounter) {
					++wordCounter;
					System.out.print(line + " ");
				}
			}
		}
		
		System.out.println();
		System.out.println("A leghoszabb szó: " + longest + ", hossza: " + longest.length());
		System.out.println("Mgh-s szavak száma: " + wordCounter);
		System.out.println("Összes szó: " + lineCounter);
		System.out.printf(wordCounter + "/" + lineCounter + ", ez százalékban %.2f\n", (float)wordCounter / lineCounter * 100);
		
		System.out.println("Írj be 1 szórészletet");
		String meh = conPut.next();
		for(String check : otbetus) {
			if(check.contains(meh)) {
				System.out.print(check + " ");
			}
		}
		conPut.close();
		System.out.println();
		
		try(var output = new PrintWriter("letra.txt")){
			ArrayList<String> used = new ArrayList();
			ArrayList<String> buffer = new ArrayList();
			
			for(String check : otbetus) {
				String threeLetters = check.substring(1, 4);
				if(!used.contains(threeLetters)) {
					used.add(threeLetters);
					for(String sajt : otbetus) {
						if(sajt.regionMatches(1, threeLetters, 0, 3)) {
							buffer.add(sajt);
						}
					}
					if(buffer.size() > 1) {
						for(String print : buffer) {
							output.println(print);
						}
						output.println();
					}
					buffer.clear();
				}
			}
		}
	}
}