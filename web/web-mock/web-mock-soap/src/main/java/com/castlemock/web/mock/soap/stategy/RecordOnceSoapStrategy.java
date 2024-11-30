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

import com.castlemock.model.core.Input;
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapOperationStatus;
import com.castlemock.model.mock.soap.domain.SoapRequest;
import com.castlemock.model.mock.soap.domain.SoapResponse;
import com.castlemock.service.mock.soap.project.input.UpdateSoapOperationsStatusInput;
import com.castlemock.web.mock.soap.converter.SoapResponseConverter;
import com.castlemock.web.mock.soap.factory.SoapMockStrategyResultFactory;
import com.castlemock.web.mock.soap.utility.SoapClient;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class RecordOnceSoapStrategy implements SoapStrategy {

    private static final int ERROR_CODE = 500;
    private final SoapClient soapHttpClient;
    private final SoapMockStrategyResultFactory soapMockStrategyResultFactory;

    public RecordOnceSoapStrategy(final SoapClient soapHttpClient,
                                  final SoapMockStrategyResultFactory soapMockStrategyResultFactory) {
        this.soapHttpClient = Objects.requireNonNull(soapHttpClient, "soapHttpClient");
        this.soapMockStrategyResultFactory = Objects.requireNonNull(soapMockStrategyResultFactory, "soapResponseStrategyManager");
    }

    @Override
    public SoapStrategyResult process(final SoapRequest request, final String projectId, final String portId,
                                         final SoapOperation operation, final HttpServletRequest httpServletRequest) {
        final Optional<SoapResponse> optionalResponse = soapHttpClient.getResponse(request, operation);
        final List<Input> postServiceRequests = new ArrayList<>();

        if (optionalResponse.isPresent()) {
            final SoapResponse response = optionalResponse.get();
            if (response.getHttpStatusCode() >= ERROR_CODE && operation.getMockOnFailure().orElse(false)) {
                return this.soapMockStrategyResultFactory.getResponse(request, projectId,
                        portId, operation, httpServletRequest);
            } else {
                postServiceRequests.add(SoapResponseConverter.toCreateSoapMockResponseInput(response,
                        projectId, portId, operation));
                postServiceRequests.add(UpdateSoapOperationsStatusInput.builder()
                        .projectId(projectId)
                        .portId(portId)
                        .operationId(operation.getId())
                        .operationStatus(SoapOperationStatus.MOCKED)
                        .build());
            }
        }

        return SoapStrategyResult.builder()
                .response(optionalResponse.orElse(null))
                .postServiceRequests(postServiceRequests)
                .build();
    }
}
