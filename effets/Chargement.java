package effets;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;



public class Chargement extends JFrame{
		
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private Thread t;
		private JProgressBar bar;
	    private JLabel label = new JLabel (" Lancement de Clustering Application ");
		
		 public Chargement()
		 {
			 this.setSize(480,100); 
			 this.setLocationRelativeTo(null);
			 this.setUndecorated(true);
			 Font POLICE = new Font("Arial" ,Font.BOLD, 25 );	
			 label.setFont(POLICE); 
			 t = new Thread(new Traitement()); 
			 bar = new JProgressBar();
			 bar.setMaximum(1000);
			 bar.setMinimum(0);
			 bar.setStringPainted(true);
			 
			 this.getContentPane().add(bar, BorderLayout.SOUTH);
			 this.getContentPane().add(label, BorderLayout.CENTER);
			 t.start();
			 this.setVisible(true);
			 
		 }
		 public class Traitement implements Runnable
		 {
			 public void run()
			 {
				 for(int val = 0; val <= 1000; val++)
				 {
					 bar.setValue(val);
					 try
					 {
						 Thread.sleep(7);
					 }catch (InterruptedException e){e.printStackTrace(); }
				 }	
				 
			 }
		 }

}	
	


