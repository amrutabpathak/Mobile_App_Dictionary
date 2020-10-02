package edu.neu.mad_sea.amrutapathak;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import edu.neu.mad_sea.amrutapathak.beans.DictionaryBean;
import edu.neu.mad_sea.amrutapathak.singleton.DictSingleton;

public class TestDictionaryActivity extends AppCompatActivity {
    private static final String TAG = "TestDictionaryActivity";
    private DictionaryBean dictionaryBean;
    private ListView lvDictResults;
    private ArrayAdapter arrAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_dictionary);
        Log.d(TAG, "onCreate: started.");
        createComponents();
    }

    private void createComponents(){
        Button btnFind = findViewById(R.id.btnSubmit);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getResultingWords();
            }
        });

        Button btnAck = findViewById(R.id.btnAck);
        btnAck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAckActivity();
            }
        });

        Button btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        final Spinner snr = (Spinner) findViewById(R.id.spnrNumberofLetters);
        ArrayAdapter<CharSequence> spnrAdptr = ArrayAdapter.createFromResource(this,
                 R.array.numbers, R.layout.support_simple_spinner_dropdown_item);
        spnrAdptr.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        snr.setAdapter(spnrAdptr);
        snr.setSelection(0);


    }

    private void getResultingWords(){
        dictionaryBean = new DictionaryBean();

          validateAndPopulateData();
        dictionaryBean = DictSingleton.getSingle_instance(getApplicationContext()).getWords(dictionaryBean);
        if(dictionaryBean.getResultingWordSet() == null || dictionaryBean.getResultingWordSet().size() == 0){
            Toast.makeText(this,"No results found for this search! Please try with different search parameters",Toast.LENGTH_LONG);
            return;
        }

        lvDictResults = (ListView)findViewById(R.id.lvResult);
        arrAdapter = new ArrayAdapter(TestDictionaryActivity.this, android.R.layout.simple_list_item_1, new ArrayList<String>(dictionaryBean.getResultingWordSet()));
        lvDictResults.setAdapter(arrAdapter);

    }

    private  void validateAndPopulateData(){

        Spinner wordLengthSpinner = findViewById(R.id.spnrNumberofLetters);
        String wordLengthStr = wordLengthSpinner.getSelectedItem().toString();


        EditText etAvailableLetters = findViewById(R.id.etconstraintText);
        String availableLetter = etAvailableLetters.getText().toString();
        boolean areLettersValid = validateLetters(availableLetter);
        if(!areLettersValid){
            return ;
        }

        EditText etConstraintPattern = findViewById(R.id.etLetterPlacement);
        String constPattern = etConstraintPattern.getText().toString();
        boolean isConstPatternValid = validateConstraint(constPattern);
        if(!isConstPatternValid){
            return ;
        }

        dictionaryBean.setWordLength(Integer.parseInt(wordLengthStr));
        dictionaryBean.setAvailableLetters(availableLetter.toLowerCase());
        dictionaryBean.setConstraintPattern(constPattern.toLowerCase());
        return ;
    }

    private boolean validateLetters(String availableLetter){
        if(availableLetter == null || availableLetter.trim().length() == 0){
            Toast.makeText(this,"Please enter some letters!",Toast.LENGTH_LONG);
            return false;
        }

        for(Character c : availableLetter.toCharArray()){
            if(Character.isLetter(c)){
               continue;
            }else{
                Toast.makeText(this,"Please enter valid alphabets!",Toast.LENGTH_LONG);
                return false;
            }
        }
        return true;
    }

    private boolean validateConstraint(String constPattern) {
        if(constPattern != null ){
            for(Character c : constPattern.toCharArray()){
                if(Character.isLetter(c) || Character.isSpaceChar(c)){
                    continue;
                }else{
                    Toast.makeText(this,"Please enter valid alphabets!",Toast.LENGTH_LONG);
                    return false;
                }
            }
        }

        return true;
    }

    private void goToAckActivity(){
        Intent intent = new Intent(this, AcknowledgementActivity.class);
        startActivity(intent);

    }

    private void clear(){
        //.setVisibility(View.GONE);
        //.setVisibility(View.GONE);

        EditText constLetters =
                (EditText) findViewById(R.id.etconstraintText);
        constLetters.setText("");

        EditText etAlphabetsPlacements =
                (EditText) findViewById(R.id.etLetterPlacement);
        etAlphabetsPlacements.setText("");

        Spinner spnrNumberofLetters = (Spinner) findViewById(R.id.spnrNumberofLetters);
        spnrNumberofLetters.setSelection(0);

    }


}