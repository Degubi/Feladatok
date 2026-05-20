use std::collections::HashMap;
use std::fs::File;
use std::io::{ BufReader, BufRead, Write, stdin };

use itertools::Itertools;

struct Telek {
    adoszam: i32,
    utca_nev: String,
    haz_szam: String,
    adosav: String,
    terulet: i32
}

fn create_telek(line: &String) -> Telek {
    let split = line.split(' ').collect::<Vec<_>>();

    Telek {
        adoszam: split[0].parse::<i32>().unwrap(),
        utca_nev: split[1].to_string(),
        haz_szam: split[2].to_string(),
        adosav: split[3].to_string(),
        terulet: split[4].parse::<i32>().unwrap(),
    }
}

fn ado(telek: &Telek, fizetendo_ado_savonkent: &HashMap<&str, i32>) -> i32 {
    let mennyiseg = fizetendo_ado_savonkent[telek.adosav.as_str()] * telek.terulet;

    if mennyiseg < 10000 { 0 } else { mennyiseg }
}

fn main() {
    let lines = BufReader::new(File::open("utca.txt").unwrap())
                         .lines()
                         .map(|k| k.unwrap())
                         .collect::<Vec<_>>();

    let first_line_split = lines[0].split(' ')
                                   .map(|k| k.parse::<i32>().unwrap())
                                   .collect::<Vec<_>>();

    let fizetendo_ado_savonkent = HashMap::from([ ("A", first_line_split[0]), ("B", first_line_split[1]), ("C", first_line_split[2]) ]);
    let telkek = lines.iter()
                      .skip(1)
                      .map(|k| create_telek(&k))
                      .collect::<Vec<_>>();

    println!("2. Feladat: Telkek száma: {}", telkek.len());
    println!("3. Feladat: Írj be 1 adószámot!");

    let mut bekert_adoszam_str = String::new();
    stdin().read_line(&mut bekert_adoszam_str);

    let bekert_adoszam = bekert_adoszam_str.trim_end().parse::<i32>().unwrap();
    let bekert_telkei = telkek.iter()
                              .filter(|k| k.adoszam == bekert_adoszam)
                              .collect::<Vec<_>>();

    if bekert_telkei.len() == 0 {
        println!("Nem szerepel az adatállományban");
    }else{
        bekert_telkei.iter().for_each(|k| println!("{} {}", k.utca_nev, k.haz_szam));
    }

    println!("5. Feladat:");

    telkek.iter()
          .sorted_by_key(|k| &k.adosav)
          .group_by(|k| &k.adosav)
          .into_iter()
          .for_each(|(adosav, telks)| {
            let savhoz_telkek = telks.collect::<Vec<_>>();
            let ossz_ado = savhoz_telkek.iter()
                                        .map(|k| ado(k, &fizetendo_ado_savonkent))
                                        .sum::<i32>();

            println!("{} sáv: {} telek, adó: {}", adosav, savhoz_telkek.len(), ossz_ado);
    });

    println!("6. Feladat: Több sávba sorolt utcák:");

    telkek.iter()
          .sorted_by_key(|k| &k.utca_nev)
          .group_by(|k| &k.utca_nev)
          .into_iter()
          .map(|(utca, telkek_utcahoz)| (utca, telkek_utcahoz.unique_by(|k| &k.adosav).count()))
          .filter(|(_, telek_szam)| *telek_szam > 1)
          .for_each(|(telek, _)| println!("{}", telek));

    let to_file = telkek.iter()
                        .sorted_by_key(|k| k.adoszam)
                        .group_by(|k| k.adoszam)
                        .into_iter()
                        .map(|(adoszam, telkek_adoszamhoz)| format!("{} {}", adoszam, telkek_adoszamhoz.map(|k| ado(k, &fizetendo_ado_savonkent)).sum::<i32>()))
                        .join("\n");

    write!(File::create("fizetendo.txt").unwrap(), "{}", to_file);
}