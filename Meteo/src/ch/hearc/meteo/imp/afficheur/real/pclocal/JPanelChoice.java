
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

import ch.hearc.meteo.imp.com.real.MeteoPortDetectionService;

public class JPanelChoice extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelChoice(MeteoPortDetectionService meteoPortDetectionService)
		{
		this.meteoPortDetectionService = meteoPortDetectionService;
		this.portName="undefined";
		geometry();
		control();
		appearance();
		}


	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/



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
							portName=portList.get(0).toString();
							System.out.println("portName Set!");

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
						jFramePCLocalManuel = new JFramePCLocalManuel(meteoPortDetectionService);
						while(jFramePCLocalManuel.getPortName().equals("undefined"))
							{

							}
						portName=jFramePCLocalManuel.getPortName();
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



	public String getPortName()
	{
	return this.portName;
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
	private JButton defaultCom;
	private JButton multipleCom;
	private String portName;

	// Tools
	private MeteoPortDetectionService meteoPortDetectionService;
	private JFramePCLocalManuel jFramePCLocalManuel;

	}
