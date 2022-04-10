package Models;

/** All 12 months on the calendar
 * @author Alex Bader
 * @version 1.0
 */

public class Month {
    /** How many times January appears */
    private int janCount;

    /** How many times february appears */
    private int febCount;

    /** How many times march appears */
    private int marchCount;

    /** How many times april appears */
    private int aprilCount;

    /** How many times may appears */
    private int mayCount;

    /** How many times june appears */
    private int juneCount;

    /** How many times july appears */
    private int julyCount;

    /** How many times august appears */
    private int augCount;

    /** How many times september appears */
    private int sepCount;

    /** How many times october appears */
    private int octCount;

    /** How many times november appears */
    private int novCount;

    /** How many times december appears */
    private int decCount;

    /**
     *
     * @param janCount january
     * @param febCount february
     * @param marchCount march
     * @param aprilCount april
     * @param mayCount may
     * @param juneCount june
     * @param julyCount july
     * @param augCount august
     * @param sepCount september
     * @param octCount october
     * @param novCount november
     * @param decCount december
     */
    public Month(int janCount, int febCount, int marchCount, int aprilCount,
                 int mayCount, int juneCount, int julyCount,
                 int augCount, int sepCount, int octCount, int novCount, int decCount) {
        this.janCount = janCount;
        this.febCount = febCount;
        this.marchCount = marchCount;
        this.aprilCount = aprilCount;
        this.mayCount = mayCount;
        this.juneCount = juneCount;
        this.julyCount = julyCount;
        this.augCount = augCount;
        this.sepCount = sepCount;
        this.octCount = octCount;
        this.novCount = novCount;
        this.decCount = decCount;
    }

    /**
     *
     * @return count of january
     */
    public int getJanCount() {
        return janCount;
    }

    /**
     *
     * @return count of february
     */
    public int getFebCount() {
        return febCount;
    }

    /**
     *
     * @return count of march
     */
    public int getMarchCount() {
        return marchCount;
    }

    /**
     *
     * @return count of april
     */
    public int getAprilCount() {
        return aprilCount;
    }

    /**
     *
     * @return count of may
     */
    public int getMayCount() {
        return mayCount;
    }

    /**
     *
     * @return count of june
     */
    public int getJuneCount() {
        return juneCount;
    }

    /**
     *
     * @return count of july
     */
    public int getJulyCount() {
        return julyCount;
    }

    /**
     *
     * @return count of august
     */
    public int getAugCount() {
        return augCount;
    }

    /**
     *
     * @return count of september
     */
    public int getSepCount() {
        return sepCount;
    }

    /**
     *
     * @return count of october
     */
    public int getOctCount() {
        return octCount;
    }

    /**
     *
     * @return count of november
     */
    public int getNovCount() {
        return novCount;
    }

    /**
     *
     * @return count of december
     */
    public int getDecCount() {
        return decCount;
    }
}
