package com.example.lastsurvivor.Personajes.Enemigos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.lastsurvivor.Juego;
import com.example.lastsurvivor.Personajes.Heroe;
import com.example.lastsurvivor.Personajes.Vida;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * CriaturaOrdinaria sera el mounstro básico que nos ataca en el juego
 * @author Carlos Daniel Tabares Comesaña
 * @version 1.0 18/03/2022
 */
public class CriaturaOrdinaria extends Enemigo {
    /**
     * Pincel utilizado
     */
    Paint paint;

    /**
     * Constructor de la clase CriaturaOrdinaria
     * @param x coordenada left del enemigo, indica si viene
     * de la izquierda o la derecha
     * @param context representa el contexto
     */
    public CriaturaOrdinaria(float x, Context context) {
        super( x, Juego.altoPantalla / 1.4f, Juego.anchoPantalla / 6.5f, Heroe.rect.height()*1.42f, context);
        paint = new Paint();
        vida = new Vida(100);
        daño = 20;//20
        tiempoDeAtaque = 1000;
        this.context=context;
        velocidad=-2;//-2

        for (int i = 1; i <= 10; i++) {//Bucle para imagenes (Reutilizamos el bucle para todas las imagenes)
            imagenesEnemigoMuerte.add(getBitmapFromAssets("Enemigos/CriaturaOrdinaria/Muerte/m"+i+".png"));
            imagenesEnemigoMuerte.set(i-1,Bitmap.createScaledBitmap(imagenesEnemigoMuerte.get(i-1), (int) rect.width(), (int) rect.height(), true));
            if (rect.left>Juego.anchoPantalla/2){
                imagenesEnemigoMuerte.set(i-1,espejo(imagenesEnemigoMuerte.get(i-1),true));
            }

            if (i<=8){
                imagenesEnemigoCaminar.add(getBitmapFromAssets("Enemigos/CriaturaOrdinaria/caminar/caminar"+i+".png"));
                imagenesEnemigoCaminar.set(i-1,Bitmap.createScaledBitmap(imagenesEnemigoCaminar.get(i-1), (int) rect.width(), (int) rect.height(), true));

                imagenesEnemigoAtacar.add(getBitmapFromAssets("Enemigos/CriaturaOrdinaria/Atacar/a"+i+".png"));
                imagenesEnemigoAtacar.set(i-1,Bitmap.createScaledBitmap(imagenesEnemigoAtacar.get(i-1), (int) rect.width(), (int) rect.height(), true));

                if (rect.left>Juego.anchoPantalla/2){
                    imagenesEnemigoCaminar.set(i-1,espejo(imagenesEnemigoCaminar.get(i-1),true));
                    imagenesEnemigoAtacar.set(i-1,espejo(imagenesEnemigoAtacar.get(i-1),true));
                }
            }

            if (i<=4){
                imagenesEnemigoHit.add(getBitmapFromAssets("Enemigos/CriaturaOrdinaria/hit/h"+i+".png"));
                imagenesEnemigoHit.set(i-1,Bitmap.createScaledBitmap(imagenesEnemigoHit.get(i-1), (int) rect.width(), (int) rect.height(), true));
                if (rect.left>Juego.anchoPantalla/2){
                    imagenesEnemigoHit.set(i-1,espejo(imagenesEnemigoHit.get(i-1),true));
                }
            }
        }
    }

    /**
     * Dibujar en pantalla
     * @param canvas lienzo
     */
    public void dibujar(Canvas canvas) {
        vida.dibujar(canvas, rect);
        dibujarSpriteEnemigo(canvas);
    }


    /**
     * Mueve al enemigo
     */
    public void moverEnemigo() {
        if (posicion.x > Heroe.rect.right) {
            posicion.x += velocidad;
            actualizaRec();
        } else {
            if (posicion.x + tx < Heroe.rect.left) {
                posicion.x -= velocidad;
                actualizaRec();
            } else {
                if (!posicionDeAtaque) {
                    posicionDeAtaque = true;
                    cargaAtaque = System.currentTimeMillis();
                }
            }
        }
    }
}
