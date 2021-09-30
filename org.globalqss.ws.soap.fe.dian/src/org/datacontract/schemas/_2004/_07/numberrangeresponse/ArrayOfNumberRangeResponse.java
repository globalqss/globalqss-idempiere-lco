
package org.datacontract.schemas._2004._07.numberrangeresponse;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfNumberRangeResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfNumberRangeResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="NumberRangeResponse" type="{http://schemas.datacontract.org/2004/07/NumberRangeResponse}NumberRangeResponse" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfNumberRangeResponse", propOrder = {
    "numberRangeResponse"
})
public class ArrayOfNumberRangeResponse {

    @XmlElement(name = "NumberRangeResponse", nillable = true)
    protected List<NumberRangeResponse> numberRangeResponse;

    /**
     * Gets the value of the numberRangeResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the numberRangeResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNumberRangeResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NumberRangeResponse }
     * 
     * 
     */
    public List<NumberRangeResponse> getNumberRangeResponse() {
        if (numberRangeResponse == null) {
            numberRangeResponse = new ArrayList<NumberRangeResponse>();
        }
        return this.numberRangeResponse;
    }

}
