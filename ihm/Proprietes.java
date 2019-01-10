package ihm;

import java.awt.Font;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;

import methodes.AfDocument;
import methodes.ExtractionInfoDoc;


public class Proprietes extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel Nom;
	private JLabel Titre;
	private JLabel Lien;
	private JLabel NbrCar;
	private JLabel NbrMot;
	private JLabel NbrMotC;
	private JLabel NbrPhra;
	
	
	public Proprietes(AfDocument d,String Cl){
		
		this.setTitle("clustering Application - " + Cl);
		this.setSize(840,480);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.setVisible(true);
		this.setLayout(null);
		Font f=new Font("tahoma", Font.BOLD, 14);
		JLabel l=new JLabel("Propriété du document qui à été séléctioné :");
		l.setFont(f);
		l.setBounds(20, 20, 400, 40);
		
		ExtractionInfoDoc.EtractInfoSimple(d.getLien());
		Nom =new JLabel("Nom : "+d.getNom() );
		Nom.setFont(f);
		Nom.setBounds(40, 80, 300, 40);
		
		Titre =new JLabel("Titre du Document : "+d.getTitre());
		Titre.setFont(f);
		Titre.setBounds(40,120,300,40);
		
		Lien=new JLabel("Lien du Document : "+d.getLien());
		Lien.setFont(f);
		Lien.setBounds(40, 160, 550, 40);
		
		NbrCar =new JLabel("Nombre de caractéres : "+d.getNbrCar());
		NbrCar.setFont(f);
		NbrCar.setBounds(40, 200, 300, 40);
		
		NbrMot =new JLabel("Nombre de mots : "+d.getNbrMot());
		NbrMot.setFont(f);
		NbrMot.setBounds(40,240,300,40);

		NbrPhra =new JLabel("Nombre de phrases : "+d.getNbrPhra());
		NbrPhra.setFont(f);
		NbrPhra.setBounds(40,280, 300, 40);
		
		this.add(l);
		this.add(Nom);
		this.add(Titre);
		this.add(Lien);
		this.add(NbrCar);
		this.add(NbrMot);
		this.add(NbrPhra);
	}
	
	private static String NomDocument(String Lien){
		File f=new File(Lien);
		return f.getName();
	}
	
}
