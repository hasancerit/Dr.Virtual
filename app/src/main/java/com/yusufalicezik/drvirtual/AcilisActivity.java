package com.yusufalicezik.drvirtual;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.yusufalicezik.drvirtual.HastaIslemleri.HastaAnaMenuActivity;
import com.yusufalicezik.drvirtual.HastaIslemleri.OnMuayeneMesajActivity;
import com.yusufalicezik.drvirtual.Utils.ViewPagerAdapter;

public class AcilisActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    ViewPager viewPager;
    ToggleButton hastaGiris,doktorGiris;

    public static int girisYapan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acilis);

        mAuth=FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null){ //eğer current kullanıcı null ise şuan current olanın hasta mı doktor mu olup olmadığını anlamak için;

            SharedPreferences ayarlar = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
             girisYapan = ayarlar.getInt("girisYapan",0);
            if(girisYapan==1){
            Intent intent=new Intent(AcilisActivity.this,HastaAnaMenuActivity.class);
            startActivity(intent);
            }else if(girisYapan==2){
            Intent intent=new Intent(AcilisActivity.this, OnMuayeneMesajActivity.class);
            startActivity(intent);
            }
        }

        //
        viewPager = findViewById(R.id.vp);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                if(i == 1){
                    doktorClick();
                }
                if(i == 0){
                    hastaClick();
                }
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public void onClickDoktor(View v){
        viewPager.setCurrentItem(1);
        doktorClick();
    }


    public void onClickHasta(View v){
        viewPager.setCurrentItem(0);
        hastaClick();
    }

    public void doktorClick(){
        girisYapan=2; //doktor ise 1, hasta ise 2 olacak.
        SharedPreferences ayarlar = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = ayarlar.edit();
        editor.putInt("girisYapan",girisYapan);
        editor.commit();
    }

    public void hastaClick(){
        girisYapan=1; //doktor ise 1, hasta ise 2 olacak.
        SharedPreferences ayarlar = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = ayarlar.edit();
        editor.putInt("girisYapan",girisYapan);
        editor.commit();
        //Intent intent=new Intent(AcilisActivity.this, HastaLoginActivity.class);
        //startActivity(intent);
    }
}
