package org.jnosql.diana.jsr363;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.jnosql.artemis.column.ColumnCrudOperation;
import org.jnosql.diana.api.column.Column;
import org.jnosql.diana.api.column.ColumnCondition;
import org.jnosql.diana.api.column.ColumnQuery;

@ApplicationScoped
public class SensorRepository {


    private static final String TEMPERATURE = "temperature";
    private static final String SENSORS = "sensors";
    private static final Column SENSOR_ID = Column.of("id", Device.ID);

    @Inject
    private ColumnCrudOperation crudOperation;

    public void save(Sensor sensor) {
        crudOperation.save(sensor);
    }


    public List<String> sensors() {
        ColumnQuery query = ColumnQuery.of(SENSORS);
        query.addCondition(ColumnCondition.eq(SENSOR_ID));
        Optional<Device> device = crudOperation.singleResult(query);
        return device.map(Device::getDevices).orElse(Collections.emptyList());
    }

    public void saveSensors(List<String> sensors) {
        Device device = Device.of(sensors);
        if (sensors.size() == 1) {
            crudOperation.save(device);
        } else {
            crudOperation.update(device);
        }
    }

    public List<Sensor> getSensor(String sensorId) {
        ColumnQuery query = ColumnQuery.of(TEMPERATURE);
        query.addCondition(ColumnCondition.eq(Column.of("sensorId", sensorId)));
        return crudOperation.find(query);
    }

}