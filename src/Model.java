import java.util.Observable;

public class Model extends Observable {
	private String currentTable;
	public void test(String table) {
		currentTable = table;
		System.out.println("in model bord 1");
		setChanged();
		notifyObservers();
	}
	
	public String getTable() {
		
		return currentTable;
	}

}
