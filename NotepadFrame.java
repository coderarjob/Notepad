import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JFileChooser;
import javax.swing.Icon;

import javax.swing.ImageIcon;
import static javax.swing.JOptionPane.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import java.io.*;
import java.util.Scanner;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import com.omnirover.java.FontDialog;

public class NotepadFrame extends JFrame implements WindowListener
{
	Action saveasAction,openaction,newaction,saveAction,exitAction;
	JLabel statuslabel;
	JFileChooser filedialog = new JFileChooser();
	File currentFile = null;
	JTextArea textarea;
	JScrollPane scrollpane;

	public NotepadFrame()
	{
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(700,500);
		setLocationRelativeTo(null);
		addWindowListener(this);

		createActions();
		createUI();
		createNewDocument();		
	}

	private void createNewDocument(){
		textarea.setText("");
		currentFile = null;
		this.setTitle("New Document");
		setStatus("New document was created.");
		
		textarea.requestFocus();
		
	}
	
	private void createActions()
	{
		exitAction =  new AbstractAction("Save"){
			public void actionPerformed(ActionEvent e)
			{
				int res = showConfirmDialog(null,"Are you sure, you want to exit?","Exit",YES_NO_OPTION);
				if (res == YES_OPTION)
					System.exit(0);
			}
		};

		saveAction = new AbstractAction("Save"){
			public void actionPerformed(ActionEvent e)
			{
				if (currentFile == null)
				{
					showMessageDialog(null,"Use save-as to save the document to a file first.");
					return;
				}
				
				SaveToFile(currentFile,filedialog.getIcon(currentFile));
			}
		};

		saveasAction = new AbstractAction("Save As",new ImageIcon("./images/save.jpg")){
			public void actionPerformed(ActionEvent e)
			{
				if (filedialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
				{
					currentFile = filedialog.getSelectedFile();
					SaveToFile(currentFile,filedialog.getIcon(currentFile));
				}
			}
		};
		
		openaction = new AbstractAction("Open",new ImageIcon("./images/open.gif")){
			public void actionPerformed(ActionEvent e)
			{
				if (filedialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				{
					try
					{
						currentFile = filedialog.getSelectedFile();
						String contents = readCompleteFile(currentFile);
						textarea.setText(contents);
						setStatus("'" + currentFile.getName() + "' file opened successfully.", filedialog.getIcon(currentFile));
						setWindowTitle(currentFile.getAbsolutePath());
					}
					catch(Exception ex){
						showMessageDialog(null,ex.getMessage());
						setStatus("Cannot open file, " + ex.getMessage());
					}
				}
			}
		};
		
		newaction = new AbstractAction("New",new ImageIcon("./images/new.png")){
			public void actionPerformed(ActionEvent e)
			{
				int res = showConfirmDialog(null,"Are you sure to create a new document?","Confirm",YES_NO_OPTION);
				if (res == YES_OPTION)
					createNewDocument();
			}
		};
	}
	
	private String readCompleteFile(File file) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(file));

		String line;
		for(;(line = br.readLine()) != null;sb.append(System.lineSeparator()))
			sb.append(line);
		br.close();
		return sb.toString();
	}
	
	private void SaveToFile(File file,Icon icon)
	{
		try
		{
			writeToFile(file,textarea.getText());
			setStatus("'" + file.getName() + "' file saved successfully.", icon);
			setWindowTitle(file.getAbsolutePath());
		}
		catch(Exception ex)
		{
			showMessageDialog(null,ex.getMessage());
			setStatus("Cannot save file, " + ex.getMessage());
		}
	}

	private void writeToFile(File file,String str) throws IOException
	{
		if (file.exists() == false)
			file.createNewFile();

		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		bw.write(str);
		bw.close();
	}

	private void createUI()
	{
		JFrame parent = this;

		setLayout(new BorderLayout());
		//---------------menus------------------------
		JMenuBar menubar = new JMenuBar();
		JMenu filemenu = new JMenu("File");
		JMenu helpmenu = new JMenu("Help");
		JMenu textmenu = new JMenu("Text");
		
		//create file menu
		filemenu.add(new JMenuItem(newaction));
		filemenu.addSeparator();
		filemenu.add(new JMenuItem(openaction));
		filemenu.addSeparator();
		filemenu.add(new JMenuItem(saveasAction));
		filemenu.add(new JMenuItem(saveAction));
		filemenu.addSeparator();
		filemenu.add(new JMenuItem(exitAction));

		//create text menu
		textmenu.add(new JMenuItem(new AbstractAction("Font and Size"){
			public void actionPerformed(ActionEvent e)
			{
				textarea.setFont(FontDialog.showDialog(parent,textarea.getFont()));
			}
		}));
		
		textmenu.add(new JCheckBoxMenuItem(new AbstractAction("Word wrap"){
			public void actionPerformed(ActionEvent e)
			{
				JCheckBoxMenuItem item = (JCheckBoxMenuItem)e.getSource();
				textarea.setLineWrap(item.isSelected());
			}
		}));
		
		//create help menu
		helpmenu.add(new JMenuItem(new AbstractAction("About"){
			public void actionPerformed(ActionEvent e)
			{
				AboutDialog.showDialog(parent);
			}
		}));
		
		menubar.add(filemenu);
		menubar.add(textmenu);
		menubar.add(helpmenu);
		this.setJMenuBar(menubar);
		
		//create toolbar
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		toolbar.add(new JButton(newaction));
		toolbar.add(new JButton(openaction));
		toolbar.add(new JButton(saveasAction));
		this.add(toolbar,BorderLayout.NORTH);
		
		textarea = new JTextArea();
		scrollpane = new JScrollPane(textarea);
		this.add(scrollpane,BorderLayout.CENTER);
		
		statuslabel = new JLabel();
		this.add(statuslabel,BorderLayout.SOUTH);
	}
	
	private void setStatus(String status)
	{
		setStatus(status,null);
	}
	
	private void setWindowTitle(String title)
	{
		this.setTitle(title);
	}
	
	private void setStatus(String status, Icon icon)
	{
		statuslabel.setText("Status: " + status);
		statuslabel.setIcon(icon);
	}

	//-----------WINDOW LISTENER METHODS----------------------
	public void windowClosing(WindowEvent e){
		exitAction.actionPerformed(null);
	}

	public void windowActivated(WindowEvent e){	}
	public void windowClosed(WindowEvent e){ }
	public void windowDeactivated(WindowEvent e){ }
	public void windowDeiconified(WindowEvent e){ }
	public void windowIconified(WindowEvent e){	}
	public void windowOpened(WindowEvent e){ }
}
