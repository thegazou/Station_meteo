
package ch.hearc.meteo.imp.afficheur.real;

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

import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.MeteoService_I;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;

public class JPanelMini extends JPanel  implements AfficheurService_I
	{

	public JPanelMini(int ID, String Name)
		{
		wait=0;
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

					meteo=new JFrameMeteo(meteoService);
					wait++;


					}
			});
		TitledBorder titleB = BorderFactory.createTitledBorder("Station:" + ID);
		titleB.setTitleFont(new Font("Calibri", Font.BOLD, 13));
		setBorder(titleB);
		setForeground(Color.blue);
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
		setLayout(new GridLayout(0, 1, 0, 0));

		/*Information Label*/
		JLabel lblLocation = new JLabel("ID : " + Name);
		lblLocation.setFont(new Font("Calibri", Font.PLAIN, 10));
		add(lblLocation);

		JLabel lblTemperature = new JLabel("Port : ");
		lblTemperature.setFont(new Font("Calibri", Font.PLAIN, 10));
		add(lblTemperature);

		JLabel lblPression = new JLabel("State : Connected");
		lblPression.setFont(new Font("Calibri", Font.PLAIN, 10));
		add(lblPression);
		}

	public Boolean isSelected()
		{
		return isSelected;
		}

	private Boolean isSelected;

	@Override
	public void printPression(MeteoEvent event)
		{
		// TODO Auto-generated method stub
		if(wait!=0)
			{
			meteo.printPression(event);
			}

		}

	@Override
	public void printAltitude(MeteoEvent event)
		{
		// TODO Auto-generated method stub
		if(wait!=0)
			{
			meteo.printAltitude(event);
			}
		//
		}

	@Override
	public void printTemperature(MeteoEvent event)
		{
		// TODO Auto-generated method stub
		if(wait!=0)
			{
			meteo.printTemperature(event);
			}

		}

	@Override
	public void updateMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions)
		{
		// TODO Auto-generated method stub

		}
	private MeteoService_I meteoService;
	private JFrameMeteo meteo;
	private int wait;
	}
