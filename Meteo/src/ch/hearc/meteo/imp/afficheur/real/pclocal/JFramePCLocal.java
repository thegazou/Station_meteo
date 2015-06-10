
package ch.hearc.meteo.imp.afficheur.real.pclocal;

import java.awt.Dimension;

import javax.swing.JFrame;

import ch.hearc.meteo.imp.com.real.MeteoPortDetectionService;

public class JFramePCLocal extends JFrame
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JFramePCLocal()
		{
		meteoPortDetectionService= new MeteoPortDetectionService();
		geometry();
		control();
		appearance();
		}


	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		// JComponent : Instanciation
		choice = new JPanelChoice(meteoPortDetectionService);
		// JComponent : add
		add(choice);
		//add(TODO,BorderLayout.CENTER);
		}

	private void control()
		{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		}

	private void appearance()
		{
		setSize(600, 400);
		setLocationRelativeTo(null); // frame centrer
		setVisible(true); // last!
		this.setSize(new Dimension(200, 100));
		this.setPreferredSize(new Dimension(200, 100));
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private JPanelChoice choice;
	private MeteoPortDetectionService meteoPortDetectionService;
	}
