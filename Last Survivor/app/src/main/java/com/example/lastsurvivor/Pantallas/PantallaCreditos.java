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
import com.example.lastsurvivor.R;

/**
 * Pantalla créditos, aqui podremos ver las personas que participaron en el proyecto
 * @author Carlos Daniel Tabares Comesaña
 * @version 1.1 18/03/2022
 */
public class PantallaCreditos extends Pantalla{

    /**
     * Pincel de texto
     */
    TextPaint tpaint;

    /**
     * Representa mediante un switch lo que se va a dibujar en ese momento
     */
    int pantallaCreditos=1;

    /**
     * Pincel utilizado
     */
    Paint paint;

    /**
     * Botón para "pasar a la siguiente pantalla", aumenta en 1 pantallaCreditos
     */
    RectF next;

    /**
     * Botón para "volver a la pantalla anterior", resta en 1 pantallaCreditos
     */
    RectF back;

    /**
     * Representa una división
     */
    float anchoPantallaEntreDos;

    /**
     * Constructor de la clase PantallaCreditos
     * @param numEscena representa el numero de escena actual
     * @param context representa el contexto
     * @param anchoPantalla ancho de la pantalla
     * @param altoPantalla alto de la pantalla
     */
    public PantallaCreditos(int numEscena, Context context, int anchoPantalla, int altoPantalla) {
        super(numEscena, context, anchoPantalla, altoPantalla);
        tpaint = new TextPaint();
        tpaint.setColor(Color.WHITE);
        tpaint.setShadowLayer(4f, 4f, 4f, Color.RED);
        tpaint.setTextAlign(Paint.Align.CENTER);
        tpaint.setTypeface(fuente);
        paint= new Paint();
        paint.setColor(Color.GRAY);

        next= new RectF(anchoPantalla / 1.2f,altoPantalla/1.31f,anchoPantalla / 1.05f,(altoPantalla / 1.18f));
        back= new RectF(anchoPantalla-(anchoPantalla / 1.05f),altoPantalla/1.31f,anchoPantalla-(anchoPantalla / 1.05f)+next.width(),(altoPantalla / 1.18f));

        anchoPantallaEntreDos=anchoPantalla/2;
    }

    /**
     * Dibujar en pantalla
     * @param canvas lienzo
     */
    public void dibujar(Canvas canvas){
        if (canvas != null) {
            super.dibujar(canvas);
            super.dibujarVolver(canvas);
            tpaint.setTextSize(altoPantalla / 13);
            canvas.drawText("Créditos", anchoPantallaEntreDos, (altoPantalla / 12), tpaint);

            switch (pantallaCreditos){
                case 1:
                    tpaint.setTextSize(altoPantalla / 15);
                    canvas.drawText("Autor:", anchoPantallaEntreDos, (altoPantalla / 3.2f), tpaint);
                    canvas.drawText("Carlos Daniel Tabares Comesaña", anchoPantallaEntreDos, (altoPantalla / 2.4f), tpaint);
                    canvas.drawText("Colaboraciones:", anchoPantallaEntreDos, (altoPantalla / 1.55f), tpaint);
                    canvas.drawText("Javier Conde", anchoPantallaEntreDos, (altoPantalla / 1.35f), tpaint);

                    tpaint.setTextSize(altoPantalla / 12);
                    canvas.drawText("Next", next.centerX(), next.centerY()+(next.height()/3.5f), tpaint);
                    break;
                case 2:
                    tpaint.setTextSize(altoPantalla / 15);
                    canvas.drawText("Gráficas:", anchoPantallaEntreDos, (altoPantalla / 3.2f), tpaint);
                    canvas.drawText("Old Guardian by Eddie's Workshop", anchoPantallaEntreDos, (altoPantalla / 2.3f), tpaint);
                    canvas.drawText("Horror City Battlers - Archive Content by MalibuDarby", anchoPantalla / 2, (altoPantalla / 1.9f), tpaint);
                    canvas.drawText("Pixel Art Forest by edermunizz", anchoPantallaEntreDos, (altoPantalla / 1.6f), tpaint);
                    canvas.drawText("Gothicvania by ansimuz", anchoPantallaEntreDos, (altoPantalla / 1.39f), tpaint);


                    tpaint.setTextSize(altoPantalla / 12);
                    canvas.drawText("Next", next.centerX(), next.centerY()+(next.height()/3.5f), tpaint);
                    canvas.drawText("Back", back.centerX(), back.centerY()+(back.height()/3.5f), tpaint);
                    break;
                case 3:
                    tpaint.setTextSize(altoPantalla / 15);
                    canvas.drawText("Música:", anchoPantallaEntreDos, (altoPantalla / 3.2f), tpaint);
                    canvas.drawText("Ruined City Theme by Cleyton Kauffman", anchoPantallaEntreDos, (altoPantalla / 2.3f), tpaint);
                    canvas.drawText("Flags by Alexander Ehlers", anchoPantallaEntreDos, (altoPantalla / 1.9f), tpaint);

                    tpaint.setTextSize(altoPantalla / 12);
                    canvas.drawText("Next", next.centerX(), next.centerY()+(next.height()/3.5f), tpaint);
                    canvas.drawText("Back", back.centerX(), back.centerY()+(back.height()/3.5f), tpaint);
                    break;
                case 4:
                    tpaint.setTextSize(altoPantalla / 15);
                    canvas.drawText("Efectos de sonido:", anchoPantallaEntreDos, (altoPantalla / 3.2f), tpaint);
                    canvas.drawText("Angry Beast by husky70", anchoPantallaEntreDos, (altoPantalla / 2.3f), tpaint);
                    canvas.drawText("Zombie Attack by soykevin", anchoPantallaEntreDos, (altoPantalla / 1.9f), tpaint);
                    canvas.drawText("Shot sound by Fesliyan", anchoPantallaEntreDos, (altoPantalla / 1.62f), tpaint);
                    canvas.drawText("KBoom Sound byTom McCann & Stephen", anchoPantallaEntreDos, (altoPantalla / 1.41f), tpaint);

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
            if (next.contains(x,y) && pantallaCreditos<4){
                if (Datos.leeBoolean("activarEfectosDeSonido",context)){
                    audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                }
                pantallaCreditos++;
            }
            if (back.contains(x,y) && pantallaCreditos>1){
                if (Datos.leeBoolean("activarEfectosDeSonido",context)){
                    audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                }
                pantallaCreditos--;
            }
        }
        return numEscena;
    }
}
