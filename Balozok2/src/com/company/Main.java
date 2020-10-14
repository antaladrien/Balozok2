package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final String adatfajl = "c:\\Users\\user\\IdeaProjects\\balozok.txt";
    private int FIZETES_HATAR = 1000;
    private double BAL_VEGE_ESELY = 0.1;

    private List<Balozo> balozok = new ArrayList<>();

    public static void main(String[] args) {
        new Main().start();
    }

    private void start() {
        adatBevitel();
        resztvevok();
        balozas();
        eredmenyHirdetes();
        balkiralynok();
        osszBevetel();
        tancokSorrendje();
        fogyasztasiSorrend();
        szavazatokSzerintiSorrend();
        holgyvalasz();
    }

    //adatok beolvasasa
    private void adatBevitel() {
        try {
            Scanner sc = new Scanner(new File(adatfajl));
            String[] adatok;
            String sor;
            while (sc.hasNextLine()) {
                sor = sc.nextLine();
                try {
                    if (!sor.isEmpty()) {
                        adatok = sor.split(";");
                        switch (adatok[1]) {
                            case "lány":
                                balozok.add(new Lany(adatok[0],
                                        Integer.parseInt(adatok[2])));
                                break;
                            case "fiú":
                                balozok.add(new Fiu(adatok[0],
                                        Integer.parseInt(adatok[2])));
                                break;
                            default:
                                throw new Exception();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Hibás a " + sor + " adatsor.");
                }
            }
        } catch (Exception ex) {
            System.out.println("Hibás fájlmegadás");
            System.exit(-1);
        }
    }

    private void resztvevok() {
        System.out.println("\nA bál résztvevői:");
        for (Balozo balozo : balozok) {
            System.out.println(balozo.toString());
        }
    }

    private void balozas() {
        int fogyasztIndex, szavazIndex, kireIndex, tancolindex, penz;
        Lany lany;
        Balozo balozo;
        boolean vege = false;

        System.out.println("\nBál-szimuláció:");
        do {
            fogyasztIndex = (int) (Math.random() * balozok.size());
            szavazIndex = (int) (Math.random() * balozok.size());
            kireIndex = (int) (Math.random() * balozok.size());
            tancolindex = (int) (Math.random() * balozok.size());

            System.out.println("Szavazás:");
            if (balozok.get(kireIndex) instanceof Lany) {
                lany = (Lany) balozok.get(kireIndex);
                balozok.get(szavazIndex).szavaz(lany);
                System.out.println(lany.getNev() + " szavazatot kapott.");
            } else {
                System.out.printf("Na ne viccelj, %s fiú\n",
                        balozok.get(kireIndex).getNev());
            }

            balozok.get(tancolindex).tancol();
            System.out.println(balozok.get(tancolindex).getNev() + " táncol");

            System.out.println("\nFogyasztás:");
            penz = (int) (Math.random() * FIZETES_HATAR);
            balozo = balozok.get(fogyasztIndex);
            if (balozo.fogyaszt(penz)) {
                System.out.println(balozo.getNev() + " fogyasztott");
            } else {
                System.out.println(balozo.getNev() + "nak nincs elég pénze");
            }

            if (Math.random() < BAL_VEGE_ESELY) vege = true;

        } while (!vege);
        System.out.println("\nVége a bálnak.");
    }

    private void osszBevetel() {
        int ossz = 0;
        for (Balozo balozo : balozok) {
            ossz += balozo.getKoltottPenz();
        }
        System.out.println("\nA büfé bevétele: " + ossz + " Ft");
    }

    private void eredmenyHirdetes() {
        System.out.println("\n A bálozók fogyasztása: ");
        for (Balozo balozo : balozok) {
            System.out.printf("%s, %d Ft-ot költött\n",
                    balozo, balozo.getKoltottPenz());
        }
        System.out.println("\nA lányok szavazatai");
        for (Balozo balozo : balozok) {
            if (balozo instanceof Lany) {
                System.out.printf("%s, %d szavazatot kapott\n",
                        balozo, ((Lany) balozo).getSzavazatokSzama());
            }
        }
    }

    private void balkiralynok() {
        int max = 0;
        for (Balozo balozo : balozok) {
            if (balozo instanceof Lany) {
                max = ((Lany) balozo).getSzavazatokSzama();
                break;
            }
        }
        for (Balozo balozo : balozok) {
            if (balozo instanceof Lany && ((Lany) balozo).getSzavazatokSzama() > max) {
                max = ((Lany) balozo).getSzavazatokSzama();
            }
        }
        System.out.println("\nA legnépszerűbb lány(ok) "
                + max + " szavazattal:");
        for (Balozo balozo : balozok) {
            if (balozo instanceof Lany && ((Lany) balozo).getSzavazatokSzama() == max) {
                System.out.println(balozo.getNev());
            }
        }
    }

    private void tancokSorrendje() {
        RendezoOsztaly.setValasztottSzempont(RendezoOsztaly.Szempont.TANCOK_SZAMA, RendezoOsztaly.CSOKKENOEN);
        Collections.sort(balozok, new RendezoOsztaly());
        balozokKiirasa("\nA táncok száma szerint rendezve: ");
    }

    private void fogyasztasiSorrend() {
        RendezoOsztaly.setValasztottSzempont(RendezoOsztaly.Szempont.FOGYASZTAS, RendezoOsztaly.NOVEKVOEN);
        Collections.sort(balozok, new RendezoOsztaly());
        balozokKiirasa("\nA fogyasztás szerint rendezve: ");
    }

    private void szavazatokSzerintiSorrend() {
        RendezoOsztaly.setValasztottSzempont(RendezoOsztaly.Szempont.SZAVAZATSZAM, RendezoOsztaly.CSOKKENOEN);
        Collections.sort(balozok, new RendezoOsztaly());
        balozokKiirasa("\nA szavazatok száma szerint rendezve: ");
    }

    private void balozokKiirasa(String cim) {
        String tempFormat = "%s, %d táncm %d Ft fogyasztás";
        System.out.println(cim);
        for (Balozo balozo : balozok) {
            System.out.printf(tempFormat, balozo, balozo.getTancSzam(), balozo.getKoltottPenz());
            if (balozo instanceof Lany) {
                System.out.println(", " + ((Lany) balozo).getSzavazatokSzama() + " szavazat");
            } else {
                System.out.println();
            }
        }
    }

    private void holgyvalasz() {
        Scanner scanner = new Scanner(System.in);
        String nev;
        do {
            System.out.println("Kit választ? ");
            nev = scanner.nextLine();
            Fiu fiu = new Fiu(nev, 0);
            if (balozok.contains(fiu)) System.out.println("Szerepel");
            else System.out.println("Nem szerepel");

            System.out.println("Keres még valakit? (i/n)");
        } while ("i".equals(scanner.next()));
    }
}
