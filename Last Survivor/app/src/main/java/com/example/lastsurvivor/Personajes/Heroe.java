package com.example.lastsurvivor.Personajes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.example.lastsurvivor.Juego;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Heroe, gestiona nuestro personaje en el juego
 * @author Carlos Daniel Tabares Comesaña
 * @version 1.0 18/03/2022
 */
public class Heroe {

    /**
     * Objeto de tipo Vida del héroe que representa su vida real
     */
    public Vida vida;

    /**
     * Pincel utilizado
     */
    Paint paint;

    /**
     * Héroe
     */
    public static RectF rect;

    /**
     * Réctagulos que representan los muros de la base del héroe
     */
    RectF muro1, muro2, muro3, muro4, muro5;

    /**
     * Contexto actual
     */
    public Context context;

    /**
     * Determina si el héroe esta disparando
     */
    public boolean disparando = false;

    /**
     * Determina el lado al que mira el héroe
     */
    public boolean orientacion = false;//true derecha , false izquierda

    /**
     * Lista de imágenes con efecto de disparo
     */
    public ArrayList<Bitmap> imagenesDisparo = new ArrayList<>();

    /**
     * Lista de imágenes con efecto de disparo invertidas
     */
    public ArrayList<Bitmap> imagenesDisparoespejo = new ArrayList<>();

    /**
     * Lista auxiliar que nos ayuda a ahorrar código a la hora de dibujar
     * las imágenes
     */
    public ArrayList<Bitmap> auxImagenes;

    /**
     * Control de frames para el efecto de disparo del héroe
     */
    int numFrame = 1;

    /**
     * Constructor de la clase Heroe
     * @param context contexto actual
     */
    public Heroe(Context context) {
        vida = new Vida(500);//debe ser 500
        paint = new Paint();
        paint.setColor(Color.MAGENTA);
        auxImagenes = imagenesDisparo;
        rect = new RectF(Juego.anchoPantalla / 2.3f, Juego.altoPantalla / 1.3f, Juego.anchoPantalla / 1.7f, Juego.altoPantalla / 1.09f);
        muro1 = new RectF(Juego.anchoPantalla / 2.3f, Juego.altoPantalla / 1.15f, Juego.anchoPantalla / 2.1f, Juego.altoPantalla / 1.09f);
        muro2 = new RectF(Juego.anchoPantalla / 1.7f - muro1.width(), Juego.altoPantalla / 1.15f, Juego.anchoPantalla / 1.7f, Juego.altoPantalla / 1.09f);
        muro3 = new RectF(Juego.anchoPantalla / 2.3f, Juego.altoPantalla / 1.3f, Juego.anchoPantalla / 2.1f, rect.top + muro1.height());
        muro4 = new RectF(Juego.anchoPantalla / 1.7f - muro1.width(), Juego.altoPantalla / 1.3f, Juego.anchoPantalla / 1.7f, rect.top + muro1.height());
        muro5 = new RectF(Juego.anchoPantalla / 2.3f, Juego.altoPantalla / 1.4f, Juego.anchoPantalla / 1.7f, Juego.altoPantalla / 1.3f);

        this.context = context;
        for (int i = 1; i <= 11; i++) {
            imagenesDisparo.add(getBitmapFromAssets("Personaje/img" + i + ".png"));
            imagenesDisparo.set(i - 1, Bitmap.createScaledBitmap(imagenesDisparo.get(i - 1), (int) rect.width(), (int) rect.height(), true));
            imagenesDisparoespejo.add(espejo(imagenesDisparo.get(i - 1), true));
        }
    }

    public void setOrientacion(boolean orientacion) {
        this.orientacion = orientacion;
        if (orientacion) {
            auxImagenes = imagenesDisparoespejo;
        } else {
            auxImagenes = imagenesDisparo;
        }
    }

    /**
     * Permite acceder a la carpeta assets
     * @param fichero ruta de la imágen
     * @return imágen
     */
    public Bitmap getBitmapFromAssets(String fichero) {
        try {
            InputStream is = context.getAssets().open(fichero);
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Dibujar en pantalla el héroe y sus animaciones
     * @param canvas lienzo
     */
    public void dibujar(Canvas canvas) {
        paint.setColor(Color.rgb(46, 75, 82));//cambio de color para diferenciar los muros
        canvas.drawRect(rect, paint);
        paint.setColor(Color.BLACK);//cambio de color para diferenciar los muros
        canvas.drawRect(muro1, paint);
        canvas.drawRect(muro2, paint);
        canvas.drawRect(muro3, paint);
        canvas.drawRect(muro4, paint);
        canvas.drawRect(muro5, paint);
        vida.dibujar(canvas, rect);

        if (disparando) {
            numFrame++;
            if (numFrame >= auxImagenes.size()) {
                numFrame = 1;
                disparando = false;
            }
        }

        canvas.drawBitmap(auxImagenes.get(numFrame), rect.left, rect.top, null);
    }

    /**
     * Realiza el espejo de la imágen (la invierte)
     * @param imagen Imágen a invertir
     * @param horizontal determina si se debe invertir la imágen
     * @return imágen invertida
     */
    public Bitmap espejo(Bitmap imagen, Boolean horizontal) {
        Matrix matrix = new Matrix();
        if (horizontal) matrix.preScale(-1, 1);
        else matrix.preScale(1, -1);
        return Bitmap.createBitmap(imagen, 0, 0, imagen.getWidth(),
                imagen.getHeight(), matrix, false);

    }
}
