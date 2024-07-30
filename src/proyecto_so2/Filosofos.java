package proyecto_so2;

import java.util.concurrent.Semaphore;

public class Filosofos extends Thread {
    private int id;
    private Tenedores tenedorIzquierdo;
    private Tenedores tenedorDerecho;
  
    private String nombre;
    private Semaphore semaforo;
    private int totalFilosofos;
    private Plato plato;

    public Filosofos(int id, Tenedores tenedorIzquierdo, Tenedores tenedorDerecho, String nombre, Semaphore semaforo, int totalFilosofos) {
        this.id = id;
        this.tenedorIzquierdo = tenedorIzquierdo;
        this.tenedorDerecho = tenedorDerecho;
        
        this.nombre = nombre;
        this.semaforo = semaforo;
        this.totalFilosofos = totalFilosofos;
        this.plato = new Plato();
    }

    public void run() {
        try {
            while (true) {
                pensar();
                if (totalFilosofos > 1) {
                    semaforo.acquire();
                    tenedorIzquierdo.qTenedores(nombre);
                    tenedorDerecho.qTenedores(nombre);
                    comer();
                    tenedorDerecho.sTenedores(nombre);
                    tenedorIzquierdo.sTenedores(nombre);
                    semaforo.release();
                } else {
                    // Si solo hay un filósofo, simplemente piensa y come sin necesitar tenedores
                    comer();
                }
                Thread.sleep(Proyecto_SO2.segundos); // Delay entre acciones
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void pensar() throws InterruptedException {
        System.out.println(nombre + " está pensando.");
        Thread.sleep(Proyecto_SO2.segundos);
    }

    private void comer() throws InterruptedException {
        System.out.println(nombre + " está comiendo. La cantidad de donas en el"
                + " plato: " + plato.getComida());
        while (plato.comerBocado()) {
            System.out.println(nombre + " da un bocado. Comida restante: " + plato.getComida());
            Thread.sleep(2000); // Delay por cada bocado
        }
        System.out.println(nombre + " ha terminado de comer.");
        plato= new Plato();
    }
}
