
package ch.hearc.meteo.imp.afficheur.simulateur.vue;

import java.awt.FlowLayout;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ch.hearc.meteo.imp.afficheur.simulateur.moo.AfficheurServiceMOO;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;

public class JPanelSlider extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelSlider(AfficheurServiceMOO afficheurServiceMOO)
		{
		this.afficheurServiceMOO = afficheurServiceMOO;

		geometry();
		apparence();
		control();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void updateMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions)
		{
		int value = (int)meteoServiceOptions.getTemperatureDT();
		jslider.setValue(value);
		setTitleBorder(value);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		int min = 1000;
		int max = 10000;
		int value;
		try
			{
			value = (int)afficheurServiceMOO.getMeteoServiceOptions().getTemperatureDT();
			}
		catch (RemoteException e)
			{
			value = (min + max) / 2;
			e.printStackTrace();
			}
		jslider = new JSlider(min, max, value);

		border = BorderFactory.createTitledBorder("");
		setTitleBorder(value);
		jslider.setBorder(border);

		setLayout(new FlowLayout(FlowLayout.CENTER));

		add(jslider);
		}

	private void apparence()
		{
		//setBackground(Color.ORANGE);

		jslider.setOrientation(SwingConstants.HORIZONTAL);
		}

	private void control()
		{
		jslider.addChangeListener(new ChangeListener()
			{

				@Override public void stateChanged(ChangeEvent e)
					{
					int value = jslider.getValue();

					try
						{
						MeteoServiceOptions meteoServiceOption = new MeteoServiceOptions(afficheurServiceMOO.getMeteoServiceOptions());
						meteoServiceOption.setTemperatureDT(value);

						setTitleBorder(value);
						afficheurServiceMOO.setMeteoServiceOptions(meteoServiceOption);
						}
					catch (RemoteException e1)
						{
						System.err.println("[JPanelSlider] remote update failed");
						e1.printStackTrace();
						}

					}
			});
		}

	private void setTitleBorder(int value)
		{
		border.setTitle("dt Temperature =" + value + " (ms)");
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Inputs
	private AfficheurServiceMOO afficheurServiceMOO;

	// Tools
	private JSlider jslider;
	private TitledBorder border;
	}
