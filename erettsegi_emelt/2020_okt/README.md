### 1. Feladat:
- Szokásos beolvasás, viszont figyelni kell, hogy 5 sor reprezentál 1 db adatot

### 2. Feladat:
- Kikell iratni, hogy hány olyan adat van, aminek ismert a dátuma.
- Beolvasáskor kezeljük le, hogy ismert e vagy sem (a nem ismerteknek HIANYZO_DATUM értéke van)

### 3. Feladat:
- Azt hogy látta-e a készítő a beolvasáskor tudjuk meg, minden 5. sor 1-es értéket vesz fel ha igen, 0-t ha nem
- Megkell számolni, hogy hányat látott, elosztani az összes adat számával és szorozni 100-al
- Ezt az értéket kell kiiratni 2 tizedesjegynyi pontossággal

### 4. Feladat:
- Az epizódonkénti hosszokat kell összeadni, de csak akkor ha a rendező látta
- A kiiratást a feladat nap óra perc formában kéri, szinte minden nyelvben van rá formázott kiiratásra

### 5. Feladat:
- Első részben a felhasználótól kell bekérni egy dátumot
- Ezzel a dátummal kell szűrni, úgy hogy csak azokat tartjuk meg ami ehhez a dátumhoz képest korábban vagy pont ugyanekkor volt az adásbakerülési ideje
- Ez mellett még szűrjük azt is, hogy nem látta a rendező
- Minden egyes ilyen elemet kell kiiratni (a feladat kiköti pontosan, hogy hogyan kell a kiiratást megformázni)

### 6. Feladat:
- 1 függvény pszeudokódját kapjuk, átkell írni normál kóddá
- Ezt a függvényt kell felhasználni majd a 7. Feladatban

### 7. Feladat:
- Első körben a felhasználótól kérünk be 1 db nap értéket
- Ezzel a nap értékkel szűrjük meg az adatainkat úgy, hogy azokat tartjuk, amiknek erre a napra esett az adásba kereülésük (A dátumot nappá konvertáljuk használva a 6. Feladat függvényét)
- Mivel csak a címeket kell kiiratnunk, kell végezni egy egyedi elem szűrést
- Ezeket az egyedi címeket kell kiiratni, kivéve ha nincs 1 db cím se amit kitudnánk írni, akkor 'Az adott napon nem kerül adásba sorozat' üzenetet kell kiiratni

### 8. Feladat:
- Ebben a feladatban minden címhez kikell számolni a hozzá tartozó összesített epizódonkénti hosszokat és magának a címnek a szereplési számát
- A feladat befejezéséhez a summa.txt fájlba kell írni, úgy hogy 1-1 sorban 1-1 'cím epizódonkénti_hossz szereplési_szám' szerepljen