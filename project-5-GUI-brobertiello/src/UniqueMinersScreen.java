import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class UniqueMinersScreen extends Screen {
	
	public UniqueMinersScreen() throws FileNotFoundException, IOException {
		
		ArrayList<Blocks> blocks = Blocks.getBlocks();
		
		int min = blocks.get(0).getNumber();
		int max = blocks.get(blocks.size()-1).getNumber();
		
		JSlider minSlider = new JSlider(min, max);
		JSlider maxSlider = new JSlider(min+1, max);
        
        minSlider.setPaintTicks(true);
        maxSlider.setPaintTicks(true);
        
        minSlider.setPaintLabels(true);
        maxSlider.setPaintLabels(true);
        
        minSlider.setMajorTickSpacing(minSlider.getMaximum() - minSlider.getMinimum());
        maxSlider.setMajorTickSpacing(maxSlider.getMaximum() - maxSlider.getMinimum());
        
        minSlider.setMinorTickSpacing(1);
        maxSlider.setMinorTickSpacing(1);
        
        minSlider.setSnapToTicks(true);
        maxSlider.setSnapToTicks(true);
		
		minSlider.addChangeListener(new ChangeListener() {
		      public void stateChanged(ChangeEvent event) {
		        int minimum = minSlider.getValue();
		        maxSlider.setMinimum(minimum+1);
		        
		        maxSlider.setMajorTickSpacing(maxSlider.getMaximum() - maxSlider.getMinimum());
		      }
		    });
		
		JPanel sliders = new JPanel(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		
		sliders.add(minSlider, c);
		
		c.gridx = 1;
		
		sliders.add(maxSlider, c);
		
		//PIE CHART
		class PieChartPanel {
			
			ChartPanel cPanel;
			
			public PieChartPanel() throws FileNotFoundException, IOException {
				String chartTitle = "Transaction Cost of Blocks";
				
				PieDataset pdata = createDataset(min, max);
				
				JFreeChart chart = ChartFactory.createPieChart(chartTitle, pdata, true, true, false);
			    
			    cPanel = new ChartPanel(chart);
			    cPanel.setPreferredSize(new Dimension(800, 600));
			    
			    minSlider.addChangeListener(new ChangeListener() {
				      public void stateChanged(ChangeEvent event) {
				        int minimum = minSlider.getValue();
				        int maximum = maxSlider.getValue();
				        
				        PieDataset newData = pdata;
				        
						try {
							newData = createDataset(minimum, maximum);
						} catch (FileNotFoundException e) {
							
						} catch (IOException e) {
							
						}
				        
				        JFreeChart newChart = ChartFactory.createPieChart(chartTitle, newData, true, true, false);
				        
				        cPanel.setChart(newChart);
				        
				      }
				    });
				
				maxSlider.addChangeListener(new ChangeListener() {
				      public void stateChanged(ChangeEvent event) {
				    	  int minimum = minSlider.getValue();
					        int maximum = maxSlider.getValue();
					        
					        PieDataset newData = pdata;
					        
							try {
								newData = createDataset(minimum, maximum);
							} catch (FileNotFoundException e) {
								
							} catch (IOException e) {
								
							}
					        
					        JFreeChart newChart = ChartFactory.createPieChart(chartTitle, newData, true, true, false);
					        
					        cPanel.setChart(newChart);
				      }
				    });
			    
			}
			
			private static PieDataset createDataset(int min, int max) throws FileNotFoundException, IOException {
				DefaultPieDataset dataset = new DefaultPieDataset( );
				//dataset.setValue( "IPhone 5s" , new Double( 20 ) );
		      
				HashMap<String, Integer> freq = Blocks.getUniqCount(min, max);
				for(String s : freq.keySet()) {
					dataset.setValue(s, freq.get(s));
				}
		      
				return dataset;         
			}
			
		}
		
		PieChartPanel pcPanel = new PieChartPanel();
		
		c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		panel.add(sliders, c);
		
		c.gridy = 1;
		panel.add(pcPanel.cPanel, c);
		
	}
	
	public JPanel getJPanel() {
		
		return panel;
		
	}
	
}