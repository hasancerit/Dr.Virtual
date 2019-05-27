package com.yusufalicezik.drvirtual.Model;

public class GenelKullanici {

    private String tc, isimsoyisim, randevuSaat;

    public GenelKullanici(String tc, String isimsoyisim, String randevuSaat) {
        this.tc = tc;
        this.isimsoyisim = isimsoyisim;
        this.randevuSaat = randevuSaat;
    }

    public GenelKullanici() {
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getIsimsoyisim() {
        return isimsoyisim;
    }

    public void setIsimsoyisim(String isimsoyisim) {
        this.isimsoyisim = isimsoyisim;
    }

    public String getRandevuSaat() {
        return randevuSaat;
    }

    public void setRandevuSaat(String randevuSaat) {
        this.randevuSaat = randevuSaat;
    }
}
