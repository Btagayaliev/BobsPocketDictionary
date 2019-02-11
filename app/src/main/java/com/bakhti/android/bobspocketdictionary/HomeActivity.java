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

public class HomeActivity extends AppCompatActivity {

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
        numbers.add(new Word("hometown", "Heimatort", R.raw.home1));
        numbers.add(new Word("city", "Stadt", R.raw.home2));
        numbers.add(new Word("village", "Dorf", R.raw.home3));
        numbers.add(new Word("balcony", "Balkon", R.raw.home4));
        numbers.add(new Word("bathroom", "Badezimmer", R.raw.home5));
        numbers.add(new Word("bedroom", "Schlafzimmer", R.raw.home6));
        numbers.add(new Word("dining room", "Esszimmer", R.raw.home7));
        numbers.add(new Word( "kitchen", "Küche", R.raw.home8));
        numbers.add(new Word( "living room", "Wohnzimmer", R.raw.home9));
        numbers.add(new Word( "armchair", "Sessel", R.raw.home10));
        numbers.add(new Word( "chair", "Stuhl", R.raw.home11));
        numbers.add(new Word( "cupboard", "Schrank", R.raw.home12));
        numbers.add(new Word( "dishwasher", "Geschirrspüler", R.raw.home13));
        numbers.add(new Word( "sofa", "Sofa", R.raw.home14));
        numbers.add(new Word( "garden", "Garten", R.raw.home15));
        numbers.add(new Word( "shower", "Dusche", R.raw.home16));
        numbers.add(new Word( "house", "Haus", R.raw.home17));
        numbers.add(new Word( "flat", "Wohnung", R.raw.home18));
        numbers.add(new Word( "window", "Fenster", R.raw.home19));
        numbers.add(new Word( "stove", "Herd", R.raw.home20));
        numbers.add(new Word( "washing machine", "Waschmaschine", R.raw.home21));
        numbers.add(new Word( "fridge", "Kühlschrank", R.raw.home22));
        numbers.add(new Word( "room", "Zimmer", R.raw.home23));

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

                    mediaPlayer = MediaPlayer.create(HomeActivity.this, word.getAudioResourceId());
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
        
        if (mediaPlayer != null) {
            // release
            mediaPlayer.release();

            //set the media player to null
            mediaPlayer = null;

            audioManager.abandonAudioFocus(audioFocusChangeListener);
        }
    }
}


