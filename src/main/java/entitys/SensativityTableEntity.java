package entitys;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by root on 13.07.2017.
 */
@Entity
@Table(name = "sensativity_table", schema = "biosensor_schema", catalog = "")
@NamedQueries(
        @NamedQuery(name = "GET_VALUE_BY_DATE", query = "SELECT s FROM SensativityTableEntity s WHERE s.time BETWEEN :timestart AND :timeend")
)
public class SensativityTableEntity {
    private int id;
    private Integer value;
    private Timestamp time;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "value")
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SensativityTableEntity that = (SensativityTableEntity) o;

        if (id != that.id) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
