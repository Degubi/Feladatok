[![Java](https://raw.githubusercontent.com/Degubi/Feladatok/badges/java.svg)](https://github.com/Degubi/Feladatok/search?l=java)
[![C#](https://raw.githubusercontent.com/Degubi/Feladatok/badges/cs.svg)](https://github.com/Degubi/Feladatok/search?l=C%23)
[![F#](https://raw.githubusercontent.com/Degubi/Feladatok/badges/fs.svg)](https://github.com/Degubi/Feladatok/search?l=F%23)
[![Python](https://raw.githubusercontent.com/Degubi/Feladatok/badges/py.svg)](https://github.com/Degubi/Feladatok/search?l=Python)

# Érettségi, OKJ és informatikai ismeretek vizsga Programozás megoldások.

- Mind a 3 (okj, infism, emelt é.) típusú feladatot tudom ajánlani mindenkinek, nagyon hasonló menetre megy az összes.
- Nem minden feladatból van megoldás az összes nyelven... A legelső megoldás mindig Java-ban készül el, azokból van a legtöbb.
- Java, C# és Python nyelveken általában 2 fajta megoldás van: Normál imperatív módon ciklusokkal, illetve funkcionális módszerrel.
- Nyelvenként szűrni a jobb oldali 'Languages' menüből lehet, rákell kattintani a nyelvre és csak azok a megoldások jelennek meg, amik abban a nyelvben vannak.

# Hiba beküldés:
- Nyitni kell 1 issue-t, hogy hol és mi a hiba, esetleg hogyan lehetne megjavítani
- Ha lehet deszkriptív leírásokat írjunk...

# Új megoldás beküldés:
- Nyitni kell 1 pull requestet, amiben benne van az új megoldás/hibajavítás
- Lehetőleg kövessük az eddigi megoldások stílusát, mappa elrendezését stb

# Új java verziókhoz:
- Java 11+:

```java
Files.writeString(dataStr, utvonalPath)       // Új
Files.write(dataStr.getBytes(), utvonalPath)  // Régi
```

```java
Path.of(utvonalStr)   // Új
Paths.get(utvonalStr) // Régi
```

```java
var x = 20       // Új
var y = "asd"    // Új
int x = 20       // Régi
String y = "asd" // Régi
```

- Java 14+:

```java
var y = "asd";

// Új
var x = switch(y) {
    case "asd" -> "moo";
    case "kek" -> "maa";
    default    -> "idk";
};

// Régi
String x;
switch(y) {
    case "asd": x = "moo"; break;
    case "kek": x = "maa"; break;
    default   : x = "idk";
}
```

```java
// Új
var hosszu = """
             Nagyon
             Szeretem
             A
             Hosszú
             Szövegeket
             """;

// Régi
var hosszu = "Nagyon\n" +
             "Szeretem\n" +
             "A\n" +
             "Hosszú\n" +
             "Szövegeket";
```

# C# Linq hasznosságok:
- IEnumerable generator:

```csharp
public static IEnumerable<T> Generate<T>(Func<T> generator) {
    while(true) yield return generator.Invoke();
}

// Használat:
Generate(() => Console.ReadLine())
```

- IEnumerable ForEach extension

```csharp
// Static classba kell tenni, ez a fv hiányzik a linq extensionok közül... -_-
public static void ForEach<T>(this IEnumerable<T> source, Action<T> action) {
    foreach (T element in source) action(element);
}
```

# Python hasznosságok:
- groupby

```python
from itertools import groupby

data = [('c', 1), ('a', 2), ('b', 3), ('b', 4)]

# groupby használat előtt a bemenetnek sorbarendezettnek kell lennie a kulcs alapján
groups = dict((k, list(v)) for k, v in groupby(data, lambda x: x[0]))

print(groups)       # {'c': [('c', 1)], 'a': [('a', 2)], 'b': [('b', 3), ('b', 4)]}
print(groups['c'])  # [('c', 1)]
```

- frequency map

```python
from collections import Counter

data = [('c', 1), ('a', 2), ('b', 3), ('b', 4)]

stat = Counter(k[0] for k in data)

print(stat)         # Counter({'b': 2, 'c': 1, 'a': 1})
print(stat['b'])    # 2
```

- dátumok és idő

```python
from datetime import time, date, datetime

print(time.fromisoformat('23:53'))                # .hour, .minute, .second
print(date.fromisoformat('2018-01-04'))           # .year, .month, .day
print(datetime.fromisoformat('2018-01-04 23:53'))
```