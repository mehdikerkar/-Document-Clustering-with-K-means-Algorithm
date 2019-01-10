package ihm;

import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import distances.DistanceCorrelation;
import distances.DistanceCosine;
import distances.DistanceEuclidian;
import distances.DistanceManathan;
import distances.Similarity;
import methodes.Corpus;
import methodes.MotCle;
import methodes.TousTraitement;
import methodes.AfDocument;
import methodes.DistancesMethodes;


public class Distances extends JFrame{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTable Tab;
	private JScrollPane ScPan;
	private DefaultTableModel Model;
	private String ID[];
	private static double MD[][];
	private static double MTf[][];
	Corpus c;
	List<AfDocument> l;
	
	public Distances (TousTraitement all, String distName,String Cl) throws Exception{
		this.setTitle("Clustering Application - " + Cl + " - Distances");
		this.setSize(1280,820);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.setVisible(true);
		this.setLayout(null);

        Model=new DefaultTableModel();
        
        l = all.getdocMapping();
        c = all.getTermMatrix();

        ID=new  String[l.size()+1];
        ID[0]="  ";
        for (int i = 0; i <l.size() ; i++) {
			ID[i+1]=l.get(i).getNom();
		}
        Model.setColumnIdentifiers(ID);
        
        Tab=new JTable(Model);
        ScPan=new JScrollPane(Tab);
        ScPan.setBounds(20, 20, 1220, 680); 
        
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        
        DistancesMethodes dist = new DistancesMethodes(c);
        
        if(MD == null){
        	if (distName.equals("Similarity")) {
    			MD = dist.distanceChoix("Similarity");
    		} else if (distName.equals("Euc")) {
    			System.out.println("je suis dans Euclide");
    			MD = dist.distanceChoix("Euc");
    		}else if(distName.equals("Cos")){
    			System.out.println("je suis dans Cosinus");
	        	MD = dist.distanceChoix("Cos");
			}else if(distName.equals("Man")){
				System.out.println("je suis dans Manhatan");
				MD = dist.distanceChoix("Man");
			} else if (distName.equals("Correlation")) {
				MD = dist.distanceChoix("Correlation");
			}else{
				throw new Exception("there is no such distance \"" + distName + "\"");
			}//MTf = dist.distanceChoix("td-idf");
        }
        //MD = dist.distanceCosin( Tab_test );   
        
        Object row[]=new Object[ID.length];       
        for ( int i = 0; i < MD.length; i++ ) {
        	for ( int j = 0; j < MD.length+1; j++ ) {
				if( j==0 ) {
					row[0]=l.get(i).getNom();
				}
				else { 
					row[j]=  MD[i][j-1];
				}
			}
			Model.addRow(row);
		}
        
        this.add(ScPan);
	}
	
	public String[] getID(){
		return ID;
	}
	public static double[][] getMD(){
		return MD;
	}
}
