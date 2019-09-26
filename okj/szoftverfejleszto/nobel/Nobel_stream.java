import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Nobel_stream {

	public static void main(String[] args) throws IOException {
		var dijak = Files.lines(Path.of("nobel.csv"))
						 .skip(1)
						 .map(Dij::new)
						 .toArray(Dij[]::new);
		
		Arrays.stream(dijak)
			  .filter(k -> k.keresztnev.equals("Arthur B.") && k.vezeteknev.equals("McDonald"))
			  .findFirst()
			  .ifPresent(k -> System.out.println("3. Feladat: Arthur díja: " + k.tipus));
		
		Arrays.stream(dijak)
			  .filter(k -> k.evszam == 2017)
			  .filter(k -> k.tipus.equals("irodalmi"))
			  .findFirst()
			  .ifPresent(k -> System.out.println("4. Feladat: Irodalmi dij 2017-ben: " + k.vezeteknev + " " + k.keresztnev));
		
		System.out.println("5. Feladat");
		Arrays.stream(dijak)
			  .filter(k -> k.vezeteknev.equals(""))
			  .filter(k -> k.evszam >= 1990)
			  .forEach(k -> System.out.println(k.evszam + ": " + k.keresztnev));
		
		System.out.println("6. Feladat");
		Arrays.stream(dijak)
			  .filter(k -> k.vezeteknev.contains("Curie"))
			  .forEach(k -> System.out.println(k.evszam + ": " + k.vezeteknev + ' ' + k.keresztnev + '(' + k.tipus + ')'));
		
		System.out.println("7. Feladat");
		Arrays.stream(dijak)
			  .collect(Collectors.groupingBy(k -> k.tipus, Collectors.counting()))
			  .forEach((dij, db) -> System.out.println(dij + ": " + db + " db"));
		
		var orvosi = Arrays.stream(dijak)
						   .filter(k -> k.tipus.equals("orvosi"))
						   .sorted(Comparator.comparingInt(k -> k.evszam))
						   .map(k -> k.evszam + ":" + k.vezeteknev + " " + k.keresztnev)
						   .collect(Collectors.joining("\n"));
		
		Files.writeString(Path.of("orvosi.txt"), orvosi);
	}
	
	public static class Dij{
		public final int evszam;
		public final String tipus;
		public final String keresztnev;
		public final String vezeteknev;
		
		public Dij(String sor) {
			var split = sor.split(";");
			
			evszam = Integer.parseInt(split[0]);
			tipus = split[1];
			keresztnev = split[2];
			vezeteknev = split.length == 4 ? split[3] : "";
		}
	}
}