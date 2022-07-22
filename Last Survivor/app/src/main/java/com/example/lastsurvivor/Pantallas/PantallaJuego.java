package com.example.lastsurvivor.Pantallas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.VibrationEffect;
import android.text.TextPaint;
import android.util.Log;
import android.view.MotionEvent;

import com.example.lastsurvivor.Constantes;
//import com.example.lastsurvivor.Personajes.Armas.Pistola;
//import com.example.lastsurvivor.Personajes.Enemigos.CriaturaExplosiva;
import com.example.lastsurvivor.Datos;
import com.example.lastsurvivor.Juego;
import com.example.lastsurvivor.MainActivity;
import com.example.lastsurvivor.Personajes.Armas.Arma;
import com.example.lastsurvivor.Personajes.Enemigos.CriaturaOrdinaria;
//import com.example.lastsurvivor.Personajes.Enemigos.CriaturaOrdinariaConDefensa;
import com.example.lastsurvivor.Personajes.Enemigos.Enemigo;
//import com.example.lastsurvivor.Personajes.Enemigos.Jefe;
//import com.example.lastsurvivor.Personajes.Enemigos.Lanzador;
//import com.example.lastsurvivor.Personajes.Enemigos.Volador;
import com.example.lastsurvivor.Personajes.Heroe;
import com.example.lastsurvivor.R;

import java.util.ArrayList;

/**
 * Pantalla de juego, pantalla que dispone del juego como tal
 * @author Carlos Daniel Tabares Comesaña
 * @version 1.0 18/03/2022
 */
public class PantallaJuego extends Pantalla {

    //PRINCIPAL
    /**
     * Guardan las coordenadas x e y al pulsar sobre la pantalla
     * por lo que su valor se asigna en el onTouch
     */
    int x, y;

    /**
     * Nuestro personaje principal con el cual jugaremos
     */
    Heroe heroe = new Heroe(context);

    /**
     * Lista de los enemigos que conforme pasen las rondas irá
     * aumentando su tamaño
     */
    ArrayList<Enemigo> enemigosLista = new ArrayList<>();

    /**
     * Lista de las balas disparadas
     */
    ArrayList<Arma> armaLista = new ArrayList<>();

    /**
     * Enemigo básico principal, se utiliza para el uso
     * de polimorfismo
     */
    CriaturaOrdinaria criaturaOrdinaria;

    /**
     * Pincel del texto
     */
    TextPaint textPaint;

    /**
     * Pincel que utilizaremos
     */
    Paint paint;
    //

    //JUGABILIDAD
    /**
     * Equivale al tamaño de la horda de enemigos que
     * vendrá en cada ronda
     */
    int tamañoHordaEnemigos = 8;

    /**
     * Si esta a true indica que la ronda ha inciado
     */
    boolean inicioRonda = true;

    /**
     * Número de ronda actual
     */
    int ronda = 1;

    /**
     * Se utiliza para la confirmación al morir y que
     * no se produzcan guardado de datos repetidamente.
     * La gestionamos en el método dibujar
     */
    boolean muerto = false;

    /**
     * Determina mediante un número random de qué
     * lado de la pantalla vendrán los enemigos
     */
    int posicionDeEnemigos;

    /**
     * Representa al número random usado para posicionDeEnemigos
     */
    int apareceEnemigo;

    /**
     * Vida extra que se le añade a los enemigos cada n de rondas
     */
    int vidaExtra = 0;
    //

    //ESTADISTICAS DE PARTIDA
    /**
     * Total de enemigos abatidos en toda la partida
     */
    int totalEnemigosAbatidos = 0;

    /**
     * Total de criaturas ordinarias abatidos en toda la partida
     */
    int criaturasOrdinariasAbatidas = 0;
    //

    //HARDWARE
    /**
     * Determina si la vibración esta o no activada
     */
    boolean vibracionActivada;
    //

    //TIEMPO
    /**
     * Tiempo de descanso entre rondas
     */
    int tickRonda = 4000;

    /**
     * Tiempo desde que finaliza una ronda
     */
    long tiempo = System.currentTimeMillis();
    //

    //OPCIONES MUERTE
    /**
     * Botón para reintentar disponible cuando morimos
     */
    RectF reintentar;

    /**
     * Botón para salir disponible cuando morimos
     */
    RectF salir;
    //

