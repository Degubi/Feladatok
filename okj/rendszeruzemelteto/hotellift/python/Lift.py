from dataclasses import dataclass
from datetime import date
from collections import Counter

@dataclass
class Hasznalat:
    idopont: date
    kartya_sorszam: int
    indulo_szint: int
    celszint: int

def parse_int_or_default(num: str, default_value: int) :
    try:
        return int(num)
    except ValueError:
        return default_value


with open('lift.txt') as input_file:
    create_hasznalat = lambda split: Hasznalat(date.fromisoformat(split[0].removesuffix('.').replace('.', '-')), int(split[1]), int(split[2]), int(split[3]))
    hasznalatok = [ create_hasznalat(k.split(' ')) for k in input_file.readlines() ]

print(f'3. Feladat: Lift alkalmak száma: {len(hasznalatok)}')
print(f'4. Feladat: A korszak {hasznalatok[0].idopont}-től {hasznalatok[-1].idopont}-ig tartott')
print(f'5. Feladat: Max célszint: {max(k.celszint for k in hasznalatok)}')

be_kartya   = parse_int_or_default(input('Írj be egy kártyaszámot: '), 5)
be_celszint = parse_int_or_default(input('Írj be egy célszintet: '), 5)
utaztak_e = any(k.kartya_sorszam == be_kartya and k.celszint == be_celszint for k in hasznalatok)

print(f'7. Feladat: A {be_kartya} kártyával {"" if utaztak_e else "nem"} utaztak a {be_celszint}. emeletre')
print('8. Feladat')

for ido, db in Counter(k.idopont for k in hasznalatok).items():
    print(f'{ido} - {db}x')