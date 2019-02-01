package com.bakhti.android.bobspocketdictionary;

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
