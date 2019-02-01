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

public class BodyActivity extends AppCompatActivity {

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
        numbers.add(new Word("body", "Körper", R.raw.body_one));
        numbers.add(new Word("body part", "Teil", R.raw.body_two));
        numbers.add(new Word("ear", "Ohr", R.raw.body_three));
        numbers.add(new Word("forehead", "Stirn", R.raw.body_four));
        numbers.add(new Word("eyebrow", "Augenbraue", R.raw.body_five));
        numbers.add(new Word("eyes", "Augen", R.raw.body_six));
        numbers.add(new Word("nose", "Nase", R.raw.body_seven));
        numbers.add(new Word( "tooth(teeth)", "Zahn(Zähne)", R.raw.body_eight));
        numbers.add(new Word( "moustache", "Schnurrbart", R.raw.body_nine));
        numbers.add(new Word( "mouth", "Mund", R.raw.body_ten));
        numbers.add(new Word( "beard", "Vollbart", R.raw.body_eleven));
        numbers.add(new Word( "hair", "Haare", R.raw.body_twelve));
        numbers.add(new Word( "straight", "gerade", R.raw.body_thirteen));
        numbers.add(new Word( "tall", "hoch, groß", R.raw.body_fourteen));
        numbers.add(new Word( "height", "Größe", R.raw.body_fifteen));
        numbers.add(new Word( "weight", "Gewicht", R.raw.body_sixteen));
        numbers.add(new Word( "health", "Gesundheit", R.raw.body_seventeen));
        numbers.add(new Word( "overweight", "Übergewicht", R.raw.body_eighteen));
        numbers.add(new Word( "healthy", "gesund", R.raw.body_nineteen));
        numbers.add(new Word( "slim", "schlank", R.raw.body_twenty));
        numbers.add(new Word( "shoulders", "Schultern", R.raw.body_twenty_one));
        numbers.add(new Word( "elbow", "Ellbogen", R.raw.body_twenty_two));
        numbers.add(new Word( "knee", "Knie", R.raw.body_twenty_three));
        numbers.add(new Word( "toe", "Zeh", R.raw.body_twenty_four));
        numbers.add(new Word( "finger", "Finger", R.raw.body_twenty_five));
        numbers.add(new Word( "arm", "Arm", R.raw.body_twenty_six));
        numbers.add(new Word( "leg", "Bein", R.raw.body_twenty_seven));
        numbers.add(new Word( "neck", "Bein", R.raw.body_twenty_eight));
        numbers.add(new Word( "short", "kurz", R.raw.body_twenty_nine));


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

                    mediaPlayer = MediaPlayer.create(BodyActivity.this, word.getAudioResourceId());
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



