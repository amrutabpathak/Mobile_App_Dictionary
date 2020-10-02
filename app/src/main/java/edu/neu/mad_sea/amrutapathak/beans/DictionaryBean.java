package edu.neu.mad_sea.amrutapathak.beans;

import java.util.Set;

public class DictionaryBean {

    private int wordLength;
    private String availableLetters;
    private String constraintPattern;
    private Set<String> resultingWordSet;

    public Set<String> getResultingWordSet() {
        return resultingWordSet;
    }

    public void setResultingWordSet(Set<String> resultingWordSet) {
        this.resultingWordSet = resultingWordSet;
    }


    public int getWordLength() {
        return wordLength;
    }

    public void setWordLength(int wordLength) {
        this.wordLength = wordLength;
    }

    public String getAvailableLetters() {
        return availableLetters;
    }

    public void setAvailableLetters(String availableLetters) {
        this.availableLetters = availableLetters;
    }

    public String getConstraintPattern() {
        return constraintPattern;
    }

    public void setConstraintPattern(String constraintPattern) {
        this.constraintPattern = constraintPattern;
    }





}
