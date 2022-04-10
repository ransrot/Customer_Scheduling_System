package Models;

/** Represents the customer in the database
 * @author Alex Bader
 * @version 1.0
 */

public class Customer {

    /** id of the customer */
    private int id;

    /** name of the customer */
    private String name;

    /** address of the customer */
    private String address;

    /** postalcode of the customer */
    private String postalCode;

    /** phonenumber of the customer */
    private String phoneNumber;

    /** div id of the customer */
    private int divisionId;

    /**
     *
     * @param id id of customer
     * @param name name of customer
     * @param address address of customer
     * @param postalCode postalcode of customer
     * @param phoneNumber phone number of customer
     * @param divisionId div id of customer
     */
    public Customer(int id, String name, String address, String postalCode, String phoneNumber, int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
    }

    /**
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @return postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     *
     * @return phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     *
     * @return div id
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     *
     * @param name set name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param address set address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @param postalCode set postalcode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     *
     * @param phoneNumber set phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     *
     * @param divisionId set div id
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
}
