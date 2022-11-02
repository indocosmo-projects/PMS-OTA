package com.indocosmo.pms.web.housekeeping.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.room.model.Room;

@Repository
public class HKDaoImpl implements HKDao {

	@Autowired
	public SessionFactory sessionFactory;

	public boolean updateHk(int roomid, String roomNum, int hkStatus) throws Exception {
		boolean isSave = false;
		try {
			Session session = sessionFactory.getCurrentSession();

			Room room = (Room) session.load(Room.class, roomid);
			room.setHkStatus((byte) hkStatus);
			session.update(room);
			isSave = true;
		} catch (Exception e) {
			isSave = false;
			e.printStackTrace();
			throw new CustomException();
		}
		return isSave;
	}
}
