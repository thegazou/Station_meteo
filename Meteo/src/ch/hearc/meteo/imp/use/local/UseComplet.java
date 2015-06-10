
package ch.hearc.meteo.imp.use.local;

//import ch.hearc.meteo.imp.afficheur.real.AfficheurSimulatorFactoryT;
//import ch.hearc.meteo.imp.com.real.MeteoServiceFactory;
//import ch.hearc.meteo.imp.afficheur.real.AfficheurSimulatorFactoryT;
//import ch.hearc.meteo.imp.afficheur.real.AfficheurSimulatorFactoryT;
import ch.hearc.meteo.imp.afficheur.real.AfficheurFactory;
import ch.hearc.meteo.imp.afficheur.real.pclocal.JFramePCLocal;
import ch.hearc.meteo.imp.com.simulateur.MeteoServiceSimulatorFactory;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoService_I;
import ch.hearc.meteo.spec.com.meteo.exception.MeteoServiceException;
import ch.hearc.meteo.spec.com.meteo.listener.MeteoAdapter;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper;
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper_I;

import com.bilat.tools.reseau.rmi.RmiTools;

public class UseComplet
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public static void main(String[] args)
		{
		try
			{
			main();
			}
		catch (MeteoServiceException e)
			{
			e.printStackTrace();
			}
		}

	public static void main() throws MeteoServiceException
		{

		//JFrameMain mainPrincipale= new JFrameMain();
		JFramePCLocal pcLocale= new JFramePCLocal(/*objet lukas*/);

		//MeteoService_I meteoService = (new MeteoServiceSimulatorFactory()).create("COM1");
		//MeteoService_I meteoService1 = (new MeteoServiceFactory()).create("COM4");
		//MeteoService_I meteoService2 = (new MeteoServiceFactory()).create("COM5");
		MeteoService_I meteoService = (new MeteoServiceSimulatorFactory()).create("COM4");
		//MeteoService_I meteoService1 = (new MeteoServiceSimulatorFactory()).create("COM5");
		//MeteoService_I meteoService2 = (new MeteoServiceSimulatorFactory()).create("COM6");
		//ArrayList<String> portsList = new ArrayList<String>();
		//ArrayList<MeteoService_I> meteoServiceArray = new ArrayList<MeteoService_I>();
		//meteoServiceArray.add(meteoService);
		//meteoServiceArray.add(meteoService1);
		//meteoServiceArray.add(meteoService2);
		//for(MeteoService_I s:meteoServiceArray)
					//{
					use(meteoService);
					//}

		//MeteoService_I meteoService = (new MeteoServiceSimulatorFactory()).create(portName);
		/*MeteoService_I meteoService = (new MeteoServiceFactory()).create("COM5");*/
		//use(meteoService);
		}

	public static void use(MeteoService_I meteoService) throws MeteoServiceException
		{
		// Service Meteo
		meteoService.connect();
		MeteoServiceOptions meteoServiceOptions = new MeteoServiceOptions(800, 1000, 1200);
		meteoService.start(meteoServiceOptions);

		// Service Affichage
		MeteoServiceWrapper_I meteoServiceWrapper = new MeteoServiceWrapper(meteoService);

		String titre = RmiTools.getLocalHost() + " " + meteoService.getPort();
		AffichageOptions affichageOption = new AffichageOptions(3, titre);
		//AfficheurService_I afficheurService = (new AfficheurSimulateurFactory()).createOnLocalPC(affichageOption, meteoServiceWrapper);
		AfficheurService_I afficheurService1 = (new AfficheurFactory()).createOnLocalPC(affichageOption, meteoServiceWrapper);
		//AfficheurService_I afficheurService11 = (new AfficheurSimulatorFactoryT()).createOnCentralPC(affichageOption, meteoServiceWrapper);

		use(meteoService, afficheurService1);
		}

	/**
	 * Liason entre les deux services d'affichage : MeteoService_I et AfficheurService_I
	 */
	public static void use(final MeteoService_I meteoService, final AfficheurService_I afficheurService) throws MeteoServiceException
		{
		meteoService.addMeteoListener(new MeteoAdapter()
			{

				@Override
				public void temperaturePerformed(MeteoEvent event)
					{
					afficheurService.printTemperature(event);
					}

				@Override
				public void altitudePerformed(MeteoEvent event)
					{
					afficheurService.printAltitude(event);
					}

				@Override
				public void pressionPerformed(MeteoEvent event)
					{
					afficheurService.printPression(event);
					}

			});

		// Modify MeteoServiceOptions
		Thread threadSimulationChangementDt = new Thread(new Runnable()
			{

				@Override
				public void run()
					{
					double x = 0;
					double dx = Math.PI / 10;

					while(true)
						{
						long dt = 1000 + (long)(5000 * Math.abs(Math.cos(x))); //ms

						System.out.println("modification dt temperature = " + dt);

						meteoService.getMeteoServiceOptions().setTemperatureDT(dt);

						//	System.out.println(meteoService.getMeteoServiceOptions());

						attendre(3000); // disons
						x += dx;
						}
					}
			});

		// Update GUI MeteoServiceOptions
		Thread threadPoolingOptions = new Thread(new Runnable()
			{

				@Override
				public void run()
					{

					while(true)
						{
						MeteoServiceOptions option = meteoService.getMeteoServiceOptions();
						afficheurService.updateMeteoServiceOptions(option);

						//System.out.println(option);

						attendre(1000); //disons
						}
					}
			});

		threadSimulationChangementDt.start();
		threadPoolingOptions.start(); // update gui
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private static void attendre(long delay)
		{
		try
			{
			Thread.sleep(delay);
			}
		catch (InterruptedException e)
			{
			e.printStackTrace();
			}
		}
	}

