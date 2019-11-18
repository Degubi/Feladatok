# -*- coding: UTF-8 -*-
from dateutil.parser import parse as parsedate

def csatlakozassa(line):
    split = line.split(";")
    return (split[0], parsedate(split[1]))

def groupbycount(extractor, data):
    returndata = {}
    
    for key, value in data.items():
        extracted = extractor(key, value)
        returndata[extracted] = returndata.get(extracted, 0) + 1
    return returndata


with open("EUcsatlakozas.txt") as file: lines = file.read().splitlines()
csatlakozasok = dict(map(csatlakozassa, lines))

print(f"3. Feladat: 2018-ig EU államok száma: {len(csatlakozasok)}");

ketezerhetben = sum(1 for _ in filter(lambda datum: datum.year == 2007, csatlakozasok.values()))
print(f"4. Feladat: 2007-ben csatlakozott országok száma: {ketezerhetben}")
print(f'5. Feladat: Magyarország csatlakozása: {csatlakozasok["Magyarország"].date()}')

if sum(1 for _ in filter(lambda datum: datum.month == 5, csatlakozasok.values())) > 0:
    print("6. Feladat: Volt májusban csatlakozás")
else:
    print("6. Feladat: Nem volt májusban csatlakozás")

sorbarendezett = sorted(csatlakozasok.items(), key = lambda kv: kv[1])
utolso = sorbarendezett[-1][0]
print(f"7. Feladat: Utoljára csatlakozott: {utolso}")

evcsoportositas = groupbycount(lambda orszag, datum: datum.year, csatlakozasok)
print("8. Feladat:")
for ev, db in evcsoportositas.items(): print(f"{ev} - {db} ország")