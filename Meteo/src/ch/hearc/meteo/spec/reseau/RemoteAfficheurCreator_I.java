
package ch.hearc.meteo.spec.reseau;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ch.hearc.meteo.spec.afficheur.AffichageOptions;

import com.bilat.tools.reseau.rmi.RmiURL;



/**
 * <pre>
 * Goal :
 * 		(G1) Permettre la connexion dynamique a chaud de station meteo sur le pc central
 *
 * Definitions:
 * 		(D1) PC-Local 		: PC where station meteo is connect
 * 		(D2) PC-Central 	: PC where afficheurSercice is running
 *
 * Notes :
 * 		 (N1) One instance only (Singleton) on PC-Central
 *		 (N2) RMI-Shared
 *
 * Warning:
 * 		(W1) PC-Local connect to PC-Central, and not in the inverse order!
 * 			 Goal : Allow during time to connect dynamicly a new PC-local to the pc-central
 *
 * 		(W2) Singleton
 * </pre>
 */
public interface RemoteAfficheurCreator_I extends Remote
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/**
	 * <pre>
	 *
	 * Rappel:
	 *		(R1) RemoteAfficheurCreator_I is a remote object running on pc-central
	 *
	 * Goal :
	 * 		(G1) Create remotely an AfficheurService_I on the PC-Central, and return the RmiURL on the pc-local to enable next a remote connection to this new instance of the created remote AfficheurService_I
	 * 		(G2) Give to the PC-Central a RmiURL, which allow the PC-Central to connect remotely to the PC-Local in order to control the meteoService remotely (start/stop)
	 *
	 * Usage:
	 * 		Must be call in the PC-Local
	 *
	 * Inputs:
	 *		RmiURL meteoServiceRmiURL 	: 	With this RmiURL, the  PC-Central can remotely call start/stop to manage the remote stationMeteo running on PC-Local)
	 *
	 * Outputs:
	 * 		RmiURL AfficheurService_I 	: 	With this RmiURL, the  PC-Local can remotely ask to the AfficheurService_I to print data on the remote PC-Central
	 *
	 * </pre>
	 */
	public RmiURL createRemoteAfficheurService(AffichageOptions affichageOptions, RmiURL meteoServiceRmiURL) throws RemoteException;

	}
