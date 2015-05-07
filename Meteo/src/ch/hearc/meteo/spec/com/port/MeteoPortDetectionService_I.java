
package ch.hearc.meteo.spec.com.port;

import java.util.List;

public interface MeteoPortDetectionService_I
	{

	/**
	 * Retourne la liste de tous les ports séries
	 */
	public List<String> findListPortSerie();

	/**
	 * Implementation conseil:
	 * 		(C1) Catcher l'ouverture du port qui peut echouer si le port est deja ouvert, et renvoyer false (le port est deja utilisé)
	 * 		(C2) Envoyer une trame, attendre une réponse en tenant compte d'un timeout
	 *
	 * Output:
	 * 		Return true si station meteo est connecté au portName
	 * 		Return false si
	 * 				(1) pas sation meteo connecté au PortName
	 * 				(2) StationMeteo connecte au PortName mais deja en utilisation (impossible d'ouvrir le port alors)
	 */
	public boolean isStationMeteoAvailable(String portName,long timeoutMS);

	/**
	 * Contraintes :
	 * 		(C1) Doit refermer les ports!
	 * 		(C2) Doit être safe (dans le sens ou un port com peut contenir un hardware sensible qui ne doit impérativement pas être déranger, ie aucune tentative d'ouverture de port autorisée)
	 *
	 * Implementation conseil:
	 * 		(I1) Utiliser la méthode  isStationMeteoAvailable(String portName)
	 * 		(I2) Pour satisfaire la contrainte C2
	 * 				Step1 : Utiliser findPortSerie (ci-dessus)																						---> listPortCom
	 * 				Step2 :	Soustraction de listPortExcluded à listPortCom	(via removeAll)															---> listPortCom (updater)
	 * 				Step3 : Instancien listPortComMeteoAvailable																					---> listPortComMeteoAvailable	(vide)
	 * 				Step4 :	Parcourir listPortCom et utiliser isStationMeteoAvailable (ci-dessous) pour peupler listPortComMeteoAvailable			---> listPortComMeteoAvailable
	 *
	 *  Output:
	 *  	Return la liste des ports surlesquels sont branchés une station météo (non encore utilisée) , except listPortExcluded
	 */
	public List<String> findListPortMeteo(List<String> listPortExcluded);

	/**
	 * Implementation conseil:
	 * 		(C1) Utiliser la méthode
	 * 						findListPortMeteo(List<String> listPortExcluded)
	 * 			 avec une listPortExcluded qui existe (Instancier) mais de taille 0
	 */
	public List<String> findListPortMeteo();


	}
