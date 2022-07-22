package com.example.lastsurvivor.Personajes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.Log;

import com.example.lastsurvivor.Juego;

/**
 * Vida, equivale a la vida nuestra y de todos los enemigos
 * @author Carlos Daniel Tabares Comesaña
 * @version 1.0 18/03/2022
 */
public class Vida {
    /**
     * Representa la cantidad de vida total que tendrá el personaje
     */
    private int vida;

    /**
     * Pincel utilizado
     */
    Paint paint;

    /**
     * Representa la cantidad de vida actual que le queda al personaje
     */
    float vidaActual;

    //float puntosDeVida;
    //boolean recienCreado;

    private int dañoRecibido = 0;

    /**
     * Representan
     * tx el tamaño en ancho del personaje
     * ty el tamaño en alto del personaje
     * vidaMaxima representa hasta donde se dibujará la vida
     */
    float tx, ty, vidaMaxima;
    //float vidaQuitada = 0;

    /**
     * Pincel utilizado
     */
    TextPaint tpaint;
    //int vidaMaxima2=-1;

    /**
     * Determina el color de la vida que dependerá de la cantidad de vida
     * que le quede al personaje
     */
    int getVidaMaximaColor;

    /**
     * Constructor de la clase Vida
     * @param vida cantidad de vida del personaje
     */
    public Vida(int vida) {
        this.vida = vida;
        paint = new Paint();
        paint.setColor(Color.GREEN);
        //recienCreado = true;
        vidaActual=0;
        tpaint=new TextPaint();
        tpaint.setColor(Color.WHITE);

        getVidaMaximaColor=this.vida;
    }

//    public float getVidaActual() {
//        return vidaActual;
//    }
//
//    public void setVidaActual(float vidaActual) {
//        this.vidaActual = vidaActual;
//    }

    public int getDañoRecibido() {
        return dañoRecibido;
    }

    public void setDañoRecibido(int dañoRecibido) {
        this.dañoRecibido = dañoRecibido;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    /**
     * Dibujar en pantalla la vida de cada enemigo
     * @param canvas lienzo
     */
    public void dibujar(Canvas canvas, RectF rect) {
        tx = (rect.right - rect.left);
        ty = (rect.bottom - rect.top);
        vidaMaxima = rect.right - (rect.width() / 8);
        tpaint.setTextSize(rect.width()/4);

//        if (getDañoRecibido() != 0) {
//            vidaQuitada = puntosDeVida * getDañoRecibido();
//            dañoRecibido = 0;
//            setVidaActual(getVidaActual()+vidaQuitada);
//        }

        //rect = new RectF(rect.left + (rect.width() / 14), rect.top + ty / 1.2f, vidaMaxima-getVidaActual(), rect.top + ty / 1.1f);
        rect = new RectF(rect.left + (rect.width() / 8), rect.top + ty / 1.18f, vidaMaxima, rect.top + ty / 1.1f);

//        if (recienCreado) {
//            puntosDeVida = rect.width() / getVida();
//            recienCreado=false;
//        }

        //Esto sera para cambiar los colores de la vida mientras no dispongamos de la opcion de reducir la barra
        if (getVida()>(getVidaMaximaColor/1.5)){
            paint.setColor(Color.GREEN);
        }
        if (getVida()<=(getVidaMaximaColor/1.8)){
            paint.setColor(Color.YELLOW);
        }
        if (getVida()<(getVidaMaximaColor/3)){
            paint.setColor(Color.RED);
        }
        canvas.drawRect(rect, paint);//dibuja la barra e vida
    }
}
