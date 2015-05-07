
package ch.hearc.meteo.imp.afficheur.simulateur.moo;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEventType_E;
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper_I;

public class AfficheurServiceMOO
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public AfficheurServiceMOO(AffichageOptions affichageOptions, MeteoServiceWrapper_I meteoServiceRemote)
		{
		// Inputs
		this.affichageOptions = affichageOptions;
		this.meteoServiceRemote = meteoServiceRemote;

		//Tools
		listAltitude = new LinkedList<MeteoEvent>();
		listPression = new LinkedList<MeteoEvent>();
		listTemperature = new LinkedList<MeteoEvent>();

		isPause = false;

		// Outputs
		statAltitude = new Stat();
		statPression = new Stat();
		statTemperature = new Stat();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void printAltitude(MeteoEvent event)
		{
		if (!isPause)
			{
			manage(listAltitude, event);
			statAltitude.update(event.getValue());

			afficherConsole(listAltitude, MeteoEventType_E.ALTITUDE.name() + ESPACE + affichageOptions.getTitre());
			}
		}

	public void printPression(MeteoEvent event)
		{
		if (!isPause)
			{
			manage(listPression, event);
			statPression.update(event.getValue());

			afficherConsole(listPression, MeteoEventType_E.PRESSION.name() + ESPACE + affichageOptions.getTitre());
			}
		}

	public void printTemperature(MeteoEvent event)
		{
		if (!isPause)
			{
			manage(listTemperature, event);
			statTemperature.update(event.getValue());

			afficherConsole(listTemperature, MeteoEventType_E.TEMPERATURE.name() + ESPACE + affichageOptions.getTitre());
			}
		}



	/*------------------------------*\
	|*				remote			*|
	\*------------------------------*/

	public void setMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions) throws RemoteException
		{
		meteoServiceRemote.setMeteoServiceOptions(meteoServiceOptions);
		}

	public MeteoServiceOptions getMeteoServiceOptions() throws RemoteException
		{
		return meteoServiceRemote.getMeteoServiceOptions();
		}

	/*------------------------------*\
	|*				Is				*|
	\*------------------------------*/

	public boolean isPause()
		{
		return isPause;
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/**
	 * service affichage only
	 */
	public void setPause(boolean etat)
		{
		isPause = etat;
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public String getTitre()
		{
		return this.affichageOptions.getTitre();
		}

	public MeteoServiceWrapper_I getMeteoServiceRemote()
		{
		return this.meteoServiceRemote;
		}

	public List<MeteoEvent> getListAltitude()
		{
		return this.listAltitude;
		}

	public List<MeteoEvent> getListPression()
		{
		return this.listPression;
		}

	public List<MeteoEvent> getListTemperature()
		{
		return this.listTemperature;
		}

	public Stat getStatAltitude()
		{
		return this.statAltitude;
		}

	public Stat getStatPression()
		{
		return this.statPression;
		}

	public Stat getStatTemperature()
		{
		return this.statTemperature;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void manage(List<MeteoEvent> listMeteoEvent, MeteoEvent event)
		{
		if (listMeteoEvent.size() == affichageOptions.getN())
			{
			listMeteoEvent.remove(0);
			}

		listMeteoEvent.add(event);
		}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static void afficherConsole(List<MeteoEvent> listMeteoEvent, String titre)
		{
		System.out.print("[AfficheurServiceMOO] : " + titre + " : ");
		for(MeteoEvent meteoEvent:listMeteoEvent)
			{
			System.out.print(meteoEvent.getValue() + " ");
			}
		System.out.println();

		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private AffichageOptions affichageOptions;
	private MeteoServiceWrapper_I meteoServiceRemote;
	private MeteoServiceOptions meteoServiceOptions;

	// Tools
	private List<MeteoEvent> listAltitude;
	private List<MeteoEvent> listPression;
	private List<MeteoEvent> listTemperature;

	// Outputs
	private Stat statAltitude;
	private Stat statPression;
	private Stat statTemperature;

	private boolean isPause;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static final String ESPACE = " ";

	}
