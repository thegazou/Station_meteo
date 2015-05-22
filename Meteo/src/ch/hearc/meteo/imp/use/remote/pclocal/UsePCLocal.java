
package ch.hearc.meteo.imp.use.remote.pclocal;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import ch.hearc.meteo.imp.afficheur.simulateur.AfficheurSimulateurFactory;
import ch.hearc.meteo.imp.com.simulateur.MeteoServiceSimulatorFactory;
import ch.hearc.meteo.imp.reseau.RemoteAfficheurCreator;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoService_I;
import ch.hearc.meteo.spec.com.meteo.exception.MeteoServiceException;
import ch.hearc.meteo.spec.com.meteo.listener.MeteoAdapter;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;
import ch.hearc.meteo.spec.reseau.RemoteAfficheurCreator_I;
import ch.hearc.meteo.spec.reseau.rmiwrapper.AfficheurServiceWrapper;
import ch.hearc.meteo.spec.reseau.rmiwrapper.AfficheurServiceWrapper_I;
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper;
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper_I;

import com.bilat.tools.reseau.rmi.RmiTools;
import com.bilat.tools.reseau.rmi.RmiURL;

public class UsePCLocal
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public static void main(String[] args) throws MeteoServiceException
		{
		main();
		}

	public static void main() throws MeteoServiceException
		{
		// Service Meteo
		String portName = "COM1";
		MeteoService_I meteoService = (new MeteoServiceSimulatorFactory()).create(portName);
		meteoService.connect();
		MeteoServiceOptions meteoServiceOptions = new MeteoServiceOptions(800, 1000, 1200);
		meteoService.start(meteoServiceOptions);
		MeteoServiceWrapper_I meteoServiceWrapper = new MeteoServiceWrapper(meteoService);

		// Création du service d'affichage local
		String titre = RmiTools.getLocalHost() + " " + meteoService.getPort();
		AffichageOptions affichageOption = new AffichageOptions(3, titre);
		AfficheurService_I afficheurService = new AfficheurSimulateurFactory().createOnLocalPC(affichageOption, meteoServiceWrapper);
		AfficheurServiceWrapper afficheurServiceWrapper = new AfficheurServiceWrapper(afficheurService);

		// Réseau
		RmiURL RmiURLRemoteAfficheur = null;
		RemoteAfficheurCreator_I telecommandeAfficheurCreator = null;
		AfficheurServiceWrapper_I telecommandeAfficheurCentral = null;
		try
			{
			//partage de l'affichage
			RmiTools.shareObject(afficheurServiceWrapper, RMI_URL_AFFICHAGE);
			//partage du service météo
			RmiTools.shareObject(meteoServiceWrapper, RMI_URL_METEO);
			//connection de la télécommande sur le créateur d'afficheur central
			telecommandeAfficheurCreator = (RemoteAfficheurCreator_I)RmiTools.connectionRemoteObjectBloquant(RemoteAfficheurCreator.RMI_URL);
			//Création d'un affichage central à l'aide de la télécommande
			RmiURLRemoteAfficheur = telecommandeAfficheurCreator.createRemoteAfficheurService(affichageOption, RMI_URL_METEO, RMI_URL_AFFICHAGE);
			//Connection de la télécommande sur l'affichage central
			telecommandeAfficheurCentral = (AfficheurServiceWrapper_I)RmiTools.connectionRemoteObjectBloquant(RmiURLRemoteAfficheur);
			}
		catch (RemoteException e)
			{
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		catch (NotBoundException e)
			{
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		new PCLocal(meteoServiceOptions, portName, affichageOption, RmiURLRemoteAfficheur);
		use(meteoService, afficheurService, telecommandeAfficheurCentral);
		}

	/**
	 * Liason entre les deux services d'affichage : MeteoService_I et AfficheurService_I
	 */
	public static void use(final MeteoService_I meteoService, final AfficheurService_I afficheurService, final AfficheurServiceWrapper_I telecommandeAfficheurCentral) throws MeteoServiceException
		{
		meteoService.addMeteoListener(new MeteoAdapter()
			{
				@Override
				public void temperaturePerformed(MeteoEvent event)
					{
					afficheurService.printTemperature(event);
					try
						{
						telecommandeAfficheurCentral.printTemperature(event);
						}
					catch (RemoteException e)
						{
						System.err.println("RemoteException: cannot send temperature update to the PCCentral.");
						}
					}

				@Override
				public void altitudePerformed(MeteoEvent event)
					{
					afficheurService.printAltitude(event);
					try
						{
						telecommandeAfficheurCentral.printAltitude(event);
						}
					catch (RemoteException e)
						{
						System.err.println("RemoteException: cannot send Altitude update to the PCCentral.");
						}
					}

				@Override
				public void pressionPerformed(MeteoEvent event)
					{
					afficheurService.printPression(event);
					try
						{
						telecommandeAfficheurCentral.printPression(event);
						}
					catch (RemoteException e)
						{
						System.err.println("RemoteException: cannot send pression update to the PCCentral.");
						}
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
						try
							{
							telecommandeAfficheurCentral.updateMeteoServiceOptions(option);
							}
						catch (RemoteException e)
							{
							System.err.println("RemoteException: cannot send option update to the PCCentral.");
							}

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

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	// Tools final
	private static final String PREFIXE_METEO = "METEO_SERVICE";
	private static final String PREFIXE_AFFICHAGE = "METEO_AFFICHAGE";

	public static final String RMI_ID_METEO = PREFIXE_METEO;
	public static final String RMI_ID_AFFICHAGE = PREFIXE_AFFICHAGE;

	public static final int RMI_LOCAL_PORT = RmiTools.PORT_RMI_DEFAUT;
	public static final RmiURL RMI_URL_METEO = new RmiURL(RMI_ID_METEO, RMI_LOCAL_PORT);
	public static final RmiURL RMI_URL_AFFICHAGE = new RmiURL(RMI_ID_AFFICHAGE, RMI_LOCAL_PORT);
	}
