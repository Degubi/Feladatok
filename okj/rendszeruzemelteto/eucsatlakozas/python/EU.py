from datetime import date
from collections import Counter

class Csatlakozas:

    def __init__(self, line: str):
        split = line.strip().split(';')

        self.orszag = split[0]
        self.datum = date.fromisoformat(split[1].replace('.', '-'))

with open('EUcsatlakozas.txt') as file:
    csatlakozasok = [ Csatlakozas(k) for k in file.readlines() ]

print(f'3. Feladat: 2018-ig csatlakozott országok száma: {len(csatlakozasok)}')

ketezerhetben_csatlakozottak_szama = sum(1 for k in csatlakozasok if k.datum.year == 2007)
print(f'4. Feladat: 2007-ben csatlakozott országok száma: {ketezerhetben_csatlakozottak_szama}')

magyarorszag_csat = next(k for k in csatlakozasok if k.orszag == 'Magyarország')
print(f'5. Feladat: Magyarország csatlakozása: {magyarorszag_csat.datum}')

voltemajusban = any(k for k in csatlakozasok if k.datum.month == 5)
print(f'6. Feladat: {"Volt" if voltemajusban else "Nem volt"} májusban csatlakozás')

utolso_csatlakozas = max((k for k in csatlakozasok), key = lambda k: k.datum)
print(f'7. Feladat: Utoljára csatlakozott: {utolso_csatlakozas.orszag}')

stat = Counter(k.datum.year for k in csatlakozasok)
for ev, db in stat.items():
    print(f'{ev} - {db} ország')