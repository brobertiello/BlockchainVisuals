import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class TransactionCostScreen extends Screen {
	
	public TransactionCostScreen() {
		
		//CHECK BOXES
		JCheckBox lowestBox = new JCheckBox();
		lowestBox.setText("Lowest Transaction Cost");
		lowestBox.setSelected(true);
		
		JCheckBox averageBox = new JCheckBox();
		averageBox.setText("Average Transaction Cost");
		averageBox.setSelected(true);
		
		JCheckBox highestBox = new JCheckBox();
		highestBox.setText("Highest Transaction Cost");
		highestBox.setSelected(true);
		
		//CHART ITSELF
		class TransactionCostChart {
			
			ChartPanel cPanel;
			
			public TransactionCostChart() {
				String chartTitle = "Transaction Cost of Blocks";
			    String xAxisLabel = "Block Number";
			    String yAxisLabel = "Transaction Cost (ETH)";
			    
			    XYDataset dataset = createDataset();
			    
			    JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, 
			            xAxisLabel, yAxisLabel, dataset);
			    
			    cPanel = new ChartPanel(chart);
			    cPanel.setPreferredSize(new Dimension(800, 600));
			    //cPanel.setSize(new Dimension(800, 600));
			}
			
			private XYDataset createDataset() {
			    XYSeriesCollection dataset = new XYSeriesCollection();
			    XYSeries series1 = new XYSeries("Lowest Transaction Cost");
			    XYSeries series2 = new XYSeries("Average Transaction Cost");
			    XYSeries series3 = new XYSeries("Highest Transaction Cost");
			    
			    ArrayList<Blocks> blocks = Blocks.getBlocks();
			    
			    for(Blocks b : blocks) {
			    	if(b.getTransactions().size()>0) {
				    	double lowest = b.getTransactions().get(0).transactionCost();
				    	
				    	double total = 0;
				    	double average;
				    	
				    	double highest = 0;
				    	
				    	for(Transaction t : b.getTransactions()) {
				    		double tCost = t.transactionCost();
				    		if(tCost < lowest) {
				    			lowest = tCost;
				    		}
				    		if(tCost > highest) {
				    			highest = tCost;
				    		}
				    		total += tCost;
				    	}
				    	
				    	average = total / b.getTransactionCount();
				    	
				    	series1.add(b.getNumber(), lowest);
				    	series2.add(b.getNumber(), average);
				    	series3.add(b.getNumber(), highest);
			    	}
			    }
			    
			    dataset.addSeries(series1);
			    dataset.addSeries(series2);
			    dataset.addSeries(series3);
			    
			    lowestBox.addItemListener(new ItemListener() {
				    public void itemStateChanged(ItemEvent e) {
				        // check if the check box is selected
				        if (e.getStateChange() == ItemEvent.SELECTED) {
				        	dataset.addSeries(series1);
				        } else {
				        	dataset.removeSeries(series1);
				        }
				    }
				});
				averageBox.addItemListener(new ItemListener() {
				    public void itemStateChanged(ItemEvent e) {
				        // check if the check box is selected
				    	if (e.getStateChange() == ItemEvent.SELECTED) {
				        	dataset.addSeries(series2);
				        } else {
				        	dataset.removeSeries(series2);
				        }
				    }
				});
				highestBox.addItemListener(new ItemListener() {
				    public void itemStateChanged(ItemEvent e) {
				        // check if the check box is selected
				    	if (e.getStateChange() == ItemEvent.SELECTED) {
				        	dataset.addSeries(series3);
				        } else {
				        	dataset.removeSeries(series3);
				        }
				    }
				});
			    
			    return dataset;
			}
			
		}
		
		TransactionCostChart chart = new TransactionCostChart();

        GridBagConstraints c = new GridBagConstraints();
        
        JPanel boxes = new JPanel(new GridBagLayout());
        
        c.gridx = 0;
        c.gridy = 0;
		
		boxes.add(lowestBox, c);
		
		c.gridx = 1;
		
		boxes.add(averageBox, c);
		
		c.gridx = 2;
		
		boxes.add(highestBox, c);
		
		c.gridx = 0;
		panel.add(boxes, c);
		
		c.gridx = 0;
        c.gridy = 1;
        
		panel.add(chart.cPanel, c);
		
	}
	
	public JPanel getJPanel() {
		
		return panel;
		
	}
	
}