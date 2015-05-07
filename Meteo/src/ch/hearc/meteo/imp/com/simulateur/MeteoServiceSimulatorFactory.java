
package ch.hearc.meteo.imp.com.simulateur;

import ch.hearc.meteo.spec.com.meteo.MeteoServiceFactory_I;
import ch.hearc.meteo.spec.com.meteo.MeteoService_I;

/**
* On pourrait aussi faire un singleton
*/
public class MeteoServiceSimulatorFactory implements MeteoServiceFactory_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public MeteoServiceSimulatorFactory()
		{
		// rien
		}

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
	@Override public MeteoService_I create(String portName)
		{
		return new MeteoServiceSimulateur(portName);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	}
