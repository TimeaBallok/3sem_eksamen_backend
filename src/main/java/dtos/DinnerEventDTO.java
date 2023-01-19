package dtos;

import entities.Assignment;
import entities.DinnerEvent;

import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class DinnerEventDTO
{
    private Integer id;
    private String time;
    private String location;
    private String dish;
    private Double price;
    private List<Assignment> assignments = new ArrayList<>();

    public DinnerEventDTO(String time, String location, String dish, Double price)
    {
        this.time = time;
        this.location = location;
        this.dish = dish;
        this.price = price;
    }

    public DinnerEventDTO(DinnerEvent dinnerEvent)
    {
        this.id = dinnerEvent.getId();
        this.time = dinnerEvent.getTime();
        this.location = dinnerEvent.getLocation();
        this.dish = dinnerEvent.getDish();
        this.price = dinnerEvent.getPrice();
    }

    public Integer getId()
    {
        return id;
    }

    public String getTime()
    {
        return time;
    }

    public String getLocation()
    {
        return location;
    }

    public String getDish()
    {
        return dish;
    }

    public Double getPrice()
    {
        return price;
    }

    public List<Assignment> getAssignments()
    {
        return assignments;
    }
}
