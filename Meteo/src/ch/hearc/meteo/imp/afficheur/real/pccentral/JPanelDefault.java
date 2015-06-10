
package ch.hearc.meteo.imp.afficheur.real.pccentral;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import org.jxmapviewer.viewer.GeoPosition;

import ch.hearc.meteo.spec.com.meteo.MeteoService_I;

public class JPanelDefault extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelDefault()
		{
		list = new ArrayList<String>();
		setLayout(new FlowLayout());

		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void update(Map<String, GeoPosition> mapspoint)
		{

		Map<String, GeoPosition> map = mapspoint;
		tabPanelMini = new JPanelMini[map.size()];
		removeAll();
		revalidate();
		repaint();
		for(int i = 1; i <= map.size(); i++)
			{
			tabPanelMini[i - 1] = new JPanelMini(i, list.get(i - 1));
			add(tabPanelMini[i - 1]);
			}

		}

	public JPanelMini[] getTabPanelMini()
		{
		return tabPanelMini;
		}


	public void setStringPort(String iD)
		{
		list.add(iD);

		}
	public void setMap(Map<String, MeteoService_I> stationList)
		{
		this.stationList = stationList;
		for(int i = 1; i <= stationList.size(); i++)
			{
			//System.out.println("JpanelDefaut: "+stationList.get(list.get(i - 1)));
			tabPanelMini[i - 1].setService(stationList.get(list.get(i - 1)));
			}

		}
	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private List<String> list;
	private Map<String, MeteoService_I> stationList;
	private JPanelMini[] tabPanelMini;

	}
