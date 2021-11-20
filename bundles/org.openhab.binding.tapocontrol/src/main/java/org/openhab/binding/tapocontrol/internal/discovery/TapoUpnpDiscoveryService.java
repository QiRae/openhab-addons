/**
 * Copyright (c) 2010-2021 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.tapocontrol.internal.discovery;

import static org.openhab.binding.tapocontrol.internal.constants.TapoBindingSettings.*;
import static org.openhab.binding.tapocontrol.internal.constants.TapoThingConstants.*;
import static org.openhab.binding.tapocontrol.internal.helpers.TapoUtils.*;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.jupnp.model.message.header.RootDeviceHeader;
import org.openhab.binding.tapocontrol.internal.device.TapoBridgeHandler;
import org.openhab.core.config.discovery.AbstractDiscoveryService;
import org.openhab.core.thing.binding.ThingHandler;
import org.openhab.core.thing.binding.ThingHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handler class for TAPO Smart Home thing discovery
 *
 * @author Christian Wild - Initial contribution
 */
@NonNullByDefault
public class TapoUpnpDiscoveryService extends AbstractDiscoveryService implements ThingHandlerService {
    private final Logger logger = LoggerFactory.getLogger(TapoUpnpDiscoveryService.class);
    protected @Nullable TapoBridgeHandler bridge;
    // @Component(service = DiscoveryService.class, configurationPid = "discovery.tapocontrol")

    /***********************************
     *
     * INITIALIZATION
     *
     ************************************/

    /**
     * INIT CLASS
     * 
     * @param bridgeHandler
     */
    public TapoUpnpDiscoveryService() {
        super(SUPPORTED_THING_TYPES_UIDS, TAPO_DISCOVERY_TIMEOUT_S, false);
    }

    @Override
    public void activate() {
        logger.debug("Starting UPnP discovery...");
        startScan();
    }

    @Override
    public void deactivate() {
        logger.debug("Stopping UPnP discovery...");
        stopScan();
    }

    @Override
    public void setThingHandler(@Nullable ThingHandler handler) {
        if (handler instanceof TapoBridgeHandler) {
            this.bridge = (TapoBridgeHandler) handler;
        }
    }

    @Override
    public @Nullable ThingHandler getThingHandler() {
        return this.bridge;
    }

    /***********************************
     *
     * SCAN HANDLING
     *
     ************************************/

    /**
     * Start scan manually
     */
    @Override
    public void startScan() {
        removeOlderResults(getTimestampOfLastScan());
        logger.debug("Starting UPnP RootDevice search...");
        if (bridge.getUpnpService() != null) {
            bridge.getUpnpService().getControlPoint().search(new RootDeviceHeader());
        } else {
            logger.debug("upnpService not set");
        }
    }
}
