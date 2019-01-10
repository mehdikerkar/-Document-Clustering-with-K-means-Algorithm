package ihm;

import java.awt.Color;
	import java.awt.Dimension;
	import java.awt.GradientPaint;
	import java.util.List;

	import javax.swing.JFrame;

	import org.jfree.chart.ChartFactory;
	import org.jfree.chart.ChartPanel;
	import org.jfree.chart.JFreeChart;
	import org.jfree.chart.axis.CategoryAxis;
	import org.jfree.chart.axis.CategoryLabelPositions;
	import org.jfree.chart.axis.NumberAxis;
	import org.jfree.chart.plot.CategoryPlot;
	import org.jfree.chart.plot.PlotOrientation;
	import org.jfree.chart.renderer.category.BarRenderer;
	import org.jfree.data.category.CategoryDataset;
	import org.jfree.data.category.DefaultCategoryDataset;
	import org.jfree.ui.ApplicationFrame;

	import methodes.AfDocument;
	
public class Stat extends JFrame{
		
		
		private List<AfDocument> liste;
		
		public Stat(List <AfDocument>  l,String title){
			this.setTitle(title);
			this.setSize(900,680);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setLocationRelativeTo(null);
			this.setResizable(true);
			this.setVisible(true);
			this.setLayout(null);
			liste=l;
			CategoryDataset data =createDataset();
			JFreeChart chartbar=CreatChart(data);
			ChartPanel pane=new ChartPanel(chartbar);
	        pane.setPreferredSize(new Dimension(900, 650));
	        setContentPane(pane);
	        
			this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		}
		
		
		public DefaultCategoryDataset createDataset(){
			
			
			String categori[]=new String[liste.size()];
			for(int i=0;i<liste.size();i++){
				categori[i]=liste.get(i).getNom();
			}
			DefaultCategoryDataset data=new DefaultCategoryDataset();
			for (int i = 0; i < categori.length; i++) {
				
					data.addValue(liste.get(i).getNbrCar(), "Caractéres", liste.get(i).getNom());
					data.addValue(liste.get(i).getNbrMot(), "Mots", liste.get(i).getNom());
					data.addValue(liste.get(i).getNbrPhra(), "Phrases", liste.get(i).getNom());
				
			}
			
			return data;
			
			
		}
		public JFreeChart CreatChart(CategoryDataset d){
			
			JFreeChart chartbar=ChartFactory.createBarChart("Statistique des documents",
															"Catégorie", 
															"Valeur", 
															d, 
															PlotOrientation.VERTICAL,
															true, 
															true, 
															false);

			chartbar.setBackgroundPaint(Color.WHITE);
			
			CategoryPlot plot = chartbar.getCategoryPlot();
	        plot.setBackgroundPaint(Color.lightGray);
	        plot.setDomainGridlinePaint(Color.white);
	        plot.setRangeGridlinePaint(Color.white);
	        
	        
	        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	        
	        BarRenderer renderer = (BarRenderer) plot.getRenderer();
	        renderer.setDrawBarOutline(false);
	        
	        GradientPaint gp0 = new GradientPaint(
	                0, 0, Color.blue, 
	                0, 0, Color.lightGray
	            );
	        GradientPaint gp1 = new GradientPaint(
	                0, 0, Color.green, 
	                0, 0, Color.lightGray
	            );
	        GradientPaint gp2 = new GradientPaint(
	                0, 0, Color.red, 
	                0, 0, Color.lightGray
	            );
	        
	        renderer.setSeriesPaint(0, gp0);
	        renderer.setSeriesPaint(1, gp1);
	        renderer.setSeriesPaint(2, gp2);
	        
	        CategoryAxis domainAxis = plot.getDomainAxis();
	        domainAxis.setCategoryLabelPositions(
	            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
	        );
	        
	        
	        
			return chartbar;	
			
		}
		

}


