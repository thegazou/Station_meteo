
package ch.hearc.meteo.imp.reseau;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import ch.hearc.meteo.imp.afficheur.real.afficheur.AfficheurFactory;
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

		//Création d'un affichage central à l'aide de la télécommande sur le service météo local
		afficheurCentral = createAfficheurService(affichageOptions, telecommandeMeteoServiceLocal);
		//Partage  de l'affichage central.
		afficheurCentralRmiURL = rmiUrlProf();
		AfficheurServiceWrapper_I afficheurCentralWrapper = new AfficheurServiceWrapper(afficheurCentral);
		RmiTools.shareObject(afficheurCentralWrapper, afficheurCentralRmiURL);

		//Ajout de la connexion à la map
		mapTitreToAfficheurCentral.put(affichageOptions.getTitre(), afficheurCentralRmiURL);

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
		//JFramePCCentrale.addStation(affichageOptions, (MeteoService_I)meteoServiceRemote);
		return (new AfficheurFactory()).createOnCentralPC(affichageOptions, meteoServiceRemote);
		}

	private void server() throws RemoteException
		{
		// done. share this
		RmiTools.shareObject(this, RMI_URL);
		}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static RmiURL rmiUrlProf()
		{

		String id = IdTools.createID(PREFIXE);

		return new RmiURL(id);
		}

	/**
	 * Thread Safe
	 */
	private static RmiURL rmiUrl()
		{
		InetAddress address = null;
		String ipName;
		try
			{
			ipName = System.getProperty("ip", String.valueOf(RMI_LOCAL_PORT));
			address = InetAddress.getByName(ipName);
			}
		catch (UnknownHostException e)
			{
			System.err.println("IP invalide");
			}
		return new RmiURL(PREFIXE, address);
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
	//public final static RmiURL RMI_URL = rmiUrl();
	public final static RmiURL RMI_URL = new RmiURL(RMI_ID, RMI_LOCAL_PORT);
	}
