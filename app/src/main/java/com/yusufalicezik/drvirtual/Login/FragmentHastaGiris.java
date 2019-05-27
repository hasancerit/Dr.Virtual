package com.yusufalicezik.drvirtual.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.yusufalicezik.drvirtual.AcilisActivity;
import com.yusufalicezik.drvirtual.HastaIslemleri.HastaAnaMenuActivity;
import com.yusufalicezik.drvirtual.HastaIslemleri.OnMuayeneMesajActivity;
import com.yusufalicezik.drvirtual.Login.HastaKayitOlActivity;
import com.yusufalicezik.drvirtual.R;

public class FragmentHastaGiris extends Fragment {
    TextView kayitOl;
    Button giris;

    private EditText hastaTc, hastaSifre;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lay1,container, false);
        giris = v.findViewById(R.id.hastaGiris);

        hastaTc=v.findViewById(R.id.edt_hastaGirisTc);
        hastaSifre=v.findViewById(R.id.edt_hastaGirisSifre);
        mAuth = FirebaseAuth.getInstance();

        kayitOl = v.findViewById(R.id.KayitOl);
        kayitOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), HastaKayitOlActivity.class));
            }
        });

        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hastaGirisYapButonTik(view);
            }
        });
        return v;
    }

    public void hastaGirisYapButonTik(View view){
        if(AcilisActivity.girisYapan==1) {
            if (!hastaSifre.getText().toString().isEmpty() && !hastaTc.getText().toString().isEmpty()) {


                String email = hastaTc.getText().toString() + "@gmail.com";
                String sifre = hastaSifre.getText().toString();

                mAuth.signInWithEmailAndPassword(email, sifre)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>(){
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent  =new Intent(getContext(), HastaAnaMenuActivity.class);
                                    startActivity(intent);
                                    //finish();
                                } else {
                                    Toast.makeText(getContext(), "TC veya şifre yanlış", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }else  if(AcilisActivity.girisYapan==2) {
            if (!hastaSifre.getText().toString().isEmpty() && !hastaTc.getText().toString().isEmpty()) {


                String email = hastaTc.getText().toString() + "@hotmail.com"; //giriş yapanın doktor/hasta olup olmadığını anlamak için gmail/hotmail
                String sifre = hastaSifre.getText().toString();

                mAuth.signInWithEmailAndPassword(email, sifre)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(getContext(), OnMuayeneMesajActivity.class);
                                    startActivity(intent);
                                    //finish();
                                } else {
                                    Toast.makeText(getContext(), "TC veya şifre yanlış", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
    }
}
