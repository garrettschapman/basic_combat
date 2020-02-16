import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CombatTracker extends JPanel {
	public final Color background_color = new Color(196, 201, 209);
	public static final Color table_color = new Color(216, 219, 210);
	
	private int currentCreature;
	private int totalCreatureCount;
	
	private AddCreature add;
	private PartySelector partySelector;
	private PartyCreator partyCreator;
	
	private final JButton addNewCreatureButton = new JButton("Add A New Creature!");
	private final JButton addExistingCreatureButton;
	private final JButton saveCreatureButton;
	private final JButton deleteCreatureButton;
	private final JButton selectPartyButton;
	private final JButton createPartyButton;
	private final JButton reorderTableButton;
	private final JButton nextTurnButton;
	
	private addCreatureInfoHandler info;
	private addNewCreatureButtonHandler newHandler = new addNewCreatureButtonHandler();
	private addExistingCreatureButtonHandler existingHandler;
	private SaveCreatureHandler saveCreatureHandler;
	private DeleteCreatureHandler deleteHandler;
	private SelectPartyHandler selectPartyHandler;
	private CreatePartyHandler createPartyHandler;
	private ReorderHandler reorderHandler;
	private NextTurnHandler nextTurnHandler;
	
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane tablePane;
	
	protected static JLabel name = new JLabel("Name: ");
	protected static JTextField inputAC = new JTextField(10);
	protected static JLabel AC = new JLabel("Armor Class: ");
	protected static JTextField inputHP = new JTextField(10);
	protected static JLabel HP = new JLabel("Hit Points: ");
	protected static JTextField inputName = new JTextField(10);
	protected static JButton okayButton1 = new JButton("Okay");
	protected static JButton cancelButton1 = new JButton("Cancel");
	protected static JTextField inputInit = new JTextField(10); protected static JLabel init = new JLabel("Initiative: "); private CreatureSelector selector; protected static JLabel search = new JLabel("Search: "); protected static JTextField searchBar = new JTextField(10); private SearchListener searchListener; protected static JLabel customName = new JLabel("Custom Name: "); protected static JButton okayButton2 = new JButton("Okay"); protected static JButton cancelButton2 = new JButton("Cancel"); private selectionHandler select; private CreatureWriter creatureWriter;
	
	CombatTracker() {
		this.addNewCreatureButton.addActionListener(this.newHandler);
		
		this.addExistingCreatureButton = new JButton("Add An Existing Creature!");
		this.existingHandler = new addExistingCreatureButtonHandler();
		this.addExistingCreatureButton.addActionListener(this.existingHandler);
		
		this.saveCreatureButton = new JButton("Save a New Creature!");
		this.saveCreatureHandler = new SaveCreatureHandler();
		this.saveCreatureButton.addActionListener(this.saveCreatureHandler);
		
		this.selectPartyButton = new JButton("Select a Party!");
		this.selectPartyHandler = new SelectPartyHandler();
		this.selectPartyButton.addActionListener(this.selectPartyHandler);
		
		this.createPartyButton = new JButton("Create a New Party!");
		this.createPartyHandler = new CreatePartyHandler();
		this.createPartyButton.addActionListener(this.createPartyHandler);
		
		this.reorderTableButton = new JButton("Reorder the Table!");
		this.reorderHandler = new ReorderHandler();
		this.reorderTableButton.addActionListener(this.reorderHandler);
		
		this.deleteCreatureButton = new JButton("Remove Selected Creature!");
		this.deleteHandler = new DeleteCreatureHandler();
		this.deleteCreatureButton.addActionListener(this.deleteHandler);
		
		this.nextTurnButton = new JButton("Next Turn!");
		this.nextTurnHandler = new NextTurnHandler();
		this.nextTurnButton.addActionListener(this.nextTurnHandler);
		
		this.model = new DefaultTableModel();
		this.currentCreature = -1;
		this.totalCreatureCount = 0;
		this.info = new addCreatureInfoHandler();
		this.selector = new CreatureSelector("./Data/CreatureInfo.txt");
		this.searchListener = new SearchListener();
		this.select = new selectionHandler();
		
		okayButton1.addActionListener(this.info);
		okayButton2.addActionListener(this.select);
		cancelButton1.addActionListener(this.info);
		cancelButton2.addActionListener(this.select);
		searchBar.addKeyListener(this.searchListener);

		makeTable();

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.addNewCreatureButton))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.addExistingCreatureButton))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.saveCreatureButton)))
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.reorderTableButton))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.deleteCreatureButton))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.nextTurnButton)))
						.addComponent(this.tablePane)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.selectPartyButton))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.createPartyButton)))));
		layout.linkSize(0, new Component[] { this.addNewCreatureButton, this.addExistingCreatureButton, this.saveCreatureButton, this.reorderTableButton, this.deleteCreatureButton, this.nextTurnButton, this.selectPartyButton, this.createPartyButton });
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(this.addNewCreatureButton)
						.addComponent(this.addExistingCreatureButton)
						.addComponent(this.saveCreatureButton))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(this.reorderTableButton)
						.addComponent(this.deleteCreatureButton)
						.addComponent(this.nextTurnButton))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.tablePane))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addComponent(this.selectPartyButton)
						.addComponent(this.createPartyButton)));
		layout.linkSize(1, new Component[] { this.addNewCreatureButton, this.addExistingCreatureButton, this.saveCreatureButton, this.reorderTableButton, this.deleteCreatureButton, this.nextTurnButton, this.selectPartyButton, this.createPartyButton });

		setBackground(this.background_color);
	}

	public Object[] convertToData(String name, String ac, int hp, String init) {
		Object[] data = new Object[6];
		data[0] = name;
		data[1] = ac;
		data[2] = Integer.valueOf(hp);
		data[4] = init;

		return data;
	}

	public void makeTable() {
		this.table = new JTable(this.model);
		this.table.setSelectionMode(0);
		this.table.setBackground(table_color);

		this.model.addColumn("Name");
		this.model.addColumn("Armor Class");
		this.model.addColumn("Hit Points");
		this.model.addColumn("Temp Hit Points");
		this.model.addColumn("Initiative");
		this.model.addColumn("Current Turn");

		this.tablePane = new JScrollPane(this.table);
	}

	public void readParty(String partyPath) {
		try {
			Scanner scan = new Scanner(new File(partyPath));
			scan.useDelimiter(";");

			String name = null, ac = null, hp = null;
			
			int i = 0;
			while(scan.hasNext()) {
				String logicalLine = scan.next();
				if(i == 0) {
					name = " " + logicalLine;
				}else if (i == 1) {
					ac = logicalLine;
				}else if (i == 2) {
					hp = logicalLine;
				} 

				if(i == 2) {
					this.model.addRow(convertToData(name, ac, Integer.parseInt(hp), ""));
					this.totalCreatureCount++;
					i = 0; continue;
				} 
				i++;
			} 

			scan.close();
			return;
		}catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "ERROR: UNABLE TO FIND " + partyPath.substring(2));
			return;
		} 
	}

	public void addNewCreature(String name, String ac, String strHP, String init) {
		int hp = 0;

		try {
			hp = Integer.parseInt(strHP);

			this.model.addRow(convertToData(" " + name, ac, hp, init));
			this.totalCreatureCount++;
			return;
		}catch (NumberFormatException e) {
			return;
		}
	}

	public void addExistingCreature(String creatureName, String newName) {
		String name = newName;

		try {
			Scanner scan = new Scanner(new File("./Data/CreatureInfo.txt"));
			scan.useDelimiter(";");

			try {
				while(scan.hasNextLine()) {
					String mainLine = scan.next();
					if(mainLine.startsWith(creatureName)) {
						int i = 0;

						String ac = null, dice = null, size = null, con = null, dex = null, initiative = null;
						int diceNum = 0, conMod = 0, initMod = 0, hitPoints = 0;
						double hitDie = 0.0D;

						while(scan.hasNext()) {
							String logicalLine = scan.next();
							if(i == 0) {
								ac = logicalLine;
							}else if (i == 1) {
								dice = logicalLine;
							}else if (i == 2) {
								size = logicalLine;
							}else if (i == 3) {
								con = logicalLine;
							}else if (i == 4) {
								dex = logicalLine;
							} 

							if(i == 4) {
								diceNum = Integer.parseInt(dice);
								conMod = Integer.parseInt(con);
								hitDie = Integer.parseInt(size);
								initMod = Integer.parseInt(dex);

								for(int j = 0; j < diceNum; j++) {
									hitPoints += 1 + (int)(Math.random() * hitDie) + conMod;
								}
								
								if(hitPoints <= 0) {
									hitPoints = 1;
								}
								
								initiative = Integer.toString(1 + (int)(Math.random() * 20.0D) + initMod);

								if(Integer.parseInt(initiative) <= 0) {
									initiative = "1";
								}

								this.model.addRow(convertToData(name, ac, hitPoints, initiative));
								
								scan.close();
								this.totalCreatureCount++;
								
								return;
							} 
							i++;
						}
					} 
				} 
			}catch (NoSuchElementException e) {
				JOptionPane.showMessageDialog(null, "ERROR: CREATURE DOES NOT EXIST IN FILE");
				scan.close();
			}catch (NullPointerException nullPointerException) {}
		}catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "ERROR: UNABLE TO FIND CREATUREINFO.TXT");
		} 
	}

	private void reorder() {
		if (this.totalCreatureCount < 2) {
			JOptionPane.showMessageDialog(null, "ERROR: NOT ENOUGH CREATURES IN COMBAT");
			return;
		} 

		ArrayList<Object[]> creatures = new ArrayList(0);
		
		while(this.model.getRowCount() > 0) {
			Object[] creature = new Object[6];

			for(int j = 0; j < this.model.getColumnCount(); j++) {
				creature[j] = this.model.getValueAt(0, j);
			}

			creatures.add(creature);
			this.model.removeRow(0);
		} 

		while(creatures.size() > 0) {
			int maxInit = Integer.parseInt(((Object[])creatures.get(0))[4].toString());
			int row = 0;

			for(int i = 0; i < creatures.size(); i++) {
				if (Integer.parseInt(((Object[])creatures.get(i))[4].toString()) > maxInit) {
					maxInit = Integer.parseInt(((Object[])creatures.get(i))[4].toString());
					row = i;
				} 
			} 

			this.model.addRow(creatures.get(row));
			creatures.remove(row);
		} 
	}

	private class addNewCreatureButtonHandler implements ActionListener {
		private addNewCreatureButtonHandler() {}

		public void actionPerformed(ActionEvent e) {
			CombatTracker.this.add = new AddCreature();
			CombatTracker.inputName.setText("");
			CombatTracker.inputAC.setText("");
			CombatTracker.inputHP.setText("");
			CombatTracker.inputInit.setText("");
		}
	}

	private class addCreatureInfoHandler implements ActionListener {
		private addCreatureInfoHandler() {}
		
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand() == "Okay") {
				String name = CombatTracker.inputName.getText();
				String ac = CombatTracker.inputAC.getText();
				String hp = CombatTracker.inputHP.getText();
				String init = CombatTracker.inputInit.getText();
				CombatTracker.this.addNewCreature(name, ac, hp, init);
			} 
			
			CombatTracker.this.add.dispose();
		}
	}

	private class addExistingCreatureButtonHandler implements ActionListener {
		private addExistingCreatureButtonHandler() {}

		public void actionPerformed(ActionEvent e) {
			CombatTracker.this.selector.pickCreature();
			CombatTracker.inputName.setText("");
			CombatTracker.searchBar.setText("");
		}
	}

	public static void setInputName(String newName) {
		inputName.setText(newName);
	}

	private class selectionHandler implements ActionListener {
		private selectionHandler() {}

		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand() == "Okay") {
				String selection = CombatTracker.this.selector.getSelectedCreature();
				String name = CombatTracker.inputName.getText();
				CombatTracker.this.addExistingCreature(selection, name);
			} 

			CombatTracker.this.selector.dispose();
			CombatTracker.this.selector = new CreatureSelector("./Data/CreatureInfo.txt");
		}
	}

	private class SearchListener implements KeyListener {
		private SearchListener() {}

		public void keyPressed(KeyEvent e) {}
		
		public void keyReleased(KeyEvent e) {
			String searchText = CombatTracker.searchBar.getText();
			CombatTracker.this.selector.filter(searchText);
		}

		public void keyTyped(KeyEvent e) {}
	}

	private class SaveCreatureHandler implements ActionListener {
		private SaveCreatureHandler() {}

		public void actionPerformed(ActionEvent e) {
			CombatTracker.this.creatureWriter = new CreatureWriter();
		}
	}

	private class SelectPartyHandler implements ActionListener {
		private SelectPartyHandler() {}

		public void actionPerformed(ActionEvent e) {
			CombatTracker.this.partySelector = new PartySelector("./Data/Parties/");
			CombatTracker.this.partySelector.setDefaultCloseOperation(3);
			String partyFile = "./Data/Parties/" + CombatTracker.this.partySelector.getPartyFile();

			CombatTracker.this.readParty(partyFile);
		}
	}
	
	private class CreatePartyHandler implements ActionListener {
		private CreatePartyHandler() {}
		public void actionPerformed(ActionEvent e) {
			String partyName = JOptionPane.showInputDialog("Enter the party name: ");
			CombatTracker.this.partyCreator = new PartyCreator(partyName);
		}
	}

	private class DeleteCreatureHandler implements ActionListener {
		private DeleteCreatureHandler() {}

		public void actionPerformed(ActionEvent e) {
			int confirm = -1;
			try {
				confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove " + CombatTracker.this.model.getValueAt(CombatTracker.this.table.getSelectedRow(), 0) + "?", "Confirmation", 2);
			}catch (ArrayIndexOutOfBoundsException e2) {
				confirm = -1;
			} 

			if(confirm == 0) {
				if(CombatTracker.this.table.getSelectedRow() != -1) {
					JOptionPane.showMessageDialog(null, CombatTracker.this.model.getValueAt(CombatTracker.this.table.getSelectedRow(), 0) + " has been removed from combat!");
					CombatTracker.this.model.removeRow(CombatTracker.this.table.getSelectedRow());
					CombatTracker.this.totalCreatureCount = CombatTracker.this.totalCreatureCount - 1;

					if(CombatTracker.this.currentCreature == CombatTracker.this.model.getRowCount()) {
						CombatTracker.this.currentCreature = CombatTracker.this.currentCreature - 1;
					}
				}else {
					JOptionPane.showMessageDialog(null, "ERROR: NO CREATURE SELECTED");
				} 
			}else if (confirm == 2) {
				JOptionPane.showMessageDialog(null, CombatTracker.this.model.getValueAt(CombatTracker.this.table.getSelectedRow(), 0) + " was not removed.");
			}else {
				JOptionPane.showMessageDialog(null, "ERROR: NO CREATURE SELECTED");
			} 
		}
	}

	private class NextTurnHandler implements ActionListener {
		private NextTurnHandler() {}

		public void actionPerformed(ActionEvent e) {
			String currentTurn = "ググググググググ";

			if(CombatTracker.this.totalCreatureCount > 0) {
				if(CombatTracker.this.currentCreature == -1) {
					CombatTracker.this.model.setValueAt(currentTurn, 0, 5);
					CombatTracker.this.model.setValueAt("", CombatTracker.this.totalCreatureCount - 1, 5);
					CombatTracker.this.currentCreature = 1;
				}else if (CombatTracker.this.currentCreature < CombatTracker.this.totalCreatureCount) {
					CombatTracker.this.model.setValueAt(currentTurn, CombatTracker.this.currentCreature, 5);
					CombatTracker.this.model.setValueAt("", CombatTracker.this.currentCreature - 1, 5);
					CombatTracker.this.currentCreature = CombatTracker.this.currentCreature + 1;
				}else {
					CombatTracker.this.model.setValueAt("", CombatTracker.this.currentCreature - 1, 5);
					CombatTracker.this.currentCreature = -1;
				} 
			}else {
				JOptionPane.showMessageDialog(null, "ERROR: NO CREATURES IN COMBAT");
			} 
		}
	}

	private class ReorderHandler implements ActionListener {
		private ReorderHandler() {}

		public void actionPerformed(ActionEvent e) {
			CombatTracker.this.reorder();
		}
	}
}