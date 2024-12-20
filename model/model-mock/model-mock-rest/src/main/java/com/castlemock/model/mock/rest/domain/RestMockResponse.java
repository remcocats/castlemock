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

package com.castlemock.model.mock.rest.domain;

import com.castlemock.model.core.http.HttpContentEncoding;
import com.castlemock.model.core.http.HttpHeader;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonDeserialize(builder = RestMockResponse.Builder.class)
public class RestMockResponse {

    @XmlElement
    private final String id;

    @XmlElement
    private final String name;

    @XmlElement
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String body;

    @XmlElement
    private final String methodId;

    @XmlElement
    private final Integer httpStatusCode;

    @XmlElement
    private final RestMockResponseStatus status;

    @XmlElement
    private final Boolean usingExpressions;

    @XmlElementWrapper(name = "httpHeaders")
    @XmlElement(name = "httpHeader")
    private final List<HttpHeader> httpHeaders;

    @XmlElementWrapper(name = "contentEncodings")
    @XmlElement(name = "contentEncoding")
    private final List<HttpContentEncoding> contentEncodings;

    @XmlElementWrapper(name = "parameterQueries")
    @XmlElement(name = "parameterQuery")
    private final List<RestParameterQuery> parameterQueries;

    @XmlElementWrapper(name = "xpathExpressions")
    @XmlElement(name = "xpathExpression")
    private final List<RestXPathExpression> xpathExpressions;

    @XmlElementWrapper(name = "jsonPathExpressions")
    @XmlElement(name = "jsonPathExpression")
    private final List<RestJsonPathExpression> jsonPathExpressions;

    @XmlElementWrapper(name = "headerQueries")
    @XmlElement(name = "headerQuery")
    private final List<RestHeaderQuery> headerQueries;


