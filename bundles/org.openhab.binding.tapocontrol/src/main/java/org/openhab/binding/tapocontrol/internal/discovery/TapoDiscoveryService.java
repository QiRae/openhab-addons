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
import org.openhab.binding.tapocontrol.internal.device.TapoBridgeHandler;
import org.openhab.binding.tapocontrol.internal.structures.TapoBridgeConfiguration;
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
public class TapoDiscoveryService extends AbstractDiscoveryService implements ThingHandlerService {
    private final Logger logger = LoggerFactory.getLogger(TapoDiscoveryService.class);
    protected @Nullable TapoBridgeHandler bridge;
    private @Nullable TapoCloudDiscoveryService cloudDiscoveryService;
    private @Nullable TapoUpnpDiscoveryService upnpDiscoveryService;

    /***********************************
     *
     * INITIALIZATION
     *
     ************************************/

    /**
     * INIT CLASS
     * 
     */
    public TapoDiscoveryService() {
        super(SUPPORTED_THING_TYPES_UIDS, TAPO_DISCOVERY_TIMEOUT_S, false);
    }

    /**
     * deactivate
     */
    @Override
    public void activate() {
        TapoBridgeConfiguration config = bridge.getBridgeConfig();
        if (config.cloudDiscoveryEnabled) {
            this.cloudDiscoveryService = new TapoCloudDiscoveryService();
            startBackgroundDiscovery();
        } else if (config.udpDiscoveryEnabled) {
            this.upnpDiscoveryService = new TapoUpnpDiscoveryService();
            this.upnpDiscoveryService.setThingHandler(bridge);
            startBackgroundDiscovery();
        }
    }

    /**
     * deactivate
     */
    @Override
    public void deactivate() {
        super.deactivate();
    }

    @Override
    public void setThingHandler(@Nullable ThingHandler handler) {
        if (handler instanceof TapoBridgeHandler) {
            this.bridge = (TapoBridgeHandler) handler;
            bridge.setDiscoveryService(this);
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
        TapoBridgeConfiguration config = bridge.getBridgeConfig();
        if (config.cloudDiscoveryEnabled) {
            this.cloudDiscoveryService.startScan();
        } else if (config.udpDiscoveryEnabled) {
            this.upnpDiscoveryService.startScan();
        }
    }
}
