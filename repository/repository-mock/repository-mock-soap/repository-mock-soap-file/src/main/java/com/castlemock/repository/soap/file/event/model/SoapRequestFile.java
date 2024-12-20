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

package com.castlemock.repository.soap.file.event.model;

import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.mock.soap.domain.SoapVersion;
import com.castlemock.repository.core.file.http.model.HttpHeaderFile;

import jakarta.xml.bind.annotation.*;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "soapRequest")
@XmlAccessorType(XmlAccessType.NONE)
public class SoapRequestFile {

    @XmlElement
    private String body;
    @XmlElement
    private String envelope;
    @XmlElement
    private String contentType;
    @XmlElement
    private String uri;
    @XmlElement
    private HttpMethod httpMethod;
    @XmlElement
    private String operationName;
    @XmlElement
    private SoapOperationIdentifierFile operationIdentifier;
    @XmlElement
    private SoapVersion soapVersion;
    @XmlElementWrapper(name = "httpHeaders")
    @XmlElement(name = "httpHeader")
    private List<HttpHeaderFile> httpHeaders;

    private SoapRequestFile() {

    }

    private SoapRequestFile(final Builder builder) {
        this.body = Objects.requireNonNull(builder.body, "body");
        this.envelope = builder.envelope;
        this.contentType = builder.contentType;
        this.uri = Objects.requireNonNull(builder.uri, "uri");
        this.httpMethod = Objects.requireNonNull(builder.httpMethod, "httpMethod");
        this.operationName = builder.operationName;
        this.soapVersion = Objects.requireNonNull(builder.soapVersion, "soapVersion");
        this.httpHeaders = Objects.requireNonNull(builder.httpHeaders, "httpHeaders");
        this.operationIdentifier = Objects.requireNonNull(builder.operationIdentifier, "operationIdentifier");
    }

    public String getBody() {
        return body;
    }

    public String getEnvelope() {
        return envelope;
    }

    public String getContentType() {
        return contentType;
    }

    public String getUri() {
        return uri;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getOperationName() {
        return operationName;
    }

    public SoapOperationIdentifierFile getOperationIdentifier() {
        return operationIdentifier;
    }

    public SoapVersion getSoapVersion() {
        return soapVersion;
    }

    public List<HttpHeaderFile> getHttpHeaders() {
        return httpHeaders;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String body;
        private String envelope;
        private String contentType;
        private String uri;
        private HttpMethod httpMethod;
        private String operationName;
        private SoapOperationIdentifierFile operationIdentifier;
        private SoapVersion soapVersion;
        private List<HttpHeaderFile> httpHeaders;

        private Builder() {
        }

        public Builder body(final String body) {
            this.body = body;
            return this;
        }

        public Builder envelope(final String envelope) {
            this.envelope = envelope;
            return this;
        }

        public Builder contentType(final String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder uri(final String uri) {
            this.uri = uri;
            return this;
        }

        public Builder httpMethod(final HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public Builder operationName(final String operationName) {
            this.operationName = operationName;
            return this;
        }

        public Builder operationIdentifier(final SoapOperationIdentifierFile operationIdentifier) {
            this.operationIdentifier = operationIdentifier;
            return this;
        }

        public Builder soapVersion(final SoapVersion soapVersion) {
            this.soapVersion = soapVersion;
            return this;
        }

        public Builder httpHeaders(final List<HttpHeaderFile> httpHeaders) {
            this.httpHeaders = httpHeaders;
            return this;
        }

        public SoapRequestFile build() {
            return new SoapRequestFile(this);
        }
    }
}