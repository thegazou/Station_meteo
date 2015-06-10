
package ch.hearc.meteo.imp.afficheur.real.pccentral;

import java.awt.FlowLayout;
import java.util.Map;

import javax.swing.JPanel;

import org.jxmapviewer.viewer.GeoPosition;

public class JPanelDefault extends JPanel
	{

	public JPanelDefault()
		{

		setLayout(new FlowLayout());

		}

	public void update(Map<String, GeoPosition> mapspoint)
		{

		Map<String, GeoPosition> map = mapspoint;
		tabPanelMini = new JPanelMini[map.size()];
		removeAll();
		revalidate();
		repaint();
		for(int i = 1; i <= map.size(); i++)
			{
			tabPanelMini[i - 1] = new JPanelMini(i, "");
			add(tabPanelMini[i - 1]);
			}

		}

	public JPanelMini[] getTabPanelMini()
		{
		return tabPanelMini;
		}

	private JPanelMini[] tabPanelMini;



	}
