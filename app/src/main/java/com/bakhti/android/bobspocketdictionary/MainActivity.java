package com.bakhti.android.bobspocketdictionary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView numbers = findViewById(R.id.numbers);
        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent numbersIntent = new Intent(MainActivity.this, NumbersActivity.class);
                startActivity(numbersIntent);
            }
        });

        TextView home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(homeIntent);
            }
        });

        TextView food = findViewById(R.id.food);
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent foodIntent = new Intent(MainActivity.this, FoodActivity.class);
                startActivity(foodIntent);
            }
        });

        TextView family = findViewById(R.id.family);
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent familyIntent = new Intent(MainActivity.this, FamilyActivity.class);
                startActivity(familyIntent);
            }
        });

        TextView clothes = findViewById(R.id.clothes);
        clothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent clothesIntent = new Intent(MainActivity.this, ClothesActivity.class);
                startActivity(clothesIntent);
            }
        });

        TextView leisure = findViewById(R.id.leisure);
        leisure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent leisureIntent = new Intent(MainActivity.this, LeisureActivity.class);
                startActivity(leisureIntent);
            }
        });

        TextView work = findViewById(R.id.work);
        work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent workIntent = new Intent(MainActivity.this, WorkActivity.class);
                startActivity(workIntent);
            }
        });

        TextView body = findViewById(R.id.body);
        body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bodyIntent = new Intent(MainActivity.this, BodyActivity.class);
                startActivity(bodyIntent);
            }
        });

        TextView phrases = findViewById(R.id.phrases);
        phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phrasesIntent = new Intent(MainActivity.this, PhrasesActivity.class);
                startActivity(phrasesIntent);
            }
        });

    }
}

