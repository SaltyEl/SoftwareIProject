package model;

/**
 *Outsourced class extends Part class, and adds companyName field
 *
 * @author Blake Ramsey
 */
public class Outsourced extends Part{

    private String companyName;


    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     *
     * @return Returns company name.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Set company name.
     * @param companyName The company name to set.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
