
package ch.hearc.meteo.imp.afficheur.real.station;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.ThermometerPlot;
import org.jfree.data.general.DefaultValueDataset;

import ch.hearc.meteo.imp.afficheur.real.afficheur.AfficheurServiceMOOReal;
import ch.hearc.meteo.imp.afficheur.simulateur.vue.atome.MathTools;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;

public class JPanelMeteoGraphe extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelMeteoGraphe(AfficheurServiceMOOReal afficheurServiceMOOReal)
		{
		i=1;
		this.afficheurServiceMOOReal=afficheurServiceMOOReal;
		geometry();
		control();
		appearance();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/
	public void update()
		{
		List<MeteoEvent> listMeteoEvent =afficheurServiceMOOReal.getListTemperature();
		for(MeteoEvent meteoEvent:listMeteoEvent)
			{
			if(i==listMeteoEvent.size())
				{
				drawTemperature(Float.parseFloat(MathTools.arrondir(meteoEvent.getValue())));
				i=1;
				}
			else{i++;}

			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/
	private void drawTemperature(float value)
		{
		dataset.setValue(value);
		}

	private void geometry()
		{
		// JComponent : Instanciation;
		 dataset = new DefaultValueDataset(0);
        temperatureThermometer = new ThermometerPlot(dataset);
        temperatureThermometer.setSubrange(0, -30.0, -10.0);
        temperatureThermometer.setSubrange(1, -9.999, 0.0);
        temperatureThermometer.setSubrange(2, 0.0, 9.999);
        temperatureThermometer.setSubrange(3, 10.0, 19.999);
        temperatureThermometer.setSubrange(4, 20.0, 29.999);
        temperatureThermometer.setSubrange(5, 30.0, 100.0);
        temperatureThermometer .setSubrangePaint(0, Color.blue.brighter());
        temperatureThermometer .setSubrangePaint(1, Color.cyan);
        temperatureThermometer .setSubrangePaint(2, Color.green);
        temperatureThermometer .setSubrangePaint(3, Color.yellow);
        temperatureThermometer .setSubrangePaint(4, Color.orange);
        temperatureThermometer .setSubrangePaint(5, Color.red);
        JFreeChart chart = new JFreeChart("Actual Temperature",
        JFreeChart.DEFAULT_TITLE_FONT, temperatureThermometer, true);
        chartPanel = new ChartPanel(chart);
		Dimension panelD = new Dimension(250,220);
		chartPanel.setPreferredSize(panelD);
		chartPanel.setMaximumSize(panelD);
		setLayout();

		}

	private void setLayout()
		{
		// JComponent : add
		add(chartPanel);

		}

	private void control()
		{
		// rien
		}

	private void appearance()
		{
		Dimension panelD = new Dimension(300,300);
        this.setPreferredSize(panelD);
        this.setMaximumSize(panelD);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools

	private ThermometerPlot  temperatureThermometer ;
	private ChartPanel chartPanel;
	private DefaultValueDataset dataset;
	private AfficheurServiceMOOReal afficheurServiceMOOReal;
	private int i;


	}
