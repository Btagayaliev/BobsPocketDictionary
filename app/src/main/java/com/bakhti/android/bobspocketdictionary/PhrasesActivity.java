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

public class PhrasesActivity extends AppCompatActivity {

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
        numbers.add(new Word("Do you speak English?", "Sprechen Sie Englisch?", R.raw.phrases_one));
        numbers.add(new Word("What is your name?", "Wie heißt du?", R.raw.phrases_two));
        numbers.add(new Word("My name is ...", "Ich heiße ...", R.raw.phrases_three));
        numbers.add(new Word("Nice to meet you.", "Freuet mich, dich kennenzulernen.", R.raw.phrases_four));
        numbers.add(new Word("How are you?", "Wie geht's dir?", R.raw.phrases_five));
        numbers.add(new Word("I'm fine, thanks.", "Danke, gut.", R.raw.phrases_six));
        numbers.add(new Word("See you later.", "Bis später.", R.raw.phrases_seven));
        numbers.add(new Word( "Have a nice day!", "Ich wünsche dir einen schönen Tag.", R.raw.phrases_eight));
        numbers.add(new Word( "Have a nice weekend!", "Wünsche dir ein schönes Wochenende.", R.raw.phrases_nine));
        numbers.add(new Word( "Can you help me?", "Kannst du mir helfen?", R.raw.phrases_ten));
        numbers.add(new Word( "How much does it cost?", "Wie viel kostet es?", R.raw.phrases_eleven));
        numbers.add(new Word( "What time is it?", "Wie spät ist es?", R.raw.phrases_twelve));
        numbers.add(new Word( "It is a nice day today.", "Heute ist ein schöner Tag.", R.raw.phrases_thirteen));
        numbers.add(new Word( "Sorry, I am late.", "Sorry, ich bin zu spät.", R.raw.phrases_fourteen));
        numbers.add(new Word( "Can I help you?", "Kann ich Ihnen helfen?", R.raw.phrases_fifteen));
        numbers.add(new Word( "I would like a cup of tea.", "Ich möchte eine Tasse Tee.", R.raw.phrases_sixteen));
        numbers.add(new Word( "Where are you from?", "Woher kommst du?", R.raw.phrases_seventeen));


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

                    mediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getAudioResourceId());
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




