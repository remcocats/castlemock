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

package com.castlemock.model.core.configuration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import jakarta.xml.bind.annotation.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * The configuration group DTO is a DTO (Data transfer object) class for the configuration group class. The
 * class contains both an identifier (See {@link #name}) and all the configurations (See {@link #configurations}) that
 * are being grouped by the configuration group.
 * @author Karl Dahlgren
 * @since 1.0
 * @see ConfigurationGroup
 * @see Configuration
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = ConfigurationGroup.Builder.class)
public class ConfigurationGroup {

    @XmlElement
    private final String id;

    @XmlElement
    private final String name;

    @XmlElementWrapper(name = "configurations")
    @XmlElement(name = "configuration")
    private final List<Configuration> configurations;

    private ConfigurationGroup(final Builder builder){
        this.id = Objects.requireNonNull(builder.id, "id");
        this.name = Objects.requireNonNull(builder.name, "name");
        this.configurations = Optional.ofNullable(builder.configurations).orElseGet(List::of);
    }

    /**
     * Returns the configuration group id
     * @return The configuration group id
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the name of the configuration group
     * @return The new of the configuration group
     */
    public String getName() {
        return name;
    }


    /**
     * Returns a list of configurations the belongs to the group
     * @return Configurations that belongs to the configuration group
     */
    public List<Configuration> getConfigurations() {
        return Optional.ofNullable(configurations)
                .map(List::copyOf)
                .orElseGet(List::of);
    }


    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private String id;
        private String name;
        private List<Configuration> configurations;

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

        public Builder configurations(final List<Configuration> configurations) {
            this.configurations = configurations;
            return this;
        }

        public ConfigurationGroup build() {
            return new ConfigurationGroup(this);
        }
    }
}
