import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Observable;

/*
 * 
 * Author: Andreas Hortlund
 */

public class Model extends Observable {
	//Sätter variabler som används senare i funktioner.
	private Tables currentTable;
	private Tables[] tables = new Tables[17];
	
	//Konstruktor till modellen som skapar nya bord av tables klassen. Sätter även ett värde för hur mångs som får plats vid bordet och bordets id.
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
	
	//Körs när man klickar på ett bord.
	public void test(String table) {
		//Sätter currentTable till det akutella bordet som klickats i arrayen, använder siffran i bordsnamnet för detta.
		currentTable = tables[Integer.parseInt(table.substring(4))];
		setChanged();
		notifyObservers();
	}
	
	//returnerar nuvarande bordet.
	public Tables getTable() {
		
		return currentTable;
	}
	//Sätter bordet till bokat.
	public boolean bookTable() {
		currentTable.booked = true;
		return currentTable.booked;
	}
	//returnerar bokatstatusen.
	public boolean getTableBookedStatus() {
		return currentTable.booked;
	}
	//Avbokar ett bord.
	public void deBookTable() {
		currentTable.booked = false;
	}
	//returnerar antalet platser som används till texten i knappen för bordet.
	public int getSeats() {
		return currentTable.seats;
	}
	
	//Returnerar namnet som den har bokat i.
	public String getBookedName() {
		return currentTable.name;
	}
	//returnerar datumet som det är reserverat i.
	public String getBookedDate() {
		return currentTable.date;
	}
	
	
	
	
	//reserverar ett bord och kollar så att datumet är framför oss.
	public boolean reserve(String[]b) {
		//Regex för att separera de olika värdena i strängen som kommer in. separerad vid '-' och ' ' och ':'
		String[] dateTime = b[0].split("-|\\s+|:");
		
		//Sätter det värdena till ett nytt objekt av localdatetime till det som man väljer vid reserveringen.
		LocalDateTime current = LocalDateTime.of(Integer.parseInt(dateTime[0]), Month.valueOf(dateTime[1]), Integer.parseInt(dateTime[2]), Integer.parseInt(dateTime[3]), Integer.parseInt(dateTime[4]));
		
		//Kollar ifall det är efter just nu och reserverar isåfall detta.
		if(current.isAfter(LocalDateTime.now())) {
			currentTable.reserved = true;
			//Sätter till namet i strängen.
			currentTable.name = b[1];
			//sätter datumet i strängen.
			currentTable.date = b[0];
			return true;
		}
		//Annars nej nej.
		return false;
	}
	
	//returnerar reservd status.
	public boolean getReserved() {
		
		return currentTable.reserved;
	}
	
	//avreserverar bordet.
	public void dereserve() {
		currentTable.reserved = false;
		currentTable.name = "";
		currentTable.date = "";
	}
	
	
	//en klass som borden skapas efter. Efter som jag bara tillåter 1 reservation per bord gjorde jag på detta sätt istället för att använda typ en hashmap.
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
