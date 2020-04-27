import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;

public class View extends JFrame implements Observer {
	
	private JPanel firstRowTables, secondRowTables, wow,tRowTables, tRowTables2, info;
	private BorderLayout bLayout = new BorderLayout();
	private GridLayout layout = new GridLayout(2,1);
	private JButton[] wTables = new JButton[6];
	private JButton[] eTables = new JButton[3];
	private JButton[] sTables = new JButton[4];
	private JButton[] tables = new JButton[16];

	public View(Controller controller) {
		
		try {
			this.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("src/er(2).png")))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		firstRowTables = new JPanel();
		secondRowTables = new JPanel();
		tRowTables = new JPanel();
		tRowTables2 = new JPanel();
		info = new JPanel();
		wow = new JPanel();
		
		
		
		firstRowTables.setLayout(new BoxLayout(firstRowTables, BoxLayout.Y_AXIS));
		secondRowTables.setLayout(new BoxLayout(secondRowTables, BoxLayout.Y_AXIS));
		tRowTables.setLayout(new BoxLayout(tRowTables, BoxLayout.X_AXIS));
		tRowTables2.setLayout(new BoxLayout(tRowTables2, BoxLayout.X_AXIS));
		
		firstRowTables.add(Box.createVerticalGlue());
		secondRowTables.add(Box.createVerticalGlue());
		secondRowTables.add(Box.createVerticalStrut(10));
		tRowTables2.add(Box.createVerticalStrut(10));
		
		for(int i = 0; i < tables.length; i++) {
			tables[i] = new JButton();
			tables[i].addActionListener(controller);
			tables[i].setActionCommand("bord" + (i+1));
			tables[i].setPreferredSize(new Dimension(100,100));
		}
		for(int i = 0; i < 6; i++) {
			tables[i].setText("<html><strong>Bord " + (i+1) + "<br>4 pers</html>");
			firstRowTables.add(Box.createRigidArea(new Dimension(0,10)));
			firstRowTables.add(tables[i]);
		}
		for(int i = 6; i < 10; i++) {
			tables[i].setText("<html><strong>Bord " + (i+1) + "<br>4 pers</html>");
			tRowTables.add(Box.createRigidArea(new Dimension(0,10)));
			tRowTables.add(tables[i]);
		}
		for(int i = 15; i > 12; i--) {
			tables[i].setText("<html><strong>Bord " + (i+1) + "<br>2 pers</html>");
			secondRowTables.add(Box.createRigidArea(new Dimension(0,10)));
			secondRowTables.add(tables[i]);
		}
		tables[10].setText("<html><strong>Bord 11<br>2 pers</html>");
		tables[11].setText("<html><strong>Bord 12<br>2 pers</html>");
		tables[12].setText("<html><strong>Bord 13<br>6 pers</html>");
		tRowTables2.add(tables[10]);
		tRowTables2.add(tables[11]);
		tRowTables2.add(tables[12]);
		
		this.setLayout(bLayout);
		wow.setLayout(layout);
		wow.add(tRowTables2);
		wow.add(tRowTables);
		
		
		this.add(firstRowTables, bLayout.WEST);
		this.add(secondRowTables, bLayout.EAST);
		this.add(wow, bLayout.SOUTH);
		
		this.addWindowListener(controller);
		
		this.setVisible(true);
		this.setSize(1000, 800);
		this.setMinimumSize(new Dimension(500, 550));
		this.setLocationRelativeTo(null);
		this.setTitle("Restaurangbokning | Labb2 | Andreas Hortlund");
	}
	
	private void showBookingDialog() {
		JTextField xField = new JTextField(5);
	      JTextField yField = new JTextField(5);

	      JPanel myPanel = new JPanel();
	      myPanel.add(new JLabel("x:"));
	      myPanel.add(xField);
	      myPanel.add(Box.createHorizontalStrut(15)); // a spacer
	      myPanel.add(new JLabel("y:"));
	      myPanel.add(yField);

	      int result = JOptionPane.showConfirmDialog(null, myPanel, 
	               "Vänligen fyll i bokningsdetaljer", JOptionPane.OK_CANCEL_OPTION);
	      if (result == JOptionPane.OK_OPTION) {
	         System.out.println("x value: " + xField.getText());
	         System.out.println("y value: " + yField.getText());
	      }
	}
	
	private void bookOrReservate() {
		Object[] options = {"Boka nu",
                "Reservera"};
		JOptionPane.showOptionDialog(null, "Vad vill ni göra?", "Bokning av bord",  JOptionPane.YES_NO_CANCEL_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    options,null);
	}

	@Override
	public void update(Observable o, Object arg) {
		Model model = (Model)o;
		bookOrReservate();
		
	}
	
}
