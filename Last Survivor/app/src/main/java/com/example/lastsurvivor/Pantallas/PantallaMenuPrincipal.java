package com.example.lastsurvivor.Pantallas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.MotionEvent;

import com.example.lastsurvivor.Constantes;
import com.example.lastsurvivor.Datos;
import com.example.lastsurvivor.Juego;
import com.example.lastsurvivor.MainActivity;
import com.example.lastsurvivor.R;

/**
 * Pantalla menú principal, será la pantalla principal del juego donde tendremos acceso a las demás
 * @author Carlos Daniel Tabares Comesaña
 * @version 1.0 18/03/2022
 */
public class PantallaMenuPrincipal extends Pantalla {

    /**
     * Pincel utilizado
     */
    Paint paint;

    /**
     * Botón para iniciar el juego
     */
    RectF jugar;

    /**
     * Botón para ir a la pantalla de estadísticas
     */
    RectF estadisticas;

    /**
     * Botón para ir a la pantalla de ajustes
     */
    RectF ajustes;

    /**
     * Botón para ir a la pantalla de créditos
     */
    RectF creditos;

    /**
     * Botón para ir a la pantalla de ayuda
     */
    RectF ayuda;

    /**
     * Pincel de texto
     */
    TextPaint tpaint;

    /**
     * Constructor de la clase PantallaMenuPrincipal
     * @param numEscena representa el numero de escena actual
     * @param context representa el contexto
     * @param anchoPantalla ancho de la pantalla
     * @param altoPantalla alto de la pantalla
     */
    public PantallaMenuPrincipal(int numEscena, Context context, int anchoPantalla, int altoPantalla) {
        super(numEscena, context, anchoPantalla, altoPantalla);
        if (Datos.leeBoolean("activarMusica",context)){
            Juego.mediaPlayer.start();
        }

        jugar = new RectF(anchoPantalla / 6, (altoPantalla / 8) * 1, anchoPantalla - (anchoPantalla / 6), (altoPantalla / 4) * 1);
        estadisticas = new RectF(anchoPantalla / 6, (altoPantalla / 8) * 2.2f, anchoPantalla - (anchoPantalla / 6), (altoPantalla / 4) * 1.6f);
        ajustes = new RectF(anchoPantalla / 6, (altoPantalla / 8) * 3.4f, anchoPantalla - (anchoPantalla / 6), (altoPantalla / 4) * 2.2f);
        creditos = new RectF(anchoPantalla / 6, (altoPantalla / 8) * 4.6f, anchoPantalla - (anchoPantalla / 6), (altoPantalla / 4) * 2.8f);
        ayuda = new RectF(anchoPantalla / 6, (altoPantalla / 8) * 5.8f, anchoPantalla - (anchoPantalla / 6), (altoPantalla / 4) * 3.4f);

        paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setAlpha(150);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        tpaint = new TextPaint();
        tpaint.setTextSize(altoPantalla/16);
        tpaint.setColor(Color.WHITE);
        tpaint.setShadowLayer(4f, 4f, 4f, Color.RED);
        tpaint.setTextAlign(Paint.Align.CENTER);

        tpaint.setTypeface(fuente);
    }

    /**
     * Dibujar en pantalla
     * @param canvas lienzo
     */
    public void dibujar(Canvas canvas) {
        if (canvas != null) {
            super.dibujar(canvas);

            canvas.drawRect(jugar, paint);
            canvas.drawRect(estadisticas, paint);
            canvas.drawRect(ajustes, paint);
            canvas.drawRect(creditos, paint);
            canvas.drawRect(ayuda, paint);

            canvas.drawText("Jugar", anchoPantalla / 2f, ((altoPantalla / 4) * 1)-(altoPantalla / 28), tpaint);
            canvas.drawText("Estadísticas", anchoPantalla / 2f, ((altoPantalla / 4) * 1.6f)-(altoPantalla / 28), tpaint);
            canvas.drawText("Ajustes", anchoPantalla / 2f, ((altoPantalla / 4) * 2.2f)-(altoPantalla / 28), tpaint);
            canvas.drawText("Créditos", anchoPantalla / 2f, ((altoPantalla / 4) * 2.8f)-(altoPantalla / 28), tpaint);
            canvas.drawText("Ayuda", anchoPantalla / 2f, ((altoPantalla / 4) * 3.4f)-(altoPantalla / 28), tpaint);

        }
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
            if (jugar.contains(x, y)) {
                if (Datos.leeBoolean("activarEfectosDeSonido",context)){
                    audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                }
                Juego.mediaPlayer.stop();
                return Constantes.PJUEGO;
            } else if (estadisticas.contains(x, y)) {
                if (Datos.leeBoolean("activarEfectosDeSonido",context)){
                    audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                }
                return Constantes.PESTADISTICAS;
            } else if (ajustes.contains(x, y)) {
                if (Datos.leeBoolean("activarEfectosDeSonido",context)){
                    audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                }
                return Constantes.PAJUSTES;
            } else if (creditos.contains(x, y)) {
                if (Datos.leeBoolean("activarEfectosDeSonido",context)){
                    audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                }
                return Constantes.PCREDITOS;
            } else if (ayuda.contains(x,y)){
                if (Datos.leeBoolean("activarEfectosDeSonido",context)){
                    audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                }
                return Constantes.PAYUDA;
            }
        }

        return numEscena;
    }
}
