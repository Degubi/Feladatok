use std::fs::File;
use std::io::{ BufReader, BufRead };

use chrono::NaiveDate;

use itertools::Itertools;

fn main() {
    let input_file = File::open("schumacher.csv").unwrap();
    let eredmenyek = BufReader::new(input_file)
                              .lines()
                              .skip(1)
                              .map(|k| create_eredmeny(&k.unwrap()))
                              .collect::<Vec<_>>();

    println!("2. Feladat: Adatsorok száma: {}", eredmenyek.len());
    println!("4. Feladat:");

    eredmenyek.iter()
              .filter(|k| k.dij_nev == "Hungarian Grand Prix")
              .filter(|k| k.helyezes != 0)
              .for_each(|k| println!("    {}: {}. hely", k.datum, k.helyezes));

    println!("5. Feladat:");

    eredmenyek.iter()
              .filter(|k| k.helyezes == 0)
              .sorted_by_key(|k| &k.vegeredmeny_statusz)
              .group_by(|k| &k.vegeredmeny_statusz)
              .into_iter()
              .for_each(|(status, stat)| {
                   let statuszhoz_statok = stat.collect::<Vec<_>>();

                   if statuszhoz_statok.len() > 2 {
                        println!("    {}: {}", status, statuszhoz_statok.len());
                   }
              });
}


fn create_eredmeny(line: &String) -> Eredmeny {
    let split = line.split(';').collect::<Vec<_>>();

    Eredmeny {
        datum: NaiveDate::parse_from_str(split[0], "%Y-%m-%d").unwrap(),
        dij_nev: split[1].to_string(),
        helyezes: split[2].parse::<i32>().unwrap(),
        befejezett_korok: split[3].parse::<i32>().unwrap(),
        szerzett_pontok: split[4].parse::<i32>().unwrap(),
        csapat: split[5].to_string(),
        vegeredmeny_statusz: split[6].to_string(),
    }
}

struct Eredmeny {
    datum: NaiveDate,
    dij_nev: String,
    helyezes: i32,
    befejezett_korok: i32,
    szerzett_pontok: i32,
    csapat: String,
    vegeredmeny_statusz: String
}