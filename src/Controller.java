import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Controller extends WindowAdapter implements ActionListener {
	private Model model;
	public Controller(Model model) {
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		model.test(e.getActionCommand());
		
		
		
		
	}
	
	public void windowClosing(WindowEvent e){
        System.exit(1);
    }

}
