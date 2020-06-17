package com.example.pass;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {
    EditText editTextTitel;
    EditText editTextPassword;
    Button buttonSave;
    String theKeyEncription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().hide();
        try {
            theKeyEncription = getIntent().getStringExtra("globalPassword");
        }
        catch (Exception e){
            Intent i = new Intent(getApplicationContext(),LogActivity.class);
            startActivity(i);
            finish();
        }
        editTextTitel = (EditText) findViewById(R.id.editTextTitel);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSave = (Button) findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "";
                if(editTextTitel.getText()==null|| editTextTitel.getText().toString().equals("")||
                   editTextPassword.getText()==null|| editTextPassword.getText().toString().equals("")){
                    makeAlert("All fields must be filled");
                }
                else{
                    addNewPass();
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    i.putExtra("globalPassword", theKeyEncription);
                    startActivity(i);
                    finish();
                }
            }
        });

    }


    private void makeAlert(String textToAlert){
        final android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(textToAlert)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    private void addNewPass(){
        Log.d("in addNewPass main","in addNewPass main ");
        LogicClass.addNewPass(this,editTextPassword.getText().toString(),editTextTitel.getText().toString(),theKeyEncription);
    }
}
