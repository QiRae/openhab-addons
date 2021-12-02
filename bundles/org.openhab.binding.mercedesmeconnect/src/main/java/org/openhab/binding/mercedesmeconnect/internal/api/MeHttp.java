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

package org.openhab.binding.mercedesmeconnect.internal.api;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.HttpResponse;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.api.Result;
import org.eclipse.jetty.client.util.BufferingResponseListener;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpMethod;

/**
 * The {@link MeHttp} handles HTTP-Connections
 *
 * @author Christian Wild - Initial contribution
 */
public class MeHttp {
    public static final String HEADER_ACCEPT = "application/json;charset=utf-8";
    public static final String HEADER_AUTH = "Bearer";
    public static final String CONTENT_CHARSET = "UTF-8";
    public static final String CONTENT_TYPE_JSON = "application/json";

    private final HttpClient httpClient;
    private String accessToken = "";
    
    /**
     * INIT CLASS
     * @param httpClient userd httpClient
     */
    public MeHttp(HttpClient httpClient){
        this.httpClient = httpClient;
    }

    @Nullable
    protected ContentResponse sendHttpRequest(String url, String payload) {
        Request httpRequest = httpClient.newRequest(url).method(HttpMethod.GET.toString());

        /* set header */
        httpRequest.header("accept", HEADER_ACCEPT);
        httpRequest.header("authorization",HEADER_AUTH + " " + this.accessToken);

        /* add request body */
        httpRequest.content(new StringContentProvider(payload, CONTENT_CHARSET), CONTENT_TYPE_JSON);
        try {
            ContentResponse httpResponse = httpRequest.send();
            return httpResponse;
        } catch (InterruptedException e) {
            logger.debug("({}) sending request interrupted: {}", uid, e.toString());
            handleError(new TapoErrorHandler(e));
        } catch (TimeoutException e) {
            logger.debug("({}) sending request timeout: {}", uid, e.toString());
            handleError(new TapoErrorHandler(ERR_CONNECT_TIMEOUT, e.toString()));
        } catch (Exception e) {
            logger.debug("({}) sending request failed: {}", uid, e.toString());
            handleError(new TapoErrorHandler(e));
        }
        return null;
    }
    }


}
