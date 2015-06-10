
package ch.hearc.meteo.imp.afficheur.real.pclocal;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ch.hearc.meteo.imp.afficheur.real.AfficheurFactory;
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

public class JPanelChoice extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelChoice(MeteoPortDetectionService meteoPortDetectionService)
		{
		this.meteoPortDetectionService = meteoPortDetectionService;
		geometry();
		control();
		appearance();
		}

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
			// JComponent : Instanciation
			multipleCom= new JButton("Use all station");
			defaultCom= new JButton("Use default station");
			final List<String> portList=meteoPortDetectionService.findListPortMeteo();
			defaultCom.addMouseListener(new MouseListener()
				{
					@Override
					public void mouseClicked(MouseEvent e)
						{
						if(portList.size()!=0){
							MeteoService_I meteoService = (new MeteoServiceFactory()).create(portList.get(0).toString());
							try
								{
								use(meteoService);
								}
							catch (MeteoServiceException e1)
								{
								e1.printStackTrace();
								}
						}
						else
							{
							JOptionPane.showMessageDialog(null,"0 Station Avaiable Actually");
							}
						}

					@Override
					public void mouseEntered(MouseEvent arg0)
						{
						}

					@Override
					public void mouseExited(MouseEvent arg0)
						{
						}

					@Override
					public void mousePressed(MouseEvent arg0)
						{
						}

					@Override
					public void mouseReleased(MouseEvent arg0)
						{
						}
				});
			multipleCom.addMouseListener(new MouseListener()
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
						if(portList.size()!=0){
						new JFramePCLocalManuel(meteoPortDetectionService);
						}
						else{
						JOptionPane.showMessageDialog(null,"0 Station Avaiable Actually");
						}

						}
				});
			// Layout : Specification
			{
		    GridLayout gl = new GridLayout();
			gl.setColumns(1);
			gl.setRows(2);
			gl.setHgap(15);
			gl.setVgap(15);
			this.setLayout(gl);
			add(multipleCom);
			add(defaultCom);

			// flowlayout.setHgap(20);
			// flowlayout.setVgap(20);
			}

		// JComponent : add

		}

	private void control()
		{
		// rien
		}

	private void appearance()
		{
		// rien
		this.setSize(new Dimension(200, 100));
		this.setPreferredSize(new Dimension(200, 100));
		this.setBorder(BorderFactory.createTitledBorder(""));
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	JButton defaultCom;
	JButton multipleCom;

	// Tools
	private MeteoPortDetectionService meteoPortDetectionService;

	}
