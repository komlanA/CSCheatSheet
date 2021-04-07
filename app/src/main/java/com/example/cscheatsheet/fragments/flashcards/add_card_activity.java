package com.example.cscheatsheet.fragments.flashcards;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cscheatsheet.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class add_card_activity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flashcard_add_card_fragment);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newQuestion = ((EditText) findViewById(R.id.editQuestion)).getText().toString();
                String newAnswer = ((EditText) findViewById(R.id.editAnswer)).getText().toString();


                if (newQuestion.isEmpty() || newAnswer.isEmpty()) {
                    Toast.makeText(add_card_activity.this, "Fill out the empty fields", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent data = new Intent();



                data.putExtra("new_q", newQuestion);
                data.putExtra("new_a", newAnswer);


                setResult(RESULT_OK, data);

                ///Go back///
                finish();
                overridePendingTransition(R.anim.static_transition, R.anim.slide_out_up);

            }
        });

        findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }
}
