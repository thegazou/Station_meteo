
package ch.hearc.meteo.imp.afficheur.real.pccentral;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import org.jxmapviewer.viewer.GeoPosition;

import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper_I;

public class JPanelDefault extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelDefault()
		{
		portsList = new ArrayList<String>();
		//setLayout(new FlowLayout());
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void update(Map<String, GeoPosition> mapsPoint)
		{
		Map<String, GeoPosition> mapsPoints = mapsPoint;
		tabPanelMini = new JPanelMini[mapsPoints.size()];
		removeAll();
		revalidate();
		repaint();
		for(int i = 1; i <= mapsPoints.size(); i++)
			{
			tabPanelMini[i - 1] = new JPanelMini(i, portsList.get(i - 1));
			add(tabPanelMini[i - 1]);
			}
		}

	public JPanelMini[] getTabPanelMini()
		{
		return tabPanelMini;
		}

	public void setStringPort(String port)
		{
		portsList.add(port);
		}

	public void setMap(Map<String, MeteoServiceWrapper_I> stationLists, AffichageOptions affichageOptions)
		{
		this.stationList = stationLists;
		for(int i = 1; i <= stationLists.size(); i++)
			{
			tabPanelMini[i - 1].setService(stationLists.get(portsList.get(i - 1)), affichageOptions);
			}

		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private List<String> portsList;
	private Map<String, MeteoServiceWrapper_I> stationList;
	private JPanelMini[] tabPanelMini;

	}
