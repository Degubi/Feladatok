import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class SMS_lambda{
	
	private static int sumIf(Uzenet[] smss, Predicate<Uzenet>... preds) {
		return Stream.of(smss).filter(preds.length == 1 ? preds[0] : preds[1].and(preds[1])).mapToInt(k -> 1).sum();
	}
	
	public static void main(String[] args) throws IOException{
		var file = Files.readAllLines(Paths.get("sms.txt"), StandardCharsets.ISO_8859_1);
		
		var smss = IntStream.iterate(1, k -> k < file.size(), k -> k + 2)
								 .mapToObj(k -> new Uzenet(file.get(k).split(" "), file.get(k + 1)))
								 .toArray(Uzenet[]::new);
		
		System.out.println("2.Feladat\nUtolsó sms szövege: " + smss[smss.length - 1].message + "\n3.Feladat");
		System.out.println("Leghosszabb üzenet: " + Stream.of(smss).min(comparing(k -> k.message)).get());
		System.out.println("Legrövidebb üzenet: " + Stream.of(smss).max(comparing(k -> k.message)).get());
		System.out.println("4.Feladat");
		System.out.println("1-20 karakteres üzenetek: " + sumIf(smss, k -> k.message.length() <= 20));
		System.out.println("21-40 karakteres üzenetek: " + sumIf(smss, k -> k.message.length() > 20, k -> k.message.length() <= 40));
		System.out.println("41-60 karakteres üzenetek: " + sumIf(smss, k -> k.message.length() > 40, k -> k.message.length() <= 60));
		System.out.println("61-80 karakteres üzenetek: " + sumIf(smss, k -> k.message.length() > 60, k -> k.message.length() <= 80));
		System.out.println("81-100 karakteres üzenetek: " + sumIf(smss, k -> k.message.length() > 80, k -> k.message.length() <= 100));
		
		//5. Feladat több mint valószínû hogy rossz, még a kérdést se értettem meg teljesen...
		System.out.println("5.Feladat\nKihagyott üzenetek: " + Stream.of(smss).filter(k -> k.time.getMinute() == 0).count());
		
		System.out.println("6. Feladat\nLeghosszabb idõ: " + IntStream.iterate(0, k -> k < smss.length, k -> k + 2)
				 .filter(k -> smss[k].number == 123456789)
				 .mapToObj(k -> Duration.between(smss[k].time, smss[k + 1].time))
				 .max(naturalOrder())
				 .get().toMinutes());
		
		System.out.println("Írd be a hiányzó üzenetet! (Óra perc szám üzenet)");
		useIO((output, input) -> {
			Uzenet missing = new Uzenet(new String[] {input.next(), input.next(), input.next()}, input.next());
			
			Stream.concat(Stream.of(smss), Stream.of(missing))
			      .sorted(comparingInt(k -> k.number))
			      .collect(groupingBy(k -> k.number, TreeMap::new, toList()))
			      .forEach((key, value) -> {
			    	  output.println(key);
			    	  value.forEach(msg -> output.println(msg.time.getHour() + " " + msg.time.getMinute() + " " + msg.message));});
		});
	}
	
	private static void useIO(BiConsumer<PrintWriter, Scanner> io) throws IOException {
		try(var output = new PrintWriter("smski.txt"); var input = new Scanner(System.in)){
				io.accept(output, input);
		}
	}
	
	static class Uzenet{
		LocalTime time;
		int number;
		String message;
		
		public Uzenet(String[] data, String msg) {
			time = LocalTime.of(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
			number = Integer.parseInt(data[2]);
			message = msg;
		}
		
		@Override
		public String toString() {
			return "Idõ: " + time.getHour() + " " + time.getMinute() + ", telefonszám: " + number + ", üzenet: " + message;
		}
	}
}