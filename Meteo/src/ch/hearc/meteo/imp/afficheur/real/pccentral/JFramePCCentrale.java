
package ch.hearc.meteo.imp.afficheur.real.pccentral;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoService_I;

public class JFramePCCentrale extends JFrame
	{

	public JFramePCCentrale()
		{
		geometry();
		control();
		appearance();
		}

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

	private JPanelDefault panelDefault;

	/*if(mapTitreToClient.contains(affichageOptions.titre()))
	    Client client = mapTitreToClient.getValue(affichageOptions.titre());
	    client.setAfficheurService(telecommndeMeteoServiceLocal);
	    client.update();
	else
	    Client client = new Client(affichageOptions.titre(), telecommandeMeteoServiceLocal);
	    mapTitreToClient.add(affichageOptions.titre(),client);
	    AffichageFactory().creatOnCentralPC(affichageOptions.titre(), telecommandeMeteoServiceLocal);*/

	public void addStation(AffichageOptions affichageOptions, MeteoService_I service)
		{
		if (stationList.containsKey(affichageOptions.getTitre()))
			{
			mapPanel.setMapsPoints(affichageOptions.getTitre());
			stationList.put(affichageOptions.getTitre(), service);
			}
		else
			{

			}

		}

	private JPanelMap mapPanel;
	private Map<String,MeteoService_I> stationList;
	}
