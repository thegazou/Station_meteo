
package ch.hearc.meteo.imp.afficheur.real.station;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ch.hearc.meteo.imp.afficheur.real.afficheur.AfficheurServiceMOOReal;
import ch.hearc.meteo.imp.afficheur.real.afficheur.StateReal;
import ch.hearc.meteo.imp.afficheur.simulateur.vue.atome.MathTools;

public class JPanelMeteoGrapheVisual extends JPanel
	{

	public JPanelMeteoGrapheVisual(AfficheurServiceMOOReal afficheurServiceMOOReal)
		{
		temp=0;
		cpt=1;
		i=1;
		this.afficheurServiceMOOReal=afficheurServiceMOOReal;
		deltat=0;
		geometry();
		control();
		appearance();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/


	public void update()
		{
		StateReal stateReal1 = afficheurServiceMOOReal.getStatTemperature();
		if(temp!=stateReal1.getLast())
			{
			drawGraphTemp(Float.parseFloat(MathTools.arrondir(stateReal1.getLast())));
			temp=stateReal1.getLast();
			}


		}
	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/
	private void drawGraphTemp(float value)
		{

		/*if(deltat==0)
			{
			deltat=10;
			}*/
		deltat=10;
		if(temperature.getItemCount()>25)
			{
			temperature.clear();
			}
	    temperature.add(deltat*cpt, value);
	    cpt++;

		}

	private void geometry()
		{

		temperature = new XYSeries("Planned");
		temperature.add(0, 0);
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(temperature);
		XYDataset dataset_fonction = dataset;
		JFreeChart chart = ChartFactory.createXYLineChart("Temperature", "delta T (ms)", "Temperature (C°)", dataset_fonction);
		XYPlot plot = (XYPlot)chart.getPlot();
		plot.setBackgroundPaint(Color.BLACK);
		plot.setDomainGridlinePaint(new Color(0x00, 0x00, 0xff));
		plot.setRangeGridlinePaint(new Color(0xff, 0x00, 0x00));
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		plot.setRenderer(renderer);
		plot.getRenderer().setSeriesPaint(0, new Color(0x00, 0xFF, 0x00));
		ChartPanel chartPanel = new ChartPanel(chart);
		Dimension panelD = new Dimension(650,280);
        chartPanel.setPreferredSize(panelD);
        chartPanel.setMaximumSize(panelD);

			// Layout : Specification
			{
			FlowLayout flowlayout = new FlowLayout(FlowLayout.CENTER);
			setLayout(flowlayout);

			// flowlayout.setHgap(20);
			// flowlayout.setVgap(20);
			}

		// JComponent : add
		add(chartPanel);
		}

	private void control()
		{
		}

	private void appearance()
		{
		Dimension panelD = new Dimension(700,350);
        this.setPreferredSize(panelD);
        this.setMaximumSize(panelD);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private long deltat;
	private int cpt;
	private AfficheurServiceMOOReal afficheurServiceMOOReal;

	// Tools
	private XYSeries temperature;
	private int i;
	private float temp;


	}
