
package ch.hearc.meteo.imp.com.logique;

public class TimeManager
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public TimeManager(dt_I dt)
		{
		// Input
		this.dt = dt;

		// Tools
		this.previousTime = currentTime();
		}

//	/**
//	 * post biuil with setDT required
//	 */
//	public TimeManager()
//		{
//		this(-1);
//		}


	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public boolean isTimeElapse()
		{
		return currentTime() - previousTime > dt.getDt();
		}



	public void reset()
		{
		previousTime = currentTime();
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public dt_I getDt()
		{
		return this.dt;
		}



	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

//	public void setDt(long dt)
//		{
//		this.dt = dt;
//		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private long currentTime()
		{
		return System.currentTimeMillis();
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private dt_I dt;

	// Tools
	private long previousTime;
	}
