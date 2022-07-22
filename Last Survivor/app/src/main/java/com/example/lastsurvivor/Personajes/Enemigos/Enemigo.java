package com.example.lastsurvivor.Personajes.Enemigos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import android.os.VibrationEffect;
import android.util.Log;

import com.example.lastsurvivor.Datos;
import com.example.lastsurvivor.Juego;
import com.example.lastsurvivor.MainActivity;
import com.example.lastsurvivor.Personajes.Vida;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Enemigo, clase de la cual heredan los enemigos
 * @author Carlos Daniel Tabares Comesaña
 * @version 1.0 18/03/2022
 */
public class Enemigo {
    /**
     * Punto que determina la posicion del enemigo
     */
    public PointF posicion;

    /**
     * Velocidad de movimiento
     */
    public int velocidad;

    /**
     * Recuadro que engloba al enemigo en si
     */
    public RectF rect;

    /**
     * Tamaño del enemigo
     * tx tamaño en ancho
     * ty tamaño en alto
     */
    float tx, ty;

    /**
     * Determina de que lado de la pantalla aparece el enemigo
     */
    public boolean apareceEnemigo;

    /**
     * Vida del enemigo
     */
    public Vida vida;

    /**
     * Daño del enemigo
     */
    public int daño;

    /**
     * Tiempo de ataque del enemigo
     */
    public int tiempoDeAtaque;

    /**
     * Tiempo que tarda en atacar el enemigo
     */
    public long cargaAtaque;

    /**
     * Indica que el enemigo ya esta en el punto
     * donde debe atacar
     */
    public boolean posicionDeAtaque;//1= izquierda//else derecha

    /**
     * Contexto actual
     */
    public Context context;

    /**
     * Controlador de frames para las imágenes al caminar
     */
    int numFrame = 0;

    /**
     * Controlador de frames para las imágenes al atacar
     */
    int numFrameAtaque= 0;

    /**
     * Controlador de frames para las imágenes al ser golpeado
     */
    int numFrameHit = 0;

    /**
     * Controlador de frames para las imágenes al morir
     */
    int numFrameMuerte = 0;

    /**
     * Tiempo trasncurrido desde último frame
     */
    long tframe=0;

    /**
     * Tiempo entre frames
     */
    int tickframe=100;

    /**
     * Lista de imágenes del enemigo al caminar
     */
    public ArrayList<Bitmap> imagenesEnemigoCaminar = new ArrayList<>();

    /**
     * Lista de imágenes del enemigo al atacar
     */
    public ArrayList<Bitmap> imagenesEnemigoAtacar = new ArrayList<>();

    /**
     *  Lista de imágenes del enemigo al morir
     */
    public ArrayList<Bitmap> imagenesEnemigoMuerte = new ArrayList<>();

    /**
     *  Lista de imágenes del enemigo al ser golpeado
     */
    public ArrayList<Bitmap> imagenesEnemigoHit = new ArrayList<>();

    /**
     * Indica que actualmente el enemigo acaba de ser impactado y se
     * están dibujando sus sprites
     */
    public Boolean efectoMuerte=false;

    /**
     * Ayuda a determinar que ya ha terminado la animación de muerte
     * del enemigo
     */
    private Boolean efectoMuerteFinalizado=false;

    /**
     * Comprueba que el enemigo no acaba de ser impactado por una bala
     */
    private boolean afectado;

    /**
     * Constructor de la clase Enemigo
     * @param x coordenada left x del enemigo
     * @param y coordenada top y del enemigo
     * @param tx tamaño en ancho del enemigo
     * @param ty tamaño en alto del enemigo
     * @param context contexto actual
     */
    public Enemigo(float x, float y, float tx, float ty, Context context) {
        //this.imagen = imagen;
        this.posicion = new PointF(x, y);
        this.tx = tx;
        this.ty = ty;
        apareceEnemigo = false;
        posicionDeAtaque = false;
        this.context = context;
        actualizaRec();
        afectado=false;
    }

    public boolean getAfectado() {
        return afectado;
    }

    public void setAfectado(boolean afectado) {
        this.afectado = afectado;
    }

    public Boolean getEfectoMuerteFinalizado() {
        return efectoMuerteFinalizado;
    }

    public void setEfectoMuerteFinalizado(Boolean efectoMuerteFinalizado) {
        this.efectoMuerteFinalizado = efectoMuerteFinalizado;
    }

    /**
     * Actualiza la posicion actual del enemigo
     */
    public void actualizaRec() {
        rect = new RectF(posicion.x, posicion.y, posicion.x + tx, posicion.y + ty);
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
        return Bitmap.createBitmap(imagen, 0, 0, imagen.getWidth(),
                imagen.getHeight(), matrix, false);
    }

    /**
     * Dibujar en pantalla los enemigos
     * @param canvas lienzo
     */
    public void dibujarSpriteEnemigo(Canvas canvas) {
        if (System.currentTimeMillis()-tframe>tickframe){
            if (efectoMuerte){
                numFrameMuerte++;
                if (numFrameMuerte >= imagenesEnemigoMuerte.size()) {
                    numFrameMuerte = 0;
                }
            }else{
                if (getAfectado()){
                    numFrameHit++;
                    if (numFrameHit >= imagenesEnemigoHit.size()) {
                        numFrameHit = 0;
                    }
                }else{
                    if (posicionDeAtaque){
                        numFrameAtaque++;
                        if (numFrameAtaque >= imagenesEnemigoAtacar.size()) {
                            numFrameAtaque = 0;
                        }
                    }else{
                        numFrame++;
                        if (numFrame >= imagenesEnemigoCaminar.size()) {
                            numFrame = 0;
                        }
                    }
                }
            }
            tframe=System.currentTimeMillis();
        }

        if (!efectoMuerte){
            if (getAfectado()){
                canvas.drawBitmap(imagenesEnemigoHit.get(numFrameHit), rect.left, rect.top, null);
                if (numFrameHit==imagenesEnemigoHit.size()-1){
                    afectado=false;
                }
            }else{
                if (posicionDeAtaque){
                    canvas.drawBitmap(imagenesEnemigoAtacar.get(numFrameAtaque), rect.left, rect.top, null);
                }else{
                    canvas.drawBitmap(imagenesEnemigoCaminar.get(numFrame), rect.left, rect.top, null);
                }
            }
        }else{
            canvas.drawBitmap(imagenesEnemigoMuerte.get(numFrameMuerte), rect.left, rect.top, null);
            if (numFrameMuerte==imagenesEnemigoMuerte.size()-1 && !getEfectoMuerteFinalizado()){
                efectoMuerte=false;
                setEfectoMuerteFinalizado(true);
            }
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
}
