package proyecto_so2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Semaphore;

public class FilosofosSwing extends JFrame {
    private JTextField txtSegundos;
    private JTextField txtFilosofoCount;
    private JTextArea txtOutput;
    private JButton btnIniciar;
    private JButton btnDetener;
    
    private Semaphore tenedorSemaforo;
    private Filosofos[] filosofos;
    private Tenedores[] tenedores;
    private boolean running = false;
    
    public FilosofosSwing() {
        setTitle("Simulación de Filósofos Comensales");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panelEntrada = new JPanel();
        panelEntrada.setLayout(new GridLayout(3, 2, 10, 10));
        
        panelEntrada.add(new JLabel("Segundos:"));
        txtSegundos = new JTextField();
        panelEntrada.add(txtSegundos);
        
        panelEntrada.add(new JLabel("Número de Filósofos:"));
        txtFilosofoCount = new JTextField();
        panelEntrada.add(txtFilosofoCount);
        
        btnIniciar = new JButton("Iniciar");
        btnDetener = new JButton("Detener");
        
        btnIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSimulacion();
            }
        });
        
        btnDetener.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detenerSimulacion();
            }
        });
        
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnIniciar);
        panelBotones.add(btnDetener);
        
        txtOutput = new JTextArea();
        txtOutput.setEditable(false);
        txtOutput.setLineWrap(true);
        txtOutput.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(txtOutput);
        scrollPane.setPreferredSize(new Dimension(580, 250));
        
        getContentPane().add(panelEntrada, BorderLayout.NORTH);
        getContentPane().add(panelBotones, BorderLayout.CENTER);
        getContentPane().add(scrollPane, BorderLayout.SOUTH);
    }
    
    private void iniciarSimulacion() {
        if (running) {
            txtOutput.append("La simulación ya está en ejecución.\n");
            return;
        }
        
        try {
            int segundos = Integer.parseInt(txtSegundos.getText()) * 1000;
            int maestros = Integer.parseInt(txtFilosofoCount.getText());
            
            if (segundos <= 0 || maestros < 1 || maestros > 10) {
                txtOutput.append("Por favor, ingrese valores válidos.\n");
                return;
            }
            
            tenedorSemaforo = new Semaphore(maestros - 1);
            tenedores = new Tenedores[maestros];
            inicializarTenedores(tenedores);
            
            filosofos = new Filosofos[maestros];
            inicializarFilosofos(filosofos, tenedores, tenedorSemaforo, segundos);
            
            for (Filosofos f : filosofos) {
                f.start();
            }
            
            running = true;
            txtOutput.append("Simulación iniciada.\n");
            
        } catch (NumberFormatException e) {
            txtOutput.append("Error en los datos de entrada.\n");
        }
    }
    
    private void detenerSimulacion() {
        if (!running) {
            txtOutput.append("La simulación no está en ejecución.\n");
            return;
        }
        
        for (Filosofos f : filosofos) {
            f.terminar();
        }
        
        for (Filosofos f : filosofos) {
            try {
                f.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        running = false;
        txtOutput.append("Simulación detenida.\n");
    }
    
    private void inicializarTenedores(Tenedores[] tenedores) {
        for (int i = 0; i < tenedores.length; i++) {
            String nombreR = "Derecho";
            String nombreL = "Izquierdo";
            String f = (i % 2 == 0) ? nombreR : nombreL;
            tenedores[i] = new Tenedores(i, f);
            tenedores[i].setOutput(txtOutput); // Asignar el JTextArea a los tenedores
        }
    }
    
    private void inicializarFilosofos(Filosofos[] f, Tenedores[] tenedores, Semaphore semaforo, int segundos) {
        String[] nombresFilosofos = {
            "Socrates", "Aristoteles", "Valencio", "Serapio", "Serafin",
            "Ruperegildo", "Eusebio", "Joaques", "Lexor", "Hanibal"
        };
        
        for (int k = 0; k < f.length; k++) {
            String nombre = nombresFilosofos[k];
            f[k] = new Filosofos(k, tenedores[k], tenedores[(k + 1) % f.length], nombre, semaforo, f.length);
            f[k].setOutput(txtOutput);
            f[k].setDelay(segundos);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 907, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 576, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FilosofosSwing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FilosofosSwing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FilosofosSwing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FilosofosSwing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FilosofosSwing().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
