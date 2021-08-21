from datetime import date
from collections import Counter

URES_RAJTSZAM = -1

class Pilota:

    def __init__(self, line):
        split = line.split(';')

        self.nev = split[0]
        self.szuletesiDatum = date.fromisoformat(split[1].replace('.', '-'))
        self.nemzetiseg = split[2]
        self.rajtszam = URES_RAJTSZAM if line[-1] == ';' else int(split[3])

with open('pilotak.csv', encoding = 'UTF-8') as file:
    file.readline()

    pilotak = [ Pilota(k.strip()) for k in file.readlines() ]

print(f'3. Feladat: Adatsorok száma: {len(pilotak)}')
print(f'4. Feladat: Utolsó pilóta neve: {pilotak[-1].nev}')
print('5. Feladat:')

for pilota in pilotak:
    if pilota.szuletesiDatum.year >= 1800 and pilota.szuletesiDatum.year <= 1900:
        print(f'    {pilota.nev} ({pilota.szuletesiDatum})')

legkisebb_rajtszamu_pilota = min((k for k in pilotak if k.rajtszam != URES_RAJTSZAM), key = lambda k: k.rajtszam)

print(f'6. Feladat: {legkisebb_rajtszamu_pilota.nemzetiseg}')
print('7. Feladat:')

rajtszam_stat = Counter(k.rajtszam for k in pilotak if k.rajtszam != URES_RAJTSZAM)

for (rajtszam, dbSzam) in rajtszam_stat.items():
    if dbSzam > 1:
        print(f'{rajtszam}', end = ' ')