    //AUDIO
    /**
     * Lista de efectos de sonido
     */
    private SoundPool efectos;

    /**
     * Distintos sonidos utilizados en el juego
     * sonidoDisparo equivale al sonido de las balas
     * sonidomounstro1 sonido 1 de mounstro
     * sonidomounstro2 sonido 2 de mounstro
     * kboomSound sonido explosión-activación del powerUp
     * kboomSound2 sonido desbloqueo del powerUp
     */
    private int sonidoDisparo, sonidomounstro1, sonidomounstro2, kboomSound, kboomSound2;

    /**
     * Maximo sonidos simultáneos
     */
    final private int maxSonidosSimultaneos = 20;

    /**
     * Nivel de volúmen
     */
    int v;

    /**
     * Gestiona volumen y efectos sonoros
     */
    public AudioManager audioManager;
    //

    //POWER UP
    /**
     * Botón para activar el powerUp KBoom
     */
    RectF powerUpKBoom;

    /**
     * Activa la posibilidad de usar el powerUp
     */
    public static boolean activarKBoom;

    /**
     * Controla que se ejecute correctamente la activación del powerUp
     */
    public static boolean comprobacionActivarKBoom;

    /**
     * Sonido para el momento en el que consigamos el powerUp
     */
    boolean kboomApareceSound;

    /**
     * Imagen para el powerUp
     */
    Bitmap imagenPowerUpKBoom;
    //

    /**
     * Constructor de la pantalla PantallaJuego
     * @param numEscena numero de escena actual
     * @param context contexto actual
     * @param anchoPantalla ancho de la pantalla
     * @param altoPantalla alto de la pantalla
     */
    public PantallaJuego(int numEscena, Context context, int anchoPantalla, int altoPantalla) {
        super(numEscena, context, anchoPantalla, altoPantalla);
        //IMAGEN
        bitmapFondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.fondonoche);
        bitmapFondo = Bitmap.createScaledBitmap(bitmapFondo, anchoPantalla, altoPantalla, false);
        //

        //PRINCIPAL
        paint = new Paint();
        paint.setColor(Color.GRAY);
        textPaint = new TextPaint();//TEXTO PARA ESCRIBIR LAS RONDAS
        textPaint.setTextSize(altoPantalla / 4.5f);
        textPaint.setColor(Color.RED);
        textPaint.setTypeface(fuente);
        //

        //JUGABILIDAD
        crearOrdaEnemigos();
        //

