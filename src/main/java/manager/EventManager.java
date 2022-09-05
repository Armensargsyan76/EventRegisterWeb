package manager;

import db.DBConnectionProvider;
import model.Event;
import model.EventType;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EventManager {

    private Connection connection = DBConnectionProvider.getInstance().getConnection();

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void add(Event event) {
        String sql = "insert into event(name, place, is_online, event_type, price, event_date) VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, event.getName());
            ps.setString(2, event.getPlace());
            ps.setBoolean(3, event.isOnline());
            ps.setString(4, event.getEventType().name());
            ps.setDouble(5, event.getPrice());
            ps.setString(6, sdf.format(event.getEventDate()));
            ps.executeUpdate();
            ResultSet resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                event.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Event> getAll() {
        String sql = "SELECT * FROM event";
        List<Event> events = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                events.add(getEventFromResultSet(resultSet));
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return events;
    }

    public Event getById(int id) {
        String sql = "SELECT * FROM event WHERE id =" + id;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                try {
                    return getEventFromResultSet(resultSet);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Event getEventFromResultSet(ResultSet resultSet) throws SQLException, ParseException {
        Event event = new Event();
        event.setId(resultSet.getInt("id"));
        event.setName(resultSet.getString("name"));
        event.setPlace(resultSet.getString("place"));
        event.setOnline(resultSet.getBoolean("is_online"));
        event.setEventType(EventType.valueOf(resultSet.getString("event_type")));
        event.setPrice(resultSet.getDouble("price"));
        event.setEventDate(resultSet.getString("event_date")==null ? null : sdf.parse(resultSet.getString("event_date")));
        return event;
    }

}
