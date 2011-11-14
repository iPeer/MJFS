package iPeer.MJFS;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

@SuppressWarnings("serial")
public class MainInterface extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;

	public static void main(String[] args) {
		try {
			MainInterface dialog = new MainInterface();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MainInterface() {
		setBounds(100, 100, 288, 153);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblMinecraftDirectory = new JLabel("Minecraft directory");
			lblMinecraftDirectory.setBounds(5, -1, 91, 14);
			contentPanel.add(lblMinecraftDirectory);
		}
		{
			textField = new JTextField();
			textField.setBounds(5, 11, 257, 20);
			textField.setEditable(false);
			contentPanel.add(textField);
			textField.setColumns(10);
		}
		
		JLabel lblJarfileToSwap = new JLabel("Jarfile to swap in");
		lblJarfileToSwap.setBounds(5, 42, 91, 14);
		contentPanel.add(lblJarfileToSwap);
		
		textField_1 = new JTextField();
		textField_1.setBounds(5, 55, 221, 20);
		contentPanel.add(textField_1);
		textField_1.setColumns(10);
		
		JButton Browse = new JButton("...");
		Browse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JarFilter jf = new JarFilter();
				fc.setFileFilter(jf);
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if (fc.showOpenDialog(contentPanel) == JFileChooser.APPROVE_OPTION) {
				textField_1.setText(fc.getSelectedFile().toString());
				}
			}
		});
		Browse.setBounds(231, 54, 31, 23);
		contentPanel.add(Browse);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton copy = new JButton("Swap!");
				copy.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						doswitch(textField.getText(), textField_1.getText());
					}
				});
				buttonPane.add(copy);
				getRootPane().setDefaultButton(copy);
			}
			{
				JButton cancelButton = new JButton("Close");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
						System.exit(0);
					}
				});
				buttonPane.add(cancelButton);
			}
			String s = System.getenv("APPDATA")+"\\.minecraft\\bin";
			textField.setText(s);
		}
	}

	protected void doswitch(String t, String t1) {
		try {
		String s = JOptionPane.showInputDialog("Please enter a name to be used when backing up this Jar.");
		if (s == null)
			s = Long.toString(System.currentTimeMillis());
		File[] f = {
		new File(t),
		new File(t1),
		new File(t,"\\minecraft.jar"),
		new File(t,"\\MJFS"),
		new File(t,"\\MJFS\\minecraft_"+s+".jar")
		};
		if (!f[3].exists())
			f[3].mkdirs();
		if (f[4].exists())
			if (JOptionPane.showConfirmDialog(getContentPane(), "A jar backup with this name already exists. Do you want to delete it to make way for this backup?", "File already exists", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				f[4].delete();
				f[2].renameTo(f[4]);
		if (!f[4].exists()) 
			f[2].renameTo(f[4]);
		f[1].renameTo(f[2]);
		JOptionPane.showMessageDialog(getContentPane(), "Jars swapped successfully!");
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(getContentPane(), "An error occured while switching the jar files, please let iPeer know about this error!\n\n"+e.getMessage());
			e.printStackTrace();
		}
		
		
	}
	
	private JFileChooser fc = new JFileChooser();
	
}
