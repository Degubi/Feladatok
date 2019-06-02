import static java.nio.file.StandardOpenOption.*;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.Map.*;
import java.util.stream.*;

public class Telekocsi_lambda {

	public static void main(String[] args) throws IOException {
		var autok = Files.lines(Path.of("autok.csv"), StandardCharsets.ISO_8859_1)
						 .skip(1)
						 .map(Auto::new)
						 .toArray(Auto[]::new);
		
		System.out.println("2. Feladat: Hirdetesek szama: " + autok.length);
		
		var bpToMiskolc = Arrays.stream(autok)
								.filter(k -> k.indulas.equals("Budapest") && k.cel.equals("Miskolc"))
								.mapToInt(k -> k.ferohely)
								.sum();
		
		System.out.println("3. Feladat: BP to Miskolc hely: " + bpToMiskolc);
		
		var utvonalakToFerohelyek = Arrays.stream(autok).collect(Collectors.groupingBy(k -> k.indulas + '-' + k.cel, Collectors.summingInt(k -> k.ferohely)));
		
		utvonalakToFerohelyek.entrySet().stream()
							 .max(Entry.comparingByValue())
							 .ifPresent(k -> System.out.println("4. Feladat: " + k.getKey() + ": " + k.getValue() + " hely"));
		
		System.out.println("5. Feladat");
		var igenyek = Files.lines(Path.of("igenyek.csv"), StandardCharsets.ISO_8859_1)
						   .skip(1)
						   .map(Igeny::new)
						   .toArray(Igeny[]::new);
		
		Arrays.stream(igenyek)
			  .forEach(igeny -> Telekocsi.autotKeresIgenyre(igeny, autok)
					  					 .ifPresent(k -> System.out.println(igeny.azonosito + " -> " + k.rendszam)));
		
		var fileba = Arrays.stream(igenyek)
						   .map(igeny -> Telekocsi.autotKeresIgenyre(igeny, autok)
								  				  .map(k -> igeny.azonosito + ": Rendszam: " + k.rendszam + ", Telefonszam: " + k.telefonszam)
								  				  .orElse(igeny.azonosito + ": " + "Sajnos nem sikerült autót találni"))
						   .collect(Collectors.joining("\n"));
		
		Files.writeString(Path.of("utazasuzenetek.txt"), fileba, WRITE, CREATE, TRUNCATE_EXISTING);
	}
	
	static Optional<Auto> autotKeresIgenyre(Igeny igeny, Auto[] autok){
		return Arrays.stream(autok)
				     .filter(k -> k.indulas.equals(igeny.indulas))
				     .filter(k -> k.cel.equals(igeny.cel))
				     .filter(k -> k.ferohely >= igeny.szemelyek)
				     .findFirst();
	}
	
	static class Auto{
		String indulas;
		String cel;
		String rendszam;
		String telefonszam;
		int ferohely;
		
		public Auto(String sor) {
			var split = sor.split(";");
			
			indulas = split[0];
			cel = split[1];
			rendszam = split[2];
			telefonszam = split[3];
			ferohely = Integer.parseInt(split[4]);
		}
	}
	
	static class Igeny{
		String azonosito;
		String indulas;
		String cel;
		int szemelyek;
		
		public Igeny(String sor) {
			var split = sor.split(";");
			
			azonosito = split[0];
			indulas = split[1];
			cel = split[2];
			szemelyek = Integer.parseInt(split[3]);
		}
	}
}