package com.example.BaseProject.dao;

import com.example.BaseProject.domain.UserReservationDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/db-test-context.xml"})
@Transactional
@Rollback(true) // 각 테스트 실행 후 자동 롤백
public class UserReservationDaoTest {

    @Autowired
    private UserReservationDao userReservationDao;


    /**
     * selectAllReservedClassByUser 테스트
     */
    @Test
    public void testSelectAllReservedClassByUser() throws Exception {
        // given
        UserReservationDto reservation = new UserReservationDto();
        reservation.setUser_id(1);  // 실제 DB에 존재하는 user_id 필요
        reservation.setClass_id(246); // 실제 DB에 존재하는 class_id 필요
        userReservationDao.insert(reservation);
        // when
        List<UserReservationDto> list = userReservationDao.reservedAllClassByUser(1, 0, 10);

        // then
        assertNotNull(list);
        assertTrue(list.size() > 0);
        assertEquals(Integer.valueOf(1), list.get(0).getUser_id());
    }

    /**
     * selectReservedClassByUser 테스트
     */
    @Test
    public void testSelectReservedClassByUser() throws Exception {
        // given
        UserReservationDto reservation = new UserReservationDto();
        reservation.setUser_id(1);
        reservation.setClass_id(246);
        userReservationDao.insert(reservation);
        // when
        List<UserReservationDto> list = userReservationDao.reservedClassByUser(1);

        // then
        assertNotNull(list);
        assertTrue(list.size() > 0);
        assertEquals(Integer.valueOf(1), list.get(0).getUser_id());
    }

    /**
     * selectClassById 테스트
     */
    @Test
    public void testSelectClassById() throws Exception {
        // given
        UserReservationDto reservation = new UserReservationDto();
        reservation.setUser_id(1);
        reservation.setClass_id(246);
        userReservationDao.insert(reservation);

        int reservationId = reservation.getId();

        // when
        UserReservationDto result = userReservationDao.selectClassById(reservationId);

        // then
        assertNotNull(result);
        assertEquals(Integer.valueOf(1), result.getUser_id());
        assertEquals(Integer.valueOf(246), result.getClass_id());
    }
}
