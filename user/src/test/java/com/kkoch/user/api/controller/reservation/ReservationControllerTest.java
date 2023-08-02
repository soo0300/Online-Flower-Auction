package com.kkoch.user.api.controller.reservation;

import com.kkoch.user.ControllerTestSupport;
import com.kkoch.user.api.controller.reservation.request.AddReservationRequest;
import com.kkoch.user.api.service.reservation.ReservationService;
import com.kkoch.user.api.service.reservation.dto.AddReservationDto;
import com.kkoch.user.jwt.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ReservationController.class})
public class ReservationControllerTest extends ControllerTestSupport {

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private JwtUtil jwtUtil;

    @DisplayName("거래 예약 항목을 입력받아 거래 예약을 등록")
    @Test
    @WithMockUser
    public void addReservation() throws Exception {
        // given
        AddReservationRequest request = AddReservationRequest.builder()
                .plantId(1L)
                .count(5)
                .price(1500)
                .build();

        // when
        given(reservationService.addReservation(anyString(), any(AddReservationDto.class))).willReturn(1L);

        // then
        mockMvc.perform(
                        post("/user-service/reservations")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data").isNumber());
    }

    @DisplayName("회원은 자신의 거래 예약을 삭제할 수 있다.")
    @Test
    @WithMockUser
    public void removeReservation() throws Exception {
        // given
        AddReservationRequest request = AddReservationRequest.builder()
                .plantId(1L)
                .count(5)
                .price(1500)
                .build();
        Long reservationId = 1L;

        // when
        given(reservationService.removeReservation(any())).willReturn(reservationId);

        // then
        mockMvc.perform(
                        delete("/user-service/reservations/{reservationId}", reservationId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data").isNumber());
    }
}

