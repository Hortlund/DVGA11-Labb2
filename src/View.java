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

/*
 * 
 * Author: Andreas Hortlund
 */

public class View extends JFrame implements Observer {
	
	//Skapar olika paneler och layouter för att lägga upp designen på programmet.
	private JPanel firstRowTables, secondRowTables, wow,tRowTables, tRowTables2,total;
	private JLabel pic;
	private BorderLayout bLayout = new BorderLayout();
	private GridLayout layout = new GridLayout(2,1);
	//En array av knappar som ska representera borden
	private JButton[] tables = new JButton[16];
	//En sträng som innehåller datum och tid från reservationen.
	private String[]res = new String[2];
	
	//Snygg ikon
	private ImageIcon tic = new ImageIcon(new ImageIcon("src/ba.png").getImage().getScaledInstance(700, 600, Image.SCALE_DEFAULT));

	public View(Controller controller) {
		
		//skapar de olika panelerna
		firstRowTables = new JPanel();
		secondRowTables = new JPanel();
		tRowTables = new JPanel();
		tRowTables2 = new JPanel();
		wow = new JPanel();
		total = new JPanel();
		pic = new JLabel(tic);
		
		
		
		//Sätter layouterna
		firstRowTables.setLayout(new BoxLayout(firstRowTables, BoxLayout.Y_AXIS));
		secondRowTables.setLayout(new BoxLayout(secondRowTables, BoxLayout.Y_AXIS));
		tRowTables.setLayout(new BoxLayout(tRowTables, BoxLayout.X_AXIS));
		tRowTables2.setLayout(new BoxLayout(tRowTables2, BoxLayout.X_AXIS));
		
		//Lägger till olika marginaler för att få komponenterna dit jag vill.
		firstRowTables.add(Box.createVerticalGlue());
		secondRowTables.add(Box.createVerticalGlue());
		secondRowTables.add(Box.createVerticalStrut(10));
		tRowTables2.add(Box.createVerticalStrut(10));
		
		//Skapar knapparna och ger dem lyssnare och actioncommands
		for(int i = 0; i < tables.length; i++) {
			tables[i] = new JButton();
			tables[i].addActionListener(controller);
			tables[i].setActionCommand("bord" + (i+1));
			tables[i].setPreferredSize(new Dimension(100,100));
		}
		
		//De tre looparna är olka beroende på vilken typ av borde det är och ger dem olika text beroende på det. Lägger till dem till panelen som jag vill.
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
		
		//Strö bord som jag lägger till.
		tables[10].setText("<html><strong>Bord 11<br>2 pers</html>");
		tables[11].setText("<html><strong>Bord 12<br>2 pers</html>");
		tables[12].setText("<html><strong>Bord 13<br>6 pers</html>");
		tRowTables2.add(Box.createRigidArea(new Dimension(100,0)));
		tRowTables2.add(tables[10]);
		tRowTables2.add(tables[11]);
		tRowTables2.add(tables[12]);
		tRowTables2.add(Box.createRigidArea(new Dimension(100,0)));
		
		//Sätter layouter på JFrame och panelerna.
		this.setLayout(bLayout);
		total.add(firstRowTables);
		total.add(Box.createHorizontalStrut(500));
		total.add(secondRowTables);
		wow.setLayout(layout);
		wow.add(tRowTables2);
		wow.add(tRowTables);
		
		//Lägger till allting till JFramen och sätter lite olika attributer till dem.
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
	
	//Sätter strängen res till värdet av datumet som valdes.
	private void setReservationValue(String[]b) {
		res = b;
	}
	
	//returnerar detta värde.
	private String[] returnReservationValue() {
		return res;
	}
	
	//funktion som skapar en dialogruta för reservering av bord.
	private int reserveTable(Object table) {
		//skapar massa komponenter som används vid valet av datum.
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
		
		//Lägger till 10 år från och med det året som är nu.
		for (int i = 0; i <= 10; i++) {
			years.addItem(LocalDateTime.now().getYear()+i);
			   
		}
		//Lägger till årets månader och lägger till den som ett textvärde.
		for (int i = 1; i <= 12; i++) {
			months.addItem(LocalDateTime.of(LocalDateTime.now().getYear(), i, 1, 1,1).getMonth());
			   
		}
		//Funktion som räknar ut max antal dagar för månaden som är vald och lägger således till så många dagar i comboboxen.
		months.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				days.removeAllItems();
				for (int i = 1; i <= LocalDateTime.of(LocalDateTime.now().getYear(), months.getSelectedIndex()+1, 1, 1,1).getMonth().maxLength(); i++) {
					days.addItem(i);
				}
			}
			
		});
		
		//Lägger till timmarna och formaterar dem snyggt, finns säkert smidigare sätt för detta eller en funktion från localdatetime, men körde med detta.
		for(int i = 0; i < 24; i++) {
			if(i < 1) {
				timeBox.addItem(i + "0:00");
			}else if(i < 10){
				timeBox.addItem("0"+i+":00");
			}else {
				timeBox.addItem(i+":00");
			}
			
		}
		//Lägger till allting till panelen och väljer default värden.
		months.setSelectedIndex(LocalDateTime.now().getMonthValue()-1);
		days.setSelectedIndex(LocalDateTime.now().getDayOfMonth()-1);
		timeBox.setSelectedIndex(LocalDateTime.now().getHour()+1);
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
			//beroende på resultat av valet från dialogrutan så visas den igen eller går vidare med reserveringen.
		}else if(result == 0) {
			if(nameField.getText() == null || nameField.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Ett namn måste anges vid reservation!");
				reserveTable(table);
			}else {
				String[]b = {years.getSelectedItem() + "-" + months.getSelectedItem() + "-" + days.getSelectedItem() + " " + timeBox.getSelectedItem(),nameField.getText()};
				setReservationValue(b);
				return 1;
			}
		}
		return 0;
		
	}
	
	//en funktion som visar info om bordet och vilka reservationer som finns.
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

	}
	
	//används ifall bordet redan ör bokat och visar olika alternativ ifall så är fallet istället för den vanliga bokningsrutan.
	private int alreadyBooked(Object table) {
		Object[] options = {"Avbryt","Avboka"};
		int result = JOptionPane.showOptionDialog(this, "Vad vill ni göra?", "Bokning av bord " + table, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,null, options,null);
		if(result == 1) {
			JOptionPane.showMessageDialog(this, "Bord " + table  + " är nu avbokat!");
			return 1;
		
		}
		return 0;
	}
	//används ifall bordet redan är reserverat och visar olika alternativ ifall så är fallet istället för den vanliga bokningsrutan.
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
		//En array av val istättet för rutans vanliga yes,no,cancel.
		Object[] options = {"Avbryt","Info","Reservera", "Boka nu"};
		int result = JOptionPane.showOptionDialog(this, "Vad vill ni göra?", "Bokning av bord " + table, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,null, options,null);
		if(result == 3) {
			JOptionPane.showMessageDialog(this, "Bord " + table  + " är nu bokat!");
			return 3;
		
		} else if(result == 1) {
			showTableInfo(table, reserved, bookedDate, bookedname);
		}else if(result == 2) {
			return reserveTable(table);
		}
		
		return 0;
	}

	//Update metod som körs när modelen har uppdaterats.
	@Override
	public void update(Observable o, Object arg) {
		//Sätter lite variabler från modellen som används senare.
		Model model = (Model)o;
		Object t = model.getTable();
		int i = Integer.parseInt(t.toString());
		int seats = model.getSeats();
		String bookedDate = model.getBookedDate();
		String bookedname =  model.getBookedName();
		
		
		
				
		//Ifall bordet är bokat så går den in och kollar valet från alreadyBooked och ifall man valt att avboka så görs detta.
		if(model.getTableBookedStatus()) {
			if(alreadyBooked(t) == 1) {
				tables[i-1].setText("<html><strong>Bord " + t + " <br>" + seats + " pers</strong><html>");
				model.deBookTable();
			}
			//Kollar status på reservation och isåfall går den in och kör alreadyreserved ifall man då väljer att avreservera så körs detta.
		}else if(model.getReserved()) {
			int reserved = alreadyReserved(t,model.getReserved(),bookedDate,bookedname);
			if(reserved == 1) {
				tables[i-1].setText("<html><strong>Bord " + t + " <br>" + seats + " pers</strong><html>");
				model.dereserve();
			}
			//Ifall bordet verken ör bokat eller reserverat så körs detta.
		}else if(!model.getTableBookedStatus() || !model.getReserved()) {
			//Sparar resultat från funktionen.
			int ye = bookOrReservate(t,model.getReserved(),bookedDate,bookedname);
			//ifall man valt boka så bokas border.
			if(ye == 3) {
				if(model.bookTable()) {
					tables[i-1].setText("<html><strong>Bord " + t + " <br>bokat!</strong><html>");
				}
				//Annars reserverar vi
			}else if(ye == 1) {
				//returnerar den falskt och datumet då är förlutet så visas detta.
				if(!model.reserve(returnReservationValue())) {
					JOptionPane.showMessageDialog(this, "<html>Datumet är i det förflutna, Välj ett annat</html>");
				}
				//Annars reserverar vi ifall det är true.
				if(model.reserve(returnReservationValue())) {
					JOptionPane.showMessageDialog(this, "Bord " + t  + " är nu reserverat!");
				}
				//sätter sen texten på bordet till reserverat.
				if(model.getReserved()) {
					tables[i-1].setText("<html><strong>Bord " + t + " <br>reserverat!</strong><html>");
				}
			}
		}
		
		
		
		
		
	}
	
}
