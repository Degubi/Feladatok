import static java.nio.file.StandardOpenOption.*;

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

public class Tarsalgo_lambda {

	public static void main(String[] args) throws IOException {
		var athaladasok = Files.lines(Path.of("ajto.txt"))
							   .map(Athaladas::new)
							   .toArray(Athaladas[]::new);
				
		System.out.println("2. Feladat");
		
		Arrays.stream(athaladasok)
			  .filter(athaladas -> athaladas.belepes)
			  .findFirst()
			  .ifPresent(athaladas -> System.out.println("Elsõ belépõ: " + athaladas.szemelyID));
		
		Arrays.stream(athaladasok)
			  .filter(athaladas -> !athaladas.belepes)
			  .reduce((l, r) -> r)
			  .ifPresent(athaladas -> System.out.println("Utolsó kilépõ: " + athaladas.szemelyID));
		
		var athaladasAdat = Arrays.stream(athaladasok).collect(Collectors.toMap(athaladas -> athaladas.szemelyID, ath -> 1, (l, r) -> l + r));
		
		System.out.println("4.Feladat");
		System.out.println("Bent maradtak: " + athaladasAdat.entrySet().stream()
					 									    .filter(entry -> entry.getValue() % 2 == 1)
					 									    .map(entry -> entry.getKey().toString())
					 									    .collect(Collectors.joining(", ")));
		System.out.println("5. Feladat");
		Arrays.stream(athaladasok)
			  .max(Comparator.comparingInt(athaladas -> athaladas.bentlevok))
			  .ifPresent(athaladas -> System.out.println("Ekkor voltak bent a legtöbben: " + athaladas.idopont));
		
		var athaladasString = athaladasAdat.entrySet().stream()
	  	  		   						   .map(entry -> entry.getKey() + " " + entry.getValue())
	  	  		   						   .collect(Collectors.joining("\n"));
		
		//Régi java-ba Files.write(Paths.get("athaladas.txt"), athaladasAdat.getBytes(), ...
		Files.writeString(Path.of("athaladas.txt"), athaladasString, CREATE, WRITE, TRUNCATE_EXISTING);
		
		try(var input = new Scanner(System.in)){
			System.out.println("6.Feladat\nÍrj be 1 ID-t");
			
			var beID = input.nextInt();
			var miEmberunk = Arrays.stream(athaladasok)
								   .filter(athaladas -> athaladas.szemelyID == beID)
								   .toArray(Athaladas[]::new);
			
			System.out.println("7. Feladat");
			System.out.println(Arrays.stream(miEmberunk)
								     .map(athaladas -> athaladas.belepes ? (athaladas.idopont.toString() + '-') : (athaladas.idopont.toString() + '\n'))
								     .collect(Collectors.joining()));
			
			var vegIndex = miEmberunk.length % 2 == 0 ? miEmberunk.length : miEmberunk.length - 1;
			var bentPercek = IntStream.iterate(0, index -> index < vegIndex, index -> index + 2)
					 				  .mapToObj(index -> Duration.between(miEmberunk[index].idopont, miEmberunk[index + 1].idopont))
					 				  .mapToLong(duration -> duration.toMinutes())
					 				  .sum();
			
			if(miEmberunk.length % 2 == 1) bentPercek += Duration.between(miEmberunk[miEmberunk.length - 1].idopont, LocalTime.of(15, 00)).toMinutes();
			
			System.out.println("8. Feladat");
			System.out.println("A " + beID + " számú személy " + bentPercek + " percet volt bent, a figyelés végén " + (miEmberunk.length % 2 == 1 ? "bent" : "kint") + " volt.");
		}
	}
	
	static class Athaladas{
		static int bentlevoSzamlalo = 0;
		
		LocalTime idopont;
		int szemelyID;
		boolean belepes;
		int bentlevok;
		
		public Athaladas(String sor) {
			var split = sor.split(" ");
			
			this.idopont = LocalTime.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
			this.szemelyID = Integer.parseInt(split[2]);
			this.belepes = split[3].equals("be");
			bentlevoSzamlalo += belepes ? 1 : -1;
			this.bentlevok = bentlevoSzamlalo;
		}
	}
}