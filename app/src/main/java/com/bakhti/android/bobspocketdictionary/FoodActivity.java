package com.bakhti.android.bobspocketdictionary;



import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class FoodActivity extends AppCompatActivity {


    MediaPlayer mediaPlayer;

    AudioManager audioManager;

    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {

            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);

            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mediaPlayer.start();

            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };

    MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener(){
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dict_list);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> numbers = new ArrayList<Word>();
        numbers.add(new Word("food", "Essen", R.raw.food1));
        numbers.add(new Word("a cup of coffee", "eine Tasse Kaffee", R.raw.food2));
        numbers.add(new Word("a glass of juice", "ein Glas Saft", R.raw.food3));
        numbers.add(new Word("a slice of bread", "eine Scheibe Brot", R.raw.food4));
        numbers.add(new Word("knife", "Messer", R.raw.food5));
        numbers.add(new Word("fork", "Gabel", R.raw.food6));
        numbers.add(new Word("spoon", "Löffel", R.raw.food7));
        numbers.add(new Word( "butter", "Butter", R.raw.food8));
        numbers.add(new Word( "sugar", "Zucker", R.raw.food9));
        numbers.add(new Word( "jam", "Marmelade", R.raw.food10));
        numbers.add(new Word( "honey", "Honig", R.raw.food11));
        numbers.add(new Word( "cheese", "Käse", R.raw.food12));
        numbers.add(new Word( "ham", "Schinken", R.raw.food13));
        numbers.add(new Word( "sandwich", "Sandwich", R.raw.food14));
        numbers.add(new Word( "cucumber", "Gurke", R.raw.food15));
        numbers.add(new Word( "onion", "Zwiebel", R.raw.food16));
        numbers.add(new Word( "lettuce", "Grüner Salat", R.raw.food17));
        numbers.add(new Word( "mayonnaise", "Mayonnaise", R.raw.food18));
        numbers.add(new Word( "salad", "Salat", R.raw.food19));
        numbers.add(new Word( "fruit", "Obst", R.raw.food20));
        numbers.add(new Word( "vegetable", "Gemüse", R.raw.food21));
        numbers.add(new Word( "apple", "Apfel", R.raw.food22));
        numbers.add(new Word( "orange", "Orange", R.raw.food23));
        numbers.add(new Word( "peach", "Pfirsich", R.raw.food24));
        numbers.add(new Word( "mushroom", "Pilz", R.raw.food25));
        numbers.add(new Word( "chicken", "Hähnchenfleisch", R.raw.food26));
        numbers.add(new Word( "beef", "Rindfleisch", R.raw.food27));
        numbers.add(new Word( "pork", "Schweinfleisch", R.raw.food28));
        numbers.add(new Word( "turkey", "Putenfleisch", R.raw.food29));
        numbers.add(new Word( "carrot", "Karotte", R.raw.food30));
        numbers.add(new Word( "sauce", "Soße", R.raw.food31));
        numbers.add(new Word( "potato", "Kartofel", R.raw.food32));
        numbers.add(new Word( "seafood", "Meeresfrüchte", R.raw.food33));


        WordAdapter adapter = new WordAdapter(this, numbers);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = numbers.get(position);
                releaseMediaPlayer();

                int result = audioManager.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mediaPlayer = MediaPlayer.create(FoodActivity.this, word.getAudioResourceId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }
            }
        });
    }

    @Override
    protected  void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;

            audioManager.abandonAudioFocus(audioFocusChangeListener);
        }
    }

}


