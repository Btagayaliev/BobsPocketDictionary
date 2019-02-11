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

public class FamilyActivity extends AppCompatActivity {

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
        numbers.add(new Word("mother", "Mutter", R.raw.family1));
        numbers.add(new Word("father", "Vater", R.raw.family2));
        numbers.add(new Word("brother", "Bruder", R.raw.family3));
        numbers.add(new Word("sister", "Schwester", R.raw.family4));
        numbers.add(new Word("son", "Sohn", R.raw.family5));
        numbers.add(new Word("daughter", "Tochter", R.raw.family6));
        numbers.add(new Word("grandmother", "Großmutter", R.raw.family7));
        numbers.add(new Word( "grandfather", "Großvater", R.raw.family8));
        numbers.add(new Word( "uncle", "Onkel", R.raw.family9));
        numbers.add(new Word( "aunt", "Tante", R.raw.family10));
        numbers.add(new Word( "child", "Kind", R.raw.family11));
        numbers.add(new Word( "grandson", "Enkel", R.raw.family12));
        numbers.add(new Word( "granddaughter", "Enkelin", R.raw.family13));
        numbers.add(new Word( "grandchild", "Enkel", R.raw.family14));
        numbers.add(new Word( "mother-in-law", "Schwiegermutter", R.raw.family15));
        numbers.add(new Word( "father-in-law", "Schwiegervater", R.raw.family16));
        numbers.add(new Word( "brother-in-law", "Schwager", R.raw.family17));
        numbers.add(new Word( "sister-in-law", "Schwägerin", R.raw.family18));
        numbers.add(new Word( "son-in-law", "Schwiegersohn", R.raw.family19));
        numbers.add(new Word( "daughter-in-law", "Schwiegertochter", R.raw.family20));
        numbers.add(new Word( "nephew", "Neffe", R.raw.family21));
        numbers.add(new Word( "niece", "Nichte", R.raw.family22));
        numbers.add(new Word( "cousine", "Cousin", R.raw.family23));

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

                    mediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getAudioResourceId());
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
            
            mediaPlayer.release();

            
            mediaPlayer = null;

            audioManager.abandonAudioFocus(audioFocusChangeListener);
        }
    }

}


