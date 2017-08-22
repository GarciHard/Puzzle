package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;
import java.util.zip.InflaterInputStream;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * Hecho con <3 por:
 * @author GarciHard
 */

public class Rompecabezas extends javax.swing.JFrame implements ActionListener {

    private JButton btnBoton[] = new JButton[16], temp, btnTemp, Verificar;
    private int t, i = 0, x, y, j = 0, x1, y1, segundos = 0, minutos = 0, horas = 0, direccion = 0, direccion2 = 0, cont = 0,
            arregloSec[] = new int[6], arregloMin[] = new int[6], arregloHora[] = new int[6];
    private Random rnd = new Random();
    private String jugador, arregloNombres[] = new String[6];
    private FileWriter prt;
    private File f;


    public void actionPerformed(ActionEvent e) {
        if (!timer.isRunning()) {

            //Color de los botones
            btnTemp = (JButton) e.getSource();
            int r = (int) (Math.random() * 1024);
            int g = (int) (Math.random() * 1024);
            int b = (int) (Math.random() * 1024);

            x = btnTemp.getLocation().x;
            y = btnTemp.getLocation().y;
            x1 = btnBoton[15].getLocation().x;
            y1 = btnBoton[15].getLocation().y;

            if ((x + 126) == x1 && y == y1) {
                btnTemp.setBackground(Color.getHSBColor(r, g, b));
                direccion = 1;
                direccion2 = -126;
                timer.start();
            } else if ((x - 126) == x1 && y == y1) {
                btnTemp.setBackground(Color.getHSBColor(r, g, b));
                direccion = -1;
                direccion2 = 126;
                timer.start();
            } else if (y + 126 == y1 && x == x1) {
                btnTemp.setBackground(Color.getHSBColor(r, g, b));
                direccion = 1;
                direccion2 = -126;
                timer.start();
            } else if (y - 126 == y1 && x == x1) {
                btnTemp.setBackground(Color.getHSBColor(r, g, b));
                direccion = -1;
                direccion2 = 126;
                timer.start();
            }
        }
    }
    //timer del movimiento
    Timer timer = new Timer(2, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            x = btnTemp.getLocation().x;
            y = btnTemp.getLocation().y;

            if (x != x1) {
                btnBoton[15].setLocation(x1 + direccion2, y1);
                btnTemp.setLocation(x + direccion, y);
                Verificar();
            } else if (y != y1) {
                btnBoton[15].setLocation(x1, y1 + direccion2);
                btnTemp.setLocation(x, y + direccion);
                Verificar();
            } else if (x == x1) {
                timer.stop();
                x1 = x1 + direccion2;
                direccion = 0;
                direccion2 = 0;
                Verificar();
            } else if (y == y1) {
                timer.stop();
                y1 = y1 + direccion2;
                direccion = 0;
                direccion2 = 0;
                Verificar();
            }
        }
    });
    // timer del cronometro
    Timer contador = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (segundos < 59 && horas < 5) {
                segundos += 1;
                txtSegundos.setText(String.valueOf("0" + segundos));
            } else if (segundos <= 59 && minutos < 60 && horas < 5) {
                segundos = 0;
                minutos += 1;
                txtMinutos.setText(String.valueOf("0" + minutos));
            } else if (minutos <= 60 && horas < 5) {
                minutos = 0;
                horas += 1;
                txtHoras.setText(String.valueOf("0" + horas));
            } else if (horas <= 5) {
                txtSegundos.setText("00");
                txtMinutos.setText("00");
                txtHoras.setText("00");
                JOptionPane.showMessageDialog(null, "Ups!! Se acabo el tiempo. Intentalo de nuevo shab00t :v.", "Puzzle Simulación",
                        JOptionPane.INFORMATION_MESSAGE);
                contador.stop();
                for (i = 0; i < 15; i++) {
                    JButton btnReiniciar;
                    btnReiniciar = new JButton();
                    btnReiniciar.setBounds(0 + 126 * (i % 4), 0 + 126 * (i / 4), 126, 126);
                    x = btnReiniciar.getLocation().x;
                    y = btnReiniciar.getLocation().y;
                    btnBoton[i].setLocation(x, y);
                }
            }
        }
    }
    );

    public Rompecabezas() {
        initComponents();
        Iniciar();
        imprimirJugadores();
    }

    //metodo para iniciar el juego
    private void Iniciar() {
        this.pnlNumeros.removeAll();
        segundos = 0;
        minutos = 0;
        horas = 0;
        cont = 0;
        txtSegundos.setText("00");
        txtMinutos.setText("00");
        txtHoras.setText("00");
        contador.stop();
        timer.stop();

        for (i = 0; i < 16; i++) {
//Valores antiguos >>> btnBoton[i].setBounds(0 + 50 * (i % 4), 0 + 50 * (i / 4), 50, 50);
            btnBoton[i] = new JButton(String.valueOf(i + 1));
            btnBoton[i].setBounds(0 + 126 * (i % 4), 0 + 126 * (i / 4), 126, 126);
            btnBoton[i].setFont(new Font("Heiti SC", Font.BOLD, 16));
            btnBoton[i].setForeground(Color.black);
            pnlNumeros.add(btnBoton[i]);
        }
        btnBoton[15].setVisible(false);
    }

    //metodo para revolver los botones
    private void Revolver() {
        for (i = 0; i < 15; i++) {
            t = rnd.nextInt(15);
            temp = btnBoton[i];
            x = temp.getLocation().x;
            y = temp.getLocation().y;
            x1 = btnBoton[t].getLocation().x;
            y1 = btnBoton[t].getLocation().y;
            btnBoton[t].setLocation(x, y);
            temp.setLocation(x1, y1);
        }
    }

    //metodo para verificar si estan ordenados
    private void Verificar() {
        for (i = 0; i < 15; i++) {
            Verificar = new JButton();
//Valores antiguos >>> Verificar.setBounds(0+50*(i%4), 0+50*(i/4), 50, 50);
            Verificar.setBounds(0 + 126 * (i % 4), 0 + 126 * (i / 4), 126, 126);

            x = Verificar.getLocation().x;
            y = Verificar.getLocation().y;

            if (((btnBoton[i].getLocation().x) == x) && ((btnBoton[i].getLocation().y) == y)) {
                cont += 1;
            } else {
                cont = 0;
            }
        }

        if (cont == 15) {
            timer.stop();
            contador.stop();
            estaOrdenadoElpuzzle();
        } else {
            cont = 0;
        }
    }
    //metodo que verifica si el jugador entra en el puntaje

    private void estaOrdenadoElpuzzle() {
        for (i = 0; i < 5; i++) {
            if (horas <= arregloHora[i] && minutos <= arregloMin[i] && segundos <= arregloSec[i] || horas <= arregloHora[i] && minutos >= arregloMin[i] && segundos <= arregloSec[i]) {
                OrdenarJugador();
            } else if (horas <= arregloHora[i] && minutos <= arregloMin[i] && segundos >= arregloSec[i] || horas <= arregloHora[i] && minutos >= arregloMin[i] && segundos >= arregloSec[i]) {
                OrdenarJugador();
            }
        }
    }

    public void OrdenarJugador() {
        jugador = (String) JOptionPane.showInputDialog(this, new JLabel("Felicidades, has resuelto el rompecabezas. Ingresa tu nombre: ", JLabel.CENTER),
                "Puzzle Simulación", JOptionPane.INFORMATION_MESSAGE);
        if (jugador != null && !jugador.equals(" ") && !jugador.equals("  ") && !jugador.equals("   ") && !jugador.equals("    ")
                && !jugador.equals("     ") && !jugador.equals("      ")) {
            JOptionPane.showMessageDialog(this, "Gracias por jugar. Puedes iniciar un nuevo juego y romper tu récord.", "Puzzle Simulación",
                    JOptionPane.INFORMATION_MESSAGE);
            arregloSec[5] = segundos;
            arregloMin[5] = minutos;
            arregloHora[5] = horas;
            arregloNombres[5] = jugador;

            System.out.println(jugador);
            System.out.println(horas + " : " + minutos + " : " + segundos);

            for (i = 0; i < 15; i++) {
                btnBoton[i].removeActionListener(this);
            }
            this.pnlNumeros.removeAll();
            Iniciar();
            this.paintAll(this.getGraphics());
            segundos = 0;
            minutos = 0;
            horas = 0;
            txtSegundos.setText("00");
            txtMinutos.setText("00");
            txtHoras.setText("00");
            cont = 0;

            //for para ordenar por hora
            for (i = 0; i <= 5 - 1; i++) {
                for (j = 0; j <= 5 - 1; j++) {
                    if (arregloHora[j] >= arregloHora[j + 1]) {
                        int tmp1 = arregloHora[j + 1];
                        arregloHora[j + 1] = arregloHora[j];
                        arregloHora[j] = tmp1;

                        int tmp2 = arregloMin[j + 1];
                        arregloMin[j + 1] = arregloMin[j];
                        arregloMin[j] = tmp2;

                        int tmp3 = arregloSec[j + 1];
                        arregloSec[j + 1] = arregloSec[j];
                        arregloSec[j] = tmp3;

                        String tmp4 = arregloNombres[j + 1];
                        arregloNombres[j + 1] = arregloNombres[j];
                        arregloNombres[j] = tmp4;
                    }
                }
            }
            //ordena por minutos
            for (i = 0; i <= 5 - 1; i++) {
                for (j = 0; j <= 5 - 1; j++) {
                    if (arregloMin[j] >= arregloMin[j + 1] && arregloHora[j] == arregloHora[j + 1]) {
                        int tmp1 = arregloHora[j + 1];
                        arregloHora[j + 1] = arregloHora[j];
                        arregloHora[j] = tmp1;

                        int tmp2 = arregloMin[j + 1];
                        arregloMin[j + 1] = arregloMin[j];
                        arregloMin[j] = tmp2;

                        int tmp3 = arregloSec[j + 1];
                        arregloSec[j + 1] = arregloSec[j];
                        arregloSec[j] = tmp3;

                        String tmp4 = arregloNombres[j + 1];
                        arregloNombres[j + 1] = arregloNombres[j];
                        arregloNombres[j] = tmp4;
                    }
                }
            }
            // ordena por segundos
            for (i = 0; i <= 5 - 1; i++) {
                for (j = 0; j <= 5 - 1; j++) {
                    if (arregloSec[j] >= arregloSec[j + 1] && arregloMin[j] == arregloMin[j + 1]) {
                        int tmp1 = arregloHora[j + 1];
                        arregloHora[j + 1] = arregloHora[j];
                        arregloHora[j] = tmp1;

                        int tmp2 = arregloMin[j + 1];
                        arregloMin[j + 1] = arregloMin[j];
                        arregloMin[j] = tmp2;

                        int tmp3 = arregloSec[j + 1];
                        arregloSec[j + 1] = arregloSec[j];
                        arregloSec[j] = tmp3;

                        String tmp4 = arregloNombres[j + 1];
                        arregloNombres[j + 1] = arregloNombres[j];
                        arregloNombres[j] = tmp4;
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Ups!, No ingresaste tu nombre. Intenta de nuevo con un juego nuevo :v.", "Puzzle Simulación",
                    JOptionPane.INFORMATION_MESSAGE);
            for (i = 0; i < 15; i++) {
                btnBoton[i].removeActionListener(this);
            }
            this.pnlNumeros.removeAll();
            Iniciar();
            this.paintAll(this.getGraphics());

            segundos = 0;
            minutos = 0;
            horas = 0;
            txtSegundos.setText("00");
            txtMinutos.setText("00");
            txtHoras.setText("00");
            cont = 0;
            //for para ordenar por hora
            for (i = 0; i < 5 - 1; i++) {
                for (j = 0; j < 5 - 1; j++) {
                    if (arregloHora[j] >= arregloHora[j + 1]) {
                        int tmp1 = arregloHora[j + 1];
                        arregloHora[j + 1] = arregloHora[j];
                        arregloHora[j] = tmp1;

                        int tmp2 = arregloMin[j + 1];
                        arregloMin[j + 1] = arregloMin[j];
                        arregloMin[j] = tmp2;

                        int tmp3 = arregloSec[j + 1];
                        arregloSec[j + 1] = arregloSec[j];
                        arregloSec[j] = tmp3;

                        String tmp4 = arregloNombres[j + 1];
                        arregloNombres[j + 1] = arregloNombres[j];
                        arregloNombres[j] = tmp4;
                    }
                }
            }
            //ordena por minutos
            for (i = 0; i < 5 - 1; i++) {
                for (j = 0; j < 5 - 1; j++) {
                    if (arregloMin[j] >= arregloMin[j + 1] && arregloHora[j] == arregloHora[j + 1]) {
                        int tmp1 = arregloHora[j + 1];
                        arregloHora[j + 1] = arregloHora[j];
                        arregloHora[j] = tmp1;

                        int tmp2 = arregloMin[j + 1];
                        arregloMin[j + 1] = arregloMin[j];
                        arregloMin[j] = tmp2;

                        int tmp3 = arregloSec[j + 1];
                        arregloSec[j + 1] = arregloSec[j];
                        arregloSec[j] = tmp3;

                        String tmp4 = arregloNombres[j + 1];
                        arregloNombres[j + 1] = arregloNombres[j];
                        arregloNombres[j] = tmp4;
                    }
                }
            }
            // ordena por segundos
            for (i = 0; i < 5 - 1; i++) {
                for (j = 0; j < 5 - 1; j++) {
                    if (arregloSec[j] >= arregloSec[j + 1] && arregloMin[j] == arregloMin[j + 1]) {
                        int tmp1 = arregloHora[j + 1];
                        arregloHora[j + 1] = arregloHora[j];
                        arregloHora[j] = tmp1;

                        int tmp2 = arregloMin[j + 1];
                        arregloMin[j + 1] = arregloMin[j];
                        arregloMin[j] = tmp2;

                        int tmp3 = arregloSec[j + 1];
                        arregloSec[j + 1] = arregloSec[j];
                        arregloSec[j] = tmp3;

                        String tmp4 = arregloNombres[j + 1];
                        arregloNombres[j + 1] = arregloNombres[j];
                        arregloNombres[j] = tmp4;
                    }
                }
            }
        }
        // guarda el nombre del jugador
        try {
            String ruta = "src/players/Jugadores.txt";
            f = new File(ruta);
            prt = new FileWriter(f, false);
            prt = new FileWriter(f, true);
            
            for (i = 0; i < 5; i++) {
                prt.write(arregloNombres[i] + "\r\n");
                prt.write(arregloHora[i] + "\r\n");
                prt.write(arregloMin[i] + "\r\n");
                prt.write(arregloSec[i] + "\r\n");
            }
            prt.close();
        } catch (Exception e) {
            System.out.println("Escritura de datos: " + e);
            JOptionPane.showMessageDialog(this, "Error al grabar el nombre", "Puzzle Simulación", JOptionPane.ERROR_MESSAGE);
        }
        imprimirJugadores();
    }

    private void imprimirJugadores() {
        // imprime el nombre y el tiempo que le tomo al jugador completar el puzzle en un txtArea
        int m = 0;
        txaJugadores.setText(null);
        String ruta = "src/players/Jugadores.txt";
        f = new File(ruta);
        
        Scanner sc;
        String leerNombre;
        int leerPuntajeSec, leerPuntajeMin, leerPuntajeHora;

        try {
            txaJugadores.setText("Nombre" + "\t\t" + "Tiempo\n"
                    + "--------------------------------------------------------\n");
            sc = new Scanner(f);
            while (sc.hasNextLine()) {
                if (m < 5) {
                    leerNombre = sc.nextLine();
                    leerPuntajeHora = Integer.parseInt(sc.nextLine());
                    leerPuntajeMin = Integer.parseInt(sc.nextLine());
                    leerPuntajeSec = Integer.parseInt(sc.nextLine());

                    if (leerPuntajeMin < 10 && leerPuntajeSec < 10) {
                        txaJugadores.setText(txaJugadores.getText() + leerNombre + "\t\t" + "0" + leerPuntajeHora + ":0" + leerPuntajeMin + ":0" + leerPuntajeSec + "\n\n");
                    } else if (leerPuntajeMin < 10 && leerPuntajeSec >= 10) {
                        txaJugadores.setText(txaJugadores.getText() + leerNombre + "\t\t" + "0" + leerPuntajeHora + ":0" + leerPuntajeMin + ":" + leerPuntajeSec + "\n\n");
                    }
                    if (leerPuntajeMin >= 10 && leerPuntajeSec < 10) {
                        txaJugadores.setText(txaJugadores.getText() + leerNombre + "\t\t" + "0" + leerPuntajeHora + ":" + leerPuntajeMin + ":0" + leerPuntajeSec + "\n\n");
                    } else if (leerPuntajeMin >= 10 && leerPuntajeSec >= 10) {
                        txaJugadores.setText(txaJugadores.getText() + leerNombre + "\t\t" + "0" + leerPuntajeHora + ":" + leerPuntajeMin + ":" + leerPuntajeSec);
                    }
                    arregloNombres[m] = leerNombre;
                    arregloHora[m] = leerPuntajeHora;
                    arregloMin[m] = leerPuntajeMin;
                    arregloSec[m] = leerPuntajeSec;
                    m += 1;
                }
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Lectura de datos: " + e);
            JOptionPane.showMessageDialog(this, "Error al leer los datos", "Puzzle Simulación", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlBackground = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblTiempo = new javax.swing.JLabel();
        txtHoras = new javax.swing.JTextField();
        txtMinutos = new javax.swing.JTextField();
        txtSegundos = new javax.swing.JTextField();
        pnlNumeros = new javax.swing.JPanel();
        scpTxaJugadores = new javax.swing.JScrollPane();
        txaJugadores = new javax.swing.JTextArea();
        btnIniciar = new javax.swing.JButton();
        btnAleatorio = new javax.swing.JButton();
        btnOrdenar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Puzzle_Simulación");
        setResizable(false);

        pnlBackground.setBackground(new java.awt.Color(26, 188, 156));

        lblTitulo.setFont(new java.awt.Font("Heiti SC", 0, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Rompecabezas Númerico");

        lblTiempo.setFont(new java.awt.Font("Heiti SC", 0, 16)); // NOI18N
        lblTiempo.setForeground(new java.awt.Color(255, 255, 255));
        lblTiempo.setText("Tiempo >");

        txtHoras.setEditable(false);
        txtHoras.setBackground(new java.awt.Color(26, 188, 156));
        txtHoras.setFont(new java.awt.Font("Heiti SC", 0, 16)); // NOI18N
        txtHoras.setForeground(new java.awt.Color(255, 255, 255));
        txtHoras.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtHoras.setBorder(null);
        txtHoras.setFocusTraversalKeysEnabled(false);
        txtHoras.setFocusable(false);

        txtMinutos.setEditable(false);
        txtMinutos.setBackground(new java.awt.Color(26, 188, 156));
        txtMinutos.setFont(new java.awt.Font("Heiti SC", 0, 16)); // NOI18N
        txtMinutos.setForeground(new java.awt.Color(255, 255, 255));
        txtMinutos.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtMinutos.setBorder(null);
        txtMinutos.setFocusTraversalKeysEnabled(false);
        txtMinutos.setFocusable(false);

        txtSegundos.setEditable(false);
        txtSegundos.setBackground(new java.awt.Color(26, 188, 156));
        txtSegundos.setFont(new java.awt.Font("Heiti SC", 0, 16)); // NOI18N
        txtSegundos.setForeground(new java.awt.Color(255, 255, 255));
        txtSegundos.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtSegundos.setBorder(null);
        txtSegundos.setFocusTraversalKeysEnabled(false);
        txtSegundos.setFocusable(false);

        pnlNumeros.setBackground(new java.awt.Color(26, 188, 156));
        pnlNumeros.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout pnlNumerosLayout = new javax.swing.GroupLayout(pnlNumeros);
        pnlNumeros.setLayout(pnlNumerosLayout);
        pnlNumerosLayout.setHorizontalGroup(
            pnlNumerosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        pnlNumerosLayout.setVerticalGroup(
            pnlNumerosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        scpTxaJugadores.setBackground(new java.awt.Color(26, 188, 156));
        scpTxaJugadores.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mejores Jugadores", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Heiti SC", 1, 16), new java.awt.Color(255, 255, 255))); // NOI18N
        scpTxaJugadores.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scpTxaJugadores.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txaJugadores.setEditable(false);
        txaJugadores.setBackground(new java.awt.Color(26, 188, 156));
        txaJugadores.setColumns(20);
        txaJugadores.setFont(new java.awt.Font("Heiti SC", 0, 15)); // NOI18N
        txaJugadores.setForeground(new java.awt.Color(255, 255, 255));
        txaJugadores.setRows(10);
        scpTxaJugadores.setViewportView(txaJugadores);

        btnIniciar.setBackground(new java.awt.Color(22, 126, 251));
        btnIniciar.setFont(new java.awt.Font("Heiti SC", 0, 16)); // NOI18N
        btnIniciar.setForeground(new java.awt.Color(255, 255, 255));
        btnIniciar.setText("Nuevo Juego");
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });

        btnAleatorio.setBackground(new java.awt.Color(223, 85, 79));
        btnAleatorio.setFont(new java.awt.Font("Heiti SC", 0, 16)); // NOI18N
        btnAleatorio.setForeground(new java.awt.Color(255, 255, 255));
        btnAleatorio.setText("Desordenar");
        btnAleatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAleatorioActionPerformed(evt);
            }
        });

        btnOrdenar.setBackground(new java.awt.Color(230, 126, 34));
        btnOrdenar.setFont(new java.awt.Font("Heiti SC", 0, 16)); // NOI18N
        btnOrdenar.setForeground(new java.awt.Color(255, 255, 255));
        btnOrdenar.setText("Ordenar");
        btnOrdenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdenarActionPerformed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(52, 73, 94));
        btnSalir.setFont(new java.awt.Font("Heiti SC", 0, 16)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(":");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText(":");

        javax.swing.GroupLayout pnlBackgroundLayout = new javax.swing.GroupLayout(pnlBackground);
        pnlBackground.setLayout(pnlBackgroundLayout);
        pnlBackgroundLayout.setHorizontalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBackgroundLayout.createSequentialGroup()
                        .addComponent(lblTiempo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHoras, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMinutos, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSegundos, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlBackgroundLayout.createSequentialGroup()
                        .addComponent(pnlNumeros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnIniciar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAleatorio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnOrdenar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(scpTxaJugadores, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        pnlBackgroundLayout.setVerticalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addGap(18, 18, 18)
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBackgroundLayout.createSequentialGroup()
                        .addComponent(pnlNumeros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTiempo)
                            .addComponent(txtHoras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSegundos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMinutos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)))
                    .addGroup(pnlBackgroundLayout.createSequentialGroup()
                        .addComponent(scpTxaJugadores, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAleatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOrdenar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        getContentPane().add(pnlBackground, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        this.pnlNumeros.removeAll();
        Iniciar();
        this.paintAll(this.getGraphics());
        contador.start();
        Revolver();

        for (i = 0; i < 16; i++) {
            btnBoton[i].addActionListener(this);
        }
    }//GEN-LAST:event_btnIniciarActionPerformed

    private void btnAleatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAleatorioActionPerformed
        Revolver();
    }//GEN-LAST:event_btnAleatorioActionPerformed

    private void btnOrdenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenarActionPerformed
        for (i = 0; i < 16; i++) {
            JButton btnOrdenar;
            btnOrdenar = new JButton();
            btnOrdenar.setBounds(0 + 126 * (i % 4), 0 + 126 * (i / 4), 126, 126);
            x = btnOrdenar.getLocation().x;
            y = btnOrdenar.getLocation().y;
            btnBoton[i].setLocation(x, y);
        }
    }//GEN-LAST:event_btnOrdenarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

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
            java.util.logging.Logger.getLogger(Rompecabezas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Rompecabezas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Rompecabezas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Rompecabezas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Rompecabezas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAleatorio;
    private javax.swing.JButton btnIniciar;
    private javax.swing.JButton btnOrdenar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblTiempo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlBackground;
    private javax.swing.JPanel pnlNumeros;
    private javax.swing.JScrollPane scpTxaJugadores;
    private javax.swing.JTextArea txaJugadores;
    private javax.swing.JTextField txtHoras;
    private javax.swing.JTextField txtMinutos;
    private javax.swing.JTextField txtSegundos;
    // End of variables declaration//GEN-END:variables

}
