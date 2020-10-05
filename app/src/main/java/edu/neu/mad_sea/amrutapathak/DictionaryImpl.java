package edu.neu.mad_sea.amrutapathak;

import android.content.Context;
import android.icu.text.Edits;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import edu.neu.mad_sea.amrutapathak.beans.DictionaryBean;
import edu.neu.mad_sea.amrutapathak.datastructimpl.Trie;
import edu.neu.mad_sea.amrutapathak.util.Constants;

public class DictionaryImpl {
    private static final String TAG = "DictionaryImpl";

    private HashSet<String> dictSet;
 private HashMap<Integer,Character> posChar;
    private HashSet<String> resultSet;
    private List<Character> chList;
    private Trie t;
    private HashSet<String> set;


    public DictionaryImpl(Context c){
        dictSet = new HashSet<>();
        resultSet = new HashSet<>();
        t = new Trie();
        set = new HashSet<>();
        buildDictionary(c);


}

public void buildDictionary(Context c){
        try {
            String l = null;
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(c.getAssets().open(Constants.DICTIONARY_PATH)));

            while ((l = br.readLine()) != null){
                t.put(l);
            }
            System.out.println("File upload success");
        br.close();
        }catch (FileNotFoundException f){
            System.out.println("File not found");
            f.printStackTrace();
        }catch (Exception e){
            System.out.println("Other error");
            e.printStackTrace();
        }finally {
        }
    }

    public DictionaryBean getWords(DictionaryBean bean) {
        Log.d(TAG, "getWords: started");
        posChar = new HashMap<>();
        set = new HashSet<>();
        resultSet = new HashSet<>();
        String constPattern = bean.getConstraintPattern();
        String allowedWords = bean.getAvailableLetters();
        chList= new ArrayList<>();
        List<Character> chListTemp = new ArrayList<>();
        if(constPattern != null) {
            for (int i = 0; i < constPattern.length(); i++) {
                if (constPattern.charAt(i) != '_') {
                    posChar.put(i, constPattern.charAt(i));
                }
            }
        }
        Log.d(TAG, "getWords: "+posChar.toString());
        for(Character ch : allowedWords.toCharArray()){
            chList.add(ch);
        }
      //  recurse(posChar,chList,0,bean.getWordLength(),0,new StringBuilder());
        List<List<Character>> res = getAllPermutations(chList,bean.getWordLength());
        StringBuilder sb = new StringBuilder();

        for(List<Character> l : res){
            sb = new StringBuilder();
            for(int i = 0; i < bean.getWordLength(); i++){
                if(posChar.containsKey(i) && posChar.get(i) != l.get(i)){
                    sb = null;
                    break;
                }
                sb.append(l.get(i));
            }
            if(sb!= null){
                set.add(sb.toString());
            }
        }

        Iterator<String> itr = set.iterator();
        while(itr.hasNext()){
           // Log.d(TAG, "getWords: Inside set");
            String str = itr.next();
            if(t.isWordPresent(str)){
                resultSet.add(str);
            }
        }
        Log.d(TAG, "getWords: resultSet"+set);
        bean.setResultingWordSet(resultSet);
        Log.d(TAG, "getWords: "+bean.getResultingWordSet().toString());
        return bean;

    }

    /* private void recurse(HashMap<Integer,Character> posChar, List<Character> allowedLetters,int allowedLetterPos, int wordLength, int pos,StringBuilder sb ){
        if(pos == wordLength ){
            set.add(sb.toString());
            Log.d(TAG, "recurse: inside check");

        }

        if(pos >= wordLength ){
            return;
        }

        for(int i = allowedLetterPos; i < allowedLetters.size(); i++){
           if(posChar.containsKey(i)){
               sb.append(posChar.get(i));

           }else{
               sb.append(allowedLetters.get(i));
               recurse(posChar,allowedLetters, allowedLetterPos+1,wordLength,pos+1,sb);

           }
            recurse(posChar,allowedLetters, allowedLetterPos,wordLength,pos+1,sb);

           sb.deleteCharAt(sb.length()-1);
        }


    } */
/*
private void recurse(HashMap<Integer,Character> posChar,List<Character> allowedLetters,int allowedLetterPos,  int wordLength, int pos, StringBuilder sb  ){
    if(pos == wordLength ){
        return;
        set.add(sb.toString());
        Log.d(TAG, "recurse: inside check");

    }

    if(allowedLetterPos >= allowedLetters.size()){
        return;
    }

    if(posChar.containsKey(pos-1)){
        sb.append(posChar.get(pos-1));
        recurse(posChar, allowedLetters, allowedLetterPos, wordLength, pos+1, sb);
    }else{
        recurse(posChar, allowedLetters, allowedLetterPos+1, wordLength, pos, sb);
        sb.append(allowedLetters.get(allowedLetterPos));
        recurse(posChar, allowedLetters, allowedLetterPos+1, wordLength, pos+1, sb);

    }

    sb.deleteCharAt(sb.length()-1);
}
*/
    public  List<List<Character>> getAllPermutations(List<Character> allowedLetters,int wordLength) {
        List<List<Character>> output = new ArrayList<>();
        Queue<List<Character>> pQueue = new LinkedList<>();
        pQueue.add(new ArrayList<Character>());
        for (Character c : allowedLetters) {
            // we will take all existing permutations and add the current number to create new permutations
            int n = pQueue.size();
            for (int i = 0; i < n; i++) {
                List<Character> prevList = pQueue.poll();
                // create a new permutation by adding the current number at every position
                for (int j = 0; j <= prevList.size(); j++) {
                    List<Character> newPermutation = new ArrayList<Character>(prevList);
                    newPermutation.add(j, c);
                    if (newPermutation.size() == allowedLetters.size())
                        output.add(newPermutation);
                    else
                        pQueue.add(newPermutation);
                }
            }
        }
        return output;
    }
}
