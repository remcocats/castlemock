/*
 * Copyright 2015 Karl Dahlgren
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

package com.castlemock.web.mock.soap.web.view.controller.mockresponse;

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.mock.soap.model.project.domain.SoapMockResponse;
import com.castlemock.core.mock.soap.model.project.domain.SoapMockResponseTestBuilder;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperationTestBuilder;
import com.castlemock.core.mock.soap.model.project.domain.SoapPort;
import com.castlemock.core.mock.soap.model.project.domain.SoapPortTestBuilder;
import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.core.mock.soap.model.project.domain.SoapProjectTestBuilder;
import com.castlemock.core.mock.soap.service.project.input.UpdateSoapMockResponseInput;
import com.castlemock.core.mock.soap.service.project.output.UpdateSoapMockResponseOutput;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.mock.soap.config.TestApplication;
import com.castlemock.web.mock.soap.web.view.controller.AbstractSoapControllerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
@WebAppConfiguration
public class UpdateSoapMockResponseControllerTest extends AbstractSoapControllerTest {

    private static final String UPDATE = "update";


    @InjectMocks
    private UpdateSoapMockResponseController updateSoapMockResponseController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Override
    protected AbstractController getController() {
        return updateSoapMockResponseController;
    }

    @Test
    public void testUpdateMockResponse() throws Exception {
        final SoapProject project = SoapProjectTestBuilder.builder().build();
        final SoapPort application = SoapPortTestBuilder.builder().build();
        final SoapOperation soapOperation = SoapOperationTestBuilder.builder().build();
        final SoapMockResponse soapMockResponse = SoapMockResponseTestBuilder.builder().build();

        when(serviceProcessor.process(any(UpdateSoapMockResponseInput.class))).thenReturn(UpdateSoapMockResponseOutput.builder()
                .mockResponse(soapMockResponse)
                .build());
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.post(SERVICE_URL + PROJECT +
                SLASH + project.getId() + SLASH + PORT + SLASH + application.getId() + SLASH + OPERATION +
                SLASH + soapOperation.getId() + SLASH + RESPONSE + SLASH + soapMockResponse.getId() + SLASH + UPDATE)
                .flashAttr("soapMockResponse", soapMockResponse);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1));
    }

}
