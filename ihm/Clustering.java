package ihm;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import methodes.AfDocument;
import methodes.Corpus;
import methodes.Kmeans2;
import methodes.DistancesMethodes;
import methodes.TousTraitement;

public class Clustering extends JFrame{
	
JTable Table;
JScrollPane ScoPan;
DefaultTableModel model;
private int[] ticketal;
	
public Clustering (TousTraitement something,int nbrIter,int nbrC, List<AfDocument> ld, String distName, String CenterChoix, String title) throws Exception{
	this.setTitle(title);
	this.setSize(900,680);
	this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	this.setLocationRelativeTo(null);
	this.setResizable(true);
	this.setVisible(true);
	this.setLayout(null);
	
	double [][] MT;
	/*DistancesMethodes dist = new DistancesMethodes(something.getTermMatrix(), distName);
	double [][] MT = dist.distanceChoix("td-idf");
	
	for(int index = 0; index < something.getTermMatrix().getListDocSize(); index++){
		dist.normalize(MT[index], index);
	}
	MT = dist.getMTf();
	*/
	MT = something.getTermMatrix().getMTf();
	
	Kmeans2 km = new Kmeans2(something, MT, nbrC, something.getTermMatrix().getTermsSize(), something.getTermMatrix().getListDocSize(), CenterChoix, distName);
	int i,j;
	
	System.out.println("check 0!");
	km.clustering(nbrC, nbrIter, null);
	System.out.println("check 1!");
	//km.printResults();
	System.out.println("ckeck 2");
		 
	ticketal = km.getTicket();
	model = new DefaultTableModel();
	Table = new JTable(model);
	ScoPan = new JScrollPane(Table);
	ScoPan.setBounds(20, 20, 800, 500);
			
	i=0;
	Object columns [] = null;
	int k;		
	while(i<nbrC)
	{	
		k = 0;
		j=0;		 
		//System.out.println("le cluster n°" +i);
		columns =new Object[ticketal.length];
		while(j<ticketal.length)
		{
			if(ticketal[j]==i){
				columns [k]=ld.get(j).getNom();
				k++;
			}
			//System.out.println("doc "+j+" : "+ ld.get(j).getNom());
			j++;
		}
		model.addColumn("Cluster " + i, columns);
		i++;
	}		
	this.add(ScoPan);
	}
}
