package note;

/**
 * Created by jgu on 8/15/15.
 */
public class Config {
    // number is based on per user
    public static final int MAX_TAG_LENGTH = 50;
    public static final int MAX_KEY_LENGTH = 100;
    public static final int MAX_VALUE_LENGTH = 500; // due to GAE limits, length > 500 will be converted to text

    public static final int MAX_NUMBER_TAGS = 5000;
    public static final int MAX_NUMBER_KEYS = 5000;
    public static final int MAX_NUMBER_RECORD = 100000; // a record is a key-value pair
}
