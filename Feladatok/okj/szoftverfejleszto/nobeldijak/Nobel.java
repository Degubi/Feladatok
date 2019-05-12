import static java.nio.file.StandardOpenOption.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Nobel {

	public static void main(String[] args) throws IOException {
		var dijak = Files.lines(Path.of("nobel.csv"))
						 .skip(1)
						 .map(Dij::new)
						 .toArray(Dij[]::new);
		
		System.out.println("3. Feladat");
		
		Arrays.stream(dijak)
			  .filter(k -> k.keresztnev.equals("Arthur B.") && k.vezeteknev.equals("McDonald"))
			  .findFirst()
			  .ifPresent(k -> System.out.println("Arthur " + k.tipus + " díjat kapott"));
		
		System.out.println("4. Feladat");
		
		Arrays.stream(dijak)
			  .filter(k -> k.evszam == 2017)
			  .filter(k -> k.tipus.equals("irodalmi"))
			  .findFirst()
			  .ifPresent(k -> System.out.println(k.keresztnev + ' ' + k.vezeteknev));
		
		var szervezetek = Arrays.stream(dijak)
								.filter(k -> k.evszam >= 1990)
								.filter(k -> k.tipus.equals("béke"))
								.filter(k -> k.vezeteknev == null)
								.map(k -> k.evszam + ": " + k.keresztnev)
								.collect(Collectors.joining("\n"));
		
		System.out.println("5. Feladat\n" + szervezetek);
		System.out.println("6. Feladat");
		
		Arrays.stream(dijak)
			  .filter(k -> k.vezeteknev != null && k.vezeteknev.contains("Curie"))
			  .forEach(k -> System.out.println(k.evszam + ": " + k.vezeteknev + ' ' + k.keresztnev + " (" + k.tipus + ')'));
		
		System.out.println("7. Feladat");
		
		Arrays.stream(dijak)
			  .collect(Collectors.groupingBy(k -> k.tipus, Collectors.counting()))
			  .forEach((tipus, darab) -> System.out.println(tipus + ": " + darab + " db"));
		
		var fileba = Arrays.stream(dijak)
						   .filter(k -> k.tipus.equals("orvosi"))
						   .map(k -> k.evszam + ";" + k.vezeteknev + ' ' + k.keresztnev)
						   .collect(Collectors.joining("\n"));
		
		Files.writeString(Path.of("orvosi.txt"), fileba, WRITE, TRUNCATE_EXISTING, CREATE);
	}
	
	static class Dij{
		public int evszam;
		public String tipus;
		public String keresztnev;
		public String vezeteknev;
		
		public Dij(String line) {
			var split = line.split(";");
			
			evszam = Integer.parseInt(split[0]);
			tipus = split[1];
			keresztnev = split[2];
			
			if(split.length == 4) {
				vezeteknev = split[3];
			}
		}
	}
}