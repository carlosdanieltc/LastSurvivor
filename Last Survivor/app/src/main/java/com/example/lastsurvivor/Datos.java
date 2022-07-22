package com.example.lastsurvivor;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Datos, pantalla para guardado y obtención de datos
 * @author Carlos Daniel Tabares Comesaña
 * @version 1.0 18/03/2022
 */
public class Datos {

    /**
     * Guarda en memoria una booleana
     * @param clave clave por la que se guarda el dato
     * @param valor valor guardado
     * @param context contexto
     * @return devuelve un SharedPreferences con el guardado
     * del dato realizado
     */
    public static boolean guarda(String clave, boolean valor, Context context){
        SharedPreferences sharedPreferences= context.getSharedPreferences("datos",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putBoolean(clave,valor);
        return editor.commit();
    }

    /**
     * Guarda en memoria un int
     * @param clave clave por la que se guarda el dato
     * @param valor valor guardado
     * @param context contexto
     * @return devuelve un SharedPreferences con el guardado
     * del dato realizado
     */
    public static boolean guarda(String clave, int valor, Context context){
        SharedPreferences sharedPreferences= context.getSharedPreferences("datos",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putInt(clave,valor);
        Log.i("pref", "leeInt: clave: "+clave+" valor: "+valor);
        return editor.commit();
    }

    /**
     * Guarda en memoria un string
     * @param clave clave por la que se guarda el dato
     * @param valor valor guardado
     * @param context contexto
     * @return devuelve un SharedPreferences con el guardado
     * del dato realizado
     */
     public static boolean guarda(String clave, String valor, Context context){
        SharedPreferences sharedPreferences= context.getSharedPreferences("datos",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString(clave,valor);
        return editor.commit();
    }

    /**
     * Lee-obtiene de memoria un dato de tipo entero
     * @param clave clave por la que se obtiene el dato
     * @param context contexto
     * @return devuelve un SharedPreferences con el valor
     * del dato solicitado
     */
    public static int leeInt(String clave,Context context){
        SharedPreferences sharedPreferences= context.getSharedPreferences("datos",Context.MODE_PRIVATE);
        Log.i("pref", "leeInt: "+(sharedPreferences.getInt(clave,0)));
        return sharedPreferences.getInt(clave,0);
    }

    /**
     * Lee-obtiene de memoria un dato de tipo string
     * @param clave clave por la que se obtiene el dato
     * @param context contexto
     * @return devuelve un SharedPreferences con el valor
     * del dato solicitado
     */
    public static String leeString(String clave,Context context){
        SharedPreferences sharedPreferences= context.getSharedPreferences("datos",Context.MODE_PRIVATE);
        return sharedPreferences.getString(clave,"");
    }

    /**
     * Lee-obtiene de memoria un dato de tipo string que representa
     * un idioma
     * @param clave clave por la que se obtiene el dato
     * @param context contexto
     * @return devuelve un SharedPreferences con el valor
     * del dato solicitado
     */
    public static String leeStringIdioma(String clave,Context context){//exclusiva para idioma
        SharedPreferences sharedPreferences= context.getSharedPreferences("datos",Context.MODE_PRIVATE);
        return sharedPreferences.getString(clave,"ESP");
    }

    /**
     * Lee-obtiene de memoria un dato de tipo boolean
     * @param clave clave por la que se obtiene el dato
     * @param context contexto
     * @return devuelve un SharedPreferences con el valor
     * del dato solicitado
     */
    public static boolean leeBoolean(String clave,Context context){
        SharedPreferences sharedPreferences= context.getSharedPreferences("datos",Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(clave,true);
    }

}
