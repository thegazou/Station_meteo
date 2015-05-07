
package ch.hearc.meteo.spec.reseau.rmiwrapper;

import java.rmi.RemoteException;

import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;

public class AfficheurServiceWrapper implements AfficheurServiceWrapper_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public AfficheurServiceWrapper(AfficheurService_I afficheurService)
		{
		this.afficheurService = afficheurService;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override public void printPression(MeteoEvent event) throws RemoteException
		{
		afficheurService.printPression(event);
		}

	@Override public void printAltitude(MeteoEvent event) throws RemoteException
		{
		afficheurService.printAltitude(event);
		}

	@Override public void printTemperature(MeteoEvent event) throws RemoteException
		{
		afficheurService.printTemperature(event);
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	@Override public void updateMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions)
		{
		afficheurService.updateMeteoServiceOptions(meteoServiceOptions);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private AfficheurService_I afficheurService;


	}
