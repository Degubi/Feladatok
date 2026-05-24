def tavolsagga(betu: str):
    match betu:
        case 'U' | 'G': return 1
        case 'F': return 2
        case 'K': return 10
        case _: return 0

aktivitas = input('1. Feladat: Adja meg az aktivitását: ')
teljes_tavolsag = sum(tavolsagga(k) for k in aktivitas)
volt_e_minden_aktivitas = 'U' in aktivitas and 'G' in aktivitas and 'F' in aktivitas and 'K' in aktivitas
teljes_vegso_tavolsag = teljes_tavolsag + (10 if volt_e_minden_aktivitas else 0)

print(f'2. Feladat: Az elért távolság: {teljes_tavolsag}')
print(f'3. Feladat: {"Bravó! Jutalma még 10 km." if volt_e_minden_aktivitas else "Nem jár jutalom."}')
print(f'4. Feladat: Eredménye: {teljes_vegso_tavolsag}km. {"Gratulálok, kihívás teljesítve!" if teljes_vegso_tavolsag >= 40 else "Legközelebb sikerül!"}')
