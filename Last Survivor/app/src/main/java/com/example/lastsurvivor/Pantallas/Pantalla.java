package com.example.lastsurvivor.Pantallas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.TextPaint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

import com.example.lastsurvivor.MainActivity;
import com.example.lastsurvivor.R;

import java.lang.reflect.Array;

/**
 * Pantalla sera la clase padre de la que heredarán el resto de pantallas
 * @author Carlos Daniel Tabares Comesaña
 * @version 1.0 18/03/2022
 */
public class Pantalla {
    /**
     * Representa el numero de escena actual
     */
    int numEscena;

    /**
     * Contexto actual
     */
    Context context;

    /**
     * Ancho de la pantalla
     */
    int anchoPantalla;

    /**
     * Alto de la pantalla
     */
    int altoPantalla;

    /**
     * Imagen de fondo del juego
     */
    Bitmap bitmapFondo;

    /**
     * Boton de volver para las distintas pantallas
     */
    RectF botonVolver;

    /**
     * Gestiona volumen y efectos sonoros
     */
    public AudioManager audioManager;

    /**
     * Imagen para el boton de volver
     */
    Bitmap bitmapVolver;

    /**
     * Pincel utilizado
     */
    Paint paint;

    /**
     * Nueva fuente
     */
    Typeface fuente;

    /**
     * Constructor de la clase Pantalla
     * @param numEscena numero de escena actual
     * @param context contexto actual
     * @param anchoPantalla ancho de la pantalla
     * @param altoPantalla alto de la pantalla
     */
    public Pantalla(int numEscena,Context context, int anchoPantalla, int altoPantalla) {
        this.context = context;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        this.numEscena=numEscena;

        paint = new Paint();
        paint.setColor(Color.GREEN); // Establecemos el color de Paint a verde
        paint.setAlpha(150); // Se establece un valor de transparencia. Valores entre 0..255
        paint.setStyle(Paint.Style.FILL); // Indica que se dibujara el relleno de un objeto
        paint.setAntiAlias(true); // Habilitamos el antialiasing para evitar los dientes de sierra en el texto

        bitmapFondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.fondonoche2);
        bitmapFondo = Bitmap.createScaledBitmap(bitmapFondo,anchoPantalla,altoPantalla,false);//esto escala la imagen al tamaño de la pantalla
        audioManager=(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        botonVolver=new RectF(anchoPantalla / 80, (altoPantalla / 15), anchoPantalla / 13, altoPantalla / 5.5f);
        bitmapVolver = BitmapFactory.decodeResource(context.getResources(), R.drawable.returnarrow);
        bitmapVolver = Bitmap.createScaledBitmap(bitmapVolver,(int)botonVolver.width(),(int)botonVolver.height(),false);

        fuente = Typeface.createFromAsset(context.getAssets(), "font/narrenschiffregular.otf");
    }

    /**
     * Devuelve el numero de escena
     * @return numero de escena
     */
    public int getNumEscena() {
        return numEscena;
    }

    /**
     * Dibuja el boton para volver atrás que se usará en las distintas pantallas
     * @param canvas canvas
     */
    public void dibujarVolver(Canvas canvas){
        canvas.drawRect(botonVolver,paint);
        canvas.drawBitmap(bitmapVolver,botonVolver.left,botonVolver.top,null);
    }

    /**
     * Dibuja en pantalla
     * @param canvas lienzo
     */
    public void dibujar(Canvas canvas){
        if (canvas!=null && bitmapFondo!=null){
            canvas.drawBitmap(bitmapFondo,0,0,null);
        }
    }

    /**
     * Actualiza la física de los elementos en pantalla
     */
    public void actualizarFisica(){

    }

    /**
     * Gestionar acciones sobre la pantalla
     * @param event evento que se produce
     * @return numero escena a la que se cambia
     */
    public int onTouch(MotionEvent event){
        return numEscena;
    }
}


