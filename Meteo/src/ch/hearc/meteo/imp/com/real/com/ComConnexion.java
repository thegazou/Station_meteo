
package ch.hearc.meteo.imp.com.real.com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import ch.hearc.meteo.imp.com.logique.MeteoServiceCallback_I;
import ch.hearc.meteo.imp.com.real.com.trame.TrameDecoder;
import ch.hearc.meteo.imp.com.real.com.trame.TrameEncoder;
import ch.hearc.meteo.spec.com.meteo.exception.MeteoServiceException;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEventType_E;

// TODO student
//  Implémenter cette classe
//  Updater l'implémentation de MeteoServiceFactory.create()

/**
 * <pre>
 * Aucune connaissance des autres aspects du projet ici
 *
 * Ouvrer les flux vers le port com
 * ecouter les trames arrivantes (pas boucle, mais listener)
 * decoder trame
 * avertir meteoServiceCallback
 *
 *</pre>
 */
public class ComConnexion implements ComConnexions_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public ComConnexion(MeteoServiceCallback_I meteoServiceCallback, String portName, ComOption comOption)
		{
		this.comOption = comOption;
		this.portName = portName;
		this.meteoServiceCallback = meteoServiceCallback;
		}

	/**
	 * <pre>
	 * Problem :
	 * 		MeteoService est un MeteoServiceCallback_I
	 * 		ComConnexions_I utilise MeteoServiceCallback_I
	 * 		MeteoService utilise ComConnexions_I
	 *
	 * On est dans la situation
	 * 		A(B)
	 * 		B(A)
	 *
	 * Solution
	 * 		 B
	 * 		 A(B)
	 *  	 B.setA(A)
	 *
	 *  Autrement dit:
	 *
	 *		ComConnexions_I comConnexion=new ComConnexion( portName,  comOption);
	 *      MeteoService_I meteoService=new MeteoService(comConnexion);
	 *      comConnexion.setMeteoServiceCallback(meteoService);
	 *
	 *      Ce travail doit se faire dans la factory
	 *
	 *  Warning : call next
	 *  	setMeteoServiceCallback(MeteoServiceCallback_I meteoServiceCallback)
	 *
	 *  </pre>
	 */
	public ComConnexion(String portName, ComOption comOption)
		{
		this(null, portName, comOption);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public void start() throws Exception
		{
		serialPort.notifyOnDataAvailable(true);
		serialPort.addEventListener(new SerialPortEventListener()
			{

				@Override
				public void serialEvent(SerialPortEvent event)
					{
					switch(event.getEventType())
						{

						case SerialPortEvent.DATA_AVAILABLE:
							try
								{
								traiterDonnees();
								}
							catch (Exception e)
								{
								// TODO Auto-generated catch block
								e.printStackTrace();
								}

						}

					}
			});

		}

	protected void traiterDonnees() throws MeteoServiceException
		{

		String line;
		try
			{
			line = reader.readLine();
			float val = TrameDecoder.valeur(line);
			MeteoEventType_E type = TrameDecoder.dataType(line);

			//			byte[] tabByte = line.getBytes();
			//			int bitDataType = (0xff & tabByte[1]);

			//System.out.println("traiterDonnees: " + val);

			switch(type)
				{
				case PRESSION:
					{
					//System.out.println("traiterDonnes: PRESSION / Val = " + val);
					meteoServiceCallback.pressionPerformed(val);
					break;
					}
				case TEMPERATURE:
					{
					//System.out.println("traiterDonnes: TEMPERATURE / Val = " + val);
					meteoServiceCallback.temperaturePerformed(val);
					break;
					}
				case ALTITUDE:
					{
					//System.out.println("traiterDonnes: ALTITUDE / Val = " + val);
					meteoServiceCallback.altitudePerformed(val);
					break;
					}
				default:
					{
					System.err.println("ComConnexion:traiterdonnees / type de données non prévu");
					break;
					}
				}
			//System.out.println("line: " + val);
			}

		catch (IOException e)
			{
			throw new MeteoServiceException("[ComConnexion] traiterDonnees failure", e);
			}
		catch (MeteoServiceException e)
			{
			throw new MeteoServiceException("[ComConnexion] traiterDonnees failure", e);
			}

		}

	@Override
	public void stop() throws Exception
		{
		serialPort.removeEventListener();

		}

	@Override
	public void connect() throws Exception
		{
		CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(portName);
		serialPort = (SerialPort)portId.open("MeteoService", 10000);

		serialPort.setSerialPortParams(comOption.getSpeed(), comOption.getDataBit(), comOption.getStopBit(), comOption.getParity());
		serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);

		outputStream = serialPort.getOutputStream();
		reader = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));

		}

	@Override
	public void disconnect() throws Exception
		{
		serialPort.close();
		outputStream.close();
		reader.close();
		}

	@Override
	public void askAltitudeAsync() throws Exception
		{

		byte[] tabByte = TrameEncoder.coder("010000");
		outputStream.write(tabByte);
		}

	@Override
	public void askPressionAsync() throws Exception
		{
		byte[] tabByte = TrameEncoder.coder("010100");
		outputStream.write(tabByte);
		}

	@Override
	public void askTemperatureAsync() throws Exception
		{
		byte[] tabByte = TrameEncoder.coder("010200");
		outputStream.write(tabByte);
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	@Override
	public String getNamePort()
		{
		return portName;
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/**
	 * For post building
	 */
	public void setMeteoServiceCallback(MeteoServiceCallback_I meteoServiceCallback)
		{
		this.meteoServiceCallback = meteoServiceCallback;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/


	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Input
	private ComOption comOption;
	private String portName;
	private MeteoServiceCallback_I meteoServiceCallback;

	// Tools
	private SerialPort serialPort;
	private BufferedWriter writer;
	private BufferedReader reader;
	private OutputStream outputStream;

	}
