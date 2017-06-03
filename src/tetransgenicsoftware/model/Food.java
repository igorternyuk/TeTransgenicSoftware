package tetransgenicsoftware.model;

public class Food {
    public static final boolean TRANSGENIC = true;
    private int id;
    private String name;
    private int brand;
    private boolean transgenic;

    public Food(int id, String name, int brand, boolean transgenic) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.transgenic = transgenic;
    }
    
    //este constructor se utiliza para
    //para construir el objeto de Food sin ID
    public Food(){}
    
    public Food(String name, int brand, boolean transgenic) {
        this.name = name;
        this.brand = brand;
        this.transgenic = transgenic;
    }
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBrand() {
        return brand;
    }

    public boolean isTransgenic() {
        return transgenic;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(int brand) {
        this.brand = brand;
    }

    public void setTransgenic(boolean transgenic) {
        this.transgenic = transgenic;
    }
    
    @Override
    public String toString(){
        return "Food: id = " + id + " nombre = " + name + " brand = " + brand + 
                " isTrans = " + isTransgenic();
    }
    
    
}
