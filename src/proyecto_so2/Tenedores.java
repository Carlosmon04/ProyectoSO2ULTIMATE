package proyecto_so2;

import javax.swing.JTextArea;

public class Tenedores {
    private int tenedor;
    private String nombre;
    private boolean libre = true;
    private JTextArea output;

    public Tenedores(int tenedor, String nombre) {
        this.tenedor = tenedor;
        this.nombre = nombre;
    }

    public void setOutput(JTextArea output) {
        this.output = output;
    }

    public synchronized void qTenedores(String nombreFilosofo) throws InterruptedException {
        while (!libre) {
            wait();
        }
        if (output != null) {
            output.append("Filósofo " + nombreFilosofo + " agarra tenedor " + this.nombre + " (" + tenedor + ")\n");
            output.setCaretPosition(output.getDocument().getLength());
        }
        libre = false;
    }

    public synchronized void sTenedores(String nombreFilosofo) {
        libre = true;
        if (output != null) {
            output.append("Filósofo " + nombreFilosofo + " suelta tenedor " + this.nombre + " (" + tenedor + ")\n");
            output.setCaretPosition(output.getDocument().getLength());
        }
        notify();
    }
}
