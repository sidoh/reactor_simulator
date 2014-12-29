package org.sidoh.reactor_simulator.simulator;

import erogenousbeef.bigreactors.common.data.RadiationData;
import erogenousbeef.bigreactors.common.data.RadiationPacket;

public interface IRadiationModeratorSimulator {
	public void moderateRadiation(RadiationData returnData, RadiationPacket radiation, IFakeReactorWorld world, MultiblockReactorSimulator reactor);
}
