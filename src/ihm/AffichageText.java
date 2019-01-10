package ihm;

	import javax.swing.JFrame;
	import javax.swing.JScrollPane;
	import javax.swing.JTextArea;

	import methodes.AfDocument;
	
public class AffichageText extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AffichageText(String str){
		this.setTitle("Clustering Application - Affichage Texte Document");
		this.setSize(900,680);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.setVisible(true);
		this.setLayout(null);
		
		JTextArea Text =new JTextArea(str);
		JScrollPane pane=new JScrollPane(Text);
		pane.setBounds(20, 20, 850, 600);
		this.add(pane);
	}


}
