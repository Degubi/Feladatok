from functools import cmp_to_key
from typing import NamedTuple

class SebessegValtozas(NamedTuple):
    kezdeti_ido: int
    zaro_ido: int
    sebesseg_a_vegen: int

def egy_adott_valtozason_beluli_ido_e(idopont: int, valtozas: SebessegValtozas):
    return idopont >= valtozas.kezdeti_ido and idopont <= valtozas.zaro_ido

def ket_valtozas_kozotti_ido_e(idopont: int, korabbiValtozas: SebessegValtozas, kesobbiValtozas: SebessegValtozas):
    return idopont >= korabbiValtozas.zaro_ido and idopont <= kesobbiValtozas.kezdeti_ido

def sebesseg_adott_idopontban(t: int, vizsgalandoI: int, sebessegValtozasok: list[SebessegValtozas]):
    valtozas1 = sebessegValtozasok[vizsgalandoI - 1]
    valtozas2 = sebessegValtozasok[vizsgalandoI]
    v1 = float(valtozas1.sebesseg_a_vegen)
    v2 = float(valtozas2.sebesseg_a_vegen)
    t1 = float(valtozas2.kezdeti_ido)
    t2 = float(valtozas2.zaro_ido)

    return v1 + ((v2 - v1) / (t2 - t1)) * (t - t1)

def valtozason_belul_megtett_ut(valtozas1: SebessegValtozas, valtozas2: SebessegValtozas):
    v1 = float(valtozas1.sebesseg_a_vegen)
    v2 = float(valtozas2.sebesseg_a_vegen)
    t1 = float(valtozas2.kezdeti_ido)
    t2 = float(valtozas2.zaro_ido)

    return (v1 + v2) / 2 * (t2 - t1)

def valtozas_kozott_megtett_ut(valtozas1: SebessegValtozas, valtozas2: SebessegValtozas):
    return valtozas1.sebesseg_a_vegen * (valtozas2.kezdeti_ido - valtozas1.zaro_ido)


auto_azonosito = input('1. Feladat: Kérem adja meg az autó azonosítóját! ')

with open(f'{auto_azonosito}.txt') as txt_input:
    valtozast_keszit = lambda k: SebessegValtozas(int(k[0]), int(k[1]), int(k[2]))

    sebesseg_valtozasok = [ valtozast_keszit(k.split('\t')) for k in txt_input.readlines() ]

sebesseg_valtozasok.insert(0, SebessegValtozas(0, 0, 0))

print(f'2. Feladat: A {sebesseg_valtozasok[1].kezdeti_ido}. mp-ben indult el, a megfigyelés végén {sebesseg_valtozasok[-1].sebesseg_a_vegen} m/s sebességgel haladt')

atlepte_e = any(k for k in sebesseg_valtozasok if k.sebesseg_a_vegen > 14)

print(f'3. Feladat: Az autó {"átlépte" if atlepte_e else "nem lépte át"} a sebességhatárt')

idot_osszehasonlit = lambda k1, k2: (k1[1].kezdeti_ido - k1[0].zaro_ido) - (k2[1].kezdeti_ido - k2[0].zaro_ido)
leghosszabb_allt_valtozas_paros = max((k for k in zip(sebesseg_valtozasok[: -1], sebesseg_valtozasok[1 :]) if k[0].sebesseg_a_vegen == 0), key = cmp_to_key(idot_osszehasonlit))

print(f'4. Feladat: Leghosszabbatt állt idő {leghosszabb_allt_valtozas_paros[0].zaro_ido} és {leghosszabb_allt_valtozas_paros[1].kezdeti_ido} mp között volt')

vizsgalando_idopont = int(input('5. Feladat: Mikor vizsgáljuk a sebességet? '))

vizsgalando_i = next(i for i in range(0, len(sebesseg_valtozasok) - 1) if \
        egy_adott_valtozason_beluli_ido_e(vizsgalando_idopont, sebesseg_valtozasok[i]) or
        ket_valtozas_kozotti_ido_e(vizsgalando_idopont, sebesseg_valtozasok[i], sebesseg_valtozasok[i + 1]) or
        egy_adott_valtozason_beluli_ido_e(vizsgalando_idopont, sebesseg_valtozasok[i + 1]))


sebesseg_a_vizsgalt_idopontban = sebesseg_adott_idopontban(vizsgalando_idopont, vizsgalando_i, sebesseg_valtozasok) if egy_adott_valtozason_beluli_ido_e(vizsgalando_idopont, sebesseg_valtozasok[vizsgalando_i]) else \
                                 sebesseg_adott_idopontban(vizsgalando_idopont, vizsgalando_i + 1, sebesseg_valtozasok) if egy_adott_valtozason_beluli_ido_e(vizsgalando_idopont, sebesseg_valtozasok[vizsgalando_i + 1]) else \
                                 sebesseg_valtozasok[vizsgalando_i].sebesseg_a_vegen

print(f'Az autó sebessége ekkor {sebesseg_a_vizsgalt_idopontban} m/s volt')

osszes_megtett_ut = sum(valtozason_belul_megtett_ut(k[0], k[1]) + valtozas_kozott_megtett_ut(k[0], k[1]) for k in zip(sebesseg_valtozasok[: -1], sebesseg_valtozasok[1 :]))

print(f'6. Feladat: A megtett út: {osszes_megtett_ut} m')

fileba = sum([[ f'{k[1].kezdeti_ido}\t{k[1].sebesseg_a_vegen}', f'{k[1].zaro_ido}\t{k[1].sebesseg_a_vegen}'] for k in zip(sebesseg_valtozasok[: -1], sebesseg_valtozasok[1 :])], [])

with open(f'v{auto_azonosito}.txt', 'w') as out_kiir:
    out_kiir.writelines(fileba)

# 8. Feladathoz nem vagyok hajlandó excelezni, sorry.
