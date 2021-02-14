from dateutil.parser import parse as parsedate
from collections import Counter

with open("EUcsatlakozas.txt") as file:
    lines = file.readlines()
    splitLines = (k.split(";") for k in lines)
    csatlakozasok = dict(((k[0], parsedate(k[1])) for k in splitLines))

ketezerhetbenCsatlakozottakSzama = sum(1 for k in csatlakozasok.values() if k.year == 2007)

print(f"3. Feladat: 2018-ig csatlakozott országok száma: {len(csatlakozasok)}")
print(f"4. Feladat: 2007-ben csatlakozott országok száma: {ketezerhetbenCsatlakozottakSzama}")
print(f'5. Feladat: Magyarország csatlakozása: {csatlakozasok["Magyarország"].date()}')

voltemajusban = any(k for k in csatlakozasok.values() if k.month == 5)
if voltemajusban:
    print("6. Feladat: Volt májusban csatlakozás")
else:
    print("6. Feladat: Nem volt májusban csatlakozás")

utolsoOrszag = max((k for k in csatlakozasok.items()), key = lambda k: k[1])[0]
print(f"7. Feladat: Utoljára csatlakozott: {utolsoOrszag}")

stat = Counter(k.year for k in csatlakozasok.values())
for ev, db in stat.items():
    print(f"{ev} - {db} ország")