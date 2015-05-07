
package ch.hearc.meteo.spec.reseau;

import java.rmi.RemoteException;

public interface RemoteAfficheurCreatorFactory_I
	{

	public RemoteAfficheurCreator_I create() throws RemoteException;

	}