        //MUSICA Y EFECTOS DE SONIDO
        if (Datos.leeBoolean("activarMusica", context)) {
            Juego.mediaPlayer = MediaPlayer.create(context, R.raw.musicajuego);
            Juego.mediaPlayer.setLooping(true);
            Juego.mediaPlayer.start();
        }
        if ((android.os.Build.VERSION.SDK_INT) >= 21) {
            SoundPool.Builder spb = new SoundPool.Builder();
            spb.setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build());
            spb.setMaxStreams(maxSonidosSimultaneos);
            this.efectos = spb.build();
        } else {
            this.efectos = new SoundPool(maxSonidosSimultaneos, AudioManager.STREAM_MUSIC, 0);
        }
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        v = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        sonidoDisparo = efectos.load(context, R.raw.shoot2, 1);
        sonidomounstro1 = efectos.load(context, R.raw.mounstro1, 1);
        sonidomounstro2 = efectos.load(context, R.raw.mounstro2, 1);
        kboomSound = efectos.load(context, R.raw.kboom, 1);
        kboomSound2 = efectos.load(context, R.raw.kboomaparece, 1);
        //

        //POWER UP
        activarKBoom = false;
        comprobacionActivarKBoom = false;
        kboomApareceSound = true;
        powerUpKBoom = new RectF(anchoPantalla - (anchoPantalla / 13), altoPantalla / 15f, anchoPantalla - (anchoPantalla / 80), altoPantalla / 5.5f);
        imagenPowerUpKBoom = BitmapFactory.decodeResource(context.getResources(), R.drawable.powerup);
        imagenPowerUpKBoom = Bitmap.createScaledBitmap(imagenPowerUpKBoom, (int) powerUpKBoom.width(), (int) powerUpKBoom.height(), false);
        //

        //HARDWARE
        vibracionActivada= Datos.leeBoolean("activarVibracion",context);
        //

        //OTROS
        reintentar = new RectF(anchoPantalla / 2.6f, altoPantalla / 2f, anchoPantalla - (anchoPantalla / 2.6f), altoPantalla / 1.7f);
        salir = new RectF(anchoPantalla / 2.3f, altoPantalla / 1.68f, anchoPantalla - (anchoPantalla / 2.3f), altoPantalla / 1.42f);
        //
    }

    /**
     * Se encarga de crear las hordas de enemigos que vendrán para cada ronda
     * llenando asi la lista de enemigosLista
     */
    public void crearOrdaEnemigos() {//Crea la horda que vendra en esa ronda y la añade a la lista
        if (inicioRonda) {//tamañoHordaEnemigos
            for (int i = 0; i < tamañoHordaEnemigos; i++) {//Esto genera los enemigos de forma random de izquierda o derecha(pero todos a la vez)
                posicionDeEnemigos = (int) (Math.random() * 2 + 1);
                if (posicionDeEnemigos == 1) {//aparecen de la izquierda
                    enemigosLista.add(new CriaturaOrdinaria(0 - anchoPantalla / 8, context));
                } else {//aparecen de la derecha
                    enemigosLista.add(new CriaturaOrdinaria(anchoPantalla + (anchoPantalla / 8), context));
                }
            }
            inicioRonda = false;
        }
    }

    /**
     * Activa el powerUp KBoom
     */
    public void KBoomMetodo() {
        if (Datos.leeBoolean("activarEfectosDeSonido", context)) {
            efectos.play(kboomSound, v, v, 1, 0, 1);
        }
        for (int i = enemigosLista.size(); i > 0; i--) {
            if (enemigosLista.get(i - 1).apareceEnemigo) {
                enemigosLista.get(i - 1).vida.setVida(0);
                enemigosLista.get(i - 1).efectoMuerte = true;
            }
        }
        activarKBoom = false;
        comprobacionActivarKBoom = false;
    }

    /**
     * Confirma que la ronda ha terminado y da inicio a la siguiente,
     * aumenta también la dificultad del juego añadiendo enemigos y
     * aumentándoles la vida
     */
    public void comprobadorDeRondas() {
        inicioRonda = true;
        ronda++;
        tamañoHordaEnemigos += 2;
        tiempo = System.currentTimeMillis();
        crearOrdaEnemigos();
        if (ronda % 3 == 0) {
            vidaExtra += 30;
        }
        if (ronda % 5 == 0) {//5
            activarKBoom = true;
            kboomApareceSound=true;
        }
    }

    /**
     * Dibujar en pantalla los distintos elementos, además en ella se calcula el tiempo
     * de espera entre las rondas los distintos sprites usados y el sonido de los enemigos.
     * Tambien guarda los datos de la partida al culminar el juego.
     * @param canvas lienzo
     */
    public void dibujar(Canvas canvas) {
        if (canvas != null) {
            canvas.drawBitmap(bitmapFondo, 0, 0, null);
            heroe.dibujar(canvas);
            dibujarVolver(canvas);
            if (activarKBoom) {
                if (Datos.leeBoolean("activarEfectosDeSonido", context)) {
                    if (kboomApareceSound){
                        efectos.play(kboomSound2, v, v, 1, 0, 1);
                    }
                    kboomApareceSound = false;
                }
                canvas.drawBitmap(imagenPowerUpKBoom, powerUpKBoom.left, powerUpKBoom.top, null);
            }

            if (heroe.vida.getVida() > 0) {
                for (int i = 0; i < armaLista.size(); i++) {
                    armaLista.get(i).dibujar(canvas);
                }
            }
            if (System.currentTimeMillis() - tiempo < tickRonda) {
                textPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("Ronda " + ronda, anchoPantalla / 2f, altoPantalla / 3.2f, textPaint);
            } else {
                for (int i = 0; i < enemigosLista.size(); i++) {
                    criaturaOrdinaria = ((CriaturaOrdinaria) enemigosLista.get(i));
                    if (criaturaOrdinaria.apareceEnemigo) {

                        if (enemigosLista.get(i).getEfectoMuerteFinalizado()) {
                            if (enemigosLista.get(i).getClass() == CriaturaOrdinaria.class) {
                                criaturasOrdinariasAbatidas++;
                                totalEnemigosAbatidos++;
                            }
                            enemigosLista.remove(enemigosLista.get(i));
                        } else {
                            criaturaOrdinaria.dibujar(canvas);
                        }

                    } else {
                        apareceEnemigo = (int) (Math.random() * 170 + 1);
                        if (apareceEnemigo == 1) {

                            if (Datos.leeBoolean("activarEfectosDeSonido", context)) {
                                int s = (int) (Math.random() * 2 + 1);
                                if (s == 1) {
                                    efectos.play(sonidomounstro1, v, v, 1, 0, 1);
                                } else {
                                    efectos.play(sonidomounstro2, v, v, 1, 0, 1);
                                }
                            }

                            criaturaOrdinaria.apareceEnemigo = true;//indica que ya va a aparecer el enemigo
                            if (ronda % 3 == 0) {//cada 3 rondas aumenta la vida de los enemigos
                                criaturaOrdinaria.vida.setVida(criaturaOrdinaria.vida.getVida() + vidaExtra);
                            }

                            criaturaOrdinaria.dibujar(canvas);
                        }
                    }
                }
            }
            if (heroe.vida.getVida() <= 0) {
                if (!muerto) {
                    muerto = true;
                    if ((ronda - 1) > Datos.leeInt("recordRondasSobrevividas", context)) {
                        Datos.guarda("recordRondasSobrevividas", ronda - 1, context);
                    }
                    Datos.guarda("totalRondasSuperadas", Datos.leeInt("totalRondasSuperadas", context) + (ronda - 1), context);
                    Datos.guarda("numeroPartidasJugadas", Datos.leeInt("numeroPartidasJugadas", context) + 1, context);
                    Datos.guarda("totalEnemigosAbatidos", Datos.leeInt("totalEnemigosAbatidos", context) + totalEnemigosAbatidos, context);
                    Datos.guarda("criaturasOrdinariasAbatidas", Datos.leeInt("criaturasOrdinariasAbatidas", context) + criaturasOrdinariasAbatidas, context);
                }
                textPaint.setTextAlign(Paint.Align.CENTER);
                textPaint.setTextSize(altoPantalla / 6);
                textPaint.setTypeface(fuente);

                canvas.drawText("Has muerto", anchoPantalla / 2f, altoPantalla / 4, textPaint);
                textPaint.setTextSize(altoPantalla / 14);
                canvas.drawText("Rondas superadas: " + (ronda - 1), anchoPantalla / 2f, altoPantalla / 2.8f, textPaint);
                canvas.drawText("Enemigos abatidos: " + criaturasOrdinariasAbatidas, anchoPantalla / 2f, altoPantalla / 2.2f, textPaint);

                textPaint.setTextSize(altoPantalla / 12);
                canvas.drawText("Reintentar", reintentar.centerX(), reintentar.centerY() + (reintentar.height() / 3.5f), textPaint);
                canvas.drawText("Salir", salir.centerX(), salir.centerY() + (salir.height() / 4), textPaint);
            }
        }
    }

    /**
     * Se encarga actualizar la fisica de los distintos elementos que se muestran
     * en pantalla, en ella gestionamos las colisiones y detectamos si un enemigo
     * o nosotros hemos muerto.
     */
    public void actualizarFisica() {
        if (comprobacionActivarKBoom) {
            Log.i("sensor", "entra aqui");
            KBoomMetodo();
        }

        for (int i = 0; i < enemigosLista.size(); i++) {//bucle para mover los enemigos de la lista
            if (enemigosLista.get(i).getClass() == CriaturaOrdinaria.class) {
                criaturaOrdinaria = ((CriaturaOrdinaria) enemigosLista.get(i));
                if (criaturaOrdinaria.apareceEnemigo) {
                    if (criaturaOrdinaria.vida.getVida() >= 0 && !criaturaOrdinaria.getAfectado()) {
                        criaturaOrdinaria.moverEnemigo();
                        if (criaturaOrdinaria.posicionDeAtaque) {
                            if (System.currentTimeMillis() - criaturaOrdinaria.cargaAtaque > criaturaOrdinaria.tiempoDeAtaque) {
                                if (Datos.leeBoolean("activarEfectosDeSonido", context)){
                                    Juego.efectos.play(Juego.golpe1, v, v, 1, 0, 1);
                                    //Deja de sonar a partir del segundo golpe
                                }
                                if (vibracionActivada && heroe.vida.getVida()>0){
                                    if (MainActivity.vibrador.hasVibrator()) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//Vibracion cada vez que ataquen
                                            MainActivity.vibrador.vibrate(VibrationEffect.createOneShot(400, VibrationEffect.EFFECT_DOUBLE_CLICK));
                                        } else {
                                            MainActivity.vibrador.vibrate(400);
                                        }
                                    }
                                }
                                if (heroe.vida.getVida() > 0) {
                                    heroe.vida.setVida(heroe.vida.getVida() - criaturaOrdinaria.daño);//Si el heroe sigue vivo atacan
                                    heroe.vida.setDañoRecibido(criaturaOrdinaria.daño);//esto es una prueba para quitar vida
                                }
                                criaturaOrdinaria.posicionDeAtaque = false;
                            }
                        }
                    }
                }
            }
        }

        if (armaLista.size() > 0) {
            for (int i = 0; i < armaLista.size(); i++) {
                armaLista.get(i).mover();
                if (armaLista.get(i).posDisparo.equals("derecha")) {
                    if (armaLista.get(i).bala.left >= anchoPantalla) {
                        armaLista.remove(i);
                    }
                } else {
                    if (armaLista.get(i).bala.right <= 0) {
                        armaLista.remove(i);
                    }
                }
                if (armaLista.size() > 0) {
                    if (enemigosLista.size() > 0) {
                        for (int j = 0; j < enemigosLista.size(); j++) {
                            if (enemigosLista.get(j).apareceEnemigo) {
                                if ((armaLista.get(i).bala.intersect(enemigosLista.get(j).rect)) && enemigosLista.get(j).vida.getVida() > 0) {
                                    enemigosLista.get(j).setAfectado(true);
                                    enemigosLista.get(j).vida.setVida(enemigosLista.get(j).vida.getVida() - armaLista.get(i).daño);
                                    enemigosLista.get(j).vida.setDañoRecibido(armaLista.get(i).daño);
                                    if (enemigosLista.get(j).vida.getVida() <= 0) {
                                        //AQUI EMPIEZO A DIBUJAR LA MUERTE COLOCANDO efectoMuerte A TRUE
                                        enemigosLista.get(j).efectoMuerte = true;
                                    }
                                    armaLista.remove(i);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (enemigosLista.size() == 0) {
            comprobadorDeRondas();
        }
    }

    /**
     * Gestionar acciones sobre la pantalla, se determina tambien
     * hacia que lado se dispara y el sonido del arma
     * @param event evento que se produce
     * @return numero escena a la que se cambia
     */
    public int onTouch(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        this.x = x;
        this.y = y;

        if (heroe.vida.getVida() > 0) {

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (botonVolver.contains(x, y)) {
                    if (Datos.leeBoolean("activarEfectosDeSonido", context)) {
                        audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                    }
                    Juego.mediaPlayer.stop();
                    Juego.mediaPlayer = MediaPlayer.create(context, R.raw.musicamenu);//
                    Juego.mediaPlayer.setLooping(true);
                    return Constantes.PMENU;
                }
            }

            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                if ((!botonVolver.contains(x, y)) && (!powerUpKBoom.contains(x, y))) {
                    if (x >= anchoPantalla / 2) {
                        armaLista.add(new Arma("derecha"));
                    } else {
                        armaLista.add(new Arma("izquierda"));
                    }
                    if (x >= anchoPantalla / 2) {
                        heroe.setOrientacion(true);//derecha
                    } else {
                        heroe.setOrientacion(false);//izquierda
                    }
                    if (Datos.leeBoolean("activarEfectosDeSonido", context)) {
                        efectos.play(sonidoDisparo, v, v, 1, 0, 1);
                    }
                    heroe.disparando = true;
                }
                if (powerUpKBoom.contains(x, y)) {
                    if (activarKBoom) {
                        comprobacionActivarKBoom = true;
                    }
                }
            }
        } else {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (salir.contains(x, y)) {
                    Juego.mediaPlayer.stop();
                    Juego.mediaPlayer = MediaPlayer.create(context, R.raw.musicamenu);
                    Juego.mediaPlayer.setLooping(true);
                    return Constantes.PMENU;
                }
                if (reintentar.contains(x, y)) {
                    return 100;
                }
            }
        }
        return numEscena;
    }

}
