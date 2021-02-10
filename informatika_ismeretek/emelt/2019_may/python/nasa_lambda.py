from keres import Keres

with open("NASAlog.txt", "r") as file: lines = file.readlines()
keresek = list(map(Keres, lines))

print(f"5. Feladat: Kérések száma: {len(keresek)}")

osszmeret = sum(map(Keres.byteMeret, keresek))
print(f"6. Feladat: Összméret: {osszmeret} byte")

domainesek = sum(1 for keres in keresek if keres.domain())
print("8. Feladat: Domaines kérések: %.2f%%" % (domainesek / len(keresek) * 100))

stat = {}
for keres in keresek:
    stat[keres.httpKod] = stat.get(keres.httpKod, 0) + 1

for key, value in stat.items(): print(f"    {key}: {value} db")