
package ch.hearc.meteo.imp.afficheur.real.station;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JFrame;

import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;



public class JFrameMeteo extends JFrame implements AfficheurService_I
	{
	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JFrameMeteo()
		{

		geometry();
		control();
		appearance();
		}
	/*------------------------------------------------------------------*\
	|*							Methodes Public  						*|
	\*------------------------------------------------------------------*/
	@Override public void printPression(MeteoEvent event)
		{

		nom=event.getSource().toString();
		setNom();
		stationPressiongraph.printPression(event);
		stationInformation.printPression(event);
		}

	@Override public void printAltitude(MeteoEvent event)
		{
		stationInformation.printAltitude(event);

		}

	@Override public void printTemperature(MeteoEvent event)
		{
		stationInformation.printTemperature(event);
		stationMeteoGraph.printTemperature(event);
		stationMeteoGraphVisual.printTemperature(event);
		}

	@Override public void updateMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions)
		{
		// TODO Auto-generated method stub

		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
			// JComponent : Instanciation
			stationFormular= new JPanelFormulaireStationMeteo();
			stationPressiongraph = new JPanelPressionGraphe();
			stationInformation = new JPanelInformationStation();
			stationMeteoGraph= new JPanelMeteoGraphe();
			stationMeteoGraphVisual= new JPanelMeteoGrapheVisual();

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
		//addWindowListener(l)
		}

	private void appearance()
		{
		setSize(1000, 650);
		setLocationRelativeTo(null); // frame centrer
		setVisible(true); // last!
		}
	private void setNom()
	{
	setTitle(nom);
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
	private String nom;


	}

