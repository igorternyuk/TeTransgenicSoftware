package exeptions;

public class BrandNotFoundException extends Exception {

    public BrandNotFoundException() {
    }

    public BrandNotFoundException(String msg) {
        super(msg);
    }
}
