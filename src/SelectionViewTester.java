import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

public class SelectionViewTester 
{
	public static void main(String[] args) 
	{
		SelectionView sv = new SelectionView();	
		
		// Testing register file menu listener...
		sv.registerFileMenuListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				System.out.println(((JMenuItem)(e.getSource())).getText() 
						+ " item was clicked.");
			}
		});
	}
}
