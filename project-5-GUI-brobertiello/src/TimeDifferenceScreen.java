import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class TimeDifferenceScreen extends Screen {
	
	public TimeDifferenceScreen() {
		
		JTextField blockOne = new JTextField("", 20);
		JTextField blockTwo = new JTextField("", 20);
		JButton addButton = new JButton("Add");
		
		JPanel inputBoxes = new JPanel(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();

		c.gridy = 0;
		
		c.gridx = 0;
		inputBoxes.add(blockOne, c);
		
		c.gridx = 1;
		inputBoxes.add(blockTwo, c);
		
		c.gridx = 2;
		inputBoxes.add(addButton, c);
		
		//CHART
		class DiffChart {
			
			ChartPanel cPanel;
			
			DefaultCategoryDataset dataset = 
				      new DefaultCategoryDataset( ); 
			
			public DiffChart() {
				
				String title = "Time Difference Between Blocks";
				
				JFreeChart barChart = ChartFactory.createBarChart(
			         title,           
			         "Blocks",            
			         "Time Units (hrs, mins, secs)",            
			         dataset,          
			         PlotOrientation.VERTICAL,           
			         true, true, false);
				         
				cPanel = new ChartPanel( barChart );
			    cPanel.setPreferredSize(new Dimension(800, 600));
				
				addButton.addActionListener(new ActionListener() { 
					  public void actionPerformed(ActionEvent e) { 
						
						try {
						    int one = Integer.parseInt(blockOne.getText());
						    int two = Integer.parseInt(blockTwo.getText());
						    try {
								addData(one, two);
							} catch (IOException e2) {
								
							}
						} catch (NumberFormatException e3) {
							
						}
					    
					  } 
					} );
				
			}
			
			private void addData(int first, int second) throws FileNotFoundException, IOException {
				String hours = "hours";
				String minutes = "minutes";
				String seconds = "seconds";
				
				Blocks a = Blocks.getBlockByNumber(first);
				Blocks b = Blocks.getBlockByNumber(second);
				
				if(a==null || b==null) {
					return;
				}
				
				String section = first + " - " + second;
				
				int[] diff = Blocks.getTimeDiff(a, b);
				
				dataset.addValue(diff[0], hours, section);
				dataset.addValue(diff[1], minutes, section);
				dataset.addValue(diff[2], seconds, section);
			}
			
		}
		
		DiffChart d = new DiffChart();
		
		c.gridx = 0;
		
		c.gridy = 0;
		panel.add(inputBoxes, c);
		
		c.gridy = 1;
		panel.add(d.cPanel, c);
		
	}
	
	public JPanel getJPanel() {
		
		return panel;
		
	}
	
}