
package ch.hearc.meteo.spec.com.meteo;

public interface MeteoServiceFactory_I
	{

	/**
	 * <pre>
	 * Example:
	 * 		Windows : namePort=COM1
	 * 		Linux	: ??
	 * 		Mac 	: ??
	 * </pre>
	 */
	public MeteoService_I create(String portName);

	}
