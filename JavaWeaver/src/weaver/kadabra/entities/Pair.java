package weaver.kadabra.entities;


/**
 * 
 * 
 * @author Lara C.
 */
public class Pair extends Object {

    private String left;
    private String right;

    /**
     * 
     */
    public Pair(){}
    /**
     * 
     */
    public Pair(String left, String right){
        this.setLeft(left);
        this.setRight(right);
    }
    /**
     * Get value on attribute left
     * @return the attribute's value
     */
    public String getLeft() {
        return this.left;
    }

    /**
     * Set value on attribute left
     * @param left
     */
    public void setLeft(String left) {
        this.left = left;
    }

    /**
     * Get value on attribute right
     * @return the attribute's value
     */
    public String getRight() {
        return this.right;
    }

    /**
     * Set value on attribute right
     * @param right
     */
    public void setRight(String right) {
        this.right = right;
    }

    /**
     * 
     */
    @Override
    public String toString() {
        String json = "{\n";
        json += " left: "+getLeft() + ",\n";
        json += " right: "+getRight() + ",\n";
        json+="}";
        return json;
    }
}
