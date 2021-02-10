from keres import Keres

keresek = []
with open("NASAlog.txt", "r") as file:
    lines = file.readlines()

    for line in lines:
        keresek.append(Keres(line))

print(f"5. Feladat: Kérések száma: {len(keresek)}")

osszmeret = 0
for keres in keresek:
    osszmeret += keres.byteMeret()

print(f"6. Feladat: Összméret: {osszmeret} byte")

domainesek = 0
for keres in keresek:
    if keres.domain():
        domainesek += 1

print("8. Feladat: Domaines kérések: %.2f%%" % (domainesek / len(keresek) * 100))

stat = {}
for keres in keresek:
    stat[keres.httpKod] = stat.get(keres.httpKod, 0) + 1

for key, value in stat.items():
    print(f"    {key}: {value} db")