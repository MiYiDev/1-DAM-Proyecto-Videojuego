package shooter2d;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Principal extends JPanel implements ActionListener {
    JLabel lblFondo;
    Personaje personaje;
    Zombie zombie;
    Bala bala;
    JButton botonPrueba;
    CrearBala crearbala;
    JLabel lblSangre;

    Frame a;

    MenuEscape mE;
    Menu m;

    // Boolean flagSentidoZombie;

    Timer temporizadorAparecerZombie;

    int localizacionZombie;
    int cantidadZombies;

    boolean flagFacil;
    boolean flagNormal;
    boolean flagDificil;

    boolean flagVelocidadFacil;
    boolean flagVelocidadNormal;
    boolean flagVelocidadDificil;

    boolean flagDisparosFacil;
    boolean flagDisparosNormal;
    boolean flagDisparosDificil;

    public Principal(Frame a, int prueba) {
        setLayout(null);
        this.a = a;

        // imagenesSangre
        for (int i = 1; i <= 10; i++) {
            ImageIcon imagenSangre = new ImageIcon(Principal.class.getResource("/shooter2d/img/sangre" + i + ".png"));
            Image conversionSangre = imagenSangre.getImage();
            Image pasadoSangre = conversionSangre.getScaledInstance(100, 100, Image.SCALE_REPLICATE);
            ImageIcon iconoSangre = new ImageIcon(pasadoSangre);
        }

        // FondoJuego
        ImageIcon imagenFondo = new ImageIcon(Principal.class.getResource("/shooter2d/img/fondo.jpg"));
        Image conversionFondo = imagenFondo.getImage();
        Image pasadoFondo = conversionFondo.getScaledInstance(1920, 1080, Image.SCALE_REPLICATE);
        ImageIcon iconoFondo = new ImageIcon(pasadoFondo);

        // Personaje
        personaje = new Personaje(this);
        personaje.setSize(210, 240);
        personaje.setLocation(300, 650);
        // System.err.println("Fondo: " + personaje.getComponentCount());
        // personaje.setIcon(personaje.iconoPersonaje);
        add(personaje);

        // lblFondo
        lblFondo = new JLabel();
        lblFondo.setSize(1920, 1080);
        lblFondo.setLocation(0, 0);
        lblFondo.setIcon(iconoFondo);
        // System.err.println("Fondo: " + lblFondo.getComponentCount());
        add(lblFondo);

        this.addKeyListener(new ManejadorTeclado());
        this.addMouseListener(new ManejadorRaton());

        crearbala = new CrearBala(this);
        add(crearbala);

        if (prueba == 0) {
            temporizadorAparecerZombie = new Timer(2000, this);
            temporizadorAparecerZombie.start();
            flagVelocidadFacil = true;
            flagDisparosFacil = true;
        }

        if (prueba == 1) {
            temporizadorAparecerZombie = new Timer(1300, this);
            temporizadorAparecerZombie.start();
            flagVelocidadNormal = true;
            flagDisparosNormal = true;

        }

        if (prueba == 2) {
            temporizadorAparecerZombie = new Timer(800, this);
            temporizadorAparecerZombie.start();
            flagVelocidadDificil = true;
            flagDisparosDificil = true;

        }

        this.setFocusable(true);
    }

    public void perder(){
      
    }

    // ===================================================================
    // INNER CLASS MANEJADOR DE EVENTOS DE TECLADO
    // ===================================================================
    private class ManejadorTeclado extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_D && personaje.flagDisparo) {
                personaje.flagDerecha = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_A && personaje.flagDisparo) {
                personaje.flagIzquierda = true;
            }

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                mE = new MenuEscape(Principal.this);
                mE.setSize(1920, 1080);
                mE.setVisible(false);
                a.add(mE);
                mE.setVisible(true);
                Principal.this.setVisible(false);

                Principal.this.temporizadorAparecerZombie.stop();                
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_D) {
                personaje.flagDerecha = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
                personaje.flagIzquierda = false;
            }

        }
    }

    // ===================================================================
    // INNER CLASS MANEJADOR DE EVENTOS DE RATON
    // ===================================================================
    private class ManejadorRaton extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            if (personaje.getIcon() == personaje.iconoPersonaje) {
                personaje.setIcon(personaje.iconoPersonajeDisparo);
            }

            if (personaje.getIcon() == personaje.iconoPersonajeVuelta) {
                personaje.setIcon(personaje.iconoPersonajeDisparoVuelta);
            }
            crearbala.crearBala();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getButton() == 1) {
                personaje.flagDisparo = false;
                if (personaje.getIcon() == personaje.iconoPersonajeDisparo) {
                    personaje.setIcon(personaje.iconoPersonaje);
                }

                if (personaje.getIcon() == personaje.iconoPersonajeDisparoVuelta) {
                    personaje.setIcon(personaje.iconoPersonajeVuelta);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == temporizadorAparecerZombie) {

            localizacionZombie = (int) (Math.random() * 2);

            zombie = new Zombie(Principal.this);
            zombie.setSize(130, 210);
            if (localizacionZombie == 1) {
                zombie.setLocation(-100, 670);
                zombie.setIcon(zombie.iconoZombie);
                // flagSentidoZombie = false;
            } else if (localizacionZombie == 0) {
                zombie.setLocation(1900, 670);
                zombie.setIcon(zombie.iconoZombieVuelta);
                // flagSentidoZombie = true;
            }
            Principal.this.add(zombie, 1);
        }
    }
}
