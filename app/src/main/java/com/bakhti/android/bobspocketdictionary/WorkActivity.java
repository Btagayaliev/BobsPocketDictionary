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

public class WorkActivity extends AppCompatActivity {

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
        numbers.add(new Word("go to work", "zur Arbeit gehen", R.raw.work_one));
        numbers.add(new Word("finish work", "Arbeit beenden", R.raw.work_two));
        numbers.add(new Word("chef", "Koch", R.raw.work_three));
        numbers.add(new Word("doctor", "Arzt", R.raw.work_four));
        numbers.add(new Word("engineer", "Ingenieur", R.raw.work_five));
        numbers.add(new Word("nurse", "Krankenschwester", R.raw.work_six));
        numbers.add(new Word("office assistant", "Büroassistent", R.raw.work_seven));
        numbers.add(new Word( "pilot", "Pilot", R.raw.work_eight));
        numbers.add(new Word( "police officer", "Polizist", R.raw.work_nine));
        numbers.add(new Word( "postman/postwoman", "Briefträger/in", R.raw.work_ten));
        numbers.add(new Word( "receptionist", "Rezeptionist", R.raw.work_eleven));
        numbers.add(new Word( "waiter/waitress", "Kellner/Kellnerin", R.raw.work_twelve));
        numbers.add(new Word( "shop assistant", "Verkäufer", R.raw.work_thirteen));
        numbers.add(new Word( "office", "Büro", R.raw.work_fourteen));
        numbers.add(new Word( "bookshop", "Buchladen", R.raw.work_fifteen));
        numbers.add(new Word( "housewife", "Hausfrau", R.raw.work_sixteen));
        numbers.add(new Word( "meet new people", "neue Leute kennen lernen", R.raw.work_seventeen));
        numbers.add(new Word( "work long hours", "viele Stunden arbeiten", R.raw.work_eighteen));
        numbers.add(new Word( "stressful", "stressig", R.raw.work_nineteen));
        numbers.add(new Word( "partner", "Partner", R.raw.work_twenty));
        numbers.add(new Word( "company", "Unternehmen", R.raw.work_twenty_one));
        numbers.add(new Word( "get a job", "einen Job bekommen", R.raw.work_twenty_two));
        numbers.add(new Word( "earn", "verdienen", R.raw.work_twenty_three));
        numbers.add(new Word( "salary", "Lohn", R.raw.work_twenty_four));
        numbers.add(new Word( "business trip", "Geschäftsreise", R.raw.work_twenty_five));
        numbers.add(new Word( "retired", "Rentner", R.raw.work_twenty_six));
        numbers.add(new Word( "architect", "Architekt", R.raw.work_twenty_seven));
        numbers.add(new Word( "flight attendant", "Flugbegleiter", R.raw.work_twenty_eight));
        numbers.add(new Word( "reporter", "Reporter", R.raw.work_twenty_nine));


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

                    mediaPlayer = MediaPlayer.create(WorkActivity.this, word.getAudioResourceId());
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
        
        if (mediaPlayer != null) {
            //Release the media player
            mediaPlayer.release();

            // Set the media player back to null
            mediaPlayer = null;

            audioManager.abandonAudioFocus(audioFocusChangeListener);
        }
    }
}



