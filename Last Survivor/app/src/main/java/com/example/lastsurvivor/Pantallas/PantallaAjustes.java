package com.example.lastsurvivor.Pantallas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.AudioManager;
import android.text.TextPaint;
import android.view.MotionEvent;

import com.example.lastsurvivor.Constantes;
import com.example.lastsurvivor.Datos;
import com.example.lastsurvivor.Juego;
import com.example.lastsurvivor.R;

/**
 * Pantalla ajustes, aqui se verán las posibles opciones de configuracián.
 * @author Carlos Daniel Tabares Comesaña
 * @version 1.0 18/03/2022
 */
public class PantallaAjustes extends Pantalla {

    /**
     * Pincel de texto
     */
    TextPaint tpaint;

    /**
     * Pincel utilizado
     */
    Paint paint;

    /**
     * Botón para activar/desactivar la música
     */
    RectF activarMusica;

    /**
     * Texto mostrado en el recuadro para activar/desactivar la música
     */
    String stringMusica;

    /**
     * Botón para activar/desactivar los efectos de sonido
     */
    RectF activarEfectos;

    /**
     * Texto mostrado en el recuadro para activar/desactivar los efectos de sonido
     */
    String stringEfectos;

    /**
     * Botón para activar/desactivar los efectos vibración
     */
    RectF activarVibracion;

    /**
     * Texto mostrado en el recuadro para activar/desactivar los efectos de vibración
     */
    String stringVibracion;

    /**
     * Botón para cambiar el idioma del juego
     */
    RectF cambiarIdioma;

    /**
     * Texto mostrado en el recuadro de cambiar idioma del juego
     */
    String stringCambiarIdioma;

    /**
     * Botón para borrar el historial
     */
    RectF borrarHistorial;

    /**
     * Constructor de la clase PantallaAjustes
     * @param numEscena representa el numero de escena actual
     * @param context representa el contexto
     * @param anchoPantalla ancho de la pantalla
     * @param altoPantalla alto de la pantalla
     */
    public PantallaAjustes(int numEscena, Context context, int anchoPantalla, int altoPantalla) {
        super(numEscena, context, anchoPantalla, altoPantalla);
        paint = new Paint();

        //Persistencia de datos
        if (Datos.leeBoolean("activarMusica", context)) {
            Datos.guarda("activarMusicaCad", "ON", context);
        }
        if (Datos.leeBoolean("activarEfectosDeSonido", context)) {
            Datos.guarda("activarEfectosDeSonidoCad", "ON", context);
        }
        if (Datos.leeBoolean("activarVibracion", context)) {
            Datos.guarda("activarVibracionCad", "ON", context);
        }

        stringMusica = Datos.leeString("activarMusicaCad", context);
        stringEfectos = Datos.leeString("activarEfectosDeSonidoCad", context);
        stringVibracion = Datos.leeString("activarVibracionCad", context);
        stringCambiarIdioma = Datos.leeStringIdioma("idioma", context);
        //Hasta aqui persistencia de datos

        activarMusica = new RectF(anchoPantalla - (anchoPantalla / 4.5f), (altoPantalla / 4.5f) * 1.2f, anchoPantalla - (anchoPantalla / 7), (altoPantalla / 3) * 1.05f);
        activarEfectos = new RectF(anchoPantalla - (anchoPantalla / 4.5f), (altoPantalla / 4.5f) * 1.70f, anchoPantalla - (anchoPantalla / 7), (altoPantalla / 3) * 1.38f);
        activarVibracion = new RectF(anchoPantalla - (anchoPantalla / 4.5f), (altoPantalla / 4.5f) * 2.2f, anchoPantalla - (anchoPantalla / 7), (altoPantalla / 3) * 1.71f);
        cambiarIdioma = new RectF(anchoPantalla - (anchoPantalla / 4.5f), (altoPantalla / 4.5f) * 2.7f, anchoPantalla - (anchoPantalla / 7), (altoPantalla / 3) * 2.04f);
        borrarHistorial = new RectF(anchoPantalla - (anchoPantalla / 4.5f), (altoPantalla / 4.5f) * 3.2f, anchoPantalla - (anchoPantalla / 7), (altoPantalla / 3) * 2.37f);

        tpaint = new TextPaint();
        tpaint.setColor(Color.WHITE);
        tpaint.setShadowLayer(4f, 4f, 4f, Color.RED);

        paint.setColor(Color.GRAY);
        paint.setAlpha(200);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        tpaint.setTypeface(fuente);
    }

    /**
     * Dibujar en pantalla
     * @param canvas lienzo
     */
    public void dibujar(Canvas canvas) {
        if (canvas != null) {
            super.dibujar(canvas);

            dibujarVolver(canvas);
            tpaint.setTextSize(altoPantalla / 13);

            canvas.drawText("Ajustes", anchoPantalla / 2.4f, (altoPantalla / 12), tpaint);

            tpaint.setTextSize(altoPantalla / 16);
            canvas.drawText("Activar/Desactivar música del juego", anchoPantalla / 9, (altoPantalla / 4.5f) * 1.5f, tpaint);
            canvas.drawText("Activar/Desactivar efectos de sonido", anchoPantalla / 9, (altoPantalla / 4.5f) * 2f, tpaint);
            canvas.drawText("Activar/Desactivar opciones de vibración", anchoPantalla / 9, (altoPantalla / 4.5f) * 2.5f, tpaint);
            canvas.drawText("Cambiar idioma", anchoPantalla / 9, (altoPantalla / 4.5f) * 3f, tpaint);
            canvas.drawText("Borrar historial de estadísticas", anchoPantalla / 9, (altoPantalla / 4.5f) * 3.5f, tpaint);

            botonAjustes(canvas);
        }
    }

