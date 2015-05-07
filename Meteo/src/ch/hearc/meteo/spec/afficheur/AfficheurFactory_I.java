
package ch.hearc.meteo.spec.afficheur;

import ch.hearc.meteo.spec.reseau.rmiwrapper.MeteoServiceWrapper_I;

public interface AfficheurFactory_I
	{

	public AfficheurService_I createOnCentralPC(AffichageOptions affichageOptions, MeteoServiceWrapper_I meteoServiceRemote);

	/**
	 * Sur le pclocal on a en plus la gestion des noms des ports com
	 */
	public AfficheurService_I createOnLocalPC(AffichageOptions affichageOptions, MeteoServiceWrapper_I meteoServiceRemote);

	}
