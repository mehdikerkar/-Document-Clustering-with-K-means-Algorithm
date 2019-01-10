package ihm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

//import com.sun.glass.ui.View;

import methodes.AfDocument;

public class Parametre extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ButtonGroup lBgl,lBgd;
	private JRadioButton En, Ar, Fr, Euc, Cos, Man;
	private JLabel Cd, Cl, Ni, Nc,Mf;
	private String n;
	private AfDocument dretour;
	private JTextField TfNi,TfNc ;
	private JButton ok;
	private Graphics g;
	
	public Parametre() {
		this.setTitle("clustering Application - paramètre");
		this.setSize(780,450);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		this.setLayout(null);
		
	//	g = null;
		//paintcomponent(g);
	
		
		AfDocument dretour =  new AfDocument("");
		Font f=new Font("tahoma", Font.BOLD, 14);
		
		///------------------------------------JLabel--------------------------------------------------------
		Cd=new JLabel("Choix de la distance :");
		Cd.setFont(f);
		Cd.setBounds(20, 200, 300, 40);
		
		Cl=new JLabel("Choix de la langue des documents :");
		Cl.setFont(f);
		Cl.setBounds(20, 20, 300, 40);
		
		Ni=new JLabel("Choix du nombre d'itération maximum voulus : ");
		Ni.setFont(f);
		Ni.setBounds(350, 100, 400, 40);
		
		
		Nc=new JLabel("Choix du nombre de Cluster voulus : ");
		Nc.setFont(f);
		Nc.setBounds(350, 180, 300, 40);
		
		Mf=new JLabel("Meilleur formule  Distance : Cosinus  les centroids : non aléatoire");
		Mf.setFont(f);
		Mf.setBounds(20, 400, 300, 40);
		
		///------------------------------------------JTextField--------------------------------------------------------
		JTextField TfNc = new JTextField();
		TfNc.setFont(f);
		TfNc.setBounds(380, 220, 60, 40);
		//TfNc.getText().matches("[0-9]*" );
		
		JTextField TfNi = new JTextField();
		TfNi.setFont(f);
		TfNi.setBounds(380, 140, 60, 40);
		//TfNi.getText().matches("[0-9]*" );
		
		///----------------------------------------JRadioButton--------------------------------------------------------
		Euc = new JRadioButton("Distance Euclidienne");
		Euc.setFont(f);
		Euc.setBounds(40, 280, 170, 40);
		
		Cos = new JRadioButton("Distance Cosinus");
		Cos.setFont(f);
		Cos.setBounds(40, 240, 150, 40);
		Cos.setSelected(true);
		
		Man = new JRadioButton("Distance Manhattan");
		Man.setFont(f);
		Man.setBounds(40, 320, 170, 40);
		
		
		
		Fr = new JRadioButton("Francais");
		Fr.setFont(f);
		Fr.setBounds(40, 60, 100, 40);
		Fr.setSelected(true);
		
		En = new JRadioButton("Anglais");
		En.setFont(f);
		En.setBounds(40, 100, 100, 40);
		
		Ar = new JRadioButton("Arabe");
		Ar.setFont(f);
		Ar.setBounds(40, 140, 100, 40);
		Ar.setEnabled(false);
		
		
		///----------------------------------------JButton--------------------------------------------------------
		
		ok = new JButton(" OK ");
		ok.setFont(f);
		ok.setBounds(600, 340, 80, 40);
		//ok.addActionListener(this);
		
		lBgl = new ButtonGroup();
		lBgl.add(Fr);
		lBgl.add(En);
		lBgl.add(Ar);
		
		lBgd = new ButtonGroup();
		lBgd.add(Euc);
		lBgd.add(Man);
		lBgd.add(Cos);
		
		//listCbL.add(Ar);
		//ActionBouton();
		
		//g = null;
		//g.drawLine(200, 50,  200, 400);
		
		getContentPane().setLayout(null);
		getContentPane().add(Fr);
		getContentPane().add(En);
		getContentPane().add(Ar);
		getContentPane().add(Man);
		getContentPane().add(Euc);
		getContentPane().add(Cos);
		getContentPane().add(Cd);
		getContentPane().add(Cl);
		getContentPane().add(Ni);
		getContentPane().add(Nc);
		getContentPane().add(TfNc);
		getContentPane().add(TfNi);
		getContentPane().add(ok);
		//getContentPane().getGraphics();

		
		

		ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				if(e.getSource() == ok){
					System.out.println("TfNi = " + TfNi.getText()  + " TfNc = " + TfNc.getText());
						if (!TfNi.getText().matches("^[0-9]+" ))
							{JOptionPane.showMessageDialog(null,"Nombre d'itérations faux ! "+ TfNi.getText(), "Error", JOptionPane.ERROR_MESSAGE);
							//JOptionPane.showMessageDialog(null,"Nombre d'itérations vide !", "Error", JOptionPane.ERROR_MESSAGE);
							}
			  
						if (!TfNc.getText().matches("^[0-9]+" ))
						{
							JOptionPane.showMessageDialog(null, "Nombre de clusters faux ! "+TfNc.getText(), "Error", JOptionPane.ERROR_MESSAGE);
							//JOptionPane.showMessageDialog(null,"Nombre de clusters vide !", "Error", JOptionPane.ERROR_MESSAGE);
						}
						
						if(TfNi.getText().matches("^[0-9]+" ) && TfNc.getText().matches("^[0-9]+" )){
						
							if (Fr.isSelected()) 
							{
								HomeFrame.setCl("Fr");
							}
							else
							{
								HomeFrame.setCl("En");
							}
							
							if (Cos.isSelected()) 
							{
								HomeFrame.setCd("Cos");
							}
							else 
							{	
								if(Euc.isSelected())
									HomeFrame.setCd("Euc");
								else
									HomeFrame.setCd("Man");	
							}
							
							HomeFrame.setNc(Integer.parseInt(TfNc.getText()));
							HomeFrame.setNi(Integer.parseInt(TfNi.getText()));
							
							HomeFrame.Pframe.setVisible(false);
							Parametre.setDefaultLookAndFeelDecorated(isEnabled());
							
							//HomeFrame.frame.setEnabled(true);
							//HomeFrame.Pframe.setVisible(false);
							
							//HomeFrame.frame.setVisible(true);
							if(HomeFrame.frame != null)
								HomeFrame.frame.dispose();
							HomeFrame.frame = new HomeFrame();
							/*if(HomeFrame.frame == null){
								System.out.println("HomeFrame.frame in parametre.java  avant null :" + HomeFrame.frame);
								HomeFrame.frame = new HomeFrame();
								System.out.println("HomeFrame.frame in parametre.java  apres no null distance :" +HomeFrame.frame.getCd());
								System.out.println("HomeFrame.frame in parametre.java  after null :" + HomeFrame.frame);
							}
							else{
								System.out.println("HomeFrame.frame in parametre.java  avant no null :" + HomeFrame.frame);
								System.out.println("HomeFrame.frame in parametre.java  avant no null distance :" +HomeFrame.frame.getCd());
								HomeFrame.frame.setVisible(true);
								System.out.println("HomeFrame.frame in parametre.java  after no null :" + HomeFrame.frame);
							}*/
							
							//HomeFrame.frame.setVisible(true);
							
						}
				}
				
			}
		});	
}

	/*
	JFrame.HIDE_ON_CLOSE.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
		
		new Parametre();
		frame.setVisible(true);
		
		
		}
	});
	*/
	public void paintcomponent(Graphics g) {
        super.paintComponents(g);
        g.drawLine(400, 50, 400, 300);
    }
	public AfDocument getdretour(AfDocument dFr, AfDocument dAr , AfDocument dEn) {
	/*		
			switch (Select(Ar, Fr, En)) {
			case "ar":
				dretour=dAr;
				//returnLang(dAr);
				break;
			case "en":
				dretour=dEn;
				//returnLang(dEn);
				break;
			case "fr":
				dretour=dFr;
				//returnLang(dFr);
				break;
			}
			*/
		return dretour;
	

		}
	private String Select(JCheckBox ar, JCheckBox fr, JCheckBox en) {
		if (ar.isSelected())
			return "ar";
		else 
			{
			if (en.isSelected())
				return "en";
			else 
				return "fr";
			}
	}
	public static AfDocument returnLang(AfDocument d){
		
		return d;
	}
	/*
	public static void main(String[] args){
		new Parametre();
 }
 */
}

 