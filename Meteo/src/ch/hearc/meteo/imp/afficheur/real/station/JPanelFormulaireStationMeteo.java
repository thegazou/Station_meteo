
package ch.hearc.meteo.imp.afficheur.real.station;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;



public class JPanelFormulaireStationMeteo extends JPanel implements AfficheurService_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelFormulaireStationMeteo()
		{
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

		}

	@Override
	public void printAltitude(MeteoEvent event)
		{
		// TODO Auto-generated method stub

		}

	@Override
	public void printTemperature(MeteoEvent event)
		{
		// TODO Auto-generated method stub

		}

	@Override
	public void updateMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions)
		{
		// TODO Auto-generated method stub

		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
			// JComponent : Instanciation
			deltat=new JSlider(SwingConstants.HORIZONTAL);
			ldeltat= new JLabel("Delta t:");
			start= new JButton("Start");
			stop= new JButton("Stop");
			pause= new JButton("Pause");
			temp=new JRadioButton("Temperature");
			press=new JRadioButton("Pression");
			altitude=new JRadioButton("Altitude");
			ButtonGroup bg = new ButtonGroup();
			bg.add(temp);
			bg.add(press);
			bg.add(altitude);
			setFormulaireLayout();
			ldeltat .setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
			temp.setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
			altitude.setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
			press.setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
			temp.setSelected(true);

		}

	private void setFormulaireLayout()
		{
		// Layout : Specification
			{
			FlowLayout flowlayout = new FlowLayout(FlowLayout.CENTER);
			setLayout(flowlayout);

			// flowlayout.setHgap(20);
			// flowlayout.setVgap(20);
			}
		add(ldeltat);
		add(deltat);
		add(temp);
		add(press);
		add(altitude);
		add(start);
		add(pause);
		add(stop);

		}

	private void control()
		{
		// rien
		}

	private void appearance()
		{
		Dimension panelD = new Dimension(250,150);
        this.setPreferredSize(panelD);
        this.setMaximumSize(panelD);
		this.setBorder(BorderFactory.createTitledBorder("Station Status"));
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	JSlider deltat ;
	JLabel ldeltat;
	JButton start;
	JButton stop;
	JButton pause;
	JRadioButton temp;
	JRadioButton press;
	JRadioButton altitude;



	}

