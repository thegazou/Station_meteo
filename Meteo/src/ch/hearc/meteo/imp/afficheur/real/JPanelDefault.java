
package ch.hearc.meteo.imp.afficheur.real;

import java.awt.FlowLayout;
import java.util.Map;

import javax.swing.JPanel;

import org.jxmapviewer.viewer.GeoPosition;

import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;

public class JPanelDefault extends JPanel implements AfficheurService_I
	{

	public JPanelDefault()
		{
		tabPanelMini = new JPanelMini[6];
		setLayout(new FlowLayout());

		}

	public void update(Map<String, GeoPosition> mapspoint)
		{
		Map<String, GeoPosition> map = mapspoint;
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

	@Override
	public void printPression(MeteoEvent event)
		{
		// TODO Auto-generated method stub
		for(int i=0;i<6;i++)
			{
		tabPanelMini[0].printPression(event);
			}
		}

	@Override
	public void printAltitude(MeteoEvent event)
		{
		// TODO Auto-generated method stub
		for(int i=0;i<6;i++)
			{
		tabPanelMini[i].printAltitude(event);
			}
		}

	@Override
	public void printTemperature(MeteoEvent event)
		{
		for(int i=0;i<6;i++)
			{
		tabPanelMini[i].printTemperature(event);
			}

		}

	@Override
	public void updateMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions)
		{
		// TODO Auto-generated method stub

		}

	}
