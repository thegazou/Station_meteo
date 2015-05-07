
package ch.hearc.meteo.spec.com.meteo;

import ch.hearc.meteo.spec.com.meteo.exception.MeteoServiceException;
import ch.hearc.meteo.spec.com.meteo.listener.MeteoListener_I;

public interface MeteoService_I
	{

	public void connect() throws MeteoServiceException;

	public void disconnect() throws MeteoServiceException;

	/**
	 * DT = Delta Time in ms
	 */
	public void start(MeteoServiceOptions meteoServiceOptions);

	public void stop();

	public boolean isRunning();

	public boolean isConnect();

	public String getPort();

	/**
	 * utile si on utilise la technique du pooling pour l'update par le GUI (sliders,...) de  dt
	 */
	public MeteoServiceOptions getMeteoServiceOptions();

	/**
	 * Utile pour que le gui puisse changer valeur du modele
	 */
	public void setMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions);

	public boolean addMeteoListener(MeteoListener_I listener);

	public boolean removeMeteoListener(MeteoListener_I listener);

	}
