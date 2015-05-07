
package ch.hearc.meteo.spec.reseau.rmiwrapper;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.exception.MeteoServiceException;

/**
 * <pre>
 * But:
 * 		L'objectif est de ne pas poluer l'implementation du serviceMeteo avec la moindre trace de RMI.
 * 		En effet, le serviceMeteo n'est pas forcement utilisable en reseau, et son implementation doit etre independante de cette problematique.
 *
 * Note:
 * 		Il s'agit par ailleurs d'un service restreint (methode volontairement enleveer, voir ci-dessous)
 *
 * Ou:
 * 		A utiliser sur le pc central uniquement.
 *
 * Usage:
 * 		(E1)	Sur le pc local on instancie un meteoservice avec la factory d�di� � cet usage.
 *
 * 					MeteoService_I meteoService = (new MeteoServiceFactory()).create(portName);
 *
 * 		(E2) 	On construit un wrapper autour de ce service
 *
 * 					MeteoServiceWrapper_I meteoServiceWrapper = new MeteoServiceWrapper(meteoService);
 *
 * 				ou meteoService est le service complet de l'�tape pr�c�dente
 *
 * 		(E3)	On RMI shared ce wrapper pour une utilisation � distance depui le pc central
 *
 * 					RmiTools.shareObject(meteoServiceWrapper, ...);
 *
 * 		(E3)	On transmet ce wrapper au service d'affichage, qui peut par son GUI piloter la station meteo
 *
 * 					 AfficheurService_I afficheurService = (new AfficheurFactory()).createOnLocalPC(affichageOption, meteoServiceWrapper);
 *
 * </pre>
 */
public interface MeteoServiceWrapper_I extends Remote
	{

	public void connect() throws RemoteException, MeteoServiceException;

	public void disconnect() throws RemoteException, MeteoServiceException;

	public void start(MeteoServiceOptions meteoServiceOptions) throws RemoteException;

	public void stop() throws RemoteException;

	public boolean isRunning() throws RemoteException;

	public boolean isConnect() throws RemoteException;

	public String getPort() throws RemoteException;

	public MeteoServiceOptions getMeteoServiceOptions() throws RemoteException;

	public void setMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions) throws RemoteException;

	/*------------------------------*\
	|*				Warning			*|
	\*------------------------------*/

	// Observation:
	// 	Les deux m�thodes ci-dessous sont volontairment enlev�e!
	//
	// Explication:
	//
	// 	Si on les utilise depuis le pc-central (c'est l'objectif!), le listener sera s�riali� sur le pc-central et deserializer sur le pc-local.
	// 	le service m�t�o va alors avertir l'instance sur le pc-local, instance de listener qui n'a aucune connexion avec celle sur le pc central.
	// 	L'instance sur le pc-central ne sera alors jamais avertie (ce qui est l'objectif)
	//
	// Tip
	//	On pourrait malgr� tout les utiliser en injectant un listener auto-shared et auto-conencted en customizant la serilalization et deserialization!
	//	Tr�s int�ressant! Contacte cedric.bilat@he-arc.ch pour plus de d�tails sur cette technique!

	//	public boolean addMeteoListener(MeteoListener_I listener);
	//  public boolean removeMeteoListener(MeteoListener_I listener);
	}
