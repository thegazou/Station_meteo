
package ch.hearc.meteo.imp.reseau;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import ch.hearc.meteo.imp.afficheur.simulateur.AfficheurSimulateurFactory;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.reseau.RemoteAfficheurCreator_I;
import ch.hearc.meteo.spec.reseau.rmiwrapper.AfficheurServiceWrapper;
import ch.hearc.meteo.spec.reseau.rmiwrapper.AfficheurServiceWrapper_I;
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper_I;

import com.bilat.tools.reseau.rmi.IdTools;
import com.bilat.tools.reseau.rmi.RmiTools;
import com.bilat.tools.reseau.rmi.RmiURL;

/**
 * <pre>
 * One instance only (Singleton) on PC-Central
 * RMI-Shared
 * </pre>
 */
public class RemoteAfficheurCreator implements RemoteAfficheurCreator_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	private RemoteAfficheurCreator() throws RemoteException
		{
		mapTitreToAfficheurCentral = new HashMap<String, RmiURL>();
		server();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/**
	 * Remote use
	 * @param meteoServiceWrapper
	 * @throws NotBoundException
	 */
	@Override
	public RmiURL createRemoteAfficheurService(AffichageOptions affichageOptions, RmiURL meteoServiceRmiURL, RmiURL affichageServiceRmiURL) throws RemoteException, NotBoundException
		{
		MeteoServiceWrapper_I telecommandeMeteoServiceLocal = null;
		RmiURL afficheurCentralRmiURL = null;
		AfficheurService_I afficheurCentral = null;
		//Connection de la télécommande au service météo local
		telecommandeMeteoServiceLocal = (MeteoServiceWrapper_I)RmiTools.connectionRemoteObject(meteoServiceRmiURL);
		//[TODO]Test si la demande vient d'un service connu
		if (mapTitreToAfficheurCentral.containsKey(affichageOptions.getTitre()))
			{
			//Reconnexion de la télécommande sur la station météo

			//Assignation du rmiUrl de l'affichage central
			afficheurCentralRmiURL = mapTitreToAfficheurCentral.get(affichageOptions.getTitre());

			}
		else
			{
			//Création d'un affichage central à l'aide de la télécommande sur le service météo local
			afficheurCentral = createAfficheurService(affichageOptions, telecommandeMeteoServiceLocal);
			//Partage  de l'affichage central.
			afficheurCentralRmiURL = rmiUrl();
			AfficheurServiceWrapper_I afficheurCentralWrapper = new AfficheurServiceWrapper(afficheurCentral);
			RmiTools.shareObject(afficheurCentralWrapper, afficheurCentralRmiURL);
			//Ajout de la connexion à la map
			mapTitreToAfficheurCentral.put(affichageOptions.getTitre(), afficheurCentralRmiURL);
			}
		return afficheurCentralRmiURL; // Retourner le RMI-ID pour une connection distante sur le serveur d'affichage
		}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static synchronized RemoteAfficheurCreator_I getInstance() throws RemoteException
		{
		if (INSTANCE == null)
			{
			INSTANCE = new RemoteAfficheurCreator();
			}

		return INSTANCE;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private AfficheurService_I createAfficheurService(AffichageOptions affichageOptions, MeteoServiceWrapper_I meteoServiceRemote)
		{
		//done
		return (new AfficheurSimulateurFactory()).createOnCentralPC(affichageOptions, meteoServiceRemote);
		}

	private void server() throws RemoteException
		{
		// done. share this
		RmiTools.shareObject(this, RMI_URL);
		}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	/**
	 * Thread Safe
	 */
	private static RmiURL rmiUrl()
		{
		InetAddress address=loadPreference();
		String id = IdTools.createID(PREFIXE);

		return new RmiURL(id,address);
		}

	private static InetAddress loadPreference()
		{
		Properties properties = new Properties();

		if (FILE_PROPERTIES.exists())
			{
			try
				{
				FileInputStream fis = new FileInputStream(FILE_PROPERTIES);
				BufferedInputStream bis = new BufferedInputStream(fis);

				properties.load(bis);

				bis.close();
				fis.close();
				}
			catch (Exception e)
				{
				System.err.println("Impossible de loader les préferences");
				}
			}

		String s = properties.getProperty("rmiCentral", "127.0.0.1");
		System.out.println(s);
		InetAddress address = null;
		try
			{
			address = InetAddress.getByName(s);
			}
		catch (UnknownHostException e)
			{
			System.err.println("IP dans le fichier ipCentral.txt invalide.");
			}

		return address;

		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	// Tools

	private static RemoteAfficheurCreator_I INSTANCE = null;
	private static Map<String, RmiURL> mapTitreToAfficheurCentral;

	// Tools final
	private static final String PREFIXE = "AFFICHEUR_SERVICE";

	public static final String RMI_ID = PREFIXE;
	public static final int RMI_LOCAL_PORT = RmiTools.PORT_RMI_DEFAUT;
	public static final RmiURL RMI_URL = rmiUrl();
	private static final File FILE_PROPERTIES = new File("./Settings/ipCentral.txt");

	}
