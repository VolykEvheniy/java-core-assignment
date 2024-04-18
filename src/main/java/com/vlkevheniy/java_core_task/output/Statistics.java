package com.vlkevheniy.java_core_task.output;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Represents a collection of statistical data items.
 */
@XmlRootElement(name = "statistics")
@XmlAccessorType(XmlAccessType.FIELD)
public class Statistics {

    private List<Item> item;

    public List<Item> getItem() {
        return item;
    }

    public void setItems(List<Item> item) {
        this.item = item;
    }

    /**
     * Represents a single statistical data item with a value and count.
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    static class Item {
        private String value;
        private Integer count;

        /**
         * Creates a new statistical data item.
         *
         * @param value The value of the item.
         * @param count The count of the item.
         */
        public Item(String value, Integer count) {
            this.value = value;
            this.count = count;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }
}
