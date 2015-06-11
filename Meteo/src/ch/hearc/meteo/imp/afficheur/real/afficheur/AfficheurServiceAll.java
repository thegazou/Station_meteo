
package ch.hearc.meteo.imp.afficheur.real.afficheur;



import ch.hearc.meteo.imp.afficheur.real.station.JFrameMeteo;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper_I;

public class AfficheurServiceAll implements AfficheurService_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/**
	 * n = #data to print
	 */
	public AfficheurServiceAll(AffichageOptions affichageOptions, MeteoServiceWrapper_I meteoServiceRemote, boolean isCentral)
		{
		afficheurServiceMOOReal = new AfficheurServiceMOOReal(affichageOptions, meteoServiceRemote);
		//JFramePCCentrale.addStation(affichageOptions, meteoServiceRemote);
		meteo= new JFrameMeteo(afficheurServiceMOOReal, isCentral);

		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override public void printAltitude(MeteoEvent event)
		{
		afficheurServiceMOOReal.printAltitude(event);
		meteo.refresh();
		}

	@Override public void printTemperature(MeteoEvent event)
		{
		afficheurServiceMOOReal.printTemperature(event);
		meteo.refresh();
		}

	@Override public void printPression(MeteoEvent event)
		{
		afficheurServiceMOOReal.printPression(event);
		meteo.refresh();
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	@Override public void updateMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions)
		{
		meteo.updateMeteoServiceOptions(meteoServiceOptions);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private AfficheurServiceMOOReal afficheurServiceMOOReal;
	private JFrameMeteo meteo;

	}
