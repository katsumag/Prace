package io.github.katsumag.prace;

import io.github.katsumag.prace.Jobs.JobType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class WrappedPlayer {

    private static HashMap<UUID, WrappedPlayer> wrappedPlayers = new HashMap<>();

    private UUID player;
    private Prace main = Prace.get();

    public WrappedPlayer(UUID player){
        this.player = player;
        wrappedPlayers.put(player, this);
    }



    public UUID getPlayer() {
        return player;
    }

    public boolean isInDb(){

        try (PreparedStatement ps = main.getDataBase().prepareStatement("SELECT * FROM `Prace` WHERE `UUID`=?")){
            ps.setString(1, this.player.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean isWrapped(UUID id){

        for (UUID uuid : wrappedPlayers.keySet()) {
            if (uuid == id){
                return true;
            }
        }
        return false;
    }

    public static WrappedPlayer getWrappedPlayer(UUID id){

        if (isWrapped(id)){
            return wrappedPlayers.get(id);
        }

        return new WrappedPlayer(id);

    }

    public void setCurrentJob(JobType type){

        if (isInDb()){
            try (PreparedStatement ps = main.getDataBase().prepareStatement("UPDATE `Prace` SET `selectedJob`=? WHERE `UUID`=?")){
                ps.setString(1, type.getName());
                ps.setString(2, this.player.toString());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else{
            try (PreparedStatement ps = main.getDataBase().prepareStatement("INSERT INTO `Prace` (`UUID`, `selectedJob`) VALUES(?, ?)")){
                ps.setString(1, this.player.toString());
                ps.setString(2, type.getName());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public JobType getCurrentJob(){

        try (PreparedStatement ps = main.getDataBase().prepareStatement("SELECT `selectedJob` FROM `Prace` WHERE `UUID`=?")){
            ps.setString(1, this.player.toString());

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return JobType.fromString(rs.getString("selectedJob"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setLevel(int level, JobType type) {

        if (isInDb()){
            if (type == JobType.MINER){
                try (PreparedStatement ps = main.getDataBase().prepareStatement("UPDATE `Prace` SET `minerLevel`=? WHERE `UUID`=?;")){

                    ps.setInt(1, level);
                    ps.setString(2, this.player.toString());
                    ps.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                if (type == JobType.BUILDER){

                    try (PreparedStatement ps = main.getDataBase().prepareStatement("UPDATE `Prace` SET `builderLevel`=? WHERE `UUID`=?;")){

                        ps.setInt(1, level);
                        ps.setString(2, this.player.toString());
                        ps.executeUpdate();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else{
                    try (PreparedStatement ps = main.getDataBase().prepareStatement("UPDATE `Prace` SET `woodCutterLevel`=? WHERE `UUID`=?;");) {

                        ps.setInt(1, level);
                        ps.setString(2, this.player.toString());
                        ps.executeUpdate();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            if (type == JobType.MINER){
                try (PreparedStatement ps = main.getDataBase().prepareStatement("INSERT INTO `Prace` (`UUID`, `minerLevel`) VALUES(?, ?);")){

                    ps.setString(1, this.player.toString());
                    ps.setInt(2, level);
                    ps.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                if (type == JobType.BUILDER){

                    try (PreparedStatement ps = main.getDataBase().prepareStatement("INSERT INTO `Prace` (`UUID`, `builderLevel`) VALUES(?, ?);")){

                        ps.setString(1, this.player.toString());
                        ps.setInt(2, level);
                        ps.executeUpdate();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else{
                    try (PreparedStatement ps = main.getDataBase().prepareStatement("INSERT INTO `Prace` (`UUID`, `woodCutterLevel`) VALUES(?, ?);");) {

                        ps.setString(1, this.player.toString());
                        ps.setInt(2, level);
                        ps.executeUpdate();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public int getLevel(JobType type) {

        if (type == JobType.BUILDER){

            try (PreparedStatement ps = main.getDataBase().prepareStatement("SELECT `builderLevel` FROM `Prace` WHERE `UUID`=?")){
                ps.setString(1, this.player.toString());

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()){
                        return rs.getInt("builderLevel");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else{

            if (type == JobType.MINER){

                try (PreparedStatement ps = main.getDataBase().prepareStatement("SELECT `minerLevel` FROM `Prace` WHERE `UUID`=?")) {
                    ps.setString(1, this.player.toString());

                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()){
                            return rs.getInt("minerLevel");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else{

                try (PreparedStatement ps = main.getDataBase().prepareStatement("SELECT `woodCutterLevel` FROM `Prace` WHERE `UUID`=?")) {
                    ps.setString(1, this.player.toString());

                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()){
                            return rs.getInt("woodCutterLevel");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return 0;
    }

    public void setEXP(int exp, JobType type) {

        if (isInDb()){
            if (type == JobType.MINER){
                try (PreparedStatement ps = main.getDataBase().prepareStatement("UPDATE `Prace` SET `minerEXP`=? WHERE `UUID`=?;")){

                    ps.setInt(1, exp);
                    ps.setString(2, this.player.toString());
                    ps.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                if (type == JobType.BUILDER){

                    try (PreparedStatement ps = main.getDataBase().prepareStatement("UPDATE `Prace` SET `builderEXP`=? WHERE `UUID`=?;")){

                        ps.setInt(1, exp);
                        ps.setString(2, this.player.toString());
                        ps.executeUpdate();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else{
                    try (PreparedStatement ps = main.getDataBase().prepareStatement("UPDATE `Prace` SET `woodCutterEXP`=? WHERE `UUID`=?;");) {

                        ps.setInt(1, exp);
                        ps.setString(2, this.player.toString());
                        ps.executeUpdate();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            if (type == JobType.MINER){
                try (PreparedStatement ps = main.getDataBase().prepareStatement("INSERT INTO `Prace` (`UUID`, `minerEXP`) VALUES(?, ?);")){

                    ps.setString(1, this.player.toString());
                    ps.setInt(2, exp);
                    ps.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                if (type == JobType.BUILDER){

                    try (PreparedStatement ps = main.getDataBase().prepareStatement("INSERT INTO `Prace` (`UUID`, `builderEXP`) VALUES(?, ?);")){

                        ps.setString(1, this.player.toString());
                        ps.setInt(2, exp);
                        ps.executeUpdate();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else{
                    try (PreparedStatement ps = main.getDataBase().prepareStatement("INSERT INTO `Prace` (`UUID`, `woodCutterEXP`) VALUES(?, ?);");) {

                        ps.setString(1, this.player.toString());
                        ps.setInt(2, exp);
                        ps.executeUpdate();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public int getEXP(JobType type){

        if (type == JobType.BUILDER){

            try (PreparedStatement ps = main.getDataBase().prepareStatement("SELECT `builderEXP` FROM `Prace` WHERE `UUID`=?")){
                ps.setString(1, this.player.toString());

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()){
                        return rs.getInt("builderEXP");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else{

            if (type == JobType.MINER){

                try (PreparedStatement ps = main.getDataBase().prepareStatement("SELECT `minerEXP` FROM `Prace` WHERE `UUID`=?")) {
                    ps.setString(1, this.player.toString());

                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()){
                            return rs.getInt("minerEXP");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else{

                try (PreparedStatement ps = main.getDataBase().prepareStatement("SELECT `woodCutterEXP` FROM `Prace` WHERE `UUID`=?")) {
                    ps.setString(1, this.player.toString());

                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()){
                            return rs.getInt("woodCutterEXP");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return 0;
    }

}
