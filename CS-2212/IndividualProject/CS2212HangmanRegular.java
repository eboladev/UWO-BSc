import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.*;

public class CS2212HangmanRegular {

	private static JFrame frame1;

	public JFrame createFrame() {
		return frame1;
	}

	public static void main(String[] args) {
		{

			final Panel panel = new Panel();
			panel.makePanel();
			panel.makeMenuBar();

			frame1 = new JFrame("Matthew Stokes's Awesome Game of Hangman");
			frame1.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					try {
						PrintWriter out = new PrintWriter(new FileWriter(
								"cat.txt"));
						String text = new String();
						text = panel.cat1.getText();
						out.println(text);
						text = panel.cat2.getText();
						out.println(text);
						out.close();

					} catch (IOException e2) {
					}
					;

					System.exit(0);

				}
			});
			frame1.setJMenuBar(panel.getMenu());
			frame1.getContentPane().add(panel.getPanel());
			// frame1.getContentPane().add(b.getPlayer1Panel());
			// frame1.pack(); // Ready to go
			frame1.setLocation(200, 50); // location
			frame1.setSize(800, 600); // size of the frame
			frame1.setVisible(true);

			while (1 > 0) { // back door method to fix time delay
				if (panel.getP2Word() != null)
					if (panel.getP2Word().length() >= 1) {
						panel.makeHangMan1();
						break;
					}

			}
			panel.solving();
			while (1 > 0) {
				if (panel.firstWordSolved == true) {

					frame1.repaint();
					break;
				}
			}
		}

	}
}
