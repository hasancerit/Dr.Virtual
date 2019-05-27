package com.yusufalicezik.drvirtual.Model;

public class Hasta {

    private String tc;
    private String isim;
    private String soyisim;
    private String tel;
    private String sifre;


    private String uid;

    public Hasta()
    {

    }
    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getSoyisim() {
        return soyisim;
    }

    public void setSoyisim(String soyisim) {
        this.soyisim = soyisim;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public Hasta(String tc, String isim, String soyisim, String tel, String sifre, String uid) {
        this.tc = tc;
        this.isim = isim;
        this.soyisim = soyisim;
        this.tel = tel;
        this.sifre = sifre;
        this.uid=uid;
    }






}
