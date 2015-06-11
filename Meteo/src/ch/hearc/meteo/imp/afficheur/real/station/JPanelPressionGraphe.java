
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

public class JPanelPressionGraphe extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelPressionGraphe(AfficheurServiceMOOReal afficheurServiceMOOReal)
		{
		temp = 0;
		this.afficheurServiceMOOReal = afficheurServiceMOOReal;
		deltat = 10;
		cpt = 0;
		geometry();
		control();
		appearance();
		}

	public void update()
		{
		StateReal stateReal1 = afficheurServiceMOOReal.getStatPression();
		if(temp!=stateReal1.getLast())
			{
			drawGraphPress(Float.parseFloat(MathTools.arrondir(stateReal1.getLast())));
			temp=stateReal1.getLast();
			}


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

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/
	private void drawGraphPress(float value)
		{
		cpt++;
		if (pression.getItemCount() > 25)
			{
			pression.clear();
			}
		pression.add(deltat * cpt, value);

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
		Dimension panelD = new Dimension(650, 280);
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
		Dimension panelD = new Dimension(700, 350);
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
	private int i;
	private float temp;

	private AfficheurServiceMOOReal afficheurServiceMOOReal;
	}
