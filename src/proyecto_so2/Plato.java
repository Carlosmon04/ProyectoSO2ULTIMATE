package proyecto_so2;

import java.util.Random;

public class Plato {
    private int comida;

    public Plato() {
        Random rand = new Random();
        this.comida = rand.nextInt(10) + 1; // Cantidad de comida entre 1 y 10
    }

    public boolean comerBocado() {
        if (comida > 0) {
            comida--;
            return true;
        }
        return false;
    }

    public int getComida() {
        return comida;
    }
}
