
package ch.hearc.meteo.spec.reseau.rmiwrapper;

import java.rmi.RemoteException;

import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoService_I;
import ch.hearc.meteo.spec.com.meteo.exception.MeteoServiceException;

public class MeteoServiceWrapper implements MeteoServiceWrapper_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public MeteoServiceWrapper(MeteoService_I meteoService)
		{
		this.meteoService = meteoService;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override public void start(MeteoServiceOptions meteoServiceOptions) throws RemoteException
		{
		meteoService.start(meteoServiceOptions);
		}

	@Override public void stop() throws RemoteException
		{
		meteoService.stop();
		}

	@Override public boolean isRunning() throws RemoteException
		{
		return meteoService.isRunning();
		}

	@Override public boolean isConnect() throws RemoteException
		{
		return meteoService.isConnect();
		}

	@Override public void connect() throws RemoteException, MeteoServiceException
		{
		meteoService.connect();
		}

	@Override public void disconnect() throws RemoteException, MeteoServiceException
		{
		meteoService.disconnect();
		}

	@Override public String getPort() throws RemoteException
		{
		return meteoService.getPort();
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	@Override public MeteoServiceOptions getMeteoServiceOptions() throws RemoteException
		{
		return meteoService.getMeteoServiceOptions();
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	@Override public void setMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions) throws RemoteException
		{
		meteoService.setMeteoServiceOptions(meteoServiceOptions);

		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private MeteoService_I meteoService;

	}
