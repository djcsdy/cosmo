/*
 * Copyright 2006 Open Source Applications Foundation
 * 
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
 */
package org.osaf.cosmo.model.hibernate;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import org.hibernate.annotations.CollectionOfElements;
import org.osaf.cosmo.model.Attribute;
import org.osaf.cosmo.model.ModelValidationException;
import org.osaf.cosmo.model.MultiValueStringAttribute;
import org.osaf.cosmo.model.QName;


/**
 * Hibernate persistent MultiValueStringAttribute.
 */
@Entity
@DiscriminatorValue("multistring")
public class HibMultiValueStringAttribute extends HibAttribute
        implements java.io.Serializable, MultiValueStringAttribute {

    /**
     * 
     */
    private static final long serialVersionUID = 8518583717902318228L;
    
    @CollectionOfElements
    @JoinTable(
            name="cosmo_multistring_values",
            joinColumns = @JoinColumn(name="attributeid")
    )
    @Column(name="stringvalue", length=2048)
    private Set<String> value = new HashSet<String>(0);

    /** default constructor */
    public HibMultiValueStringAttribute() {
    }

    public HibMultiValueStringAttribute(QName qname, Set<String> value)
    {
        setQName(qname);
        this.value = value;
    }

    // Property accessors
    
    /* (non-Javadoc)
     * @see org.osaf.cosmo.model.Attribute#getValue()
     */
    public Set<String> getValue() {
        return this.value;
    }

    /* (non-Javadoc)
     * @see org.osaf.cosmo.model.MultiValueStringAttribute#setValue(java.util.Set)
     */
    public void setValue(Set<String> value) {
        this.value = value;
    }
    
    public void setValue(Object value) {
        if (value != null && !(value instanceof Set))
            throw new ModelValidationException(
                    "attempted to set non Set value on attribute");
        setValue((Set<String>) value);
    }
    
    @Override
	public Attribute copy() {
        MultiValueStringAttribute attr = new HibMultiValueStringAttribute();
        attr.setQName(getQName().copy());
        Set<String> newValue = new HashSet<String>(value);
        attr.setValue(newValue);
        return attr;
    }

}
