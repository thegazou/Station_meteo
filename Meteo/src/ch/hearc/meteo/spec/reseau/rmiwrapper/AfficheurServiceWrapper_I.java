
package ch.hearc.meteo.spec.reseau.rmiwrapper;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;

/**
 * <pre>
 * But:
 * 		L'objectif est de ne pas poluer l'implementation du AfficheurService avec la moindre trace de RMI.
 *		 En effet, le AfficheurService n'est pas forcement utilisable en reseau, et son implementation doit etre independante de cette problematique.
 *
 * ou:
 * 		A utiliser sur PC Local
 * </pre>
 */
public interface AfficheurServiceWrapper_I extends Remote
	{

	public void printPression(MeteoEvent event) throws RemoteException;

	public void printAltitude(MeteoEvent event) throws RemoteException;

	public void printTemperature(MeteoEvent event) throws RemoteException;

	/**
	 * Utile pour l'update du GUI du pc-central (slider ...) de dt, a distance si slider est changé depuis pc local
	 */
	public void updateMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions);


	}
