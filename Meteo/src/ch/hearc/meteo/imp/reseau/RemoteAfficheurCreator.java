
package ch.hearc.meteo.imp.reseau;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

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
		mapRmiURLAfficheurLocalToAfficheurCentral = new HashMap<RmiURL, AfficheurService_I>();
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
		AfficheurServiceWrapper_I telecommandeAfficheurLocal = null;
		MeteoServiceWrapper_I telecommandeMeteoServiceLocal = null;
		RmiURL afficheurServicermiURL = null;
		AfficheurService_I afficheurCentral;
		//Connection de la télécommande à l'affichage local
		telecommandeAfficheurLocal = (AfficheurServiceWrapper_I)RmiTools.connectionRemoteObject(affichageServiceRmiURL);
		//Connection de la télécommande au service météo local
		telecommandeMeteoServiceLocal = (MeteoServiceWrapper_I)RmiTools.connectionRemoteObject(meteoServiceRmiURL);
		//[TODO]Test si la demande vient d'un service connu

		//Création d'un affichage central à l'aide de la télécommande sur le service météo local
		afficheurCentral = createAfficheurService(affichageOptions, telecommandeMeteoServiceLocal);

		//Partage  de l'affichage central.
		afficheurServicermiURL = rmiUrl();
		AfficheurServiceWrapper_I afficheurCentralWrapper = new AfficheurServiceWrapper(afficheurCentral);
		RmiTools.shareObject(afficheurCentralWrapper, afficheurServicermiURL);

		return afficheurServicermiURL; // Retourner le RMI-ID pour une connection distante sur le serveur d'affichage
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
		String id = IdTools.createID(PREFIXE);

		return new RmiURL(id);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	// Tools

	private static RemoteAfficheurCreator_I INSTANCE = null;

	private static Map<RmiURL, AfficheurService_I> mapRmiURLAfficheurLocalToAfficheurCentral;
	// Tools final
	private static final String PREFIXE = "AFFICHEUR_SERVICE";

	public static final String RMI_ID = PREFIXE;
	public static final int RMI_LOCAL_PORT = RmiTools.PORT_RMI_DEFAUT;
	public static final RmiURL RMI_URL = new RmiURL(RMI_ID, RMI_LOCAL_PORT);

	}
