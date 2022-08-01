from datetime import time, date, datetime
from math import ceil
from itertools import groupby

class Kolcsonzes:

    def __init__(self, line: str):
        split = line.split(';')

        self.nev = split[0]
        self.jarmuAzonosito = split[1]
        self.elvitelIdopont = time(int(split[2]), int(split[3]))
        self.visszahozatalIdopont = time(int(split[4]), int(split[5]))

with open('kolcsonzesek.txt', encoding = 'UTF-8') as file:
    file.readline()

    kolcsonzesek = [ Kolcsonzes(k) for k in file.readlines() ]

print(f'5. Feladat: Kölcsönzések száma: {len(kolcsonzesek)}')

bekert_nev = input('Írj be 1 nevet! ')
bekert_nevhez_tartozo_kolcsonzesek = [ k for k in kolcsonzesek if k.nev == bekert_nev ]
bekerthez_kiirando = 'Nem volt ilyen nevű kölcsönző' if len(bekert_nevhez_tartozo_kolcsonzesek) == 0 \
                     else '\n'.join(f'{k.elvitelIdopont}-{k.visszahozatalIdopont}' for k in kolcsonzesek)

print(bekerthez_kiirando)

bekert_idopont_parts = input('Adj meg 1 időpontot! (óra:perc) ').split(':')
bekert_idopont = time(int(bekert_idopont_parts[0]), int(bekert_idopont_parts[1]))

print('7. Feladat:')

for kolcsonzes in kolcsonzesek:
    if bekert_idopont > kolcsonzes.elvitelIdopont and bekert_idopont < kolcsonzes.visszahozatalIdopont:
        print(f'    {kolcsonzes.elvitelIdopont}-{kolcsonzes.visszahozatalIdopont}: {kolcsonzes.nev}')

calc_time_diff = lambda k: datetime.combine(date.min, k.elvitelIdopont) - datetime.combine(date.min, k.visszahozatalIdopont)
total_started_hours = sum(ceil((calc_time_diff(k).seconds // 60 % 60) / 30.0) for k in kolcsonzesek)

print(f'8. Feladat: A bevétel {total_started_hours * 2400}Ft')

with open('F.txt', 'w') as output:
    output.writelines(f'{k.elvitelIdopont}-{k.visszahozatalIdopont}: {k.nev}' for k in kolcsonzesek if k.jarmuAzonosito == 'F')

print('10. Feladat:')

kolcsonzesek.sort(key = lambda k: k.jarmuAzonosito)

stat = ((k, sum(1 for _ in v)) for k, v in groupby(kolcsonzesek, lambda k: k.jarmuAzonosito))

for azonosito, dbSzam in stat:
    print(f'    {azonosito}-{dbSzam}')