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
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    String globalPassword;
    TextView TextViewTitel;
    EditText editTextPassword;
    Button buttonSave;
    String titel;
    String thePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getSupportActionBar().hide();
        globalPassword = getIntent().getStringExtra("globalPassword");
        titel = getIntent().getStringExtra("titel");
        thePass = getIntent().getStringExtra("thePass");

        TextViewTitel = (TextView) findViewById(R.id.TextViewTitel);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        TextViewTitel.setText(titel);
        editTextPassword.setText(thePass);


        buttonSave.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                    editThePass();
                  }
              }
        );






    }

    private void editThePass(){
        Log.d("in addNewPass main","in addNewPass main ");
        String key = titel;
        String newValue = editTextPassword.getText().toString();
        if (newValue.length()==0){
            /*Toast toast = Toast.makeText(getApplicationContext(),
                    "can't be empty bro!",
                    Toast.LENGTH_SHORT);*/

            final android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage("can't be empty bro!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            builder.show();
            return;
        }
        LogicClass.editPass(key,newValue,globalPassword,this);
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        i.putExtra("globalPassword", globalPassword);
        startActivity(i);
        finish();
    }
}
