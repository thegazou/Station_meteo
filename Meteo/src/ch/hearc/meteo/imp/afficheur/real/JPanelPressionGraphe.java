
package ch.hearc.meteo.imp.afficheur.real;

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

import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;

public class JPanelPressionGraphe extends JPanel implements AfficheurService_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelPressionGraphe()
		{
		deltat=10;
		cpt=0;
		geometry();
		control();
		appearance();
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/
	@Override
	public void printPression(MeteoEvent event)
		{
		// TODO Auto-generated method stub
		drawGraphPress(event.getValue());

		}

	@Override
	public void printAltitude(MeteoEvent event)
		{
		// TODO Auto-generated method stub

		}

	@Override
	public void printTemperature(MeteoEvent event)
		{


		}

	@Override
	public void updateMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions)
		{
		// TODO Auto-generated method stub

		}

	public void dessiner()
		{

		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/
	private void drawGraphPress(float value)
		{
		cpt++;
		if(pression.getItemCount()>25)
			{
			pression.clear();
			}
		pression.add(deltat*cpt, value);


		}

	private void geometry()
		{

		pression = new XYSeries("Planned");
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(pression);
		XYDataset dataset_fonction = dataset;
		JFreeChart chart = ChartFactory.createXYLineChart("Pression", "delta T (ms)", "Pression (hPa)", dataset_fonction);
		XYPlot plot = (XYPlot)chart.getPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		plot.setBackgroundPaint(Color.BLACK);
		plot.setDomainGridlinePaint(new Color(0x00, 0x00, 0xff));
		plot.setRangeGridlinePaint(new Color(0xff, 0x00, 0x00));
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

	private int deltat;
	private int cpt;

	// Tools
	private XYSeries pression;
	}
