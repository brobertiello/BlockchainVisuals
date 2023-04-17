import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	JPanel panel;
	JComboBox<String> menu;
	JPanel screen;
	
	HashMap<String, Screen> screens = new HashMap<String, Screen>();
	
	public Window() throws FileNotFoundException, IOException {
		
		//set window title
		setTitle("Project 5 - Brandon Robertiello");
		
		//set window close operation
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    //set panel
	    panel = new JPanel(new GridBagLayout());
	    add(panel);
	    
	    //set gridbag
        GridBagConstraints c = new GridBagConstraints();
	    
	    //set menu
	    String[] slides = {"-select chart-", "Unique Miners", "Transaction Cost", "Time Difference"};
	    menu = new JComboBox<String>(slides);
	    
	    //add menu
	    c.gridx = 0;
	    c.gridy = 0;
	    panel.add(menu, c);
	    
	    //add screens
	    screens.put("-select chart-", new Screen());
	    screens.put("Unique Miners", new UniqueMinersScreen());
	    screens.put("Transaction Cost", new TransactionCostScreen());
	    screens.put("Time Difference", new TimeDifferenceScreen());
	    
	    c.gridy = 1;
	    
	    for(Screen s : screens.values()) {
	    	panel.add(s.getJPanel(), c);
	    	s.getJPanel().setVisible(false);
	    }
	    
	    menu.addItemListener(new ItemListener() {
	    	  public void itemStateChanged(ItemEvent e) {
	    		  
	    	    // Get the selected item from the JComboBox
	    	    String selectedItem = (String)menu.getSelectedItem();
	    	    
	    	    screen.setVisible(false);
	    	    
	    	    screen = screens.get(selectedItem).getJPanel();
	    	    
	    	    screen.setVisible(true);
	    	    
	    	  }
	    	});
	    
	    //set screen
	    screen = screens.get("-select chart-").getJPanel();
	    screen.setVisible(true);
	    
	    //finalize window
	    setSize(1600, 800);
	    setVisible(true);
		
	}
	
}