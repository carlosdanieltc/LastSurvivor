package com.example.lastsurvivor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.lastsurvivor.Pantallas.Pantalla;
import com.example.lastsurvivor.Pantallas.PantallaAjustes;
import com.example.lastsurvivor.Pantallas.PantallaAyuda;
import com.example.lastsurvivor.Pantallas.PantallaBorrarHistorial;
import com.example.lastsurvivor.Pantallas.PantallaCreditos;
import com.example.lastsurvivor.Pantallas.PantallaEstadisticas;
import com.example.lastsurvivor.Pantallas.PantallaJuego;
import com.example.lastsurvivor.Pantallas.PantallaMenuPrincipal;
import com.example.lastsurvivor.Personajes.Enemigos.CriaturaOrdinaria;
import com.example.lastsurvivor.Personajes.Enemigos.Enemigo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Juego, clase SurfaceView la cual gestiona el juego
 * @author Carlos Daniel Tabares Comesaña
 * @version 1.0 18/03/2022
 */
public class Juego extends SurfaceView implements SurfaceHolder.Callback {
    /**
     * Interfaz abstracta para manejar la superficie de dibujado
     */
    private SurfaceHolder surfaceHolder;

    /**
     * Contexto de la aplicación
     */
    private Context context;

    /**
     * Imagen de fondo
     */
    private Bitmap bitmapFondo;

    /**
     * Ancho de la pantalla, su valor se actualiza en el método surfaceChanged
     */
    public static int anchoPantalla = 1;

    /**
     * Alto de la pantalla, su valor se actualiza en el método surfaceChanged
     */
    public static int altoPantalla = 1;

    /**
     * Hilo encargado de dibujar y actualizar la física
     */
    private Hilo hilo;

    /**
     * Control del hilo
     */
    private boolean funcionando = false;

    /**
     * Pantalla actual
     */
    Pantalla pantalla;

    /**
     * Audio y efectos de sonido del juego
     */
    public AudioManager audioManager;

    /**
     * Para hacer sonar música de fondo
     */
    public static MediaPlayer mediaPlayer;

    /**
     * Lista con efectos de sonido
     */
    static public SoundPool efectos;

    /**
     * Máximo de efectos simultáneos
     */
    private int maxSonidosSimultaneos=20;

    /**
     * Volumen
     */
    static public int v;

    /**
     * Efectos de sonido
     * sonidoOpciones sonido al pulsar en las distintas opciones
     * ataquecriaturao ataque de los enemigos
     * golpe1 sonido al golpearnos los enemigos
     */
    static public int sonidoOpciones,ataquecriaturao,golpe1;

