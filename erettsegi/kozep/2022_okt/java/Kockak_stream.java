import module java.base;

public class Kockak_stream {

    public static void main(String[] args) {
        var random = new Random();
        var alkalmakSzama = Integer.parseInt(System.console().readLine("Hány alkalommal legyen feldobás? "));
        var korok = Stream.generate(() -> kortGeneral(random))
                          .distinct()
                          .limit(alkalmakSzama)
                          .toArray(Kor[]::new);

        Arrays.stream(korok)
              .forEach(k -> System.out.println("Dobás: " + k.elso + " + " + k.masodik + " + " + k.harmadik + " = " + k.osszeg + ", nyert: " + (k.osszeg < 10 ? "Anni" : "Panni")));

        var statok = Arrays.stream(korok)
                           .collect(Collectors.partitioningBy(k -> k.osszeg < 10, Collectors.counting()));

        System.out.println("A játékok során " + statok.getOrDefault(true, 0L) + " alkalommal nyert Anni, " + statok.getOrDefault(false, 0L) + " alkalommal pedig Panni");
    }

    static Kor kortGeneral(Random random) {
        var elso = dobastGeneral(random);
        var masodik = dobastGeneral(random);
        var harmadik = dobastGeneral(random);

        return new Kor(elso, masodik, harmadik, elso + masodik + harmadik);
    }

    static int dobastGeneral(Random random) {
        return 1 + random.nextInt(6);
    }

    record Kor(int elso, int masodik, int harmadik, int osszeg) {}
}