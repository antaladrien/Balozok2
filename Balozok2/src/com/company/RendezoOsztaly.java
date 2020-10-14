package com.company;

import java.util.Comparator;

public class RendezoOsztaly implements Comparator<Balozo> {

    public enum Szempont{TANCOK_SZAMA, FOGYASZTAS, SZAVAZATSZAM}

    public static final boolean NOVEKVOEN = true;
    public static final boolean CSOKKENOEN = false;

    private static Szempont valasztottSzempont;
    private static boolean miModon;

    @Override
    public int compare(Balozo o1, Balozo o2) {
        switch (valasztottSzempont) {
            case TANCOK_SZAMA:
                return miModon ? o1.getTancSzam() - o2.getTancSzam() : o2.getTancSzam() - o1.getTancSzam();
            case FOGYASZTAS:
                return miModon ? o1.getKoltottPenz() - o2.getKoltottPenz() : o2.getKoltottPenz() - o1.getKoltottPenz();
            case SZAVAZATSZAM: {
                int o1Szavazatszam, o2Szavazatszam;
                o1Szavazatszam = (o1 instanceof Lany) ? ((Lany) o1).getSzavazatokSzama() : 0;
                o2Szavazatszam = (o2 instanceof Lany) ? ((Lany) o2).getSzavazatokSzama() : 0;
                return miModon ? o1Szavazatszam - o2Szavazatszam : o2Szavazatszam - o1Szavazatszam;
            }
            default:
                return 0;
        }
    }

    public static void setValasztottSzempont(Szempont valasztottSzempont, boolean miModon) {
        RendezoOsztaly.valasztottSzempont = valasztottSzempont;
        RendezoOsztaly.miModon = miModon;
    }

    public static Szempont getValasztottSzempont() {
        return valasztottSzempont;
    }

    public static boolean isMiModon() {
        return miModon;
    }
}
