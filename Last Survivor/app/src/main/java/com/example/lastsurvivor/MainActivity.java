package com.example.lastsurvivor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.lastsurvivor.Pantallas.Pantalla;
import com.example.lastsurvivor.Pantallas.PantallaJuego;

/**
 * MainActivity activity principal
 * @author Carlos Daniel Tabares Comesaña
 * @version 1.0 18/03/2022
 */
public class MainActivity extends AppCompatActivity implements SensorEventListener {
    /**
     * Elemento hardware para vibración
     */
    public static Vibrator vibrador;

    /**
     * Determina si se pausa el sensor al cerrar la app
     */
    boolean pausa=false;

    //Sensor
    /**
     * Instancia del sensor
     */
    private SensorManager senSensorManager;

    /**
     * Creación del sensor acelerómetro
     */
    public static Sensor senAccelerometer;

    /**
     * Tiempo transcurrido necesario para detectar el movimiento del dispositivo como
     * agite.
     */
    private long lastUpdate = 0;

    /**
     * Determina las últimas posiciones de los ejes x,y & z
     */
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;

    /**
     * Método que desarrolla la inicialización del Activity: se restaura
     * un estado anterior si lo hay, se inicializan variables, se inician los
     * componentes de la interfaz gráfica
     * @param savedInstanceState parametro que ofrece datos sobre la instancia previa
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Juego juego = new Juego(this);
        juego.setKeepScreenOn(true);
        setContentView(juego);

        vibrador= (Vibrator)getSystemService(VIBRATOR_SERVICE);

        senSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);

        //Codigo para poner la aplicacion en pantalla completa
        if (Build.VERSION.SDK_INT < 16) { // versiones anteriores a Jelly Bean
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else { // versiones iguales o superiores a Jelly Bean
            final int flags = View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION // Oculta la barra de navegación
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // Oculta la barra de navegación
                    | View.SYSTEM_UI_FLAG_IMMERSIVE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            final View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(flags);
            decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    decorView.setSystemUiVisibility(flags);
                }
            });
        }
        getSupportActionBar().hide(); // se oculta la barra de ActionBar
    }

    /**
     * Indica que un sensor a generado un nuevo valor como un objeto SensorEvent.
     * @param sensorEvent evento del sensor
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000;


                if (speed > SHAKE_THRESHOLD) {
                    if (PantallaJuego.activarKBoom){
                        PantallaJuego.comprobacionActivarKBoom=true;
                    }
                }

                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }

    /**
     * Método no ulitizado pero necesario al implementar el SensorEventListener
     * @param sensor
     * @param accuracy
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * Desregistra el sensor cuando se deja de usar.
     */
    public void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
        pausa=true;
        Juego.mediaPlayer.pause();
    }

    /**
     * Registra el sensor antes de usarlo
     */
    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        if (pausa){
            Juego.mediaPlayer.start();
            pausa=false;
        }
    }
}

