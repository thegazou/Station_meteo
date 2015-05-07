
package ch.hearc.meteo.imp.com.real.com;

/**
 * Le cosntructeur de ComConnexions doit avoir comme paramètre d'entrée un MeteoServiceCallback_I
 */
public interface ComConnexions_I
	{

	/**
	 * Open stream
	 */
	public void connect() throws Exception;

	/**
	 * Close stream
	 */
	public void disconnect() throws Exception;

	public String getNamePort();

	/**
	 * assynchrone, when data "value" received , must call altitudePerformed(value) of MeteoServiceCallback_I
	 */
	public void askAltitudeAsync() throws Exception;

	/**
	 * assynchrone, when data "value" received , must call pressionPerformed(value) of MeteoServiceCallback_I
	 */
	public void askPressionAsync() throws Exception;

	/**
	 * assynchrone, when data "value" received , must call temperaturePerformed(value) of MeteoServiceCallback_I
	 */
	public void askTemperatureAsync() throws Exception;

	/**
	 * add listener on the port com
	 */
	public void start() throws Exception;

	/**
	 * remove listener on the port com
	 */
	public void stop() throws Exception;

	}
