package edu.neu.mad_sea.amrutapathak;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import edu.neu.mad_sea.amrutapathak.beans.DictionaryBean;
import edu.neu.mad_sea.amrutapathak.util.Constants;

public class DictionaryImpl {
 private HashSet<String> dictSet;
 private HashMap<Integer,Character> posChar;
    private HashSet<String> resultSet;
    private List<Character> chList;


    public DictionaryImpl(Context c){
        dictSet = new HashSet<>();
        resultSet = new HashSet<>();
        buildDictionary(c);

}

public void buildDictionary(Context c){
        try {
            String l = null;
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(c.getAssets().open(Constants.DICTIONARY_PATH)));

            while ((l = br.readLine()) != null){
                dictSet.add(l);
            }
        }catch (FileNotFoundException f){
            System.out.println("File not found");
            f.printStackTrace();
        }catch (Exception e){
            System.out.println("Other error");
            e.printStackTrace();
        }
    }

    public DictionaryBean getWords(DictionaryBean bean){
        Set<String> set = new HashSet<String>();
        posChar = new HashMap<>();
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

        for(Character ch : allowedWords.toCharArray()){
            chList.add(ch);
        }
        recurse(posChar,chList,0,bean.getWordLength(),0,new StringBuilder());
        bean.setResultingWordSet(set);
        return bean;

    }

    private void recurse(HashMap<Integer,Character> posChar, List<Character> allowedLetters,int allowedLetterPos, int wordLength, int pos,StringBuilder sb ){
        if(pos == wordLength -1){
            resultSet.add(sb.toString());
        }

        if(pos >= wordLength || allowedLetterPos >=  wordLength){
            return;
        }

        for(int i = allowedLetterPos; i < allowedLetters.size(); i++){
           if(posChar.containsKey(i)){
               sb.append(posChar.get(i));
           }else{
               sb.append(allowedLetters.get(i));
           }

           recurse(posChar,allowedLetters, allowedLetterPos+1,wordLength,pos+1,sb);
        }


    }


}
