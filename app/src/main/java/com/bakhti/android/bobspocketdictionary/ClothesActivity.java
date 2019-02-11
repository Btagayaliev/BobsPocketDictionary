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

public class ClothesActivity extends AppCompatActivity {

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
        numbers.add(new Word("suit", "Anzug", R.raw.clothes1));
        numbers.add(new Word("shirt", "Hemd", R.raw.clothes2));
        numbers.add(new Word("T-shirt", "T-shirt", R.raw.clothes3));
        numbers.add(new Word("tie", "Krawatte", R.raw.clothes4));
        numbers.add(new Word("shoes", "Schuhe", R.raw.clothes5));
        numbers.add(new Word("sneakers", "Turnschuhe", R.raw.clothes6));
        numbers.add(new Word("socks", "Socken", R.raw.clothes7));
        numbers.add(new Word( "belt", "Gürtel", R.raw.clothes8));
        numbers.add(new Word( "jeans", "Jeans", R.raw.clothes9));
        numbers.add(new Word( "pants", "Hosen", R.raw.clothes10));
        numbers.add(new Word( "sweater", "Sweatshirt", R.raw.clothes11));
        numbers.add(new Word( "winter coat", "Mantel", R.raw.clothes12));
        numbers.add(new Word( "skirt", "Rock", R.raw.clothes13));
        numbers.add(new Word( "dress", "Kleid", R.raw.clothes14));
        numbers.add(new Word( "blouse", "Bluse", R.raw.clothes15));
        numbers.add(new Word( "jacket", "Jacke", R.raw.clothes16));
        numbers.add(new Word( "boots", "Stiefel", R.raw.clothes17));
        numbers.add(new Word( "scarf", "Schal", R.raw.clothes18));
        numbers.add(new Word( "handbag", "Handtasche", R.raw.clothes19));
        numbers.add(new Word( "earrings", "Ohrringe", R.raw.clothes20));
        numbers.add(new Word( "gloves", "Handschuhe", R.raw.clothes21));
        numbers.add(new Word( "hat", "Mütze", R.raw.clothes22));
        numbers.add(new Word( "swimsuit", "Badeanzug", R.raw.clothes23));
        numbers.add(new Word( "wear", "tragen", R.raw.clothes24));
        numbers.add(new Word( "ring", "Ring", R.raw.clothes25));

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

                    mediaPlayer = MediaPlayer.create(ClothesActivity.this, word.getAudioResourceId());
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

    //Release the media player
    private void releaseMediaPlayer() {
        
        if (mediaPlayer != null) {
            
            mediaPlayer.release();

            // Set the media player to null
            
            mediaPlayer = null;

            audioManager.abandonAudioFocus(audioFocusChangeListener);
        }
    }

}



