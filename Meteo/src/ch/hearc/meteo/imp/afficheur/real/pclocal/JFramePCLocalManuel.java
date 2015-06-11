
package ch.hearc.meteo.imp.afficheur.real.pclocal;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import ch.hearc.meteo.imp.com.real.MeteoPortDetectionService;

public class JFramePCLocalManuel extends JFrame
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JFramePCLocalManuel(MeteoPortDetectionService meteoPortDetectionService)
		{
		this.meteoPortDetectionService = meteoPortDetectionService;
		this.portName="undefined";
		geometry();
		control();
		appearance();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		refresh=new JButton("Refresh");
		refresh.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
					{
					remove();
					populate();
					setLayout();
					setControl();
					}


			});
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
	private void remove()
		{
		comList.removeAll(comList);
		statList.removeAll(statList);

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
		portName=button.getText();

		}

	public String getPortName()
	{
		return portName;
	}
	private void setLayout()
		{
		Box left = Box.createVerticalBox();
		Box right = Box.createVerticalBox();
		for(int i = 0; i < tailleListe; i++)
			{
			comList.get(i).setPreferredSize(new Dimension(140, 30));
			left.add(comList.get(i));
			left.add(Box.createVerticalGlue());
			statList.get(i).setPreferredSize(new Dimension(140, 30));
			right.add(statList.get(i));
			right.add(Box.createVerticalGlue());
			}
		left.add(refresh);
		Box line = Box.createHorizontalBox();
		line.add(left);
		line.add(right);

		setLayout(new BorderLayout());
		add(line, BorderLayout.CENTER);

		}

	private void populate()
		{
		List<String> portList = meteoPortDetectionService.findListPortMeteo();
		System.out.println("portList="+portList);
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
	private JButton refresh;
	private String portName;

	}
