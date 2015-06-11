
package ch.hearc.meteo.imp.afficheur.real.station;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import ch.hearc.meteo.imp.afficheur.real.afficheur.AfficheurServiceMOOReal;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper_I;

public class JPanelFormulaireStationMeteo extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelFormulaireStationMeteo(AfficheurServiceMOOReal afficheurServiceMOOReal)
		{
		this.afficheurServiceMOOReal=afficheurServiceMOOReal;
		meteoServiceRemote=afficheurServiceMOOReal.getMeteoServiceRemote();
		dtAltitude = 0;
		dtPression = 0;
		dtTemp = 0;
		geometry();
		control();
		appearance();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/
	public void update()
		{
		// NOT TO DO
		}

	public void updateMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions)
		{
		// NOT TO DO

		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		// JComponent : Instanciation
		deltat = new JSlider(SwingConstants.HORIZONTAL);
		deltat.setMaximum(100);
		deltat.setMinimum(10);
		ldeltat = new JLabel("Delta t:");
		start = new JButton("Start");
		stop = new JButton("Stop");
		pause = new JButton("Pause");
		temp = new JRadioButton("Temperature");
		press = new JRadioButton("Pression");
		altitude = new JRadioButton("Altitude");
		ButtonGroup bg = new ButtonGroup();
		bg.add(temp);
		bg.add(press);
		bg.add(altitude);
		setFormulaireLayout();
		ldeltat.setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
		temp.setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
		altitude.setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
		press.setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
		temp.setSelected(true);
		deltat.addMouseListener(new MouseListener()
			{

				@Override
				public void mouseReleased(MouseEvent e)
					{
					// TODO Auto-generated method stub
					setDT(deltat.getValue());
					}

				@Override
				public void mousePressed(MouseEvent e)
					{
					// TODO Auto-generated method stub

					}

				@Override
				public void mouseExited(MouseEvent e)
					{
					// TODO Auto-generated method stub

					}

				@Override
				public void mouseEntered(MouseEvent e)
					{
					// TODO Auto-generated method stub

					}

				@Override
				public void mouseClicked(MouseEvent e)
					{
					// TODO Auto-generated method stub

					}
			});

		}

	private void setDT(int value)
		{
		if (temp.isSelected())
			{
			dtTemp = value;
			}
		if (press.isSelected())
			{
			dtPression = value;
			}
		if (altitude.isSelected())
			{
			dtAltitude = value;
			}
		}

	private void setFormulaireLayout()
		{
			{
			FlowLayout flowlayout = new FlowLayout(FlowLayout.CENTER);
			setLayout(flowlayout);
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
		start.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
					{
					try
						{
						long altitudeDT = 2000;
						long pressionDT = 3000;
						long temperatureDT = 1000;
						MeteoServiceOptions meteoServiceOptions = new MeteoServiceOptions(altitudeDT, pressionDT, temperatureDT);

						meteoServiceRemote.start(meteoServiceOptions);

						enableStop();
						}
					catch (RemoteException e1)
						{
						e1.printStackTrace();
						}

					}
			});

		stop.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
					{
					try
						{
						meteoServiceRemote.stop();

						enableStart();
						}
					catch (RemoteException e1)
						{
						e1.printStackTrace();
						}

					}
			});

		pause.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
					{
					afficheurServiceMOOReal.setPause(!afficheurServiceMOOReal.isPause());
					}
			});

		threadEtatBouton = new Thread(new Runnable()
			{

				@Override
				public void run()
					{
					while(true)
						{
						try
							{
							sleep(POOLING_ETAT_BOUTON_DT);
							updateEtatBouton();
							}
						catch (RemoteException e)
							{
							System.err.println("Connexion perdu avec " + afficheurServiceMOOReal.getTitre());
							disableStartAndStop();
							System.out.println("[TODO]: Mise en veille du thread de récupération.");

							}
						}
					}
			});
		threadEtatBouton.start();
		}

	private void updateEtatBouton() throws RemoteException
		{

		if (meteoServiceRemote.isRunning())
			{
			enableStop();
			}
		else
			{
			enableStart();
			}
		}

	private void enableStart()
		{
		stop.setEnabled(false);
		start.setEnabled(true);
		}

	private void enableStop()
		{
		start.setEnabled(false);
		stop.setEnabled(true);
		}

	//Gère la déconnexion à chaud
	private void disableStartAndStop()
		{
		start.setEnabled(false);
		stop.setEnabled(false);
		}

	@SuppressWarnings("deprecation")
	public void stop()
		{
		threadEtatBouton.stop();
		}

	public void start()
		{
		threadEtatBouton.start();
		}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static void sleep(long delayMS)
		{
		try
			{
			Thread.sleep(delayMS);
			}
		catch (InterruptedException e)
			{
			e.printStackTrace();
			}
		}


	private void appearance()
		{
		Dimension panelD = new Dimension(250, 150);
		this.setPreferredSize(panelD);
		this.setMaximumSize(panelD);
		this.setBorder(BorderFactory.createTitledBorder("Station Status"));
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	JSlider deltat;
	JLabel ldeltat;
	JButton start;
	JButton stop;
	JButton pause;
	JRadioButton temp;
	JRadioButton press;
	JRadioButton altitude;
	private int dtTemp;
	private int dtPression;
	private int dtAltitude;
	private AfficheurServiceMOOReal afficheurServiceMOOReal;
	private MeteoServiceWrapper_I meteoServiceRemote;
	private Thread threadEtatBouton;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static final long POOLING_ETAT_BOUTON_DT = 2000;


	}
