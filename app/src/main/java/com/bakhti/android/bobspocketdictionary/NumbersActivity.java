package com.bakhti.android.bobspocketdictionary;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

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
        numbers.add(new Word("one", "eins", R.raw.num_one ));
        numbers.add(new Word("two", "zwei", R.raw.num_two));
        numbers.add(new Word("three", "drei", R.raw.num_three));
        numbers.add(new Word("four", "vier", R.raw.num_four));
        numbers.add(new Word("five", "fünf", R.raw.num_five));
        numbers.add(new Word("six", "sechs", R.raw.num_six));
        numbers.add(new Word("seven", "sieben", R.raw.num_seven));
        numbers.add(new Word( "eight", "acht", R.raw.num_eight));
        numbers.add(new Word( "nine", "neuen", R.raw.num_nine));
        numbers.add(new Word( "ten", "zehen", R.raw.num_ten));
        numbers.add(new Word( "eleven", "elf", R.raw.num_eleven));
        numbers.add(new Word( "twelve", "zwölf", R.raw.num_twelve));
        numbers.add(new Word( "thirteen", "dreizehen", R.raw.num_thirteen));
        numbers.add(new Word( "twenty", "zwanzig", R.raw.num_twenty));
        numbers.add(new Word( "twenty-one", "einundzwanzig", R.raw.num_twenty_one));
        numbers.add(new Word( "thirty", "dreizig", R.raw.num_thirty));
        numbers.add(new Word( "forty", "vierzig", R.raw.num_forty));
        numbers.add(new Word( "fifty", "fünfzig", R.raw.num_fifty));
        numbers.add(new Word( "sixty", "sechzig", R.raw.num_sixty));
        numbers.add(new Word( "seventy", "sibzig", R.raw.num_seventy));
        numbers.add(new Word( "eighty", "achtzig", R.raw.num_eighty));
        numbers.add(new Word( "ninety", "neuenzig", R.raw.num_ninety));
        numbers.add(new Word( "one hundred", "hundert", R.raw.num_hundred));

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

                    mediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getAudioResourceId());
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

