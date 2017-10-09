package com.rijo.umbrella.activities;

/**
 * Created by rijogeorge on 10/9/17.
 */

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.rijo.umbrella.R;

public class UmbrellaPreferencesActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    SharedPreferences SPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SPreference.registerOnSharedPreferenceChangeListener(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        SPreference.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        MainActivity.preferenceChanged=true;
    }


    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            EditTextPreference edit_Pref = (EditTextPreference)
                    getPreferenceScreen().findPreference("zipValue");
            edit_Pref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    // put validation here..
                    if(String.valueOf(newValue).length()==5){
                        return true;
                    }else{
                        return false;
                    }
                }
            });
        }
    }

}