from datetime import datetime, time
from dataclasses import dataclass
from functools import reduce

@dataclass
class Belepes:
    idobelyeg: datetime
    azonosito: str
    helyszin_azonosito: int
    jegytipus: str

def read_file_lines(path: str):
    with open(path) as inp: return inp.readlines()


muzdat_lines = read_file_lines('muzdat.txt')
arak = muzdat_lines[0].split(' ')
format_time = lambda k: k.strftime('%H:%M')

teljes_kastelyjegy_ar = int(arak[0])
teljes_kiallitohely_ar = int(arak[1])
teljes_napijegy_ar = int(arak[2])
muzeumok_nevei = read_file_lines('muznev.txt')[0].strip().replace('"', '').split(', ')
kastely_nyitas = datetime(2026, 10, 22, 10, 0)
kastely_zaras = datetime(2026, 10, 22, 18, 0)

create_belepes = lambda k: Belepes(datetime.fromisoformat(f'2026-10-22 {k[0]}'), k[1], int(k[2]), k[3])
belepesek = [ create_belepes(k.strip().split(' ')) for k in muzdat_lines[1 : ] ]

print(f'2. Feladat: Az adott napon {len(belepesek)} látogató volt')
print('3. Feladat:')
print(f'Az első látogató {format_time(belepesek[0].idobelyeg)}-kor lépett a(z) {muzeumok_nevei[belepesek[0].helyszin_azonosito]} helyszínre')
print(f'Az utolsó látogató {format_time(belepesek[-1].idobelyeg)}-kor lépett a(z) {muzeumok_nevei[belepesek[-1].helyszin_azonosito]} helyszínre')

bekert_jegy_sorszam = input('4. Feladat: Kérem a jegy sorszámát! ')

for belepes in belepesek:
    if belepes.azonosito == bekert_jegy_sorszam:
        print(f'{format_time(belepes.idobelyeg)} {muzeumok_nevei[belepes.helyszin_azonosito]}')

utolso_jegyvasarlas = next(k for k in reversed(belepesek) if k.jegytipus != '-')
ido_zarasig = kastely_zaras - utolso_jegyvasarlas.idobelyeg
orak_zarasig, percek_zarasig = divmod(int(ido_zarasig.seconds) // 60, 60)

print(f'Az utolsó vásárló {orak_zarasig}:{percek_zarasig} perccel a zárás előtt vett jegyet')

calculate_ar = lambda k: (0 if k.jegytipus == '-' else \
                          teljes_napijegy_ar if k.jegytipus.endswith('N') else \
                          teljes_kastelyjegy_ar if k.helyszin_azonosito == 0 else teljes_kiallitohely_ar \
                         ) // (2 if k.jegytipus.startswith('K') else 1)

teljes_bevetel = sum(calculate_ar(k) for k in belepesek)

print(f'6. Feladat: A teljes bevétel {teljes_bevetel} Ft volt')

napi_jegyek = 0
nemnapi_jegyek = 0
for belepes in belepesek:
    if belepes.jegytipus.endswith('N'):
        if belepes.helyszin_azonosito == 0:
            napi_jegyek += 1
        else:
            nemnapi_jegyek += 1

print(f'7. Feladat: A napijegyet váltók általában{"" if napi_jegyek > nemnapi_jegyek else "nem"} a Kastélyban kezdtek')

orankenti_stat = {}
for belepes in belepesek:
    if belepes.helyszin_azonosito == 0:
        key = (belepes.idobelyeg - kastely_nyitas).total_seconds() // 3600 + kastely_nyitas.hour

        orankenti_stat[key] = orankenti_stat.get(key, 0) + 1

with open('stat.txt', 'w') as stat_out:
    stat_out.write('óra\tfő\n')

    for k in range(10, 18):
        stat_out.write(f'{k}\t{orankenti_stat.get(k, 0)}\n')

print('9. Feladat: Jutalmat kapnak\nidőpont\tjegy\tkiállítás')

azonosito_csoportok = {}
for belepes in belepesek:
    csoport = azonosito_csoportok.get(belepes.azonosito, list())
    csoport.append(belepes)

    azonosito_csoportok[belepes.azonosito] = csoport

napijegy_csoportok_utolsoi = []

for csoport in azonosito_csoportok.values():
    if len(csoport) == len(muzeumok_nevei) and any(k for k in csoport if k.jegytipus.endswith('N')):
        napijegy_csoportok_utolsoi.append(csoport[-1])

napijegy_csoportok_utolsoi.sort(key = lambda k: k.idobelyeg)

for utolso_napi_jegyek in napijegy_csoportok_utolsoi:
    print(f'{format_time(utolso_napi_jegyek.idobelyeg)}\t{utolso_napi_jegyek.azonosito}\t{muzeumok_nevei[utolso_napi_jegyek.helyszin_azonosito]}');
