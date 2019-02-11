package com.bakhti.android.bobspocketdictionary;


//Creates the frame for each entry in the dictionary
public class Word {

    private int number;
    private String englishTranslation;
    private String germanTranslation;
    private int audioResourceId;

    public Word(String englishTranslation, String germanTranslation, int audioResourceId) {
        this.englishTranslation = englishTranslation;
        this.germanTranslation = germanTranslation;
        this.audioResourceId = audioResourceId;
    }

    //Getter methods
    public int getNumber() {
        return this.number;
    }

    public String getEnglishTranslation() {
        return this.englishTranslation;
    }

    public String getGermanTranslation() {
        return this.germanTranslation;
    }

    public int getAudioResourceId() {
        return this.audioResourceId;
    }

}
