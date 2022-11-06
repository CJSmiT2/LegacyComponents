package ua.org.smit.legacy.tags;

import java.util.Objects;

/**
 *
 * @author smit
 */
public class TagName {

    private final String value;

    public TagName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.value);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TagName other = (TagName) obj;
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        return true;
    }

}
