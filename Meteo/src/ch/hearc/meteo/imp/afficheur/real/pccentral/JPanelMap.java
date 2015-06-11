
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

public class JPanelMap extends JPanel
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
		/*Random Position*/
		Random randomGenerator = new Random();
		int one = randomGenerator.nextInt(70);
		int two = randomGenerator.nextInt(70);
		int four = randomGenerator.nextInt(70);
		int five = randomGenerator.nextInt(70);
		mapspoint.put(ID, new GeoPosition(one, two, 0, four, five, 0));
		waypoints = this.peupler();
		panelDefault.setStringPort(ID);
		updateAll();
		mapViewer.setZoom(10);
		mapViewer.setAddressLocation(new GeoPosition(one, two, 0, four, five, 0));

		}

	public Set<MyWaypoint> peupler()
		{

		mapspoint = getMapsPoints();
		Iterator<Entry<String, GeoPosition>> mapIterator = mapspoint.entrySet().iterator();
		int i = 0;
		int numberOfStation = mapspoint.size();
		while(mapIterator.hasNext() && i < numberOfStation)
			{
			Entry<String, GeoPosition> value = mapIterator.next();
			waypoints.add(new MyWaypoint(i + 1 + "", Color.GREEN, value.getValue()));
			i++;
			}
		return waypoints;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void appearance()
		{
		//Nothing
		}

	private void control()
		{
		//Nothing
		}

	private void geometry()
		{

		/*Create a map*/
		TileFactoryInfo tilefactoryInfo = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
		DefaultTileFactory defaultTileFactory = new DefaultTileFactory(tilefactoryInfo);
		defaultTileFactory.setThreadPoolSize(10);
		File file = new File(System.getProperty("user.home") + File.separator + ".jxmapviewer2");
		LocalResponseCache.installResponseCache(tilefactoryInfo.getBaseURL(), file, false);

		/*Create new JXMapViewer*/
		mapViewer = new JXMapViewer();
		mapViewer.setTileFactory(defaultTileFactory);
		waypoints = new HashSet<MyWaypoint>();
		mapspoint = new LinkedHashMap<String, GeoPosition>();
		waypoints = this.peupler();
		updateAll();

		/*Set Default zoom */
		mapViewer.setZoom(8);
		mapViewer.setAddressLocation(new GeoPosition(40, 40, 0, 40, 40, 0));

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
