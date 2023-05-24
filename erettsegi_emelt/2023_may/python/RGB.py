from dataclasses import dataclass

@dataclass
class Color:
    red: int
    green: int
    blue: int

def color_sum(color: Color): return color.red + color.green + color.blue

def hatar(sorSzam: int, elteres: int, pixelek2D: list[list[Color]]):
    sor = pixelek2D[sorSzam]

    return any(True for i in range(0, len(sor) - 1) if abs(sor[i].blue - sor[i + 1].blue) > elteres)

def kepsort_beolvas(line: str):
    split = line.split(' ')

    return [ Color(red = int(split[i]), green = int(split[i + 1]), blue = int(split[i + 2])) for i in range(0, len(split)) if i % 3 == 0 ]


with open('kep.txt') as kep_input:
    pixelek2D = [ kepsort_beolvas(k) for k in kep_input.readlines() ]

print('2. Feladat: Kérem egy képpont adatait!')

bekertSorIndex = int(input('Sor: ')) - 1
bekertOszlopIndex = int(input('Oszlop: ')) - 1

print(f'Képpont színe:  {pixelek2D[bekertSorIndex][bekertOszlopIndex]}')

pixelek1D = [ l for k in pixelek2D for l in k ]
vilagosKeppontokSzama = sum(1 for k in pixelek1D if color_sum(k) > 600)
legsotetebbKeppontErtek = min(color_sum(k) for k in pixelek1D)

print(f'3. Feladat: Világos képpontok száma: {vilagosKeppontokSzama}')
print(f'4. Feladat: Legsötétebb pont RGB összege: {legsotetebbKeppontErtek}')

for color in pixelek1D:
    if color_sum(color) == legsotetebbKeppontErtek:
        print(color)

hatarOszlopIndexek = [ i for i in range(0, len(pixelek2D)) if hatar(i, 10, pixelek2D) ]

print(f'6. Feladat: Felhő legfelső sora: {hatarOszlopIndexek[0] + 1}, utolsó sora: {hatarOszlopIndexek[-1] + 1}')