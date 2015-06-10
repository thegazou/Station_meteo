
package ch.hearc.meteo.imp.com.real;

import ch.hearc.meteo.imp.com.logique.MeteoServiceCallback_I;
import ch.hearc.meteo.imp.com.real.com.ComConnexion;
import ch.hearc.meteo.imp.com.real.com.ComConnexions_I;
import ch.hearc.meteo.imp.com.real.com.ComOption;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceFactory_I;
import ch.hearc.meteo.spec.com.meteo.MeteoService_I;

public class MeteoServiceFactory implements MeteoServiceFactory_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/
	/**
	 * <pre>
	 * Example:
	 * 		Windows : namePort=COM1
	 * 		Linux	: ??
	 * 		Mac 	: ??
	 * </pre>
	 */
	@Override
	public MeteoService_I create(String portName)
		{

		//		ComOption comOption = new ComOption();
		//		ComConnexion comConnexion = new ComConnexion("COM4", comOption);
		//		MeteoService_I meteoServiceReal = new MeteoService(comConnexion);
		//		comConnexion.setMeteoServiceCallback((MeteoServiceCallback_I)meteoServiceReal);
		ComOption comOption = new ComOption();
		ComConnexions_I comConnexion = new ComConnexion(portName, comOption);
		MeteoService_I meteoService = new MeteoService(comConnexion);
		comConnexion.setMeteoServiceCallback((MeteoServiceCallback_I)meteoService);

<<<<<<< HEAD
		return meteoService;
=======
		return meteoService; //MeteoServiceSimulateur(portName);
>>>>>>> 2c698b199b0101742a0c916cacba81f66c3c8ad8
		}
	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	}
