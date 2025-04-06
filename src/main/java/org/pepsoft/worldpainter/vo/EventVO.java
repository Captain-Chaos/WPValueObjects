/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pepsoft.worldpainter.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static java.util.stream.Collectors.toMap;

/**
 * A WorldPainter event.
 *
 * @author pepijn
 */
public final class EventVO implements Serializable {
    /**
     * Create a new event with key {@code key}.
     */
    public EventVO(String key) {
        this.key = key;
    }

    /**
     * Set the {@link #ATTRIBUTE_COUNT} attribute and return the event for method chaining.
     */
    public EventVO count(long count) {
        setAttribute(ATTRIBUTE_COUNT, count);
        return this;
    }

    /**
     * Set the {@link #ATTRIBUTE_DURATION} attribute and return the event for method chaining.
     */
    public EventVO duration(long duration) {
        setAttribute(ATTRIBUTE_DURATION, duration);
        return this;
    }

    /**
     * Set the {@link #ATTRIBUTE_TIMESTAMP} attribute and return the event for method chaining.
     */
    public EventVO addTimestamp() {
        setAttribute(ATTRIBUTE_TIMESTAMP, new Date());
        return this;
    }

    /**
     * Get the key of the event.
     */
    public String getKey() {
        return key;
    }

    /**
     * Get a copy of the attributes currently stored on the event indexed by name.
     */
    public Map<String, Serializable> getAttributes() {
        return (attributes != null) ? attributes.entrySet().stream().collect(toMap(entry -> entry.getKey().getKey(), Entry::getValue)) : null;
    }

    /**
     * Replace all attributes currently stored on the event with a new set of attributes indexed by name.
     */
    public void setAttributes(Map<String, Serializable> attributes) {
        this.attributes = (attributes != null) ? attributes.entrySet().stream().collect(toMap(entry -> new AttributeKeyVO<>(entry.getKey()), Entry::getValue)) : null;
    }

    /**
     * Set an arbitrary attribute indexed by {@link AttributeKeyVO}.
     */
    public <T extends Serializable> EventVO setAttribute(AttributeKeyVO<T> key, T value) {
        if (value != null) {
            if (attributes == null) {
                attributes = new HashMap<>();
            }
            attributes.put(key, value);
        } else if ((attributes != null) && attributes.containsKey(key)) {
            attributes.remove(key);
            if (attributes.isEmpty()) {
                attributes = null;
            }
        }
        return this;
    }

    /**
     * Get an arbitrary attribute indexed by {@link AttributeKeyVO}.
     */
    @SuppressWarnings("unchecked")
    public <T extends Serializable> T getAttribute(AttributeKeyVO<T> key) {
        if (attributes != null) {
            return (T) attributes.get(key);
        } else {
            return null;
        }
    }

    /**
     * Set the {@code transient} property to {@code true} and return the event for method chaining.
     */
    public EventVO setTransient() {
        _transient = true;
        return this;
    }

    /**
     * Indicates whether the event is transient, meaning that it should not be stored or transmitted, only broadcast to
     * any registered event listeners.
     */
    public boolean isTransient() {
        return _transient;
    }

    @Override
    public String toString() {
        return "EventVO{" + "key=" + key + ", attributes=" + attributes + '}';
    }
    
    private final String key;
    private Map<AttributeKeyVO<? extends Serializable>, Serializable> attributes;
    private transient boolean _transient;

    /**
     * The number of times the event occurred.
     */
    public static final AttributeKeyVO<Long> ATTRIBUTE_COUNT = new AttributeKeyVO<>("count");
    /**
     * How long the event lasted, in ms.
     */
    public static final AttributeKeyVO<Long> ATTRIBUTE_DURATION = new AttributeKeyVO<>("duration");
    /**
     * When the event started, in ms since the epoch.
     */
    public static final AttributeKeyVO<Date> ATTRIBUTE_TIMESTAMP = new AttributeKeyVO<>("timestamp");

    private static final long serialVersionUID = 1L;
}