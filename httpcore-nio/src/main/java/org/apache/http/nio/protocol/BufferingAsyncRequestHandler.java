/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.apache.http.nio.protocol;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.annotation.Immutable;
import org.apache.http.concurrent.Cancellable;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;

/**
 * @since 4.2
 */
@Immutable
public class BufferingAsyncRequestHandler implements HttpAsyncRequestHandler<HttpRequest> {

    private final HttpRequestHandler handler;

    public BufferingAsyncRequestHandler(final HttpRequestHandler handler) {
        super();
        if (handler == null) {
            throw new IllegalArgumentException("Request handler may not be null");
        }
        this.handler = handler;
    }

    public HttpAsyncRequestConsumer<HttpRequest> processRequest(final HttpRequest request,
            final HttpContext context) {
        return new BasicAsyncRequestConsumer();
    }

    public Cancellable handle(
            final HttpRequest request,
            final HttpAsyncServiceExchange httpexchange,
            final HttpContext context) throws HttpException, IOException {
        this.handler.handle(request, httpexchange.getResponse(), context);
        httpexchange.submitResponse();
        return null;
    }

}