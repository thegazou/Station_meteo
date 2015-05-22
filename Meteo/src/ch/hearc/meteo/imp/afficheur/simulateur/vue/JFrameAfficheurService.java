
package ch.hearc.meteo.imp.afficheur.simulateur.vue;

import javax.swing.JFrame;

import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;

public class JFrameAfficheurService extends JFrame
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JFrameAfficheurService(AfficheurServiceMOO afficheurServiceMOO, boolean isCentral)
		{
		this.afficheurServiceMOO = afficheurServiceMOO;
		this.isCentral = isCentral;
		geometry();
		control();
		apparence();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void refresh()
		{
		panelRoot.update();
		}

	public void updateMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions)
		{
		panelRoot.updateMeteoServiceOptions(meteoServiceOptions);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		panelRoot = new JPanelRoot(afficheurServiceMOO);
		add(panelRoot);
		}

	private void control()
		{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		}

	private void apparence()
		{
		if (isCentral)
			{
			setTitle("Central " + afficheurServiceMOO.getTitre());
			}
		else
			{
			setTitle(afficheurServiceMOO.getTitre());
			}

		setSize(500, 550);
		setResizable(false);
		setVisible(true);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private AfficheurServiceMOO afficheurServiceMOO;
	private boolean isCentral;
	// Tools
	private JPanelRoot panelRoot;

	}
