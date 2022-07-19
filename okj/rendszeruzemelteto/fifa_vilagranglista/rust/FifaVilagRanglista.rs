use std::fs::File;
use std::io::{ BufReader, BufRead };

use encoding_rs::WINDOWS_1252;
use encoding_rs_io::DecodeReaderBytesBuilder;
use itertools::Itertools;

struct Eredmeny {
    csapat: String,
    helyezes: i32,
    valtozas: i32,
    pontszam: i32
}

fn create_eredmeny(line: &String) -> Eredmeny {
    let split = line.split(';').collect::<Vec<&str>>();

    Eredmeny {
        csapat: split[0].to_string(),
        helyezes: split[1].parse::<i32>().unwrap(),
        valtozas: split[2].parse::<i32>().unwrap(),
        pontszam: split[3].parse::<i32>().unwrap()
    }
}

fn main() {
    let input_file = File::open("fifa.txt").unwrap();
    let eredmenyek = BufReader::new(DecodeReaderBytesBuilder::new().encoding(Some(WINDOWS_1252)).build(input_file))
                              .lines()
                              .skip(1)
                              .map(|k| create_eredmeny(&k.unwrap()))
                              .collect::<Vec<Eredmeny>>();

    println!("3. Feladat: Csapatok száma: {}", eredmenyek.len());

    let pontszamok = eredmenyek.iter()
                               .map(|k| k.pontszam as f32)
                               .collect::<Vec<f32>>();

    println!("4. Feladat: Átlagpontszám: {:.2}", pontszamok.iter().sum::<f32>() / pontszamok.len() as f32);

    match eredmenyek.iter().max_by_key(|k| k.valtozas) {
        Some(k) => println!("5. Feladat: Legtöbb helyet javító csapat: {}, helyezés: {}, pontszám: {}", k.csapat, k.helyezes, k.pontszam),
        None => {}
    }

    let volt_e_mo = eredmenyek.iter().any(|k| k.csapat == "Magyarország");

    println!("{}", if volt_e_mo { "6. Feladat: Csapatok között van Magyarország" } else { "6. Feladat: Csapatok között nincs Magyarország" });
    println!("7. Feladat:");

    eredmenyek.iter()
              .sorted_by_key(|k| k.valtozas)
              .group_by(|k| k.valtozas)
              .into_iter()
              .map(|(valtozas, items)| (valtozas, items.count()))
              .filter(|(_, count)| *count > 1)
              .for_each(|(valtozas, count)| println!("    {} helyet változott: {} csapat", valtozas, count));
}