package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "dinner_event")
@NamedQuery(name = "DinnerEvent.deleteAllRows", query = "DELETE from DinnerEvent ")
public class DinnerEvent
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String time;
    private String location;
    private String dish;
    private Double price;

    @OneToMany(mappedBy = "dinnerEvent")
    private List<Assignment> assignments = new ArrayList<>();

    public DinnerEvent()
    {
    }


    public DinnerEvent(String time, String location, String dish, Double price)
    {
        this.time = time;
        this.location = location;
        this.dish = dish;
        this.price = price;
    }

    public List<Assignment> getAssignments()
    {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments)
    {
        this.assignments = assignments;
    }


    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getDish()
    {
        return dish;
    }

    public void setDish(String dish)
    {
        this.dish = dish;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    @Override
    public String toString()
    {
        return "DinnerEvent{" +
                "id=" + id +
                ", time='" + time + '\'' +
                ", location='" + location + '\'' +
                ", dish='" + dish + '\'' +
                ", price=" + price +
                ", assignments=" + assignments +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DinnerEvent that = (DinnerEvent) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }
}