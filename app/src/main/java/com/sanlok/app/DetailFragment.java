package com.sanlok.app;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Anip Mehta on 11/1/2015.
 */
public class DetailFragment extends Fragment {
    TextView text;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {

        View view = inflater.inflate(R.layout.menu, container, false);
        String menu = getArguments().getString("Menu");
        text = (TextView) view.findViewById(R.id.detail);
        text.setText(menu);
        return view;
    }
}
