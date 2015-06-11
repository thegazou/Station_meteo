
package ch.hearc.meteo.imp.use.remote.pccentral;
import ch.hearc.meteo.imp.afficheur.real.pccentral.JFramePCCentrale;

public class UsePCCentral
	{
	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public static void main(String[] args)
		{
		main();
		}

	public static void main()
		{
		new JFramePCCentrale();
		new PCCentral().run();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	}

