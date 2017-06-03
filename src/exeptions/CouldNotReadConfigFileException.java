package exeptions;

public class CouldNotReadConfigFileException extends Exception {

    public CouldNotReadConfigFileException() {
    }

    public CouldNotReadConfigFileException(String msg) {
        super(msg);
    }
}
