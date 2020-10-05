package edu.neu.mad_sea.amrutapathak;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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
    private EditText etConstraintText;
    private Button  btnFind;
    private TextView tvTotalTime;
    private long startTime;
    private long endTime;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_dictionary);
        Log.d(TAG, "onCreate: started.");
        createComponents();
    }

    private void createComponents(){
         btnFind = findViewById(R.id.btnSubmit);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = System.currentTimeMillis();
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

         etConstraintText = findViewById(R.id.etconstraintText);
        etConstraintText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                enableFind();
            }
        });
        tvTotalTime = (TextView) findViewById(R.id.totalTimeId);
        tvTotalTime.setVisibility(View.GONE);
        lvDictResults = (ListView) findViewById(R.id.lvResult);

        final Spinner snr = (Spinner) findViewById(R.id.spnrNumberofLetters);
        ArrayAdapter<CharSequence> spnrAdptr = ArrayAdapter.createFromResource(this,
                 R.array.numbers, R.layout.support_simple_spinner_dropdown_item);
        spnrAdptr.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        snr.setAdapter(spnrAdptr);
        snr.setSelection(0);


    }

    private void enableFind(){
        boolean canFind = false;
        if(etConstraintText.getText()!= null && etConstraintText.getText().toString()!= null && etConstraintText.getText().toString().length()!=0){
            canFind = true;
        }
        btnFind.setEnabled(canFind);
    }
    private void getResultingWords(){

            dictionaryBean = new DictionaryBean();

            if(!validateAndPopulateData()){
                Toast.makeText(this, "Aww Snap ..Something went wrong. Please check your inputs", Toast.LENGTH_LONG);
                return;
            }
            dictionaryBean = DictSingleton.getSingle_instance(getApplicationContext()).getWords(dictionaryBean);
            if (dictionaryBean.getResultingWordSet() == null || dictionaryBean.getResultingWordSet().size() == 0) {
                Toast.makeText(this, "No results found for this search! Please try with different search parameters", Toast.LENGTH_LONG);
                return;
            }


            arrAdapter = new ArrayAdapter(TestDictionaryActivity.this, android.R.layout.simple_list_item_1, new ArrayList<String>(dictionaryBean.getResultingWordSet()));
            lvDictResults.setAdapter(arrAdapter);
            lvDictResults.setVisibility(View.VISIBLE);
            endTime = System.currentTimeMillis();
            tvTotalTime.setText(totalTimeTaken(startTime,endTime));
            tvTotalTime.setVisibility(View.VISIBLE);




    }

    private String totalTimeTaken(long startTime, long endTime){
        return "Total time taken "+(endTime-startTime)+ " ms.";
    }

    private  boolean validateAndPopulateData(){

        Spinner wordLengthSpinner = findViewById(R.id.spnrNumberofLetters);
        String wordLengthStr = wordLengthSpinner.getSelectedItem().toString();


        EditText etAvailableLetters = findViewById(R.id.etconstraintText);
        String availableLetter = etAvailableLetters.getText().toString();
        boolean areLettersValid = validateLetters(availableLetter);
        if(!areLettersValid){
            return false;
        }

        EditText etConstraintPattern = findViewById(R.id.etLetterPlacement);
        String constPattern = etConstraintPattern.getText().toString();
        boolean isConstPatternValid = validateConstraint(constPattern, availableLetter);
        if(!isConstPatternValid){
            return false;
        }

        dictionaryBean.setWordLength(Integer.parseInt(wordLengthStr));
        dictionaryBean.setAvailableLetters(availableLetter.toLowerCase());
        dictionaryBean.setConstraintPattern(constPattern.toLowerCase());
        return true;
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

    private boolean validateConstraint(String constPattern , String availableLetter) {
        if(constPattern != null ){
            Log.d(TAG, "validateConstraint: started");
            for(Character c : constPattern.toCharArray()){

                if(c!='_' && !availableLetter.contains(c+"")){
                    return false;
                }

                if(availableLetter!= null && availableLetter.length() < constPattern.length()){
                    return false;
                }
                if(Character.isLetter(c) || c == '_'){
                    continue;
                }else{
                    Toast.makeText(this,"Please enter valid alphabets!",Toast.LENGTH_LONG);
                    return false;
                }

            }
        }
        Log.d(TAG, "validateConstraint: " );
        return true;
    }

    private void goToAckActivity(){
        Intent intent = new Intent(this, AcknowledgementActivity.class);
        startActivity(intent);

    }

    private void clear(){

        EditText constLetters =
                (EditText) findViewById(R.id.etconstraintText);
        constLetters.setText("");

        EditText etAlphabetsPlacements =
                (EditText) findViewById(R.id.etLetterPlacement);
        etAlphabetsPlacements.setText("");

        Spinner spnrNumberofLetters = (Spinner) findViewById(R.id.spnrNumberofLetters);
        spnrNumberofLetters.setSelection(0);

        lvDictResults.setVisibility(View.GONE);

        tvTotalTime.setVisibility(View.GONE);





    }


}