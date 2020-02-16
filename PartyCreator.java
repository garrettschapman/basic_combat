import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PartyCreator extends JFrame {
	private JPanel panel = new JPanel();
	private JLabel nameLabel = new JLabel("Name: ");
	private JLabel acLabel = new JLabel("Armor Class: ");
	private JLabel hpLabel = new JLabel("Hit Points: ");
	
	private JTextField nameField = new JTextField(10);
	private JTextField acField = new JTextField(10);
	private JTextField hpField = new JTextField(10);
	
	private AddHandler addHandler;
	private JButton cancelButton;
	private CancelHandler cancelHandler;
	
	private String partyName;
	private ArrayList<String> partyList = new ArrayList<>(0);
	
	private JButton doneButton = new JButton("Finish Party!");
	
	private DoneHandler doneHandler = new DoneHandler(); private JButton addButton; public PartyCreator(String title) {
		this.doneButton.addActionListener(this.doneHandler);
		
		this.addButton = new JButton("Add a New Member!");
		this.addHandler = new AddHandler();
		this.addButton.addActionListener(this.addHandler);
		
		this.cancelButton = new JButton("Cancel");
		this.cancelHandler = new CancelHandler();
		this.cancelButton.addActionListener(this.cancelHandler);
		
		this.partyName = title;

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
										.addComponent(this.hpLabel))

								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.hpField)))
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.doneButton))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.addButton))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.cancelButton)))));
		layout.linkSize(0, new Component[] { this.doneButton, this.addButton, this.cancelButton });
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(this.nameLabel)
						.addComponent(this.nameField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(this.acLabel)
						.addComponent(this.acField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(this.hpLabel)
						.addComponent(this.hpField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(this.doneButton)
						.addComponent(this.addButton)
						.addComponent(this.cancelButton)));
		layout.linkSize(1, new Component[] { this.doneButton, this.addButton, this.cancelButton });
		
		add(this.panel);
		setTitle("New Party: " + this.partyName);
		setSize(490, 180);
		setLocationRelativeTo((Component)null);
		setVisible(true);

		if(this.partyName == null || this.partyName.equals("")) {
			dispose();
		}
	}

	private void addPartyMember() {
		int hp;
		String name = this.nameField.getText();
		this.nameField.setText("");
		String ac = this.acField.getText();
		this.acField.setText("");

		if(name.equals("") || ac.equals("")) {
			JOptionPane.showMessageDialog(null, "ERROR: INPUT REQUIRED");
			this.hpField.setText("");
			return;
		} 

		try {
			hp = Integer.parseInt(this.hpField.getText());
		}catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "ERROR: INVALID NUMBER OF HIT POINTS ENTERED");
			this.hpField.setText("");
			return;
		} 

		this.hpField.setText("");
		String partyMember = String.valueOf(name) + ";" + ac + ";" + hp + ";";
		this.partyList.add(partyMember);
	}

	private void writeParty() {
		int hp;
		String name = this.nameField.getText();
		this.nameField.setText("");
		String ac = this.acField.getText();
		this.acField.setText("");

		try {
			hp = Integer.parseInt(this.hpField.getText());
		}catch (NumberFormatException e) {
			hp = 0;
		} 
		
		this.hpField.setText("");

		if(!name.equals("") && !ac.equals("") && hp != 0) {
			String partyMember = String.valueOf(name) + ";" + ac + ";" + hp + ";";
			this.partyList.add(partyMember);
		} 

		if(this.partyList.size() == 0) {
			dispose();
		}

		String fileName = "./Data/Parties/" + this.partyName + ".txt";
		Path file = Paths.get(fileName, new String[0]);

		try {
			Files.write(file, (Iterable)this.partyList, Charset.forName("UTF-8"), new OpenOption[] { StandardOpenOption.CREATE, StandardOpenOption.APPEND });
		}catch (IOException iOException) {}
	}

	private class DoneHandler implements ActionListener {
		private DoneHandler() {}

		public void actionPerformed(ActionEvent e) {
			PartyCreator.this.writeParty();
			PartyCreator.this.dispose();
		}
	}

	private class AddHandler implements ActionListener {
		private AddHandler() {}
		
		public void actionPerformed(ActionEvent e) {
			PartyCreator.this.addPartyMember();
		}
	}

	private class CancelHandler implements ActionListener {
		private CancelHandler() {}

		public void actionPerformed(ActionEvent e) {
			PartyCreator.this.dispose();
		}
	}
}