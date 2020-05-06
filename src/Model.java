import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Observable;

public class Model extends Observable {
	private Tables currentTable;
	private Tables[] tables = new Tables[17];
	private int btnTable;
	
	public Model() {
		for(int i = 0; i < tables.length; i++) {
			tables[i] = new Tables();
			if(i <= 10) {
				tables[i].seats = 4;
			}else if(i == 13) {
				tables[i].seats = 6;
			}else {
				tables[i].seats = 2;
			}
			tables[i].id = i;
		}
	}
	
	public void test(String table) {
		
		currentTable = tables[Integer.parseInt(table.substring(4))];
		btnTable = Integer.parseInt(table.substring(4));
		setChanged();
		notifyObservers();
	}
	
	public Tables getTable() {
		
		return currentTable;
	}
	
	public boolean bookTable() {
		currentTable.booked = true;
		return currentTable.booked;
	}
	
	public boolean getTableBookedStatus() {
		return currentTable.booked;
	}
	
	public void deBookTable() {
		currentTable.booked = false;
	}
	
	public int getSeats() {
		return currentTable.seats;
	}
	
	public String getBookedName() {
		return currentTable.name;
	}
	public String getBookedDate() {
		return currentTable.date;
	}
	
	
	
	public boolean getReserved() {
		
		return currentTable.reserved;
	}
	
	public boolean reserve(String[]b) {
		//LocalDateTime.parse(b[0], formatter)
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String[] dateTime = b[0].split("-|\\s+|:");
		
		LocalDateTime current = LocalDateTime.of(Integer.parseInt(dateTime[0]), Month.valueOf(dateTime[1]), Integer.parseInt(dateTime[2]), Integer.parseInt(dateTime[3]), Integer.parseInt(dateTime[4]));
		
		if(current.isAfter(LocalDateTime.now())) {
			currentTable.reserved = true;
			currentTable.name = b[1];
			currentTable.date = b[0];
			return true;
		}
		
		return false;
	}
	
	public void dereserve() {
		currentTable.reserved = false;
		currentTable.name = "";
		currentTable.date = "";
	}
	
	
	
	public class Tables{
		
		private int id;
		private int seats;
		private boolean booked;
		private boolean reserved;
		private String name;
		private String date;
		
		public Tables() {
			booked = false;
			reserved = false;
			date = null;
		}
		
		public String toString() {
			return String.valueOf(id);
			
		}

		
	}

}
