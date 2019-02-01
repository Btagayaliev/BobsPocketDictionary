package com.bakhti.android.bobspocketdictionary;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.res.AssetFileDescriptor;

import java.io.IOException;
import java.util.ArrayList;

public class LeisureActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    AudioManager audioManager;

    AssetFileDescriptor af;

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
        numbers.add(new Word("leisure", "Freizeit", R.raw.leisure1));
        numbers.add(new Word("ice hockey", "Eishockey", R.raw.lesiure2));
        numbers.add(new Word("champion", "Gewinner", R.raw.leisure3));
        numbers.add(new Word("soccer", "Fußball", R.raw.leisure4));
        numbers.add(new Word("outdoors", "draußen", R.raw.leisure5));
        numbers.add(new Word("horse riding", "Reiten", R.raw.leisure6));
        numbers.add(new Word("wrestling", "Wrestling", R.raw.leisure7));
        numbers.add(new Word( "bowling", "Bowling", R.raw.leisure8));
        numbers.add(new Word( "watching TV", "Fernsehen", R.raw.leisure9));
        numbers.add(new Word( "cooking", "Kochen", R.raw.leisure10));
        numbers.add(new Word( "baking", "Backen", R.raw.leisure11));
        numbers.add(new Word( "listening to music", "Musik hören", R.raw.leisure12));
        numbers.add(new Word( "photography", "Fotografie", R.raw.leisure13));
        numbers.add(new Word( "in the countryside", "auf dem Land", R.raw.leisure14));
        numbers.add(new Word( "trip", "Reise", R.raw.leisure15));
        numbers.add(new Word( "theater", "Theater", R.raw.leisure16));
        numbers.add(new Word( "surf the Internet", "im Internet surfen", R.raw.leisure17));
        numbers.add(new Word( "go shopping", "Einkaufen gehen", R.raw.leisure18));
        numbers.add(new Word( "go out with friends", "mit Freunden ausgehen", R.raw.leisure19));
        numbers.add(new Word( "swimming", "Schwimmen", R.raw.leisure20));
        numbers.add(new Word( "cycling", "Radfahren", R.raw.leisure21));
        numbers.add(new Word( "go for a walk", "spazieren gehen", R.raw.leisure22));
        numbers.add(new Word( "fishing", "Fishfang", R.raw.leisure23));
        numbers.add(new Word( "drawing", "Zeichnung", R.raw.leisure24));
        numbers.add(new Word( "jogging", "Joggen", R.raw.leisure25));
        numbers.add(new Word( "gardening", "im Garten arbeiten", R.raw.leisure26));
        numbers.add(new Word( "painting", "Malerei", R.raw.leisure27));
        numbers.add(new Word( "reading", "Lesen", R.raw.leisure28));
        numbers.add(new Word( "yoga", "Yoga", R.raw.leisure29));


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

                    mediaPlayer = MediaPlayer.create(LeisureActivity.this, word.getAudioResourceId());
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


    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null.
            mediaPlayer = null;

            audioManager.abandonAudioFocus(audioFocusChangeListener);
        }
    }
}


