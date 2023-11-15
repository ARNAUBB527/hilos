package hilos;


class CFil extends Thread {
    private String nombre;
    private int sleepTime;

    public CFil(String nombre) {
        this.nombre = nombre;
        this.sleepTime = 1000; // Valor predeterminado
    }

    public String getNombre() {
        return nombre;
    }

    public void setTemporizacion(int tiempo) {
        this.sleepTime = tiempo;
    }

    @Override
    public void run() {
        System.out.println("Iniciando " + nombre);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Terminando " + nombre);
    }
}