/*
 * Copyright 2024 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.castlemock.web.mock.soap.stategy;

import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapRequest;
import com.castlemock.model.mock.soap.domain.SoapResponse;
import com.castlemock.web.mock.soap.factory.SoapMockStrategyResultFactory;
import com.castlemock.web.mock.soap.utility.SoapClient;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Objects;
import java.util.Optional;

public final class MockSoapStrategy implements SoapStrategy {

    private final SoapClient soapClient;
    private final SoapMockStrategyResultFactory soapMockStrategyResultFactory;

    public MockSoapStrategy(final SoapClient soapClient,
                            final SoapMockStrategyResultFactory soapMockStrategyResultFactory) {
        this.soapClient = Objects.requireNonNull(soapClient, "soapClient");
        this.soapMockStrategyResultFactory = Objects.requireNonNull(soapMockStrategyResultFactory, "soapResponseStrategyManager");
    }

    @Override
    public SoapStrategyResult process(final SoapRequest request, final String projectId,
                                final String portId, final SoapOperation operation,
                                final HttpServletRequest httpServletRequest) {
        final SoapStrategyResult result = this.soapMockStrategyResultFactory.getResponse(request, projectId,
                portId, operation, httpServletRequest);

        if(result.getResponse().isEmpty()) {
            if (operation.getAutomaticForward().orElse(false) && operation.getForwardedEndpoint().isPresent()) {
                final Optional<SoapResponse> response = this.soapClient.getResponse(request, operation);
                return SoapStrategyResult.builder()
                        .response(response.orElse(null))
                        .build();
            }
        }

        return result;
    }

}
