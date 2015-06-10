
package ch.hearc.meteo.imp.com.real;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import gnu.io.CommPortIdentifier;

import ch.hearc.meteo.spec.com.port.MeteoPortDetectionService_I;

public class MeteoPortDetectionService implements MeteoPortDetectionService_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	@Override
	public List<String> findListPortSerie()
		{

		Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
		List<String> listPortSerie = new LinkedList<String>();
		while(portEnum.hasMoreElements())
			{
			CommPortIdentifier portIdentifier = portEnum.nextElement();
			//listPortSerie.add(portIdentifier.getName() + " - " + getPortTypename(portIdentifier.getPortType()));
			listPortSerie.add(portIdentifier.getName());
			}
		return listPortSerie;
		}

	@Override
	public boolean isStationMeteoAvailable(String portName, long timeoutMS)
		{
		// TODO Auto-generated method stub
		return true;
		}

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
	@Override
	public List<String> findListPortMeteo(List<String> listPortExcluded)
		{

		//List<String> listPortsCom = new LinkedList<String>();
		List<String> listPortsCom = findListPortSerie();

		listPortsCom.removeAll(listPortExcluded);

		List<String> listPortComMeteoAvailable = new LinkedList<String>();

//		System.out.println("MeteoPortDetectionService.findListPortMeteo: listPortsCom.size()" + listPortsCom.size());
		for(String portCom:listPortsCom)
			{
//			System.out.println("MeteoPortDetectionService.findListPortMeteo: " + portCom);
			if (isStationMeteoAvailable(portCom, 5000))
				{
				listPortComMeteoAvailable.add(portCom);
				}
			}
//		System.out.println("MeteoPortDetectionService.findListPortMeteo: listPortsCom.size()" + listPortsCom.size());

		return listPortComMeteoAvailable;
		}

	@Override
	public List<String> findListPortMeteo()
		{
		return findListPortMeteo(new LinkedList<String>());
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static String getPortTypename(int portType)
		{
		switch(portType)
			{
			case CommPortIdentifier.PORT_I2C:
				return "I2C";
			case CommPortIdentifier.PORT_PARALLEL:
				return "Parallel";
			case CommPortIdentifier.PORT_RAW:
				return "Raw";
			case CommPortIdentifier.PORT_RS485:
				return "RS485";
			case CommPortIdentifier.PORT_SERIAL:
				return "Serial";
			default:
				return "unknown type";
			}
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	private List<String> supplierNames;
	}
