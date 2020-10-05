package edu.neu.mad_sea.amrutapathak.datastructimpl;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class Trie {
    private static final String TAG = "Trie";

    boolean isAWord;
    Map<Character, Trie> trieMap;

    public Trie(){
        isAWord = false;
        trieMap = new HashMap<>();
    }

    public void put(String word){
        Trie t = this;

        for(Character c : word.toCharArray()){
            if(t.trieMap.get(c) == null){
                t.trieMap.put(c,new Trie());
            }
            t = t.trieMap.get(c);
        }

        t.isAWord = true;


    }

    public boolean isWordPresent(String word){
        //Log.d(TAG, "isWordPresent: started");
        Trie t = this;
if(word.equals("roti")){
    Log.d(TAG, "isWordPresent: roti");
}
        for(Character c : word.toCharArray()){
            t = t.trieMap.get(c);
            if(word.equals("roti")){
                Log.d(TAG, "isWordPresent: roti"+t.trieMap.get(c)+t.isAWord);
            }
            if(t == null || t.trieMap== null ){
                return false;
            }

        }

        return t.isAWord;
    }
}
