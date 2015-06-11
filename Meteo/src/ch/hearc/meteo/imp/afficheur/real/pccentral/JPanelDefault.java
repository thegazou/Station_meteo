
package ch.hearc.meteo.imp.afficheur.real.pccentral;

import java.awt.FlowLayout;
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
	public void setMap(Map<String, MeteoServiceWrapper_I> stationList2, AffichageOptions affichageOptions)
		{
		this.stationList = stationList2;
		for(int i = 1; i <= stationList2.size(); i++)
			{
			//System.out.println("JpanelDefaut: "+stationList.get(list.get(i - 1)));
			tabPanelMini[i - 1].setService(stationList2.get(list.get(i - 1)),affichageOptions);
			}

		}
	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private List<String> list;
	private Map<String, MeteoServiceWrapper_I> stationList;
	private JPanelMini[] tabPanelMini;

	}
