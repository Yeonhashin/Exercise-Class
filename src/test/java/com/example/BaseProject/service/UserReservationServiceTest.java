package com.example.BaseProject.service;

import com.example.BaseProject.dao.UserReservationDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/db-test-context.xml"})
@Transactional
@Rollback
public class UserReservationServiceTest {

    @Autowired
    private UserReservationDao userReservationDao; // DB 연결용 DAO만 컨텍스트에서 받음

    private UserReservationService userReservationService;
    private EmailAsyncService emailAsyncServiceMock;

    @Before
    public void setUp() throws Exception {
        emailAsyncServiceMock = mock(EmailAsyncService.class);
        doNothing().when(emailAsyncServiceMock).triggerEmailAsync(anyInt(), anyString());

        userReservationService = new UserReservationService(emailAsyncServiceMock);

        // DAO 주입
        Field daoField = ReflectionUtils.findField(UserReservationService.class, "userReservationDao");
        ReflectionUtils.makeAccessible(daoField);
        ReflectionUtils.setField(daoField, userReservationService, userReservationDao);

    }

    @Test
    public void testReserveClass() throws Exception {
        int userId = 1;
        int classId = 282;

        int result = userReservationService.reserveClass(userId, classId, false);

        // insert 성공 여부는 1로 검증
        assertEquals(1, result);

        // 이메일 호출에 전달된 reservationId와 status 검증 (ArgumentCaptor 사용)
        ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> statusCaptor = ArgumentCaptor.forClass(String.class);

        verify(emailAsyncServiceMock, times(1))
                .triggerEmailAsync(idCaptor.capture(), statusCaptor.capture());

        Integer sentReservationId = idCaptor.getValue();
        String sentStatus = statusCaptor.getValue();

        // reservation id는 DB에서 생성된 값이므로 0보다 커야 함
        assertNotNull(sentReservationId);
        assertTrue(sentReservationId > 0);

        // status는 reserve (isWait=false 이므로)
        assertEquals("reserve", sentStatus);
    }

    @Test
    public void testCancelReservation() throws Exception {
        int userId = 1;
        int reservationId = 280;

        // 실제 메소드 호출
        int result = userReservationService.cancelReservation(userId, reservationId, false);

        // DB 업데이트가 정상 수행되었는지 검증
        assertEquals(1, result);

//         이메일 발송 검증 (ArgumentCaptor 사용)
        ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> statusCaptor = ArgumentCaptor.forClass(String.class);

        verify(emailAsyncServiceMock, times(1))
                .triggerEmailAsync(idCaptor.capture(), statusCaptor.capture());

        // 전달된 인자 확인
        assertEquals(reservationId, idCaptor.getValue().intValue());
        assertEquals("cancel", statusCaptor.getValue());
    }

    public static class UserClassService {


    }
}
