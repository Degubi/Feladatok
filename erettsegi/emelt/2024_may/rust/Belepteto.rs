use std::{fs::File, io::{stdin, BufRead, BufReader, Write}};
use chrono::NaiveTime;
use itertools::Itertools;

fn main() {
    let mozgasok = BufReader::new(File::open("bedat.txt").unwrap())
                            .lines()
                            .map(|k| {
                                let line = k.unwrap();
                                let split = line.split(' ').into_iter().collect::<Vec<_>>();

                                Mozgas { azonosito: split[0].to_string(), idopont: split[1].parse().unwrap(), tipus: split[2].parse().unwrap()}
                            })
                            .collect::<Vec<_>>();

    println!("2. Feladat: Első belépő: {}, utolsó kilépő: {} ", mozgasok[0].idopont, mozgasok[mozgasok.len() - 1].idopont);


    let keso_kezdet = NaiveTime::from_hms_opt(7, 50, 0).unwrap();
    let keso_zaras = NaiveTime::from_hms_opt(8, 15, 0).unwrap();

    let kesok_fileba = mozgasok.iter()
                               .filter(|k| k.idopont > keso_kezdet && k.idopont < keso_zaras)
                               .map(|k| format!("{} {}", k.idopont, k.azonosito))
                               .join("\n");

    write!(File::create("kesok.txt").unwrap(), "{}", kesok_fileba);

    let menzasok_szama = mozgasok.iter()
                                 .filter(|k| k.tipus == 3)
                                 .count();

    println!("4. Feladat: Menzások száma: {menzasok_szama}");

    let kolcsonzesek_szama = mozgasok.iter()
                                     .filter(|k| k.tipus == 4)
                                     .map(|k| &k.azonosito)
                                     .unique()
                                     .count();

    println!("5. Feladat: Könyvtári kölcsönzések száma: {kolcsonzesek_szama}\n{}, mint a menzán", if kolcsonzesek_szama > menzasok_szama { "Többen voltak" } else { "Nem voltak többen" });

    let szunet_kezdete = NaiveTime::from_hms_opt(10, 45, 0).unwrap();
    let kapu_zaras = NaiveTime::from_hms_opt(10, 50, 0).unwrap();
    let szunet_vege = NaiveTime::from_hms_opt(11, 0, 0).unwrap();

    let szunetbe_ismert_kilepok = mozgasok.iter()
                                          .filter(|k| k.idopont > szunet_kezdete && k.idopont < szunet_vege)
                                          .filter(|k| k.tipus == 2)
                                          .map(|k| &k.azonosito)
                                          .collect::<Vec<_>>();

    let szunet_elott_mar_belepok = mozgasok.iter()
                                           .filter(|k| k.idopont < szunet_kezdete)
                                           .map(|k| &k.azonosito)
                                           .collect::<Vec<_>>();

    let szunet_vegeig_belepok = mozgasok.iter()
                                        .filter(|k| k.idopont > kapu_zaras && k.idopont < szunet_vege)
                                        .filter(|k| k.tipus == 1)
                                        .map(|k| &k.azonosito)
                                        .filter(|k| !szunetbe_ismert_kilepok.contains(k) && szunet_elott_mar_belepok.contains(k))
                                        .join(" ");

    println!("6. Feladat: {szunet_vegeig_belepok}");
    println!("7. Feladat: Kérem egy tanuló azonosítóját!");

    let mut bekert_azonosito_str = String::new();
    stdin().read_line(&mut bekert_azonosito_str);

    let bekert_azonosito = bekert_azonosito_str.trim_end();
    let bekert_tanulo_mozgasai = mozgasok.iter()
                                         .filter(|k| k.azonosito == bekert_azonosito)
                                         .collect::<Vec<_>>();

    let eltelt_ido = bekert_tanulo_mozgasai[bekert_tanulo_mozgasai.len() - 1].idopont - bekert_tanulo_mozgasai[0].idopont;

    println!("Eltelt idő: {} óra {} perc", eltelt_ido.num_hours(), eltelt_ido.num_minutes() - (eltelt_ido.num_hours() * 60));
}

struct Mozgas {
    azonosito: String,
    idopont: NaiveTime,
    tipus: i32
}