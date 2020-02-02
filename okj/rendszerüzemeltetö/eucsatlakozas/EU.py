from dateutil.parser import parse as parsedate

csatlakozasok = {}
with open("EUcsatlakozas.txt") as file:
    lines = file.readlines()
    
    for line in lines:
        split = line.split(";")
        
        csatlakozasok[split[0]] = parsedate(split[1])


print(f"3. Feladat: 2018-ig csatlakozott országok száma: {len(csatlakozasok)}")

ketezerhetben = 0
for datum in csatlakozasok.values():
    if datum.year == 2007:
        ketezerhetben += 1
        
print(f"4. Feladat: 2007-ben csatlakozott országok száma: {ketezerhetben}")
print(f'5. Feladat: Magyarország csatlakozása: {csatlakozasok["Magyarország"].date()}')

voltemajusban = False
for datum in csatlakozasok.values():
    if datum.month == 5:
        voltemajusban = True
        break
    
if voltemajusban:
    print("6. Feladat: Volt májusban csatlakozás")
else:
    print("6. Feladat: Nem volt májusban csatlakozás")

utolsoorszag = list(csatlakozasok.keys())[0]
for orszag, datum in csatlakozasok.items():
    if datum > csatlakozasok[utolsoorszag]:
        utolsoorszag = orszag

print(f"7. Feladat: Utoljára csatlakozott: {utolsoorszag}")

stat = {}
for datum in csatlakozasok.values():
    ev = datum.year
    stat[ev] = stat.get(ev, 0) + 1

for ev, db in stat.items():
    print(f"{ev} - {db} ország")