    private RestMockResponse(final Builder builder){
        this.id = Objects.requireNonNull(builder.id, "id");
        this.status = Objects.requireNonNull(builder.status, "status");
        this.name = Objects.requireNonNull(builder.name, "name");
        this.methodId = Objects.requireNonNull(builder.methodId, "methodId");
        this.httpStatusCode = Objects.requireNonNull(builder.httpStatusCode, "httpStatusCode");
        this.usingExpressions = builder.usingExpressions;
        this.body = builder.body;
        this.httpHeaders = Optional.ofNullable(builder.httpHeaders).orElseGet(List::of);
        this.contentEncodings = Optional.ofNullable(builder.contentEncodings).orElseGet(List::of);
        this.parameterQueries = Optional.ofNullable(builder.parameterQueries).orElseGet(List::of);
        this.xpathExpressions = Optional.ofNullable(builder.xpathExpressions).orElseGet(List::of);
        this.jsonPathExpressions = Optional.ofNullable(builder.jsonPathExpressions).orElseGet(List::of);
        this.headerQueries = Optional.ofNullable(builder.headerQueries).orElseGet(List::of);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Optional<String> getBody() {
        return Optional.ofNullable(body);
    }

    public String getMethodId() {
        return methodId;
    }

    public RestMockResponseStatus getStatus() {
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

    public List<RestParameterQuery> getParameterQueries() {
        return Optional.ofNullable(parameterQueries)
                .map(List::copyOf)
                .orElseGet(List::of);
    }

    public List<RestXPathExpression> getXpathExpressions() {
        return Optional.ofNullable(xpathExpressions)
                .map(List::copyOf)
                .orElseGet(List::of);
    }

    public List<RestJsonPathExpression> getJsonPathExpressions() {
        return Optional.ofNullable(jsonPathExpressions)
                .map(List::copyOf)
                .orElseGet(List::of);
    }

    public List<RestHeaderQuery> getHeaderQueries() {
        return Optional.ofNullable(headerQueries)
                .map(List::copyOf)
                .orElseGet(List::of);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestMockResponse that = (RestMockResponse) o;
        return usingExpressions == that.usingExpressions &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(body, that.body) &&
                Objects.equals(methodId, that.methodId) &&
                Objects.equals(httpStatusCode, that.httpStatusCode) &&
                status == that.status &&
                Objects.equals(httpHeaders, that.httpHeaders) &&
                Objects.equals(contentEncodings, that.contentEncodings) &&
                Objects.equals(parameterQueries, that.parameterQueries) &&
                Objects.equals(xpathExpressions, that.xpathExpressions) &&
                Objects.equals(jsonPathExpressions, that.jsonPathExpressions) &&
                Objects.equals(headerQueries, that.headerQueries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, body, methodId, httpStatusCode, status, usingExpressions, httpHeaders, contentEncodings, parameterQueries, xpathExpressions, jsonPathExpressions, headerQueries);
    }

    @Override
    public String toString() {
        return "RestMockResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", body='" + body + '\'' +
                ", methodId='" + methodId + '\'' +
                ", httpStatusCode=" + httpStatusCode +
                ", status=" + status +
                ", usingExpressions=" + usingExpressions +
                ", httpHeaders=" + httpHeaders +
                ", contentEncodings=" + contentEncodings +
                ", parameterQueries=" + parameterQueries +
                ", xpathExpressions=" + xpathExpressions +
                ", jsonPathExpressions=" + jsonPathExpressions +
                ", headerQueries=" + headerQueries +
                '}';
    }



    public Builder toBuilder() {
        return builder()
                .id(id)
                .name(name)
                .body(body)
                .methodId(methodId)
                .status(status)
                .httpStatusCode(httpStatusCode)
                .usingExpressions(usingExpressions)
                .httpHeaders(Optional.ofNullable(httpHeaders)
                        .map(headers -> headers.stream()
                        .map(HttpHeader::toBuilder)
                        .map(HttpHeader.Builder::build)
                        .collect(Collectors.toList()))
                        .orElse(null))
                .contentEncodings(Optional.ofNullable(contentEncodings)
                        .map(ArrayList::new)
                        .orElse(null))
                .parameterQueries(Optional.ofNullable(parameterQueries)
                        .map(queries -> queries.stream()
                                .map(RestParameterQuery::toBuilder)
                                .map(RestParameterQuery.Builder::build)
                                .collect(Collectors.toList()))
                        .orElse(null))
                .xpathExpressions(Optional.ofNullable(xpathExpressions)
                        .map(expressions -> expressions
                                .stream()
                                .map(RestXPathExpression::toBuilder)
                                .map(RestXPathExpression.Builder::build)
                                .collect(Collectors.toList()))
                        .orElse(null))
                .jsonPathExpressions(Optional.ofNullable(jsonPathExpressions)
                        .map(expressions -> expressions
                                .stream()
                                .map(RestJsonPathExpression::toBuilder)
                                .map(RestJsonPathExpression.Builder::build)
                                .collect(Collectors.toList()))
                        .orElse(null))
                .headerQueries(Optional.ofNullable(headerQueries)
                        .map(queries -> queries
                                .stream()
                                .map(RestHeaderQuery::toBuilder)
                                .map(RestHeaderQuery.Builder::build)
                                .collect(Collectors.toList()))
                        .orElse(null));
    }


    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private String id;
        private String name;
        private String body;
        private String methodId;
        private Integer httpStatusCode;
        private RestMockResponseStatus status;
        private Boolean usingExpressions;
        private List<HttpHeader> httpHeaders;
        private List<HttpContentEncoding> contentEncodings;
        private List<RestParameterQuery> parameterQueries;
        private List<RestXPathExpression> xpathExpressions;
        private List<RestJsonPathExpression> jsonPathExpressions;
        private List<RestHeaderQuery> headerQueries;

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

        public Builder methodId(final String methodId) {
            this.methodId = methodId;
            return this;
        }

        public Builder httpStatusCode(final Integer httpStatusCode) {
            this.httpStatusCode = httpStatusCode;
            return this;
        }

        public Builder status(final RestMockResponseStatus status) {
            this.status = status;
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

        public Builder parameterQueries(final List<RestParameterQuery> parameterQueries) {
            this.parameterQueries = parameterQueries;
            return this;
        }

        public Builder xpathExpressions(final List<RestXPathExpression> xpathExpressions) {
            this.xpathExpressions = xpathExpressions;
            return this;
        }

        public Builder jsonPathExpressions(final List<RestJsonPathExpression> jsonPathExpressions) {
            this.jsonPathExpressions = jsonPathExpressions;
            return this;
        }

        public Builder headerQueries(final List<RestHeaderQuery> headerQueries) {
            this.headerQueries = headerQueries;
            return this;
        }

        public RestMockResponse build() {
            return new RestMockResponse(this);
        }
    }
}
