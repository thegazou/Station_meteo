
package ch.hearc.meteo.imp.com.real;

import ch.hearc.meteo.imp.com.logique.MeteoService_A;
import ch.hearc.meteo.imp.com.real.com.ComConnexions_I;
import ch.hearc.meteo.spec.com.meteo.exception.MeteoServiceException;

/**
 * <pre>
 * Repousse les requêtes sur comConnexion et transforme les Exceptions en MeteoServiceException
 * Aucune trace de javacomm ici, toute cette problematique est encapsuler dans l'objet implementant ComConnexions_I (separation des couches)
 * </pre>
 */
public class MeteoService extends MeteoService_A
	{

	/**
	 * <pre>
	 * Problem :
	 * 		MeteoService est un MeteoServiceCallback_I
	 * 		ComConnexions_I utilise MeteoServiceCallback_I
	 * 		MeteoService utilise ComConnexions_I
	 *
	 * On est dans la situation
	 * 		A(B)
	 * 		B(A)
	 *
	 * Solution
	 * 		 B
	 * 		 A(B)
	 *  	 B.setA(A)
	 *
	 *  Autrement dit:
	 *
	 *		ComConnexions_I comConnexion=new ComConnexion( portName,  comOption);
	 *      MeteoService_I meteoService=new MeteoService(comConnexion);
	 *      comConnexion.setMeteoServiceCallback(meteoService);
	 *
	 *      Ce travail doit se faire dans la factory
	 *
	 *  Warning : call next
	 *  	setMeteoServiceCallback(MeteoServiceCallback_I meteoServiceCallback)
	 *
	 *  Faire tout ca dans MeteoFactory
	 *
	 *  </pre>
	 */
	public MeteoService(ComConnexions_I comConnexion)
		{
		super(comConnexion.getNamePort());

		this.comConnexion = comConnexion;
		}

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/**
	 * assynchrone, when data "value" received , must call altitudePerformed(value) of MeteoServiceCallback_I
	 */
	@Override public void askAltitudeAsync() throws MeteoServiceException
		{
		try
			{
			comConnexion.askAltitudeAsync();
			}
		catch (Exception e)
			{
			throw new MeteoServiceException("[MeteoService] askAltitudeAsync failure", e);
			}
		}

	/**
	 * assynchrone, when data "value" received , must call pressionPerformed(value) of MeteoServiceCallback_I
	 */
	@Override public void askPressionAsync() throws MeteoServiceException
		{
		try
			{
			comConnexion.askPressionAsync();
			}
		catch (Exception e)
			{
			throw new MeteoServiceException("[MeteoService] : askPressionAsync failure", e);
			}
		}

	/**
	 * assynchrone, when data "value" received , must call temperaturePerformed(value) of MeteoServiceCallback_I
	 */
	@Override public void askTemperatureAsync() throws MeteoServiceException
		{
		try
			{
			comConnexion.askTemperatureAsync();
			}
		catch (Exception e)
			{
			throw new MeteoServiceException("[MeteoService] : askTemperatureAsync failure", e);
			}
		}

	@Override protected void connectHardware() throws MeteoServiceException
		{
		try
			{
			comConnexion.connect();
			}
		catch (Exception e)
			{
			throw new MeteoServiceException("[MeteoService] : connect failure", e);
			}

		}

	@Override protected void disconnectHardware() throws MeteoServiceException
		{
		try
			{
			comConnexion.disconnect();
			}
		catch (Exception e)
			{
			throw new MeteoServiceException("[MeteoService] : disconnect failure", e);
			}

		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private ComConnexions_I comConnexion;



	}
