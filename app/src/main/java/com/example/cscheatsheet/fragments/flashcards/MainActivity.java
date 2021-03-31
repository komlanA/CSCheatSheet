package com.example.cscheatsheet.fragments.flashcards;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cscheatsheet.R;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int cardIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();


        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.question_text)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.answer_text)).setText(allFlashcards.get(0).getAnswer());
        }


        findViewById(R.id.answer_text).setVisibility(View.INVISIBLE);

        findViewById(R.id.answer_text).setBackgroundColor(getResources().getColor(R.color.green));
        findViewById(R.id.question_text).setBackgroundColor(getResources().getColor(R.color.yellow));

        ///ANIMATE///

        final View questionView = findViewById(R.id.question_text);
        final View answerView = findViewById(R.id.answer_text);

        questionView.setCameraDistance(25000);
        answerView.setCameraDistance(25000);


        findViewById(R.id.question_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //findViewById(R.id.question_text).setVisibility(View.INVISIBLE);
                //findViewById(R.id.answer_text).setVisibility(View.VISIBLE);

                questionView.animate()
                        .rotationX(90)
                        .setDuration(200)
                        .withEndAction(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        questionView.setVisibility(View.INVISIBLE);
                                        answerView.setVisibility(View.VISIBLE);
                                        // second quarter turn
                                        answerView.setRotationX(-90);
                                        answerView.animate()
                                                .rotationX(0)
                                                .setDuration(200)
                                                .start();
                                    }
                                }
                        ).start();
            }
        });

        findViewById(R.id.answer_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //findViewById(R.id.question_text).setVisibility(View.VISIBLE);
                //findViewById(R.id.answer_text).setVisibility(View.INVISIBLE);

                answerView.animate()
                        .rotationX(90)
                        .setDuration(200)
                        .withEndAction(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        questionView.setVisibility(View.INVISIBLE);
                                        questionView.setVisibility(View.VISIBLE);
                                        // second quarter turn
                                        questionView.setRotationX(-90);
                                        questionView.animate()
                                                .rotationX(0)
                                                .setDuration(200)
                                                .start();
                                    }
                                }
                        ).start();

            }
        });


        ///ADD CARD////
        findViewById(R.id.menu_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewCard();
            }
        });


        ///NEXT CARD///
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextCard();
            }
        });

        ///PREV CARD///
        findViewById(R.id.prev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevCard();
            }
        });

        ////DELETE CARDS////
        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCard();
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null) { // this 100 needs to match the 100 we used when we called startActivityForResult!

            String update_q = data.getExtras().getString("new_q"); // "string" needs to match the key we used when we put the string in the Intent
            String update_a = data.getExtras().getString("new_a");

            ((TextView) findViewById(R.id.question_text)).setText(update_q);
            ((TextView) findViewById(R.id.answer_text)).setText(update_a);

            flashcardDatabase.insertCard(new Flashcard(update_q, update_a));

            System.out.println("*******!!Updated!!*******");

            allFlashcards = flashcardDatabase.getAllCards();
        }


    }


    public void addNewCard() {

        final Animation left_in = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        final Animation right_in = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
        final Animation left_out = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
        final Animation right_out = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);

        cardIndex = allFlashcards.size();

        Intent i = new Intent(MainActivity.this, add_card_activity.class);

        MainActivity.this.startActivityForResult(i, 100);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    public void nextCard(){

        final View v = findViewById(R.id.question_text);


        final Animation leftOutAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.slide_out_left);
        final Animation rightInAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.slide_in_right);




        if (allFlashcards.size() == 0) {
            showToast("Please add new cards");
            return;
        }

        cardIndex++;

        // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
        if (cardIndex > allFlashcards.size()-1) {
            cardIndex = 0;
        }

        leftOutAnim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                v.startAnimation(leftOutAnim);
                System.out.println("left out");


            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // set the question and answer TextViews with data from the database
                ((TextView) findViewById(R.id.question_text)).setText(allFlashcards.get(cardIndex).getQuestion());
                ((TextView) findViewById(R.id.answer_text)).setText(allFlashcards.get(cardIndex).getAnswer());
                v.startAnimation(rightInAnim);
                System.out.println("Right in");


            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // we don't need to worry about this method
            }
        });

        v.startAnimation(leftOutAnim);


        System.out.println("The size is " + allFlashcards.size());
        System.out.println("Current index of " + cardIndex);
    }

    public void prevCard(){


        if (allFlashcards.size() == 0) {
            showToast("Please add new cards");
            return;
        }

        cardIndex--;

        // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
        if (cardIndex < 0) {
            cardIndex = allFlashcards.size()-1;
        }

        // set the question and answer TextViews with data from the database
        ((TextView) findViewById(R.id.question_text)).setText(allFlashcards.get(cardIndex).getQuestion());
        ((TextView) findViewById(R.id.answer_text)).setText(allFlashcards.get(cardIndex).getAnswer());


        System.out.println("The size is " + allFlashcards.size());
        System.out.println("Current index of " + cardIndex);
    }

    public void deleteCard(){


        if (allFlashcards.size() == 0) {
            showToast("Please add some cards first");
            return;
        }


        flashcardDatabase.deleteCard(((TextView) findViewById(R.id.question_text)).getText().toString());

        //update index as card is removed
        cardIndex--;
        if (cardIndex <=0){
            cardIndex = 0;
        }

        allFlashcards = flashcardDatabase.getAllCards();

        if (allFlashcards.size() == 0){
            ((TextView) findViewById(R.id.question_text)).setText("Add a question");
            ((TextView) findViewById(R.id.answer_text)).setText("Add an answer");
            return;
        }


        ((TextView) findViewById(R.id.question_text)).setText(allFlashcards.get(cardIndex).getQuestion());
        ((TextView) findViewById(R.id.answer_text)).setText(allFlashcards.get(cardIndex).getAnswer());
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
