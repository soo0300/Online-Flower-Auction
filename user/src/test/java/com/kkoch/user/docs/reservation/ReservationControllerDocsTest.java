package com.kkoch.user.docs.reservation;

import com.kkoch.user.api.controller.reservation.ReservationController;
import com.kkoch.user.api.controller.reservation.request.AddReservationRequest;
import com.kkoch.user.api.controller.reservation.response.ReservationResponse;
import com.kkoch.user.api.service.reservation.ReservationService;
import com.kkoch.user.api.service.reservation.dto.AddReservationDto;
import com.kkoch.user.docs.RestDocsSupport;
import com.kkoch.user.domain.Grade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.kkoch.user.domain.Grade.SUPER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReservationControllerDocsTest extends RestDocsSupport {

    private final ReservationService reservationService = mock(ReservationService.class);

    @Override
    protected Object initController() {
        return new ReservationController(reservationService);
    }

    @DisplayName("거래 예약 등록 API")
    @Test
    void addReservation() throws Exception {
        AddReservationRequest request = AddReservationRequest.builder()
            .type("장미(스탠다드)")
            .name("하젤")
            .grade(SUPER)
            .count(10)
            .price(2500)
            .build();

        ReservationResponse response = ReservationResponse.builder()
            .count(10)
            .price(2500)
            .grade(SUPER.getText())
            .createdDate(LocalDateTime.now())
            .build();

        given(reservationService.addReservation(anyString(), any(AddReservationDto.class)))
            .willReturn(response);

        mockMvc.perform(post("/{memberKey}/reservations", UUID.randomUUID().toString())
                .header("Authorization", "token")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("reservation-create",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("type").type(JsonFieldType.STRING)
                        .description("품목명"),
                    fieldWithPath("name").type(JsonFieldType.STRING)
                        .description("품종명"),
                    fieldWithPath("grade").type(JsonFieldType.STRING)
                        .description("예약 등급"),
                    fieldWithPath("count").type(JsonFieldType.NUMBER)
                        .description("예약 단수"),
                    fieldWithPath("price").type(JsonFieldType.NUMBER)
                        .description("예약 가격")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.count").type(JsonFieldType.NUMBER)
                        .description("예약 단수"),
                    fieldWithPath("data.price").type(JsonFieldType.NUMBER)
                        .description("예약 가격"),
                    fieldWithPath("data.grade").type(JsonFieldType.STRING)
                        .description("예약 등급"),
                    fieldWithPath("data.createdDate").type(JsonFieldType.STRING)
                        .description("예약 등록일")
                )
            ));
    }
}
