
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.AbstractAction;
import java.awt.Window;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.BorderFactory;

public class AboutDialog extends JDialog{
    
    public AboutDialog(Window owner)
    {
        super(owner,"About");
        setLocationRelativeTo(null);
        
        JLabel titleLabel = new JLabel(getAboutTitle());
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 15));
        titleLabel.setOpaque(true);
        titleLabel.setBorder(BorderFactory.createEtchedBorder());
        titleLabel.setBackground(Color.WHITE);

        JTextArea contentArea = new JTextArea(getAboutText());
        contentArea.setWrapStyleWord(true);
        contentArea.setLineWrap(true);
        contentArea.setEditable(false);

        add(titleLabel,BorderLayout.PAGE_START);
        add(contentArea,BorderLayout.CENTER);
        setSize(320,220);
    }

    private String getAboutText()
    {
        return "This is a java application that emulates a simple note-taking application.\n" + 
                "The purpose of this application was to learn about how to create UI in swing.\n" + 
                "Author: Arjob Mukherjee (arjobmukherjee@gmail.com)";
    }

    private String getAboutTitle()
    {
        return "Java Notepad with Font Dialog";
    }

    public static void showDialog(Window owner)
    {
        JDialog newAboutWindow = new AboutDialog(owner);
        newAboutWindow.setVisible(true);
    }
}