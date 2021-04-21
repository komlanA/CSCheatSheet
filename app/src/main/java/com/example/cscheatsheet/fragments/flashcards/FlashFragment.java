package com.example.cscheatsheet.fragments.flashcards;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cscheatsheet.R;

import java.util.List;

import static com.parse.Parse.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class FlashFragment extends Fragment {

    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int cardIndex = 0;
    private TextView question_text;
    private TextView answer_text;
    private Button next;
    private Button prev;
    private Button delete;
    private ImageView menu_button;

    // Context context = getContext();

    public FlashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.flashcard_main, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        super.onViewCreated(view, savedInstanceState);
        question_text = view.findViewById(R.id.question_text);
        answer_text = view.findViewById(R.id.answer_text);
        next = view.findViewById(R.id.next);
        prev = view.findViewById(R.id.prev);
        delete = view.findViewById(R.id.delete);
        menu_button = view.findViewById(R.id.menu_button);



        if (allFlashcards != null && allFlashcards.size() > 0) {
            question_text.setText(allFlashcards.get(0).getQuestion());
            answer_text.setText(allFlashcards.get(0).getAnswer());
        }


        answer_text.setVisibility(View.INVISIBLE);

        answer_text.setBackgroundColor(getResources().getColor(R.color.answerColor));
        question_text.setBackgroundColor(getResources().getColor(R.color.questionColor));

        ///ANIMATE///

        final View questionView = question_text;
        final View answerView = answer_text;

        questionView.setCameraDistance(25000);
        answerView.setCameraDistance(25000);


        question_text.setOnClickListener(new View.OnClickListener() {
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

        answer_text.setOnClickListener(new View.OnClickListener() {
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
        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewCard();
            }
        });


        ///NEXT CARD///
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextCard();
            }
        });

        ///PREV CARD///
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevCard();
            }
        });

        ////DELETE CARDS////
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCard();
            }
        });


    }
    public void addNewCard(){



        cardIndex = allFlashcards.size();

        Intent i = new Intent(getActivity(), add_card_activity.class);

        FlashFragment.this.startActivityForResult(i, 100);

        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    public void nextCard(){

        final View v = question_text;


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
                question_text.setText(allFlashcards.get(cardIndex).getQuestion());
                answer_text.setText(allFlashcards.get(cardIndex).getAnswer());
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
        question_text.setText(allFlashcards.get(cardIndex).getQuestion());
        answer_text.setText(allFlashcards.get(cardIndex).getAnswer());


        System.out.println("The size is " + allFlashcards.size());
        System.out.println("Current index of " + cardIndex);
    }

    public void deleteCard(){

        if (allFlashcards.size() == 0) {
            showToast("Please add some cards first");
            return;
        }


        flashcardDatabase.deleteCard(question_text.getText().toString());

        //update index as card is removed
        cardIndex--;
        if (cardIndex <=0){
            cardIndex = 0;
        }

        allFlashcards = flashcardDatabase.getAllCards();

        if (allFlashcards.size() == 0){
            question_text.setText("Add a question");
            answer_text.setText("Add an answer");
            return;
        }


        question_text.setText(allFlashcards.get(cardIndex).getQuestion());
        answer_text.setText(allFlashcards.get(cardIndex).getAnswer());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null) { // this 100 needs to match the 100 we used when we called startActivityForResult!

            String update_q = data.getExtras().getString("new_q"); // "string" needs to match the key we used when we put the string in the Intent
            String update_a = data.getExtras().getString("new_a");

            question_text.setText(update_q);
            answer_text.setText(update_a);

            flashcardDatabase.insertCard(new Flashcard(update_q, update_a));

            System.out.println("*******!!Updated!!*******");

            allFlashcards = flashcardDatabase.getAllCards();
        }

    }

    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
