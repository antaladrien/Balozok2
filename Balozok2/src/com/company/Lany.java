package com.company;

public class Lany extends Balozo {

    private int szavazatokSzama;

    public Lany(String nev, int zsebPenz) {
        super(nev, zsebPenz);
    }

    public void szavazatotKap() {
        szavazatokSzama++;
    }

    public int getSzavazatokSzama() {
        return szavazatokSzama;
    }

    @Override
    public String osztalyNev() {
        return "lány";
    }
}
