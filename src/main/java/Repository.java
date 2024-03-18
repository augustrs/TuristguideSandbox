import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Repository {
List<Attraction> attractionList = new ArrayList<>();
    public int findTouristAttractionFromListSQL(String name) {

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://turistguidesql.mysql.database.azure.com/attraction_tags", "turistguidesql", "Joakimerdum1")) {
            String SQL = "SELECT * FROM ATTRACTION WHERE ANAME = ?";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();

            int id = rs.getInt("ATTRACTIONID");
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public int findTouristTagFromListSQL(String name) {

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://turistguidesql.mysql.database.azure.com/attraction_tags", "turistguidesql", "Joakimerdum1")) {
            String SQL = "SELECT * FROM TAGS WHERE TAGNAME = ?";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();

            int id = rs.getInt("TAGID");
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    public void deleteTouristAttractionFromListSQL(String name) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://turistguidesql.mysql.database.azure.com/attraction_tags", "turistguidesql", "Joakimerdum1")) {
            int id = findTouristAttractionFromListSQL(name);
            if (id != -1) {
                String deleteAttractionTagsSQL = "DELETE FROM ATTRACTIONTAGS WHERE ATTRACTIONID = ?";
                PreparedStatement psAttractionTags = connection.prepareStatement(deleteAttractionTagsSQL);
                psAttractionTags.setInt(1, id);
                psAttractionTags.executeUpdate();

                String deleteAttractionSQL = "DELETE FROM ATTRACTION WHERE ATTRACTIONID = ?";
                PreparedStatement psAttraction = connection.prepareStatement(deleteAttractionSQL);
                psAttraction.setInt(1, id);
                psAttraction.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void createTouristAttractionOnListSQL(String name, String description, String location) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://turistguidesql.mysql.database.azure.com/attraction_tags", "turistguidesql", "Joakimerdum1")) {
            String SQL = "INSERT INTO ATTRACTION (ATTRACTIONID, ANAME, DESCR, LOC) VALUES (DEFAULT, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addTagOnAttraction(String attractionName, String tagName) {
        int attractionId = findTouristAttractionFromListSQL(attractionName);
        int tagId = findTouristTagFromListSQL(tagName);

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://turistguidesql.mysql.database.azure.com/attraction_tags", "turistguidesql", "Joakimerdum1")) {
            String SQL = "INSERT INTO ATTRACTIONTAGS (TAGID, ATTRACTIONID) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setInt(1, tagId);
            ps.setInt(2, attractionId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateName(String oldName, String newName) {
        int id = findTouristAttractionFromListSQL(oldName);

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://turistguidesql.mysql.database.azure.com/attraction_tags", "turistguidesql", "Joakimerdum1")) {
            String SQL = "UPDATE ATTRACTION SET ANAME = ? WHERE ATTRACTIONID = ?";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1, newName);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateDescription(String name, String newDescription) {
        int id = findTouristAttractionFromListSQL(name);

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://turistguidesql.mysql.database.azure.com/attraction_tags", "turistguidesql", "Joakimerdum1")) {
            String SQL = "UPDATE ATTRACTION SET DESCR = ? WHERE ATTRACTIONID = ?";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1, newDescription);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateLocation(String name, String newLocation) {
        int id = findTouristAttractionFromListSQL(name);

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://turistguidesql.mysql.database.azure.com/attraction_tags", "turistguidesql", "Joakimerdum1")) {
            String SQL = "UPDATE ATTRACTION SET LOC = ? WHERE ATTRACTIONID = ?";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1, newLocation);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Attraction> getAttractionAsObject() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://turistguidesql.mysql.database.azure.com/attraction_tags", "turistguidesql", "Joakimerdum1")) {
            String SQL = "SELECT * FROM ATTRACTION";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("ATTRACTIONID");
                String name = rs.getString("ANAME");
                String description = rs.getString("DESCR");
                String location = rs.getString("LOC");
                List<String> tags = getTagsForAttraction(id);
                attractionList.add(Attraction.createAttraction(id, name, description, location,tags));
            }
            return attractionList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public List<String> getTagsForAttraction(int attractionId) {
            List<String> tags = new ArrayList<>();
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://turistguidesql.mysql.database.azure.com/attraction_tags", "turistguidesql", "Joakimerdum1")) {
                String SQL = "SELECT tags.TAGNAME FROM ATTRACTIONTAGS at INNER JOIN TAGS tags ON at.TAGID = tags.TAGID WHERE at.ATTRACTIONID = ?";
                PreparedStatement ps = connection.prepareStatement(SQL);
                ps.setInt(1, attractionId);
                ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                    tags.add(rs.getString("TAGNAME"));
                }
                return tags;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

}
