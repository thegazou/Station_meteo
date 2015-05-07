
package ch.hearc.meteo.imp.com.logique;

import java.util.LinkedList;
import java.util.List;

import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoService_I;
import ch.hearc.meteo.spec.com.meteo.exception.MeteoServiceException;
import ch.hearc.meteo.spec.com.meteo.listener.MeteoListener_I;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEventType_E;
import ch.hearc.meteo.spec.com.meteo.listener.event.Sources;

/**
 * Couche logique
 */
public abstract class MeteoService_A implements MeteoService_I ,MeteoServiceCallback_I ,Questionnable_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public MeteoService_A(String namePort)
		{
		// Inputs
		this.namePort = namePort;

		// Tools
		listMeteoListener = new LinkedList<MeteoListener_I>();

		isRunning = false;
		isConnected = false;

		// Outputs
		this.source = new Sources(namePort);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	protected abstract void connectHardware() throws MeteoServiceException;

	protected abstract void disconnectHardware() throws MeteoServiceException;

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override public String toString()
		{
		StringBuilder builder = new StringBuilder();
		builder.append("MeteoService_A (");
		builder.append(this.namePort);
		builder.append(")");
		return builder.toString();
		}

	@Override public void altitudePerformed(float value)
		{
		avertirAltitudeListener(meteoEvent(value, MeteoEventType_E.ALTITUDE));
		}

	@Override public void pressionPerformed(float value)
		{
		avertirPressionListener(meteoEvent(value, MeteoEventType_E.PRESSION));
		}

	@Override public void temperaturePerformed(float value)
		{
		avertirTemperatureListener(meteoEvent(value, MeteoEventType_E.TEMPERATURE));
		}

	/**
	 * Call first connect
	 */
	@Override synchronized public void start(MeteoServiceOptions meteoServiceOptions)
		{
		if (isConnected)
			{
			if (!isRunning)
				{
				System.out.println("MeteoService_A : Start");

				this.meteoServiceOptions = meteoServiceOptions;
				isRunning = true;

				questionneur = new Questionneur(this);
				threadQuestionnaire = new Thread(questionneur);
				threadQuestionnaire.start();
				}
			else
				{
				System.out.println("MeteoService_A : Already started");
				}
			}
		else
			{
			System.out.println("MeteoService_A : Not connected : impossible to start");
			}
		}

	@Override synchronized public void stop()
		{
		System.out.println("MeteoService_A : Stop");

		if (isRunning)
			{
			isRunning = false;
			questionneur.stopAsync(); // pas suffisant

			//v1
			while(threadQuestionnaire.isAlive())
				{
				try
					{
					Thread.sleep(DELAY_DEAD_THREAD);
					}
				catch (InterruptedException e)
					{
					e.printStackTrace();
					}
//				threadQuestionnaire = null;
//				questionneur = null;
				}

			// V2
			//thread.stop(); // vu la nature du thread, pas genant comme méthode

			//v3 ou pool thread avec 1 thread
			}
		}

	@Override synchronized public void connect() throws MeteoServiceException
		{
		if (!isConnected)
			{
			connectHardware();
			System.out.println("MeteoService_A : Connect");
			isConnected = true;
			}
		else
			{
			System.out.println("MeteoService_A : Already connected");
			}
		}

	@Override synchronized public void disconnect() throws MeteoServiceException
		{
		if (isConnected)
			{
			stop();
			disconnectHardware();
			System.out.println("MeteoService_A : Disconnect");
			isConnected = false;
			}

		}

	@Override synchronized public boolean addMeteoListener(MeteoListener_I listener)
		{
		return listMeteoListener.add(listener);
		}

	@Override synchronized public boolean removeMeteoListener(MeteoListener_I listener)
		{
		return listMeteoListener.remove(listener);
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	@Override public String getPort()
		{
		return namePort;
		}

	@Override public MeteoServiceOptions getMeteoServiceOptions()
		{
		return meteoServiceOptions;
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	@Override public void setMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions)
		{
		this.meteoServiceOptions = meteoServiceOptions;
		}

	/*------------------------------*\
	|*				Is				*|
	\*------------------------------*/

	@Override public synchronized boolean isRunning()
		{
		return isRunning;
		}

	@Override public synchronized boolean isConnect()
		{
		return isConnected;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private MeteoEvent meteoEvent(float value, MeteoEventType_E meteoEventType)
		{
		long time = System.currentTimeMillis();
		return new MeteoEvent(source, meteoEventType, value, time);
		}

	private synchronized void avertirAltitudeListener(MeteoEvent event)
		{
		for(MeteoListener_I listener:listMeteoListener)
			{
			listener.altitudePerformed(event);
			}
		}

	private synchronized void avertirPressionListener(MeteoEvent event)
		{
		for(MeteoListener_I listener:listMeteoListener)
			{
			listener.pressionPerformed(event);
			}
		}

	private synchronized void avertirTemperatureListener(MeteoEvent event)
		{
		for(MeteoListener_I listener:listMeteoListener)
			{
			listener.temperaturePerformed(event);
			}
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private String namePort;
	private Questionneur questionneur;
	private MeteoServiceOptions meteoServiceOptions;

	// Tools
	private List<MeteoListener_I> listMeteoListener;
	private boolean isRunning;

	private boolean isConnected;

	private Thread threadQuestionnaire;

	// Outputs
	private Sources source;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static final long DELAY_DEAD_THREAD = 200;

	}
