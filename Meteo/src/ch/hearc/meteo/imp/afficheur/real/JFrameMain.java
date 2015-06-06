
package ch.hearc.meteo.imp.afficheur.real;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoService_I;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper_I;

public class JFrameMain extends JFrame implements AfficheurService_I
	{

	public JFrameMain(MeteoService_I meteoService)
		{
		this.meteoService = meteoService;
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

		mapPanel= new JPanelMap(panelDefault);
		panelMap.add(mapPanel, BorderLayout.CENTER);

		JPanel panelMenu = new JPanel();
		getContentPane().add(panelMenu, BorderLayout.NORTH);
		panelMenu.setLayout(new BorderLayout(0, 0));

		JToolBar toolBarMenu = new JToolBar();
		toolBarMenu.setFloatable(false);
		panelMenu.add(toolBarMenu, BorderLayout.NORTH);

		mapPanel.setMapsPoints("Londre");
		mapPanel.setMapsPoints("Neuch");
		mapPanel.setMapsPoints("Locle");
		mapPanel.setMapsPoints("France");
		mapPanel.setMapsPoints("La Brevine");
		mapPanel.setMapsPoints("star");
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

	@Override
	public void printPression(MeteoEvent event)
		{
		mapPanel.printPression(event);
		}

	@Override
	public void printAltitude(MeteoEvent event)
		{
		mapPanel.printAltitude(event);
		}

	@Override
	public void printTemperature(MeteoEvent event)
		{
		mapPanel.printTemperature(event);
		}

	@Override
	public void updateMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions)
		{
		// TODO Auto-generated method stub

		}
	public void add(AffichageOptions affichageOptions, MeteoServiceWrapper_I telecommandeMeteoServiceLocal)
	{

	}


	private MeteoService_I meteoService;
	private JPanelMap mapPanel;

	}
