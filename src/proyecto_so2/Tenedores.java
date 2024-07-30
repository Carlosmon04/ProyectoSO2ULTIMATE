package proyecto_so2;

public class Tenedores {
    private int tenedor;
    private String nombre;
    private boolean libre = true;

    public Tenedores(int tenedor, String nombre) {
        this.tenedor = tenedor;
        this.nombre = nombre;
    }

    public synchronized void qTenedores(String nombreFilosofo) throws InterruptedException {
        while (!libre) {
            wait();
        }
        System.out.println("Filosofo " + nombreFilosofo + " agarra tenedor " + this.nombre + " (" + tenedor + ")");
        libre = false;
    }

    public synchronized void sTenedores(String nombreFilosofo) {
        libre = true;
        System.out.println("Filosofo " + nombreFilosofo + " suelta tenedor " + this.nombre + " (" + tenedor + ")");
        notify();
    }
}
