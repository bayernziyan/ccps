package org.ccps.op.core;

import org.ccps.op.core.Platform;
import org.ccps.op.core.PlatformImpl;

public final class PlatformFactory {

	
	
	
	public static PlatformFactory instance() {
		return PlatformFactoryHolder.INSTANCE;
	}

	private PlatformFactory() {
	}

	private static class PlatformFactoryHolder {
		static final PlatformFactory INSTANCE = new PlatformFactory();
	}

}
