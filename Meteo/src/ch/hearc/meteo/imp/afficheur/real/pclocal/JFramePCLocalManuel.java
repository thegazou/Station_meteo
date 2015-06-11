
package ch.hearc.meteo.imp.afficheur.real.pclocal;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import ch.hearc.meteo.imp.afficheur.real.afficheur.AfficheurFactory;
import ch.hearc.meteo.imp.com.real.MeteoPortDetectionService;
import ch.hearc.meteo.imp.com.real.MeteoServiceFactory;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoService_I;
import ch.hearc.meteo.spec.com.meteo.exception.MeteoServiceException;
import ch.hearc.meteo.spec.com.meteo.listener.MeteoAdapter;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper;
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper_I;

import com.bilat.tools.reseau.rmi.RmiTools;

public class JFramePCLocalManuel extends JFrame
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JFramePCLocalManuel(MeteoPortDetectionService meteoPortDetectionService)
		{
		this.meteoPortDetectionService = meteoPortDetectionService;
		geometry();
		control();
		appearance();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/
	public static void use(MeteoService_I meteoService) throws MeteoServiceException
		{
		// Service Meteo
		meteoService.connect();
		MeteoServiceOptions meteoServiceOptions = new MeteoServiceOptions(800, 1000, 1200);
		meteoService.start(meteoServiceOptions);

		// Service Affichage
		MeteoServiceWrapper_I meteoServiceWrapper = new MeteoServiceWrapper(meteoService);
		String titre = RmiTools.getLocalHost() + " " + meteoService.getPort();
		AffichageOptions affichageOption = new AffichageOptions(3, titre);
		AfficheurService_I afficheurService1 = (new AfficheurFactory()).createOnLocalPC(affichageOption, meteoServiceWrapper);
		use(meteoService, afficheurService1);
		}

	public static void use(final MeteoService_I meteoService, final AfficheurService_I afficheurService) throws MeteoServiceException
		{
		meteoService.addMeteoListener(new MeteoAdapter()
			{

				@Override
				public void temperaturePerformed(MeteoEvent event)
					{
					afficheurService.printTemperature(event);
					}

				@Override
				public void altitudePerformed(MeteoEvent event)
					{
					afficheurService.printAltitude(event);
					}

				@Override
				public void pressionPerformed(MeteoEvent event)
					{
					afficheurService.printPression(event);
					}

			});

		// Modify MeteoServiceOptions
		Thread threadSimulationChangementDt = new Thread(new Runnable()
			{

				@Override
				public void run()
					{
					double x = 0;
					double dx = Math.PI / 10;

					while(true)
						{
						long dt = 1000 + (long)(5000 * Math.abs(Math.cos(x))); //ms

						System.out.println("modification dt temperature = " + dt);

						meteoService.getMeteoServiceOptions().setTemperatureDT(dt);

						//	System.out.println(meteoService.getMeteoServiceOptions());

						attendre(3000); // disons
						x += dx;
						}
					}
			});

		// Update GUI MeteoServiceOptions
		Thread threadPoolingOptions = new Thread(new Runnable()
			{

				@Override
				public void run()
					{

					while(true)
						{
						MeteoServiceOptions option = meteoService.getMeteoServiceOptions();
						afficheurService.updateMeteoServiceOptions(option);

						//System.out.println(option);

						attendre(1000); //disons
						}
					}
			});

		threadSimulationChangementDt.start();
		threadPoolingOptions.start(); // update gui
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private static void attendre(long delay)
		{
		try
			{
			Thread.sleep(delay);
			}
		catch (InterruptedException e)
			{
			e.printStackTrace();
			}
		}

	private void geometry()
		{
		populate();
		setLayout();
		setControl();
		}

	private void setControl()
		{
		for(int i = 0; i < tailleListe; i++)
			{
			comList.get(i).addMouseListener(new MouseListener()
				{

					@Override
					public void mouseReleased(MouseEvent arg0)
						{
						}

					@Override
					public void mousePressed(MouseEvent arg0)
						{
						}

					@Override
					public void mouseExited(MouseEvent arg0)
						{
						}

					@Override
					public void mouseEntered(MouseEvent arg0)
						{
						}

					@Override
					public void mouseClicked(MouseEvent arg0)
						{
						setMeteoService(arg0.getSource());
						}

				});
			}

		}

	private void setMeteoService(Object object)
		{
		JButton button = (JButton)object;
		button.setVisible(false);
		for(int i = 0; i < tailleListe; i++)
			{
			if (button.getText() == comList.get(i).getText())
				{
				statList.get(i).setText("Used");
				statList.get(i).setVisible(false);
				}
			}
		MeteoService_I meteoService = (new MeteoServiceFactory()).create(button.getText());
		try
			{
			use(meteoService);
			}
		catch (MeteoServiceException e)
			{
			e.printStackTrace();
			}

		}

	private void setLayout()
		{
		Box left = Box.createVerticalBox();
		Box right = Box.createVerticalBox();
		for(int i = 0; i < tailleListe; i++)
			{
			/* Test si connecter pour able ou non */
			}

		for(int i = 0; i < tailleListe; i++)
			{
			comList.get(i).setPreferredSize(new Dimension(140, 30));
			left.add(comList.get(i));
			left.add(Box.createVerticalGlue());
			statList.get(i).setPreferredSize(new Dimension(140, 30));
			right.add(statList.get(i));
			right.add(Box.createVerticalGlue());
			}
		Box line = Box.createHorizontalBox();
		line.add(left);
		line.add(right);
		setLayout(new BorderLayout());
		add(line, BorderLayout.CENTER);

		}

	private void populate()
		{

		List<String> portList = meteoPortDetectionService.findListPortMeteo();
		System.out.println(portList);
		String state = "Connected";
		tailleListe = portList.size();
		// JComponent : Instanciation
		comList = new ArrayList<JButton>();
		statList = new ArrayList<JLabel>();
		if (tailleListe != 0)
			{
			for(int i = 0; i < tailleListe; i++)
				{
				comList.add(new JButton(portList.get(i).toString()));
				statList.add(new JLabel(state));
				}
			}
		else
			{
			JOptionPane.showMessageDialog(null,"0 Station Avaiable Actually");
			}
		}

	private void control()
		{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		}

	private void appearance()
		{
		setSize(200, 300);
		setLocationRelativeTo(null); // frame centrer
		setVisible(true); // last!
		setTitle("Stations");

		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	private MeteoPortDetectionService meteoPortDetectionService;
	private ArrayList<JButton> comList;
	private ArrayList<JLabel> statList;
	private int tailleListe;

	}
