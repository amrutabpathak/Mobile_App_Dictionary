package edu.neu.mad_sea.amrutapathak.singleton;

import android.content.Context;

import edu.neu.mad_sea.amrutapathak.DictionaryImpl;

public class DictSingleton {
    private static DictionaryImpl single_instance = null;

    public static DictionaryImpl getSingle_instance(Context c) {
        if (single_instance == null){
            single_instance = new DictionaryImpl(c);
        }
        return single_instance;
    }
}
