
package ch.hearc.meteo.imp.com.logique;

import ch.hearc.meteo.spec.com.meteo.exception.MeteoServiceException;

public class Questionneur implements Runnable
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public Questionneur(final Questionnable_I questionnable)
		{
		// Input
		this.questionnable = questionnable;

		// Tools
		// cette tehnique permet l'utilisation des méthodes set sur meteoServiceOption
		// le questionnaire  va alors en tenir compte
		timeManagerAltitude = new TimeManager(new dt_I()
			{

				@Override public long getDt()
					{
					return questionnable.getMeteoServiceOptions().getAltitudeDT();
					}
			});

		timeManagerPression = new TimeManager(new dt_I()
			{

				@Override public long getDt()
					{
					return questionnable.getMeteoServiceOptions().getPressionDT();
					}
			});

		timeManagerTemperature = new TimeManager(new dt_I()
			{

				@Override public long getDt()
					{
					return questionnable.getMeteoServiceOptions().getTemperatureDT();
					}
			});
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override public void run()
		{
		this.isRunning = true;
		while(isRunning)
			{
			try
				{
				if (timeManagerTemperature.isTimeElapse())
					{
					questionnable.askTemperatureAsync();
					timeManagerTemperature.reset();
					}

				if (timeManagerAltitude.isTimeElapse())
					{
					questionnable.askAltitudeAsync();
					timeManagerAltitude.reset();
					}

				if (timeManagerPression.isTimeElapse())
					{
					questionnable.askPressionAsync();
					timeManagerPression.reset();
					}
				}
			catch (MeteoServiceException e)
				{
				e.printStackTrace();
				}

			if (!isRunning) // si arret demandé, pas nécessaire d'attendre
				{
				// update
				timeToWaitMS = min(questionnable.getMeteoServiceOptions().getDelayMS(), timeManagerAltitude.getDt().getDt(), timeManagerPression.getDt().getDt(), timeManagerTemperature.getDt().getDt());

				System.out.println("[Questionneur] : sleeping "+ timeToWaitMS+ " (ms) from thread : "+Thread.currentThread().getName());
				sleep(timeToWaitMS);
				}
			}
		}

	/**
	 * asynchonre
	 */
	public void stopAsync()
		{
		this.isRunning = false;
		}



	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static void sleep(long delayMS)
		{
		try
			{
			Thread.sleep(delayMS);
			}
		catch (InterruptedException e)
			{
			e.printStackTrace();
			}
		}

	private static long min(long a, long b, long c, long d)
		{
		return Math.min(Math.min(Math.min(a, b), c), d);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private Questionnable_I questionnable;

	// tools
	private boolean isRunning;

	private TimeManager timeManagerAltitude;
	private TimeManager timeManagerPression;
	private TimeManager timeManagerTemperature;

	private long timeToWaitMS;

	}
