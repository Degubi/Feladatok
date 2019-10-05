import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.Map.*;
import java.util.stream.*;

public class Morze_stream {

	public static void main(String[] args) throws IOException {
		var betuToMorze = Files.lines(Path.of("morzeabc.txt"), StandardCharsets.ISO_8859_1)
					   		   .skip(1)
					   		   .map(k -> k.split("\t"))
					   		   .collect(Collectors.toMap(k -> k[0], k -> k[1]));
		
		var morzeToBetu = betuToMorze.entrySet().stream().collect(Collectors.toMap(Entry::getValue, Entry::getKey));
		
		System.out.println("3. Feladat: Karakterek száma: " + betuToMorze.size());
		System.out.println("4. Feladat: Írjon be 1 karaktert!");
		
		try(var input = new Scanner(System.in)){
			var bekert = input.nextLine();
			
			if(betuToMorze.containsKey(bekert)) {
				System.out.println("A " + bekert + " karakter kódja: " + betuToMorze.get(bekert));
			}else {
				System.out.println("Nem található a kódtárban ilyen karakter!");
			}
		}
		
		var morze = Files.lines(Path.of("morze.txt"))
						 .map(k -> new Idezet(k, morzeToBetu))
						 .toArray(Idezet[]::new);
		
		System.out.println("7. Feladat: Elsõ idézet szerzõje: " + morze[0].szerzo);
		Arrays.stream(morze)
			  .max(Comparator.comparingInt(k -> k.uzenet.length()))
			  .ifPresent(k -> System.out.println("8. Feladat: Leghosszab idézet: " + k.szerzo + ": " + k.uzenet));
		
		System.out.println("8. Feladat: Arisztotelés idézetei: ");
		Arrays.stream(morze)
			  .filter(k -> k.szerzo.equalsIgnoreCase("Arisztotelész"))
			  .forEach(k -> System.out.println('-' + k.uzenet));
		
		var fileba = Arrays.stream(morze)
						  .map(k -> k.szerzo + ':' + k.uzenet)
						  .collect(Collectors.toList());
		
		Files.write(Path.of("forditas.txt"), fileba);
	}
	
	static String morze2Szoveg(String uzenet, Map<String, String> abc) {
		var forditott = new StringBuilder();
		
		for(var szavak : uzenet.split("       ")) {
			for(var betuk : szavak.split("   ")) {
				forditott.append(abc.get(betuk));
			}
			
			forditott.append(' ');
		}
		
		return forditott.deleteCharAt(forditott.length() - 1).toString();
	}
	
	public static class Idezet{
		public final String szerzo;
		public final String uzenet;
		
		public Idezet(String sor, Map<String, String> abc) {
			var split = sor.split(";");
			
			this.szerzo = morze2Szoveg(split[0], abc);
			this.uzenet = morze2Szoveg(split[1], abc);
		}
	}
}