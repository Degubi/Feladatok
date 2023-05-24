use std::fs::File;
use std::io::{ BufReader, BufRead };

use encoding_rs::WINDOWS_1252;
use encoding_rs_io::DecodeReaderBytesBuilder;
use itertools::Itertools;
use chrono::{ NaiveDate, Datelike };

struct Csatlakozas {
    orszag: String,
    datum: NaiveDate
}

fn create_csatlakozas(line: &String) -> Csatlakozas {
    let split = line.split(';').collect::<Vec<_>>();

    Csatlakozas {
        orszag: split[0].to_string(),
        datum: NaiveDate::parse_from_str(split[1], "%Y.%m.%d").unwrap()
    }
}

fn main() {
    let input_file = File::open("EUcsatlakozas.txt").unwrap();
    let csatlakozasok = BufReader::new(DecodeReaderBytesBuilder::new().encoding(Some(WINDOWS_1252)).build(input_file))
                                 .lines()
                                 .map(|k| create_csatlakozas(&k.unwrap()))
                                 .collect::<Vec<_>>();

    println!("3. Feladat: 2018-ig EU államok száma: {}", csatlakozasok.len());

    let csatlakozott2007 = csatlakozasok.iter()
                                        .filter(|k| k.datum.year() == 2007)
                                        .count();

    println!("4. Feladat: 2007-ben csatlakozott országok száma: {}", csatlakozott2007);

    let magyarorszag_csat = csatlakozasok.iter()
                                         .filter(|k| k.orszag == "Magyarország")
                                         .find(|k| k.orszag == "Magyarország")
                                         .unwrap();

    println!("5. Feladat: Magyarország csatlakozása: {}", magyarorszag_csat.datum);

    let volt_e_majusban = csatlakozasok.iter().any(|k| k.datum.month() == 5);

    println!("6. Feladat: {} májusban csatlakozás", if volt_e_majusban { "Volt" } else { "Nem volt" });

    let utoljara_csatlakozott = csatlakozasok.iter()
                                             .max_by_key(|k| k.datum)
                                             .unwrap();

    println!("7. Feladat: Utoljára csatlakozott: {}", utoljara_csatlakozott.orszag);
    println!("8. Feladat:");

    csatlakozasok.iter()
                 .sorted_by_key(|k| k.datum.year())
                 .group_by(|k| k.datum.year())
                 .into_iter()
                 .for_each(|(ev, db)| println!("{} - {} db ország", ev, db.count()));
}