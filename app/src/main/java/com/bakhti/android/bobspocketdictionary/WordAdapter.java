package com.bakhti.android.bobspocketdictionary;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.app.Activity;
import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    public WordAdapter(Activity context, ArrayList<Word> words) {
        super(context, 0, words);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_view, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        Word currentWord = getItem(position);

        // Find the TextView in the list_view.xml layout with the ID version_name
        TextView englishWord = (TextView) listItemView.findViewById(R.id.item1);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        englishWord.setText(currentWord.getEnglishTranslation());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView germanWord = (TextView) listItemView.findViewById(R.id.item2);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        germanWord.setText(currentWord.getGermanTranslation());

       /* // Find the ImageView in the list_item.xml layout with the ID list_item_icon
        ImageView iconView = (ImageView) listItemView.findViewById(R.id.list_item_icon);
        // Get the image resource ID from the current AndroidFlavor object and
        // set the image to iconView
        iconView.setImageResource(currentAndroidFlavor.getImageResourceId());*/

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}

