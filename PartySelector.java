import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PartySelector extends JFrame {
	private File directory;
	private String partyFile = new String();
	private final JPanel panel = new JPanel();

	public PartySelector(String directory) {
		this.directory = new File(directory);
		JFileChooser selector = new JFileChooser();
		selector.setCurrentDirectory(this.directory);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT Files", new String[] { "txt" });
		selector.setFileFilter(filter);
		int returnVal = selector.showOpenDialog(this.panel);
		
		if(returnVal == 0) {
			this.partyFile = selector.getSelectedFile().getName();
		}
	}

	public String getPartyFile() {
		return this.partyFile;
	}
}