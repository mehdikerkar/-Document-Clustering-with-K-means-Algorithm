package ihm;

import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.plaf.synth.SynthSeparatorUI;
import javax.swing.table.DefaultTableModel;

import Programme.PorterStemmer;
import methodes.AfDocument;
import methodes.ExtractionInfoDoc;

public class AffichMotsCle extends JFrame{

	 JTable Table;
	 JScrollPane ScoPan;
	 DefaultTableModel model;
	 String Id[]={"Mots cle","Mots Sans Stem","Nombre de répétition","Nom document"};
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AffichMotsCle(ArrayList<AfDocument> ld) throws Exception{
		
		this.setTitle("clustering Application - Affichage Mots Cle");
		this.setSize(1100,600);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.setVisible(true);
		this.setLayout(null);
		
		Font f=new Font("tahoma", Font.BOLD, 14);
		JLabel l=new JLabel("Le tableau des Mots Cle : ");
		l.setFont(f);
		l.setBounds(20, 20, 400, 40);
		int i=0,j=0;
		
		model = new DefaultTableModel();
		model.setColumnIdentifiers(Id);
		Table = new JTable(model);
		ScoPan = new JScrollPane(Table);
		ScoPan.setBounds(20, 20, 1040, 520);
		
		StringBuilder strBuild = new StringBuilder();
		
		i=0;
		Object row [] = null;
		HashSet<String> hs = new HashSet();
		while(i<ld.size())
		{	j=0;
		 
			while(j<ld.get(i).getMotCle().size())
			{row =new Object[4];
				row [0]=ld.get(i).getMotCle().get(j).getMot();
				int index = ld.get(i).getMotAvecStem().indexOf(ld.get(i).getMotCle().get(j).getMot());
				Iterator<String> iter = ld.get(i).getMotText().get(index).iterator();
				while(iter.hasNext()){
					strBuild.append(iter.next());
					if(iter.hasNext())
						strBuild.append(",");
				}
				row [1]=strBuild.toString();
				row [2]=ld.get(i).getMotCle().get(j).getNbrF();
				row [3]=ld.get(i).getNom();
				j++;
				model.addRow(row);
				strBuild.setLength(0);
				hs.clear();
			}
			i++;
		}
		this.add(ScoPan);
	}
	
	
}
