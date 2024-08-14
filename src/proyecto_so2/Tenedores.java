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
// si utiliza sincronizacion para que tengan orden al momento de accer a los tenedores o soltarlos tambien
    public synchronized void qTenedores(String nombreFilosofo) throws InterruptedException {
        //uso la variable libre para determinar si el tenedor esta en uso o no
        while (!libre) {
            wait();//wait, los demas deben esperar porque ya esta ocupado el puesto o tenedor
        }
        if (output != null) {
            output.append("Filósofo " + nombreFilosofo + " agarra tenedor " + this.nombre + " (" + tenedor + ")\n");
            output.setCaretPosition(output.getDocument().getLength());
        }
        libre = false;
    }

    public synchronized void sTenedores(String nombreFilosofo) {
        //Aqui estaria Soltando los tenedores he indicando cual es
        libre = true;
        if (output != null) {
            output.append("Filósofo " + nombreFilosofo + " suelta tenedor " + this.nombre + " (" + tenedor + ")\n");
            output.setCaretPosition(output.getDocument().getLength());
        }
        notify();//con notify se les avisa a los demas hilos que ya esta listo o que hubo un cambio
    }
}
