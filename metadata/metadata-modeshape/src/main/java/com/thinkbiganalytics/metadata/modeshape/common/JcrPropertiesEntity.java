/**
 *
 */
package com.thinkbiganalytics.metadata.modeshape.common;

/*-
 * #%L
 * thinkbig-metadata-modeshape
 * %%
 * Copyright (C) 2017 ThinkBig Analytics
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.thinkbiganalytics.metadata.api.Propertied;
import com.thinkbiganalytics.metadata.modeshape.MetadataRepositoryException;
import com.thinkbiganalytics.metadata.modeshape.support.JcrPropertyUtil;
import com.thinkbiganalytics.metadata.modeshape.support.JcrUtil;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.nodetype.ConstraintViolationException;

/**
 *
 */
public class JcrPropertiesEntity extends JcrEntity implements Propertied {

    public static final String PROPERTIES_NAME = "tba:properties";

    /**
     *
     */
    public JcrPropertiesEntity(Node node) {
        super(node);
    }

    public JcrProperties getPropertiesObject() {
        return JcrUtil.getOrCreateNode(this.node, PROPERTIES_NAME, JcrProperties.NODE_TYPE, JcrProperties.class);
    }

    public void clearAdditionalProperties() {
        try {
            Node propsNode = getPropertiesObject().getNode();
            Map<String, Object> props = getPropertiesObject().getProperties();
            for (Map.Entry<String, Object> prop : props.entrySet()) {
                if (!JcrPropertyUtil.hasProperty(propsNode.getPrimaryNodeType(), prop.getKey())) {
                    Property property = propsNode.getProperty(prop.getKey());
                    property.remove();
                }
            }
        } catch (RepositoryException e) {
            throw new MetadataRepositoryException("Unable to clear the Properties for this entity. ", e);
        }
    }

    @Override
    /**
     * This will return just the extra properties.
     * All primary properties should be defined as getter/setter on the base object
     * You can call the getAllProperties to return the complete set of properties as a map
     */
    public Map<String, Object> getProperties() {

        JcrProperties props = getPropertiesObject();
        if (props != null) {
            return props.getProperties();
        }
        return null;
    }

    public void setProperties(Map<String, Object> properties) {

        //add the properties as attrs
        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            setProperty(entry.getKey(), entry.getValue());
        }

    }

    /**
     * Get the Nodes Properties along with the extra mixin properties
     */
    public Map<String, Object> getAllProperties() {

        //first get the other extra mixin properties
        Map<String, Object> properties = getProperties();
        if (properties == null) {
            properties = new HashMap<>();
        }
        //merge in this nodes properties
        Map<String, Object> thisProperties = super.getProperties();
        if (thisProperties != null)

        {
            properties.putAll(thisProperties);
        }
        return properties;
    }

    public <T> T getProperty(String name, T defValue) {
        if (hasProperty(name)) {
            return getProperty(name, (Class<T>) defValue.getClass(), false);
        } else {
            return defValue;
        }
    }

    public boolean hasProperty(String name) {
        try {
            if (this.node.hasProperty(name)) {
                return true;
            } else {
                return getPropertiesObject().getNode().hasProperty(name);
            }
        } catch (RepositoryException e) {
            throw new MetadataRepositoryException("Unable to check Property " + name);
        }
    }

    public <T> T getProperty(String name, Class<T> type) {
        return getProperty(name, type, true);
    }


    @Override
    public <T> T getProperty(String name, Class<T> type, boolean allowNotFound) {
        try {
            if ("nt:frozenNode".equalsIgnoreCase(this.node.getPrimaryNodeType().getName())) {
                T item = super.getProperty(name, type, true);
                if (item == null) {
                    item = getPropertiesObject().getProperty(name, type, allowNotFound);
                }
                return item;
            } else {
                if (JcrPropertyUtil.hasProperty(this.node.getPrimaryNodeType(), name)) {
                    return super.getProperty(name, type, allowNotFound);
                } else {
                    return getPropertiesObject().getProperty(name, type, allowNotFound);
                }
            }
        } catch (RepositoryException e) {
            throw new MetadataRepositoryException("Unable to get Property " + name);
        }

    }

    /**
     * Override
     * if the incoming name matches that of a primary property on this Node then set it, otherwise add it the mixin bag of properties
     */
    public void setProperty(String name, Object value) {
        try {
            if (JcrPropertyUtil.hasProperty(this.node.getPrimaryNodeType(), name)) {
                super.setProperty(name, value);
            } else {
                getPropertiesObject().setProperty(name, value);
            }
        } catch (RepositoryException e) {
            throw new MetadataRepositoryException("Unable to set Property " + name + ":" + value);
        }
    }

    /**
     * Merges any new properties in with the other Extra Properties
     */
    @Override
    public Map<String, Object> mergeProperties(Map<String, Object> props) {
        Map<String, Object> newProps = new HashMap<>();
        Map<String, Object> origProps = getProperties();
        if (origProps != null) {
            newProps.putAll(origProps);
        }
        if (props != null) {
            newProps.putAll(props);
        }
        JcrProperties properties = getPropertiesObject();
        for (Map.Entry<String, Object> entry : newProps.entrySet()) {
            try {
                properties.setProperty(entry.getKey(), entry.getValue());
            } catch (MetadataRepositoryException e) {
                if (ExceptionUtils.getRootCause(e) instanceof ConstraintViolationException) {
                    //this is ok
                } else {
                    throw e;
                }
            }
        }
        return newProps;
    }

    public Map<String, Object> replaceProperties(Map<String, Object> props) {
        clearAdditionalProperties();
        setProperties(props);
        return props;
    }

    @Override
    public void removeProperty(String key) {
        setProperty(key, null);
    }
}
