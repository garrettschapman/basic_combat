import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CreatureSelector extends JFrame {
	private JPanel panel = new JPanel();
	private File creatureInfo;
	
	private ArrayList<String> creatures;
	private JList<String> listOfCreatures;
	private DefaultListModel<String> listModel = new DefaultListModel<>();
	
	private JScrollPane scroll;
	private SelectionListener selectionListener = new SelectionListener();

	public CreatureSelector(String creatureList) {
		this.creatureInfo = new File(creatureList);
		this.creatures = new ArrayList<>(0);
		
		try {
			Scanner scan = new Scanner(this.creatureInfo);
			scan.useDelimiter(";");
			
			int j = 0;
			while(scan.hasNext()) {
				String test = scan.next();
				if(j % 6 == 0) {
					this.creatures.add(test);
				}
				j++;
			}
			
			scan.close();
		}catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "ERROR: UNABLE TO FIND CREATUREINFO.TXT");
		} 

		for(int i = 0; i < this.creatures.size(); i++) {
			this.listModel.addElement(this.creatures.get(i));
		}

		this.listOfCreatures = new JList<>(this.listModel);
		this.listOfCreatures.setSelectionMode(0);
		this.listOfCreatures.addListSelectionListener(this.selectionListener);
		this.scroll = new JScrollPane(this.listOfCreatures);
	}

	public void pickCreature() {
		GroupLayout layout = new GroupLayout(this.panel);
		this.panel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(CombatTracker.search))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(CombatTracker.searchBar)))
						.addComponent(this.scroll)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(CombatTracker.customName))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(CombatTracker.inputName)))
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(CombatTracker.okayButton2))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(CombatTracker.cancelButton2)))));
		layout.linkSize(0, new Component[] { CombatTracker.okayButton2, CombatTracker.cancelButton2 });
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(CombatTracker.search)
						.addComponent(CombatTracker.searchBar))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.scroll))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(CombatTracker.customName)
						.addComponent(CombatTracker.inputName))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addComponent(CombatTracker.okayButton2)
						.addComponent(CombatTracker.cancelButton2)));
		layout.linkSize(1, new Component[] { CombatTracker.okayButton2, CombatTracker.cancelButton2 });
		
		add(this.panel);
		setTitle("Select a Creature");
		setSize(300, 280);
		setLocationRelativeTo((Component)null);
		setVisible(true);
	}

	public void filter(String filter) {
		ArrayList<String> filteredList = new ArrayList<>(0);

		try {
			Scanner scan = new Scanner(this.creatureInfo);
			scan.useDelimiter(";");

			int j = 0;
			while(scan.hasNext()) {
				String test = scan.next();
				if (j % 6 == 0 && 
						test.toLowerCase().contains(filter.toLowerCase())) {
					filteredList.add(test);
				}
				j++;
			} 

			scan.close();
		}catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "ERROR: UNABLE TO FIND CREATUREINFO.TXT");
		} 

		String[] filteredCreatures = new String[filteredList.size()];
		for(int i = 0; i < filteredList.size(); i++) {
			filteredCreatures[i] = filteredList.get(i);
		}
		
		this.listOfCreatures.setListData(filteredCreatures);
	}

	private class SelectionListener implements ListSelectionListener {
		private SelectionListener() {}

		public void valueChanged(ListSelectionEvent e) {
			if(!e.getValueIsAdjusting() && 
					CreatureSelector.this.listOfCreatures.getSelectedIndex() != -1) {

				String selection = CreatureSelector.this.listOfCreatures.getSelectedValue();
				CombatTracker.setInputName(selection);
			} 
		}
	}

	public String getSelectedCreature() {
		return this.listOfCreatures.getSelectedValue();
	}
}