    /**
     * Constructor de la clase Juego
     * @param context contexto de ka aplicación
     */
    public Juego(Context context) {
        super(context);
        this.surfaceHolder = getHolder(); // Se obtiene el holder
        this.surfaceHolder.addCallback(this); // y se indica donde van las funciones callback
        this.context = context; // Obtenemos el contexto
        hilo = new Hilo(); // Inicializamos el hilo
        setFocusable(true); // Aseguramos que reciba eventos de toque
        bitmapFondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.imagenprueba);
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);//audio

        mediaPlayer= MediaPlayer.create(context, R.raw.musicamenu);//musica
        v= audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mediaPlayer.setVolume(0.9f,0.9f);
        mediaPlayer.setLooping(true);

        //SOUNDPOOL
        //MUSICA
        if ((android.os.Build.VERSION.SDK_INT) >= 21) {
            SoundPool.Builder spb=new SoundPool.Builder();
            spb.setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build());
            spb.setMaxStreams(maxSonidosSimultaneos);
            this.efectos=spb.build();
        } else {
            this.efectos=new SoundPool(maxSonidosSimultaneos, AudioManager.STREAM_MUSIC, 0);
        }
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        sonidoOpciones=efectos.load(context, R.raw.tocaropciones,1);
        ataquecriaturao=efectos.load(context, R.raw.ataquecriaturao,1);
        golpe1=efectos.load(context, R.raw.golpe1,1);
        //
    }


    /**
     * Se invoca cuando el tamaño del lienzo de dibujo varía y en la cual podemos obtener su
     * tamaño.
     * Nuevo (x,y ) y viejo (oldw,oldh) tamaño del lienzo
     * @param w nuevo ancho
     * @param h nuevo alto
     * @param oldw viejo ancho
     * @param oldh viejo alto
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) { // nuevo (x,y ) y viejo (oldw,oldh) tamaño del lienzo
        super.onSizeChanged(w, h, oldw, oldh);
        this.anchoPantalla = w;
        this.altoPantalla = h;
    }

    /**
     * Gestionamos las acciones sobre la pantalla
     * @param event Representa un evento asociado a un movimiento
     * @return devuelve true despues de haber ejecutado la tarea según la acción hecha sobre
     * pantalla
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {//aqui gestionamos el cambio de pantalla
        int accion = event.getAction(); // Acción realizada sobre la pantalla

        switch (accion) { // En MotionEvent tememos todas las posibilidades de acciones
            case MotionEvent.ACTION_DOWN: // Levantamos un dedo que pulsa en la pantalla
                pantalla.onTouch(event);
                break;
            case MotionEvent.ACTION_UP: // Pulsamos la pantalla
                int aa = pantalla.onTouch(event);
                cambioEscena(aa);
                break;
            case MotionEvent.ACTION_MOVE: // Pulsamos la pantalla
                pantalla.onTouch(event);
                break;
        }
        return true;
    }

    /**
     * Gestionamos el cambio de escena (Cambio ente pantallas)
     * @param nuevaEscena devuelve el número de escena de la pantalla a la que cambiamos
     */
    public void cambioEscena(int nuevaEscena) {//AQUI SE SELECCIONARA LA PANTALLA A CAMBIAR
        if (pantalla.getNumEscena() != nuevaEscena) {
            switch (nuevaEscena) {
                case Constantes.PMENU:
                    pantalla = new PantallaMenuPrincipal(1, context, anchoPantalla, altoPantalla);
                    break;
                case Constantes.PJUEGO:
                    pantalla = new PantallaJuego(2, context, anchoPantalla, altoPantalla);
                    break;
                case 100:
                    pantalla = new PantallaJuego(2, context, anchoPantalla, altoPantalla);
                    break;
                case Constantes.PESTADISTICAS:
                    pantalla = new PantallaEstadisticas(3, context, anchoPantalla, altoPantalla);
                    break;
                case Constantes.PAYUDA:
                    pantalla = new PantallaAyuda(4, context, anchoPantalla, altoPantalla);
                    break;
                case Constantes.PAJUSTES:
                    pantalla = new PantallaAjustes(5, context, anchoPantalla, altoPantalla);
                    break;
                case Constantes.PCREDITOS:
                    pantalla = new PantallaCreditos(6, context, anchoPantalla, altoPantalla);
                    break;
                case Constantes.PBORRARH:
                    pantalla = new PantallaBorrarHistorial(7, context, anchoPantalla, altoPantalla);
                    break;
            }
        }
    }


    // Callbacks del SurfaceHolder ///////////////////////////////

    /**
     * Se ejecuta inmediatamente después de la creación de la superficie de dibujo
     * @param holder holder
     */
    @Override // En cuanto se crea el SurfaceView se lanze el hilo
    public void surfaceCreated(SurfaceHolder holder) {

    }


    // Si hay algún cambio en la superficie de dibujo (normalmente su tamaño) obtenemos el nuevo tamaño

    /**
     * Se ejecuta inmediatamente después de que la superficie de dibujo tenga cambios
     * o bien de tamaño o bien de forma
     * @param holder holder
     * @param format formato
     * @param width nuevo ancho
     * @param height nuevo alto
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {//desde aqui se llama a la pantalla principal para luego gestionar cada cambio de pantalla en los onTouch
        anchoPantalla = width;
        altoPantalla = height;

        pantalla = new PantallaMenuPrincipal(1, context, anchoPantalla, altoPantalla);

        hilo.setSurfaceSize(width, height);
        hilo.setFuncionando(true);
        if (hilo.getState() == Thread.State.NEW) hilo.start();
        if (hilo.getState() == Thread.State.TERMINATED) {
            hilo = new Hilo();
            hilo.start();
        }
    }

    // Al finalizar el surface, se para el hilo

    /**
     * Se ejecuta inmediatamente antes de la destrucción de la superficie de dibujo
     * @param holder holder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hilo.setFuncionando(false);
        try {
            hilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clase Hilo en la cual se ejecuta el método de dibujo y de física
     * para que se haga en paralelo con la gestión de la interfaz de usuario
     * @author Carlos Daniel Tabares Comesaña
     * @version 1.0 18/03/2022
     */
    public class Hilo extends Thread {

        /**
         * Constructor de la clase Hilo
         */
        public Hilo() {
        }

        /**
         * Ejecuta el hilo
         */
        @Override
        public void run() {//Aqui se llama a las distintas pantallas
            while (funcionando) {
                Canvas c = null; //Siempre es necesario repinta todo el lienzo
                try {
                    if (!surfaceHolder.getSurface().isValid())
                        continue; // si la superficie no está preparada repetimos


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        c = surfaceHolder.lockHardwareCanvas(); // Obtenemos el lienzo con Aceleración Hw. Desde la API 26
                    }
                    if (c == null){
                        c = surfaceHolder.lockCanvas(); // Obtenemos el lienzo con aceleración software
                    }
                    synchronized (surfaceHolder) { // La sincronización es necesaria por ser recurso común
                        pantalla.actualizarFisica();// Movimiento de los elementos
                        if (c!=null){
                            pantalla.dibujar(c);
                        }
                    }
                } finally { // Haya o no excepción, hay que liberar el lienzo
                    if (c != null) {
                        surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }

        // Activa o desactiva el funcionamiento del hilo
        void setFuncionando(boolean flag) {
            funcionando = flag;
        }

        // Función llamada si cambia el tamaño del view
        public void setSurfaceSize(int width, int height) {
            synchronized (surfaceHolder) { // Se recomienda realizarlo de forma atómica
                if (bitmapFondo != null) { // Cambiamos el tamaño de la imagen de fondo al tamaño de la pantalla
                    bitmapFondo = Bitmap.createScaledBitmap(bitmapFondo, width, height, true);
                }
            }
        }
    }
}
