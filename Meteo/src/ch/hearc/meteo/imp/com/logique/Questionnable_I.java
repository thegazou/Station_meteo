
package ch.hearc.meteo.imp.com.logique;

import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.exception.MeteoServiceException;

public interface Questionnable_I
	{

	/**
	 * assynchrone, when data "value" received , must call altitudePerformed(value)
	 */
	public void askAltitudeAsync() throws MeteoServiceException;

	/**
	 * assynchrone, when data "value" received , must call pressionPerformed(value)
	 */
	public void askPressionAsync() throws MeteoServiceException;

	/**
	 * assynchrone, when data "value" received , must call temperaturePerformed(value)
	 */
	public void askTemperatureAsync() throws MeteoServiceException;

	public MeteoServiceOptions getMeteoServiceOptions();

	}
