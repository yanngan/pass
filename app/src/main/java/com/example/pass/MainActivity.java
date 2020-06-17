package com.example.pass;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public ArrayList<String> keys;
    public ImageButton imageButtonAdd;
    public RecyclerView RecyclerView;
    public String globalPassword;
    public TextView textViewTITLE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("in MainActivity","on creat start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        keys = new ArrayList<>();
        imageButtonAdd = (ImageButton) findViewById(R.id.imageButtonAdd);
        RecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        textViewTITLE = (TextView) findViewById(R.id.textViewTITLE);
        try {
            globalPassword = getIntent().getStringExtra("globalPassword");
        }
        catch (Exception e){
            Intent i = new Intent(getApplicationContext(),LogActivity.class);
            startActivity(i);
            finish();
        }

        Log.d("in MainActivity","on creat after getExta");
        imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),AddActivity.class);
                i.putExtra("globalPassword",globalPassword);
                startActivity(i);
                finish();
            }
        });
        Log.d("in MainActivity","on creat beafor aetall");
        LogicClass.getAllPassword(globalPassword,this);
        Log.d("in MainActivity","on creat after aetall");
        /*
        Intent = new Intent(myContext, EditActivity.class);
        intent.putExtra("titel", key.get(i));
        intent.putExtra("thePass",thePassDecrypted);
        myContext.startActivity(intent);

         */

    }


    /*using from LogicClass.getAllPassword()
        if have no password yet
     */
    public void makeAlertAndGoToAddNewPass(){
        final android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("you have no password yet go to add new password")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }


    private void getAllPassword(){
        LogicClass.getAllPassword(globalPassword,this);

    }
    public void creatTheRecyclerView() {
        PasswordAdaptor thepasswordAdaptor = new PasswordAdaptor(this,keys);
        RecyclerView.setAdapter(thepasswordAdaptor);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //finish();
    }
}
