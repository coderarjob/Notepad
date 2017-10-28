import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class Notepad
{
	public static void main(String[] main)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception ex)
		{
			javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage());
		}
		
		NotepadFrame frame = new NotepadFrame();
		frame.setVisible(true);
	}
}
