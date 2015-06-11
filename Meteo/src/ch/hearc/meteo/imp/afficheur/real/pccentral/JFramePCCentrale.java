
package ch.hearc.meteo.imp.afficheur.real.pccentral;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.exception.MeteoServiceException;
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper_I;

public class JFramePCCentrale extends JFrame
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JFramePCCentrale()
		{

		geometry();
		control();
		appearance();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		stationList = new HashMap<String, MeteoServiceWrapper_I>();
		JPanel TabPane = new JPanel();
		getContentPane().add(TabPane);
		TabPane.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
		TabPane.add(tabbedPane, BorderLayout.CENTER);

		panelDefault = new JPanelDefault();
		JPanel panelMap = new JPanel();
		tabbedPane.addTab("World Map", null, panelMap, null);
		tabbedPane.addTab("Station Information", null, panelDefault, null);
		panelMap.setLayout(new GridLayout(0, 1, 0, 0));

		mapPanel = new JPanelMap(panelDefault);
		panelMap.add(mapPanel, BorderLayout.CENTER);
		meteoServiceWrapper=new MeteoServiceWrapper_I()
			{

				@Override
				public void stop() throws RemoteException
					{
					// TODO Auto-generated method stub

					}

				@Override
				public void start(MeteoServiceOptions meteoServiceOptions) throws RemoteException
					{
					// TODO Auto-generated method stub

					}

				@Override
				public void setMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions) throws RemoteException
					{
					// TODO Auto-generated method stub

					}

				@Override
				public boolean isRunning() throws RemoteException
					{
					// TODO Auto-generated method stub
					return false;
					}

				@Override
				public boolean isConnect() throws RemoteException
					{
					// TODO Auto-generated method stub
					return false;
					}

				@Override
				public String getPort() throws RemoteException
					{
					// TODO Auto-generated method stub
					return null;
					}

				@Override
				public MeteoServiceOptions getMeteoServiceOptions() throws RemoteException
					{
					// TODO Auto-generated method stub
					return null;
					}

				@Override
				public void disconnect() throws RemoteException, MeteoServiceException
					{
					// TODO Auto-generated method stub

					}

				@Override
				public void connect() throws RemoteException, MeteoServiceException
					{
					// TODO Auto-generated method stub

					}
			};
		affichageOptions=new AffichageOptions(10, "COM1");
		JFramePCCentrale.addStation(affichageOptions, meteoServiceWrapper);
		}

	private void control()
		{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		}

	private void appearance()
		{
		setSize(750, 450);
		setLocationRelativeTo(null);
		setVisible(true);
		this.setTitle("Central work Computer");
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public static void addStation(AffichageOptions affichageOptions, MeteoServiceWrapper_I serviceWrapper)
		{
		if (stationList.containsKey(affichageOptions.getTitre()))
			{
			stationList.replace(affichageOptions.getTitre(), serviceWrapper);
			panelDefault.setMap(stationList, affichageOptions);
			}
		else
			{
			stationList.put(affichageOptions.getTitre(), serviceWrapper);
			mapPanel.setMapsPoints(affichageOptions.getTitre());
			panelDefault.setMap(stationList, affichageOptions);
			}

		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private static JPanelDefault panelDefault;
	private static JPanelMap mapPanel;
	private static Map<String, MeteoServiceWrapper_I> stationList;
	private MeteoServiceWrapper_I meteoServiceWrapper;
	private AffichageOptions affichageOptions;
	}
