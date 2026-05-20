### 1. Feladat:
- Szokásos beolvasás, minden sor 1 db adatot reprezentál

### 2. Feladat:
- Először a felhasználótól kell bekérni egy város kódját
- Ezt a kódot felhasználva vizsgáljuk azokat az adatokat, amiknek ez a városkódjuk
- Csak a legutolsó ilyen adatnak az időpontját kell kiiratni

### 3. Feladat:
- Mivel a legalacsonyabb és a legmagasabb hőmérsékletű adatnak kell kiiratni pár dolgát, érdemes sorbarendezni mindent a hőmérséklet alapján
- Sorbarendezés után a legelső és a legutolsó adatról kell mesélni kiiratásba

### 4. Feladat:
- Arra kell szűrni, hogy szélcsend volt-e. Szélcsend az azt jelenti, hogy az irány az '000' és az erősség 0 értékeket vesz fel
- 2 lehetőség van ezután:
- Ha szűrés után 1 db adatunk sincs akkor 'Nem volt szélcsend a mérések idején'-t kell kiírni
- Ha volt ilyen adat akkor a települést és az idő tagjaikat kell kiiratni minden egyes adatnak

### 5. Feladat:
- Ez a feladat rész összeköthető a 6. Feladat-al
- Érdemes település alapján össszecsoportosítani az összes adatot
- Ezután minden településre kell kiszámolni a hőmérséklettel kapcsolatban csomó dolgot:
- Ingadozás: Legmagasabb hőmérsékel és a legalacsonyabb különbsége
- Kerekített közép: Az átlag hőmérsékletet kell kerekíteni
- A kerekített közepet csak akkor kell kiiratni, ha minden órára van adat, egyébként 'NA'-t kell írni helyette
- Azt, hogy minden órára van-e adat az nagyon egyszerű, összegyűjtjük az összes egyedi órát és ha a darabszámuk 24 akkor van minden órára adat

### 6. Feladat:
- Az 5. Feladatban készített településenkénti csoportosítással ez a feladat nagyon egyszerű:
- Minden településre külön .txt fájlt kell létrehozni
- Ezekbe a fájlokba kell beleírni az időt, illetve '#' karaktereket annyiszor, ahanyas erősségű volt a szél (erre érdemes a nyelvben olyat keresni amivel könnyen lehet ismételni szöveget)