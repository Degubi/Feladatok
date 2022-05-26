import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Szamok_stream {

    public static void main(String[] args) throws IOException {
        var rand = new Random();
        var lines = Files.readAllLines(Path.of("felszam.txt"), StandardCharsets.ISO_8859_1);
        var feladatok = IntStream.iterate(0, k -> k < lines.size(), k -> k + 2)
                                 .mapToObj(i -> new Feladat(lines.get(i), lines.get(i + 1)))
                                 .toArray(Feladat[]::new);

        System.out.println(feladatok.length + " Feladat van a fájlban!");

        var matekFeladatok = Arrays.stream(feladatok)
                                   .filter(k -> k.temakor.equals("matematika"))
                                   .toArray(Feladat[]::new);

        System.out.println("Matekfeladatok száma: " + matekFeladatok.length);

        var matekFeladatokPontonkent = Arrays.stream(matekFeladatok)
                                             .collect(Collectors.groupingBy(k -> k.pont));

        System.out.println("1 pontot érő feladatok száma: " + matekFeladatokPontonkent.get(1).size());
        System.out.println("2 pontot érő feladatok száma: " + matekFeladatokPontonkent.get(2).size());
        System.out.println("3 pontot érő feladatok száma: " + matekFeladatokPontonkent.get(3).size());

        var valaszSzerintRendezettFeladatok = Arrays.stream(feladatok)
                                                    .sorted(Comparator.comparingInt(k -> k.valasz))
                                                    .toArray(Feladat[]::new);

        System.out.println("A legkisebb válaszú feladat: " + valaszSzerintRendezettFeladatok[0].valasz +
                           ", a legnagyobb: " + valaszSzerintRendezettFeladatok[valaszSzerintRendezettFeladatok.length - 1].valasz);

        var temakorok = Arrays.stream(feladatok)
                              .map(k -> k.temakor)
                              .distinct()
                              .collect(Collectors.joining(", "));

        System.out.println("Előforduló témakörök: " + temakorok);

        try(var console = new Scanner(System.in)){
            System.out.println("Írj be 1 témakört!");
            var readKor = console.nextLine();

            var bekertTemakorFeladatai = Arrays.stream(feladatok)
                                               .filter(k -> k.temakor.equals(readKor))
                                               .toArray(Feladat[]::new);

            var randomFeladat = bekertTemakorFeladatai[rand.nextInt(bekertTemakorFeladatai.length)];
            System.out.println(randomFeladat.kerdes);

            var bekertValasz = console.nextInt();
            System.out.println(bekertValasz == randomFeladat.valasz ? "Kapott pontszám: " + randomFeladat.pont
                                                                    : "Rossz válasz, 0 pont...\nA helyes válasz: " + randomFeladat.valasz);
        }

        var generalt = rand.ints(0, feladatok.length)
                           .mapToObj(i -> feladatok[i])
                           .distinct()
                           .limit(10)
                           .toArray(Feladat[]::new);

        var infoSorok = Arrays.stream(generalt)
                              .map(k -> k.pont + " " + k.kerdes)
                              .collect(Collectors.joining("\n"));

        var pontSum = Arrays.stream(generalt)
                            .mapToInt(k -> k.pont)
                            .sum();

        Files.writeString(Path.of("tesztfel.txt"), infoSorok + "\n" + pontSum);
    }
}