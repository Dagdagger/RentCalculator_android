package com.example.omarbrito.rentcalculator;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main2Activity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final TextView bRent = findViewById(R.id.textView4);
        final TextView bElectricity = findViewById(R.id.textView5);
        final TextView bInsurance = findViewById(R.id.textView6);
        final TextView bInternet = findViewById(R.id.textView7);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Rents rent = dataSnapshot.child("rents").getValue(Rents.class);
                String base_rent = "Base Rent is " + rent.base_rent;
                String electricity = "Electricity is" + rent.electricity;
                String insurance = "Insurance is " + rent.insurance;
                String internet  = "Internet is " + rent.internet;
                bRent.setText(base_rent);
                bElectricity.setText(electricity);
                bInsurance.setText(insurance);
                bInternet.setText(internet);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public static class Rents {

        public String base_rent;
        public String electricity;
        public String insurance;
        public String internet;
        public String whatever;

        public Rents(String base_rent, String electricity, String insurance, String internet ){
            this.base_rent = base_rent;
            this.electricity = electricity;
            this.insurance = insurance;
            this.internet = internet;

        }
        public Rents() {}




    }
}
