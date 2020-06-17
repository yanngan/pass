package com.example.pass;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PasswordAdaptor extends RecyclerView.Adapter<PasswordAdaptor.ViewHolder> {

    private static final String TAG = "passwordAdapter";
    private ArrayList<String> key = new ArrayList<>();
    private Context myContext;


    public PasswordAdaptor(Context myContext, ArrayList<String> key){
        this.key = key;
        this.myContext = myContext;
    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pass_row,parent,false);
        ViewHolder viewHolder =new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        Log.d("in onBindViewHolder",key.get(i));
            viewHolder.textViewTitel.setText(key.get(i));
            SharedPreferences sp = myContext.getSharedPreferences("mySherePreference", Context.MODE_PRIVATE);
            String thePassCrypted = sp.getString(key.get(i), "");
            final String thePassDecrypted  = LogicClass.dencryption(((MainActivity)myContext).globalPassword,thePassCrypted);

            if(thePassDecrypted!=null&& thePassDecrypted.length()!=0){
                viewHolder.textViewThePass.setText(thePassDecrypted);
                viewHolder.imageButtonCopy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager clipboard = (ClipboardManager) myContext.getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("",thePassDecrypted);
                        clipboard.setPrimaryClip(clip);
                        Toast toast = Toast.makeText(myContext.getApplicationContext(),
                                "Copied!!",
                                Toast.LENGTH_SHORT);

                        toast.show();
                    }
                });
                viewHolder.imageButtonEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(myContext, EditActivity.class);
                        intent.putExtra("titel", key.get(i));
                        intent.putExtra("thePass",thePassDecrypted);
                        intent.putExtra("globalPassword",((MainActivity)myContext).globalPassword);
                        myContext.startActivity(intent);
                        ((MainActivity) myContext).finish();
                    }
                });
                viewHolder.imageButtonRemove.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                        LogicClass.removePass(key.get(i),(Activity)myContext);
                          Intent intent = new Intent(myContext, MainActivity.class);
                          intent.putExtra("globalPassword",((MainActivity)myContext).globalPassword);
                          myContext.startActivity(intent);
                          ((MainActivity) myContext).finish();
                      }
                  });
            }
            else {
                viewHolder.textViewTitel.setVisibility(View.GONE);
                viewHolder.textViewThePass.setVisibility(View.GONE);
                viewHolder.imageButtonCopy.setVisibility(View.GONE);
                viewHolder.imageButtonEdit.setVisibility(View.GONE);
                viewHolder.imageButtonRemove.setVisibility(View.GONE);
                ((MainActivity) myContext).imageButtonAdd.setVisibility(View.GONE);
                ((MainActivity) myContext).textViewTITLE.setText("ERROR!");

                final android.app.AlertDialog.Builder builder = new AlertDialog.Builder((MainActivity)myContext);
                builder.setCancelable(false);
                builder.setMessage("your key is incorrect.. \ntry again")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                Intent intent = new Intent((MainActivity)myContext, LogActivity.class);
                                myContext.startActivity(intent);
                                ((MainActivity) myContext).finish();
                            }
                        });
                builder.show();
            }
    }

    @Override
    public int getItemCount() {
        return key.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewTitel;
        TextView textViewThePass;
        ImageButton imageButtonCopy;
        ImageButton imageButtonEdit;
        ImageButton imageButtonRemove;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitel = itemView.findViewById(R.id.textViewTitel);
            textViewThePass = itemView.findViewById(R.id.textViewThePass);
            imageButtonCopy = itemView.findViewById(R.id.imageButtonCopy);
            imageButtonEdit = itemView.findViewById(R.id.imageButtonEdit);
            imageButtonRemove = itemView.findViewById(R.id.imageButtonRemove);
        }
    }
}
