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

package com.castlemock.model.mock.soap.domain;

import com.castlemock.model.core.http.HttpContentEncoding;
import com.castlemock.model.core.http.HttpHeader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = SoapMockResponse.Builder.class)
public class SoapMockResponse {

    @XmlElement
    private final String id;

    @XmlElement
    private final String name;

    @XmlElement
    private final String body;

    @XmlElement
    private final String operationId;

    @XmlElement
    private final SoapMockResponseStatus status;

    @XmlElement
    private final Integer httpStatusCode;

    @XmlElement
    private final Boolean usingExpressions;

    @XmlElementWrapper(name = "httpHeaders")
    @XmlElement(name = "httpHeader")
    private final List<HttpHeader> httpHeaders;

    @XmlElementWrapper(name = "contentEncodings")
    @XmlElement(name = "contentEncoding")
    private final List<HttpContentEncoding> contentEncodings;

    @XmlElementWrapper(name = "xpathExpressions")
    @XmlElement(name = "xpathExpression")
    private final List<SoapXPathExpression> xpathExpressions;

    private SoapMockResponse(final Builder builder){
        this.id = Objects.requireNonNull(builder.id, "id");
        this.name = Objects.requireNonNull(builder.name, "name");
        this.body = Objects.requireNonNull(builder.body, "body");
        this.operationId = Objects.requireNonNull(builder.operationId, "operationId");
        this.status = Objects.requireNonNull(builder.status, "status");
        this.httpStatusCode = Objects.requireNonNull(builder.httpStatusCode, "httpStatusCode");
        this.usingExpressions = builder.usingExpressions;
        this.httpHeaders = Optional.ofNullable(builder.httpHeaders).orElseGet(List::of);
        this.contentEncodings = Optional.ofNullable(builder.contentEncodings).orElseGet(List::of);
        this.xpathExpressions = Optional.ofNullable(builder.xpathExpressions).orElseGet(List::of);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBody() {
        return body;
    }

    public String getOperationId() {
        return operationId;
    }

    public SoapMockResponseStatus getStatus() {
        return status;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }


    public Optional<Boolean> getUsingExpressions() {
        return Optional.ofNullable(usingExpressions);
    }


    public List<HttpHeader> getHttpHeaders() {
        return Optional.ofNullable(httpHeaders)
                .map(List::copyOf)
                .orElseGet(List::of);
    }


    public List<HttpContentEncoding> getContentEncodings() {
        return Optional.ofNullable(contentEncodings)
                .map(List::copyOf)
                .orElseGet(List::of);
    }


    public List<SoapXPathExpression> getXpathExpressions() {
        return Optional.ofNullable(xpathExpressions)
                .map(List::copyOf)
                .orElseGet(List::of);
    }

    public Builder toBuilder() {
        return builder()
                .id(id)
                .name(name)
                .body(body)
                .operationId(operationId)
                .status(status)
                .httpStatusCode(httpStatusCode)
                .usingExpressions(usingExpressions)
                .httpHeaders(Optional.ofNullable(httpHeaders)
                        .map(headers -> headers
                                .stream()
                                .map(HttpHeader::toBuilder)
                                .map(HttpHeader.Builder::build)
                                .collect(Collectors.toList()))
                        .orElse(null))
                .contentEncodings(Optional.ofNullable(contentEncodings)
                        .map(ArrayList::new)
                        .orElse(null))
                .xpathExpressions(Optional.ofNullable(xpathExpressions)
                        .map(expressions -> expressions
                                .stream()
                                .map(SoapXPathExpression::toBuilder)
                                .map(SoapXPathExpression.Builder::build)
                                .collect(Collectors.toList()))
                        .orElse(null));
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SoapMockResponse that = (SoapMockResponse) o;
        return usingExpressions == that.usingExpressions && Objects.equals(id, that.id)
                && Objects.equals(name, that.name) && Objects.equals(body, that.body)
                && Objects.equals(operationId, that.operationId) && status == that.status
                && Objects.equals(httpStatusCode, that.httpStatusCode) && Objects.equals(httpHeaders, that.httpHeaders)
                && Objects.equals(contentEncodings, that.contentEncodings) && Objects.equals(xpathExpressions, that.xpathExpressions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, body, operationId, status, httpStatusCode,
                usingExpressions, httpHeaders, contentEncodings, xpathExpressions);
    }

    @Override
    public String toString() {
        return "SoapMockResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", body='" + body + '\'' +
                ", operationId='" + operationId + '\'' +
                ", status=" + status +
                ", httpStatusCode=" + httpStatusCode +
                ", usingExpressions=" + usingExpressions +
                ", httpHeaders=" + httpHeaders +
                ", contentEncodings=" + contentEncodings +
                ", xpathExpressions=" + xpathExpressions +
                '}';
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private String id;
        private String name;
        private String body;
        private String operationId;
        private SoapMockResponseStatus status;
        private Integer httpStatusCode;
        private Boolean usingExpressions;
        private List<HttpHeader> httpHeaders;
        private List<HttpContentEncoding> contentEncodings;
        private List<SoapXPathExpression> xpathExpressions;

        private Builder() {
        }

        public Builder id(final String id) {
            this.id = id;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder body(final String body) {
            this.body = body;
            return this;
        }

        public Builder operationId(final String operationId) {
            this.operationId = operationId;
            return this;
        }

        public Builder status(final SoapMockResponseStatus status) {
            this.status = status;
            return this;
        }

        public Builder httpStatusCode(final Integer httpStatusCode) {
            this.httpStatusCode = httpStatusCode;
            return this;
        }

        public Builder usingExpressions(final Boolean usingExpressions) {
            this.usingExpressions = usingExpressions;
            return this;
        }

        public Builder httpHeaders(final List<HttpHeader> httpHeaders) {
            this.httpHeaders = httpHeaders;
            return this;
        }

        public Builder contentEncodings(final List<HttpContentEncoding> contentEncodings) {
            this.contentEncodings = contentEncodings;
            return this;
        }

        public Builder xpathExpressions(final List<SoapXPathExpression> xpathExpressions) {
            this.xpathExpressions = xpathExpressions;
            return this;
        }

        public SoapMockResponse build() {
            return new SoapMockResponse(this);
        }
    }
}
