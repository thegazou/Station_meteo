
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

import ch.hearc.meteo.imp.afficheur.real.afficheur.AfficheurServiceMOOReal;
import ch.hearc.meteo.spec.afficheur.AffichageOptions;
import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper_I;

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
					String str = name;
					String[] splited = str.split(" ");
					System.out.println(splited[0]);
					afficheurServiceMOOReal=new AfficheurServiceMOOReal(affichageOptions, meteoService);




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

	public void setService(MeteoServiceWrapper_I meteoService_I, AffichageOptions affichageOptions)
		{
		this.affichageOptions=affichageOptions;
		meteoService= meteoService_I;
		}


	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/



	private MeteoServiceWrapper_I meteoService;
	private int wait;
	private int ID;
	private String name;
	private AfficheurServiceMOOReal afficheurServiceMOOReal;
	private AffichageOptions affichageOptions;







	}
