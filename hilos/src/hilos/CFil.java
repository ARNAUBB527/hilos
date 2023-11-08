package hilos;

//Clase CFil
public class CFil extends Thread {
 private String nombre;
 private int temporizacion;

 public CFil(String nombre) {
     this.nombre = nombre;
 }

 public String getNombre() {
     return nombre;
 }

 public void setTemporizacion(int temporizacion) {
     this.temporizacion = temporizacion;
 }

 @Override
 public void run() {
     try {
         // Realiza alguna tarea o proceso aquí
         Thread.sleep(temporizacion); // Simula una temporización
         System.out.println("Hilo " + nombre + " ha terminado.");
     } catch (InterruptedException e) {
         e.printStackTrace();
     }
 }
}
