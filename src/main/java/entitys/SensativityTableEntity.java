package entitys;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by root on 13.07.2017.
 */
@Entity
//@Table(name = "sensativity_table", schema = "biosensor_schema", catalog = "")
@NamedQueries(
        @NamedQuery(name = "GET_VALUE_BY_DATE",
                query = "SELECT s FROM SensativityTableEntity s WHERE s.date BETWEEN :timestart AND :timeend")
)
public class SensativityTableEntity {
    @Id
    @GeneratedValue
    private int id;
    private Integer value;
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SensativityTableEntity that = (SensativityTableEntity) o;

        if (id != that.id) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
