
package ch.hearc.meteo.imp.afficheur.real;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ch.hearc.meteo.spec.afficheur.AfficheurService_I;
import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;

public class JPanelInformationStation extends JPanel implements AfficheurService_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelInformationStation()
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
		drawPression(event.getValue());

		}

	@Override
	public void printAltitude(MeteoEvent event)
		{
		drawAltitude(event.getValue());

		}

	@Override
	public void printTemperature(MeteoEvent event)
		{
		drawTemperature(event.getValue());
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

	private void drawPression(float value)
		{
		if(maxPress==0.0)
			{
			maxPress=value;
			}
		if(minPress==0.0)
			{
			minPress=value;
			}
		//Attention min,max Press non déclaré
		if (value > maxPress)
			{
			maxPress = value;
			}
		if (value < minPress)
			{
			minPress = value;
			}
		stationActuPressVal.setText(String.valueOf(value));
		stationActuMaxPressVal.setText(String.valueOf(maxPress));
		stationActuMinPressVal.setText(String.valueOf(minPress));
		}

	private void drawAltitude(float value)
		{
		stationActuAltitudeVal.setText(String.valueOf(value));

		}

	private void drawTemperature(float value)
		{
		if(maxTemp==0.0)
			{
			maxTemp=value;
			}
		if(minTemp==0.0)
			{
			minTemp=value;
			}
		//Attention min,max Temp non déclaré
		if (value > maxTemp)
			{
			maxTemp = value;
			}
		if (value < minTemp)
			{
			minTemp = value;
			}
		stationActuTempVal.setText(String.valueOf(value));
		stationActuMaxTempVal.setText(String.valueOf(maxTemp));
		stationActuMinTempVal.setText(String.valueOf(minTemp));

		}

	private void geometry()
		{
		// JComponent : Instanciation
		stationName = new JLabel("Name of the Station:");
		stationActuTemp = new JLabel("Actual Temperature:");
		stationActuPress = new JLabel("Actual Pression:");
		stationActuMinPress = new JLabel("Min pression:");
		stationActuMaxPress = new JLabel("Max pression:");
		stationActuMinTemp = new JLabel("Min Temperature:");
		stationActuMaxTemp = new JLabel("Max Temperature:");
		stationActuAltitude = new JLabel("Actual Altitude");

		stationNameVal = new JLabel("Actual Station");
		stationActuTempVal = new JLabel("0");
		stationActuPressVal = new JLabel("0");
		stationActuMinPressVal = new JLabel("0");
		stationActuMaxPressVal = new JLabel("0");
		stationActuMinTempVal = new JLabel("0");
		stationActuMaxTempVal = new JLabel("0");
		stationActuAltitudeVal = new JLabel("0");
		setInfoLayout();

		stationName .setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
		stationActuTemp .setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
		stationActuPress.setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
		stationActuMinPress .setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
		stationActuMaxPress .setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
		stationActuMinTemp .setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
		stationActuMaxTemp .setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
		stationActuAltitude  .setFont(new Font("Comic Sans MS", Font.PLAIN, 10));

		stationNameVal .setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
		stationActuTempVal .setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
		stationActuPressVal .setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
		stationActuMinPressVal .setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
		stationActuMaxPressVal .setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
		stationActuMinTempVal.setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
		stationActuMaxTempVal .setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
		stationActuAltitudeVal .setFont(new Font("Comic Sans MS", Font.PLAIN, 10));

		}

	private void setInfoLayout()
		{
		// Layout : Specification
		GridLayout gl = new GridLayout();
		gl.setColumns(2);
		gl.setRows(8);
		gl.setHgap(15);
		gl.setVgap(15);
		this.setLayout(gl);

		// JComponent : add
		add(stationName);
		add(stationNameVal);
		add(stationActuTemp);
		add(stationActuTempVal);
		add(stationActuAltitude);
		add(stationActuAltitudeVal);
		add(stationActuPress);
		add(stationActuPressVal);
		add(stationActuMinPress);
		add(stationActuMinPressVal);
		add(stationActuMaxPress);
		add(stationActuMaxPressVal);
		add(stationActuMaxTemp);
		add(stationActuMaxTempVal);
		add(stationActuMinTemp);
		add(stationActuMinTempVal);


		}

	private void control()
		{
		// rien
		}

	private void appearance()
		{
		Dimension panelD = new Dimension(250,250);
        this.setPreferredSize(panelD);
        this.setMaximumSize(panelD);
		this.setBorder(BorderFactory.createTitledBorder("Informations of the station"));
		System.out.println(this.getFont());
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Tools
	JLabel stationName;
	JLabel stationActuTemp;
	JLabel stationActuMaxTemp;
	JLabel stationActuMinTemp;
	JLabel stationActuPress;
	JLabel stationActuMaxPress;
	JLabel stationActuMinPress;
	JLabel stationActuAltitude;

	JLabel stationNameVal;
	JLabel stationActuTempVal;
	JLabel stationActuMaxTempVal;
	JLabel stationActuMinTempVal;
	JLabel stationActuPressVal;
	JLabel stationActuMaxPressVal;
	JLabel stationActuMinPressVal;
	JLabel stationActuAltitudeVal;

	private float minTemp;
	private float maxTemp;
	private float minPress;
	private float maxPress;

	}
