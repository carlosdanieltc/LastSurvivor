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
 * Pantalla de ayuda, aqui podremos ver tanto una breve historia del juego además de como jugar
 * @author Carlos Daniel Tabares Comesaña
 * @version 1.0 18/03/2022
 */
public class PantallaAyuda extends Pantalla{

    /**
     * Pincel para el texto usado
     */
    TextPaint tpaint;

    /**
     * Representa medidante un switch lo que se va a dibujar en ese momento
     */
    int pantallaAyuda=1;

    /**
     * Pincel utilizado
     */
    Paint paint;

    /**
     * Botón para "pasar a la siguiente pantalla", aumenta en 1 pantallaAyuda
     */
    RectF next;

    /**
     * Botón para "volver a la pantalla anterior", resta en 1 pantallaAyuda
     */
    RectF back;

    /**
     * Constructor de la clase PantallaAyuda
     * @param numEscena representa la escena actual
     * @param context contexto actual
     * @param anchoPantalla ancho de la pantalla
     * @param altoPantalla alto de la pantalla
     */
    public PantallaAyuda(int numEscena, Context context, int anchoPantalla, int altoPantalla) {
        super(numEscena, context, anchoPantalla, altoPantalla);
        tpaint = new TextPaint();

        tpaint.setTypeface(fuente);
        tpaint.setColor(Color.WHITE);
        tpaint.setShadowLayer(4f, 4f, 4f, Color.RED);

        paint= new Paint();
        paint.setColor(Color.GRAY);

        next= new RectF(anchoPantalla / 1.3f,altoPantalla/1.31f,anchoPantalla / 1.05f,(altoPantalla / 1.16f));
        back= new RectF(anchoPantalla-(anchoPantalla / 1.05f),altoPantalla/1.31f,anchoPantalla-(anchoPantalla / 1.05f)+next.width(),(altoPantalla / 1.18f));
    }

    /**
     * Dibujar en pantalla
     * @param canvas lienzo
     */
    public void dibujar(Canvas canvas){
        if (canvas != null) {
            super.dibujar(canvas);
            super.dibujarVolver(canvas);
            tpaint.setTextAlign(Paint.Align.CENTER);
            tpaint.setTextSize(altoPantalla / 13);
            canvas.drawText("Ayuda", anchoPantalla / 1.13f, (altoPantalla / 12), tpaint);
            tpaint.setTextSize(altoPantalla / 8);
            canvas.drawText("Last Survivor", anchoPantalla / 2f, (altoPantalla / 7), tpaint);

            switch (pantallaAyuda){
                case 1:
                    tpaint.setTextAlign(Paint.Align.LEFT);
                    tpaint.setTextSize(altoPantalla / 15);
                    canvas.drawText("Diciembre, año 2022, el planeta tierra ya no es como lo", anchoPantalla / 18f, (altoPantalla / 3.5f)*1.32f, tpaint);
                    canvas.drawText("conocíamos hace tan solo unos meses, nos hemos visto", anchoPantalla / 18f, (altoPantalla / 3.5f)*1.62f, tpaint);
                    canvas.drawText("invadidos por una plaga de seres de otro mundo (O eso", anchoPantalla / 18f, (altoPantalla / 3.5f)*1.92f, tpaint);
                    canvas.drawText("creemos), unos seres de los que aún no concocemos nada.", anchoPantalla / 18f, (altoPantalla / 3.5f)*2.22f, tpaint);

                    next= new RectF(anchoPantalla / 1.3f,altoPantalla/1.31f,anchoPantalla / 1.05f,(altoPantalla / 1.16f));
                    tpaint.setTextSize(altoPantalla / 12);
                    canvas.drawText("Controles", next.left, next.centerY()+(next.height()/3.5f), tpaint);
                    break;
                case 2:
                    tpaint.setTextSize(altoPantalla / 15);
                    tpaint.setTextAlign(Paint.Align.LEFT);
                    canvas.drawText("Last Survivor es un juego de defensa por oleadas, el", anchoPantalla / 18f, (altoPantalla / 3.5f)*1.22f, tpaint);
                    canvas.drawText("único objetivo del mismo es sobrevivir a las oleadas", anchoPantalla / 18f, (altoPantalla / 3.5f)*1.52f, tpaint);
                    canvas.drawText("de enemigos que irán llegando poco a poco de ambos", anchoPantalla / 18f, (altoPantalla / 3.5f)*1.82f, tpaint);
                    canvas.drawText("extremos, aumentando cada vez mas su cantidad y su", anchoPantalla / 18f, (altoPantalla / 3.5f)*2.12f, tpaint);
                    canvas.drawText("resistencia.", anchoPantalla / 18f, (altoPantalla / 3.5f)*2.42f, tpaint);

                    tpaint.setTextAlign(Paint.Align.CENTER);
                    next= new RectF(anchoPantalla / 1.2f,altoPantalla/1.31f,anchoPantalla / 1.05f,(altoPantalla / 1.18f));
                    tpaint.setTextSize(altoPantalla / 12);
                    canvas.drawText("Next", next.centerX(), next.centerY()+(next.height()/3.5f), tpaint);
                    canvas.drawText("Back", back.centerX(), back.centerY()+(back.height()/3.5f), tpaint);
                    break;
                case 3:
                    tpaint.setTextSize(altoPantalla / 15);
                    tpaint.setTextAlign(Paint.Align.LEFT);
                    canvas.drawText(" - Toca la pantalla dependiendo del lado al que", anchoPantalla / 18f, (altoPantalla / 3.5f)*1.22f, tpaint);
                    canvas.drawText("quieras disparar.", anchoPantalla / 18f, (altoPantalla / 3.5f)*1.52f, tpaint);
                    canvas.drawText("- Cada 5 rondas obtendrás un KBoom, toca en su", anchoPantalla / 18f, (altoPantalla / 3.5f)*1.82f, tpaint);
                    canvas.drawText("icono para activarlo o agita el dispositivo!", anchoPantalla / 18f, (altoPantalla / 3.5f)*2.12f, tpaint);
                    canvas.drawText(" - ¡SOBREVIVE!", anchoPantalla / 18f, (altoPantalla / 3.5f)*2.42f, tpaint);


                    tpaint.setTextAlign(Paint.Align.CENTER);
                    tpaint.setTextSize(altoPantalla / 12);
                    canvas.drawText("Back", back.centerX(), back.centerY()+(back.height()/3.5f), tpaint);
                    break;
            }
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
        if (event.getAction()==MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (botonVolver.contains(x,y)){
                if (Datos.leeBoolean("activarEfectosDeSonido",context)){
                    audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                }
                return Constantes.PMENU;
            }
            if (next.contains(x,y) && pantallaAyuda<3){
                if (Datos.leeBoolean("activarEfectosDeSonido",context)){
                    audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                }
                pantallaAyuda++;
            }
            if (back.contains(x,y) && pantallaAyuda>1){
                if (Datos.leeBoolean("activarEfectosDeSonido",context)){
                    audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                }
                pantallaAyuda--;
            }
        }
        return numEscena;
    }
}
