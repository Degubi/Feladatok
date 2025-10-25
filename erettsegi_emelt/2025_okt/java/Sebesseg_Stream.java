import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Sebesseg_Stream {

    void main() throws IOException {
        var lines = Files.readAllLines(Path.of("ut.txt"));
        var totalRoadMeters = Integer.parseInt(lines.getFirst());
        var roadData = lines.stream()
                            .skip(1)
                            .map(k -> k.split(" "))
                            .map(k -> new RoadData(Integer.parseInt(k[0]), k[1]))
                            .toArray(RoadData[]::new);

        var cityStartingOrEndingIndices = IntStream.range(0, roadData.length)
                                                   .filter(i -> roadData[i].value.length() >= 4 || roadData[i].value.charAt(0) == ']')
                                                   .toArray();

        var citiesData = IntStream.iterate(0, i -> i < cityStartingOrEndingIndices.length, i -> i + 2)
                                  .mapToObj(i -> new CityData(roadData[cityStartingOrEndingIndices[i]].value, Arrays.copyOfRange(roadData, cityStartingOrEndingIndices[i], cityStartingOrEndingIndices[i + 1] + 1)))
                                  .toArray(CityData[]::new);

        System.out.println("2. Feladat:");

        Arrays.stream(citiesData)
              .forEach(k -> System.out.println(k.name));

        var inputDistance = ((int) Float.parseFloat(System.console().readLine("3. Feladat: Adja meg a vizsgált szakasz hosszát km-ben! ")) * 1000);

        Arrays.stream(roadData)
              .gather(Gatherers.windowSliding(2))
              .gather(Gatherers.scan(() -> new DistanceToData(0, null), (k, dataPair) -> new DistanceToData(k.currentDistance + (dataPair.get(1).distance - dataPair.get(0).distance), dataPair.get(0))))
              .takeWhile(k -> k.currentDistance <= inputDistance)
              .filter(k -> isSpeedLimitingData(k.data))
              .mapToInt(k -> Integer.parseInt(k.data.value))
              .min()
              .ifPresent(k -> System.out.println("Ezen a távon belül a legalacsonyabb megendetett sebesség " + k + "km/óra volt"));

        var totalDistanceInCities = Arrays.stream(citiesData)
                                          .mapToInt(Sebesseg_Stream::calculateDistance)
                                          .sum();

        System.out.printf("4. Feladat: Az út %.2f százaléka vezetett településen belül\n", (float) totalDistanceInCities / totalRoadMeters * 100);

        var inputCityName = System.console().readLine("5. Feladat: Adja meg 1 település nevét! ");
        var inputCityIndex = IntStream.range(0, citiesData.length)
                                      .filter(i -> citiesData[i].name.equals(inputCityName))
                                      .findFirst()
                                      .orElseThrow();

        var roadDataForInputCity = citiesData[inputCityIndex];
        var speedLimitingTableCount = Arrays.stream(roadDataForInputCity.roadData)
                                            .filter(Sebesseg_Stream::isSpeedLimitingData)
                                            .count();

        System.out.println("Sebességkorlátozások száma: " + speedLimitingTableCount + ", út teljes hossza: " + calculateDistance(roadDataForInputCity));

        var oneBeforeInputCity = inputCityIndex == 0 ? null : citiesData[inputCityIndex - 1];
        var oneAfterInputCity = inputCityIndex == (citiesData.length - 1) ? null : citiesData[inputCityIndex + 1];

        Stream.of(oneBeforeInputCity, oneAfterInputCity)
              .filter(Objects::nonNull)
              .min(Comparator.comparingInt(Sebesseg_Stream::calculateDistance))
              .ifPresent(k -> System.out.println("6. Feladat: A Legközelebbi település: " + k.name));
    }

    static boolean isSpeedLimitingData(RoadData data) {
        return Character.isDigit(data.value.charAt(0));
    }

    static int calculateDistance(CityData data) {
        return data.roadData[data.roadData.length - 1].distance - data.roadData[0].distance;
    }

    record RoadData(int distance, String value) {}
    record DistanceToData(int currentDistance, RoadData data) {}
    record CityData(String name, RoadData[] roadData) {}
}
