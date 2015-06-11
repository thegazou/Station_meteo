
package ch.hearc.meteo.imp.afficheur.real.afficheur;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper_I;

public class AfficheurServiceMOOReal
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public AfficheurServiceMOOReal(AffichageOptions affichageOptions, MeteoServiceWrapper_I meteoServiceRemote)
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
		statAltitude = new StateReal();
		statPression = new StateReal();
		statTemperature = new StateReal();
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

			}
		}

	public void printPression(MeteoEvent event)
		{
		if (!isPause)
			{
			manage(listPression, event);
			statPression.update(event.getValue());

			}
		}

	public void printTemperature(MeteoEvent event)
		{
		if (!isPause)
			{
			manage(listTemperature, event);
			statTemperature.update(event.getValue());

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

	public StateReal getStatAltitude()
		{
		return this.statAltitude;
		}

	public StateReal getStatPression()
		{
		return this.statPression;
		}

	public StateReal getStatTemperature()
		{
		return this.statTemperature;
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	public void setMeteoServiceRemote(MeteoServiceWrapper_I meteoServiceRemote)
		{
		this.meteoServiceRemote = meteoServiceRemote;
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

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private AffichageOptions affichageOptions;
	private MeteoServiceWrapper_I meteoServiceRemote;

	// Tools
	private List<MeteoEvent> listAltitude;
	private List<MeteoEvent> listPression;
	private List<MeteoEvent> listTemperature;

	// Outputs
	private StateReal statAltitude;
	private StateReal statPression;
	private StateReal statTemperature;

	private boolean isPause;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static final String ESPACE = " ";

	}
