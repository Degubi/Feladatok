from dataclasses import dataclass
from datetime import time, timedelta

@dataclass
class Mozgas:
    azonosito: str
    idopont: time
    tipus: int


with open('bedat.txt') as bedat:
    mozgast_keszit = lambda split: Mozgas(split[0], time.fromisoformat(split[1]), int(split[2]))
    mozgasok = [ mozgast_keszit(k.split(' ')) for k in bedat.readlines() ]

print(f'2. Feladat: Első belépő: {mozgasok[0].idopont}, utolsó kilépő: {mozgasok[-1].idopont}')

keso_kezdet = time(7, 50)
keso_zaras = time(8, 15)
kesok = (f'{k.idopont} {k.azonosito}' for k in mozgasok if k.idopont > keso_kezdet and k.idopont < keso_zaras)

with open('kesok.txt', 'w') as output: output.writelines(kesok)

menzasok_szama = sum(1 for k in mozgasok if k.tipus == 3)

print(f'4. Feladat: Menzások száma: {menzasok_szama}')

kolcsonzesek_szama = len(set(k.azonosito for k in mozgasok if k.tipus == 4))

print(f'5. Feladat: Könyvtári kölcsönzések száma: {kolcsonzesek_szama}.\n{"Többen voltak" if kolcsonzesek_szama > menzasok_szama else "Nem voltak többen"}, mint a menzán')

szunet_kezdete = time(10, 45)
kapu_zaras = time(10, 50)
szunet_vege = time(11, 0)

szunetbe_ismert_kilepok = [ k.azonosito for k in mozgasok if k.idopont > szunet_kezdete and k.idopont < szunet_vege and k.tipus == 2 ]
szunet_elott_mar_belepok = [ k.azonosito for k in mozgasok if k.idopont < szunet_kezdete ]
szunet_vegeig_belepok = (k.azonosito for k in mozgasok if k.idopont > kapu_zaras and k.idopont < szunet_vege and k.tipus == 1 and \
                                                          not k.azonosito in szunetbe_ismert_kilepok and k.azonosito in szunet_elott_mar_belepok)

print(f'6. Feladat: {str.join(" ", szunet_vegeig_belepok)}')

bekert_azonosito = input('7. Feladat: Kérem egy tanuló azonosítóját! ')
bekert_tanulo_mozgasai = [ k for k in mozgasok if k.azonosito == bekert_azonosito ]
utolso_ido = bekert_tanulo_mozgasai[-1].idopont
elso_ido = bekert_tanulo_mozgasai[1].idopont
eltelt_ido_mp = (timedelta(hours = utolso_ido.hour, minutes = utolso_ido.minute) - timedelta(hours = elso_ido.hour, minutes = elso_ido.minute)).total_seconds()

print(f'Eltelt idő: {int(eltelt_ido_mp / 3600)} óra {int(eltelt_ido_mp / 60) % 60} perc')