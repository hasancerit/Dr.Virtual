package com.yusufalicezik.drvirtual.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class DoktorCalismaTarihiParcala {



    public ArrayList<String> gunlerListe=new ArrayList<>();
    public ArrayList<String> saatlerListe=new ArrayList<>();



    //gelenVeri="1-3,1-5,2-5"



    public ArrayList<ArrayList> parcala(String gelenVeri){

        ArrayList<ArrayList> donecek=new ArrayList<>();
        try {
            String[] dizi = gelenVeri.split(",");
            //dizi[0]="1-3"   dizi[1]="1-5"
            for (int i = 0; i < dizi.length; i++) {
                String[] yeniDizi = dizi[i].split("-");

                //sonucta aynı indis numaralarına sahipler.

                gunlerListe.add(yeniDizi[0]);
                saatlerListe.add(yeniDizi[1]);



            }


        }catch (Exception e){
            e.printStackTrace();
        }

        donecek.add(gunlerListe);
        donecek.add(saatlerListe);
        return  donecek;
    }


}
