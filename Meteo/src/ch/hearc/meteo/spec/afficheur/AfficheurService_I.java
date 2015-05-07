
package ch.hearc.meteo.spec.afficheur;

import ch.hearc.meteo.spec.com.meteo.MeteoServiceOptions;
import ch.hearc.meteo.spec.com.meteo.listener.event.MeteoEvent;

public interface AfficheurService_I
	{

	public void printPression(MeteoEvent event);

	public void printAltitude(MeteoEvent event);

	public void printTemperature(MeteoEvent event);

	public void updateMeteoServiceOptions(MeteoServiceOptions meteoServiceOptions);

	}
