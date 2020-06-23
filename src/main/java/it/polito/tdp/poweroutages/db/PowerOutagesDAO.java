package it.polito.tdp.poweroutages.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polito.tdp.poweroutages.model.Adiacenza;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;

public class PowerOutagesDAO {
	
	public Map<Integer,Nerc> loadAllNercs() {

		String sql = "SELECT id, value FROM nerc";
		Map<Integer,Nerc> nercList = new HashMap<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.put(n.getId(),n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	
	/*
	 	select nr.nerc_one, nr.nerc_two, count( distinct year(p1.date_event_began), month(p1.date_event_began))
from nerc n1, nerc n2, nercRelations nr, poweroutages p1, poweroutages p2
where n1.id=nr.nerc_one and n2.id=nr.nerc_two and nr.nerc_one=p1.nerc_id and nr.nerc_two=p2.nerc_id and 
	year(p1.date_event_began)=year(p2.date_event_began) and month(p1.date_event_began)=month(p2.date_event_began)
group by nr.nerc_one,nr.nerc_two
	 */
	public List<Adiacenza> loadAllAdiacenze(Map<Integer,Nerc> nercs) {

		String sql = "select nr.nerc_one, nr.nerc_two, count( distinct year(p1.date_event_began), month(p1.date_event_began)) as peso " + 
				"from nerc as n1, nerc as n2, nercRelations as nr, poweroutages as p1, poweroutages as p2 " + 
				"where n1.id=nr.nerc_one and n2.id=nr.nerc_two and nr.nerc_one=p1.nerc_id and nr.nerc_two=p2.nerc_id and " + 
				"	year(p1.date_event_began)=year(p2.date_event_began) and month(p1.date_event_began)=month(p2.date_event_began) " + 
				"group by nr.nerc_one,nr.nerc_two";
		List<Adiacenza> nercList = new ArrayList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				if(nercs.containsKey(res.getInt("nr.nerc_one"))&&nercs.containsKey(res.getInt("nr.nerc_two"))) {
					
					nercList.add(new Adiacenza(nercs.get(res.getInt("nr.nerc_one")),nercs.get(res.getInt("nr.nerc_two")),res.getInt("peso")));
				}
				
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	public List<PowerOutage> loadAllPOwerOutage(Map<Integer,Nerc> nercs) {

		String sql = "select id, nerc_id, date_event_began, date_event_finished " + 
				"from poweroutages ";
		List<PowerOutage> nercList = new ArrayList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				if(nercs.containsKey(res.getInt("nerc_id"))) {
					
					nercList.add(new PowerOutage(res.getInt("id"),nercs.get(res.getInt("nerc_id")),res.getTimestamp("date_event_began").toLocalDateTime()
							,res.getTimestamp("date_event_finished").toLocalDateTime()));
				}
				
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
}
