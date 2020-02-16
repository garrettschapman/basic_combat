import java.awt.Component;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AddCreature extends JFrame {
	private JPanel panel = new JPanel();
 
	public AddCreature() {
		GroupLayout layout = new GroupLayout(this.panel);
		this.panel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(CombatTracker.okayButton1))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(CombatTracker.cancelButton1)))
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(CombatTracker.name))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(CombatTracker.inputName)))
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(CombatTracker.AC))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(CombatTracker.inputAC)))
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(CombatTracker.HP))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(CombatTracker.inputHP)))
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(CombatTracker.init))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(CombatTracker.inputInit)))));
		layout.linkSize(0, new Component[] { CombatTracker.okayButton1, CombatTracker.cancelButton1 });
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(CombatTracker.name)
						.addComponent(CombatTracker.inputName))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(CombatTracker.AC)
						.addComponent(CombatTracker.inputAC))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(CombatTracker.HP)
						.addComponent(CombatTracker.inputHP))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(CombatTracker.init)
						.addComponent(CombatTracker.inputInit))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addComponent(CombatTracker.okayButton1)
						.addComponent(CombatTracker.cancelButton1)));
		layout.linkSize(1, new Component[] { CombatTracker.okayButton1, CombatTracker.cancelButton1 });
		
		add(this.panel);
		setTitle("Enter Creature Information");
		setSize(330, 190);
		setLocationRelativeTo((Component)null);
		setVisible(true);
	}
}