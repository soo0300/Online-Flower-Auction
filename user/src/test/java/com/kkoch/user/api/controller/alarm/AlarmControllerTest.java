package com.kkoch.user.api.controller.alarm;

import com.kkoch.user.ControllerTestSupport;
import com.kkoch.user.api.controller.alarm.response.AlarmResponse;
import com.kkoch.user.api.service.alarm.AlarmQueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {AlarmController.class})
class AlarmControllerTest extends ControllerTestSupport {

    @MockBean
    private AlarmQueryService alarmQueryService;

    @DisplayName("회원은 본인의 알림을 조회할 수 있다.")
    @Test
    @WithMockUser
    void getAlarms() throws Exception {
        //given
        AlarmResponse alarm1 = createAlarmResponse(1L, "alarm1");
        AlarmResponse alarm2 = createAlarmResponse(2L, "alarm2");
        AlarmResponse alarm3 = createAlarmResponse(3L, "alarm3");
        List<AlarmResponse> responses = List.of(alarm1, alarm2, alarm3);


        given(alarmQueryService.searchAlarms(anyString()))
            .willReturn(responses);

        //when //then
        mockMvc.perform(
                get("/user-service/alarms")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("SUCCESS"))
            .andExpect(jsonPath("$.data").isArray());
    }

    private AlarmResponse createAlarmResponse(long alarmId, String content) {
        return AlarmResponse.builder()
            .alarmId(alarmId)
            .content(content)
            .open(true)
            .createDate(LocalDate.of(2023, 7, 10).atStartOfDay())
            .build();
    }
}