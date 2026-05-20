use std::fs::File;
use std::io::{ BufReader, BufRead, stdin, Write };

use itertools::Itertools;

fn main() {
    let melysegek = BufReader::new(File::open("melyseg.txt").unwrap())
                             .lines()
                             .map(|k| k.unwrap().parse::<i32>().unwrap())
                             .collect::<Vec<_>>();

    println!("1. Feladat: {}", melysegek.len());
    println!("2. Feladat: Írjon be egy távolságértéket!");

    let mut bekert_tavolsag_indexe_str = String::new();
    stdin().read_line(&mut bekert_tavolsag_indexe_str);

    let bekert_tavolsag_indexe = bekert_tavolsag_indexe_str.trim_end().parse::<usize>().unwrap() - 1;
    let melyseg_a_bekert_helyen = melysegek[bekert_tavolsag_indexe];

    println!("A felszín {}m mélyen van", melyseg_a_bekert_helyen);

    let erintetlen_felulet_szam = melysegek.iter()
                                           .filter(|k| **k == 0)
                                           .count();

    println!("3. Feladat: Érintetlen felszín: {:.2}%", erintetlen_felulet_szam as f32 / melysegek.len() as f32 * 100f32);

    let godor_kezdo_zaro_indexek = (0 .. melysegek.len() - 1).filter(|i| (melysegek[*i] == 0 && melysegek[*i + 1] != 0) || (melysegek[*i] != 0 && melysegek[*i + 1] == 0))
                                                             .map(|i| i + 1)
                                                             .collect::<Vec<_>>();

    let godrok = (0 .. godor_kezdo_zaro_indexek.len()).step_by(2)
                                                      .map(|i| &melysegek[godor_kezdo_zaro_indexek[i] .. godor_kezdo_zaro_indexek[i + 1]])
                                                      .collect::<Vec<&[i32]>>();
    let godrok_fileba = godrok.iter()
                              .map(|k| k.iter().join(" "))
                              .join("\n");

    write!(File::create("godrok.txt").unwrap(), "{}", godrok_fileba);
    println!("5. Feladat: Gödrök száma: {}", godrok.len());

    if melyseg_a_bekert_helyen == 0 {
        println!("6. Feladat: Az adott helyen nincs gödör");
    }else{
        let bekert_godor_index = (0 .. godor_kezdo_zaro_indexek.len()).step_by(2)
                                .find(|i| bekert_tavolsag_indexe >= godor_kezdo_zaro_indexek[*i] && bekert_tavolsag_indexe <= godor_kezdo_zaro_indexek[*i + 1])
                                .unwrap() / 2;

        let bekert_hely_kezdo_godor_tavolsag = (godor_kezdo_zaro_indexek[bekert_godor_index] + 1) as i32;
        let bekert_hely_zaro_godor_tavolsag = godor_kezdo_zaro_indexek[bekert_godor_index + 1] as i32;

        println!("    a) Gödör kezdete: {}m, vége: {}m", bekert_hely_kezdo_godor_tavolsag, bekert_hely_zaro_godor_tavolsag);

        let a_godor = godrok[bekert_godor_index];
        let legmelyebb_pont_index = (0 .. a_godor.len())
                                    .max_by_key(|i| a_godor[*i])
                                    .unwrap();

        let bal_szeltol_legnagyobbig_no = (0 .. legmelyebb_pont_index - 1).all(|i| a_godor[i] <= a_godor[i + 1]);
        let legnagyobbtol_jobb_szelig_csokken = (legmelyebb_pont_index + 1 .. a_godor.len() - 1).all(|i| a_godor[i] >= a_godor[i + 1]);

        println!("    b) {}", if bal_szeltol_legnagyobbig_no && legnagyobbtol_jobb_szelig_csokken { "Folyamatosan Mélyül" } else { "Nem mélyül folyamatosan" });
        println!("    c) Legnagyobb méység: {}m", a_godor[legmelyebb_pont_index]);

        let terfogat = a_godor.iter().sum::<i32>() * 10;
        let vizmennyiseg = terfogat - 10 * (bekert_hely_zaro_godor_tavolsag - bekert_hely_kezdo_godor_tavolsag + 1);

        println!("    d) Térfogat: {}m^3", terfogat);
        println!("    e) Vízmennyiség: {}m^3", vizmennyiseg);
    }
}