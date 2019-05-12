import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Szavak_lambda {
	
	public static void main(String[] args) throws IOException{
		var conPut = new Scanner(System.in);
		System.out.println("Írj be 1 szót");
		
		Stream.of(conPut.next().toLowerCase())
			  .filter(k -> k.contains("a") || k.contains("e"))
			  .filter(k -> k.contains("i") || k.contains("o"))
			  .filter(k -> k.contains("u"))
			  .findFirst()
			  .ifPresentOrElse(k -> System.out.println("Van benne"), () -> System.out.println("Nincs benne"));
		
		String longest = Files.lines(Paths.get("szoveg.txt"))
							  .max(Comparator.comparingInt(String::length)).get();
		
		System.out.println("A leghoszabb szó: " + longest + ", hossza: " + longest.length());
		
		long lineCount = Files.lines(Paths.get("szoveg.txt")).count();
		System.out.println("Összes szó: " + lineCount);
		
		long mghWordCount = Files.lines(Paths.get("szoveg.txt"))
							 	 .filter(k -> k.contains("a") || k.contains("e"))
							 	 .filter(k -> k.contains("i") || k.contains("o"))
							 	 .filter(k -> k.contains("u"))
							 	 .count();
		
		System.out.println(Files.lines(Paths.get("szoveg.txt"))
			 	 .filter(k -> k.contains("a") || k.contains("e"))
			 	 .filter(k -> k.contains("i") || k.contains("o"))
			 	 .filter(k -> k.contains("u"))
			 	 .collect(Collectors.joining(" ")));
		
		System.out.println("Mgh-s szavak száma: " + mghWordCount);
		System.out.printf(mghWordCount + "/" + lineCount + ", ez százalékban %.2f\n", (float)mghWordCount / lineCount * 100);
		
		String[] otbetus = Files.lines(Paths.get("szoveg.txt"))
				 				.filter(k -> k.length() == 5)
				 				.toArray(String[]::new);
		
		System.out.println("Írj be 1 szórészletet");
		String meh = conPut.next();
		
		System.out.println(Arrays.stream(otbetus)
			  .filter(k -> k.contains(meh))
			  .collect(Collectors.joining(" ")));

		conPut.close();
		
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