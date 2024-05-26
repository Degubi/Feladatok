import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Szavak_stream {

    public static void main(String[] args) throws IOException{
        var bekertSzo = System.console().readLine("Írj be 1 szót: ").toLowerCase();
        if(bekertSzo.contains("a") || bekertSzo.contains("e") || bekertSzo.contains("i") || bekertSzo.contains("o") || bekertSzo.contains("u")) {
            System.out.println("Van benne magánhangzó");
        }else{
            System.out.println("Nincs benne magánhangzó");
        }

        Files.lines(Path.of("szoveg.txt"))
             .max(Comparator.comparingInt(String::length))
             .ifPresent(longest -> System.out.println("A leghoszabb szó: " + longest + ", hossza: " + longest.length()));

        var lineCount = Files.lines(Path.of("szoveg.txt")).count();
        System.out.println("Összes szó: " + lineCount);

        var mghWordCount = Files.lines(Path.of("szoveg.txt"))
                                .filter(k -> k.contains("a") || k.contains("e"))
                                .filter(k -> k.contains("i") || k.contains("o"))
                                .filter(k -> k.contains("u"))
                                .count();

        System.out.println(Files.lines(Path.of("szoveg.txt"))
                                .filter(k -> k.contains("a") || k.contains("e"))
                                .filter(k -> k.contains("i") || k.contains("o"))
                                .filter(k -> k.contains("u"))
                                .collect(Collectors.joining(" ")));

        System.out.println("Mgh-s szavak száma: " + mghWordCount);
        System.out.printf(mghWordCount + "/" + lineCount + ", ez százalékban %.2f\n", (float)mghWordCount / lineCount * 100);

        var otbetus = Files.lines(Path.of("szoveg.txt"))
                           .filter(k -> k.length() == 5)
                           .toArray(String[]::new);

        var bekertSzoreszlet = System.console().readLine("Írj be 1 szórészletet: ");

        System.out.println(Arrays.stream(otbetus)
                                 .filter(k -> k.contains(bekertSzoreszlet))
                                 .collect(Collectors.joining(" ")));

        try(var output = new PrintWriter("letra.txt")) {
            var used = new ArrayList<String>();
            var buffer = new ArrayList<String>();

            for(var check : otbetus) {
                var threeLetters = check.substring(1, 4);

                if(!used.contains(threeLetters)) {
                    used.add(threeLetters);

                    for(var sajt : otbetus) {
                        if(sajt.regionMatches(1, threeLetters, 0, 3)) {
                            buffer.add(sajt);
                        }
                    }
                    if(buffer.size() > 1) {
                        for(var print : buffer) {
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