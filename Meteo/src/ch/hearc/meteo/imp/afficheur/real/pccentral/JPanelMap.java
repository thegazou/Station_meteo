
package ch.hearc.meteo.imp.afficheur.real.pccentral;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.LocalResponseCache;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;

import ch.hearc.meteo.imp.afficheur.real.jxmap.FancyWaypointRenderer;
import ch.hearc.meteo.imp.afficheur.real.jxmap.MyWaypoint;
import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;

public class JPanelMap extends JPanel implements AfficheurService_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelMap(JPanelDefault panelDefault)
		{
		this.panelDefault = panelDefault;
		geometry();
		control();
		appearance();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public void printPression(MeteoEvent event)
		{
		// TODO Auto-generated method stub
		panelDefault.printPression(event);

		}

	@Override
	public void printAltitude(MeteoEvent event)
		{
		// TODO Auto-generated method stub
		panelDefault.printAltitude(event);
		}

	@Override
	public void printTemperature(MeteoEvent event)
		{
		// TODO Auto-generated method stub
		panelDefault.printTemperature(event);
		}

	@Override
	public void updateMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions)
		{
		// TODO Auto-generated method stub

		}

	public void updateAll()
		{
		panelDefault.update(mapspoint);
		WaypointPainter<MyWaypoint> waypointPainter = new WaypointPainter<MyWaypoint>();
		waypointPainter.setWaypoints(waypoints);
		waypointPainter.setRenderer(new FancyWaypointRenderer());
		mapViewer.setOverlayPainter(waypointPainter);
		}

	public Map<String, GeoPosition> getMapsPoints()
		{
		return mapspoint;
		}

	public void setMapsPoints(String ID)
		{
		Random randomGenerator = new Random();
		int one = randomGenerator.nextInt(50);
		int two = randomGenerator.nextInt(50);

		int four = randomGenerator.nextInt(50);
		int five = randomGenerator.nextInt(50);

		mapspoint.put(ID, new GeoPosition(one, two, 0, four, five, 0));
		waypoints = this.peupler();
		updateAll();
		mapViewer.setZoom(10);
		mapViewer.setAddressLocation(new GeoPosition(one, two, 0, four, five, 0));

		}

	public Set<MyWaypoint> peupler()
		{

		mapspoint = getMapsPoints();
		Iterator<Entry<String, GeoPosition>> iteratormap = mapspoint.entrySet().iterator();

		int i = 0;
		int nombreDeStationDetecte = mapspoint.size();
		while(iteratormap.hasNext() && i < nombreDeStationDetecte)
			{
			Entry<String, GeoPosition> myvalue = iteratormap.next();
			if (i == 1)
				{
				waypoints.add(new MyWaypoint(i + 1 + "", Color.BLUE, myvalue.getValue()));
				}
			else
				{
				waypoints.add(new MyWaypoint(i + 1 + "", Color.GRAY, myvalue.getValue()));
				}

			i++;
			}

		return waypoints;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void appearance()
		{

		}

	private void control()
		{

		}

	private void geometry()
		{

		/*Create a map*/
		TileFactoryInfo tilefactoryInfo = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
		DefaultTileFactory defaultTileFactory = new DefaultTileFactory(tilefactoryInfo);
		defaultTileFactory.setThreadPoolSize(8);
		File file = new File(System.getProperty("user.home") + File.separator + ".jxmapviewer2");
		LocalResponseCache.installResponseCache(tilefactoryInfo.getBaseURL(), file, false);
		mapViewer = new JXMapViewer();
		mapViewer.setTileFactory(defaultTileFactory);
		waypoints = new HashSet<MyWaypoint>();
		mapspoint = new LinkedHashMap<String, GeoPosition>();
		waypoints = this.peupler();
		updateAll();

		mapViewer.setZoom(10);
		mapViewer.setAddressLocation(new GeoPosition(50, 7, 0, 8, 41, 0));

		/*Zoom and other functions */
		MouseInputListener miListener = new PanMouseInputListener(mapViewer);
		mapViewer.addMouseListener(miListener);
		mapViewer.addMouseMotionListener(miListener);
		mapViewer.addMouseListener(new CenterMapListener(mapViewer));
		mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));
		mapViewer.addKeyListener(new PanKeyListener(mapViewer));

		/*Layout*/
		setLayout(new BorderLayout());
		add(mapViewer, BorderLayout.CENTER);

		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	private JXMapViewer mapViewer;
	private static Map<String, GeoPosition> mapspoint;
	private static Set<MyWaypoint> waypoints;

	//inputs
	private JPanelDefault panelDefault;

	}
