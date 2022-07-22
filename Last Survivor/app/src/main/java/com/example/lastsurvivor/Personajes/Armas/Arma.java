package com.example.lastsurvivor.Personajes.Armas;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.example.lastsurvivor.Juego;
import com.example.lastsurvivor.Personajes.Heroe;

import java.util.Random;

/**
 * Arma equivale al arma del juego, esta clase creará las balas que se disparan
 * @author Carlos Tabares
 * @version 1.0 18/03/2022
 */
public class Arma {
    /**
     * Representa la bala
     */
    public RectF bala;

    /**
     * Pincel utilizado
     */
    Paint paint;

    /**
     * Ancho y alto de la bala
     * tx ancho
     * ty alto
     */
    float tx, ty;

    /**
     * Punto 1 de la bala
     */
    public PointF posicion;

    /**
     * Indica hacia donde se está disparando
     */
    public String posDisparo;

    /**
     * Velocidad de la bala
     */
    int velocidad=-40;

    /**
     * Daño de la bala
     */
    public int daño=40;

    /**
     * Altura a la que viajará la bala
     */
    float altoBala=Heroe.rect.top+Heroe.rect.height()/2;

    /**
     * Constructor de la clase Arma
     * @param posDisparo Indica hacia donde se está disparando
     */
    public Arma(String posDisparo) {
        this.tx = Juego.anchoPantalla/100;
        this.ty = Juego.altoPantalla/80;
        if (posDisparo.equals("derecha")){
            this.posicion = new PointF(Heroe.rect.right-tx, altoBala);
        }
        if (posDisparo.equals("izquierda")){
            this.posicion = new PointF(Heroe.rect.left, altoBala);
        }

        this.posDisparo=posDisparo;
        this.paint = new Paint();
        this.paint.setColor(Color.YELLOW);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(10);

        bala = new RectF(posicion.x, posicion.y, posicion.x + tx, posicion.y + ty);

        actualizaHitbox();
    }

    /**
     * Dibujar en pantalla
     * @param c lienzo
     */
    public void dibujar(Canvas c) {
        c.drawRect(bala,paint);
    }

    /**
     * Actualiza la posicion de la bala
     */
    public void actualizaHitbox() {
        bala = new RectF(posicion.x, posicion.y, posicion.x + tx, posicion.y + ty);
    }

    /**
     * Mueve la bala
     */
    public void mover() {
        if (posDisparo.equals("derecha")){
            if (posicion.x <= Juego.anchoPantalla){
                posicion.x -= velocidad;
                actualizaHitbox();
            }
        }
        if (posDisparo.equals("izquierda")){
            if (posicion.x > 0-bala.width()*1.1) {
                posicion.x += velocidad;
                actualizaHitbox();
            }
        }
    }
}
