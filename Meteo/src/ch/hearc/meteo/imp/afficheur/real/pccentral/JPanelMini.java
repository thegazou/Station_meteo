
package ch.hearc.meteo.imp.afficheur.real.pccentral;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import ch.hearc.meteo.imp.afficheur.real.afficheur.AfficheurFactory;
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

public class JPanelMini extends JPanel
	{
	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/


	public JPanelMini(int ID, String Name)
		{
		this.ID=ID;
		this.name=Name;
		geometry();

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
		AfficheurService_I afficheurService1 = (new AfficheurFactory()).createOnCentralPC(affichageOption, meteoServiceWrapper);
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
		addMouseListener(new MouseListener()
			{

				@Override
				public void mouseReleased(MouseEvent e)
					{
					// TODO Auto-generated method stub

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
					String str = name;
					String[] splited = str.split(" ");
					System.out.println(splited[0]);
					meteoService = (new MeteoServiceFactory()).create(splited[0]);
					try
						{
						use(meteoService);
						}
					catch (MeteoServiceException e1)
						{
						e1.printStackTrace();
						}


					}
			});
		TitledBorder titleB = BorderFactory.createTitledBorder("Station:" + ID);
		titleB.setTitleFont(new Font("Calibri", Font.BOLD, 13));
		setBorder(titleB);
		setBackground(Color.WHITE);
		addMouseListener(new MouseAdapter()
			{

				Boolean swap = false;

				@Override
				public void mousePressed(MouseEvent e)
					{
					if (swap == false)
						{
						Color LightBlue = new Color(216, 216, 216);
						JPanelMini.this.setBackground(LightBlue);
						swap = true;
						}
					else
						{
						swap = false;
						JPanelMini.this.setBackground(UIManager.getColor("Button.background"));

						}
					JPanelMini.this.isSelected = swap;

					}
			});
		setLayout();


		}

	private void setLayout()
		{
		setLayout(new GridLayout(0, 1, 0, 0));

		/*Information Label*/
		JLabel labelID = new JLabel("ID : " + ID);
		labelID.setFont(new Font("Calibri", Font.PLAIN, 10));
		add(labelID);

		JLabel labelPort = new JLabel("Port :"+ name);
		labelPort.setFont(new Font("Calibri", Font.PLAIN, 10));
		add(labelPort);

		JLabel labelState = new JLabel("State : Connected");
		labelState.setFont(new Font("Calibri", Font.PLAIN, 10));
		add(labelState);

		}

	public Boolean isSelected()
		{
		return isSelected;
		}

	private Boolean isSelected;

	public void setService(MeteoService_I meteoService_I)
		{
		//System.out.println("JPanelMini: "+meteoService_I.getPort());
		meteoService= meteoService_I;
		}


	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/



	private MeteoService_I meteoService;
	private int wait;
	private int ID;
	private String name;







	}
