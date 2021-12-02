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
package org.openhab.binding.mercedesmeconnect.internal.constants;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.core.thing.ThingTypeUID;

/**
 * The {@link MercedesMeConnectBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Christian Wild - Initial contribution
 */
@NonNullByDefault
public class MercedesMeConnectBindingConstants {

    private static final String BINDING_ID = "mercedesmeconnect";

    // List of all Thing Type UIDs
    public static final ThingTypeUID THING_TYPE_SAMPLE = new ThingTypeUID(BINDING_ID, "vehicle");

    // List of all Channel ids
    public static final String CHANNEL_1 = "channel1";

    // Setting Constants
    public static final String API_CLIENT_ID = "46b3dc82-dfa7-41a4-97ed-dd67e93fb122";
    public static final String API_CLIENT_SECRET = "PolybIBdCsxalufdhSbQlncVyEjcevDqHFautRqwtiOahsjVuobiwczlBrYfjTnc";
    public static final String API_URL = "https://api.mercedes-benz.com/vehicledata/v2/vehicles/<%VID%>/resources";
}
