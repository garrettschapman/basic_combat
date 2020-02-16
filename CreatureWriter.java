import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class CreatureWriter extends JFrame {
	private JPanel panel = new JPanel();
	private JLabel nameLabel = new JLabel("Name: ");
	private JLabel acLabel = new JLabel("Armor Class: ");
	private JLabel hdLabel = new JLabel("Number of Hit Dice: ");
	private JLabel sizeLabel = new JLabel("Size: ");
	private JLabel conLabel = new JLabel("Constitution Modifier: ");
	private JLabel dexLabel = new JLabel("Dexterity Modifier: ");
	
	private JButton okayButton;
	private OkayHandler okay;
	private JButton cancelButton;
	private CancelHandler cancel;
	
	private JTextField nameField = new JTextField(10);
	private JTextField acField = new JTextField(10);
	private JTextField hdField = new JTextField(10);
	
	private JRadioButton small;
	private JRadioButton medium;
	private JRadioButton large;
	private JRadioButton huge;
	private JRadioButton gargantuan;
	private ButtonGroup sizeGroup;
	private JRadioButton tiny = new JRadioButton("Tiny", false);
	
	private String[] modifier;
	private JComboBox conMod;
	private ConHandler conHandler;
	private JComboBox dexMod;
	private DexHandler dexHandler;
	
	private String name;
	private String ac;
	private int hd;
	private int size;
	private int con;
	private int dex;
	
	private String finalCreature;
	
	public CreatureWriter() {
		this.tiny.addItemListener(new SizeHandler(4));
		this.small = new JRadioButton("Small", false);
		this.small.addItemListener(new SizeHandler(6));
		this.medium = new JRadioButton("Medium", true);
		this.medium.addItemListener(new SizeHandler(6));
		this.large = new JRadioButton("Large", false);
		this.large.addItemListener(new SizeHandler(10));
		this.huge = new JRadioButton("Huge", false);
		this.huge.addItemListener(new SizeHandler(12));
		this.gargantuan = new JRadioButton("Gargantuan", false);
		this.gargantuan.addItemListener(new SizeHandler(20));
		
		this.sizeGroup = new ButtonGroup();
		this.sizeGroup.add(this.tiny);
		this.sizeGroup.add(this.small);
		this.sizeGroup.add(this.medium);
		this.sizeGroup.add(this.large);
		this.sizeGroup.add(this.huge);
		this.sizeGroup.add(this.gargantuan);

		this.modifier = new String[] { "10", "9", "8", "7", "6", "5", "4", "3", "2", "1", "0", "-1", "-2", "-3", "-4", "-5" };

		this.conMod = new JComboBox<>(this.modifier);
		this.conMod.setSelectedIndex(10);
		this.conHandler = new ConHandler();
		this.conMod.addActionListener(this.conHandler);

		this.dexMod = new JComboBox<>(this.modifier);
		this.dexMod.setSelectedIndex(10);
		this.dexHandler = new DexHandler();
		this.dexMod.addActionListener(this.dexHandler);

		this.size = 8;

		this.okayButton = new JButton("Okay");
		this.okay = new OkayHandler();
		this.okayButton.addActionListener(this.okay);

		this.cancelButton = new JButton("Cancel");
		this.cancel = new CancelHandler();
		this.cancelButton.addActionListener(this.cancel);

		GroupLayout layout = new GroupLayout(this.panel);
		this.panel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.nameLabel))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.nameField)))
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.acLabel))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.acField)))
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.hdLabel))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.hdField)))
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.sizeLabel))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.tiny))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.small))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.medium))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.large))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.huge))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.gargantuan)))
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.conLabel))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.conMod)))
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.dexLabel))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.dexMod)))
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.okayButton))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.cancelButton)))));
		layout.linkSize(0, new Component[] { this.okayButton, this.cancelButton });
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(this.nameLabel)
						.addComponent(this.nameField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(this.acLabel)
						.addComponent(this.acField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(this.hdLabel)
						.addComponent(this.hdField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(this.sizeLabel)
						.addComponent(this.tiny)
						.addComponent(this.small)
						.addComponent(this.medium)
						.addComponent(this.large)
						.addComponent(this.huge)
						.addComponent(this.gargantuan))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(this.conLabel)
						.addComponent(this.conMod))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(this.dexLabel)
						.addComponent(this.dexMod))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(this.okayButton)
						.addComponent(this.cancelButton)));
		layout.linkSize(1, new Component[] { this.okayButton, this.cancelButton });

		add(this.panel);
		setTitle("Enter New Creature Information");
		setSize(450, 250);
		setLocationRelativeTo((Component)null);
		setVisible(true);
	}

	private void createCreature() {
		this.name = this.nameField.getText();
		this.ac = this.acField.getText();

		try {
			this.hd = Integer.parseInt(this.hdField.getText());
		}catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "ERROR: INVALID NUMBER OF HIT DICE ENTERED");
			return;
		} 

		this.finalCreature = String.valueOf(this.name) + ";" + this.ac + ";" + this.hd + ";" + this.size + ";" + this.con + ";" + this.dex + ";";
		writeCreature(this.finalCreature);
	}

	private void writeCreature(String creature) {
		BufferedWriter writer = null;

		try {
			writer = new BufferedWriter(new FileWriter("./Data/CreatureInfo.txt", true));
			writer.write(creature);
			writer.newLine();
			writer.flush();
			writer.close();
		}catch (IOException e) {
			JOptionPane.showMessageDialog(null, "ERROR: COULD NOT FIND CREATUREINFO.TXT");
			return;
		} 
		
		dispose();
	}

	private class SizeHandler implements ItemListener {
		private int dieSize;
		
		public SizeHandler(int size) {
			this.dieSize = size;
		}

		public void itemStateChanged(ItemEvent e) {
			CreatureWriter.this.size = this.dieSize;
		}
	}

	private class ConHandler implements ActionListener {
		private ConHandler() {}

		public void actionPerformed(ActionEvent e) {
			JComboBox box = (JComboBox)e.getSource();
			CreatureWriter.this.con = Integer.parseInt((String)box.getSelectedItem());
		}
	}

	private class DexHandler implements ActionListener {
		private DexHandler() {}

		public void actionPerformed(ActionEvent e) {
			JComboBox box = (JComboBox)e.getSource();
			CreatureWriter.this.dex = Integer.parseInt((String)box.getSelectedItem());
		}
	}

	private class OkayHandler implements ActionListener {
		private OkayHandler() {}

		public void actionPerformed(ActionEvent e) {
			CreatureWriter.this.createCreature();
		}
	}

	private class CancelHandler implements ActionListener {
		private CancelHandler() {}

		public void actionPerformed(ActionEvent e) {
			CreatureWriter.this.dispose();
		}
	}
}