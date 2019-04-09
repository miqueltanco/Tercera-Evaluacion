package Arkanoid;  

import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton; 
import javax.swing.JFrame; 
import javax.swing.JLabel; 
import javax.swing.JOptionPane; 
import javax.swing.JTextField; 

public class prueba extends JFrame implements ActionListener { 

	private JLabel text;  // etiqueta o text no editable 
	private JTextField caixa;  // caixa de text, per inserir dades 
	private JButton bot�;  // bot� amb una determinada acci� 

	public prueba () { 
		super ();  // fem servir el contructor de la classe pare JFrame 
		configurarVentana ();  // configurem la finestra 
		inicializarComponentes ();  // inicialitzem els atributs o components 
	} 

	private void configurarVentana () { 
		this.setTitle ( "Aquesta �s Una Finestra");  // col�loquem t�tol a la finestra 
		this.setSize (310, 210);  // col�loquem tamanio a la finestra (ample, alt) 
		this.setLocationRelativeTo (null);  // centrem la finestra a la pantalla 
		this.setLayout (null);  // no fem servir cap layout, nom�s aix� podrem donar posicions als components 
		this.setResizable (false);  // fem que la finestra no sigui redimiensionable 
		this.setDefaultCloseOperation (JFrame. EXIT_ON_CLOSE);  // fem que quan es tanqui la finestra acaba tot proc�s 
	} 

	private void inicializarComponentes () { 
		// creem els components 
		text = new JLabel (); 
		caixa = new JTextField (); 
		bot� = new JButton (); 
		// configurem els components 
		text.  setText ( "Inseriu Nom");  // col�loquem un text a l'etiqueta 
		text.  setBounds (50, 50, 100, 25);  // col�loquem posici� i tamanio al text (x, y, ample, alt) 
		caixa.  setBounds (150, 50, 100, 25);  // col�loquem posici� i tamanio a la caixa (x, y, ample, alt) 
		bot�.  setText ( "Mostra Missatge");  // col�loquem un text al bot� 
		bot�.  setBounds (50, 100, 200, 30);  // col�loquem posici� i tamanio al bot� (x, y, ample, alt) 
		bot�.  addActionListener (this);  // fem que el bot� tingui una acci� i aquesta acci� estar� en aquesta classe 
		// afegim els components a la finestra 
		this.  add (text); 
		this.  add (caixa); 
		this.  add (bot�); 
	} 
	
	@Override 
	public void actionPerformed (ActionEvent e) { 
		
		String CORAZON = "\\Items\\corazon.png";
		
		ImageIcon corazon = new ImageIcon(new ImageIcon(getClass().getResource(CORAZON)).getImage());	
		
		ArrayList<ImageIcon> listaImagenes= new ArrayList<ImageIcon>(); 
		
		listaImagenes.add(corazon);
		
		String nom = caixa.  getText ();  // obtenim el contingut de la caixa de text 
		JOptionPane.  showMessageDialog (this, "Hola " + listaImagenes.get(0));  // mostrem un missatge (frame, missatge) 
	} 

	public static void main (String [] args) { 
		prueba V = new prueba ();  // creem una finestra 
		V.  setVisible (true);  // fem visible la finestra creada 
	} 
} 