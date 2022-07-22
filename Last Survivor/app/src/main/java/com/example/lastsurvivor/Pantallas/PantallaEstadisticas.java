package com.example.lastsurvivor.Pantallas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.media.AudioManager;
import android.text.TextPaint;
import android.view.MotionEvent;

import com.example.lastsurvivor.Constantes;
import com.example.lastsurvivor.Datos;

/**
 * Pantalla de estadísticas, aqui podremos ver los récords y puntuaciones que llevamos en le juego
 * @author Carlos Daniel Tabares Comesaña
 * @version 1.0 18/03/2022
 */
public class PantallaEstadisticas extends Pantalla {

    /**
     * Pincel de texto
     */
    TextPaint tpaint;

    /**
     * Representa una opreación
     */
    float xTexto=anchoPantalla / 6f;

    /**
     * Representa una operación
     */
    float yTexto=altoPantalla / 3.2f;

    /**
     * Constructor de la clase PantallaEstadísticas
     * @param numEscena representa el numero de escena actual
     * @param context representa el contexto
     * @param anchoPantalla ancho de la pantalla
     * @param altoPantalla alto de la pantalla
     */
    public PantallaEstadisticas(int numEscena, Context context, int anchoPantalla, int altoPantalla) {
        super(numEscena, context, anchoPantalla, altoPantalla);
        tpaint = new TextPaint();
        tpaint.setColor(Color.WHITE);
        tpaint.setShadowLayer(4f, 4f, 4f, Color.RED);
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

            tpaint.setTextSize(altoPantalla / 13f);
            canvas.drawText("Estadísticas", anchoPantalla / 2.6f, (altoPantalla / 12), tpaint);

            tpaint.setTextSize(altoPantalla / 16); // tamaño del texto en pixels 50
            canvas.drawText("Récord rondas sobrevividas..............................", xTexto, yTexto, tpaint);
            canvas.drawText(Datos.leeInt("recordRondasSobrevividas",context)+"", anchoPantalla / 1.3f, yTexto, tpaint);
            canvas.drawText("Total de rondas superadas................................", xTexto, yTexto * 1.35f, tpaint);
            canvas.drawText(Datos.leeInt("totalRondasSuperadas",context)+"", anchoPantalla / 1.3f, yTexto * 1.35f, tpaint);
            canvas.drawText("Número de partidas jugadas............................", xTexto, yTexto * 1.7f, tpaint);//
            canvas.drawText(Datos.leeInt("numeroPartidasJugadas",context)+"", anchoPantalla / 1.3f, yTexto* 1.7f, tpaint);
            canvas.drawText("Total enemigos abatidos......................................", xTexto, yTexto * 2.05f, tpaint);
            canvas.drawText(Datos.leeInt("totalEnemigosAbatidos",context)+"", anchoPantalla / 1.3f, yTexto* 2.05f, tpaint);
            canvas.drawText("Criaturas ordinarias abatidas...........................", xTexto, yTexto * 2.4f, tpaint);
            canvas.drawText(Datos.leeInt("criaturasOrdinariasAbatidas",context)+"", anchoPantalla / 1.3f, yTexto* 2.4f, tpaint);
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
            if (botonVolver.contains(x, y)) {
                if (Datos.leeBoolean("activarEfectosDeSonido",context)){
                    audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                }
                return Constantes.PMENU;
            }
        }
        return numEscena;
    }
}
