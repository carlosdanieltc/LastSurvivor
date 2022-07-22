package com.example.lastsurvivor.Pantallas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.AudioManager;
import android.text.TextPaint;
import android.view.MotionEvent;

import com.example.lastsurvivor.Constantes;
import com.example.lastsurvivor.Datos;

/**
 * Pantalla borrar historial, pantalla de confirmación antes de borrar el historial del juego
 * @author Carlos Daniel Tabares Comesaña
 * @version 1.0 18/03/2022
 */
public class PantallaBorrarHistorial extends Pantalla{

    /**
     * Pincel del texto
     */
    TextPaint tpaint;

    /**
     * Pincel utilizado
     */
    Paint paint;

    /**
     * Botones para aceptar o no el borrado de historial
     */
    RectF si,no;

    /**
     * Constructor de la clase PantallaBorrarHsitorial
     * @param numEscena representa el numero de escena actual
     * @param context representa el contexto
     * @param anchoPantalla ancho de la pantalla
     * @param altoPantalla alto de la pantalla
     */
    public PantallaBorrarHistorial(int numEscena, Context context, int anchoPantalla, int altoPantalla) {
        super(numEscena, context, anchoPantalla, altoPantalla);

        paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setAlpha(200);
        paint.setAntiAlias(true);

        tpaint = new TextPaint();
        tpaint.setColor(Color.WHITE); // Color del texto
        tpaint.setTypeface(fuente);

        tpaint.setShadowLayer(4f, 4f, 4f, Color.RED); // Sombra del texto
        si=new RectF(anchoPantalla/4,altoPantalla/2,anchoPantalla/2.5f,altoPantalla/1.5f);
        no=new RectF((anchoPantalla-(anchoPantalla/4))-si.width(),altoPantalla/2,anchoPantalla-(anchoPantalla/4),altoPantalla/1.5f);

    }

    /**
     * Dibujar en pantalla
     * @param canvas lienzo
     */
    public void dibujar(Canvas canvas){
        if (canvas != null) {
            super.dibujar(canvas);

            tpaint.setTextSize(altoPantalla / 13);
            tpaint.setTextAlign(Paint.Align.CENTER);
            //canvas.drawText("Ajustes", anchoPantalla / 2, (altoPantalla / 12), tpaint);
            tpaint.setTextSize(altoPantalla / 12);
            canvas.drawText("¿Seguro que deseas borrar el historial?", anchoPantalla / 2, (altoPantalla / 3), tpaint);
            tpaint.setTextSize(altoPantalla / 8);
            canvas.drawText("SI",si.centerX(),si.centerY()+si.height()/4,tpaint);
            canvas.drawText("NO",no.centerX(),no.centerY()+si.height()/4,tpaint);
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
            if (si.contains(x, y)) {
                if (Datos.leeBoolean("activarEfectosDeSonido",context)){
                    audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                }
                Datos.guarda("recordRondasSobrevividas",0,context);
                Datos.guarda("totalRondasSuperadas",0,context);
                Datos.guarda("totalEnemigosAbatidos",0,context);
                Datos.guarda("criaturasOrdinariasAbatidas",0,context);
                Datos.guarda("numeroPartidasJugadas",0,context);
                return Constantes.PAJUSTES;
            }
            if (no.contains(x, y)){
                if (Datos.leeBoolean("activarEfectosDeSonido",context)){
                    audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                }
                return Constantes.PAJUSTES;
            }
        }
        return numEscena;
    }
}
