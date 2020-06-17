package com.example.pass;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LogActivity extends AppCompatActivity {
    TextView textViewResult;
    TextView textViewDecry;
    EditText editTextKey;
    Button buttonSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        getSupportActionBar().hide();


        textViewResult = (TextView) findViewById(R.id.textViewResult);
        textViewDecry = (TextView) findViewById(R.id.textViewDecry);
        editTextKey = (EditText) findViewById(R.id.editTextKey);
        buttonSubmit = (Button) findViewById(R.id.buttonSave);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextKey.getText()==null|| editTextKey.getText().toString().equals("")){
                    makeAlert("can't be empty");
                }
                else{
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    i.putExtra("globalPassword", editTextKey.getText().toString());
                    startActivity(i);
                    finish();
                }
            }
        });

        //String aa = LogicClass.encryption("test","my name is yann ganem");
        //textViewResult.setText("result:  "+aa);

        //String bb = LogicClass.dencryption("test",aa);
        //textViewDecry.setText(bb);

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
}
