package com.company;

public class Fiu extends Balozo {

    public Fiu(String nev, int zsebPenz) {
        super(nev, zsebPenz);
    }

    @Override
    public String osztalyNev() {
        return "fiú";
    }
}
