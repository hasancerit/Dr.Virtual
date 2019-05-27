package com.yusufalicezik.drvirtual.Login;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.yusufalicezik.drvirtual.Model.Hasta;
import com.yusufalicezik.drvirtual.R;

import java.util.ArrayList;

public class HastaKayitOlActivity extends AppCompatActivity {

   private EditText hastaTc, hastaIsim, hastaSoyisim, hastaTel, hastaSifre;


    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mRef;


    Hasta hasta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasta_kayit_ol);

        hastaTc=findViewById(R.id.edt_hastaKayitOlTC);
        hastaIsim=findViewById(R.id.edt_hastaKayitOlIsim);
        hastaSoyisim=findViewById(R.id.edt_hastaKayitOlSoyisim);
        hastaTel=findViewById(R.id.edt_hastaKayitOlTel);
        hastaSifre=findViewById(R.id.edt_hastaKayitOlSifre);

        database=FirebaseDatabase.getInstance();
        mRef=database.getReference();
        mAuth = FirebaseAuth.getInstance();

        hasta=new Hasta();

    }

    public void hastaKayitOlButonTik(View view){
        if(!hastaTc.getText().toString().isEmpty() && !hastaIsim.getText().toString().isEmpty() &&
                !hastaSoyisim.getText().toString().isEmpty() && !hastaTel.getText().toString().isEmpty() &&
                !hastaSifre.getText().toString().isEmpty()){

            hasta.setTc(hastaTc.getText().toString());
            hasta.setIsim(hastaIsim.getText().toString());
            hasta.setSoyisim(hastaSoyisim.getText().toString());
            hasta.setTel(hastaTel.getText().toString());
            hasta.setSifre(hastaSifre.getText().toString());



        String email=hastaTc.getText().toString() + "@gmail.com";
        String sifre=hastaSifre.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, sifre)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            hasta.setUid(String.valueOf(mAuth.getCurrentUser().getUid().toString()));
                            verileriVeritabaninaYaz(hasta);

                        } else {
                            Toast.makeText(HastaKayitOlActivity.this, "Kayıt BAŞARISIZ", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        }else{
            Toast.makeText(this, "Lütfen tüm alanları doldurun !", Toast.LENGTH_SHORT).show();
        }
    }

    private void verileriVeritabaninaYaz(Hasta kaydedilecekHasta) {
        mRef.child("hastalar").child(kaydedilecekHasta.getUid()).setValue(kaydedilecekHasta)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()) {

                            finish();


                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Başarısız(Firebase hatası)", Toast.LENGTH_SHORT).show();

                        }
                    }
                });


        //parse kayıt işlemleri için
        final ParseObject object=new ParseObject("Hastalar");
        object.put("tc",kaydedilecekHasta.getTc());
        object.put("isim",kaydedilecekHasta.getIsim());
        object.put("soyisim",kaydedilecekHasta.getSoyisim());
        object.put("tel",kaydedilecekHasta.getTel());
        object.put("sifre",kaydedilecekHasta.getSifre());
        object.put("uid",kaydedilecekHasta.getUid());

        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e!=null){
                    Toast.makeText(HastaKayitOlActivity.this, "Başarısız(PARSE hatası)", Toast.LENGTH_SHORT).show();
                }else{

                }
            }
        });



    }
}
