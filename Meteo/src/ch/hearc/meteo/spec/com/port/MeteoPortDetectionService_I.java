
package ch.hearc.meteo.spec.com.port;

import java.util.List;

public interface MeteoPortDetectionService_I
	{

	/**
	 * Retourne la liste de tous les ports s�ries
	 */
	public List<String> findListPortSerie();

	/**
	 * Implementation conseil:
	 * 		(C1) Catcher l'ouverture du port qui peut echouer si le port est deja ouvert, et renvoyer false (le port est deja utilis�)
	 * 		(C2) Envoyer une trame, attendre une r�ponse en tenant compte d'un timeout
	 *
	 * Output:
	 * 		Return true si station meteo est connect� au portName
	 * 		Return false si
	 * 				(1) pas sation meteo connect� au PortName
	 * 				(2) StationMeteo connecte au PortName mais deja en utilisation (impossible d'ouvrir le port alors)
	 */
	public boolean isStationMeteoAvailable(String portName,long timeoutMS);

	/**
	 * Contraintes :
	 * 		(C1) Doit refermer les ports!
	 * 		(C2) Doit �tre safe (dans le sens ou un port com peut contenir un hardware sensible qui ne doit imp�rativement pas �tre d�ranger, ie aucune tentative d'ouverture de port autoris�e)
	 *
	 * Implementation conseil:
	 * 		(I1) Utiliser la m�thode  isStationMeteoAvailable(String portName)
	 * 		(I2) Pour satisfaire la contrainte C2
	 * 				Step1 : Utiliser findPortSerie (ci-dessus)																						---> listPortCom
	 * 				Step2 :	Soustraction de listPortExcluded � listPortCom	(via removeAll)															---> listPortCom (updater)
	 * 				Step3 : Instancien listPortComMeteoAvailable																					---> listPortComMeteoAvailable	(vide)
	 * 				Step4 :	Parcourir listPortCom et utiliser isStationMeteoAvailable (ci-dessous) pour peupler listPortComMeteoAvailable			---> listPortComMeteoAvailable
	 *
	 *  Output:
	 *  	Return la liste des ports surlesquels sont branch�s une station m�t�o (non encore utilis�e) , except listPortExcluded
	 */
	public List<String> findListPortMeteo(List<String> listPortExcluded);

	/**
	 * Implementation conseil:
	 * 		(C1) Utiliser la m�thode
	 * 						findListPortMeteo(List<String> listPortExcluded)
	 * 			 avec une listPortExcluded qui existe (Instancier) mais de taille 0
	 */
	public List<String> findListPortMeteo();


	}
