package com.fime.pddl;

public class Resource {

    private String resourceName;
    private boolean resourceFree;
    private int resourceQuantity;

    public Resource() {
    }

    public String getResourceName() {
        return this.resourceName;
    }

    public boolean isResourceFree() {
        return this.resourceFree;
    }

    public int getResourceQuantity() {
        return this.resourceQuantity;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public void setResourceFree(boolean resourceFree) {
        this.resourceFree = resourceFree;
    }

    public void setResourceQuantity(int resourceQuantity) {
        this.resourceQuantity = resourceQuantity;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Resource)) return false;
        final Resource other = (Resource) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$resourceName = this.resourceName;
        final Object other$resourceName = other.resourceName;
        if (this$resourceName == null ? other$resourceName != null : !this$resourceName.equals(other$resourceName))
            return false;
        if (this.resourceFree != other.resourceFree) return false;
        if (this.resourceQuantity != other.resourceQuantity) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Resource;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $resourceName = this.resourceName;
        result = result * PRIME + ($resourceName == null ? 43 : $resourceName.hashCode());
        result = result * PRIME + (this.resourceFree ? 79 : 97);
        result = result * PRIME + this.resourceQuantity;
        return result;
    }

    public String toString() {
        return "Resource(resourceName=" + this.resourceName + ", resourceFree=" + this.resourceFree + ", resourceQuantity=" + this.resourceQuantity + ")";
    }
}
