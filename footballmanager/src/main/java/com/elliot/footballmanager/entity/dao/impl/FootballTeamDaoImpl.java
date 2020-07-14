package com.elliot.footballmanager.entity.dao.impl;

import com.elliot.footballmanager.entity.dao.FootballTeamDao;
import com.elliot.footballmanager.entity.FootballTeam;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.elliot.footballmanager.database.SqliteDatabaseConnector;

/**
 * @author Elliot
 *
 */
public class FootballTeamDaoImpl implements FootballTeamDao {

	@Override
	public Collection<FootballTeam> getAllFootballTeams(Integer leagueId) {
		Collection<FootballTeam> footballTeams = new ArrayList<FootballTeam>();
		String query = "SELECT * FROM FOOTBALL_TEAM WHERE LEAGUE_ID = ?";
		
		try (Connection conn = SqliteDatabaseConnector.connect();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			
			pstmt.setInt(1, leagueId);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				FootballTeam footballTeam = new FootballTeam(rs.getInt("FOOTBALL_TEAM_ID"), rs.getString("TEAM_NAME"),
						rs.getInt("LEAGUE_ID"), rs.getString("LOCATION"), 
						rs.getString("STADIUM"), rs.getInt("STADIUM_CAPACITY"));
				footballTeams.add(footballTeam);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return footballTeams;
	}

	@Override
	public FootballTeam getFootballTeamById(Integer footballTeamId) {
		String query = "SELECT *FROM FOOTBALL_TEAM WHERE FOOTBALL_TEAM_ID = ?";
		
		try (Connection conn = SqliteDatabaseConnector.connect();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, footballTeamId);
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.isAfterLast()) {
				return null;
			}
			return new FootballTeam(rs.getInt("FOOTBALL_TEAM_ID"), rs.getString("TEAM_NAME"),
					rs.getInt("LEAGUE_ID"), rs.getString("LOCATION"), 
					rs.getString("STADIUM"), rs.getInt("STADIUM_CAPACITY"));	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public FootballTeam getFootballTeamByName(String footballTeamName) {
		String query = "SELECT *FROM FOOTBALL_TEAM WHERE TEAM_NAME LIKE ?";
		
		try (Connection conn = SqliteDatabaseConnector.connect();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, footballTeamName);
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.isAfterLast()) {
				return null;
			}
			return new FootballTeam(rs.getInt("FOOTBALL_TEAM_ID"), rs.getString("TEAM_NAME"),
					rs.getInt("LEAGUE_ID"), rs.getString("LOCATION"), 
					rs.getString("STADIUM"), rs.getInt("STADIUM_CAPACITY"));	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
