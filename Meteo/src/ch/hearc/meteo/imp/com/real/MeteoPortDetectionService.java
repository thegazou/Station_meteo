
package ch.hearc.meteo.imp.com.real;

import java.util.ArrayList;
import java.util.Enumeration;
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
		while(portEnum.hasMoreElements())
			{
			CommPortIdentifier portIdentifier = portEnum.nextElement();
			System.out.println(portIdentifier.getName() + " - " + getPortTypename(portIdentifier.getPortType()));
			}
		return null;
		}

	@Override
	public boolean isStationMeteoAvailable(String portName, long timeoutMS)
		{
		// TODO Auto-generated method stub
		return false;
		}

	@Override
	public List<String> findListPortMeteo(List<String> listPortExcluded)
		{
		// TODO Auto-generated method stub
		return null;
		}

	@Override
	public List<String> findListPortMeteo()
		{
			supplierNames = new ArrayList<String>();
			supplierNames.add("sup1");
			supplierNames.add("sup2");
			supplierNames.add("sup3");

			return supplierNames;
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
