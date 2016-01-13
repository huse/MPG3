package CustomListView;

/**
 * Created by f1 on 1/4/2016.
 */
public class Model {
  /*  private int id;
    private String mpg;
    private String fuel;
    private String date;
    private String distance;
    private String price;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFuel() {
        return fuel;
    }

    public String getMpg() {
        return mpg;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public void setMpg(String mpg) {
        this.mpg = mpg;
    }*/

    public Model(){}

    public Model(String mpg, String fuel, String date, String distance, String price) {
        super();
/*        this.mpg = mpg;
        this.fuel = fuel;
        this.date = date;
        this.distance = distance;
        this.price = price;*/
    }

    private String name;
    private boolean selected;
    private Boolean running;
    public Model(String name) {
        this.name = name;
        selected = false;
        running = false;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public boolean isRunning() {
        return running;
    }
    public void setRuning(boolean running) {
        this.running = running;
    }
}
