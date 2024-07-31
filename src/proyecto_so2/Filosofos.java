package proyecto_so2;

import javax.swing.JTextArea;
import java.util.concurrent.Semaphore;

public class Filosofos extends Thread {
    private int id;
    private Tenedores tenedorIzquierdo;
    private Tenedores tenedorDerecho;
    private String nombre;
    private Semaphore semaforo;
    private int totalFilosofos;
    private Plato plato;
    private volatile boolean haTerminado;
    private static int contTerminado = 0; // Contador de filósofos que han terminado
    private static final Object lock = new Object(); // Objeto para sincronización
    private JTextArea output;
    private int delay;

    public Filosofos(int id, Tenedores tenedorIzquierdo, Tenedores tenedorDerecho, String nombre, Semaphore semaforo, int totalFilosofos) {
        this.id = id;
        this.tenedorIzquierdo = tenedorIzquierdo;
        this.tenedorDerecho = tenedorDerecho;
        this.nombre = nombre;
        this.semaforo = semaforo;
        this.totalFilosofos = totalFilosofos;
        this.plato = new Plato();
        this.haTerminado = false;
    }

    public void setOutput(JTextArea output) {
        this.output = output;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void run() {
        try {
            while (!haTerminado) {
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
                    comer();
                }
                Thread.sleep(delay); // Delay entre acciones
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    private void pensar() throws InterruptedException {
        if (output != null) {
            output.append(nombre + " está pensando.\n");
            output.setCaretPosition(output.getDocument().getLength());
        }
        Thread.sleep(delay);
    }

    private void comer() throws InterruptedException {
        if (output != null) {
            output.append(nombre + " está comiendo. La cantidad de donas en el plato: " + plato.getComida() + "\n");
            output.setCaretPosition(output.getDocument().getLength());
        }
        while (plato.comerBocado()) {
            if (output != null) {
                output.append(nombre + " da un bocado. Comida restante: " + plato.getComida() + "\n");
                output.setCaretPosition(output.getDocument().getLength());
            }
            Thread.sleep(2000);
        }
        if (output != null) {
            output.append(nombre + " ha terminado de comer.\n");
            output.setCaretPosition(output.getDocument().getLength());
        }
        plato = new Plato();
        marcarComoTerminado();
    }

    private void marcarComoTerminado() throws InterruptedException {
        synchronized (lock) {
            contTerminado++;
            if (contTerminado == totalFilosofos) {
                output.append("Todos Los Filosofos Comieron");
                Thread.sleep(6000);
                System.exit(0); // Finalizar la aplicación cuando todos los filósofos hayan terminado
            }
        }
    }

    public void terminar() {
        haTerminado = true;
    }
}
