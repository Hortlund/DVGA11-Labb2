import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.util.*;
import java.util.Calendar;
import java.util.Date;


public class View extends JFrame implements Observer {
	
	private String[] originText;
	private JPanel firstRowTables, secondRowTables, wow,tRowTables, tRowTables2, info, total;
	private JLabel pic;
	private BorderLayout bLayout = new BorderLayout();
	private GridLayout layout = new GridLayout(2,1);
	private JButton[] tables = new JButton[16];
	
	private String[]res = new String[2];
	
	private ImageIcon tic = new ImageIcon(new ImageIcon("src/ba.png").getImage().getScaledInstance(700, 600, Image.SCALE_DEFAULT));

	public View(Controller controller) {
		
		firstRowTables = new JPanel();
		secondRowTables = new JPanel();
		tRowTables = new JPanel();
		tRowTables2 = new JPanel();
		info = new JPanel();
		wow = new JPanel();
		total = new JPanel();
		pic = new JLabel(tic);
		
		
		
		
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
		firstRowTables.add(Box.createRigidArea(new Dimension(10,0)));
		for(int i = 0; i < 6; i++) {
			tables[i].setText("<html><strong>Bord " + (i+1) + "<br>4 pers</html>");
			firstRowTables.add(Box.createRigidArea(new Dimension(0,10)));
			firstRowTables.add(tables[i]);
		}
		tRowTables.add(Box.createRigidArea(new Dimension(10,0)));
		for(int i = 6; i < 10; i++) {
			tables[i].setText("<html><strong>Bord " + (i+1) + "<br>4 pers</html>");
			tRowTables.add(Box.createRigidArea(new Dimension(0,10)));
			tRowTables.add(tables[i]);
		}
		tRowTables.add(Box.createRigidArea(new Dimension(10,0)));
		
		for(int i = 15; i > 12; i--) {
			tables[i].setText("<html><strong>Bord " + (i+1) + "<br>2 pers</html>");
			secondRowTables.add(Box.createRigidArea(new Dimension(0,10)));
			secondRowTables.add(tables[i]);
		}
		
		
		tables[10].setText("<html><strong>Bord 11<br>2 pers</html>");
		tables[11].setText("<html><strong>Bord 12<br>2 pers</html>");
		tables[12].setText("<html><strong>Bord 13<br>6 pers</html>");
		tRowTables2.add(Box.createRigidArea(new Dimension(100,0)));
		tRowTables2.add(tables[10]);
		tRowTables2.add(tables[11]);
		tRowTables2.add(tables[12]);
		tRowTables2.add(Box.createRigidArea(new Dimension(100,0)));
		
		this.setLayout(bLayout);
		total.add(firstRowTables);
		total.add(Box.createHorizontalStrut(500));
		total.add(secondRowTables);
		wow.setLayout(layout);
		wow.add(tRowTables2);
		wow.add(tRowTables);
		
		
		this.add(firstRowTables, bLayout.WEST);
		this.add(pic, bLayout.CENTER);
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

	      int result = JOptionPane.showConfirmDialog(this, myPanel, 
	               "Vänligen fyll i bokningsdetaljer", JOptionPane.OK_CANCEL_OPTION);
	      if (result == JOptionPane.OK_OPTION) {
	    	  /*
	         System.out.println("x value: " + xField.getText());
	         System.out.println("y value: " + yField.getText());
	         */
	      }
	}
	
	private void setReservationValue(String[]b) {
		res = b;
	}
	
	private String[] returnReservationValue() {
		return res;
	}
	
