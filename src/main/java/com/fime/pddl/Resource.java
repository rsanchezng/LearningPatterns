package com.fime.pddl;

public class Resource {

    private String name;
    private boolean available;
    private int quantity;

    public Resource() {
    }

    public String getName() {
        return this.name;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Resource)) return false;
        final Resource other = (Resource) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$name = this.name;
        final Object other$name = other.name;
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        if (this.available != other.available) return false;
        if (this.quantity != other.quantity) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Resource;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $name = this.name;
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        result = result * PRIME + (this.available ? 79 : 97);
        result = result * PRIME + this.quantity;
        return result;
    }

    public String toString() {
        return "Resource(name=" + this.name + ", available=" + this.available + ", quantity=" + this.quantity + ")";
    }
}