    /**
     * Metodo para dibujar los botones de las opciones
     * @param canvas lienzo
     */
    public void botonAjustes(Canvas canvas) {
        canvas.drawRect(activarMusica, paint);
        canvas.drawRect(activarEfectos, paint);
        canvas.drawRect(activarVibracion, paint);
        canvas.drawRect(cambiarIdioma, paint);
        canvas.drawRect(borrarHistorial, paint);

        tpaint.setTextSize(altoPantalla / 18);
        canvas.drawText(stringMusica, anchoPantalla - (anchoPantalla / 4.8f), (altoPantalla / 3.05f), tpaint);
        canvas.drawText(stringEfectos, anchoPantalla - (anchoPantalla / 4.8f), (altoPantalla / 3) * 1.31f, tpaint);
        canvas.drawText(stringVibracion, anchoPantalla - (anchoPantalla / 4.8f), (altoPantalla / 3) * 1.65f, tpaint);
        canvas.drawText(stringCambiarIdioma, anchoPantalla - (anchoPantalla / 4.72f), (altoPantalla / 3) * 1.98f, tpaint);
        canvas.drawText("DEL", anchoPantalla - (anchoPantalla / 4.8f), (altoPantalla / 3) * 2.31f, tpaint);
    }

    /**
     * Actualiza la física de los elementos en pantalla
     */
    public void actualizarFisica() {

    }

    /**
     * Gestionar acciones sobre la pantalla
     * @param event evento que se produce
     * @return numero escena a la que se cambia
     */
    public int onTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (botonVolver.contains(x, y)) {
                if (Datos.leeBoolean("activarEfectosDeSonido", context)) {
                    audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                }
                return Constantes.PMENU;
            }
            if (activarMusica.contains(x, y)) {
                if (Datos.leeBoolean("activarEfectosDeSonido", context)) {
                    audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                }
                if (Datos.leeBoolean("activarMusica", context)) {
                    Datos.guarda("activarMusicaCad", "OFF", context);
                    stringMusica = Datos.leeString("activarMusicaCad", context);
                    Datos.guarda("activarMusica", false, context);
                    Juego.mediaPlayer.pause();
                } else {
                    Datos.guarda("activarMusicaCad", "ON", context);
                    stringMusica = Datos.leeString("activarMusicaCad", context);
                    Datos.guarda("activarMusica", true, context);
                    Juego.mediaPlayer.start();
                }
            }//Hasta aqui musica
            if (activarEfectos.contains(x, y)) {
                audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                if (Datos.leeBoolean("activarEfectosDeSonido", context)) {
                    Datos.guarda("activarEfectosDeSonidoCad", "OFF", context);
                    stringEfectos = Datos.leeString("activarEfectosDeSonidoCad", context);
                    Datos.guarda("activarEfectosDeSonido", false, context);
                } else {
                    Datos.guarda("activarEfectosDeSonidoCad", "ON", context);
                    stringEfectos = Datos.leeString("activarEfectosDeSonidoCad", context);
                    Datos.guarda("activarEfectosDeSonido", true, context);
                }
            }//Hasta aqui efectos
            if (activarVibracion.contains(x, y)) {
                if (Datos.leeBoolean("activarEfectosDeSonido", context)) {
                    audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                }
                if (Datos.leeBoolean("activarVibracion", context)) {
                    Datos.guarda("activarVibracionCad", "OFF", context);
                    stringVibracion = Datos.leeString("activarVibracionCad", context);
                    Datos.guarda("activarVibracion", false, context);
                } else {
                    Datos.guarda("activarVibracionCad", "ON", context);
                    stringVibracion = Datos.leeString("activarVibracionCad", context);
                    Datos.guarda("activarVibracion", true, context);
                }
            }//Hasta aqui vibracion
            if (cambiarIdioma.contains(x, y)) {
                if (Datos.leeBoolean("activarEfectosDeSonido", context)) {
                    audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                }
                if (Datos.leeStringIdioma("idioma", context).equals("ESP")) {
                    Datos.guarda("idioma", "ENG", context);
                    stringCambiarIdioma = "ENG";
                } else {
                    Datos.guarda("idioma", "ESP", context);
                    stringCambiarIdioma = "ESP";
                }
            }//Hasta aqui cambiar idioma
            if (borrarHistorial.contains(x, y)) {
                if (Datos.leeBoolean("activarEfectosDeSonido", context)) {
                    audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                }
                return Constantes.PBORRARH;
            }
        }
        return numEscena;
    }
}
