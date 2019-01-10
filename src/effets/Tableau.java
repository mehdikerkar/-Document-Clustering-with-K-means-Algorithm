package effets;
	import java.awt.Color;
	import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
	import javax.swing.table.DefaultTableCellRenderer;
	
public class Tableau extends DefaultTableCellRenderer{
	
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		 Component combo;

		public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column) 
		{
			Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			
				if (column == 0 )
			
				{	/*cell.setBackground(Color.GRAY);
					combo = null;
					combo.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 16));*/
					Color c=new Color(200,196,196);
					cell.setBackground(c);
				}
				else
					cell.setBackground(Color.WHITE);
				
				
				return cell;
	}
				
			
		
}
