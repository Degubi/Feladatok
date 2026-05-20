### 1. Feladat:
- Beolvasás egyszerű, minden sor 1 db mélységet reprezentál
- Megoldásban int[]-be van beolvasva
- Ez mellett még ki is kell iratni, hogy hány db mélység adat lett beolvasva

### 2. Feladat:
- Bekell kérni egy távolság értéket a felhasználótól
- Nem szabad elfelejteni, hogy a 0-ás indexelés miatt el kell venni 1-et a bekért értékből
- Ezt az indexet kell felhasználni az érték kikéréshez, majd kiiratni

### 3. Feladat:
- Érintetlen felület száma = ahol az adott mélység 0 a fájlban
- Érintetlen felszín százaléka = Érintetlen felület száma / összes beolvasott mélység db száma * 100

### 4. Feladat:
- Innestől vált a feladat retard módra:
- Én a megoldásban int[]-ökbe gyűjtöttem ki az összes gödröt
- 1 gödör megkereséséhez 2 db index kell: a gödör kezdő és a záró indexe
- Kezdő index: ahol az adott helyen 0 és a következő helyen nem 0
- Záró index: ahol az adott helyen nem 0 és a következő helyen 0
- Ezeket az index párosokat felhasználva kilehet gyűjteni a gödröket
- Ez a feladat csak annyit kér, hogy ezeket a gödröket írjuk ki a fájlba, de így megoldva a feladat többi része egyszerűbb lesz

### 5. Feladat:
- Ha az előző feladatban jól csináltunk mindent, akkor csak kikell iratnunk a gödrök számát

### 6. Feladat:
- Ez a feladat 2 irányba indulhat el:
- A 2. feladatban bekért indexet felkell használni és megnézni hogy az adott helyen 0 érték van e (azaz nincs gödör)
- Ha 0 érték van akkor egyszerűen kiírjuk, hogy itt nincs gödör
- Egyéb esetben pedig megyünk 200 millió alfeladatra...

### 6. a) Feladat:
- Itt megkell keresni a kezdő és záró távolságát a gödörnek
- Ehhez nekünk csak annyit kell tenni, hogy a kezdő és záró gödör indexpárjaink közül megkeressük az a párost, ami között van a bekért távolság indexe
- Ebből nekünk a kezdő és záró távolság kiiratása triviális

### 6. b) Feladat:
- Itt először megkeressük a legmélyebb pontnak az indexét
- Ezután ennek az indexnek kell figyelni a jobb és bal oldalait:
- Ha a gödör bal szélétől indulunk a legmélyebb pont indexéig az elemeknek folyamatosan nőniük kell (bal oldal)
- Ha a legmélyebb pont indexétől indulunk el a gödör jobb oldali széléig az elemeknek folyamatosan csökkeniük kell (jobb oldal)
- Ha mind a 2 feltétel igaz akkor folyamatosan mélyül, egyébként nem

### 6. c) Feladat:
- Itt annyi, hogy amit találtunk legnagyobb mélységet kiiratjuk

### 6. d) Feladat:
- Térfogat = A gödör összes mélységértékének az összege szorozva 10-el
- Ezt kell kiiratni

### 6. e) Feladat:
- Fogjuk a gödör kezdő távolságát, hozzáadunk 1-et
- Vesszük a záró távolságot és az előbb számolt értéket, kivonjuk egymásból, szorozzuk 10-el
- Térfogatból elvesszük az előbb kapott értéket
- Ezt kell kiiratni, ez lesz a 'Vízmennyiség'