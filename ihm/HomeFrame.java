package ihm;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import ihm.Stat;
import effets.Tableau;
import methodes.AfDocument;
import methodes.TousTraitement;


public class HomeFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton AjoutBtn;
	private JButton DistBtn;
	private JButton StatBtn;
	private JButton OuvrirBtn;
	private JButton MotCleBtn;
	private JButton ClusterBtn;
	private JButton PropBtn;
	private JButton AffichBtn;
	private JButton ParamBtn;
	private JLabel LienLabel;
	private JLabel LNbrI;
	private JLabel LNbrC;
	private JLabel LCd;
	private JLabel Logo;
	private JTextField LienText;

	private static int nbrIter;
	private static int nbrC;
	private static String langChoix;
	private static String distName;

	private JTable Table;
	private JScrollPane ScoPan;
	private DefaultTableModel model;
	private String Id[] = { "Numéro du doc", "Nombre de ligne", "Nom", "Titre" };

	// private List<TextDoc> list=new ArrayList<TextDoc>();
	/*
	 * private static List<AfDocument> list=new ArrayList<AfDocument>(); public
	 * static List<AfDocument> getList() { return list; }
	 */
	
	private JPanel contentPane;
	static HomeFrame frame;
	static Parametre Pframe;

	public static void main(String[] args) {

		/*
		 * Chargement Intro = new Chargement(); Intro.setVisible(true); try {
		 * 
		 * Thread.sleep(7500); Intro.setVisible(false); } catch
		 * (InterruptedException e) { e.printStackTrace(); }
		 */

		EventQueue.invokeLater(new Runnable() {

			public void run() {

				try {
					//frame = new HomeFrame();
					Pframe =  new Parametre();				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the frame.
	 */
	public HomeFrame() {
		this.setTitle("Clustering Application - " + langChoix);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);

		CreationComposants();
		LocationComposants();
		ModificationCompsants();
		ActionsComposants();
		AjoutComposants();
		
	}

	public void CreationComposants() {
		LienLabel = new JLabel("Lien du document:");
		LienText = new JTextField();

		AjoutBtn = new JButton("Ajouter", null);

		Logo = new JLabel("Logo + Sarl KMKJ");
		ParamBtn = new JButton("Paramètre", null);
		OuvrirBtn = new JButton("Ouvrir...", null);
		PropBtn = new JButton("Propriété", null);
		LNbrI = new JLabel("Nbr Itération : " + nbrIter);
		LNbrC = new JLabel("Nbr Clusters : " + nbrC);
		if (distName == "Euc")
			LCd = new JLabel("Distance : Euclidienne");
		else {
			if (distName == "Man")
				LCd = new JLabel("Distance : Manhattan");
			else
				LCd = new JLabel("Distance : Cosinus");
		}
		AffichBtn = new JButton("Voir texte", null);
		StatBtn = new JButton("Statistique", null);
		MotCleBtn = new JButton("Mots Cle", null);
		DistBtn = new JButton("Distance", null);
		ClusterBtn = new JButton("Démmarer Clustering", null);

		model = new DefaultTableModel();
		model.setColumnIdentifiers(Id);
		Table = new JTable(model);
		ScoPan = new JScrollPane(Table);
	}

	public void LocationComposants() {
		LienLabel.setBounds(50, 20, 300, 40);
		LienText.setBounds(20, 65, 820, 40);
		OuvrirBtn.setBounds(860, 65, 100, 40);

		ParamBtn.setBounds(30, 125, 120, 40);
		AjoutBtn.setBounds(170, 125, 120, 40);
		PropBtn.setBounds(310, 125, 120, 40);
		LNbrI.setBounds(500, 125, 120, 40);
		LNbrC.setBounds(640, 125, 120, 40);
		LCd.setBounds(780, 125, 160, 40);

		AffichBtn.setBounds(30, 580, 120, 40);
		StatBtn.setBounds(170, 580, 120, 40);
		MotCleBtn.setBounds(310, 580, 120, 40);
		DistBtn.setBounds(450, 580, 120, 40);
		ClusterBtn.setBounds(750, 580, 200, 40);

		Logo.setBounds(5, 645, 150, 20);

		ScoPan.setBounds(20, 190, 940, 365);
	}

	public void ModificationCompsants() {
		Font f = new Font("Arial", Font.BOLD, 14);
		Color c = new Color(200, 196, 196);
		LienLabel.setFont(f);
		LienText.setFont(f);
		LienText.setEnabled(true);
		LienText.setEditable(false);
		Table.setDefaultRenderer(Object.class, new Tableau());

		OuvrirBtn.setFont(f);
		ParamBtn.setFont(f);
		AjoutBtn.setFont(f);
		PropBtn.setFont(f);
		LNbrC.setFont(f);
		LNbrI.setFont(f);
		LCd.setFont(f);

		StatBtn.setFont(f);
		AffichBtn.setFont(f);
		MotCleBtn.setFont(f);
		DistBtn.setFont(f);
		ClusterBtn.setFont(f);
	}

	ArrayList<String> docNom = new ArrayList<String>();
	TousTraitement traitement;
	private boolean dejaCalculerM = false;
	private boolean dejaCalculerD = false;
	private int nbrRow = 0;
	private HashSet<String> name = new HashSet<String>();

	double minSignificantValue = 1.2;    // le minimum pour eliminer les mots
	String CenterChoix = "random";       // first or last or random

	public void ActionsComposants() {

		OuvrirBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FileFilter filter = new FileNameExtensionFilter("Texte", "txt");
				FileFilter filter2 = new FileNameExtensionFilter("Document Word", "Docx");
				JFileChooser ch = new JFileChooser();
				ch.setMultiSelectionEnabled(true);
				ch.setAcceptAllFileFilterUsed(false);
				ch.setFileFilter(filter2);
				ch.setFileFilter(filter);
				File[] fs = null;
				if (ch.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					// des fichiers ont été choisis
					// (sortie par OK)
					// JFileChooser choixF= ch;
					fs = ch.getSelectedFiles();
					for (int i = 0; i < fs.length; ++i) {
						docNom.add(fs[i].getAbsolutePath());
					}
				} // if
				StringBuilder str = new StringBuilder();
				for (int i = 0; i < docNom.size(); i++) {
					str.append("\"");
					str.append(fs[i].getName());
					str.append("\" ");
				}
				System.out.println(str);
				LienText.setText(str.toString());
			}
		});

		AffichBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String str;
				int i = Table.getSelectedRow();

				if (i < 0) {
					JOptionPane.showMessageDialog(null, "Aucun document n'est séléctioné", "erreur", 0);
				} else {
					str = traitement.getdocMapping().get(i).getText();
					new AffichageText(str);
				}
			}
		});

		ParamBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//frame.setEnabled(false);
				if(Pframe == null){
					Pframe = new Parametre();
					System.out.println("je suis dans null");
					//frame.setEnabled(false);
				}
				else{
					Pframe.setVisible(true);
					System.out.println("je ne suis pas dans null");
					//frame.setEnabled(false);
				}
					// HomeFrame.frame.setEnabled(false);
				// new Parametre();
				// frame.setVisible(false);

			}
		});

		AjoutBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (LienText.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Le lien est vide", "Erreur", 0);
				} else {
					try {
						traitement = new TousTraitement(docNom);
						traitement.simpleExtraction();
						dejaCalculerM = false;
						dejaCalculerD = false;
						ArrayList<AfDocument> list = traitement.getdocMapping();
						for (int i = 0; i < docNom.size(); ++i) {
							Object row[] = new Object[4];
							row[0] = nbrRow;
							row[1] = traitement.getdocMapping().get(i).getNbrLigne();
							row[2] = traitement.getdocMapping().get(i).getNom();
							row[3] = traitement.getdocMapping().get(i).getTitre();
							model.addRow(row);
							nbrRow++;
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

		});

		PropBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = Table.getSelectedRow();
				if (i < 0) {
					JOptionPane.showMessageDialog(null,
							" Aucun document n'est séléctioné \n    Vous étes obligé de choisire un document dans la table");
				} else {
					new Proprietes(traitement.getdocMapping().get(i), langChoix);
				}
			}
		});

		MotCleBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (traitement.getdocMapping().isEmpty())
					JOptionPane.showMessageDialog(null, "List vide !", "Erreur", 0);
				else {
					try {
						if (!dejaCalculerM) {
							traitement.advancedExtraction(minSignificantValue, langChoix);
							dejaCalculerM = true;
						}
						new AffichMotsCle(traitement.getdocMapping());

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		DistBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (traitement.getdocMapping().isEmpty())
					JOptionPane.showMessageDialog(null, "List vide !", "Erreur", 0);
				else {
					try {
						if (!dejaCalculerD) {
							traitement.advancedExtraction(minSignificantValue, langChoix);
							dejaCalculerD = true;
						}
						new Distances(traitement, distName, langChoix);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		StatBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (traitement.getdocMapping().isEmpty())
					JOptionPane.showMessageDialog(null, "List vide !", "Erreur", 0);
				else {
					Stat s = new Stat(traitement.getdocMapping(), "Clustering Application - " + langChoix + " - Statistique");
					s.pack();
					RefineryUtilities.centerFrameOnScreen(s);
					s.setVisible(true);
					s.setDefaultCloseOperation(ApplicationFrame.HIDE_ON_CLOSE);

				}
			}
		});
		ClusterBtn.addActionListener(new ActionListener() {

			private Clustering clus;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (traitement.getdocMapping().isEmpty())
					JOptionPane.showMessageDialog(null, "List vide !", "Erreur", 0);
				else if (docNom.size() < 2)
					JOptionPane.showMessageDialog(null, "Au moin 2 document pour faire le Clustering !", "Erreur", 0);
				else {
					if (!dejaCalculerM && !dejaCalculerD)
						try {
							traitement.advancedExtraction(minSignificantValue, langChoix);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					try {
						clus = new Clustering(traitement, nbrIter, nbrC, traitement.getdocMapping(), distName, CenterChoix,
								"Clustering Application - " + langChoix + " - Resultat du clustering");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});
	}

	public void AjoutComposants() {
		getContentPane().setLayout(null);
		getContentPane().add(LienLabel);
		getContentPane().add(LienText);
		getContentPane().add(OuvrirBtn);

		getContentPane().add(ParamBtn);
		getContentPane().add(AjoutBtn);
		getContentPane().add(PropBtn);
		getContentPane().add(LNbrI);
		getContentPane().add(LNbrC);
		getContentPane().add(LCd);
		getContentPane().add(AffichBtn);
		this.add(ScoPan);
		getContentPane().add(StatBtn);
		getContentPane().add(MotCleBtn);
		getContentPane().add(DistBtn);
		getContentPane().add(ClusterBtn);

		getContentPane().add(Logo);
	}
	
	public int getNi() {
		return nbrIter;
	}

	public static void setNi(int ni) {
		nbrIter = ni;
	}

	public static int getNc() {
		return nbrC;
	}

	public static void setNc(int nc) {
		nbrC = nc;
	}

	public static String getCl() {
		return langChoix;
	}

	public static void setCl(String cl) {
		langChoix = cl;
	}

	public String getCd() {
		return distName;
	}

	public static void setCd(String cd) {
		distName = cd;
	}

}