	private int reserveTable() {
		JTextField nameField = new JTextField();
		JComboBox years = new JComboBox();
		JComboBox months = new JComboBox();
		JComboBox days = new JComboBox();
		JComboBox timeBox = new JComboBox();
		JLabel name  = new JLabel("Namn:");
		JLabel year = new JLabel("År:");
		JLabel month = new JLabel("Månad:");
		JLabel day = new JLabel("Dag:");
		JLabel time = new JLabel("Tid:");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5,2));
		
		for (int i = 0; i <= 10; i++) {
			years.addItem(LocalDateTime.now().getYear()+i);
			   
		}
		for (int i = 1; i <= 12; i++) {
			months.addItem(LocalDateTime.of(LocalDateTime.now().getYear(), i, 1, 1,1).getMonth());
			   
		}
		months.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				days.removeAllItems();
				for (int i = 1; i <= LocalDateTime.of(LocalDateTime.now().getYear(), months.getSelectedIndex()+1, 1, 1,1).getMonth().maxLength(); i++) {
					days.addItem(i);
				}
			}
			
		});
		
		for(int i = 0; i < 24; i++) {
			if(i < 1) {
				timeBox.addItem(i + "0:00");
			}else if(i < 10){
				timeBox.addItem("0"+i+":00");
			}else {
				timeBox.addItem(i+":00");
			}
			
		}
		
		months.setSelectedIndex(LocalDateTime.now().getMonthValue()-1);
		days.setSelectedIndex(LocalDateTime.now().getDayOfMonth()-1);
		timeBox.setSelectedIndex(LocalDateTime.now().getHour());
		panel.add(name);
		panel.add(nameField);
		panel.add(year);
		panel.add(years);
		panel.add(month);
		panel.add(months);
		panel.add(day);
		panel.add(days);
		panel.add(time);
		panel.add(timeBox);
		int result = JOptionPane.showConfirmDialog(this, panel, "Reservera bord",JOptionPane.OK_CANCEL_OPTION);
		if(result == 2) {
			
		}else if(result == 0) {
			if(nameField.getText() == null || nameField.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Ett namn måste anges vid reservation!");
				
				reserveTable();
				//this.dispose();
			}else {
				//System.out.println(years.getSelectedItem() + "-" + months.getSelectedItem() + "-" + days.getSelectedItem() + " " + timeBox.getSelectedItem() + " " + nameField.getText());
				String[]b = {years.getSelectedItem() + "-" + months.getSelectedItem() + "-" + days.getSelectedItem() + " " + timeBox.getSelectedItem(),nameField.getText()};
				setReservationValue(b);
				return 1;
			}
			
			/*
			result = JOptionPane.showConfirmDialog(this, panel, "Reservera bord",JOptionPane.OK_CANCEL_OPTION);
			System.out.println(years.getSelectedItem() + "-" + months.getSelectedItem() + "-" + days.getSelectedItem() + " " + timeBox.getSelectedItem());
			*/
		}
		return 0;
		
	}
	
	private void showTableInfo(Object table, boolean reserved, String bookedDate, String bookedname) {
		
		JLabel status = new JLabel();
		JPanel panel = new JPanel();
		if(!reserved) {
			status.setText("Inga reservationer");
		}else if(reserved) {
			status.setText("<html><strong>Reserverat: </strong>" + bookedDate +"<br><strong>Namn: </strong>"+ bookedname + "</html>");
		}
		panel.add(status);
		int result = JOptionPane.showConfirmDialog(this, panel, "Information",JOptionPane.OK_CANCEL_OPTION);
		System.out.println(result);

	}
	
	private int alreadyBooked(Object table) {
		Object[] options = {"Avbryt","Avboka"};
		int result = JOptionPane.showOptionDialog(this, "Vad vill ni göra?", "Bokning av bord " + table, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,null, options,null);
		if(result == 1) {
			JOptionPane.showMessageDialog(this, "Bord " + table  + " är nu avbokat!");
			return 1;
		
		}
		return 0;
	}
	
	private int alreadyReserved(Object table, boolean reserved, String bookedDate, String bookedname) {
		Object[] options = {"Avbryt","Avreservera", "Info"};
		int result = JOptionPane.showOptionDialog(this, "Vad vill ni göra?", "Hantering av reservationer" + table, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,null, options,null);
		if(result == 1) {
			JOptionPane.showMessageDialog(this, "Bord " + table  + " är nu avreserverat!");
			return 1;
		
		} else if(result == 2) {
			showTableInfo(table,reserved,bookedDate,bookedname);
		}
		return 0;
	}
	
	private int bookOrReservate(Object table, boolean reserved, String bookedDate, String bookedname) {
		Object[] options = {"Avbryt","Info","Reservera", "Boka nu"};
		int result = JOptionPane.showOptionDialog(this, "Vad vill ni göra?", "Bokning av bord " + table, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,null, options,null);
		if(result == 3) {
			JOptionPane.showMessageDialog(this, "Bord " + table  + " är nu bokat!");
			return 3;
		
		} else if(result == 1) {
			showTableInfo(table, reserved, bookedDate, bookedname);
		}else if(result == 2) {
			System.out.println(result);
			return reserveTable();
		}
		
		return 0;
	}

	@Override
	public void update(Observable o, Object arg) {
		Model model = (Model)o;
		Object t = model.getTable();
		int i = Integer.parseInt(t.toString());
		int seats = model.getSeats();
		String bookedDate = model.getBookedDate();
		String bookedname =  model.getBookedName();
		
		
		
				
		
		if(model.getTableBookedStatus()) {
			if(alreadyBooked(t) == 1) {
				tables[i-1].setText("<html><strong>Bord " + t + " <br>" + seats + " pers</strong><html>");
				model.deBookTable();
			}
		}else if(model.getReserved()) {
			int reserved = alreadyReserved(t,model.getReserved(),bookedDate,bookedname);
			if(reserved == 1) {
				tables[i-1].setText("<html><strong>Bord " + t + " <br>" + seats + " pers</strong><html>");
				model.dereserve();
			}else if(reserved == 2) {
				//showTableInfo(t,model.getReserved(),bookedDate,bookedname);
			}	
		}else if(!model.getTableBookedStatus() || !model.getReserved()) {
			int ye = bookOrReservate(t,model.getReserved(),bookedDate,bookedname);
			if(ye == 3) {
				if(model.bookTable()) {
					tables[i-1].setText("<html><strong>Bord " + t + " <br>bokat!</strong><html>");
				}
			}else if(ye == 1) {
				
				model.reserve(returnReservationValue());
				if(model.getReserved()) {
					tables[i-1].setText("<html><strong>Bord " + t + " <br>reserverat!</strong><html>");
				}
			}
		}
		
		
		
		
		
	}
	
}
