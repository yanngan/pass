package com.example.pass;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class LogicClass {

    private static SecretKeySpec secretKey;
    private static byte[] key;



    public static void setKey(String myKey)
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String encryption( String secret, String strToEncrypt)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String dencryption( String secret ,String strToDecrypt)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    /**
     *
     * @param activity
     * @return the Set or null if not existing
     */
    public static Set<String> getSet(Activity activity){
        Log.d("getSet","start");
        SharedPreferences sp = activity.getSharedPreferences("mySherePreference", Context.MODE_PRIVATE);
        Log.d("getSet","afetr getSharedPreferences");
        try {
            HashSet<String> mySet = (HashSet) sp.getStringSet("setKey", null);
            return mySet;
        }
        catch (Exception e){
            return  null;
        }
    }

    public static ArrayList<String> SetToArrayList(Activity activity){
        Log.d("SetToArray","start");
        //Log.d("SetToArray", "lenght = "+getSet(activity).toArray().length);
        Set<String> set = getSet(activity);
        if(set==null){
            return null;
        }
        else{
            Object[] s = getSet(activity).toArray();
            Log.d("getAllPassword","after getSet");
             ArrayList <String> toReturn = new ArrayList<>();
            for (int i = 0; i <s.length ; i++) {
                String a = (String)s[i];
                toReturn.add(a);
            }
            return toReturn;
        }
    }


    public static  void addNewPass(Activity activity, String thePass, String theTitel,String theEncriptKey){
        Log.d("in addNewPass", "in start");
        Set<String> mySet = getSet(activity);
        if(mySet == null){
            Log.d("in addNewPass", "in mySet == null");
            SharedPreferences sp = activity.getSharedPreferences("mySherePreference", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            thePass = encryption(theEncriptKey,thePass);
            Log.d("in addNewPass-null",thePass);
            editor.putString(theTitel,thePass).apply(); //add the password to the SharedPreferences
            HashSet<String> newSetkey= new HashSet<>(); //add a new HashSet of key to the SharedPreferences
            newSetkey.add(theTitel);
            editor.putStringSet("setKey",newSetkey ).apply();
        }
        else{
            Log.d("in addNewPass", "in else");
            //add the password to the SharedPreferences
            SharedPreferences sp = activity.getSharedPreferences("mySherePreference", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            thePass = encryption(theEncriptKey,thePass);
            editor.putString(theTitel,thePass).apply(); //add the password to the SharedPreferences
            //add the key to the HashSet that contain the Key's
            mySet.add(theTitel);
            editor.putStringSet("setKey",mySet).apply();
        }
        SharedPreferences sp = activity.getSharedPreferences("mySherePreference", Context.MODE_PRIVATE);
        Log.d("end of add",sp.getString(theTitel,"notFaung"));
        Set<String> aa = sp.getStringSet("setKey",null);
        String text = "";
        if (aa!=null){
            text = ""+aa.size();
        }
        else{
            text = "nullllll";
        }
        Log.d("end of add setKey",""+text);


    }

    public static void removePass(String Key, Activity activity){
        Set<String> mySet = getSet(activity);
        mySet.remove(Key);
        SharedPreferences sp = activity.getSharedPreferences("mySherePreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(Key).apply();
    }

    public static void editPass (String key, String newValue, String globalPassword, Activity activity){
        SharedPreferences sp = activity.getSharedPreferences("mySherePreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        newValue = encryption(globalPassword,newValue);
        editor.putString(key,newValue).apply();
    }

    public static void getAllPassword(String Key, MainActivity theMainActivity){
        Log.d("getAllPassword","start");
        ArrayList<String> theKey = SetToArrayList((Activity)theMainActivity);
        Log.d("getAllPassword","after setToArray");
        //Log.d("in getAllPassword",""+theKey.size() );
        if(theKey==null||theKey.size()==0){
            theMainActivity.makeAlertAndGoToAddNewPass();
        }
        else{
            theMainActivity.keys=theKey;
            theMainActivity.creatTheRecyclerView();
        }
    }
}
