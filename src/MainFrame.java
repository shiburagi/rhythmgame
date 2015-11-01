import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3286664591307289162L;

	private Container canvas;

	private MainPanel mp;

	public MainFrame() {
		canvas = getContentPane();
		canvas.setLayout(new BorderLayout());
		
		mp = new MainPanel(this);
		canvas.add(mp, BorderLayout.CENTER);
		
		/**
		 * Key listener
		 */
		mp.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				for (int j = 0; j < Variable.keyNoteCode.length; j++) {
					if(e.getKeyCode() == Variable.keyNoteCode[j]){	
						mp.keyRelease(j);
						return;
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_F2){
					mp.startGame();
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				for (int j = 0; j < Variable.keyNoteCode.length; j++) {
					if(e.getKeyCode() == Variable.keyNoteCode[j]){	
						mp.keyPress(j);
						return;
					}
				}
				
			}
		});
		mp.setFocusable(true);

		this.setSize(220, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new MainFrame().setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

}
