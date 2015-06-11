
package ch.hearc.meteo.imp.afficheur.real.pccentral;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoService_I;

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
		JPanel TabPane = new JPanel();
		getContentPane().add(TabPane);
		TabPane.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
		TabPane.add(tabbedPane, BorderLayout.CENTER);

		panelDefault = new JPanelDefault();
		JPanel panelMap = new JPanel();
		tabbedPane.addTab("Map", null, panelMap, null);
		tabbedPane.addTab("Station Information", null, panelDefault, null);
		panelMap.setLayout(new GridLayout(0, 1, 0, 0));

		mapPanel = new JPanelMap(panelDefault);
		panelMap.add(mapPanel, BorderLayout.CENTER);
		stationList = new HashMap<String, MeteoService_I>();
		af = new AffichageOptions(3, "COM5 COM6");
		addStation(af, meteo);
		}

	private void control()
		{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		}

	private void appearance()
		{
		setSize(600, 400);
		setLocationRelativeTo(null);
		setVisible(true);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public static void addStation(AffichageOptions affichageOptions, MeteoService_I service)
		{
		if (stationList.containsKey(affichageOptions.getTitre()))
			{
			stationList.replace(affichageOptions.getTitre(), service);
			panelDefault.setMap(stationList);
			}
		else
			{
			//System.out.println("FramePrincipale: "+service.getPort());
			stationList.put(affichageOptions.getTitre(), service);
			mapPanel.setMapsPoints(affichageOptions.getTitre());
			panelDefault.setMap(stationList);
			}

		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private static JPanelDefault panelDefault;
	private static JPanelMap mapPanel;
	private MeteoService_I meteo;
	private AffichageOptions af;
	private static Map<String, MeteoService_I> stationList;
	}
