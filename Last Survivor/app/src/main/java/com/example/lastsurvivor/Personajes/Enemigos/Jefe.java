//package com.example.lastsurvivor.Personajes.Enemigos;
//
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//
//import com.example.lastsurvivor.Personajes.Vida;
//
//public class Jefe extends Enemigo{
//    Paint paint;
//    //RectF rect;
//    Vida vida;
//    Bitmap imagen;
//
//    public Jefe(Bitmap imagen, float x, float y, int tx, int ty) {
//        super(imagen,x,y,tx,ty);
//        paint=new Paint();
//        paint.setColor(Color.RED);
//        vida=new Vida(300);
//    }
//
//    public void dibujar(Canvas canvas, int anchoPantalla, int altoPantalla){
//        canvas.drawRect(rect, paint);
//        vida.dibujar(canvas,rect);
//    }
//
//    public void actualizarFisica(){
//        posicion.x += velocidad;
////        if (posicion.y > alto) {
////            posicion.y = 0;
////            posicion.x = g.nextFloat() * (ancho - imagen.getWidth());
////        }
//        actualizaRec();
//    }
//
//    //Establece el movimiento de un enemigo en una pantalla definida por alto y ancho y cierta velocidad
//    public void moverEnemigo(float velocidad,float posDeAtaque) {
//        if (posicion.x > posDeAtaque) {
//            posicion.x += velocidad;
////        if (posicion.y > alto) {
////            posicion.y = 0;
////            posicion.x = g.nextFloat() * (ancho - imagen.getWidth());
////        }
//            actualizaRec();
//        }
//    }
//}
