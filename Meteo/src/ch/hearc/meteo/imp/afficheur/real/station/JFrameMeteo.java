
package ch.hearc.meteo.imp.afficheur.real.station;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JFrame;

import ch.hearc.meteo.imp.afficheur.real.afficheur.AfficheurServiceMOOReal;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;



public class JFrameMeteo extends JFrame
	{
	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JFrameMeteo(AfficheurServiceMOOReal afficheurServiceMOOReal, boolean isCentral)
		{
		this.afficheurServiceMOOReal=afficheurServiceMOOReal;
		this.isCentral=isCentral;
		geometry();
		control();
		appearance();
		}
	/*------------------------------------------------------------------*\
	|*							Methodes Public  						*|
	\*------------------------------------------------------------------*/
	public void refresh()
		{
		stationFormular.update();
		stationPressiongraph.update();
		stationInformation.update();
		stationMeteoGraph.update();
		stationMeteoGraphVisual.update();
		}
	public void updateMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions)
		{
		stationFormular.updateMeteoServiceOptions(meteoServiceOptions);
		}


	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
			// JComponent : Instanciation
			stationFormular= new JPanelFormulaireStationMeteo(afficheurServiceMOOReal);
			stationPressiongraph = new JPanelPressionGraphe(afficheurServiceMOOReal);
			stationInformation = new JPanelInformationStation(afficheurServiceMOOReal);
			stationMeteoGraph= new JPanelMeteoGraphe(afficheurServiceMOOReal);
			stationMeteoGraphVisual= new JPanelMeteoGrapheVisual(afficheurServiceMOOReal);
			setLayout();
		}

	private void setLayout()
		{
		Box left = Box.createVerticalBox();
		left.add(stationInformation);
		left.add(stationMeteoGraph);
		left.add(stationFormular);
		left.add(Box.createHorizontalGlue());

		Box right = Box.createVerticalBox();
		right.add(stationMeteoGraphVisual);
		right.add(stationPressiongraph);

		Box line= Box.createHorizontalBox();
		line.add(left);
		line.add(right);



		setLayout(new BorderLayout());
	    add(line, BorderLayout.CENTER);


		}
	private void control()
		{
		}

	private void appearance()
		{
		if (isCentral)
			{
			setTitle("Central " + afficheurServiceMOOReal.getTitre());
			}
		else
			{
			setTitle(afficheurServiceMOOReal.getTitre());
			}
		setSize(1000, 650);
		setLocationRelativeTo(null); // frame centrer
		setVisible(true); // last!
		}
	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools

	private JPanelFormulaireStationMeteo stationFormular;
	private JPanelPressionGraphe stationPressiongraph;
	private JPanelInformationStation stationInformation;
	private JPanelMeteoGraphe stationMeteoGraph;
	private JPanelMeteoGrapheVisual stationMeteoGraphVisual;
	private AfficheurServiceMOOReal afficheurServiceMOOReal;
	private boolean isCentral;





	}

