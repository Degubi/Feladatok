use std::fs::File;
use std::io::{ BufReader, BufRead, stdin };

fn main() {
    let input_file = File::open("kep.txt").unwrap();
    let pixelek2D = BufReader::new(input_file)
                             .lines()
                             .map(|k| kepsort_beolvas(&k.unwrap()))
                             .collect::<Vec<_>>();

    println!("2. Feladat: Kérem egy képpont adatait!");

    let mut bekert_sor_str = String::new();
    let mut bekert_oszlop_str = String::new();

    println!("Sor:");
    stdin().read_line(&mut bekert_sor_str);

    println!("Oszlop:");
    stdin().read_line(&mut bekert_oszlop_str);

    let bekert_sor = bekert_sor_str.trim_end().parse::<usize>().unwrap();
    let bekert_oszlop = bekert_oszlop_str.trim_end().parse::<usize>().unwrap();
    let bekert_pixel = &pixelek2D[bekert_sor][bekert_oszlop];

    println!("4. Képpont színe: RGB({},{},{})", bekert_pixel.red, bekert_pixel.green, bekert_pixel.blue);

    let pixelek1D = pixelek2D.iter().flatten().collect::<Vec<_>>();
    let vilagos_keppontok_szama = pixelek1D.iter()
                                           .filter(|k| color_sum(k) > 600)
                                           .count();

    let legsotetebb_keppont_ertek = pixelek1D.iter()
                                             .map(|k| color_sum(k))
                                             .min().unwrap();

    println!("3. Feladat: Világos képpontok száma: {}", vilagos_keppontok_szama);
    println!("4. Feladat: Legsötétebb pont RGB összege: {}", legsotetebb_keppont_ertek);

    pixelek1D.iter()
             .filter(|k| color_sum(k) == legsotetebb_keppont_ertek)
             .for_each(|k| println!("RGB({},{},{})", k.red, k.green, k.blue));

    let hatar_oszlop_indexek = (0 .. pixelek2D.len()).filter(|k| hatar(*k, 10, &pixelek2D)).collect::<Vec<_>>();

    println!("6. Feladat: Felhő legfelső sora: {}, utolsó sora: {}", hatar_oszlop_indexek[0] + 1, hatar_oszlop_indexek.last().unwrap() + 1)
}

fn kepsort_beolvas(line: &String) -> Vec<Color> {
    let split = line.split(' ').collect::<Vec<_>>();

    (0 .. split.len())
    .step_by(3)
    .map(|i| Color {
        red: split[i].parse::<i32>().unwrap(),
        green: split[i + 1].parse::<i32>().unwrap(),
        blue: split[i + 2].parse::<i32>().unwrap()
    }).collect::<Vec<_>>()
}

fn hatar(sorszam: usize, elteres: i32, pixelek2D: &Vec<Vec<Color>>) -> bool {
    let sor = &pixelek2D[sorszam];

    (0 .. sor.len() - 1).any(|i| (sor[i].blue - sor[i + 1].blue).abs() > elteres)
}

fn color_sum(color: &Color) -> i32 { color.red + color.green + color.blue }

struct Color {
    red: i32,
    green: i32,
    blue: i32
}